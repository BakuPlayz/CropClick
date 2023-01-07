package com.github.bakuplayz.cropclick.menu.menus.crop;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.crops.CropMenu;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the Drop Chance menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class DropChanceMenu extends BaseMenu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int PERCENTAGE_MIN = 0;
    private final int PERCENTAGE_MAX = 100;
    private final double PERCENT_TO_DECIMAL = 0.01;
    private final double DECIMAL_TO_PERCENT = 10_000;


    private final Crop crop;
    private final BaseSeed seed;
    private final boolean hasSeed;

    private final CropConfigSection cropSection;
    private final SeedConfigSection seedSection;


    public DropChanceMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.DROP_CHANCE_TITLE);
        this.cropSection = plugin.getCropsConfig().getCropSection();
        this.seedSection = plugin.getCropsConfig().getSeedSection();
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

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropMenu(plugin, player, crop));

        String cropName = crop.getName();
        if (clicked.equals(getCropIncreaseItem(MIN_CHANGE))) {
            increaseCropDropChance(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getCropIncreaseItem(MAX_CHANGE))) {
            increaseCropDropChance(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getCropDecreaseItem(MIN_CHANGE))) {
            decreaseCropDropChance(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getCropDecreaseItem(MAX_CHANGE))) {
            decreaseCropDropChance(cropName, MAX_CHANGE);
        }

        if (hasSeed) {
            String seedName = seed.getName();

            if (clicked.equals(getSeedIncreaseItem(MIN_CHANGE))) {
                increaseSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedIncreaseItem(MAX_CHANGE))) {
                increaseSeedDropAmount(seedName, MAX_CHANGE);
            }

            if (clicked.equals(getSeedDecreaseItem(MIN_CHANGE))) {
                decreaseSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedDecreaseItem(MAX_CHANGE))) {
                decreaseSeedDropAmount(seedName, MAX_CHANGE);
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
        String name = MessageUtils.beautify(crop.getName(), false);
        String status = crop.isHarvestable()
                        ? LanguageAPI.Menu.DROP_CHANCE_STATUS_ENABLED.get(plugin)
                        : LanguageAPI.Menu.DROP_CHANCE_STATUS_DISABLED.get(plugin);
        double chance = MathUtils.round(
                crop.getDrop().getChance() * DECIMAL_TO_PERCENT
        );

        return new ItemBuilder(crop.getMenuType())
                .setName(LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_DROP_CHANCE.get(plugin, chance)
                ))
                .setMaterial(!crop.isHarvestable(), Material.RED_STAINED_GLASS_PANE)
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
        double chance = MathUtils.round(
                seed.getDrop().getChance() * DECIMAL_TO_PERCENT
        );

        return new ItemBuilder(seed.getMenuType())
                .setName(LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_DROP_CHANCE.get(plugin, chance)
                ))
                .setMaterial(!seed.isEnabled(), Material.RED_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the crop increase chance {@link ItemStack item} based on the provided chance.
     *
     * @param chance the chance to be increased with when clicked.
     *
     * @return the crop increase chance item.
     */
    private @NotNull ItemStack getCropIncreaseItem(int chance) {
        double beforeValue = MathUtils.round(
                cropSection.getDropChance(crop.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.min(beforeValue + chance, PERCENTAGE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_NAME.get(plugin, chance, "Crop"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the crop decrease chance {@link ItemStack item} based on the provided chance.
     *
     * @param chance the chance to be decreased with when clicked.
     *
     * @return the crop decrease chance item.
     */
    private @NotNull ItemStack getCropDecreaseItem(int chance) {
        double beforeValue = MathUtils.round(
                cropSection.getDropChance(crop.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.max(beforeValue - chance, PERCENTAGE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, chance, "Crop"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the seed increase chance {@link ItemStack item} based on the provided chance.
     *
     * @param chance the chance to be increased with when clicked.
     *
     * @return the seed increase chance item.
     */
    private @NotNull ItemStack getSeedIncreaseItem(int chance) {
        double beforeValue = MathUtils.round(
                seedSection.getDropChance(seed.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.min(beforeValue + chance, PERCENTAGE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_NAME.get(plugin, chance, "Seed"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the seed decrease chance {@link ItemStack item} based on the provided chance.
     *
     * @param chance the chance to be decreased with when clicked.
     *
     * @return the seed decrease chance item.
     */
    private @NotNull ItemStack getSeedDecreaseItem(int chance) {
        double beforeValue = MathUtils.round(
                seedSection.getDropChance(seed.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.max(beforeValue - chance, PERCENTAGE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, chance, "Seed"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Increases the {@link #crop current crop's} drop chance with the provided amount.
     *
     * @param cropName The name of the crop.
     * @param amount   the amount to be increased with.
     */
    private void increaseCropDropChance(@NotNull String cropName, int amount) {
        int oldChance = (int) (cropSection.getDropChance(cropName) * DECIMAL_TO_PERCENT + amount);
        int newChance = Math.min(oldChance, PERCENTAGE_MAX);
        cropSection.setDropChance(cropName, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Decreases the {@link #crop current crop's} drop chance with the provided amount.
     *
     * @param cropName The name of the crop.
     * @param amount   the amount to be decreased with.
     */
    private void decreaseCropDropChance(@NotNull String cropName, int amount) {
        int oldChance = (int) (cropSection.getDropChance(cropName) * DECIMAL_TO_PERCENT - amount);
        int newChance = Math.max(oldChance, PERCENTAGE_MIN);
        cropSection.setDropChance(cropName, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Increases the {@link #seed current seed's} drop chance with the provided amount.
     *
     * @param seedName The name of the seed.
     * @param amount   the amount to be increased with.
     */
    private void increaseSeedDropAmount(@NotNull String seedName, int amount) {
        int oldChance = (int) (seedSection.getDropChance(seedName) * DECIMAL_TO_PERCENT + amount);
        int newChance = Math.min(oldChance, PERCENTAGE_MAX);
        seedSection.setDropChance(seedName, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Decreases the {@link #seed current seed's} drop chance with the provided amount.
     *
     * @param seedName The name of the seed.
     * @param amount   the amount to be decreased with.
     */
    private void decreaseSeedDropAmount(@NotNull String seedName, int amount) {
        int oldChance = (int) (seedSection.getDropChance(seedName) * DECIMAL_TO_PERCENT - amount);
        int newChance = Math.max(oldChance, PERCENTAGE_MIN);
        seedSection.setDropChance(seedName, newChance * PERCENT_TO_DECIMAL);
    }

}