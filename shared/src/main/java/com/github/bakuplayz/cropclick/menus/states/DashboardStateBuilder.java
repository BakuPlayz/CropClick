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
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.menus.AutofarmsMenu;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.DefaultConfig.ConfigurationKey;


public final class DashboardStateBuilder {

    @NotNull
    public static DashboardMenuStateHandler createStateHandler(@NotNull AutofarmsMenu menu, @NotNull CropClick plugin, @NotNull Player player) {
        return new DashboardMenuStateHandler(menu, plugin, CropPlayer.fromPlayer(player));
    }


    public final static class DashboardMenuStateHandler extends MenuStateHandler<DashboardMenuState, AutofarmsMenu> {

        private final CropPlayer player;

        private final DefaultConfig config;


        public DashboardMenuStateHandler(@NotNull AutofarmsMenu observer, @NotNull CropClick plugin, @NotNull CropPlayer player) {
            super(observer, new DashboardMenuState(plugin.getConfigManager().getDefaultConfig(), player));
            this.config = plugin.getConfigManager().getDefaultConfig();
            this.player = player;
        }


        public void toggleAutofarmState() {
            updateState(state.isAutofarmEnabled, (state) -> !state, DashboardMenuStateFlag.AUTOFARM_TOGGLE);
        }


        public void toggleLinkState() {
            updateState(state.isLinkModeEnabled, (state) -> !state, DashboardMenuStateFlag.LINK_MODE);
        }


        @Override
        protected <P> DashboardMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == DashboardMenuStateFlag.LINK_MODE) {
                state.setLinkModeEnabled(infer(partial));
                player.getAutofarmFeatures().setIsLinkModeEnabled(infer(partial));
            }

            if (flag == DashboardMenuStateFlag.AUTOFARM_TOGGLE) {
                state.setAutofarmEnabled(infer(partial));
                config.set(ConfigurationKey.AUTOFARMS_ENABLED, (boolean) partial);
            }
            return state;
        }

    }

    @Getter
    @Setter
    public static final class DashboardMenuState extends PaginatedMenuState {

        private boolean isLinkModeEnabled;

        private boolean isAutofarmEnabled;


        private DashboardMenuState(@NotNull DefaultConfig config, @NotNull CropPlayer player) {
            this.isLinkModeEnabled = player.getAutofarmFeatures().isLinkModeEnabled();
            this.isAutofarmEnabled = config.getBoolean(ConfigurationKey.AUTOFARMS_ENABLED);
        }

    }

    public static final class DashboardMenuStateFlag {

        public final static int LINK_MODE = 0x00000001;

        public final static int AUTOFARM_TOGGLE = 0x00000002;

    }

}
