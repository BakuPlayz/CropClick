package com.github.bakuplayz.cropclick.update;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.Param;
import com.github.bakuplayz.cropclick.utils.RequestUtil;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class UpdateManager {

    private final CropClick plugin;

    private @Getter @Setter(AccessLevel.PRIVATE) String updateURL;
    private @Getter @Setter(AccessLevel.PRIVATE) String updateMessage;
    private @Getter @Setter(AccessLevel.PRIVATE) UpdateState updateState;


    public UpdateManager(@NotNull CropClick plugin) {
        setUpdateState(UpdateState.NOT_FETCHED_YET);
        setUpdateMessage("");
        this.plugin = plugin;

        //startInterval();
    }


    /**
     * It sends the update message to the sender.
     *
     * @param sender The CommandSender to send the message to.
     */
    public void sendAlert(@NotNull CommandSender sender) {
        sender.sendMessage(MessageUtils.colorize(updateMessage));
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
        String UPDATE_URL = "https://api.github.com/repos/Bakuplayz/CropClick/releases";
        try {
            JsonElement response = new RequestUtil(UPDATE_URL)
                    .setDefaultHeaders()
                    .setParams(
                            new Param("serverVersion", getServerVersion()),
                            new Param("pluginVersion", getPluginVersion())
                    )
                    .post(true)
                    .getResponse();

            if (response.isJsonNull()) {
                setUpdateState(UpdateState.UP_TO_DATE);
                setUpdateMessage("");
                setUpdateURL("");
                return;
            }

            JsonArray updates = response.getAsJsonArray();
            JsonObject update = updates.get(0).getAsJsonObject();
            JsonElement updateMsg = update.get("message");
            JsonElement updateUrl = update.get("url");
            if (updateMsg.isJsonNull() || updateUrl.isJsonNull()) {
                setUpdateState(UpdateState.FAILED_TO_FETCH);
                setUpdateMessage("");
                setUpdateURL("");
                return;
            }

            setUpdateURL(updateUrl.getAsString());
            setUpdateMessage(updateMsg.getAsString());
            setUpdateState(UpdateState.NEW_UPDATE);
        } catch (Exception e) {
            e.printStackTrace();
            setUpdateState(UpdateState.FAILED_TO_FETCH);
            LanguageAPI.Console.UPDATE_FETCH_FAILED.send();
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

            case UP_TO_DATE:
                return LanguageAPI.Menu.GENERAL_STATES_UP_TO_DATE.get(plugin);

            case NOT_FETCHED_YET:
                return LanguageAPI.Menu.GENERAL_STATES_NOT_YET_FETCHED.get(plugin);

            case FAILED_TO_FETCH:
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