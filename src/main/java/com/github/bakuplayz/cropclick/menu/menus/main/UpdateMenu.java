package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class UpdateMenu extends Menu {

    private final boolean isUpdated;


    public UpdateMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.UPDATES_TITLE);
        isUpdated = plugin.getUpdateManager().isUpdated();
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(20, getPlayerItem());
        inventory.setItem(22, getUpdateItem());
        inventory.setItem(24, getConsoleItem());

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new MainMenu(plugin, player));

        //TODO: Comeback and fix this...
        if (clicked.equals(getUpdateItem())) {
            if (!isUpdated) {
                player.sendMessage(MessageUtil.colorize("&7No new updates."));
                return;
            }

            player.sendMessage(MessageUtil.colorize("&7Get the new update on Spigot!"));
            player.sendMessage(MessageUtil.colorize("&7https://www.spigotmc.org/resources/cropclick.69480/history"));
        }

        if (clicked.equals(getPlayerItem())) {
            plugin.getConfig().set("updateMessage.player", !getState("player"));
            plugin.saveConfig();
        }

        if (clicked.equals(getConsoleItem())) {
            plugin.getConfig().set("updateMessage.console", !getState("console"));
            plugin.saveConfig();
        }

        updateMenu();
    }


    private @NotNull ItemStack getUpdateItem() {
        return new ItemUtil(Material.ANVIL)
                .setName(plugin, LanguageAPI.Menu.UPDATES_UPDATES_ITEM_NAME)
                .setLore(LanguageAPI.Menu.UPDATES_UPDATES_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.UPDATES_UPDATES_ITEM_STATUS.get(plugin, isUpdated)
                )).toItemStack();
    }


    private @NotNull ItemStack getPlayerItem() {
        return new ItemUtil(Material.ITEM_FRAME)
                .setName(plugin, LanguageAPI.Menu.UPDATES_PLAYER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.UPDATES_PLAYER_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.UPDATES_PLAYER_ITEM_STATUS.get(plugin, getState("player"))
                )).toItemStack();
    }


    private @NotNull ItemStack getConsoleItem() {
        return new ItemUtil(Material.ITEM_FRAME)
                .setName(plugin, LanguageAPI.Menu.UPDATES_CONSOLE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.UPDATES_CONSOLE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.UPDATES_CONSOLE_ITEM_STATUS.get(plugin, getState("console"))
                )).toItemStack();
    }


    private boolean getState(@NotNull String name) {
        return plugin.getConfig().getBoolean("updateMessage." + name, true);
    }

}