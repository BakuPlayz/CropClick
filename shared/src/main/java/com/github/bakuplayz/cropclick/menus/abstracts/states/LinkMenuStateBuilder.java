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
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmFactory;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractLinkMenu;
import com.github.bakuplayz.spigotspin.menu.common.ViewerMap;
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

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.LINK_ACTION_FAILURE;

/**
 * A class for creating and handling the {@link AbstractLinkMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public class LinkMenuStateBuilder {

    @NotNull
    public static LinkMenuStateHandler createStateHandler(
            @NotNull AbstractLinkMenu menu,
            @NotNull CropClick plugin,
            @Nullable Autofarm autofarm,
            @NotNull Block block,
            @NotNull ViewerMap viewers,
            @NotNull LinkContext context
    ) {
        return new LinkMenuStateHandler(menu, plugin, autofarm, block, viewers, context);
    }


    public enum LinkContext {

        COMMAND,

        DISPENSER,

        CONTAINER,

        CROP

    }

    public static class LinkMenuStateHandler extends MenuStateHandler<LinkMenuState, AbstractLinkMenu> {


        private final Block block;

        private final Autofarm autofarm;

        private final ViewerMap viewers;

        private final CropClick plugin;


        private LinkMenuStateHandler(
                @NotNull AbstractLinkMenu observer,
                @NotNull CropClick plugin,
                @Nullable Autofarm autofarm,
                @NotNull Block block,
                @NotNull ViewerMap viewers,
                @NotNull LinkContext context
        ) {
            super(observer, new LinkMenuState(autofarm, block, context, plugin.getAutofarmManager(), viewers));
            this.autofarm = autofarm;
            this.viewers = viewers;
            this.plugin = plugin;
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

            CropPlayer player = CropPlayer.of(viewers.get(0));
            player.getAutofarmFunctionality().deselectComponents();

            viewers.get(0).closeInventory();

            Autofarm autofarm = AutofarmFactory.createDefault(viewers.get(0), crop, container, dispenser);

            if (!autofarm.isComponentsPresent(plugin.getAutofarmManager())) {
                LINK_ACTION_FAILURE.send(plugin, viewers.get(0));
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
                autofarm.setOwnerId(viewers.get(0).getUniqueId());
            }

            if (flag == LinkMenuStateFlag.UNLINKED_STATE) {
                state.setUnlinked(infer(partial));
            }

            if (flag == LinkMenuStateFlag.CROP_SELECTED_STATE) {
                CropPlayer player = CropPlayer.of(viewers.get(0));

                if (state.isCropSelected) {
                    state.setCropLocation(null);
                    player.getAutofarmFunctionality().deselectCrop(block);
                } else {
                    state.setCropLocation(block.getLocation());
                    player.getAutofarmFunctionality().selectCrop(block);
                }

                state.setClickedSelected(infer(partial));
                state.setCropSelected(infer(partial));
            }

            if (flag == LinkMenuStateFlag.CONTAINER_SELECTED_STATE) {
                CropPlayer player = CropPlayer.of(viewers.get(0));

                if (state.isContainerSelected) {
                    state.setContainerLocation(null);
                    player.getAutofarmFunctionality().deselectContainer(block);
                } else {
                    state.setContainerLocation(block.getLocation());
                    player.getAutofarmFunctionality().selectContainer(block);
                }

                state.setClickedSelected(infer(partial));
                state.setContainerSelected(infer(partial));
            }

            if (flag == LinkMenuStateFlag.DISPENSER_SELECTED_STATE) {
                CropPlayer player = CropPlayer.of(viewers.get(0));

                if (state.isDispenserSelected) {
                    state.setDispenserLocation(null);
                    player.getAutofarmFunctionality().deselectDispenser(block);
                } else {
                    state.setDispenserLocation(block.getLocation());
                    player.getAutofarmFunctionality().selectDispenser(block);
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
                @NotNull LinkContext context,
                @NotNull AutofarmManager manager,
                @NotNull ViewerMap viewers
        ) {
            this.isUnlinked = autofarm != null && autofarm.isLinked();
            this.isEnabled = autofarm != null && autofarm.isEnabled();
            this.cropLocation = getCropLocation(autofarm, manager, viewers.get(0));
            this.dispenserLocation = getDispenserLocation(autofarm, manager, viewers.get(0));
            this.containerLocation = getContainerLocation(autofarm, manager, viewers.get(0));
            this.isCropSelected = isSelectedAndNotLinked(autofarm, getCropLocation());
            this.isDispenserSelected = isSelectedAndNotLinked(autofarm, getDispenserLocation());
            this.isContainerSelected = isSelectedAndNotLinked(autofarm, getContainerLocation());
            this.isUnclaimed = autofarm != null && Autofarm.UNKNOWN_OWNER.equals(autofarm.getOwnerId());
            this.isClickedSelected = getClickedSelectedStatus(CropPlayer.of(viewers.get(0)), block, context);
        }


        private boolean isSelectedAndNotLinked(Autofarm autofarm, Location location) {
            return isUnlinked && autofarm == null && location != null;
        }


        private Location getDispenserLocation(@Nullable Autofarm autofarm, @NotNull AutofarmManager manager, @NotNull Player player) {
            return autofarm == null ? manager.getSelectedDispenser(player) : autofarm.getDispenserLocation();
        }


        private Location getContainerLocation(@Nullable Autofarm autofarm, @NotNull AutofarmManager manager, @NotNull Player player) {
            return autofarm == null ? manager.getSelectedContainer(player) : autofarm.getContainerLocation();
        }


        private Location getCropLocation(@Nullable Autofarm autofarm, @NotNull AutofarmManager manager, @NotNull Player player) {
            return autofarm == null ? manager.getSelectedCrop(player) : autofarm.getCropLocation();
        }


        private boolean getClickedSelectedStatus(@NotNull CropPlayer player, @NotNull Block block, @NotNull LinkContext context) {
            switch (context) {
                case CROP:
                    return player.getAutofarmFunctionality().isCropSelected(block);

                case CONTAINER:
                    return player.getAutofarmFunctionality().isContainerSelected(block);

                case DISPENSER:
                    return player.getAutofarmFunctionality().isDispenserSelected(block);
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
