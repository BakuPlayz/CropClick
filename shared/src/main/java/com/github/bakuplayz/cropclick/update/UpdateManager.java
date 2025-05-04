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
import com.github.bakuplayz.cropclick.api.UpdateAPI;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.common.http.HttpParam;
import com.github.bakuplayz.cropclick.common.http.HttpRequestBuilder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;


/**
 * A manager controlling the plugin's updates.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class UpdateManager implements UpdateAPI {

    /**
     * The URL to the {@link CropClick CropClick's} update server.
     */
    private final static String UPDATE_URL = "https://spigot.bakuplayz.dev/CropClick";

    private final CropClick plugin;

    @Getter
    private final UpdateInfo info;


    public UpdateManager(@NotNull CropClick plugin) {
        this.info = new UpdateInfo("", "", "", UpdateState.NOT_FETCHED_YET);
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

                if (response.getState() == UpdateState.UP_TO_DATE) {
                    info.resetTo(UpdateState.UP_TO_DATE);
                    return;
                }

                if (response.getState() == UpdateState.NO_UPDATE_FOUND) {
                    info.resetTo(UpdateState.NO_UPDATE_FOUND);
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
            } catch (IOException e) {
                Log.info("Update fetch failed. Make sure your online to keep CropClick up to date.");
                info.resetTo(UpdateState.FAILED_TO_FETCH);
                return;
            }
            // TODO: Send alert...
        }, 0, 30 * 60 * 20);
    }


    /**
     * Checks whether {@link CropClick} is up-to-date or not.
     *
     * @return true if it is, otherwise false.
     */
    @Override
    public boolean isUpdated() {
        return getInfo().getState() == UpdateState.UP_TO_DATE;
    }


    /**
     * Gets the current local version of CropClick.
     *
     * @return the local version of CropClick.
     */
    @NotNull
    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

}