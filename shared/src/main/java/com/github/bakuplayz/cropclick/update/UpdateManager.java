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
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.common.network.HttpParam;
import com.github.bakuplayz.cropclick.common.network.HttpRequestBuilder;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import dev.bakuplayz.spigotstore.task.api.TaskContext;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.github.bakuplayz.cropclick.configurations.config.DefaultConfig.ConfigurationKey;


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
    private final static String UPDATE_URL = "https://spigot.bakuplayz.dev/CropClick";

    private final CropClick plugin;


    @Getter
    private final UpdateInfo info;

    @Getter
    private final NotificationFeatures notifications;


    public UpdateManager(@NotNull CropClick plugin) {
        this.info = new UpdateInfo("", "", "", UpdateState.NOT_FETCHED_YET);
        this.notifications = new NotificationFeatures(plugin.getConfigManager().getDefaultConfig());
        this.plugin = plugin;

        start();
    }


    private void start() {
        plugin.getTaskScheduler().scheduleRepeatingTask(() -> {
            try {
                UpdateResponse response = new HttpRequestBuilder<>(UPDATE_URL, UpdateResponse.class)
                                                  .setDefaultHeaders()
                                                  .setParams(
                                                          new HttpParam("serverVersion", Versions.getServerVersion()),
                                                          new HttpParam("pluginVersion", getVersion())
                                                  )
                                                  .post(true)
                                                  .getResponse();

                if (response == null) {
                    info.resetTo(UpdateState.FAILED_TO_FETCH);
                    return;
                }

                if (response.getState() == UpdateState.UP_TO_DATE || response.getState() == UpdateState.NO_UPDATE_FOUND) {
                    Log.info("Searched for updates and found none. You are up to date :)");
                    info.resetTo(response.getState());
                    return;
                }

                String title = response.getTitle();
                String message = response.getMessage();
                String url = response.getUrls().get("short");
                if (message == null || url == null || title == null) {
                    info.resetTo(UpdateState.FAILED_TO_FETCH);
                    return;
                }

                info.setUrl(url);
                info.setTitle(title);
                info.setMessage(message);
                info.setState(UpdateState.NEW_UPDATE);
                Log.info("Searched for updates and found one!");
            } catch (IOException e) {
                Log.info("Failed to fetch update. Make sure your online to keep CropClick up to date.");
                info.resetTo(UpdateState.FAILED_TO_FETCH);
                return;
            }

            notifications.alertConsole();
        }, TaskContext.BACKGROUND, 0, 30 * 60 * 20);
    }


    /**
     * Checks whether {@link CropClick} is up-to-date or not.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isUpdated() {
        return getInfo().getState() == UpdateState.UP_TO_DATE;
    }


    /**
     * Gets the current local version of CropClick.
     *
     * @return the local version of CropClick.
     */
    @NotNull
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }


    @RequiredArgsConstructor
    public final class NotificationFeatures {

        @NotNull
        private final DefaultConfig config;


        public void alertPlayer(@NotNull Player player) {
            if (!config.getBoolean(ConfigurationKey.UPDATE_MESSAGE_PLAYER)) {
                return;
            }

            if (isUpdated()) {
                Messages.readify("Searched for updates and found none. You are up to date :)", 10)
                        .stream().map(Messages::colorize)
                        .forEach(player::sendMessage);
                return;
            }

            player.sendMessage("Searched for updates and found one!");
            player.sendMessage(Messages.colorize(String.format("Title: &f%s", info.getTitle())));
            player.sendMessage(Messages.colorize(String.format("Link: &f%s", info.getUrl())));
            player.sendMessage(Messages.colorize(String.format("Message: &f%s", info.getMessage())));
        }


        public void alertConsole() {
            if (!config.getBoolean(ConfigurationKey.UPDATE_MESSAGE_CONSOLE)) {
                return;
            }

            if (isUpdated()) {
                Log.info("Searched for updates and found none. You are up to date :)");
                return;
            }

            Log.info("Searched for updates and found one!");
            Log.info(Messages.colorize(String.format("Title: &f%s", info.getTitle())));
            Log.info(Messages.colorize(String.format("Link: &f%s", info.getUrl())));
            Log.info(Messages.colorize(String.format("Message: &f%s", info.getMessage())));
        }


    }

}