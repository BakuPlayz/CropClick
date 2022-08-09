package com.github.bakuplayz.cropclick.menu.menus.crop;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.CocoaBean;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.crops.CropMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
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
public final class DropChanceMenu extends Menu {

    private final Crop crop;
    private final Seed seed;
    private final boolean hasSeed;

    private final CropsConfig cropsConfig;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int PERCENTAGE_MIN = 0;
    private final int PERCENTAGE_MAX = 100;
    private final double DECIMAL_TO_PERCENT = 10_000;
    private final double PERCENT_TO_DECIMAL = 0.01;


    public DropChanceMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.DROP_CHANCE_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.hasSeed = crop.hasSeed();
        this.seed = crop.getSeed();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(hasSeed ? 10 : 19, getCropRemoveItem(MAX_CHANGE));
        inventory.setItem(hasSeed ? 11 : 20, getCropRemoveItem(MIN_CHANGE));
        inventory.setItem(hasSeed ? 13 : 22, getCropItem());
        inventory.setItem(hasSeed ? 15 : 24, getCropAddItem(MIN_CHANGE));
        inventory.setItem(hasSeed ? 16 : 25, getCropAddItem(MAX_CHANGE));

        if (hasSeed) {
            inventory.setItem(28, getSeedRemoveItem(MAX_CHANGE));
            inventory.setItem(29, getSeedRemoveItem(MIN_CHANGE));
            inventory.setItem(31, getSeedItem());
            inventory.setItem(33, getSeedAddItem(MIN_CHANGE));
            inventory.setItem(34, getSeedAddItem(MAX_CHANGE));
        }

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new CropMenu(plugin, player, crop));

        String cropName = crop.getName();
        if (clicked.equals(getCropAddItem(MIN_CHANGE))) {
            increaseCropDropChance(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getCropAddItem(MAX_CHANGE))) {
            increaseCropDropChance(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getCropRemoveItem(MIN_CHANGE))) {
            decreaseCropDropChance(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getCropRemoveItem(MAX_CHANGE))) {
            decreaseCropDropChance(cropName, MAX_CHANGE);
        }

        if (hasSeed) {
            String seedName = seed.getName();

            if (clicked.equals(getSeedItem())) {
                cropsConfig.toggleSeed(seedName);
            }

            if (clicked.equals(getSeedAddItem(MIN_CHANGE))) {
                increaseSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedAddItem(MAX_CHANGE))) {
                increaseSeedDropAmount(seedName, MAX_CHANGE);
            }

            if (clicked.equals(getSeedRemoveItem(MIN_CHANGE))) {
                decreaseSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedRemoveItem(MAX_CHANGE))) {
                decreaseSeedDropAmount(seedName, MAX_CHANGE);
            }
        }

        updateMenu();
    }


    private @NotNull ItemStack getCropItem() {
        String name = MessageUtils.beautify(crop.getName(), false);
        String status = crop.isHarvestable()
                        ? LanguageAPI.Menu.DROP_CHANCE_STATUS_ENABLED.get(plugin)
                        : LanguageAPI.Menu.DROP_CHANCE_STATUS_DISABLED.get(plugin);
        int chance = (int) (crop.getDrop().getChance() * DECIMAL_TO_PERCENT);

        return new ItemUtil(crop.getMenuType())
                .setName(LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_DROP_CHANCE.get(plugin, chance))
                .setDamage(crop instanceof CocoaBean ? 3 : -1)
                .setDamage(crop.isHarvestable() ? -1 : 15)
                .setMaterial(crop.isHarvestable() ? null : Material.STAINED_GLASS_PANE)
                .toItemStack();
    }


    private @NotNull ItemStack getSeedItem() {
        String name = MessageUtils.beautify(seed.getName(), false);
        String status = MessageUtils.getEnabledStatus(plugin, seed.isEnabled());
        int chance = (int) (seed.getDrop().getChance() * DECIMAL_TO_PERCENT);

        return new ItemUtil(seed.getMenuType())
                .setName(LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_DROP_CHANCE.get(plugin, chance))
                .setMaterial(crop.isHarvestable() ? null : Material.STAINED_GLASS_PANE)
                .setDamage(crop.isHarvestable() ? -1 : 15)
                .toItemStack();
    }


    private @NotNull ItemStack getCropAddItem(int amount) {
        int beforeValue = (int) (cropsConfig.getCropDropChance(crop.getName()) * DECIMAL_TO_PERCENT);
        int afterValue = Math.min(beforeValue + amount, PERCENTAGE_MAX);

        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getCropRemoveItem(int amount) {
        int beforeValue = (int) (cropsConfig.getCropDropChance(crop.getName()) * DECIMAL_TO_PERCENT);
        int afterValue = Math.max(beforeValue - amount, PERCENTAGE_MIN);

        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    private @NotNull ItemStack getSeedAddItem(int amount) {
        int beforeValue = (int) (cropsConfig.getSeedDropChance(seed.getName()) * DECIMAL_TO_PERCENT);
        int afterValue = Math.min(beforeValue + amount, PERCENTAGE_MAX);

        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getSeedRemoveItem(int amount) {
        int beforeValue = (int) (cropsConfig.getSeedDropChance(seed.getName()) * DECIMAL_TO_PERCENT);
        int afterValue = Math.max(beforeValue - amount, PERCENTAGE_MIN);

        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    /**
     * Increase the drop chance of a crop by a given amount, but don't let it go over 100%.
     *
     * @param name   The name of the crop.
     * @param amount The amount to increase the drop chance by.
     */
    public void increaseCropDropChance(@NotNull String name, int amount) {
        int oldChance = (int) (cropsConfig.getCropDropChance(name) * DECIMAL_TO_PERCENT + amount);
        int newChance = Math.min(oldChance, PERCENTAGE_MAX);
        cropsConfig.setCropDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Decrease the drop chance of a crop by a certain amount, but don't let it go below the minimum percentage.
     *
     * @param name   The name of the crop.
     * @param amount The amount to decrease the chance by.
     */
    public void decreaseCropDropChance(@NotNull String name, int amount) {
        int oldChance = (int) (cropsConfig.getCropDropChance(name) * DECIMAL_TO_PERCENT - amount);
        int newChance = Math.max(oldChance, PERCENTAGE_MIN);
        cropsConfig.setCropDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Increase the seed drop chance of the crop with the given name by the given amount, but don't let it go over 100%.
     *
     * @param name   The name of the crop.
     * @param amount The amount to increase the seed drop chance by.
     */
    public void increaseSeedDropAmount(@NotNull String name, int amount) {
        int oldChance = (int) (cropsConfig.getSeedDropChance(name) * DECIMAL_TO_PERCENT + amount);
        int newChance = Math.min(oldChance, PERCENTAGE_MAX);
        cropsConfig.setSeedDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Removes the given amount from the seed drop amount of the given seed, but never less than the percentage min.
     *
     * @param name   The name of the seed.
     * @param amount The amount to add to the current amount.
     */
    public void decreaseSeedDropAmount(@NotNull String name, int amount) {
        int oldChance = (int) (cropsConfig.getSeedDropChance(name) * DECIMAL_TO_PERCENT - amount);
        int newChance = Math.max(oldChance, PERCENTAGE_MIN);
        cropsConfig.setSeedDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }

}