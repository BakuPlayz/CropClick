package com.github.bakuplayz.cropclick.menu.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class PaginatedMenu extends Menu {

    protected final int MAX_ITEMS_PER_PAGE = 21;


    protected int itemIndex;
    protected int page;

    protected List<ItemStack> menuItems;


    public PaginatedMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull LanguageAPI.Menu menuTitle) {
        super(plugin, player, menuTitle);
        this.itemIndex = 0;
        this.page = 0;
    }


    /**
     * Set the back items in the inventory.
     */
    protected final void setBackItems() {
        inventory.setItem(46, getBackItem());
        inventory.setItem(52, getBackItem());
    }


    /**
     * If the page is greater than 0, set the previous page item. Set the current page item. If the menu items size is
     * greater than the item index, set the next page item.
     */
    protected final void setPageItems() {
        if (page > 0) {
            inventory.setItem(48, getPreviousPageItem());
        }

        inventory.setItem(49, getCurrentPageItem());

        if (menuItems.size() > itemIndex) {
            inventory.setItem(50, getNextPageItem());
        }
    }


    /**
     * If the item is not in the top row, the left column, or the right column, then add it to the inventory.
     */
    protected final void setPaginatedItems() {
        int index = 0;
        for (int i = 0; i < 35; ++i) {
            boolean isLeft = i % 9 == 0;
            boolean isRight = i % 9 == 8;
            boolean isTop = (i / 9.0) <= 1.0;
            boolean isForbidden = isLeft || isRight || isTop;
            if (isForbidden) continue;

            updateIndex(index++);
            if (isIndexOutOfBounds()) break;
            inventory.setItem(i, menuItems.get(itemIndex));
        }
    }


    /**
     * If the player clicks the previous page item, and the page is greater than 0, then subtract 1 from the page and
     * update the menu. If the player clicks the next page item, and the page is not above the bounds, then add 1 to the
     * page and update the menu.
     *
     * @param clicked The item that was clicked.
     */
    protected final void handlePagination(@NotNull ItemStack clicked) {
        if (clicked.equals(getPreviousPageItem())) {
            if (page > 0) page -= 1;
            refresh();
        }

        if (clicked.equals(getNextPageItem())) {
            if (!isIndexOutOfBounds()) page += 1;
            refresh();
        }
    }


    /**
     * Update the item index to the index of the item in the current page.
     *
     * @param i the index of the item in the current page.
     */
    private void updateIndex(int i) {
        this.itemIndex = MAX_ITEMS_PER_PAGE * page + i;
    }


    /**
     * If the item index is greater than or equal to the number of items in the menu, then the index is out of bounds.
     *
     * @return The method is returning a boolean value.
     */
    private boolean isIndexOutOfBounds() {
        return itemIndex >= menuItems.size();
    }


    /**
     * It returns an ItemStack with the material of an arrow, the name of the previous page item, and the lore of the
     * previous page item.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getPreviousPageItem() {
        return new ItemBuilder(Material.ARROW)
                .setName(plugin, LanguageAPI.Menu.GENERAL_PREVIOUS_PAGE_ITEM_NAME)
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the current page.
     *
     * @return The current page item.
     */
    private @NotNull ItemStack getCurrentPageItem() {
        return new ItemBuilder(Material.BOOK)
                .setName(LanguageAPI.Menu.GENERAL_CURRENT_PAGE_ITEM_NAME.get(plugin, page + 1))
                .toItemStack();
    }


    /**
     * It returns an ItemStack with the material of an arrow, the name of the next page item, and the lore of the next page
     * item.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getNextPageItem() {
        return new ItemBuilder(Material.ARROW)
                .setName(plugin, LanguageAPI.Menu.GENERAL_NEXT_PAGE_ITEM_NAME)
                .toItemStack();
    }


    /**
     * Return a list of items that will be displayed in the menu.
     *
     * @return A list of items.
     */
    protected abstract List<ItemStack> getMenuItems();

}