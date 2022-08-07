package com.github.bakuplayz.cropclick.menu.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.base.LinkMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.AutofarmsMenu;
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
public final class DispenserLinkMenu extends LinkMenu {

    private final boolean isRedirected;


    public DispenserLinkMenu(@NotNull CropClick plugin,
                             @NotNull Player player,
                             @NotNull Block block,
                             boolean isRedirected) {
        super(plugin, player, block, LanguageAPI.Menu.DISPENSER_LINK_TITLE, Component.DISPENSER);
        this.isRedirected = isRedirected;
    }


    @Override
    public void setMenuItems() {
        super.setMenuItems();

        if (isRedirected) {
            setBackItem();
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new AutofarmsMenu(plugin, player));

        if (!isUnlinked) {
            handlePreviews(clicked);
            handleToggle(clicked);
            updateMenu();
        }

        if (!clicked.equals(dispenserItem)) {
            return;
        }

        if (isUnlinked) {
            handleSelect();
            updateMenu();
            handleLink();
        }
    }

}