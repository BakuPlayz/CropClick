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
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * An enumeration representing all the possible update states the plugin can be in.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public enum UpdateState {

    /**
     * The response sent when a new update was found on the update server.
     */
    NEW_UPDATE,

    /**
     * The response sent when no update at all was found on the update server.
     */
    NO_UPDATE_FOUND,

    /**
     * The response sent when no new update was found on the update server.
     */
    UP_TO_DATE,

    /**
     * The response sent when the update server has yet to be checked for an update.
     */
    NOT_FETCHED_YET,

    /**
     * The response sent when an update was unable to be retrieved.
     */
    FAILED_TO_FETCH;


    @NotNull
    public String toReadable(@NotNull CropClick plugin) {
        switch (this) {
            case NEW_UPDATE:
                return GENERAL_STATES_NEW_UPDATE.get(plugin);

            case NO_UPDATE_FOUND:
                return GENERAL_STATES_NO_UPDATE_FOUND.get(plugin);

            case UP_TO_DATE:
                return GENERAL_STATES_UP_TO_DATE.get(plugin);

            case FAILED_TO_FETCH:
                return GENERAL_STATES_FAILED_TO_FETCH.get(plugin);

            case NOT_FETCHED_YET:
            default:
                return GENERAL_STATES_NOT_YET_FETCHED.get(plugin);
        }
    }

}