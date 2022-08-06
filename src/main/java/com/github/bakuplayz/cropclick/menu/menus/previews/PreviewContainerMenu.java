package com.github.bakuplayz.cropclick.menu.menus.previews;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.PreviewMenu;
import lombok.AccessLevel;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.DoubleChestInventory;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PreviewContainerMenu extends PreviewMenu {

    private @Setter(AccessLevel.PRIVATE) Inventory containerInventory;


    public PreviewContainerMenu(@NotNull CropClick plugin,
                                @NotNull Player player,
                                @NotNull String shortFarmerID,
                                @NotNull Inventory inventory) {
        super(plugin, player, LanguageAPI.Menu.CONTAINER_PREVIEW_TITLE, shortFarmerID);
        setInventoryType(inventory instanceof DoubleChestInventory ? null : inventory.getType());
        setContainerInventory(inventory);
    }


    @Override
    public void setMenuItems() {
        inventory.setContents(containerInventory.getContents());
    }

}