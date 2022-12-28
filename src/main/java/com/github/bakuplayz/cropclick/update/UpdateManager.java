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
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * A manager controlling the plugin's updates.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class UpdateManager {

    private final static String UPDATE_URL = "https://bakuplayz-plugins-api.vercel.app/CropClick";

    private final CropClick plugin;


    private @Getter @Setter(AccessLevel.PRIVATE) String updateURL;
    private @Getter @Setter(AccessLevel.PRIVATE) String updateMessage;
    private @Getter @Setter(AccessLevel.PRIVATE) UpdateState updateState;


    public UpdateManager(@NotNull CropClick plugin) {
        setUpdateState(UpdateState.NOT_FETCHED_YET);
        setUpdateMessage("");
        this.plugin = plugin;

        startInterval();
    }


    /**
     * It sends the update message to the sender.
     *
     * @param sender The sender to send the message to.
     */
    public void sendAlert(CommandSender sender) {
        if (sender instanceof Player) {
            if (!getPlayerMessageState()) {
                return;
            }
        }

        if (sender instanceof ConsoleCommandSender) {
            if (!getConsoleMessageState()) {
                return;
            }
        }

        if (updateMessage.equals("")) {
            //TODO: LanuageAPI later
            sender.sendMessage(MessageUtils.colorize("[&aCropClick&r] Searched for updates and found none. You are up to date :)"));
            return;
        }

        sender.sendMessage(MessageUtils.colorize("[&aCropClick&r] " + updateMessage));
    }


    /**
     * Run the fetch function in an interval, with the given period in-between each fetch.
     */
    private void startInterval() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(
                plugin,
                this::fetch,
                0,
                60 * 30 * 20  // (30 minutes in ticks)
        );
    }


    /**
     * It fetches the new update state and message from the server, defined in the URL variable above.
     */
    private void fetch() {
        try {
            JsonElement response = new HttpRequestBuilder(UPDATE_URL)
                    .setDefaultHeaders()
                    .setParams(
                            new HttpParam("serverVersion", getServerVersion()),
                            new HttpParam("pluginVersion", getPluginVersion())
                    )
                    .post(true)
                    .getResponse();

            if (response.isJsonNull()) {
                setUpdateState(UpdateState.FAILED_TO_FETCH);
                setUpdateMessage("");
                setUpdateURL("");
            }

            if (response.getAsJsonObject().has("status")) {
                setUpdateState(UpdateState.UP_TO_DATE);
                setUpdateMessage("");
                setUpdateURL("");
                return;
            }

            JsonArray versions = response.getAsJsonObject().get("versions").getAsJsonArray();
            if (versions.size() == 0) {
                setUpdateState(UpdateState.NO_UPDATE_FOUND);
                setUpdateMessage("");
                setUpdateURL("");
                return;
            }

            JsonObject version = versions.get(0).getAsJsonObject();
            JsonElement versionMsg = version.get("message");
            JsonElement versionUrl = version.get("url");
            if (versionMsg.isJsonNull() || versionUrl.isJsonNull()) {
                setUpdateState(UpdateState.FAILED_TO_FETCH);
                setUpdateMessage("");
                setUpdateURL("");
                return;
            }

            setUpdateURL(versionUrl.getAsString());
            setUpdateMessage(versionMsg.getAsString());
            setUpdateState(UpdateState.NEW_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
            setUpdateState(UpdateState.FAILED_TO_FETCH);
            LanguageAPI.Console.UPDATE_FETCH_FAILED.send();
        } finally {
            sendAlert(Bukkit.getConsoleSender());
        }
    }


    /**
     * If the update state is up-to-date, then return true.
     *
     * @return The updateState equal to UP_TO_DATE.
     */
    public boolean isUpdated() {
        return updateState == UpdateState.UP_TO_DATE;
    }


    /**
     * Gets the plugin version from the plugin description.
     *
     * @return The version of the plugin.
     */
    private @NotNull String getPluginVersion() {
        return plugin.getDescription().getVersion();
    }


    /**
     * Gets the server version from bukkit.
     *
     * @return The server version.
     */
    private @NotNull String getServerVersion() {
        return VersionUtils.getServerVersion();
    }


    /**
     * It returns a message that tells the user the update state.
     *
     * @return The message that is being returned is the message that is being displayed in the menu.
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
     * It returns a boolean value from the config.yml file.
     *
     * @return The boolean value of the "updateMessage.player" key in the config.yml file.
     */
    public boolean getPlayerMessageState() {
        return plugin.getConfig().getBoolean("updateMessage.player", true);
    }


    /**
     * It toggles the state of the player message.
     */
    public void togglePlayerMessage() {
        boolean state = getPlayerMessageState();
        plugin.getConfig().set("updateMessage.player", !state);
        plugin.saveConfig();
    }


    /**
     * It returns a boolean value from the config.yml file.
     *
     * @return The boolean value of the "updateMessage.console" key in the config.yml file.
     */
    public boolean getConsoleMessageState() {
        return plugin.getConfig().getBoolean("updateMessage.console", true);
    }


    /**
     * It toggles the state of the console message.
     */
    public void toggleConsoleMessage() {
        boolean state = getConsoleMessageState();
        plugin.getConfig().set("updateMessage.console", !state);
        plugin.saveConfig();
    }

}