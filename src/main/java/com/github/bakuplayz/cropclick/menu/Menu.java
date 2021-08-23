package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public abstract class Menu implements InventoryHolder {

    protected Player player;
    protected CropClick plugin;
    protected @Getter Inventory inventory;

    protected LanguageAPI.Menu menuTitle;

    public Menu(final @NotNull Player player,
                final @NotNull CropClick plugin,
                final @NotNull LanguageAPI.Menu menuTitle) {
        this.menuTitle = menuTitle;
        this.player = player;
        this.plugin = plugin;
    }

    public abstract void setMenuItems();

    public abstract void handleMenu(final InventoryClickEvent event);

    private void setInventory() {
        this.inventory = Bukkit.createInventory(this, 54, menuTitle.get(plugin));
    }

    public final void open() {
        setInventory();
        setMenuItems();
        player.openInventory(inventory);
    }

    public final void updateMenu() {
        inventory.clear();
        setMenuItems();
    }
}
