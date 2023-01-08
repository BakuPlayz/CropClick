package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.*;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.*;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the Addons menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AddonsMenu extends BaseMenu {

    /**
     * A variable checking if the {@link McMMOAddon} is on the server.
     */
    private final boolean mmoInstalled;

    /**
     * A variable checking if the {@link McMMOAddon} is enabled in {@link CropClick}.
     */
    private final boolean mmoEnabled;

    /**
     * A variable checking if the {@link JobsRebornAddon} is on the server.
     */
    private final boolean jobsInstalled;

    /**
     * A variable checking if the {@link JobsRebornAddon} is enabled in {@link CropClick}.
     */
    private final boolean jobsEnabled;

    /**
     * A variable checking if the {@link TownyAddon} is on the server.
     */
    private final boolean townyInstalled;

    /**
     * A variable checking if the {@link TownyAddon} is enabled in {@link CropClick}.
     */
    private final boolean townyEnabled;

    /**
     * A variable checking if the {@link WorldGuardAddon} is on the server.
     */
    private final boolean guardInstalled;

    /**
     * A variable checking if the {@link WorldGuardAddon} is enabled in {@link CropClick}.
     */
    private final boolean guardEnabled;

    /**
     * A variable checking if the {@link OfflineGrowthAddon} is on the server.
     */
    private final boolean growthInstalled;

    /**
     * A variable checking if the {@link OfflineGrowthAddon} is enabled in {@link CropClick}.
     */
    private final boolean growthEnabled;

    /**
     * A variable checking if the {@link ResidenceAddon} is on the server.
     */
    private final boolean residenceInstalled;

    /**
     * A variable checking if the {@link ResidenceAddon} is enabled in {@link CropClick}.
     */
    private final boolean residenceEnabled;


    public AddonsMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.ADDONS_TITLE);
        AddonManager addonManager = plugin.getAddonManager();
        this.mmoInstalled = addonManager.isInstalled("mcMMO");
        this.mmoEnabled = addonManager.isEnabled("mcMMO");
        this.townyInstalled = addonManager.isInstalled("Towny");
        this.townyEnabled = addonManager.isEnabled("Towny");
        this.jobsInstalled = addonManager.isInstalled("JobsReborn");
        this.jobsEnabled = addonManager.isEnabled("JobsReborn");
        this.guardInstalled = addonManager.isInstalled("WorldGuard");
        this.guardEnabled = addonManager.isEnabled("WorldGuard");
        this.residenceInstalled = addonManager.isInstalled("Residence");
        this.residenceEnabled = addonManager.isEnabled("Residence");
        this.growthInstalled = addonManager.isInstalled("OfflineGrowth");
        this.growthEnabled = addonManager.isEnabled("OfflineGrowth");
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(10, getJobsItem());
        inventory.setItem(13, getMCMMOItem());
        inventory.setItem(16, getGrowthItem());

        inventory.setItem(28, getResidenceItem());
        inventory.setItem(31, getTownyItem());
        inventory.setItem(34, getGuardItem());

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new MainMenu(plugin, player));

        if (clicked.equals(getJobsItem())) {
            if (jobsInstalled) new JobsRebornMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getMCMMOItem())) {
            if (mmoInstalled) new McMMOMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getGrowthItem())) {
            if (growthInstalled) new OfflineGrowthMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getResidenceItem())) {
            if (residenceInstalled) new ResidenceMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getTownyItem())) {
            if (townyInstalled) new TownyMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getGuardItem())) {
            if (guardInstalled) new WorldGuardMenu(plugin, player).openMenu();
        }
    }


    /**
     * Gets the JobsReborn {@link ItemStack item}.
     *
     * @return the jobs item.
     */
    public @NotNull ItemStack getJobsItem() {
        return new ItemBuilder(Material.STONE_HOE)
                .setName(plugin, LanguageAPI.Menu.ADDONS_JOBS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDONS_JOBS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDONS_JOBS_ITEM_STATUS.get(plugin, jobsEnabled)
                ))
                .setMaterial(
                        jobsEnabled ? null : Material.GRAY_STAINED_GLASS_PANE
                )
                .setMaterial(
                        jobsInstalled ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Gets the mcMMO {@link ItemStack item}.
     *
     * @return the mcMMO item.
     */
    public @NotNull ItemStack getMCMMOItem() {
        return new ItemBuilder(Material.GOLDEN_SWORD)
                .setName(plugin, LanguageAPI.Menu.ADDONS_MCMMO_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDONS_MCMMO_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDONS_MCMMO_ITEM_STATUS.get(plugin, mmoEnabled)
                ))
                .setMaterial(
                        mmoEnabled ? null : Material.GRAY_STAINED_GLASS_PANE
                )
                .setMaterial(
                        mmoInstalled ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Gets the OfflineGrowth {@link ItemStack item}.
     *
     * @return the growth item.
     */
    public @NotNull ItemStack getGrowthItem() {
        return new ItemBuilder(Material.TALL_GRASS)
                .setName(plugin, LanguageAPI.Menu.ADDONS_GROWTH_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDONS_GROWTH_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDONS_GROWTH_ITEM_STATUS.get(plugin, growthEnabled)
                ))
                .setMaterial(
                        growthEnabled ? null : Material.GRAY_STAINED_GLASS_PANE
                )
                .setMaterial(
                        growthInstalled ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Gets the Residence {@link ItemStack item}.
     *
     * @return the residence item.
     */
    public @NotNull ItemStack getResidenceItem() {
        return new ItemBuilder(Material.OAK_FENCE)
                .setName(plugin, LanguageAPI.Menu.ADDONS_RESIDENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDONS_RESIDENCE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDONS_RESIDENCE_ITEM_STATUS.get(plugin, residenceEnabled)
                ))
                .setMaterial(
                        residenceEnabled ? null : Material.GRAY_STAINED_GLASS_PANE
                )
                .setMaterial(
                        residenceInstalled ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Gets the Towny {@link ItemStack item}.
     *
     * @return the towny item.
     */
    public @NotNull ItemStack getTownyItem() {
        return new ItemBuilder(Material.OAK_FENCE_GATE)
                .setName(plugin, LanguageAPI.Menu.ADDONS_TOWNY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDONS_TOWNY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDONS_TOWNY_ITEM_STATUS.get(plugin, townyEnabled)
                ))
                .setMaterial(
                        townyEnabled ? null : Material.GRAY_STAINED_GLASS_PANE
                )
                .setMaterial(
                        townyInstalled ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Gets the WorldGuard {@link ItemStack item}.
     *
     * @return the guard item.
     */
    public @NotNull ItemStack getGuardItem() {
        return new ItemBuilder(Material.GRASS)
                .setName(plugin, LanguageAPI.Menu.ADDONS_GUARD_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDONS_GUARD_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDONS_GUARD_ITEM_STATUS.get(plugin, guardEnabled)
                ))
                .setMaterial(
                        guardEnabled ? null : Material.GRAY_STAINED_GLASS_PANE
                )
                .setMaterial(
                        guardInstalled ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }

}
