package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.SettingsMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
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
public final class InteractMenu extends Menu {

    public InteractMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.INTERACT_TITLE);
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(20, getLeftItem());
        inventory.setItem(22, getJumpItem());
        inventory.setItem(24, getRightItem());

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new SettingsMenu(plugin, player, true));

        if (clicked.equals(getLeftItem())) {
            boolean canLeft = plugin.getConfig().getBoolean("interact.leftClick", true);
            plugin.getConfig().set("interact.leftClick", !canLeft);
            plugin.saveConfig();
        }

        if (clicked.equals(getJumpItem())) {
            boolean canJump = plugin.getConfig().getBoolean("interact.jump", false);
            plugin.getConfig().set("interact.jump", !canJump);
            plugin.saveConfig();
        }

        if (clicked.equals(getRightItem())) {
            boolean canRight = plugin.getConfig().getBoolean("interact.rightClick", true);
            plugin.getConfig().set("interact.rightClick", !canRight);
            plugin.saveConfig();
        }

        updateMenu();
    }


    private @NotNull ItemStack getJumpItem() {
        boolean canJump = plugin.getConfig().getBoolean("interact.jump", true);
        return new ItemUtil(Material.STONE)
                .setName(plugin, LanguageAPI.Menu.INTERACT_JUMP_ITEM_NAME)
                .setLore(LanguageAPI.Menu.INTERACT_JUMP_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.INTERACT_JUMP_ITEM_STATUS.get(plugin, canJump)
                )).toItemStack();
    }


    private @NotNull ItemStack getLeftItem() {
        boolean canLeft = plugin.getConfig().getBoolean("interact.leftClick", true);
        return new ItemUtil(Material.STONE)
                .setName(plugin, LanguageAPI.Menu.INTERACT_LEFT_ITEM_NAME)
                .setLore(LanguageAPI.Menu.INTERACT_LEFT_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.INTERACT_LEFT_ITEM_STATUS.get(plugin, canLeft)
                )).toItemStack();
    }


    private @NotNull ItemStack getRightItem() {
        boolean canRight = plugin.getConfig().getBoolean("interact.rightClick", true);
        return new ItemUtil(Material.STONE)
                .setName(plugin, LanguageAPI.Menu.INTERACT_RIGHT_ITEM_NAME)
                .setLore(LanguageAPI.Menu.INTERACT_RIGHT_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.INTERACT_RIGHT_ITEM_STATUS.get(plugin, canRight)
                )).toItemStack();
    }

}