package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.menu.Menu;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;

public final class GeneralMenu extends Menu {

    public GeneralMenu(@NotNull Player player,
                       @NotNull CropClick plugin) {
        super(player, plugin);
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getSlots() {
        return 0;
    }

    @Override
    public void setMenuItems() {

    }

    @Override
    public void handleMenu(InventoryClickEvent event) {

    }
}
