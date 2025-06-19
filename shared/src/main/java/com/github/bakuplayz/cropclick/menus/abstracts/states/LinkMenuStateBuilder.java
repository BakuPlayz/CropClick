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
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.Autofarms;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractLinkMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.LINK_ACTION_FAILURE;

/**
 * A class for creating and handling the {@link AbstractLinkMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class LinkMenuStateBuilder {

    @NotNull
    public static LinkMenuStateHandler createStateHandler(
            @NotNull AbstractLinkMenu menu,
            @NotNull CropClick plugin,
            @Nullable Autofarm autofarm,
            @NotNull Block block,
            @NotNull Player player,
            @NotNull LinkContext context
    ) {
        return new LinkMenuStateHandler(menu, plugin, autofarm, block, CropPlayer.fromPlayer(player), context);
    }


    public enum LinkContext {

        COMMAND,

        DISPENSER,

        CONTAINER,

        CROP

    }

    public final static class LinkMenuStateHandler extends MenuStateHandler<LinkMenuState, AbstractLinkMenu> {


        private final Block block;

        private final Autofarm autofarm;

        private final CropPlayer player;

        private final CropClick plugin;


        private LinkMenuStateHandler(
                @NotNull AbstractLinkMenu observer,
                @NotNull CropClick plugin,
                @Nullable Autofarm autofarm,
                @NotNull Block block,
                @NotNull CropPlayer player,
                @NotNull LinkContext context
        ) {
            super(observer, new LinkMenuState(autofarm, block, player, context));
            this.autofarm = autofarm;
            this.plugin = plugin;
            this.player = player;
            this.block = block;
        }


        public void toggleAutofarm() {
            updateState(state.isEnabled, (state) -> !state, LinkMenuStateFlag.ENABLED_STATE);
        }


        public void claimAutofarm() {
            updateState(state.isUnclaimed, (state) -> !state, LinkMenuStateFlag.UNCLAIMED_STATE);
        }


        public void toggleCropSelect() {
            updateState(state.isCropSelected, (state) -> !state, LinkMenuStateFlag.CROP_SELECTED_STATE);
            handleLink();
        }


        public void toggleContainerSelect() {
            updateState(state.isContainerSelected, (state) -> !state, LinkMenuStateFlag.CONTAINER_SELECTED_STATE);
            handleLink();
        }


        public void toggleDispenserSelect() {
            updateState(state.isDispenserSelected, (state) -> !state, LinkMenuStateFlag.DISPENSER_SELECTED_STATE);
            handleLink();
        }


        private void handleLink() {
            if (!state.isCropSelected()) return;
            if (!state.isContainerSelected()) return;
            if (!state.isDispenserSelected()) return;

            Location crop = state.getCropLocation();
            Location container = state.getContainerLocation();
            Location dispenser = state.getDispenserLocation();

            player.getAutofarmFeatures().deselectComponents();
            player.getOfflinePlayer().getPlayer().closeInventory();

            Autofarm autofarm = Autofarm.fromPlayer(player, crop, container, dispenser);

            if (!Autofarms.areComponents(plugin.getCropManager(), crop.getBlock(), container.getBlock(), dispenser.getBlock())) {
                LINK_ACTION_FAILURE.send(plugin, player);
                return;
            }

            Bukkit.getPluginManager().callEvent(
                    new PlayerLinkAutofarmEvent(player, autofarm)
            );
        }


        @Override
        protected <P> LinkMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == LinkMenuStateFlag.ENABLED_STATE) {
                state.setEnabled(infer(partial));
                autofarm.isEnabled(infer(partial));
            }

            if (flag == LinkMenuStateFlag.UNCLAIMED_STATE) {
                state.setUnclaimed(infer(partial));
                autofarm.setOwnerId(player.getPlayerUUID());
            }

            if (flag == LinkMenuStateFlag.UNLINKED_STATE) {
                state.setUnlinked(infer(partial));
            }

            if (flag == LinkMenuStateFlag.CROP_SELECTED_STATE) {
                if (state.isCropSelected) {
                    state.setCropLocation(null);
                    player.getAutofarmFeatures().deselectCrop(block);
                } else {
                    state.setCropLocation(block.getLocation());
                    player.getAutofarmFeatures().selectCrop(block);
                }

                state.setClickedSelected(infer(partial));
                state.setCropSelected(infer(partial));
            }

            if (flag == LinkMenuStateFlag.CONTAINER_SELECTED_STATE) {
                if (state.isContainerSelected) {
                    state.setContainerLocation(null);
                    player.getAutofarmFeatures().deselectContainer(block);
                } else {
                    state.setContainerLocation(block.getLocation());
                    player.getAutofarmFeatures().selectContainer(block);
                }

                state.setClickedSelected(infer(partial));
                state.setContainerSelected(infer(partial));
            }

            if (flag == LinkMenuStateFlag.DISPENSER_SELECTED_STATE) {
                if (state.isDispenserSelected) {
                    state.setDispenserLocation(null);
                    player.getAutofarmFeatures().deselectDispenser(block);
                } else {
                    state.setDispenserLocation(block.getLocation());
                    player.getAutofarmFeatures().selectDispenser(block);
                }

                state.setClickedSelected(infer(partial));
                state.setDispenserSelected(infer(partial));
            }

            return state;
        }

    }


    @Getter
    @Setter
    public static final class LinkMenuState implements MenuState {

        private Location cropLocation;

        private Location containerLocation;

        private Location dispenserLocation;

        private boolean isCropSelected;

        private boolean isContainerSelected;

        private boolean isDispenserSelected;

        private boolean isClickedSelected;

        private boolean isUnclaimed;

        private boolean isUnlinked;

        private boolean isEnabled;


        private LinkMenuState(
                @Nullable Autofarm autofarm,
                @NotNull Block block,
                @NotNull CropPlayer player,
                @NotNull LinkContext context
        ) {
            this.isUnlinked = autofarm == null;
            this.isEnabled = autofarm != null && autofarm.isEnabled();
            this.cropLocation = getCropLocation(autofarm, player);
            this.dispenserLocation = getDispenserLocation(autofarm, player);
            this.containerLocation = getContainerLocation(autofarm, player);
            this.isClickedSelected = getClickedSelectedStatus(player, block, context);
            this.isCropSelected = isSelectedAndNotLinked(getCropLocation());
            this.isDispenserSelected = isSelectedAndNotLinked(getDispenserLocation());
            this.isContainerSelected = isSelectedAndNotLinked(getContainerLocation());
            this.isUnclaimed = autofarm != null && Autofarm.UNKNOWN_OWNER.equals(autofarm.getOwnerId());
        }


        private boolean isSelectedAndNotLinked(Location location) {
            return isUnlinked && location != null;
        }


        private Location getDispenserLocation(@Nullable Autofarm autofarm, @NotNull CropPlayer player) {
            return autofarm == null ? player.getAutofarmFeatures().getSelectedDispenser() : autofarm.getDispenserLocation();
        }


        private Location getContainerLocation(@Nullable Autofarm autofarm, @NotNull CropPlayer player) {
            return autofarm == null ? player.getAutofarmFeatures().getSelectedContainer() : autofarm.getContainerLocation();
        }


        private Location getCropLocation(@Nullable Autofarm autofarm, @NotNull CropPlayer player) {
            return autofarm == null ? player.getAutofarmFeatures().getSelectedCrop() : autofarm.getCropLocation();
        }


        private boolean getClickedSelectedStatus(@NotNull CropPlayer player, @NotNull Block block, @NotNull LinkContext context) {
            switch (context) {
                case CROP:
                    return player.getAutofarmFeatures().isCropSelected(block);

                case CONTAINER:
                    return player.getAutofarmFeatures().isContainerSelected(block);

                case DISPENSER:
                    return player.getAutofarmFeatures().isDispenserSelected(block);
            }

            return false;
        }

    }

    public static final class LinkMenuStateFlag {

        public final static int UNCLAIMED_STATE = 0x1;

        public final static int UNLINKED_STATE = 0x2;

        public final static int ENABLED_STATE = 0x3;

        public final static int CROP_SELECTED_STATE = 0x4;

        public final static int CONTAINER_SELECTED_STATE = 0x5;

        public final static int DISPENSER_SELECTED_STATE = 0x6;

        public final static List<Integer> CLICKED_SELECTED = Arrays.asList(UNCLAIMED_STATE, UNLINKED_STATE, CROP_SELECTED_STATE, CONTAINER_SELECTED_STATE, DISPENSER_SELECTED_STATE);

    }

}
