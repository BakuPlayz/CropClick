package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.menu.PaginatedMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CropsMenu extends PaginatedMenu {

    private final List<Crop> crops;

    public CropsMenu(@NotNull Player player, @NotNull CropClick plugin) {
        super(player, plugin, LanguageAPI.Menu.CROPS_TITLE);
        this.crops = plugin.getCropManager().getCrops();
        this.menuItems = getMenuItems();
    }

    @Override
    public void setMenuItems() {
        setPaginatedItems();
        setPageItems();
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (clicked.equals(getPreviousPageItem())) {
            if (page > 0) page -= 1;
            updateMenu();
        }

        if (clicked.equals(getNextPageItem())) {
            if (isIndexAboveBounds()) page += 1;
            updateMenu();
        }

        for (int i = 0; i < menuItems.size(); i++) {
            if (clicked.equals(menuItems.get(i))) {
                Crop crop = crops.get(i);
                // Open crop menu.
            }
        }
    }

    private @NotNull ItemStack getMenuItem(@NotNull Crop crop) {
        return new ItemUtil(crop.getClickableType())
                .setName(crop.getName()) //languageAPI
                .setLore("") //LanugageAPI
                .toItemStack();
    }

    private @NotNull List<ItemStack> getMenuItems() {
        return crops.stream().map(this::getMenuItem).collect(Collectors.toList());
    }

}
