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
public final class ContainerInteractMenu extends AutofarmMenu {

    private final AutofarmManager autofarmManager;


    public ContainerInteractMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Block block) {
        super(plugin, player, block, LanguageAPI.Menu.CONTAINER_INTERACT_TITLE, Component.CONTAINER);
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

        if (!clicked.equals(containerItem)) {
            return;
        }

        if (isClickedSelected) {
            autofarmManager.deselectContainer(player, block);
        } else {
            autofarmManager.selectContainer(player, block);
        }

        updateMenu();
        handleLink();
    }

}