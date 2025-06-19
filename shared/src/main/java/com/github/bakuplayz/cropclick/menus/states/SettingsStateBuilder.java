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

package com.github.bakuplayz.cropclick.menus.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.menus.SettingsMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.DefaultConfig.ConfigurationKey;


/**
 * A class for creating and handling the {@link SettingsMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SettingsStateBuilder {

    @NotNull
    public static SettingsMenuStateHandler createStateHandler(@NotNull SettingsMenu menu, @NotNull CropClick plugin) {
        return new SettingsMenuStateHandler(menu, plugin.getConfigManager().getDefaultConfig());
    }


    public final static class SettingsMenuStateHandler extends MenuStateHandler<SettingsMenuState, SettingsMenu> {

        private final DefaultConfig config;


        private SettingsMenuStateHandler(@NotNull SettingsMenu observer, @NotNull DefaultConfig config) {
            super(observer, new SettingsMenuState(config));
            this.config = config;
        }


        public void toggleAutofarmState() {
            updateState(state.isAutoFarmEnabled, (state) -> !state, SettingsMenuStateFlag.AUTOFARM_TOGGLE);
        }


        @Override
        protected <P> SettingsMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == SettingsMenuStateFlag.AUTOFARM_TOGGLE) {
                state.setAutoFarmEnabled(infer(partial));
                config.set(ConfigurationKey.AUTOFARMS_ENABLED, infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class SettingsMenuState implements MenuState {

        private boolean isAutoFarmEnabled;


        private SettingsMenuState(@NotNull DefaultConfig config) {
            this.isAutoFarmEnabled = config.getBoolean(ConfigurationKey.AUTOFARMS_ENABLED);
        }

    }

    public static final class SettingsMenuStateFlag {

        public final static int AUTOFARM_TOGGLE = 0x00000001;

    }

}
