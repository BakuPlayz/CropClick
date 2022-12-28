package com.github.bakuplayz.cropclick.menu.menus.crops;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.crop.DropChanceMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * Represents the Crop menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropMenu extends Menu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 576;

    private final BaseCrop crop;
    private final BaseSeed seed;
    private final String cropName;
    private final boolean hasSeed;

    private final CropConfigSection cropSection;
    private final SeedConfigSection seedSection;


    public CropMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull BaseCrop crop) {
        super(plugin, player, LanguageAPI.Menu.CROP_TITLE);
        this.cropSection = plugin.getCropsConfig().getCropSection();
        this.seedSection = plugin.getCropsConfig().getSeedSection();
        this.cropName = crop.getName();
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

        inventory.setItem(46, getChanceItem());
        inventory.setItem(47, getLinkableItem());
        inventory.setItem(51, getReplantItem());
        inventory.setItem(52, getAtLeastItem());

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.CROP));

        if (clicked.equals(getChanceItem())) {
            new DropChanceMenu(plugin, player, crop).open();
        }

        if (clicked.equals(getLinkableItem())) {
            cropSection.toggleLinkable(cropName);
        }

        if (clicked.equals(getReplantItem())) {
            cropSection.toggleReplant(cropName);
        }

        if (clicked.equals(getAtLeastItem())) {
            cropSection.toggleDropAtLeastOne(cropName);
        }

        if (clicked.equals(getCropItem())) {
            cropSection.toggleHarvest(cropName);
        }

        // Crop Add
        if (clicked.equals(getCropAddItem(MIN_CHANGE))) {
            addCropDropAmount(MIN_CHANGE);
        }

        if (clicked.equals(getCropAddItem(MAX_CHANGE))) {
            addCropDropAmount(MAX_CHANGE);
        }

        // Crop Remove
        if (clicked.equals(getCropRemoveItem(MIN_CHANGE))) {
            removeCropDropAmount(MIN_CHANGE);
        }

        if (clicked.equals(getCropRemoveItem(MAX_CHANGE))) {
            removeCropDropAmount(MAX_CHANGE);
        }

        if (hasSeed) {
            String seedName = seed.getName();

            if (clicked.equals(getSeedItem())) {
                seedSection.toggle(seedName);
            }

            // Seed Add
            if (clicked.equals(getSeedAddItem(MIN_CHANGE))) {
                addSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedAddItem(MAX_CHANGE))) {
                addSeedDropAmount(seedName, MAX_CHANGE);
            }

            // Seed Remove
            if (clicked.equals(getSeedRemoveItem(MIN_CHANGE))) {
                removeSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedRemoveItem(MAX_CHANGE))) {
                removeSeedDropAmount(seedName, MAX_CHANGE);
            }
        }

        refresh();
    }


    /**
     * It creates an item representing the crop item.
     *
     * @return an item representing the crop item.
     */
    private @NotNull ItemStack getCropItem() {
        String name = MessageUtils.beautify(cropName, false);
        String status = crop.isHarvestable()
                        ? LanguageAPI.Menu.CROP_STATUS_ENABLED.get(plugin)
                        : LanguageAPI.Menu.CROP_STATUS_DISABLED.get(plugin);

        return new ItemBuilder(crop.getMenuType())
                .setName(LanguageAPI.Menu.CROP_CROP_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.CROP_CROP_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.CROP_CROP_ITEM_DROP_VALUE.get(plugin, crop.getDrop().getAmount())
                ))
                .setMaterial(crop.isHarvestable() ? null : Material.GRAY_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * It creates an item representing the seed item.
     *
     * @return an item representing the seed item.
     */
    private @NotNull ItemStack getSeedItem() {
        String name = MessageUtils.beautify(seed.getName(), false);
        String status = MessageUtils.getEnabledStatus(plugin, seed.isEnabled());

        return new ItemBuilder(seed.getMenuType())
                .setName(LanguageAPI.Menu.CROP_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.CROP_SEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.CROP_SEED_ITEM_DROP_VALUE.get(plugin, seed.getDrop().getAmount())
                ))
                .setMaterial(seed.isEnabled() ? null : Material.GRAY_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * It creates an item representing the drop chances.
     *
     * @return an item representing the drop chances.
     */
    private @NotNull ItemStack getChanceItem() {
        int cropChance = getDropChanceAsPercent(true);

        ItemBuilder item = new ItemBuilder(Material.OAK_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.CROP_CHANCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.CROP_CHANCE_ITEM_CROP_STATUS.get(plugin, cropChance));

        if (hasSeed) {
            int seedChance = getDropChanceAsPercent(false);
            item.setLore(
                    LanguageAPI.Menu.CROP_CHANCE_ITEM_CROP_STATUS.get(plugin, cropChance),
                    LanguageAPI.Menu.CROP_CHANCE_ITEM_SEED_STATUS.get(plugin, seedChance)
            );
        }

        return item.toItemStack();
    }


    /**
     * It creates an item representing the linkable item.
     *
     * @return an item representing the linkable item.
     */
    private @NotNull ItemStack getLinkableItem() {
        boolean isLinkable = cropSection.isLinkable(cropName);

        return new ItemBuilder(Material.STONE_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.CROP_LINKABLE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.CROP_LINKABLE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.CROP_LINKABLE_ITEM_STATUS.get(plugin, isLinkable))
                ).toItemStack();
    }


    /**
     * It creates an item representing the "should" replant crop item.
     *
     * @return an item representing the "should" replant crop item.
     */
    private @NotNull ItemStack getReplantItem() {
        boolean shouldReplant = cropSection.shouldReplant(cropName);

        return new ItemBuilder(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.CROP_REPLANT_ITEM_NAME)
                .setLore(LanguageAPI.Menu.CROP_REPLANT_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.CROP_REPLANT_ITEM_STATUS.get(plugin, shouldReplant))
                ).toItemStack();
    }


    /**
     * It creates an item representing the at least one drop item.
     *
     * @return an item representing the at least one drop item.
     */
    private @NotNull ItemStack getAtLeastItem() {
        boolean atLeastOne = cropSection.shouldDropAtLeastOne(cropName);

        return new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.CROP_AT_LEAST_ITEM_NAME)
                .setLore(LanguageAPI.Menu.CROP_AT_LEAST_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.CROP_AT_LEAST_ITEM_STATUS.get(plugin, atLeastOne))
                ).toItemStack();
    }


    /**
     * It creates an item representing the amount of crop to add.
     *
     * @param amount the given crop to add.
     *
     * @return an item representing the amount of crop to add.
     */
    private @NotNull ItemStack getCropAddItem(int amount) {
        int beforeValue = cropSection.getDropAmount(cropName);
        int afterValue = Math.min(beforeValue + amount, MAX_VALUE);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_ADD_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of crop to remove.
     *
     * @param amount the given crop to remove.
     *
     * @return an item representing the amount of crop to remove.
     */
    private @NotNull ItemStack getCropRemoveItem(int amount) {
        int beforeValue = cropSection.getDropAmount(cropName);
        int afterValue = Math.max(beforeValue - amount, MIN_VALUE);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of seed to add.
     *
     * @param amount the given seed to add.
     *
     * @return an item representing the amount of seed to add.
     */
    private @NotNull ItemStack getSeedAddItem(int amount) {
        int beforeValue = seedSection.getDropAmount(seed.getName());
        int afterValue = Math.min(beforeValue + amount, MAX_VALUE);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_ADD_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of seed to remove.
     *
     * @param amount the given seed to remove.
     *
     * @return an item representing the amount of seed to remove.
     */
    private @NotNull ItemStack getSeedRemoveItem(int amount) {
        int beforeValue = seedSection.getDropAmount(seed.getName());
        int afterValue = Math.max(beforeValue - amount, MIN_VALUE);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Add the given amount to the current drop amount, but don't exceed the maximum value.
     *
     * @param amount The amount to add to the current drop amount.
     */
    public void addCropDropAmount(int amount) {
        int oldAmount = cropSection.getDropAmount(cropName) + amount;
        int newAmount = Math.min(oldAmount, MAX_VALUE);
        cropSection.setDropAmount(cropName, newAmount);
    }


    /**
     * Remove the given amount from the current crop drop amount, but don't go below the minimum value.
     *
     * @param amount The amount to remove from the current drop amount.
     */
    public void removeCropDropAmount(int amount) {
        int oldAmount = cropSection.getDropAmount(cropName) - amount;
        int newAmount = Math.max(oldAmount, MIN_VALUE);
        cropSection.setDropAmount(cropName, newAmount);
    }


    /**
     * Adds the given amount to the seed drop amount of the given seed, but never more than 576.
     *
     * @param seedName The name of the seed.
     * @param amount   The amount of seeds to add to the current amount.
     */
    public void addSeedDropAmount(@NotNull String seedName, int amount) {
        int oldAmount = seedSection.getDropAmount(seedName) + amount;
        int newAmount = Math.min(oldAmount, MAX_VALUE);
        seedSection.setDropAmount(seedName, newAmount);
    }


    /**
     * Removes the given amount from the seed drop amount of the given seed, but never less than zero.
     *
     * @param seedName The name of the seed.
     * @param amount   The amount to add to the current amount.
     */
    public void removeSeedDropAmount(@NotNull String seedName, int amount) {
        int oldAmount = seedSection.getDropAmount(seedName) - amount;
        int newAmount = Math.max(oldAmount, MIN_VALUE);
        seedSection.setDropAmount(seedName, newAmount);
    }


    /**
     * It returns the drop chance of the crop or seed, depending on the boolean parameter.
     *
     * @param isCrop Whether the drop is a crop or a seed.
     *
     * @return The drop chance of the crop or seed.
     */
    private int getDropChanceAsPercent(boolean isCrop) {
        double DECIMAL_TO_PERCENT = 10_000;
        if (isCrop) {
            return (int) (cropSection.getDropChance(cropName) * DECIMAL_TO_PERCENT);
        }
        return (int) (seedSection.getDropChance(seed.getName()) * DECIMAL_TO_PERCENT);
    }

}