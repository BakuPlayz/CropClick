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
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.cropclick.menus.links.DispenserLinkMenu;
import com.github.bakuplayz.cropclick.permissions.PermissionKey;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Autofarms menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ManageAutofarmsMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, Autofarm> {


    public ManageAutofarmsMenu(@NotNull CropClick plugin, boolean shouldShowBack) {
        super(AUTOFARMS_TITLE.getTitle(plugin), plugin, shouldShowBack);
    }


    @NotNull
    @Override
    public CompletableFuture<List<Autofarm>> getFuturePaginationItems() {
        CompletableFuture<List<Autofarm>> future = new CompletableFuture<>();
        CropPlayer player = CropPlayer.fromPlayer(viewers.get(0));

        plugin.getAutofarmManager().getAutofarms().thenAccept(autofarms -> future.complete(
                autofarms.stream()
                        .filter(autofarm -> {
                            boolean canClaim = player.getPermissions().has(PermissionKey.AUTOFARM_CLAIM);
                            boolean canUnlinkOthers = player.getPermissions().canUnlink(autofarm);
                            return canUnlinkOthers || canClaim;
                        })
                        .collect(Collectors.toList())
        ));

        return future;
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull Autofarm autofarm, int position) {
        return new AutofarmItem(autofarm);
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler(@NotNull Player player) {
        return new BasicPaginatedStateHandler(this);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull Autofarm autofarm, int position) {
        return (item, player) -> new DispenserLinkMenu(
                plugin,
                autofarm,
                autofarm.getDispenserLocation().getBlock(),
                true
        ).open(player);
    }


    @AllArgsConstructor
    private final class AutofarmItem extends ClickableItem {

        private final Autofarm autofarm;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = Messages.getStatusMessage(plugin, autofarm.isEnabled());
                OfflinePlayer player = Bukkit.getOfflinePlayer(autofarm.getOwnerId());

                setMaterial(XMaterial.DISPENSER);
                setLore(AUTOFARMS_ITEM_OWNER.get(plugin, getName(player)));
                setName(AUTOFARMS_ITEM_NAME.get(plugin, autofarm.getShortenedId(), status));
            });
        }


        private String getName(@NotNull OfflinePlayer player) {
            if (player.getUniqueId().equals(Autofarm.UNKNOWN_OWNER)) {
                return AUTOFARMS_ITEM_OWNER_UNCLAIMED.get(plugin);
            }

            return player.getName();
        }

    }

}
