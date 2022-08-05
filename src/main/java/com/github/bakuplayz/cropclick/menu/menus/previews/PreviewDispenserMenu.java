package com.github.bakuplayz.cropclick.menu.menus.previews;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.PreviewMenu;
import lombok.AccessLevel;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class PreviewDispenserMenu extends PreviewMenu {

    private @Setter(AccessLevel.PRIVATE) Inventory dispenserInventory;


    public PreviewDispenserMenu(@NotNull CropClick plugin,
                                @NotNull Player player,
                                @NotNull String shortFarmerID,
                                @NotNull Inventory inventory) {
        super(plugin, player, LanguageAPI.Menu.DISPENSER_PREVIEW_TITLE, shortFarmerID);
        setDispenserInventory(inventory);
        setInventoryType(inventory.getType());
    }


    @Override
    public void setMenuItems() {
        inventory.setContents(dispenserInventory.getContents());
    }

}