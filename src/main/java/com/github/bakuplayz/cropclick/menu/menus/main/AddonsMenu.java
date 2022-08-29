package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.*;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
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
 * @since 2.0.0
 */
public final class AddonsMenu extends Menu {

    private final boolean mmoPresent;
    private final boolean mmoEnabled;

    private final boolean jobsPresent;
    private final boolean jobsEnabled;

    private final boolean townyPresent;
    private final boolean townyEnabled;

    private final boolean guardPresent;
    private final boolean guardEnabled;

    private final boolean growthPresent;
    private final boolean growthEnabled;

    private final boolean residencePresent;
    private final boolean residenceEnabled;


    public AddonsMenu(@NotNull CropClick plugin, @NotNull Player player) {
        super(plugin, player, LanguageAPI.Menu.ADDONS_TITLE);
        AddonManager addonManager = plugin.getAddonManager();
        this.mmoPresent = addonManager.isPresent("mcMMO");
        this.mmoEnabled = addonManager.isEnabled("mcMMO");
        this.townyPresent = addonManager.isPresent("Towny");
        this.townyEnabled = addonManager.isEnabled("Towny");
        this.jobsPresent = addonManager.isPresent("JobsReborn");
        this.jobsEnabled = addonManager.isEnabled("JobsReborn");
        this.guardPresent = addonManager.isPresent("WorldGuard");
        this.guardEnabled = addonManager.isEnabled("WorldGuard");
        this.residencePresent = addonManager.isPresent("Residence");
        this.residenceEnabled = addonManager.isEnabled("Residence");
        this.growthPresent = addonManager.isPresent("OfflineGrowth");
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

        handleBack(clicked, new MainMenu(plugin, player));

        if (clicked.equals(getJobsItem())) {
            if (jobsPresent || true) new JobsRebornMenu(plugin, player).open();
        }

        if (clicked.equals(getMCMMOItem())) {
            if (mmoPresent || true) new McMMOMenu(plugin, player).open();
        }

        if (clicked.equals(getGrowthItem())) {
            if (growthPresent || true) new OfflineGrowthMenu(plugin, player).open();
        }

        if (clicked.equals(getResidenceItem())) {
            if (residencePresent || true) new ResidenceMenu(plugin, player).open();
        }

        if (clicked.equals(getTownyItem())) {
            if (townyPresent || true) new TownyMenu(plugin, player).open();
        }

        if (clicked.equals(getGuardItem())) {
            if (guardPresent || true) new WorldGuardMenu(plugin, player).open();
        }
    }


    /**
     * Return a new ItemStack with the name and lore set to the Jobs Reborn item's name and lore, and the material set to either
     * a stone hoe or stained-glass pane depending on whether Jobs Reborn present and/or enabled.
     *
     * @return An ItemStack with the name "Jobs Reborn" and the lore of ex: "Enabled: true".
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
                        jobsPresent ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Return a new ItemStack with the name and lore set to the mcMMO item's name and lore, and the material set to either
     * a gold sword or stained-glass pane depending on whether mcMMO present and/or enabled.
     *
     * @return An ItemStack with the name "mcMMO" and the lore of ex: "Enabled: true".
     */
    public @NotNull ItemStack getMCMMOItem() {
        return new ItemBuilder(Material.GOLDEN_SWORD)
                .setName(plugin, LanguageAPI.Menu.ADDONS_MCMMO_ITEM_NAME)
                .setLore(LanguageAPI.Menu.ADDONS_MCMMO_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.ADDONS_MCMMO_ITEM_STATUS.get(plugin, mmoEnabled)
                ))

                .setMaterial(
                        mmoPresent ? null : Material.GRAY_STAINED_GLASS_PANE
                )
                .setMaterial(
                        mmoPresent ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Return a new ItemStack with the name and lore set to the OfflineGrowth item's name and lore, and the material set to either
     * a long grass or stained-glass pane depending on whether OfflineGrowth present and/or enabled.
     *
     * @return An ItemStack with the name "Offline Growth" and the lore of ex: "Enabled: true".
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
                        growthPresent ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Return a new ItemStack with the name and lore set to the Residence item's name and lore, and the material set to either
     * a fence post or stained-glass pane depending on whether Residence present and/or enabled.
     *
     * @return An ItemStack with the name "Residence" and the lore of ex: "Enabled: true".
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
                        residencePresent ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Return a new ItemStack with the name and lore set to the Towny item's name and lore, and the material set to either
     * a fence gate or stained-glass pane depending on whether Towny present and/or enabled.
     *
     * @return An ItemStack with the name "Towny" and the lore of ex: "Enabled: true".
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
                        townyPresent ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }


    /**
     * Return a new ItemStack with the name and lore set to the WorldGuard item's name and lore, and the material set to either
     * a grass-block or stained-glass pane depending on whether WorldGuard present and/or enabled.
     *
     * @return An ItemStack with the name "World Guard" and the lore of ex: "Enabled: true".
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
                        guardPresent ? null : Material.ORANGE_STAINED_GLASS_PANE
                )
                .toItemStack();
    }

}