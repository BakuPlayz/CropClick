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

package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.SettingsMenu;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


/**
 * A class representing the Toggle menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class ToggleMenu extends PaginatedMenu {

    private final PlayersConfig playersConfig;

    /**
     * A variable containing all the {@link Player players'} UUIDS.
     */
    private final List<String> players;


    public ToggleMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.TOGGLE_TITLE);
        this.playersConfig = plugin.getPlayersConfig();
        this.players = getPlayers();
    }


    @Override
    public void setMenuItems() {
        this.menuItems = getMenuItems();

        setPaginatedItems();
        setPageItems();
        setBackItems();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new SettingsMenu(plugin, player, true));
        handlePagination(clicked);

        int index = indexOfPlayer(clicked);
        if (index == -1) {
            return;
        }

        playersConfig.togglePlayer(
                players.get(index)
        );

        refreshMenu();
    }


    /**
     * Finds the index of the {@link OfflinePlayer#getUniqueId() player's ID} based on the {@link ItemStack clicked item}.
     *
     * @param clicked the item that was clicked.
     *
     * @return the index of the player's ID, otherwise -1.
     */
    private int indexOfPlayer(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(item -> {
                            if (item.getType() != clicked.getType()) {
                                return false;
                            }

                            if (item.getDurability() != clicked.getDurability()) {
                                return false;
                            }

                            if (!item.hasItemMeta()) {
                                return false;
                            }

                            if (!item.getItemMeta().hasDisplayName()) {
                                return false;
                            }

                            return item.getItemMeta().getDisplayName().equals(clicked.getItemMeta().getDisplayName());
                        })
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * Creates a menu {@link ItemStack item} based on the {@link OfflinePlayer#getUniqueId() provided player's ID}.
     *
     * @param playerID the UUID to base the item on.
     *
     * @return the created menu item.
     */
    private @NotNull ItemStack createMenuItem(@NotNull String playerID) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(
                UUID.fromString(playerID)
        );
        String status = MessageUtils.getStatusMessage(
                plugin,
                playersConfig.isEnabled(offlinePlayer)
        );

        assert offlinePlayer.getName() != null; // Cannot be null since the playerID grantee a valid ID.

        return new ItemBuilder(Material.SKULL_ITEM)
                .setName(LanguageAPI.Menu.TOGGLE_ITEM_NAME.get(plugin, offlinePlayer.getName()))
                .setLore(LanguageAPI.Menu.TOGGLE_ITEM_STATUS.get(plugin, status))
                .toPlayerHead(offlinePlayer);
    }


    /**
     * Gets all the {@link #players players' IDs} as {@link #menuItems menu items}.
     *
     * @return players as menu items.
     */
    @Override
    protected @NotNull List<ItemStack> getMenuItems() {
        return players.stream()
                      .map(this::createMenuItem)
                      .collect(Collectors.toList());
    }


    /**
     * Gets all the {@link OfflinePlayer#getUniqueId() players' IDs} that ever joined the server.
     *
     * @return the found player IDs.
     */
    private @NotNull List<String> getPlayers() {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                     .map(OfflinePlayer::getUniqueId)
                     .map(Object::toString)
                     .collect(Collectors.toList());
    }

}