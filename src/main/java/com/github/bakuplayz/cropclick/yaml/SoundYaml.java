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

package com.github.bakuplayz.cropclick.yaml;

import com.github.bakuplayz.cropclick.runnables.sounds.Sound;
import com.github.bakuplayz.cropclick.utils.Enableable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


/**
 * A class representing a Sound object as YAML.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
@EqualsAndHashCode
public final class SoundYaml implements Yamlable, Enableable {

    /**
     * The given delay before playing the sound ({@link Sound#MIN_DELAY min delay} & {@link Sound#MAX_DELAY max delay}).
     */
    private @Setter @Getter @Accessors(chain = true) double delay;

    /**
     * The given pitch the sound should be played at ({@link Sound#MIN_PITCH min pitch} & {@link Sound#MAX_PITCH max pitch}).
     */
    private @Setter @Getter @Accessors(chain = true) double pitch;

    /**
     * The given range-in-blocks or volume the sound should be played at ({@link Sound#MIN_VOLUME min volume} & {@link Sound#MAX_VOLUME max volume}).
     */
    private @Setter @Getter @Accessors(chain = true) double volume;


    public SoundYaml(double delay, double pitch, double volume) {
        this.delay = delay;
        this.pitch = pitch;
        this.volume = volume;
    }


    /**
     * It converts {@link SoundYaml this} object to a YAML-styled map.
     *
     * @return a {@link HashMap YAML-formatted HashMap}.
     */
    @Override
    @Contract(" -> new")
    public @NotNull Map<String, Object> toYaml() {
        return new HashMap<String, Object>() {{
            put("delay", delay);
            put("pitch", pitch);
            put("volume", volume);
        }};
    }


    /**
     * It checks whether the Sound is enabled, by validating only ones with a {@link #volume} and {@link #pitch} more than zero.
     *
     * @return true if the sound is enabled, otherwise false.
     */
    @Override
    public boolean isEnabled() {
        return volume > 0.0 && pitch > 0.0;
    }

}