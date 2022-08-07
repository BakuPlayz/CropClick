package com.github.bakuplayz.cropclick.menu.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.base.LinkMenu;
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
public final class ContainerLinkMenu extends LinkMenu {

    public ContainerLinkMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Block block) {
        super(plugin, player, block, LanguageAPI.Menu.CONTAINER_LINK_TITLE, Component.CONTAINER);
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (!isUnlinked) {
            handlePreviews(clicked);
            handleToggle(clicked);
            updateMenu();
        }

        if (!clicked.equals(containerItem)) {
            return;
        }

        if (isUnlinked) {
            handleSelect();
            updateMenu();
            handleLink();
        }
    }

}