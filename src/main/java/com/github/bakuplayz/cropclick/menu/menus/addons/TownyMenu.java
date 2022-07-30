package com.github.bakuplayz.cropclick.menu.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.AddonMenu;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.AddonsMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
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
 * @see Menu
 * @since 1.6.0
 */
public final class TownyMenu extends AddonMenu {

    public TownyMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.TOWNY_TITLE, "Towny");
    }


    @Override
    public void setMenuItems() {
        setToggleItem();
        setWorldsItem();
        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new AddonsMenu(plugin, player));
        handleWorlds(clicked, new WorldsMenu(plugin, player, WorldMenuState.TOWNY));
        handleToggle(clicked, getToggleItem());

        updateMenu();
    }


    /**
     * Return a new ItemStack with the name and lore set to the Towny item's name and lore, and the material set to either
     * a fence gate or stained-glass pane depending on whether Towny is enabled.
     *
     * @return An ItemStack with the name "Towny" and the lore of ex: "Enabled: true".
     */
    @NotNull
    @Override
    protected ItemStack getToggleItem() {
        return new ItemUtil(Material.FENCE_GATE)
                .setName(plugin, LanguageAPI.Menu.ADDON_TOWNY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDON_TOWNY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDON_TOWNY_ITEM_STATUS.get(plugin, addonEnabled)
                ))
                .setMaterial(addonEnabled ? null : Material.STAINED_GLASS_PANE)
                .setDamage(addonEnabled ? -1 : 7)
                .toItemStack();
    }

}