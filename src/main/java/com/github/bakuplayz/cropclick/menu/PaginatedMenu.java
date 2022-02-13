package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int itemIndex = 0;
    protected final int MAX_ITEMS_PER_PAGE = 45;

    protected List<ItemStack> menuItems;

    public PaginatedMenu(final @NotNull Player player,
                         final @NotNull CropClick plugin,
                         final @NotNull LanguageAPI.Menu menuTitle) {
        super(player, plugin, menuTitle);
    }

    protected final void setPageItems() {
        if (page != 0) {
            inventory.setItem(48, getPreviousPageItem());
        }

        inventory.setItem(49, getCurrentPageItem());

        if (menuItems.size() > itemIndex) {
            inventory.setItem(50, getNextPageItem());
        }
    }

    protected final void setPaginatedItems() {
        for (int i = 0; i < MAX_ITEMS_PER_PAGE; i++) {
            updateIndex(i);
            if (isIndexOutOfBounds()) break;
            inventory.addItem(menuItems.get(itemIndex));
        }
    }

    protected final void updateIndex(final int i) {
        this.itemIndex = MAX_ITEMS_PER_PAGE * page + i;
    }

    protected final boolean isIndexOutOfBounds() {
        return itemIndex >= menuItems.size();
    }

    protected final boolean isIndexAboveBounds() {
        return itemIndex > menuItems.size();
    }

    protected final @NotNull ItemStack getPreviousPageItem() {
        return new ItemUtil(Material.ARROW)
                .setName(LanguageAPI.Menu.PREVIOUS_PAGE_ITEM_NAME.get(plugin))
                .toItemStack();
    }

    protected final @NotNull ItemStack getCurrentPageItem() {
        return new ItemUtil(Material.BOOK)
                .setName(LanguageAPI.Menu.CURRENT_PAGE_ITEM_NAME.get(plugin, page + 1))
                .toItemStack();
    }

    protected final @NotNull ItemStack getNextPageItem() {
        return new ItemUtil(Material.ARROW)
                .setName(LanguageAPI.Menu.NEXT_PAGE_ITEM_NAME.get(plugin))
                .toItemStack();
    }
}
