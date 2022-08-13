package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.CocoaBean;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
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
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class CropsMenu extends PaginatedMenu {

    //TODO: Check all the comments... since they are a bit weird..

    private final List<Crop> crops;

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

        int index = getIndexOfCrop(clicked);
        if (index == -1) {
            return;
        }

        Crop crop = crops.get(index);
        switch (menuState) {
            case CROP:
                new CropMenu(plugin, player, crop).open();
                break;

            case SOUNDS:
                new SoundsMenu(plugin, player, crop).open();
                break;

            case PARTICLES:
                new ParticlesMenu(plugin, player, crop).open();
                break;

            case NAME:
                new NameMenu(plugin, player, crop).open();
                break;

            case JOBS_REBORN:
                new JobsCropMenu(plugin, player, crop).open();
                break;

            case MCMMO:
                new McMMOCropMenu(plugin, player, crop).open();
                break;
        }
    }


    /**
     * "Get the index of the clicked item in the menuItems list, or -1 if it's not in the list."
     * <p>
     * The first line of the function is the return statement. It's returning an int, which is the index of the clicked
     * item in the menuItems list.
     * </p>
     *
     * @param clicked The item that was clicked.
     *
     * @return The index of the item in the menuItems list.
     */
    private int getIndexOfCrop(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * It creates an item for the menu.
     *
     * @param crop The crop that the item is being created for.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getMenuItem(@NotNull Crop crop) {
        String name = MessageUtils.beautify(crop.getName(), false);
        String status = crop.isHarvestable()
                        ? LanguageAPI.Menu.CROPS_STATUS_ENABLED.get(plugin)
                        : LanguageAPI.Menu.CROPS_STATUS_DISABLED.get(plugin);
        ItemUtil menuItem = new ItemUtil(crop.getMenuType())
                .setName(LanguageAPI.Menu.CROPS_ITEM_NAME.get(plugin, name, status))
                .setDamage(crop instanceof CocoaBean ? 3 : -1)
                .setDamage(crop.isHarvestable() ? -1 : 15)
                .setMaterial(crop.isHarvestable() ? null : Material.STAINED_GLASS_PANE);

        switch (menuState) {
            case CROP:
                menuItem.setLore(LanguageAPI.Menu.CROPS_ITEM_DROP_VALUE.get(plugin, getDropValue(crop)));
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
     * Get a list of menu items by mapping each crop to a menu item.
     *
     * @return A list of ItemStacks.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return crops.stream()
                    .map(this::getMenuItem)
                    .collect(Collectors.toList());
    }


    /**
     * Get the McMMO experience for the given crop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The McMMO experience for the crop.
     */
    private double getMcMMOExperience(@NotNull Crop crop) {
        return addonSection.getMcMMOExperience(crop.getName());
    }


    /**
     * It gets the money that a player gets from a job.
     *
     * @param crop The crop that the player is harvesting.
     *
     * @return The amount of money that the player will receive for harvesting the crop.
     */
    private double getJobsMoney(@NotNull Crop crop) {
        return addonSection.getJobsMoney(crop.getName());
    }


    /**
     * It returns the amount of points a player gets for harvesting a crop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The jobs point for the crop.
     */
    private double getJobsPoints(@NotNull Crop crop) {
        return addonSection.getJobsPoints(crop.getName());
    }


    /**
     * This function returns the experience gained from harvesting a crop.
     *
     * @param crop The crop that was harvested.
     *
     * @return The experience gained from harvesting a crop.
     */
    private double getJobsExperience(@NotNull Crop crop) {
        return addonSection.getJobsExperience(crop.getName());
    }


    /**
     * Returns the amount of sounds for a crop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The amount of sounds for a crop.
     */
    private int getAmountOfSounds(@NotNull Crop crop) {
        return soundSection.getAmountOfSounds(crop.getName());
    }


    /**
     * This function returns the amount of particles a crop has.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The amount of particles that are in the config file for the crop.
     */
    private int getAmountOfParticles(@NotNull Crop crop) {
        return particleSection.getAmountOfSounds(crop.getName());
    }


    /**
     * This function returns the amount of items that a crop will drop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The amount of the drop.
     */
    private int getDropValue(@NotNull Crop crop) {
        return crop.getDrop().getAmount();
    }


    /**
     * If the crop has a drop, return the drop's name, otherwise return the crop's name.
     *
     * @param crop The crop to get the drop name of.
     *
     * @return The name of the crop or the name of the drop.
     */
    @Contract("_ -> new")
    private @NotNull String getDropName(@NotNull Crop crop) {
        return cropSection.getDropName(crop.getName());
    }

}