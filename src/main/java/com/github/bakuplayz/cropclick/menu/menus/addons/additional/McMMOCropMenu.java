package com.github.bakuplayz.cropclick.menu.menus.addons.additional;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.addons.McMMOMenu;
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
 * @see McMMOMenu
 * @see Menu
 * @since 1.6.0
 */
public final class McMMOCropMenu extends Menu {

    private final Crop crop;

    private final CropsConfig cropsConfig;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;


    public McMMOCropMenu(@NotNull CropClick plugin,
                         @NotNull Player player,
                         @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.MCMMO_CROP_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(19, getExperienceRemoveItem(MAX_CHANGE));
        inventory.setItem(20, getExperienceRemoveItem(MIN_CHANGE));
        inventory.setItem(22, getExperienceItem());
        inventory.setItem(24, getExperienceAddItem(MIN_CHANGE));
        inventory.setItem(25, getExperienceAddItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.MCMMO));

        String cropName = crop.getName();
        if (clicked.equals(getExperienceAddItem(MIN_CHANGE))) {
            cropsConfig.addMcMMOExperience(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getExperienceAddItem(MAX_CHANGE))) {
            cropsConfig.addMcMMOExperience(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MIN_CHANGE))) {
            cropsConfig.removeMcMMOExperience(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MAX_CHANGE))) {
            cropsConfig.removeMcMMOExperience(cropName, MAX_CHANGE);
        }

        updateMenu();
    }


    @NotNull
    private ItemStack getExperienceItem() {
        double experience = cropsConfig.getMcMMOExperience(crop.getName());
        return new ItemUtil(Material.EXP_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience))
                .toItemStack();
    }


    @NotNull
    private ItemStack getExperienceAddItem(int amount) {
        double beforeValue = cropsConfig.getMcMMOExperience(crop.getName());
        double afterValue = Math.min(beforeValue + amount, 10_000);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ADD_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    @NotNull
    private ItemStack getExperienceRemoveItem(int amount) {
        double beforeValue = cropsConfig.getMcMMOExperience(crop.getName());
        double afterValue = Math.max(beforeValue - amount, 0);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REMOVE_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }

}