package com.github.bakuplayz.cropclick.menu.menus.interacts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.AutofarmMenu;
import com.github.bakuplayz.cropclick.menu.Menu;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Menu
 * @since 1.6.0
 */
public final class CropInteractMenu extends AutofarmMenu {

    public CropInteractMenu(@NotNull CropClick plugin,
                            @NotNull Player player,
                            @NotNull Block block) {
        super(plugin, player, block, LanguageAPI.Menu.CROP_INTERACT_TITLE);
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (isLinked) return;
        if (!clicked.equals(cropItem)) return;

        if (isCropSelected) {
            autofarmManager.deselectCrop(player, block);
        }

        autofarmManager.selectCrop(player, block);

        updateMenu();
        handleLink();
    }

}