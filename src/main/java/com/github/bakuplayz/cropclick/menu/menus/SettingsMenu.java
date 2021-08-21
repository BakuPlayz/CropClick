package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public final class SettingsMenu extends Menu {

    public SettingsMenu(final CropClick plugin, final Player player) {
        super(player, plugin);
    }

    @Override
    public String getTitle() {
        return "CropClick: Settings"; //LanguageAPI
    }

    @Override
    public int getSlots() {
        return 54;
    }

    @Override
    public void setMenuItems() {

    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

    }
}
