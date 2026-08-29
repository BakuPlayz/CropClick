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
package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.Container;
import com.github.bakuplayz.cropclick.common.Locations;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.states.LinkMenuStateBuilder;
import com.github.bakuplayz.cropclick.menus.abstracts.states.LinkMenuStateBuilder.LinkContext;
import com.github.bakuplayz.cropclick.menus.abstracts.states.LinkMenuStateBuilder.LinkMenuState;
import com.github.bakuplayz.cropclick.menus.abstracts.states.LinkMenuStateBuilder.LinkMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.abstracts.states.LinkMenuStateBuilder.LinkMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.previews.PreviewContainerMenu;
import com.github.bakuplayz.cropclick.menus.previews.PreviewDispenserMenu;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.permissions.PermissionKey;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.common.ViewState;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.menu.items.state.StateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Abstract Link menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractLinkMenu extends AbstractStateMenu<LinkMenuState, LinkMenuStateHandler> {

    private final CropClick plugin;

    private final Block block;

    private final Autofarm autofarm;

    private final boolean showBackButton;


    public AbstractLinkMenu(
            @NotNull CropClick plugin,
            @NotNull String title,
            @Nullable Autofarm autofarm,
            @NotNull Block block,
            boolean showBackButton
    ) {
        super(title);
        this.block = block;
        this.plugin = plugin;
        this.autofarm = autofarm;
        this.showBackButton = showBackButton;
    }


    protected abstract LinkContext getContext();


    @NotNull
    @Override
    public final LinkMenuStateHandler createStateHandler(@NotNull Player player) {
        return LinkMenuStateBuilder.createStateHandler(this, plugin, autofarm, block, player, getContext());
    }


    @Override
    public void setItems() {
        boolean isUnlinked = stateHandler.getState().isUnlinked();
        boolean isUnclaimed = stateHandler.getState().isUnclaimed();

        setItemIf(!isUnclaimed && !isUnlinked, 13, new ToggleItem(), LinkMenuStateFlag.ENABLED_STATE);
        setItemIf(!isUnclaimed, isUnlinked ? 20 : 29, new CropItem(), LinkMenuStateFlag.CROP_SELECTED_STATE);
        setItemIf(!isUnclaimed, isUnlinked ? 22 : 31, new DispenserItem(), LinkMenuStateFlag.DISPENSER_SELECTED_STATE);
        setItemIf(!isUnclaimed, isUnlinked ? 24 : 33, new ContainerItem(), LinkMenuStateFlag.CONTAINER_SELECTED_STATE);
        setItemIf(isUnclaimed, 31, new ClaimItem());
        setItemIf(showBackButton, 49, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @Override
    public boolean isFramePosition(int position) {
        boolean isLeft = position % 9 == 0;
        boolean isRight = position % 9 == 8;
        boolean isTop = (position / 9.0d) <= 1.0d;
        return isLeft || isRight || isTop;
    }


    @NotNull
    @Override
    public Item getFrameItem(int position) {
        return new GlassItem();
    }


    @NotNull
    @Unmodifiable
    private List<String> getUnlinkedLore() {
        return LINK_FORMAT_STATE.builder(plugin)
                       .replace("%state%", LINK_STATES_UNLINKED.get(plugin))
                       .buildAsList();
    }


    @NotNull
    private List<String> getSelectedLore(@NotNull Location location) {
        List<String> selectedPart = new ArrayList<>(getBaseLore(location));
        selectedPart.add("");
        selectedPart.addAll(
                LINK_FORMAT_STATE.builder(plugin)
                        .replace("%state%", LINK_STATES_SELECTED.get(plugin))
                        .buildAsList()
        );
        return selectedPart;
    }


    @NotNull
    private List<String> getBaseLore(@NotNull Location location) {
        return Arrays.asList(
                LINK_FORMAT_X.builder(plugin).replace("%x%", location.getBlockX()).build(),
                LINK_FORMAT_Y.builder(plugin).replace("%y%", location.getBlockY()).build(),
                LINK_FORMAT_Z.builder(plugin).replace("%z%", location.getBlockZ()).build()
        );
    }


    private final class CropItem extends ClickableStateItem<LinkMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> updateItem(getState()));
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            updateItem(state);
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> {
                if (getState().isUnlinked() && getContext() == LinkContext.CROP) {
                    stateHandler.toggleCropSelect();
                }
            };
        }


        private void updateItem(@NotNull LinkMenuState state) {
            setLore(getLore(state));
            setName(LINK_CROP_NAME.get(plugin));
            setMaterial(getAdaptiveMaterial(state));
            setMaterial(state.isUnlinked(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(state.isCropSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
        }


        @NotNull
        private XMaterial getAdaptiveMaterial(@NotNull LinkMenuState state) {
            Crop crop = plugin.getCropManager().getFinder().findByLocation(state.getCropLocation());
            return crop == null ? XMaterial.WHEAT : crop.getMenuType();
        }


        @NotNull
        private List<String> getLore(@NotNull LinkMenuState state) {
            Location location = state.getCropLocation();

            if (location == null) {
                return getUnlinkedLore();
            }

            return state.isCropSelected() ? getSelectedLore(location) : getLinkedLore(location);
        }


        @NotNull
        private List<String> getLinkedLore(@NotNull Location location) {
            return getBaseLore(location);
        }

    }

    private final class ContainerItem extends ClickableStateItem<LinkMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> updateItem(getState()));
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            updateItem(state);
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> {
                if (getState().isUnlinked() && getContext() == LinkContext.CONTAINER) {
                    stateHandler.toggleContainerSelect();
                    return;
                }
                if (autofarm == null) return;

                Container container = Container.fromBlock(
                        getState().getContainerLocation().getBlock()
                );

                // We can never get to a state where the container we find
                // here is null, so we just tell the compiler that too.
                assert container != null;

                new PreviewContainerMenu(
                        plugin,
                        autofarm,
                        container.getInventory()
                ).open(player);
            };
        }


        private void updateItem(@NotNull LinkMenuState state) {
            setLore(getLore(state));
            setName(LINK_CONTAINER_NAME.get(plugin));
            setMaterial(getAdaptiveMaterial(state));
            setMaterial(state.isUnlinked(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(state.isContainerSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
        }


        @NotNull
        private XMaterial getAdaptiveMaterial(@NotNull LinkMenuState state) {
            return XMaterial.matchXMaterial(state.getContainerLocation() == null ? Material.CHEST : state.getContainerLocation().getBlock().getType());
        }


        @NotNull
        private List<String> getLore(@NotNull LinkMenuState state) {
            Location location = state.getContainerLocation();

            if (location == null) {
                return getUnlinkedLore();
            }

            if (location instanceof DoublyLocation) {
                DoublyLocation doubly = Locations.findDoubly(location);
                location = doubly == null ? location : doubly;
            }

            return state.isContainerSelected() ? getSelectedLore(location) : getLinkedLore(location);
        }


        @NotNull
        private List<String> getLinkedLore(@NotNull Location location) {
            return LINK_CONTAINER_TIPS.builder(plugin).append(getBaseLore(location)).buildAsList();
        }

    }

    private final class DispenserItem extends ClickableStateItem<LinkMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> updateItem(getState()));
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            updateItem(state);
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> {
                if (getState().isUnlinked() && getContext() == LinkContext.DISPENSER) {
                    stateHandler.toggleDispenserSelect();
                    return;
                }
                if (autofarm == null) return;

                new PreviewDispenserMenu(
                        plugin,
                        autofarm,
                        ((Dispenser) getState().getDispenserLocation().getBlock().getState()).getInventory()
                ).open(player);
            };
        }


        private void updateItem(@NotNull LinkMenuState state) {
            setLore(getLore(state));
            setMaterial(XMaterial.DISPENSER);
            setName(LINK_DISPENSER_NAME.get(plugin));
            setMaterial(state.isUnlinked(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(state.isDispenserSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
        }


        @NotNull
        private List<String> getLore(@NotNull LinkMenuState state) {
            Location location = state.getDispenserLocation();

            if (location == null) {
                return getUnlinkedLore();
            }

            return state.isDispenserSelected() ? getSelectedLore(location) : getLinkedLore(location);
        }


        @NotNull
        private List<String> getLinkedLore(@NotNull Location location) {
            return LINK_DISPENSER_TIPS.builder(plugin).append(getBaseLore(location)).buildAsList();
        }

    }

    private final class ClaimItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                CropPlayer player = CropPlayer.fromPlayer(viewers.get(0));

                setViewState(player.getPermissions().has(PermissionKey.AUTOFARM_CLAIM) ? ViewState.VISIBLE : ViewState.INVISIBLE);
                setLore(LINK_CLAIM_STATUS.builder(plugin).buildAsList());
                setMaterial(XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
                setName(LINK_CLAIM_NAME.get(plugin));
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> {
                stateHandler.claimAutofarm();
                forceRerender();
            };
        }

    }

    private final class ToggleItem extends ClickableStateItem<LinkMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                updateItem(getState());
                setViewState();
            });
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            updateItem(state);
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.toggleAutofarm();
        }


        private void setViewState() {
            CropPlayer player = CropPlayer.fromPlayer(viewers.get(0));
            boolean toggleOthers = player.getPermissions().has(PermissionKey.AUTOFARM_TOGGLE_OTHERS);
            boolean toggleSelf = player.getPermissions().has(PermissionKey.AUTOFARM_TOGGLE);

            // Cannot be null since item only shown iff autofarm is
            // not null.
            assert autofarm != null;

            if (player.getPlayerUUID() == autofarm.getOwnerId()) {
                setViewState(toggleSelf ? ViewState.VISIBLE : ViewState.DISABLED);
            } else {
                setViewState(toggleOthers ? ViewState.VISIBLE : ViewState.DISABLED);
            }
        }


        private void updateItem(@NotNull LinkMenuState state) {
            setLore(getLore(state));
            setName(LINK_TOGGLE_NAME.get(plugin));
            setMaterial(state.isEnabled() ? getMaterial() : XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(@NotNull LinkMenuState state) {
            return LINK_TOGGLE_STATUS.builder(plugin)
                           .replace("%status%", Messages.getStatusMessage(plugin, state.isEnabled()))
                           .buildAsList();
        }


        private XMaterial getMaterial() {
            return XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE;
        }

    }

    private final class GlassItem extends StateItem<LinkMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                updateItem(getState());
                setFlags(LinkMenuStateFlag.CLICKED_SELECTED);
            });
        }


        @Override
        public void update(@NotNull LinkMenuState state, int flag) {
            updateItem(state);
        }


        private void updateItem(@NotNull LinkMenuState state) {
            setMaterial(XMaterial.GRAY_STAINED_GLASS_PANE);
            setMaterial(!state.isUnlinked(), XMaterial.YELLOW_STAINED_GLASS_PANE);
            setMaterial(state.isUnclaimed(), XMaterial.WHITE_STAINED_GLASS_PANE);
            setMaterial(state.isClickedSelected(), XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE);
            setName(LINK_GLASS_ITEM_NAME_LINKED.get(plugin));
            setName(state.isUnlinked(), LINK_GLASS_ITEM_NAME_UNLINKED.get(plugin));
            setName(state.isUnclaimed(), LINK_GLASS_ITEM_NAME_UNCLAIMED.get(plugin));
            setName(state.isClickedSelected(), LINK_GLASS_ITEM_NAME_SELECTED.get(plugin));
        }

    }

}