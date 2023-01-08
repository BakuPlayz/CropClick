package com.github.bakuplayz.cropclick.menu.menus.addons;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.AddonMenu;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
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
 * A class representing the Towny menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
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

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new AddonsMenu(plugin, player));
        handleWorlds(clicked, new WorldsMenu(plugin, player, WorldMenuState.TOWNY));
        handleToggle(clicked, getToggleItem());

        this.refreshMenu();
    }


    /**
     * Gets the toggle {@link ItemStack item}.
     *
     * @return the toggle item.
     */
    @Override
    protected @NotNull ItemStack getToggleItem() {
        return new ItemBuilder(Material.FENCE_GATE)
                .setName(LanguageAPI.Menu.ADDON_TOWNY_ITEM_NAME.get(plugin,
                        MessageUtils.getStatusMessage(plugin, isEnabled())
                ))
                .setLore(LanguageAPI.Menu.ADDON_TOWNY_ITEM_TIPS.getAsList(plugin))
                .setMaterial(!isEnabled(), Material.STAINED_GLASS_PANE)
                .setDamage(!isEnabled(), 7)
                .toItemStack();
    }

}