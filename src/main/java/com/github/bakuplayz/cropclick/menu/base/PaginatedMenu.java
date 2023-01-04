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
 * A class representing the base of a paginated menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class PaginatedMenu extends BaseMenu {

    /**
     * The maximum amount of items per paginated page.
     */
    public static final int MAX_ITEMS_PER_PAGE = 21;


    /**
     * The current page being displayed.
     */
    protected int page;

    /**
     * The current index where the pointer currently pointing at in the {@link #menuItems}.
     */
    protected int itemIndex;

    /**
     * A variable containing all the {@link ItemStack menu items}.
     */
    protected List<ItemStack> menuItems;


    public PaginatedMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull LanguageAPI.Menu menuTitle) {
        super(plugin, player, menuTitle);
        this.itemIndex = 0;
        this.page = 0;
    }


    /**
     * Sets the {@link ItemStack back items} to their designated places in the {@link #inventory}.
     */
    protected final void setBackItems() {
        inventory.setItem(46, getBackItem());
        inventory.setItem(52, getBackItem());
    }


    /**
     * Sets the {@link ItemStack page items} to their designated places in the {@link #inventory}.
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
     * Sets the {@link ItemStack paginated items} to their designated places in the {@link #inventory}.
     */
    protected final void setPaginatedItems() {
        int index = 0;
        for (int i = 0; i < 35; ++i) {
            boolean isLeft = i % 9 == 0;
            boolean isRight = i % 9 == 8;
            boolean isTop = (i / 9.0) <= 1.0;
            if (isLeft || isRight || isTop) {
                continue;
            }

            updateIndex(index++);
            if (isIndexOutOfBounds()) {
                break;
            }
            inventory.setItem(i, menuItems.get(itemIndex));
        }
    }


    /**
     * Handles all the clicks related to {@link #setPaginatedItems() the paginated items}.
     *
     * @param clicked the item that was clicked.
     */
    protected final void handlePagination(@NotNull ItemStack clicked) {
        if (clicked.equals(getPreviousPageItem())) {
            if (page > 0) page -= 1;
            refreshMenu();
        }

        if (clicked.equals(getNextPageItem())) {
            if (!isIndexOutOfBounds()) page += 1;
            refreshMenu();
        }
    }


    /**
     * Updates the pointer of the {@link #itemIndex}.
     *
     * @param currentIndex the current index of the pointer.
     */
    private void updateIndex(int currentIndex) {
        this.itemIndex = MAX_ITEMS_PER_PAGE * page + currentIndex;
    }


    /**
     * Checks whether the {@link #itemIndex current index pointer} is pointing out of bounds.
     *
     * @return true if it is, otherwise false.
     */
    private boolean isIndexOutOfBounds() {
        return itemIndex >= menuItems.size();
    }


    /**
     * Gets the previous page {@link ItemStack item}.
     *
     * @return the previous page item.
     */
    private @NotNull ItemStack getPreviousPageItem() {
        return new ItemBuilder(Material.ARROW)
                .setName(plugin, LanguageAPI.Menu.GENERAL_PREVIOUS_PAGE_ITEM_NAME)
                .toItemStack();
    }


    /**
     * Gets the current page {@link ItemStack item}.
     *
     * @return the current page item.
     */
    private @NotNull ItemStack getCurrentPageItem() {
        return new ItemBuilder(Material.BOOK)
                .setName(LanguageAPI.Menu.GENERAL_CURRENT_PAGE_ITEM_NAME.get(plugin, page + 1))
                .toItemStack();
    }


    /**
     * Gets the next page {@link ItemStack item}.
     *
     * @return the next page item.
     */
    private @NotNull ItemStack getNextPageItem() {
        return new ItemBuilder(Material.ARROW)
                .setName(plugin, LanguageAPI.Menu.GENERAL_NEXT_PAGE_ITEM_NAME)
                .toItemStack();
    }


    /**
     * Gets all the {@link #menuItems menu items}.
     *
     * @return all menu items.
     */
    protected abstract List<ItemStack> getMenuItems();

}