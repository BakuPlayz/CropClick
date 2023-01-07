package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the Updates menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class UpdatesMenu extends Menu {

    private final UpdateManager updateManager;


    public UpdatesMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.UPDATES_TITLE);
        this.updateManager = plugin.getUpdateManager();
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

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new MainMenu(plugin, player));

        if (clicked.equals(getPlayerItem())) {
            updateManager.togglePlayerMessage();
        }

        if (clicked.equals(getConsoleItem())) {
            updateManager.toggleConsoleMessage();
        }

        if (clicked.equals(getUpdateItem())) {
            if (!updateManager.isUpdated()) {
                player.sendMessage(MessageUtils.colorize("&7No new updates."));
                return;
            }

            String updateURL = updateManager.getUpdateURL();
            if (updateURL.equals("")) return;

            String updateMessage = updateManager.getUpdateMessage();
            if (updateMessage.equals("")) return;

            player.sendMessage(MessageUtils.colorize(updateMessage));
            player.sendMessage(MessageUtils.colorize("&7Get the new update on Spigot!"));
            player.sendMessage(MessageUtils.colorize("&7" + updateURL));
        }

        refresh();
    }


    /**
     * It creates an item representing the current update state.
     *
     * @return An item representing the current update state.
     */
    private @NotNull ItemStack getUpdateItem() {
        String updateState = updateManager.getUpdateStateMessage();

        return new ItemBuilder(Material.ANVIL)
                .setName(plugin, LanguageAPI.Menu.UPDATES_UPDATES_ITEM_NAME)
                .setLore(LanguageAPI.Menu.UPDATES_UPDATES_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.UPDATES_UPDATES_ITEM_STATE.get(plugin, updateState)
                )).toItemStack();
    }


    /**
     * It creates an item representing the player update message.
     *
     * @return An item representing the player update message.
     */
    private @NotNull ItemStack getPlayerItem() {
        String status = MessageUtils.getEnabledStatus(
                plugin,
                updateManager.getPlayerMessageState()
        );

        return new ItemBuilder(Material.ITEM_FRAME)
                .setName(plugin, LanguageAPI.Menu.UPDATES_PLAYER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.UPDATES_PLAYER_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.UPDATES_PLAYER_ITEM_STATUS.get(plugin, status)
                )).toItemStack();
    }


    /**
     * It creates an item representing the console update message.
     *
     * @return An item representing the console update message.
     */
    private @NotNull ItemStack getConsoleItem() {
        String status = MessageUtils.getEnabledStatus(
                plugin,
                updateManager.getConsoleMessageState()
        );

        return new ItemBuilder(Material.ITEM_FRAME)
                .setName(plugin, LanguageAPI.Menu.UPDATES_CONSOLE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.UPDATES_CONSOLE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.UPDATES_CONSOLE_ITEM_STATUS.get(plugin, status)
                )).toItemStack();
    }

}