package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
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
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Represents the Crops menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class CropsMenu extends PaginatedMenu {

    private final List<BaseCrop> crops;

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

        int index = getIndexOfCrop(clicked);
        if (index == -1) {
            return;
        }

        BaseCrop crop = crops.get(index);
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
     * "Get the index of the crop item that was clicked on."
     * <p>
     * The first thing we do is create a stream of all the items in the menu. Then we filter the stream to only contain the
     * item that was clicked on. Then we map the stream to only contain the index of the item that was clicked on. Finally,
     * we find the first item in the stream and return it. If there is no item in the stream, we return -1.
     * </p>
     *
     * @param clicked The item that was clicked.
     *
     * @return The index of the crop in the {@link #menuItems menuItems list}.
     */
    private int getIndexOfCrop(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * It creates a crop item for the menu.
     *
     * @param crop The crop that the item is being created for.
     *
     * @return The given crop as a menu item.
     */
    private @NotNull ItemStack getMenuItem(@NotNull BaseCrop crop) {
        String name = MessageUtils.beautify(crop.getName(), false);
        String status = crop.isHarvestable()
                        ? LanguageAPI.Menu.CROPS_STATUS_ENABLED.get(plugin)
                        : LanguageAPI.Menu.CROPS_STATUS_DISABLED.get(plugin);
        ItemBuilder menuItem = new ItemBuilder(crop.getMenuType())
                .setName(LanguageAPI.Menu.CROPS_ITEM_NAME.get(plugin, name, status))
                .setMaterial(crop.isHarvestable() ? null : Material.GRAY_STAINED_GLASS_PANE);

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
     * It gets a list of menu items by mapping each crop to a menu item.
     *
     * @return A list of crops as menuItems.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return crops.stream()
                    .map(this::getMenuItem)
                    .collect(Collectors.toList());
    }


    /**
     * It gets the <a href="https://www.spigotmc.org/resources/official-mcmmo-original-author-returns.64348/">mcMMO</a> experience for the given crop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The <a href="https://www.spigotmc.org/resources/official-mcmmo-original-author-returns.64348/">mcMMO</a> experience given when harvesting a crop.
     */
    private double getMcMMOExperience(@NotNull BaseCrop crop) {
        return addonSection.getMcMMOExperience(crop.getName());
    }


    /**
     * It gets the <a href="https://www.spigotmc.org/resources/jobs-reborn.4216/">JobsReborn</a> money for the given crop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The <a href="https://www.spigotmc.org/resources/jobs-reborn.4216/">JobsReborn</a> money given when harvesting a crop.
     */
    private double getJobsMoney(@NotNull BaseCrop crop) {
        return addonSection.getJobsMoney(crop.getName());
    }


    /**
     * It gets the <a href="https://www.spigotmc.org/resources/jobs-reborn.4216/">JobsReborn</a> points for the given crop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The <a href="https://www.spigotmc.org/resources/jobs-reborn.4216/">JobsReborn</a> points given when harvesting a crop.
     */
    private double getJobsPoints(@NotNull BaseCrop crop) {
        return addonSection.getJobsPoints(crop.getName());
    }


    /**
     * It gets the <a href="https://www.spigotmc.org/resources/jobs-reborn.4216/">JobsReborn</a> experience for the given crop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The <a href="https://www.spigotmc.org/resources/jobs-reborn.4216/">JobsReborn</a> experience given when harvesting a crop.
     */
    private double getJobsExperience(@NotNull BaseCrop crop) {
        return addonSection.getJobsExperience(crop.getName());
    }


    /**
     * It returns the amount of sounds the given crop has.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The amount of sounds the crop has.
     */
    private int getAmountOfSounds(@NotNull BaseCrop crop) {
        return soundSection.getAmountOfSounds(crop.getName());
    }


    /**
     * It returns the amount of particles the given crop has.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The amount of particles the crop has.
     */
    private int getAmountOfParticles(@NotNull BaseCrop crop) {
        return particleSection.getAmountOfParticles(crop.getName());
    }


    /**
     * It returns the amount of drops the given crop will drop.
     *
     * @param crop The crop that is being harvested.
     *
     * @return The amount of drop.
     */
    private int getDropValue(@NotNull BaseCrop crop) {
        return crop.getDrop().getAmount();
    }


    /**
     * It gets the drop's name or the crop's name, if no drop name is set.
     *
     * @param crop The crop to get the drop name of.
     *
     * @return The name of the crop or the name of the drop.
     */
    @Contract("_ -> new")
    private @NotNull String getDropName(@NotNull BaseCrop crop) {
        return cropSection.getDropName(crop.getName());
    }

}