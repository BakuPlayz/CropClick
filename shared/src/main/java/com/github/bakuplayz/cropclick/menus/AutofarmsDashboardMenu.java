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
package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.menus.states.DashboardStateBuilder;
import com.github.bakuplayz.cropclick.permissions.PermissionKey;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;
import static com.github.bakuplayz.cropclick.menus.states.DashboardStateBuilder.*;

public class AutofarmsDashboardMenu extends AbstractStateMenu<DashboardMenuState, DashboardMenuStateHandler> {

    private final CropClick plugin;

    private final boolean showBackButton;


    public AutofarmsDashboardMenu(@NotNull CropClick plugin, boolean showBackButton) {
        super(AUTOFARMS_DASHBOARD_TITLE.getTitle(plugin));
        this.showBackButton = showBackButton;
        this.plugin = plugin;
    }


    @Override
    public DashboardMenuStateHandler createStateHandler(@NotNull Player player) {
        return DashboardStateBuilder.createStateHandler(this, plugin, player);
    }


    @Override
    public void setItems() {
        CropPlayer player = CropPlayer.fromPlayer(getViewers().get(0));
        boolean canToggleAll = player.getPermissions().has(PermissionKey.AUTOFARM_TOGGLE_ALL);

        setItemIf(canToggleAll, 20, new AutofarmsToggleItem(), DashboardMenuStateFlag.AUTOFARM_TOGGLE);
        setItem(canToggleAll ? 22 : 21, new ManageAutofarmsItem());
        setItem(canToggleAll ? 24 : 23, new LinkToggleItem(), DashboardMenuStateFlag.LINK_MODE);
        setItemIf(showBackButton, 49, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    private final class AutofarmsToggleItem extends ClickableStateItem<DashboardMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> updateItem(getState()));
        }


        @Override
        public void update(@NotNull DashboardMenuState state, int flag) {
            updateItem(state);
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> stateHandler.toggleAutofarmState();
        }


        private void updateItem(@NotNull DashboardMenuState state) {
            String status = AUTOFARMS_DASHBOARD_TOGGLE_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", Messages.getStatusMessage(plugin, state.isAutofarmEnabled()))
                                    .build();
            setName(AUTOFARMS_DASHBOARD_TOGGLE_ITEM_NAME.get(plugin));
            setLore(AUTOFARMS_DASHBOARD_TOGGLE_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            setMaterial(state.isAutofarmEnabled() ? XMaterial.DISPENSER : XMaterial.GRAY_STAINED_GLASS_PANE);
        }

    }

    private final class ManageAutofarmsItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return plugin.getAutofarmManager().getAmountOfFarms().thenAccept(farms -> {
                String status = AUTOFARMS_DASHBOARD_MANAGE_AUTOFARMS_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", farms)
                                        .build();
                setMaterial(XMaterial.BOOK);
                setName(AUTOFARMS_DASHBOARD_MANAGE_AUTOFARMS_ITEM_NAME.get(plugin));
                setLore(AUTOFARMS_DASHBOARD_MANAGE_AUTOFARMS_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> new ManageAutofarmsMenu(plugin, true).open(player);
        }

    }

    private final class LinkToggleItem extends ClickableStateItem<DashboardMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> updateItem(getState()));
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> stateHandler.toggleLinkState();
        }


        @Override
        public void update(@NotNull DashboardMenuState state, int flag) {
            updateItem(state);
        }


        private void updateItem(@NotNull DashboardMenuState state) {
            String status = AUTOFARMS_DASHBOARD_LINK_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", Messages.getStatusMessage(plugin, state.isLinkModeEnabled()))
                                    .build();
            setName(AUTOFARMS_DASHBOARD_LINK_ITEM_NAME.get(plugin));
            setLore(AUTOFARMS_DASHBOARD_LINK_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            setMaterial(state.isLinkModeEnabled() ? XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE : XMaterial.GRAY_STAINED_GLASS_PANE);
        }

    }

}
