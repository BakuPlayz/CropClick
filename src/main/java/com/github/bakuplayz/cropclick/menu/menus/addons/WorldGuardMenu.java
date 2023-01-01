package com.github.bakuplayz.cropclick.menu.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.AddonMenu;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.AddonsMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the WorldGuard menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class WorldGuardMenu extends AddonMenu {

    public WorldGuardMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.WORLD_GUARD_TITLE, "WorldGuard");
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

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new AddonsMenu(plugin, player));
        handleWorlds(clicked, new WorldsMenu(plugin, player, WorldMenuState.WORLD_GUARD));
        handleToggle(clicked, getToggleItem());

        refresh();
    }


    /**
     * Return a new ItemStack with the name and lore set to the WorldGuard item's name and lore, and the material set to either
     * a grass-block or stained-glass pane depending on whether WorldGuard is enabled.
     *
     * @return An ItemStack with the name "World Guard" and the lore of ex: "Enabled: true".
     */
    @Override
    protected @NotNull ItemStack getToggleItem() {
        return new ItemBuilder(Material.GRASS)
                .setName(LanguageAPI.Menu.ADDON_GUARD_ITEM_NAME.get(plugin,
                        MessageUtils.getEnabledStatus(plugin, isAddonEnabled)
                ))
                .setLore(LanguageAPI.Menu.ADDON_GUARD_ITEM_TIPS.getAsList(plugin))
                .setMaterial(isAddonEnabled ? null : Material.GRAY_STAINED_GLASS_PANE)
                .toItemStack();
    }

}