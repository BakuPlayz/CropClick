/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.menus.settings.sounds.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Maths;
import com.github.bakuplayz.cropclick.common.types.Sound;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.settings.sounds.SoundMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link SoundMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SoundStateBuilder {

    private final static int NO_ORDER = -1;


    @NotNull
    public static SoundMenuStateHandler createStateHandler(@NotNull SoundMenu menu, @NotNull CropClick plugin, @NotNull Crop crop, @NotNull String soundName) {
        return new SoundMenuStateHandler(menu, plugin, crop, soundName);
    }


    public final static class SoundMenuStateHandler extends MenuStateHandler<SoundMenuState, SoundMenu> {


        private final Crop crop;

        private final String soundName;

        private final CropsConfig cropsConfig;


        private SoundMenuStateHandler(@NotNull SoundMenu observer, @NotNull CropClick plugin, @NotNull Crop crop, @NotNull String soundName) {
            super(observer, new SoundMenuState(plugin, crop, soundName));
            this.cropsConfig = plugin.getConfigManager().getCropsConfig();
            this.soundName = soundName;
            this.crop = crop;
        }


        public void decreaseDelay(int decrement) {
            updateState(state.delay, (state) -> Math.max(state - decrement, Sound.MIN_DELAY), SoundMenuStateFlag.DELAY);
            updateOrderIfRemovable();
            updateOrderStatus();
        }


        public void increaseDelay(int increment) {
            updateState(state.delay, (state) -> Math.min(state + increment, Sound.MAX_DELAY), SoundMenuStateFlag.DELAY);
            updateOrderStatus();
        }


        public void decreaseVolume(int decrement) {
            updateState(state.volume, (state) -> Math.max(state - decrement, Sound.MIN_VOLUME), SoundMenuStateFlag.VOLUME);
            updateOrderIfRemovable();
            updateOrderStatus();
        }


        public void increaseVolume(int increment) {
            updateState(state.volume, (state) -> Math.min(state + increment, Sound.MAX_VOLUME), SoundMenuStateFlag.VOLUME);
            updateOrderStatus();
        }


        public void decreasePitch(double decrement) {
            updateState(state.pitch, (state) -> Math.max(state - decrement, Sound.MIN_PITCH), SoundMenuStateFlag.PITCH);
            updateOrderIfRemovable();
            updateOrderStatus();
        }


        public void increasePitch(double increment) {
            updateState(state.pitch, (state) -> Math.min(state + increment, Sound.MAX_PITCH), SoundMenuStateFlag.PITCH);
            updateOrderStatus();
        }


        public void decreaseOrder() {
            updateState(state.order, (state) -> --state, SoundMenuStateFlag.ORDER);
        }


        public void increaseOrder() {
            updateState(state.order, (state) -> ++state, SoundMenuStateFlag.ORDER);
        }


        private void updateOrderStatus() {
            if (state.delay == 0) return;
            if (state.pitch == 0) return;
            if (state.volume == 0) return;

            state.setOrder(cropsConfig.getSoundOrder(crop, soundName));
            state.setMaxOrder(cropsConfig.countKeys(ConfigurationKey.SOUNDS, crop.getName()) - 1);
            updateState(state.hasOrder, (s) -> state.getOrder() != NO_ORDER, SoundMenuStateFlag.ORDER_STATE);
        }


        private void updateOrderIfRemovable() {
            if (state.delay != 0) return;
            if (state.pitch != 0) return;
            if (state.volume != 0) return;

            state.setOrder(NO_ORDER);
            state.setMaxOrder(state.maxOrder - 1);
            updateState(state.hasOrder, (s) -> state.getOrder() != NO_ORDER, SoundMenuStateFlag.ORDER_STATE);
        }


        @Override
        protected <P> SoundMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == SoundMenuStateFlag.DELAY) {
                state.setDelay(infer(partial));
                cropsConfig.set(ConfigurationKey.SOUND_DELAY, infer(partial), crop.getName(), soundName);
            }

            if (flag == SoundMenuStateFlag.VOLUME) {
                state.setVolume(infer(partial));
                cropsConfig.set(ConfigurationKey.SOUND_VOLUME, infer(partial), crop.getName(), soundName);
            }

            if (flag == SoundMenuStateFlag.PITCH) {
                state.setPitch(Maths.roundFormatted(infer(partial)));
                cropsConfig.set(ConfigurationKey.SOUND_PITCH, Maths.roundFormatted(infer(partial)), crop.getName(), soundName);
            }

            if (flag == SoundMenuStateFlag.ORDER) {
                cropsConfig.swapSoundOrder(crop, state.order, infer(partial));
                state.setOrder(infer(partial));
            }

            if (flag == SoundMenuStateFlag.ORDER_STATE) {
                if (state.order == NO_ORDER) {
                    cropsConfig.set(ConfigurationKey.SOUND, null, crop.getName(), soundName);
                }
                state.setHasOrder(infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class SoundMenuState implements MenuState {

        private long delay;

        private double volume;

        private double pitch;

        private int order;

        private int maxOrder;

        private boolean hasOrder;


        private SoundMenuState(@NotNull CropClick plugin, @NotNull Crop crop, @NotNull String soundName) {
            CropsConfig config = plugin.getConfigManager().getCropsConfig();

            this.volume = config.getDouble(ConfigurationKey.SOUND_VOLUME, crop.getName(), soundName);
            this.pitch = config.getDouble(ConfigurationKey.SOUND_PITCH, crop.getName(), soundName);
            this.delay = config.getLong(ConfigurationKey.SOUND_DELAY, crop.getName(), soundName);
            this.maxOrder = config.countKeys(ConfigurationKey.SOUNDS, crop.getName()) - 1;
            this.order = config.getSoundOrder(crop, soundName);
            this.hasOrder = order != NO_ORDER;
        }

    }

    public static final class SoundMenuStateFlag {

        public final static int DELAY = 0x1;

        public final static int VOLUME = 0x2;

        public final static int PITCH = 0x3;

        public final static int ORDER = 0x4;

        public final static int ORDER_STATE = 0x5;

    }

}
