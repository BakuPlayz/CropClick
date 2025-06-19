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
package com.github.bakuplayz.cropclick.menus.settings.states;

import com.github.bakuplayz.cropclick.menus.settings.worlds.WorldMenu;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link WorldMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class WorldStateBuilder {

    @NotNull
    public static WorldMenuStateHandler createStateHandler(@NotNull WorldMenu menu, @NotNull FarmWorld world) {
        return new WorldMenuStateHandler(menu, world);
    }


    public final static class WorldMenuStateHandler extends MenuStateHandler<WorldMenuState, WorldMenu> {

        private final FarmWorld world;


        private WorldMenuStateHandler(@NotNull WorldMenu observer, @NotNull FarmWorld world) {
            super(observer, new WorldMenuState(world));
            this.world = world;
        }


        public void toggleAutofarmsState() {
            updateState(state.isAutofarmsAllowed, (state) -> !state, WorldMenuStateFlag.AUTOFARMS);
        }


        public void toggleBanishedState() {
            updateState(state.isBanished, (state) -> !state, WorldMenuStateFlag.BANISHED);
        }


        public void togglePlayersState() {
            updateState(state.isPlayersAllowed, (state) -> !state, WorldMenuStateFlag.PLAYERS);
        }


        @Override
        protected <P> WorldMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == WorldMenuStateFlag.AUTOFARMS) {
                state.setAutofarmsAllowed(infer(partial));
                world.allowsAutofarms(infer(partial));
            }

            if (flag == WorldMenuStateFlag.BANISHED) {
                state.setBanished(infer(partial));
                world.isBanished(infer(partial));
            }

            if (flag == WorldMenuStateFlag.PLAYERS) {
                state.setPlayersAllowed(infer(partial));
                world.allowsPlayers(infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class WorldMenuState implements MenuState {

        private boolean isAutofarmsAllowed;

        private boolean isPlayersAllowed;

        private boolean isBanished;


        private WorldMenuState(@NotNull FarmWorld world) {
            this.isAutofarmsAllowed = world.allowsAutofarms();
            this.isPlayersAllowed = world.allowsPlayers();
            this.isBanished = world.isBanished();
        }

    }

    public static final class WorldMenuStateFlag {

        public final static int AUTOFARMS = 0x01;

        public final static int PLAYERS = 0x02;

        public final static int BANISHED = 0x03;

    }

}
