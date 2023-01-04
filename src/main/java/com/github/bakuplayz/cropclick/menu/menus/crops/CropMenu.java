package com.github.bakuplayz.cropclick.menu.menus.crops;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
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
 * A class representing the Crop menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropMenu extends BaseMenu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int MIN_VALUE = 0;
    private final int MAX_VALUE = 576;


    private final Crop crop;
    private final Seed seed;
    private final String cropName;
    private final boolean hasSeed;

    private final CropConfigSection cropSection;
    private final SeedConfigSection seedSection;


    public CropMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
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
        inventory.setItem(hasSeed ? 10 : 19, getCropDecreaseItem(MAX_CHANGE));
        inventory.setItem(hasSeed ? 11 : 20, getCropDecreaseItem(MIN_CHANGE));
        inventory.setItem(hasSeed ? 13 : 22, getCropItem());
        inventory.setItem(hasSeed ? 15 : 24, getCropIncreaseItem(MIN_CHANGE));
        inventory.setItem(hasSeed ? 16 : 25, getCropIncreaseItem(MAX_CHANGE));

        if (hasSeed) {
            inventory.setItem(28, getSeedDecreaseItem(MAX_CHANGE));
            inventory.setItem(29, getSeedDecreaseItem(MIN_CHANGE));
            inventory.setItem(31, getSeedItem());
            inventory.setItem(33, getSeedIncreaseItem(MIN_CHANGE));
            inventory.setItem(34, getSeedIncreaseItem(MAX_CHANGE));
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
            new DropChanceMenu(plugin, player, crop).openMenu();
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
        if (clicked.equals(getCropIncreaseItem(MIN_CHANGE))) {
            increaseCropDropAmount(MIN_CHANGE);
        }

        if (clicked.equals(getCropIncreaseItem(MAX_CHANGE))) {
            increaseCropDropAmount(MAX_CHANGE);
        }

        // Crop Remove
        if (clicked.equals(getCropDecreaseItem(MIN_CHANGE))) {
            decreaseCropDropAmount(MIN_CHANGE);
        }

        if (clicked.equals(getCropDecreaseItem(MAX_CHANGE))) {
            decreaseCropDropAmount(MAX_CHANGE);
        }

        if (hasSeed) {
            if (clicked.equals(getSeedItem())) {
                seedSection.toggle(seed.getName());
            }

            // Seed Add
            if (clicked.equals(getSeedIncreaseItem(MIN_CHANGE))) {
                increaseSeedDropAmount(MIN_CHANGE);
            }

            if (clicked.equals(getSeedIncreaseItem(MAX_CHANGE))) {
                increaseSeedDropAmount(MAX_CHANGE);
            }

            // Seed Remove
            if (clicked.equals(getSeedDecreaseItem(MIN_CHANGE))) {
                decreaseSeedDropAmount(MIN_CHANGE);
            }

            if (clicked.equals(getSeedDecreaseItem(MAX_CHANGE))) {
                decreaseSeedDropAmount(MAX_CHANGE);
            }
        }

        refreshMenu();
    }


    /**
     * Gets the crop {@link ItemStack item}.
     *
     * @return the crop item.
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
                .setMaterial(!crop.isHarvestable(), Material.GRAY_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the seed {@link ItemStack item}.
     *
     * @return the seed item.
     */
    private @NotNull ItemStack getSeedItem() {
        String name = MessageUtils.beautify(seed.getName(), false);
        String status = MessageUtils.getStatusMessage(plugin, seed.isEnabled());

        return new ItemBuilder(seed.getMenuType())
                .setName(LanguageAPI.Menu.CROP_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.CROP_SEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.CROP_SEED_ITEM_DROP_VALUE.get(plugin, seed.getDrop().getAmount())
                ))
                .setMaterial(!seed.isEnabled(), Material.GRAY_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the chance {@link ItemStack item}.
     *
     * @return the chance item.
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
     * Gets the linkable {@link ItemStack item}.
     *
     * @return the linkable item.
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
     * Gets the replant {@link ItemStack item}.
     *
     * @return the replant item.
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
     * Gets the at-least-one {@link ItemStack item}.
     *
     * @return the at-least-one item.
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
     * Gets the crop increase {@link ItemStack item} based on the provided amount.
     *
     * @param amount the amount to be added when clicked.
     *
     * @return the crop increase amount item.
     */
    private @NotNull ItemStack getCropIncreaseItem(int amount) {
        int beforeValue = cropSection.getDropAmount(cropName);
        int afterValue = Math.min(beforeValue + amount, MAX_VALUE);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_ADD_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the crop decrease {@link ItemStack item} based on the provided amount.
     *
     * @param amount the amount to be removed when clicked.
     *
     * @return the crop decrease amount item.
     */
    private @NotNull ItemStack getCropDecreaseItem(int amount) {
        int beforeValue = cropSection.getDropAmount(cropName);
        int afterValue = Math.max(beforeValue - amount, MIN_VALUE);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the seed increase {@link ItemStack item} based on the provided amount.
     *
     * @param amount the amount to be added when clicked.
     *
     * @return the seed increase amount item.
     */
    private @NotNull ItemStack getSeedIncreaseItem(int amount) {
        int beforeValue = seedSection.getDropAmount(seed.getName());
        int afterValue = Math.min(beforeValue + amount, MAX_VALUE);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_ADD_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the seed decrease {@link ItemStack item} based on the provided amount.
     *
     * @param amount the amount to be decreased with when clicked.
     *
     * @return the seed decrease amount item.
     */
    private @NotNull ItemStack getSeedDecreaseItem(int amount) {
        int beforeValue = seedSection.getDropAmount(seed.getName());
        int afterValue = Math.max(beforeValue - amount, MIN_VALUE);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Increases the {@link #crop current crop's} amount with the provided amount.
     *
     * @param amount the amount to be increased with.
     */
    private void increaseCropDropAmount(int amount) {
        int oldAmount = cropSection.getDropAmount(cropName) + amount;
        int newAmount = Math.min(oldAmount, MAX_VALUE);
        cropSection.setDropAmount(cropName, newAmount);
    }


    /**
     * Decreases the {@link #crop current crop's} amount with the provided amount.
     *
     * @param amount the amount to be decreased with.
     */
    private void decreaseCropDropAmount(int amount) {
        int oldAmount = cropSection.getDropAmount(cropName) - amount;
        int newAmount = Math.max(oldAmount, MIN_VALUE);
        cropSection.setDropAmount(cropName, newAmount);
    }


    /**
     * Increases the {@link #seed current seed's} amount with the provided amount.
     *
     * @param amount the amount to be increased with.
     */
    private void increaseSeedDropAmount(int amount) {
        String seedName = seed.getName();
        int oldAmount = seedSection.getDropAmount(seedName) + amount;
        int newAmount = Math.min(oldAmount, MAX_VALUE);
        seedSection.setDropAmount(seedName, newAmount);
    }


    /**
     * Decreases the {@link #seed current seed's} amount with the provided amount.
     *
     * @param amount the amount to be decreased with.
     */
    private void decreaseSeedDropAmount(int amount) {
        String seedName = seed.getName();
        int oldAmount = seedSection.getDropAmount(seedName) - amount;
        int newAmount = Math.max(oldAmount, MIN_VALUE);
        seedSection.setDropAmount(seedName, newAmount);
    }


    /**
     * Gets the {@link Drop#getChance() drop chance} as percent.
     *
     * @param isCrop true if a crop, otherwise false.
     *
     * @return the drop chance as percent.
     */
    private int getDropChanceAsPercent(boolean isCrop) {
        double DECIMAL_TO_PERCENT = 10_000;
        if (isCrop) {
            return (int) (cropSection.getDropChance(cropName) * DECIMAL_TO_PERCENT);
        }
        return (int) (seedSection.getDropChance(seed.getName()) * DECIMAL_TO_PERCENT);
    }

}