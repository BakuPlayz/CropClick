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

package com.github.bakuplayz.cropclick.menu.menus.addons.additional;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.JobsRebornAddon;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.JobsRebornMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the JobsReborn Crop menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see CropsMenu
 * @see JobsRebornMenu
 * @see BaseMenu
 * @since 2.0.0
 */
public final class JobsCropMenu extends BaseMenu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int VALUE_MIN = 0;
    private final int VALUE_MAX = 10_000;


    private final String cropName;
    private final AddonConfigSection addonSection;


    public JobsCropMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.JOBS_CROP_TITLE);
        this.addonSection = plugin.getCropsConfig().getAddonSection();
        this.cropName = crop.getName();
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(10, getMoneyDecreaseItem(MAX_CHANGE));
        inventory.setItem(11, getMoneyDecreaseItem(MIN_CHANGE));
        inventory.setItem(13, getMoneyItem());
        inventory.setItem(15, getMoneyIncreaseItem(MIN_CHANGE));
        inventory.setItem(16, getMoneyIncreaseItem(MAX_CHANGE));

        inventory.setItem(19, getPointsDecreaseItem(MAX_CHANGE));
        inventory.setItem(20, getPointsDecreaseItem(MIN_CHANGE));
        inventory.setItem(22, getPointsItem());
        inventory.setItem(24, getPointsIncreaseItem(MIN_CHANGE));
        inventory.setItem(25, getPointsIncreaseItem(MAX_CHANGE));

        inventory.setItem(28, getExperienceDecreaseItem(MAX_CHANGE));
        inventory.setItem(29, getExperienceDecreaseItem(MIN_CHANGE));
        inventory.setItem(31, getExperienceItem());
        inventory.setItem(33, getExperienceIncreaseItem(MIN_CHANGE));
        inventory.setItem(34, getExperienceIncreaseItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.JOBS_REBORN));

        // Points
        if (clicked.equals(getPointsIncreaseItem(MIN_CHANGE))) {
            increasePoints(MIN_CHANGE);
        }

        if (clicked.equals(getPointsIncreaseItem(MAX_CHANGE))) {
            increasePoints(MAX_CHANGE);
        }

        if (clicked.equals(getPointsDecreaseItem(MIN_CHANGE))) {
            decreasePoints(MIN_CHANGE);
        }

        if (clicked.equals(getPointsDecreaseItem(MAX_CHANGE))) {
            decreasePoints(MAX_CHANGE);
        }

        // Money
        if (clicked.equals(getMoneyIncreaseItem(MIN_CHANGE))) {
            increaseMoney(MIN_CHANGE);
        }

        if (clicked.equals(getMoneyIncreaseItem(MAX_CHANGE))) {
            increaseMoney(MAX_CHANGE);
        }

        if (clicked.equals(getMoneyDecreaseItem(MIN_CHANGE))) {
            decreaseMoney(MIN_CHANGE);
        }

        if (clicked.equals(getMoneyDecreaseItem(MAX_CHANGE))) {
            decreaseMoney(MAX_CHANGE);
        }

        // Experience
        if (clicked.equals(getExperienceIncreaseItem(MIN_CHANGE))) {
            increaseExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceIncreaseItem(MAX_CHANGE))) {
            increaseExperience(MAX_CHANGE);
        }

        if (clicked.equals(getExperienceDecreaseItem(MIN_CHANGE))) {
            decreaseExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceDecreaseItem(MAX_CHANGE))) {
            decreaseExperience(MAX_CHANGE);
        }

        refreshMenu();
    }


    /**
     * Gets the points {@link ItemStack item}.
     *
     * @return the points item.
     */
    private @NotNull ItemStack getPointsItem() {
        double points = addonSection.getJobsPoints(cropName);

        return new ItemBuilder(Material.GOLD_NUGGET)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_POINTS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_POINTS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.JOBS_CROP_POINTS_ITEM_VALUE.get(plugin, points))
                )
                .toItemStack();
    }


    /**
     * Gets the money {@link ItemStack item}.
     *
     * @return the money item.
     */
    private @NotNull ItemStack getMoneyItem() {
        double money = addonSection.getJobsMoney(cropName);

        return new ItemBuilder(Material.GOLD_INGOT)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_MONEY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_MONEY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.JOBS_CROP_MONEY_ITEM_VALUE.get(plugin, money))
                )
                .toItemStack();
    }


    /**
     * Gets the experience {@link ItemStack item}.
     *
     * @return the experience item.
     */
    private @NotNull ItemStack getExperienceItem() {
        double experience = addonSection.getJobsExperience(cropName);

        return new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_EXPERIENCE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.JOBS_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience))
                )
                .toItemStack();
    }


    /**
     * Gets the points increase {@link ItemStack item} based on the provided points.
     *
     * @param points the points to be increased with when clicked.
     *
     * @return the points increase item.
     */
    private @NotNull ItemStack getPointsIncreaseItem(int points) {
        double beforeValue = addonSection.getJobsPoints(cropName);
        double afterValue = Math.min(beforeValue + points, VALUE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, points, "Points"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the points decrease {@link ItemStack item} based on the provided points.
     *
     * @param points the points to be decreased with when clicked.
     *
     * @return the points decrease item.
     */
    private @NotNull ItemStack getPointsDecreaseItem(int points) {
        double beforeValue = addonSection.getJobsPoints(cropName);
        double afterValue = Math.max(beforeValue - points, VALUE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, points, "Points"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the money increase {@link ItemStack item} based on the provided money.
     *
     * @param money the money to be increased with when clicked.
     *
     * @return the money increase item.
     */
    private @NotNull ItemStack getMoneyIncreaseItem(int money) {
        double beforeValue = addonSection.getJobsMoney(cropName);
        double afterValue = Math.min(beforeValue + money, VALUE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, money, "Money"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the money decrease {@link ItemStack item} based on the provided money.
     *
     * @param money the money to be decreased with when clicked.
     *
     * @return the money decrease item.
     */
    private @NotNull ItemStack getMoneyDecreaseItem(int money) {
        double beforeValue = addonSection.getJobsMoney(cropName);
        double afterValue = Math.max(beforeValue - money, VALUE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, money, "Money"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the experience increase {@link ItemStack item} based on the provided experience.
     *
     * @param experience the experience to be increased with when clicked.
     *
     * @return the experience increase item.
     */
    private @NotNull ItemStack getExperienceIncreaseItem(int experience) {
        double beforeValue = addonSection.getJobsExperience(cropName);
        double afterValue = Math.min(beforeValue + experience, VALUE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, experience, "Experience"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the experience decrease {@link ItemStack item} based on the provided experience.
     *
     * @param experience the experience to be decreased with when clicked.
     *
     * @return the experience decrease item.
     */
    private @NotNull ItemStack getExperienceDecreaseItem(int experience) {
        double beforeValue = addonSection.getJobsExperience(cropName);
        double afterValue = Math.max(beforeValue - experience, VALUE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, experience, "Experience"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Increases the {@link JobsRebornAddon JobsReborn} points with the provided points.
     *
     * @param points the points to be increased with.
     */
    private void increasePoints(int points) {
        double oldAmount = addonSection.getJobsPoints(cropName) + points;
        double newAmount = Math.min(oldAmount, VALUE_MAX);
        addonSection.setJobsPoints(cropName, newAmount);
    }


    /**
     * Decreases the {@link JobsRebornAddon JobsReborn} points with the provided points.
     *
     * @param points the points to be decreased with.
     */
    private void decreasePoints(int points) {
        double oldAmount = addonSection.getJobsPoints(cropName) - points;
        double newAmount = Math.max(oldAmount, VALUE_MIN);
        addonSection.setJobsPoints(cropName, newAmount);
    }


    /**
     * Increases the {@link JobsRebornAddon JobsReborn} money with the provided money.
     *
     * @param money the money to be increased with.
     */
    private void increaseMoney(int money) {
        double oldAmount = addonSection.getJobsMoney(cropName) + money;
        double newAmount = Math.min(oldAmount, VALUE_MAX);
        addonSection.setJobsMoney(cropName, newAmount);
    }


    /**
     * Decreases the {@link JobsRebornAddon JobsReborn} money with the provided money.
     *
     * @param money the money to be decreased with.
     */
    private void decreaseMoney(int money) {
        double oldAmount = addonSection.getJobsMoney(cropName) - money;
        double newAmount = Math.max(oldAmount, VALUE_MIN);
        addonSection.setJobsMoney(cropName, newAmount);
    }


    /**
     * Increases the {@link JobsRebornAddon JobsReborn} experience with the provided experience.
     *
     * @param experience the experience to be increased with.
     */
    private void increaseExperience(int experience) {
        double oldAmount = addonSection.getJobsExperience(cropName) + experience;
        double newAmount = Math.min(oldAmount, VALUE_MAX);
        addonSection.setJobsExperience(cropName, newAmount);
    }


    /**
     * Decreases the {@link JobsRebornAddon JobsReborn} experience with the provided experience.
     *
     * @param experience the experience to be decreased with.
     */
    private void decreaseExperience(int experience) {
        double oldAmount = addonSection.getJobsExperience(cropName) - experience;
        double newAmount = Math.max(oldAmount, VALUE_MIN);
        addonSection.setJobsExperience(cropName, newAmount);
    }


}