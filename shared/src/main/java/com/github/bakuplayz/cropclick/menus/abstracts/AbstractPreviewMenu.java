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

import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPlainMenu;
import com.github.bakuplayz.spigotspin.menu.common.CollectionUtils;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import lombok.AllArgsConstructor;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing the Abstract Preview menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractPreviewMenu extends AbstractPlainMenu {

    protected final Inventory previewInventory;


    public AbstractPreviewMenu(@NotNull String title, @NotNull Inventory previewInventory) {
        super(title);
        this.previewInventory = previewInventory;
    }


    @Override
    public void setItems() {
        Arrays.stream(previewInventory.getContents())
                .map(CollectionUtils.toIndexed())
                .forEach((indexed) -> setItem(indexed.getIndex(), new PreviewItem(indexed.getValue())));
    }


    @AllArgsConstructor
    private final static class PreviewItem extends Item {

        private final ItemStack item;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            // Don't do anything inside of this, we don't need to modify the item in any way so far.
            return CompletableFuture.completedFuture(null);
        }


        @NotNull
        @Override
        public ItemStack asItemStack() {
            return item;
        }

    }

}
