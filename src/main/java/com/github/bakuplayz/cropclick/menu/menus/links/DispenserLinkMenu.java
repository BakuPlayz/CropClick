package com.github.bakuplayz.cropclick.menu.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.LinkMenu;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.AutofarmsMenu;
import com.github.bakuplayz.cropclick.menu.states.AutofarmsMenuState;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class DispenserLinkMenu extends LinkMenu {

    private final AutofarmsMenuState menuState;


    public DispenserLinkMenu(@NotNull CropClick plugin,
                             @NotNull Player player,
                             @NotNull Block block,
                             Autofarm autofarm,
                             AutofarmsMenuState state) {
        super(plugin, player, block, autofarm, LanguageAPI.Menu.DISPENSER_LINK_TITLE, Component.DISPENSER);
        this.menuState = state;
    }


    @Override
    public void setMenuItems() {
        super.setMenuItems();

        if (menuState != AutofarmsMenuState.LINK) {
            setBackItem();
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (menuState != AutofarmsMenuState.LINK) {
            handleBack(clicked, new AutofarmsMenu(plugin, player, menuState));
        }

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

        if (!clicked.equals(dispenserItem)) {
            return;
        }

        if (isUnlinked) {
            handleSelect();
            refresh();
            handleLink();
        }
    }

}