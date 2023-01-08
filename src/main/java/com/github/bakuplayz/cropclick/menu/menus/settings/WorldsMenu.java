package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
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
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A class representing the Worlds menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class WorldsMenu extends PaginatedMenu {

    private final AddonManager addonManager;

    /**
     * A variable containing the state or menu to return to when clicking the {@link #getBackItem() back item}.
     */
    private final WorldMenuState menuState;

    /**
     * A variable containing all the {@link FarmWorld farm worlds}.
     */
    private final List<FarmWorld> worlds;


    public WorldsMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull WorldMenuState state) {
        super(plugin, player, LanguageAPI.Menu.WORLDS_TITLE);
        this.worlds = new ArrayList<>(plugin.getWorldManager().getWorlds().values());
        this.addonManager = plugin.getAddonManager();
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

        int index = indexOfWorld(clicked);
        if (index == -1) {
            return;
        }

        FarmWorld world = worlds.get(index);
        switch (menuState) {
            case SETTINGS:
                new WorldMenu(plugin, player, world).openMenu();
                return;

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

        refreshMenu();
    }


    /**
     * Finds the index of the {@link FarmWorld world} based on the {@link ItemStack clicked item}.
     *
     * @param clicked the item that was clicked.
     *
     * @return the index of the world, otherwise -1.
     */
    private int indexOfWorld(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * Creates a menu {@link ItemStack item} based on the {@link FarmWorld provided world}.
     *
     * @param world the world to base the item on.
     *
     * @return the created menu item.
     */
    private @NotNull ItemStack createMenuItem(@NotNull FarmWorld world) {
        String name = MessageUtils.beautify(world.getName(), true);
        ItemBuilder menuItem = new ItemBuilder(Material.GRASS)
                .setName(LanguageAPI.Menu.WORLDS_ITEM_NAME.get(plugin, name))
                .setMaterial(name.contains("End"), Material.ENDER_STONE)
                .setMaterial(name.contains("Nether"), Material.NETHERRACK);

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
     * Gets all the {@link #worlds} as {@link #menuItems menu items}.
     *
     * @return worlds as menu items.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return worlds.stream()
                     .map(this::createMenuItem)
                     .collect(Collectors.toList());
    }

}