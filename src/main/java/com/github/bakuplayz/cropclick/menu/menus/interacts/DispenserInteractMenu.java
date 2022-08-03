package com.github.bakuplayz.cropclick.menu.menus.interacts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
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
public final class DispenserInteractMenu extends AutofarmMenu {

    private final AutofarmManager autofarmManager;


    public DispenserInteractMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Block block) {
        super(plugin, player, block, LanguageAPI.Menu.DISPENSER_INTERACT_TITLE, Component.DISPENSER);
        this.autofarmManager = plugin.getAutofarmManager();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (!isUnlinked) {
            if (clicked.equals(dispenserItem)) {
                openDispenser();
                return;
            }

            if (clicked.equals(containerItem)) {
                openContainer();
                return;
            }
        }

        if (!clicked.equals(dispenserItem)) {
            return;
        }

        if (isClickedSelected) {
            autofarmManager.deselectDispenser(player, block);
        } else {
            autofarmManager.selectDispenser(player, block);
        }

        updateMenu();
        handleLink();
    }

}