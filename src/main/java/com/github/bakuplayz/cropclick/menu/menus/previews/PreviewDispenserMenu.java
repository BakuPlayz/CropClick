package com.github.bakuplayz.cropclick.menu.menus.previews;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.PreviewMenu;
import lombok.AccessLevel;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PreviewDispenserMenu extends PreviewMenu {

    private @Setter(AccessLevel.PRIVATE) Inventory dispenserInventory;


    public PreviewDispenserMenu(@NotNull CropClick plugin,
                                @NotNull Player player,
                                @NotNull String shortFarmerID,
                                @NotNull Inventory inventory) {
        super(plugin, player, LanguageAPI.Menu.DISPENSER_PREVIEW_TITLE, shortFarmerID);
        setInventoryType(inventory.getType());
        setDispenserInventory(inventory);
    }


    @Override
    public void setMenuItems() {
        inventory.setContents(dispenserInventory.getContents());
    }

}