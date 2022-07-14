package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.PaginatedMenu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class AutofarmsMenu extends PaginatedMenu {

    public AutofarmsMenu(@NotNull Player player, @NotNull CropClick plugin) {
        super(player, plugin, LanguageAPI.Menu.AUTOFARMS_TITLE);
    }

    @Override
    public void setMenuItems() {

    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

    }
}
