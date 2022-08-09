package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.SettingsMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
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
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class ToggleMenu extends PaginatedMenu {

    private final PlayersConfig playersConfig;

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

        handleBack(clicked, new SettingsMenu(plugin, player, true));
        handlePagination(clicked);

        int index = getIndexOfPlayer(clicked);
        if (index == -1) {
            return;
        }

        String player = players.get(index);
        playersConfig.togglePlayer(player);

        updateMenu();
    }


    /**
     * "Get the index of the player item that was clicked on."
     * <p>
     * The first thing we do is create a stream of all the items in the menu. Then we filter the stream to only contain the
     * item that was clicked on. Then we map the stream to only contain the index of the item that was clicked on. Finally,
     * we find the first item in the stream and return it. If there is no item in the stream, we return -1.
     * </p>
     *
     * @param clicked The item that was clicked.
     *
     * @return The index of the sound in the menuItems list.
     */
    private int getIndexOfPlayer(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * "It returns an ItemStack with the name of the player."
     * <p>
     * The first thing we do is get the player's name. We use the `MessageUtils` class to beautify the name. This means that
     * we capitalize the first letter of the name and lowercase the rest.
     * </p>
     *
     * @param playerID The UUID of the player to get the menu item for.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getMenuItem(@NotNull String playerID) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerID));
        String status = MessageUtils.getEnabledStatus(
                plugin,
                playersConfig.isEnabled(playerID)
        );

        return new ItemUtil(Material.SKULL_ITEM)
                .setName(LanguageAPI.Menu.TOGGLE_ITEM_NAME.get(plugin, offlinePlayer.getName()))
                .setLore(LanguageAPI.Menu.TOGGLE_ITEM_STATUS.get(plugin, status))
                .toItemStack();
    }


    /**
     * Get a list of menu items for each player, and return them.
     *
     * @return A list of ItemStacks.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return players.stream()
                      .map(this::getMenuItem)
                      .collect(Collectors.toList());
    }


    /**
     * Get a list of all the players on the server.
     *
     * @return A list of all the players on the server.
     */
    private @NotNull List<String> getPlayers() {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                     .map(OfflinePlayer::getUniqueId)
                     .map(Object::toString)
                     .collect(Collectors.toList());
    }

}