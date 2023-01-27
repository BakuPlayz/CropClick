/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.update;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.http.HttpParam;
import com.github.bakuplayz.cropclick.http.HttpRequestBuilder;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
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

        if (updateState != UpdateState.NEW_UPDATE) {
            MessageUtils.readify(LanguageAPI.Update.UPDATE_FOUND_NO_UPDATES.get(), 10)
                        .stream().map(MessageUtils::colorize)
                        .forEach(player::sendMessage);
            return;
        }

        LanguageAPI.Update.UPDATE_FOUND_NEW_UPDATE.send(player);
        LanguageAPI.Update.UPDATE_TITLE_FORMAT_PLAYER.send(player, updateTitle);
        LanguageAPI.Update.UPDATE_LINK_FORMAT_PLAYER.send(player, updateURL);
        LanguageAPI.Update.UPDATE_MESSAGE_FORMAT_PLAYER.send(player, updateMessage);
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

        if (updateState != UpdateState.NEW_UPDATE) {
            LanguageAPI.Update.UPDATE_FOUND_NO_UPDATES.send(logger);
            return;
        }

        LanguageAPI.Update.UPDATE_FOUND_NEW_UPDATE.send(logger);
        LanguageAPI.Update.UPDATE_TITLE_FORMAT_LOGGER.send(logger, updateTitle);
        LanguageAPI.Update.UPDATE_LINK_FORMAT_LOGGER.send(logger, updateURL);
        LanguageAPI.Update.UPDATE_MESSAGE_FORMAT_LOGGER.send(logger, updateMessage);
    }


    /**
     * Fetches the updates from the {@link #UPDATE_URL update server}.
     */
    public void fetchUpdate() {
        try {
            JsonElement response = new HttpRequestBuilder(UpdateManager.UPDATE_URL)
                    .setDefaultHeaders()
                    .setParams(
                            new HttpParam("serverVersion", VersionUtils.getServerVersion()),
                            new HttpParam("pluginVersion", plugin.getDescription().getVersion())
                    )
                    .post(true)
                    .getResponse();

            if (response == null) {
                setUpdateProperties(UpdateState.FAILED_TO_FETCH);
                return;
            }

            if (response.getAsJsonObject().has("status")) {
                setUpdateProperties(UpdateState.UP_TO_DATE);
                return;
            }

            if (!response.getAsJsonObject().has("version")) {
                setUpdateProperties(UpdateState.NO_UPDATE_FOUND);
                return;
            }

            JsonObject version = response.getAsJsonObject();
            JsonElement versionTitle = version.get("title");
            JsonElement versionMessage = version.get("message");
            JsonElement versionUrl = version.get("urls").getAsJsonObject().get("short");
            if (versionMessage == null || versionUrl == null || versionTitle == null) {
                setUpdateProperties(UpdateState.FAILED_TO_FETCH);
                return;
            }

            setUpdateProperties(
                    versionUrl.getAsString(),
                    versionTitle.getAsString(),
                    versionMessage.getAsString(),
                    UpdateState.NEW_UPDATE
            );
        } catch (Exception e) {
            e.printStackTrace();
            setUpdateProperties(UpdateState.FAILED_TO_FETCH);
            LanguageAPI.Update.UPDATE_FETCH_FAILED.send(plugin.getLogger());
            return;
        }
        sendAlert(plugin.getLogger());
    }


    /**
     * Sets {@link #updateURL}, {@link #updateTitle}, {@link #updateMessage} and {@link #updateState} to the provided.
     *
     * @param url     the url to set.
     * @param title   the title to set.
     * @param message the message to set.
     * @param state   the update state to set.
     */
    private void setUpdateProperties(String url, String title, String message, UpdateState state) {
        setUpdateURL(url);
        setUpdateTitle(title);
        setUpdateMessage(message);
        setUpdateState(state);
    }


    /**
     * Sets {@link #updateURL}, {@link #updateTitle}, {@link #updateMessage} to an empty string and {@link #updateState} to the provided.
     *
     * @param state the update state to set.
     */
    private void setUpdateProperties(UpdateState state) {
        setUpdateProperties("", "", "", state);
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