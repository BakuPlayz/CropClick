package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.*;
import com.github.bakuplayz.cropclick.menu.menus.main.SettingsMenu;
import com.github.bakuplayz.cropclick.menu.menus.worlds.WorldMenu;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A class representing the Worlds menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class WorldsMenu extends PaginatedMenu {

    private final AddonManager addonManager;

    private final WorldMenuState menuState;

    private final List<FarmWorld> worlds;


    public WorldsMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull WorldMenuState state) {
        super(plugin, player, LanguageAPI.Menu.WORLDS_TITLE);
        this.addonManager = plugin.getAddonManager();
        this.worlds = getWorlds();
        this.menuState = state;
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

        switch (menuState) {
            case SETTINGS:
                handleBack(clicked, new SettingsMenu(plugin, player, true));
                break;

            case JOBS_REBORN:
                handleBack(clicked, new JobsRebornMenu(plugin, player));
                break;

            case MCMMO:
                handleBack(clicked, new McMMOMenu(plugin, player));
                break;

            case OFFLINE_GROWTH:
                handleBack(clicked, new OfflineGrowthMenu(plugin, player));
                break;

            case RESIDENCE:
                handleBack(clicked, new ResidenceMenu(plugin, player));
                break;

            case TOWNY:
                handleBack(clicked, new TownyMenu(plugin, player));
                break;

            case WORLD_GUARD:
                handleBack(clicked, new WorldGuardMenu(plugin, player));
                break;
        }

        handlePagination(clicked);

        int index = getIndexOfWorld(clicked);
        if (index == -1) {
            return;
        }

        FarmWorld world = worlds.get(index);
        switch (menuState) {
            case SETTINGS:
                new WorldMenu(plugin, player, world).open();
                break;

            case JOBS_REBORN:
                world.toggleAddon(addonManager, "JobsReborn");
                break;

            case MCMMO:
                world.toggleAddon(addonManager, "mcMMO");
                break;

            case OFFLINE_GROWTH:
                world.toggleAddon(addonManager, "OfflineGrowth");
                break;

            case RESIDENCE:
                world.toggleAddon(addonManager, "Residence");
                break;

            case TOWNY:
                world.toggleAddon(addonManager, "Towny");
                break;

            case WORLD_GUARD:
                world.toggleAddon(addonManager, "WorldGuard");
                break;
        }

        refresh();
    }


    /**
     * "Get the index of the FarmWorld that was clicked on."
     * <p>
     * The first thing we do is create a stream of all the items in the menu. Then we filter the stream to only contain the
     * item that was clicked on. Then we map the stream to only contain the index of the item that was clicked on. Finally,
     * we find the first item in the stream and return it. If there is no item in the stream, we return -1.
     * </p>
     *
     * @param clicked The item that was clicked.
     *
     * @return The index of the world in the menuItems list.
     */
    private int getIndexOfWorld(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * It creates an item for the menu.
     *
     * @param world The world that the item is being created for.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getMenuItem(@NotNull FarmWorld world) {
        String name = MessageUtils.beautify(world.getName(), true);
        ItemBuilder menuItem = new ItemBuilder(Material.GRASS_BLOCK)
                .setName(LanguageAPI.Menu.WORLDS_ITEM_NAME.get(plugin, name))
                .setMaterial(name.contains("End") ? Material.END_STONE : null)
                .setMaterial(name.contains("Nether") ? Material.NETHERRACK : null);

        boolean status;

        switch (menuState) {
            case JOBS_REBORN:
                status = world.isBanishedAddon(addonManager, "JobsReborn");

                menuItem.setLore(
                        LanguageAPI.Menu.WORLDS_ITEM_JOBS_TIPS.getAsList(plugin,
                                LanguageAPI.Menu.WORLDS_ITEM_STATUS.get(plugin, status)
                        ));
                break;

            case MCMMO:
                status = world.isBanishedAddon(addonManager, "mcMMO");

                menuItem.setLore(
                        LanguageAPI.Menu.WORLDS_ITEM_MCMMO_TIPS.getAsList(plugin,
                                LanguageAPI.Menu.WORLDS_ITEM_STATUS.get(plugin, status)
                        ));
                break;

            case WORLD_GUARD:
                status = world.isBanishedAddon(addonManager, "WorldGuard");

                menuItem.setLore(
                        LanguageAPI.Menu.WORLDS_ITEM_GUARD_TIPS.getAsList(plugin,
                                LanguageAPI.Menu.WORLDS_ITEM_STATUS.get(plugin, status)
                        ));
                break;

            case RESIDENCE:
                status = world.isBanishedAddon(addonManager, "Residence");

                menuItem.setLore(
                        LanguageAPI.Menu.WORLDS_ITEM_RESIDENCE_TIPS.getAsList(plugin,
                                LanguageAPI.Menu.WORLDS_ITEM_STATUS.get(plugin, status)
                        ));
                break;

            case TOWNY:
                status = world.isBanishedAddon(addonManager, "Towny");

                menuItem.setLore(
                        LanguageAPI.Menu.WORLDS_ITEM_TOWNY_TIPS.getAsList(plugin,
                                LanguageAPI.Menu.WORLDS_ITEM_STATUS.get(plugin, status)
                        ));
                break;

            case OFFLINE_GROWTH:
                status = world.isBanishedAddon(addonManager, "OfflineGrowth");

                menuItem.setLore(
                        LanguageAPI.Menu.WORLDS_ITEM_GROWTH_TIPS.getAsList(plugin,
                                LanguageAPI.Menu.WORLDS_ITEM_STATUS.get(plugin, status)
                        ));
                break;

            default:
                menuItem.setLore(
                        LanguageAPI.Menu.WORLDS_ITEM_STATUS.get(plugin, world.isBanished())
                );
                break;
        }

        return menuItem.toItemStack();
    }


    /**
     * Get a list of menu items, where each menu item is a world.
     *
     * @return A list of ItemStacks.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return worlds.stream()
                     .map(this::getMenuItem)
                     .collect(Collectors.toList());
    }


    /**
     * This function returns a list of all the worlds that the plugin is managing.
     *
     * @return A list of FarmWorlds.
     */
    @Contract(" -> new")
    private @NotNull List<FarmWorld> getWorlds() {
        return new ArrayList<>(plugin.getWorldManager().getWorlds().values());
    }

}