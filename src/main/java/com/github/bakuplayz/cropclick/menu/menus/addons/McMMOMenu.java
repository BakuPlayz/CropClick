package com.github.bakuplayz.cropclick.menu.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.AddonMenu;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.AddonsMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
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
public final class McMMOMenu extends AddonMenu {


    public McMMOMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.MCMMO_TITLE, "mcMMO");
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(20, getCropsSettingsItem());

        setToggleItem();
        setWorldsItem();
        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new AddonsMenu(plugin, player));
        handleWorlds(clicked, new WorldsMenu(plugin, player, WorldMenuState.MCMMO));
        handleToggle(clicked, getToggleItem());

        if (clicked.equals(getCropsSettingsItem())) {
            new CropsMenu(plugin, player, CropMenuState.MCMMO).open();
        }

        refresh();
    }


    /**
     * It creates an ItemStack with the material of wheat, sets the name to the "Crop Settings" and a
     * small description of the settings.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getCropsSettingsItem() {
        return new ItemBuilder(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.ADDON_CROP_SETTINGS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDON_CROP_SETTINGS_ITEM_TIPS.getAsList(plugin))
                .toItemStack();
    }


    /**
     * Return a new ItemStack with the name and lore set to the mcMMO item's name and lore, and the material set to either
     * a gold sword or stained-glass pane depending on whether mcMMO is enabled.
     *
     * @return An ItemStack with the name "mcMMO" and the lore of ex: "Enabled: true".
     */
    @Override
    protected @NotNull ItemStack getToggleItem() {
        return new ItemBuilder(Material.GOLDEN_SWORD)
                .setName(LanguageAPI.Menu.ADDON_MCMMO_ITEM_NAME.get(plugin,
                        MessageUtils.getEnabledStatus(plugin, isAddonEnabled)
                ))
                .setLore(LanguageAPI.Menu.ADDON_MCMMO_ITEM_TIPS.getAsList(plugin))
                .setMaterial(isAddonEnabled ? null : Material.GRAY_STAINED_GLASS_PANE)
                .toItemStack();
    }

}