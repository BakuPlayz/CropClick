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
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;


/**
 * A class representing the Help menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class HelpMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, Subcommand> {

    public HelpMenu(@NotNull CropClick plugin, boolean showBackButton) {
        super(HELP_TITLE.getTitle(plugin), plugin, showBackButton);
    }


    @Override
    public List<Subcommand> getPaginationItems() {
        return plugin.getCommandManager().getCommands();
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull Subcommand command, int position) {
        return new HelpItem(command);
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler(@NotNull Player player) {
        return new BasicPaginatedStateHandler(this);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull Subcommand command, int position) {
        return ((item, player) -> {
            player.closeInventory();
            command.perform(player);
        });
    }


    @AllArgsConstructor
    private final class HelpItem extends ClickableItem {

        private final Subcommand command;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.BOOK);
                setName(HELP_ITEM_NAME.get(plugin, command.getName()));
                setLore(HELP_ITEM_DESCRIPTION.get(plugin, command.getDescription()),
                        HELP_ITEM_PERMISSION.get(plugin, command.getPermission()),
                        HELP_ITEM_USAGE.get(plugin, command.getUsage())
                );
            });
        }

    }

}
