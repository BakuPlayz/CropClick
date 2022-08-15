package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ToggleMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SettingsMenu extends Menu {

    private final boolean isRedirected;


    public SettingsMenu(@NotNull CropClick plugin, @NotNull Player player, boolean isRedirected) {
        super(plugin, player, LanguageAPI.Menu.SETTINGS_TITLE);
        this.isRedirected = isRedirected;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(10, getToggleItem());
        inventory.setItem(13, getParticlesItem());
        inventory.setItem(16, getSoundsItem());

        inventory.setItem(28, getNameItem());
        inventory.setItem(31, getAutofarmsItem());
        inventory.setItem(34, getWorldsItem());

        if (isRedirected) setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new MainMenu(plugin, player));

        if (clicked.equals(getAutofarmsItem())) {
            toggleAutofarms();
            updateMenu();
            return;
        }

        if (clicked.equals(getParticlesItem())) {
            new CropsMenu(plugin, player, CropMenuState.PARTICLES).open();
        }

        if (clicked.equals(getSoundsItem())) {
            new CropsMenu(plugin, player, CropMenuState.SOUNDS).open();
        }

        if (clicked.isSimilar(getToggleItem())) {
            new ToggleMenu(plugin, player).open();
        }

        if (clicked.equals(getNameItem())) {
            new CropsMenu(plugin, player, CropMenuState.NAME).open();
        }

        if (clicked.equals(getWorldsItem())) {
            new WorldsMenu(plugin, player, WorldMenuState.SETTINGS).open();
        }
    }


    private @NotNull ItemStack getAutofarmsItem() {
        String status = MessageUtils.getEnabledStatus(
                plugin,
                getAutofarmsState()
        );

        return new ItemUtil(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_AUTOFARMS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_AUTOFARMS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_AUTOFARMS_ITEM_STATUS.get(plugin, status)))
                .toItemStack();
    }


    private @NotNull ItemStack getParticlesItem() {
        return new ItemUtil(Material.FIREWORK_ROCKET)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_PARTICLES_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_PARTICLES_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_PARTICLES_ITEM_STATUS.get(plugin, getAmountOfParticles())))
                .toItemStack();
    }


    private @NotNull ItemStack getSoundsItem() {
        return new ItemUtil(Material.NOTE_BLOCK)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_SOUNDS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_SOUNDS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_SOUNDS_ITEM_STATUS.get(plugin, getAmountOfSounds())))
                .toItemStack();
    }


    //TODO: Get as skull item with the players head
    private @NotNull ItemStack getToggleItem() {
        return new ItemUtil(Material.PLAYER_HEAD)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_TOGGLE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_TOGGLE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_TOGGLE_ITEM_STATUS.get(plugin, getAmountOfEnabled())))
                .toItemStack();
    }


    private @NotNull ItemStack getNameItem() {
        return new ItemUtil(Material.NAME_TAG)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_NAME_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_NAME_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_NAME_ITEM_STATUS.get(plugin, getAmountOfRenamed())))
                .toItemStack();
    }


    private @NotNull ItemStack getWorldsItem() {
        return new ItemUtil(Material.GRASS_BLOCK)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_WORLDS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_WORLDS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_WORLDS_ITEM_STATUS.get(plugin, getAmountOfBanished())))
                .toItemStack();
    }


    /**
     * This function returns the value of the autofarms.isEnabled key in the config.yml file.
     *
     * @return The boolean value of the autofarms.isEnabled key in the config.yml file.
     */
    private boolean getAutofarmsState() {
        return plugin.getAutofarmManager().isEnabled();
    }


    /**
     * Toggle the state of autofarms.
     */
    private void toggleAutofarms() {
        boolean state = getAutofarmsState();
        plugin.getConfig().set("autofarms.isEnabled", !state);
        plugin.saveConfig();
    }


    /**
     * It returns the amount of particles in the ParticleEffect enum.
     *
     * @return The amount of particles in the ParticleEffect enum.
     */
    private int getAmountOfParticles() {
        return ParticleEffect.getAvailableEffects().size();
    }


    /**
     * Return the amount of sounds in the Sound enum.
     *
     * @return The amount of sounds in the Sound enum.
     */
    private int getAmountOfSounds() {
        return Sound.values().length;
    }


    /**
     * Get the amount of players that have the plugin enabled.
     *
     * @return The amount of players that have the plugin enabled.
     */
    private int getAmountOfEnabled() {
        int amountOfPlayers = Bukkit.getOfflinePlayers().length;
        int amountOfDisabled = plugin.getPlayersConfig().getDisabledPlayers().size();
        return amountOfPlayers - amountOfDisabled;
    }


    /**
     * It returns the amount of crops that have a drop name of "null" (meaning unchanged name).
     *
     * @return The amount of crops that have been renamed.
     */
    private int getAmountOfRenamed() {
        return (int) plugin.getCropManager().getCrops()
                           .stream()
                           .filter(crop -> !crop.getDrop().getName().equals(crop.getName()))
                           .count();
    }


    /**
     * Get the amount of banished worlds.
     *
     * @return The number of banished worlds.
     */
    private int getAmountOfBanished() {
        return (int) plugin.getWorldManager().getWorlds().values().stream()
                           .filter(FarmWorld::isBanished)
                           .count();
    }

}