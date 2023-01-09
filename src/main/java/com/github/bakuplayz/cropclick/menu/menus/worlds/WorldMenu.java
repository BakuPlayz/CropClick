/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.menu.menus.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the World menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class WorldMenu extends BaseMenu {

    private final FarmWorld world;


    public WorldMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull FarmWorld world) {
        super(plugin, player, LanguageAPI.Menu.WORLD_TITLE);
        this.world = world;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(20, getPlayersItem());
        inventory.setItem(22, getWorldItem());
        inventory.setItem(24, getAutofarmsItem());

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new WorldsMenu(plugin, player, WorldMenuState.SETTINGS));

        if (clicked.equals(getWorldItem())) {
            world.isBanished(!world.isBanished());
        }

        if (clicked.equals(getAutofarmsItem())) {
            world.allowsAutofarms(!world.allowsAutofarms());
        }

        if (clicked.getType() == Material.SKULL_ITEM) {
            world.allowsPlayers(!world.allowsPlayers());
        }

        refreshMenu();
    }


    /**
     * Gets the players {@link ItemStack item}.
     *
     * @return the players item.
     */
    private @NotNull ItemStack getPlayersItem() {
        return new ItemBuilder(Material.SKULL_ITEM)
                .setName(plugin, LanguageAPI.Menu.WORLD_PLAYERS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.WORLD_PLAYERS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.WORLD_PLAYERS_ITEM_STATUS.get(plugin, world.allowsPlayers())
                )).toPlayerHead(player);
    }


    /**
     * Gets the autofarms {@link ItemStack item}.
     *
     * @return the autofarms item.
     */
    private @NotNull ItemStack getAutofarmsItem() {
        return new ItemBuilder(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.WORLD_AUTOFARMS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.WORLD_AUTOFARMS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.WORLD_AUTOFARMS_ITEM_STATUS.get(plugin, world.allowsAutofarms())
                )).toItemStack();
    }


    /**
     * Gets the world {@link ItemStack item}.
     *
     * @return the world item.
     */
    private @NotNull ItemStack getWorldItem() {
        String name = MessageUtils.beautify(world.getName(), true);

        return new ItemBuilder(Material.GRASS)
                .setName(LanguageAPI.Menu.WORLD_WORLD_ITEM_NAME.get(plugin, name))
                .setMaterial(name.contains("End"), Material.ENDER_STONE)
                .setMaterial(name.contains("Nether"), Material.NETHERRACK)
                .setLore(LanguageAPI.Menu.WORLD_WORLD_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.WORLD_WORLD_ITEM_STATUS.get(plugin, world.isBanished())
                ))
                .toItemStack();
    }

}