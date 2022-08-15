package com.github.bakuplayz.cropclick.menu.menus.addons.additional;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.addons.JobsRebornMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
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
 * @see CropsMenu
 * @see JobsRebornMenu
 * @see Menu
 * @since 2.0.0
 */
public final class JobsCropMenu extends Menu {

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
        inventory.setItem(10, getMoneyRemoveItem(MAX_CHANGE));
        inventory.setItem(11, getMoneyRemoveItem(MIN_CHANGE));
        inventory.setItem(13, getMoneyItem());
        inventory.setItem(15, getMoneyAddItem(MIN_CHANGE));
        inventory.setItem(16, getMoneyAddItem(MAX_CHANGE));

        inventory.setItem(19, getPointsRemoveItem(MAX_CHANGE));
        inventory.setItem(20, getPointsRemoveItem(MIN_CHANGE));
        inventory.setItem(22, getPointsItem());
        inventory.setItem(24, getPointsAddItem(MIN_CHANGE));
        inventory.setItem(25, getPointsAddItem(MAX_CHANGE));

        inventory.setItem(28, getExperienceRemoveItem(MAX_CHANGE));
        inventory.setItem(29, getExperienceRemoveItem(MIN_CHANGE));
        inventory.setItem(31, getExperienceItem());
        inventory.setItem(33, getExperienceAddItem(MIN_CHANGE));
        inventory.setItem(34, getExperienceAddItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.JOBS_REBORN));

        // Points
        if (clicked.equals(getPointsAddItem(MIN_CHANGE))) {
            addJobsPoints(MIN_CHANGE);
        }

        if (clicked.equals(getPointsAddItem(MAX_CHANGE))) {
            addJobsPoints(MAX_CHANGE);
        }

        if (clicked.equals(getPointsRemoveItem(MIN_CHANGE))) {
            removeJobsPoints(MIN_CHANGE);
        }

        if (clicked.equals(getPointsRemoveItem(MAX_CHANGE))) {
            removeJobsPoints(MAX_CHANGE);
        }

        // Money
        if (clicked.equals(getMoneyAddItem(MIN_CHANGE))) {
            addJobsMoney(MIN_CHANGE);
        }

        if (clicked.equals(getMoneyAddItem(MAX_CHANGE))) {
            addJobsMoney(MAX_CHANGE);
        }

        if (clicked.equals(getMoneyRemoveItem(MIN_CHANGE))) {
            removeJobsMoney(MIN_CHANGE);
        }

        if (clicked.equals(getMoneyRemoveItem(MAX_CHANGE))) {
            removeJobsMoney(MAX_CHANGE);
        }

        // Experience
        if (clicked.equals(getExperienceAddItem(MIN_CHANGE))) {
            addJobsExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceAddItem(MAX_CHANGE))) {
            addJobsExperience(MAX_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MIN_CHANGE))) {
            removeJobsExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MAX_CHANGE))) {
            removeJobsExperience(MAX_CHANGE);
        }

        updateMenu();
    }


    private @NotNull ItemStack getPointsItem() {
        double points = addonSection.getJobsPoints(cropName);
        return new ItemUtil(Material.GOLD_NUGGET)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_POINTS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_POINTS_ITEM_VALUE.get(plugin, points))
                .toItemStack();
    }


    private @NotNull ItemStack getMoneyItem() {
        double money = addonSection.getJobsMoney(cropName);
        return new ItemUtil(Material.GOLD_INGOT)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_MONEY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_MONEY_ITEM_VALUE.get(plugin, money))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceItem() {
        double experience = addonSection.getJobsExperience(cropName);
        return new ItemUtil(Material.EXP_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience))
                .toItemStack();
    }


    private @NotNull ItemStack getPointsAddItem(int amount) {
        double beforeValue = addonSection.getJobsPoints(cropName);
        double afterValue = Math.min(beforeValue + amount, VALUE_MAX);
        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, amount, "Points"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getPointsRemoveItem(int amount) {
        double beforeValue = addonSection.getJobsPoints(cropName);
        double afterValue = Math.max(beforeValue - amount, VALUE_MIN);
        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Points"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getMoneyAddItem(int amount) {
        double beforeValue = addonSection.getJobsMoney(cropName);
        double afterValue = Math.min(beforeValue + amount, VALUE_MAX);
        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, amount, "Money"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getMoneyRemoveItem(int amount) {
        double beforeValue = addonSection.getJobsMoney(cropName);
        double afterValue = Math.max(beforeValue - amount, VALUE_MIN);
        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Money"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceAddItem(int amount) {
        double beforeValue = addonSection.getJobsExperience(cropName);
        double afterValue = Math.min(beforeValue + amount, VALUE_MAX);
        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceRemoveItem(int amount) {
        double beforeValue = addonSection.getJobsExperience(cropName);
        double afterValue = Math.max(beforeValue - amount, VALUE_MIN);
        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Adds the specified amount of points to the crop.
     *
     * @param amount The amount of points to add.
     */
    public void addJobsPoints(int amount) {
        double oldAmount = addonSection.getJobsPoints(cropName) + amount;
        double newAmount = Math.min(oldAmount, VALUE_MAX);
        addonSection.setJobsPoints(cropName, newAmount);
    }


    /**
     * Removes the specified amount of points from the specified player.
     *
     * @param amount The amount of points to remove.
     */
    public void removeJobsPoints(int amount) {
        double oldAmount = addonSection.getJobsPoints(cropName) - amount;
        double newAmount = Math.max(oldAmount, VALUE_MIN);
        addonSection.setJobsPoints(cropName, newAmount);
    }


    /**
     * Adds the specified amount of money to the crop.
     *
     * @param amount The amount of money to add to the specified crop.
     */
    public void addJobsMoney(int amount) {
        double oldAmount = addonSection.getJobsMoney(cropName) + amount;
        double newAmount = Math.min(oldAmount, VALUE_MAX);
        addonSection.setJobsMoney(cropName, newAmount);
    }


    /**
     * Remove money from a player's Jobs account.
     *
     * @param amount The amount of money to remove from the player's balance.
     */
    public void removeJobsMoney(int amount) {
        double oldAmount = addonSection.getJobsMoney(cropName) - amount;
        double newAmount = Math.max(oldAmount, VALUE_MIN);
        addonSection.setJobsMoney(cropName, newAmount);
    }


    /**
     * Adds the given amount of experience to the given job, but caps it at 10,000.
     *
     * @param amount The amount of experience to add.
     */
    public void addJobsExperience(int amount) {
        double oldAmount = addonSection.getJobsExperience(cropName) + amount;
        double newAmount = Math.min(oldAmount, VALUE_MAX);
        addonSection.setJobsExperience(cropName, newAmount);
    }


    /**
     * Removes the specified amount of experience from the specified job.
     *
     * @param amount The amount of experience to remove.
     */
    public void removeJobsExperience(int amount) {
        double oldAmount = addonSection.getJobsExperience(cropName) - amount;
        double newAmount = Math.max(oldAmount, VALUE_MIN);
        addonSection.setJobsExperience(cropName, newAmount);
    }


}