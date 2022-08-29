package com.github.bakuplayz.cropclick.menu.menus.crop;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.crops.CropMenu;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MathUtil;
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

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int PERCENTAGE_MIN = 0;
    private final int PERCENTAGE_MAX = 100;
    private final double DECIMAL_TO_PERCENT = 10_000;
    private final double PERCENT_TO_DECIMAL = 0.01;


    private final Crop crop;
    private final Seed seed;
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
        double chance = MathUtil.round(
                crop.getDrop().getChance() * DECIMAL_TO_PERCENT
        );

        return new ItemBuilder(crop.getMenuType())
                .setName(LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.DROP_CHANCE_CROP_ITEM_DROP_CHANCE.get(plugin, chance)
                ))
                .setMaterial(crop.isHarvestable() ? null : Material.RED_STAINED_GLASS_PANE)
                .toItemStack();
    }


    private @NotNull ItemStack getSeedItem() {
        String name = MessageUtils.beautify(seed.getName(), false);
        String status = MessageUtils.getEnabledStatus(plugin, seed.isEnabled());
        double chance = MathUtil.round(
                seed.getDrop().getChance() * DECIMAL_TO_PERCENT
        );

        return new ItemBuilder(seed.getMenuType())
                .setName(LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.DROP_CHANCE_SEED_ITEM_DROP_CHANCE.get(plugin, chance)
                ))
                .setMaterial(seed.isEnabled() ? null : Material.RED_STAINED_GLASS_PANE)
                .toItemStack();
    }


    private @NotNull ItemStack getCropAddItem(int amount) {
        double beforeValue = MathUtil.round(
                cropSection.getDropChance(crop.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.min(beforeValue + amount, PERCENTAGE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getCropRemoveItem(int amount) {
        double beforeValue = MathUtil.round(
                cropSection.getDropChance(crop.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.max(beforeValue - amount, PERCENTAGE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getSeedAddItem(int amount) {
        double beforeValue = MathUtil.round(
                seedSection.getDropChance(seed.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.min(beforeValue + amount, PERCENTAGE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getSeedRemoveItem(int amount) {
        double beforeValue = MathUtil.round(
                seedSection.getDropChance(seed.getName()) * DECIMAL_TO_PERCENT
        );
        double afterValue = Math.max(beforeValue - amount, PERCENTAGE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.DROP_CHANCE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Increase the drop chance of a crop by a given amount, but don't let it go over 100%.
     *
     * @param name   The name of the crop.
     * @param amount The amount to increase the drop chance by.
     */
    public void increaseCropDropChance(@NotNull String name, int amount) {
        int oldChance = (int) (cropSection.getDropChance(name) * DECIMAL_TO_PERCENT + amount);
        int newChance = Math.min(oldChance, PERCENTAGE_MAX);
        cropSection.setDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Decrease the drop chance of a crop by a certain amount, but don't let it go below the minimum percentage.
     *
     * @param name   The name of the crop.
     * @param amount The amount to decrease the chance by.
     */
    public void decreaseCropDropChance(@NotNull String name, int amount) {
        int oldChance = (int) (cropSection.getDropChance(name) * DECIMAL_TO_PERCENT - amount);
        int newChance = Math.max(oldChance, PERCENTAGE_MIN);
        cropSection.setDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Increase the seed drop chance of the crop with the given name by the given amount, but don't let it go over 100%.
     *
     * @param name   The name of the crop.
     * @param amount The amount to increase the seed drop chance by.
     */
    public void increaseSeedDropAmount(@NotNull String name, int amount) {
        int oldChance = (int) (seedSection.getDropChance(name) * DECIMAL_TO_PERCENT + amount);
        int newChance = Math.min(oldChance, PERCENTAGE_MAX);
        seedSection.setDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }


    /**
     * Removes the given amount from the seed drop amount of the given seed, but never less than the percentage min.
     *
     * @param name   The name of the seed.
     * @param amount The amount to add to the current amount.
     */
    public void decreaseSeedDropAmount(@NotNull String name, int amount) {
        int oldChance = (int) (seedSection.getDropChance(name) * DECIMAL_TO_PERCENT - amount);
        int newChance = Math.max(oldChance, PERCENTAGE_MIN);
        seedSection.setDropChance(name, newChance * PERCENT_TO_DECIMAL);
    }

}