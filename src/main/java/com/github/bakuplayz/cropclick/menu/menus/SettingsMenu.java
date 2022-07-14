package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class SettingsMenu extends Menu {

    public SettingsMenu(CropClick plugin, Player player) {
        super(player, plugin, LanguageAPI.Menu.SETTINGS_TITLE);
    }

    @Override
    public void setMenuItems() {

    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

    }

}
