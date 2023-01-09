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

package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.JobsRebornAddon;
import com.github.bakuplayz.cropclick.addons.addon.McMMOAddon;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.JobsRebornMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.McMMOMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.additional.JobsCropMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.additional.McMMOCropMenu;
import com.github.bakuplayz.cropclick.menu.menus.crops.CropMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.NameMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ParticlesMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.SoundsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;
import java.util.stream.Collectors;


/**
 * A class representing the Crops menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class CropsMenu extends PaginatedMenu {

    /**
     * A variable containing all the registered {@link Crop crops}.
     */
    private final List<Crop> crops;

    /**
     * A variable containing the state or menu to return to when clicking the {@link #getBackItem() back item}.
     */
    private final CropMenuState menuState;

    private final CropConfigSection cropSection;
    private final AddonConfigSection addonSection;
    private final SoundConfigSection soundSection;
    private final ParticleConfigSection particleSection;


    public CropsMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull CropMenuState state) {
        super(plugin, player, LanguageAPI.Menu.CROPS_TITLE);
        this.particleSection = plugin.getCropsConfig().getParticleSection();
        this.soundSection = plugin.getCropsConfig().getSoundSection();
        this.addonSection = plugin.getCropsConfig().getAddonSection();
        this.cropSection = plugin.getCropsConfig().getCropSection();
        this.crops = plugin.getCropManager().getCrops();
        this.menuState = state;
    }


    @Override
    public void setMenuItems() {
        this.menuItems = getMenuItems();

        setPaginatedItems();
        setPageItems();
        setBackItems();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        switch (menuState) {
            case CROP:
                handleBack(clicked, new MainMenu(plugin, player));
                break;

            case NAME:
            case SOUNDS:
            case PARTICLES:
                handleBack(clicked, new SettingsMenu(plugin, player, true));
                break;

            case MCMMO:
                handleBack(clicked, new McMMOMenu(plugin, player));
                break;

            case JOBS_REBORN:
                handleBack(clicked, new JobsRebornMenu(plugin, player));
                break;
        }

        handlePagination(clicked);

        int index = indexOfCrop(clicked);
        if (index == -1) {
            return;
        }

        Crop crop = crops.get(index);
        switch (menuState) {
            case CROP:
                new CropMenu(plugin, player, crop).openMenu();
                break;

            case SOUNDS:
                new SoundsMenu(plugin, player, crop).openMenu();
                break;

            case PARTICLES:
                new ParticlesMenu(plugin, player, crop).openMenu();
                break;

            case NAME:
                new NameMenu(plugin, player, crop).openMenu();
                break;

            case JOBS_REBORN:
                new JobsCropMenu(plugin, player, crop).openMenu();
                break;

            case MCMMO:
                new McMMOCropMenu(plugin, player, crop).openMenu();
                break;
        }
    }


    /**
     * Finds the index of the {@link Crop crop} based on the {@link ItemStack clicked item}.
     *
     * @param clicked the item that was clicked.
     *
     * @return the index of the crop, otherwise -1.
     */
    private int indexOfCrop(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * Creates a menu {@link ItemStack item} based on the {@link Crop provided crop}.
     *
     * @param crop the crop to base the item on.
     *
     * @return the created menu item.
     */
    private @NotNull ItemStack createMenuItem(@NotNull Crop crop) {
        String name = MessageUtils.beautify(crop.getName(), false);
        String status = crop.isHarvestable()
                        ? LanguageAPI.Menu.CROPS_STATUS_ENABLED.get(plugin)
                        : LanguageAPI.Menu.CROPS_STATUS_DISABLED.get(plugin);
        ItemBuilder menuItem = new ItemBuilder(crop.getMenuType())
                .setName(LanguageAPI.Menu.CROPS_ITEM_NAME.get(plugin, name, status))
                .setMaterial(!crop.isHarvestable(), Material.GRAY_STAINED_GLASS_PANE);

        switch (menuState) {
            case CROP:
                menuItem.setLore(LanguageAPI.Menu.CROPS_ITEM_DROP_VALUE.get(plugin, getAmountOfDrops(crop)));
                break;

            case PARTICLES:
                menuItem.setLore(LanguageAPI.Menu.CROPS_ITEM_PARTICLES.get(plugin, getAmountOfParticles(crop)));
                break;

            case SOUNDS:
                menuItem.setLore(LanguageAPI.Menu.CROPS_ITEM_SOUNDS.get(plugin, getAmountOfSounds(crop)));
                break;

            case NAME:
                menuItem.setLore(LanguageAPI.Menu.CROPS_ITEM_DROP_NAME.get(plugin, getDropName(crop)));
                break;

            case MCMMO:
                menuItem.setLore(LanguageAPI.Menu.CROPS_ITEM_MMO_EXPERIENCE.get(plugin, getMcMMOExperience(crop)));
                break;

            case JOBS_REBORN:
                menuItem.setLore(
                        LanguageAPI.Menu.CROPS_ITEM_JOBS_MONEY.get(plugin, getJobsMoney(crop)),
                        LanguageAPI.Menu.CROPS_ITEM_JOBS_POINTS.get(plugin, getJobsPoints(crop)),
                        LanguageAPI.Menu.CROPS_ITEM_JOBS_EXPERIENCE.get(plugin, getJobsExperience(crop))
                );
        }

        return menuItem.toItemStack();
    }


    /**
     * Gets all the {@link #crops} as {@link #menuItems menu items}.
     *
     * @return crops as menu items.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return crops.stream()
                    .map(this::createMenuItem)
                    .collect(Collectors.toList());
    }


    /**
     * Gets the {@link McMMOAddon mcMMO} experience gained when harvesting the {@link Crop provided crop}.
     *
     * @param crop the crop to base the experience on.
     *
     * @return the experience gained when harvesting the crop.
     */
    private double getMcMMOExperience(@NotNull Crop crop) {
        return addonSection.getMcMMOExperience(crop.getName());
    }


    /**
     * Gets the {@link JobsRebornAddon JobsReborn} money gained when harvesting the {@link Crop provided crop}.
     *
     * @param crop the crop to base the money on.
     *
     * @return the money gained when harvesting the crop.
     */
    private double getJobsMoney(@NotNull Crop crop) {
        return addonSection.getJobsMoney(crop.getName());
    }


    /**
     * Gets the {@link JobsRebornAddon JobsReborn} points gained when harvesting the {@link Crop provided crop}.
     *
     * @param crop the crop to base the points on.
     *
     * @return the points gained when harvesting the crop.
     */
    private double getJobsPoints(@NotNull Crop crop) {
        return addonSection.getJobsPoints(crop.getName());
    }


    /**
     * Gets the {@link JobsRebornAddon JobsReborn} experience gained when harvesting the {@link Crop provided crop}.
     *
     * @param crop the crop to base the experience on.
     *
     * @return the experience gained when harvesting the crop.
     */
    private double getJobsExperience(@NotNull Crop crop) {
        return addonSection.getJobsExperience(crop.getName());
    }


    /**
     * Gets the amount of {@link Sound#values() sounds} associated with the {@link Crop crop}.
     *
     * @param crop the crop associated with the sounds.
     *
     * @return the amount of sounds associated with the crop.
     */
    private int getAmountOfSounds(@NotNull Crop crop) {
        return soundSection.getAmountOfSounds(crop.getName());
    }


    /**
     * Gets the amount of {@link ParticleEffect#values() sounds} associated with the {@link Crop crop}.
     *
     * @param crop the crop associated with the particles.
     *
     * @return the amount of particles associated with the crop.
     */
    private int getAmountOfParticles(@NotNull Crop crop) {
        return particleSection.getAmountOfParticles(crop.getName());
    }


    /**
     * Gets the amount of {@link Drop drops} given when the {@link Crop provided crop} is harvested.
     *
     * @param crop the crop to base the amount of drops on.
     *
     * @return the amount of drops given when harvesting the crop.
     */
    private int getAmountOfDrops(@NotNull Crop crop) {
        return crop.getDrop().getAmount();
    }


    /**
     * Gets the name of the {@link Drop drop} of the {@link Crop provided crop}.
     *
     * @param crop the crop to base the name of the drop on.
     *
     * @return the name of the crop's drop.
     */
    @Contract("_ -> new")
    private @NotNull String getDropName(@NotNull Crop crop) {
        return cropSection.getDropName(crop.getName());
    }

}