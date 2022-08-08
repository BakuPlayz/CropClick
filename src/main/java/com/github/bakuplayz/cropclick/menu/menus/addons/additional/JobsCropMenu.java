package com.github.bakuplayz.cropclick.menu.menus.addons.additional;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
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
 * @version 1.6.0
 * @see CropsMenu
 * @see JobsRebornMenu
 * @see Menu
 * @since 1.6.0
 */
public final class JobsCropMenu extends Menu {

    private final Crop crop;

    private final CropsConfig cropsConfig;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;


    public JobsCropMenu(@NotNull CropClick plugin, Player player, Crop crop) {
        super(plugin, player, LanguageAPI.Menu.JOBS_CROP_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.crop = crop;
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

        String cropName = crop.getName();
        if (clicked.equals(getPointsAddItem(MIN_CHANGE))) {
            addJobsPoints(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getPointsAddItem(MAX_CHANGE))) {
            addJobsPoints(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getPointsRemoveItem(MIN_CHANGE))) {
            removeJobsPoints(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getPointsRemoveItem(MAX_CHANGE))) {
            removeJobsPoints(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getMoneyAddItem(MIN_CHANGE))) {
            addJobsMoney(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getMoneyAddItem(MAX_CHANGE))) {
            addJobsMoney(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getMoneyRemoveItem(MIN_CHANGE))) {
            removeJobsMoney(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getMoneyRemoveItem(MAX_CHANGE))) {
            removeJobsMoney(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getExperienceAddItem(MIN_CHANGE))) {
            addJobsExperience(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getExperienceAddItem(MAX_CHANGE))) {
            addJobsExperience(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MIN_CHANGE))) {
            removeJobsExperience(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MAX_CHANGE))) {
            removeJobsExperience(cropName, MAX_CHANGE);
        }

        updateMenu();
    }


    private @NotNull ItemStack getPointsItem() {
        double points = cropsConfig.getJobsPoints(crop.getName());
        return new ItemUtil(Material.GOLD_NUGGET)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_POINTS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_POINTS_ITEM_VALUE.get(plugin, points))
                .toItemStack();
    }


    private @NotNull ItemStack getMoneyItem() {
        double money = cropsConfig.getJobsMoney(crop.getName());
        return new ItemUtil(Material.GOLD_INGOT)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_MONEY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_MONEY_ITEM_VALUE.get(plugin, money))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceItem() {
        double experience = cropsConfig.getJobsExperience(crop.getName());
        return new ItemUtil(Material.EXP_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.JOBS_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.JOBS_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience))
                .toItemStack();
    }


    private @NotNull ItemStack getPointsAddItem(int amount) {
        double beforeValue = cropsConfig.getJobsPoints(crop.getName());
        double afterValue = Math.min(beforeValue + amount, 10_000);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, amount, "Points"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getPointsRemoveItem(int amount) {
        double beforeValue = cropsConfig.getJobsPoints(crop.getName());
        double afterValue = Math.max(beforeValue - amount, 0);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Points"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    private @NotNull ItemStack getMoneyAddItem(int amount) {
        double beforeValue = cropsConfig.getJobsMoney(crop.getName());
        double afterValue = Math.min(beforeValue + amount, 10_000);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, amount, "Money"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getMoneyRemoveItem(int amount) {
        double beforeValue = cropsConfig.getJobsMoney(crop.getName());
        double afterValue = Math.max(beforeValue - amount, 0);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Money"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceAddItem(int amount) {
        double beforeValue = cropsConfig.getJobsExperience(crop.getName());
        double afterValue = Math.min(beforeValue + amount, 10_000);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceRemoveItem(int amount) {
        double beforeValue = cropsConfig.getJobsExperience(crop.getName());
        double afterValue = Math.max(beforeValue - amount, 0);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.JOBS_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    /**
     * Adds the specified amount of points to the crop.
     *
     * @param name   The name of the crop.
     * @param amount The amount of points to add.
     */
    public void addJobsPoints(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getJobsPoints(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        cropsConfig.setJobsPoints(name, newAmount);
    }


    /**
     * Removes the specified amount of points from the specified player.
     *
     * @param name   The name of the player.
     * @param amount The amount of points to remove.
     */
    public void removeJobsPoints(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getJobsPoints(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        cropsConfig.setJobsPoints(name, newAmount);
    }


    /**
     * Adds the specified amount of money to the crop.
     *
     * @param name   The name of the crop.
     * @param amount The amount of money to add to the specified crop.
     */
    public void addJobsMoney(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getJobsMoney(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        cropsConfig.setJobsMoney(name, newAmount);
    }


    /**
     * Remove money from a player's Jobs account.
     *
     * @param name   The name of the player
     * @param amount The amount of money to remove from the player's balance.
     */
    public void removeJobsMoney(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getJobsMoney(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        cropsConfig.setJobsMoney(name, newAmount);
    }


    /**
     * Adds the given amount of experience to the given job, but caps it at 10,000.
     *
     * @param name   The name of the job.
     * @param amount The amount of experience to add.
     */
    public void addJobsExperience(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getJobsExperience(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        cropsConfig.setJobsExperience(name, newAmount);
    }


    /**
     * Removes the specified amount of experience from the specified job.
     *
     * @param name   The name of the job you want to remove experience from.
     * @param amount The amount of experience to remove.
     */
    public void removeJobsExperience(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getJobsExperience(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        cropsConfig.setJobsExperience(name, newAmount);
    }


}