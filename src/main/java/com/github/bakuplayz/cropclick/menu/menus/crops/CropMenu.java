package com.github.bakuplayz.cropclick.menu.menus.crops;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.CocoaBean;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.templates.Seed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtil;
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
 * @since 1.6.0
 */
public final class CropMenu extends Menu {

    private final Crop crop;
    private final Seed seed;
    private final boolean hasSeed;

    private final CropsConfig cropsConfig;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;


    public CropMenu(@NotNull CropClick plugin,
                    @NotNull Player player,
                    @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.CROP_TITLE);
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

        inventory.setItem(47, getReplantItem());
        inventory.setItem(51, getAtLeastItem());

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.CROP));

        String cropName = crop.getName();

        if (clicked.equals(getReplantItem())) {
            cropsConfig.toggleReplantCrop(cropName);
        }

        if (clicked.equals(getAtLeastItem())) {
            cropsConfig.toggleDropAtLeastOne(cropName);
        }

        if (clicked.equals(getCropItem())) {
            cropsConfig.toggleCrop(cropName);
        }

        if (clicked.equals(getCropAddItem(MIN_CHANGE))) {
            cropsConfig.addCropDropAmount(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getCropAddItem(MAX_CHANGE))) {
            cropsConfig.addCropDropAmount(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getCropRemoveItem(MIN_CHANGE))) {
            cropsConfig.removeCropDropAmount(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getCropRemoveItem(MAX_CHANGE))) {
            cropsConfig.removeCropDropAmount(cropName, MAX_CHANGE);
        }

        if (hasSeed) {
            String seedName = seed.getName();

            if (clicked.equals(getSeedItem())) {
                cropsConfig.toggleSeed(seedName);
            }

            if (clicked.equals(getSeedAddItem(MIN_CHANGE))) {
                cropsConfig.addSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedAddItem(MAX_CHANGE))) {
                cropsConfig.addSeedDropAmount(seedName, MAX_CHANGE);
            }

            if (clicked.equals(getSeedRemoveItem(MIN_CHANGE))) {
                cropsConfig.removeSeedDropAmount(seedName, MIN_CHANGE);
            }

            if (clicked.equals(getSeedRemoveItem(MAX_CHANGE))) {
                cropsConfig.removeSeedDropAmount(seedName, MAX_CHANGE);
            }
        }

        updateMenu();
    }


    private @NotNull ItemStack getCropItem() {
        String name = MessageUtil.beautify(crop.getName(), false);
        String status = MessageUtil.getEnabledStatus(plugin, crop.isEnabled());
        return new ItemUtil(crop.getMenuType())
                .setName(LanguageAPI.Menu.CROP_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.CROP_ITEM_DROP_VALUE.get(plugin, crop.getDrop().getAmount()))
                .setDamage(crop instanceof CocoaBean ? 3 : -1)
                .setDamage(crop.isEnabled() ? -1 : 15)
                .setMaterial(crop.isEnabled() ? null : Material.STAINED_GLASS_PANE)
                .toItemStack();
    }


    private @NotNull ItemStack getSeedItem() {
        String name = MessageUtil.beautify(seed.getName(), false);
        String status = MessageUtil.getEnabledStatus(plugin, seed.isEnabled());
        return new ItemUtil(seed.getMenuType())
                .setName(LanguageAPI.Menu.CROP_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.CROP_SEED_ITEM_DROP_VALUE.get(plugin, seed.getDrop().getAmount()))
                .setMaterial(crop.isEnabled() ? null : Material.STAINED_GLASS_PANE)
                .setDamage(crop.isEnabled() ? -1 : 15)
                .toItemStack();
    }


    private @NotNull ItemStack getReplantItem() {
        boolean shouldReplant = cropsConfig.shouldReplantCrop(crop.getName());
        return new ItemUtil(Material.IRON_PLATE)
                .setName(plugin, LanguageAPI.Menu.CROP_REPLANT_ITEM_NAME)
                .setLore(LanguageAPI.Menu.CROP_REPLANT_ITEM_STATUS.get(plugin, shouldReplant))
                .toItemStack();
    }


    private @NotNull ItemStack getAtLeastItem() {
        boolean atLeastOne = cropsConfig.shouldDropAtLeastOne(crop.getName());
        return new ItemUtil(Material.GOLD_PLATE)
                .setName(plugin, LanguageAPI.Menu.CROP_AT_LEAST_ITEM_NAME)
                .setLore(LanguageAPI.Menu.CROP_AT_LEAST_ITEM_STATUS.get(plugin, atLeastOne))
                .toItemStack();
    }


    private @NotNull ItemStack getCropAddItem(int amount) {
        int beforeValue = cropsConfig.getCropDropAmount(crop.getName());
        int afterValue = Math.min(beforeValue + amount, 576);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_ADD_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getCropRemoveItem(int amount) {
        int beforeValue = cropsConfig.getCropDropAmount(crop.getName());
        int afterValue = Math.max(beforeValue - amount, 0);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Crop"))
                .setLore(LanguageAPI.Menu.CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    private @NotNull ItemStack getSeedAddItem(int amount) {
        int beforeValue = cropsConfig.getSeedDropAmount(seed.getName());
        int afterValue = Math.min(beforeValue + amount, 576);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_ADD_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getSeedRemoveItem(int amount) {
        int beforeValue = cropsConfig.getSeedDropAmount(seed.getName());
        int afterValue = Math.max(beforeValue - amount, 0);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Seed"))
                .setLore(LanguageAPI.Menu.CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }

}