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
import com.github.bakuplayz.cropclick.addons.addon.AureliumAddon;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.McMMOMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the mcMMO Crop menu.
 *
 * @author BakuPlayz
 * @version 2.0.2
 * @see CropsMenu
 * @see McMMOMenu
 * @see BaseMenu
 * @since 2.0.2
 */
public final class AureliumCropMenu extends BaseMenu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int EXPERIENCE_MIN = 0;
    private final int EXPERIENCE_MAX = 10_000;


    private final String cropName;
    private final AddonConfigSection addonSection;


    public AureliumCropMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.AURELIUM_CROP_TITLE);
        this.addonSection = plugin.getCropsConfig().getAddonSection();
        this.cropName = crop.getName();
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(19, getExperienceDecreaseItem(MAX_CHANGE));
        inventory.setItem(20, getExperienceDecreaseItem(MIN_CHANGE));
        inventory.setItem(22, getExperienceItem());
        inventory.setItem(24, getExperienceIncreaseItem(MIN_CHANGE));
        inventory.setItem(25, getExperienceIncreaseItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.AURELIUM));

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
     * Gets the experience {@link ItemStack item}.
     *
     * @return the experience item.
     */
    private @NotNull ItemStack getExperienceItem() {
        double experience = addonSection.getAureliumExperience(cropName);

        return new ItemBuilder(Material.EXPERIENCE_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.AURELIUM_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.AURELIUM_CROP_EXPERIENCE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.AURELIUM_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience)
                ))
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
        double beforeValue = addonSection.getAureliumExperience(cropName);
        double afterValue = Math.min(beforeValue + experience, EXPERIENCE_MAX);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.AURELIUM_CROP_ADD_ITEM_NAME.get(plugin, experience, "Experience"))
                .setLore(LanguageAPI.Menu.AURELIUM_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
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
        double beforeValue = addonSection.getAureliumExperience(cropName);
        double afterValue = Math.max(beforeValue - experience, EXPERIENCE_MIN);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.AURELIUM_CROP_REMOVE_ITEM_NAME.get(plugin, experience, "Experience"))
                .setLore(LanguageAPI.Menu.AURELIUM_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Increases the {@link AureliumAddon Aurelium} experience with the provided experience.
     *
     * @param experience the experience to be increased with.
     */
    private void increaseExperience(int experience) {
        double oldAmount = addonSection.getAureliumExperience(cropName) + experience;
        double newAmount = Math.min(oldAmount, EXPERIENCE_MAX);
        addonSection.setAureliumExperience(cropName, newAmount);
    }


    /**
     * Decreases the {@link AureliumAddon Aurelium} experience with the provided experience.
     *
     * @param experience the experience to be decreased with.
     */
    private void decreaseExperience(int experience) {
        double oldAmount = addonSection.getAureliumExperience(cropName) - experience;
        double newAmount = Math.max(oldAmount, EXPERIENCE_MIN);
        addonSection.setAureliumExperience(cropName, newAmount);
    }

}