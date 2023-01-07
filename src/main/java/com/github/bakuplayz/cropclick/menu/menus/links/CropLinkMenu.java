package com.github.bakuplayz.cropclick.menu.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.LinkMenu;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the Crop Link menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class CropLinkMenu extends LinkMenu {

    public CropLinkMenu(@NotNull CropClick plugin,
                        @NotNull Player player,
                        @NotNull Block block,
                        Autofarm autofarm) {
        super(plugin, player, block, autofarm, LanguageAPI.Menu.CROP_LINK_TITLE, Component.CROP);
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        if (isUnclaimed) {
            handleUnclaimed(clicked);
            refresh();
            return;
        }

        if (!isUnlinked) {
            handlePreviews(clicked);
            handleToggle(clicked);
            refresh();
        }

        if (!clicked.equals(cropItem)) {
            return;
        }

        if (isUnlinked) {
            handleSelect();
            refresh();
            handleLink();
        }
    }

}