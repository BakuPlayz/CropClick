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
package com.github.bakuplayz.cropclick.menus.abstracts.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.configurations.config.AddonsConfig;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractAddonMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.AddonsConfig.ConfigurationKey;


/**
 * A class for creating and handling the {@link AbstractAddonMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class AddonMenuStateBuilder {

    @NotNull
    public static AddonMenuStateHandler createStateHandler(@NotNull AbstractAddonMenu menu, @NotNull CropClick plugin, @NotNull AbstractAddon addon) {
        return new AddonMenuStateHandler(menu, plugin, addon);
    }


    public final static class AddonMenuStateHandler extends MenuStateHandler<AddonMenuState, AbstractAddonMenu> {

        private final AbstractAddon addon;

        private final AddonsConfig config;


        private AddonMenuStateHandler(@NotNull AbstractAddonMenu observer, @NotNull CropClick plugin, @NotNull AbstractAddon addon) {
            super(observer, new AddonMenuState(addon));
            this.addon = addon;
            this.config = plugin.getConfigManager().getAddonsConfig();
        }


        public void toggleAddon() {
            updateState(state.isAddonEnabled, (state) -> !state, AddonMenuStateFlag.ADDON_STATE);
        }


        @Override
        protected <P> AddonMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == AddonMenuStateFlag.ADDON_STATE) {
                state.setAddonEnabled(infer(partial));
                config.set(ConfigurationKey.ADDON_ENABLED, addon.getName(), infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class AddonMenuState implements MenuState {

        private boolean isAddonEnabled;


        private AddonMenuState(@NotNull AbstractAddon addon) {
            this.isAddonEnabled = addon.isEnabled();
        }

    }

    public static final class AddonMenuStateFlag {

        public final static int ADDON_STATE = 0x1;

    }

}
