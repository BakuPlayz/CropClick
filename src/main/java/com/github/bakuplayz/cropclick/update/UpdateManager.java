package com.github.bakuplayz.cropclick.update;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.http.HttpParam;
import com.github.bakuplayz.cropclick.http.HttpRequestBuilder;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;


/**
 * A manager controlling the plugin's updates.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class UpdateManager {

    /**
     * The URL to the {@link CropClick CropClick's} update server.
     */
    private final static String UPDATE_URL = "https://bakuplayz-plugins-api.vercel.app/CropClick";

    private final CropClick plugin;

    private @Getter @Setter(AccessLevel.PRIVATE) String updateURL;
    private @Getter @Setter(AccessLevel.PRIVATE) String updateTitle;
    private @Getter @Setter(AccessLevel.PRIVATE) String updateMessage;
    private @Getter @Setter(AccessLevel.PRIVATE) UpdateState updateState;


    public UpdateManager(@NotNull CropClick plugin) {
        setUpdateState(UpdateState.NOT_FETCHED_YET);
        setUpdateMessage("");
        setUpdateTitle("");
        this.plugin = plugin;

        fetchUpdates();
    }


    /**
     * Sends the {@link #updateMessage update message} to the {@link Player provided player}.
     *
     * @param player the player to send the message to.
     */
    public void sendAlert(@NotNull Player player) {
        if (!canPlayerReceiveUpdates()) {
            return;
        }

        if (updateMessage.equals("")) {
            LanguageAPI.Update.UPDATE_FOUND_NO_UPDATES.send(player);
            return;
        }

        LanguageAPI.Update.UPDATE_FOUND_NEW_UPDATE.send(player);
        LanguageAPI.Update.UPDATE_TITLE_FORMAT_PLAYER.send(player, updateTitle);
        LanguageAPI.Update.UPDATE_LINK_FORMAT_PLAYER.send(player, updateURL);
        for (String message : MessageUtils.readify(MessageUtils.colorize("&7Message: &7") + updateMessage, 10)) {
            LanguageAPI.Update.UPDATE_MESSAGE_FORMAT_PLAYER.send(player, message);
        }
    }


    /**
     * Sends the {@link #updateMessage update message} to the {@link Logger provided logger}.
     *
     * @param logger the logger to send the message to.
     */
    public void sendAlert(@NotNull Logger logger) {
        if (!canConsoleReceiveUpdates()) {
            return;
        }

        if (updateMessage.equals("")) {
            LanguageAPI.Update.UPDATE_FOUND_NO_UPDATES.send(logger);
            return;
        }

        LanguageAPI.Update.UPDATE_FOUND_NEW_UPDATE.send(logger);
        LanguageAPI.Update.UPDATE_TITLE_FORMAT_LOGGER.send(logger, updateTitle);
        LanguageAPI.Update.UPDATE_LINK_FORMAT_LOGGER.send(logger, updateURL);
        for (String message : MessageUtils.readify("Message: " + updateMessage, 15)) {
            LanguageAPI.Update.UPDATE_MESSAGE_FORMAT_LOGGER.send(logger, message);
        }
    }


    /**
     * Starts fetching updates from the {@link #UPDATE_URL update server}.
     */
    private void fetchUpdates() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                this::fetchUpdate,
                0, // ticks before running the first time.
                60 * 30 * 20  // ticks before running again (30 minutes as ticks).
        );
    }


    /**
     * Fetches the updates from the {@link #UPDATE_URL update server}.
     */
    private void fetchUpdate() {
        try {
            JsonElement response = new HttpRequestBuilder(UPDATE_URL)
                    .setDefaultHeaders()
                    .setParams(
                            new HttpParam("serverVersion", VersionUtils.getServerVersion()),
                            new HttpParam("pluginVersion", plugin.getDescription().getVersion())
                    )
                    .post(true)
                    .getResponse();

            if (response == null) {
                setUpdateState(UpdateState.FAILED_TO_FETCH);
                setUpdateMessage("");
                setUpdateTitle("");
                setUpdateURL("");
                return;
            }

            if (response.getAsJsonObject().has("status")) {
                setUpdateState(UpdateState.UP_TO_DATE);
                setUpdateMessage("");
                setUpdateTitle("");
                setUpdateURL("");
                return;
            }

            JsonArray versions = response.getAsJsonObject().get("versions").getAsJsonArray();
            if (versions.size() == 0) {
                setUpdateState(UpdateState.NO_UPDATE_FOUND);
                setUpdateMessage("");
                setUpdateTitle("");
                setUpdateURL("");
                return;
            }

            JsonObject version = versions.get(0).getAsJsonObject();
            JsonElement versionUrl = version.get("shortUrl");
            JsonElement versionTitle = version.get("title");
            JsonElement versionMsg = version.get("message");
            if (versionMsg == null || versionUrl == null || versionTitle == null) {
                setUpdateState(UpdateState.FAILED_TO_FETCH);
                setUpdateMessage("");
                setUpdateTitle("");
                setUpdateURL("");
                return;
            }

            setUpdateURL(versionUrl.getAsString());
            setUpdateTitle(versionTitle.getAsString());
            setUpdateMessage(versionMsg.getAsString());
            setUpdateState(UpdateState.NEW_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
            setUpdateState(UpdateState.FAILED_TO_FETCH);
            LanguageAPI.Update.UPDATE_FETCH_FAILED.send(plugin.getLogger());
        } finally {
            sendAlert(plugin.getLogger());
        }
    }


    /**
     * Checks whether {@link CropClick} is up-to-date or not.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isUpdated() {
        return updateState == UpdateState.UP_TO_DATE;
    }


    /**
     * Gets the {@link UpdateState update state's} message.
     *
     * @return the update state's message.
     */
    public @NotNull String getUpdateStateMessage() {
        switch (updateState) {
            case NEW_UPDATE:
                return LanguageAPI.Menu.GENERAL_STATES_NEW_UPDATE.get(plugin);

            case NO_UPDATE_FOUND:
                return LanguageAPI.Menu.GENERAL_STATES_NO_UPDATE_FOUND.get(plugin);

            case UP_TO_DATE:
                return LanguageAPI.Menu.GENERAL_STATES_UP_TO_DATE.get(plugin);

            case NOT_FETCHED_YET:
                return LanguageAPI.Menu.GENERAL_STATES_NOT_YET_FETCHED.get(plugin);

            default:
                return LanguageAPI.Menu.GENERAL_STATES_FAILED_TO_FETCH.get(plugin);
        }
    }


    /**
     * Checks whether {@link Player OP players} can receive {@link #updateMessage update messages}.
     *
     * @return true if they can, otherwise false (default: true).
     */
    public boolean canPlayerReceiveUpdates() {
        return plugin.getConfig().getBoolean("updateMessage.player", true);
    }


    /**
     * Toggles the {@link #updateMessage update message} for {@link Player OP players}.
     */
    public void toggleUpdatesPlayer() {
        plugin.getConfig().set("updateMessage.player", !canPlayerReceiveUpdates());
        plugin.saveConfig();
    }


    /**
     * Checks whether {@link ConsoleCommandSender the console} can receive {@link #updateMessage update messages}.
     *
     * @return true if it can, otherwise false (default: true).
     */
    public boolean canConsoleReceiveUpdates() {
        return plugin.getConfig().getBoolean("updateMessage.console", true);
    }


    /**
     * Toggles the {@link #updateMessage update message} for {@link ConsoleCommandSender the console}.
     */
    public void toggleConsoleMessage() {
        plugin.getConfig().set("updateMessage.console", !canConsoleReceiveUpdates());
        plugin.saveConfig();
    }

}