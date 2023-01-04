package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ToggleMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;


/**
 * A class representing the Settings menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SettingsMenu extends BaseMenu {

    /**
     * A variable checking if the menu request to {@link SettingsMenu} was a redirect.
     */
    private final boolean isRedirected;

    /**
     * A variable checking if the {@link VersionUtils#getServerVersion() server version} supports {@link ParticleEffect particles}.
     */
    private final boolean supportsParticles;


    public SettingsMenu(@NotNull CropClick plugin, @NotNull Player player, boolean isRedirected) {
        super(plugin, player, LanguageAPI.Menu.SETTINGS_TITLE);
        this.supportsParticles = !VersionUtils.between(0.0, 13.9);
        this.isRedirected = isRedirected;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(10, getToggleItem());

        if (supportsParticles) {
            inventory.setItem(13, getParticlesItem());
        }

        inventory.setItem(supportsParticles ? 16 : 13, getSoundsItem());

        inventory.setItem(supportsParticles ? 28 : 16, getNameItem());
        inventory.setItem(supportsParticles ? 31 : 28, getAutofarmsItem());
        inventory.setItem(supportsParticles ? 34 : 31, getWorldsItem());

        if (isRedirected) setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new MainMenu(plugin, player));

        if (clicked.equals(getAutofarmsItem())) {
            toggleAutofarms();
            refreshMenu();
            return;
        }

        if (supportsParticles && clicked.equals(getParticlesItem())) {
            new CropsMenu(plugin, player, CropMenuState.PARTICLES).openMenu();
        }

        if (clicked.equals(getSoundsItem())) {
            new CropsMenu(plugin, player, CropMenuState.SOUNDS).openMenu();
        }

        if (clicked.isSimilar(getToggleItem())) {
            new ToggleMenu(plugin, player).openMenu();
        }

        if (clicked.equals(getNameItem())) {
            new CropsMenu(plugin, player, CropMenuState.NAME).openMenu();
        }

        if (clicked.equals(getWorldsItem())) {
            new WorldsMenu(plugin, player, WorldMenuState.SETTINGS).openMenu();
        }
    }


    /**
     * Gets the autofarms {@link ItemStack item}.
     *
     * @return the autofarms item.
     */
    private @NotNull ItemStack getAutofarmsItem() {
        String status = MessageUtils.getStatusMessage(
                plugin,
                plugin.getAutofarmManager().isEnabled()
        );

        return new ItemBuilder(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_AUTOFARMS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_AUTOFARMS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_AUTOFARMS_ITEM_STATUS.get(plugin, status)))
                .toItemStack();
    }


    /**
     * Gets the particles {@link ItemStack item}.
     *
     * @return the particles item.
     */
    private @NotNull ItemStack getParticlesItem() {
        return new ItemBuilder(Material.FIREWORK_ROCKET)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_PARTICLES_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_PARTICLES_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_PARTICLES_ITEM_STATUS.get(plugin, getAmountOfParticles())))
                .toItemStack();
    }


    /**
     * Gets the sounds {@link ItemStack item}.
     *
     * @return the sounds item.
     */
    private @NotNull ItemStack getSoundsItem() {
        return new ItemBuilder(Material.NOTE_BLOCK)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_SOUNDS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_SOUNDS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_SOUNDS_ITEM_STATUS.get(plugin, getAmountOfSounds())))
                .toItemStack();
    }


    /**
     * Gets the toggle {@link ItemStack item}.
     *
     * @return the toggle item.
     */
    private @NotNull ItemStack getToggleItem() {
        return new ItemBuilder(Material.PLAYER_HEAD)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_TOGGLE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_TOGGLE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_TOGGLE_ITEM_STATUS.get(plugin, getAmountOfEnabled())))
                .toPlayerHead(player);
    }


    /**
     * Gets the name {@link ItemStack item}.
     *
     * @return the name item.
     */
    private @NotNull ItemStack getNameItem() {
        return new ItemBuilder(Material.NAME_TAG)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_NAME_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_NAME_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_NAME_ITEM_STATUS.get(plugin, getAmountOfRenamed())))
                .toItemStack();
    }


    /**
     * Gets the worlds {@link ItemStack item}.
     *
     * @return the worlds item.
     */
    private @NotNull ItemStack getWorldsItem() {
        return new ItemBuilder(Material.GRASS_BLOCK)
                .setName(plugin, LanguageAPI.Menu.SETTINGS_WORLDS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SETTINGS_WORLDS_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SETTINGS_WORLDS_ITEM_STATUS.get(plugin, getAmountOfBanished())))
                .toItemStack();
    }


    /**
     * Toggles the enabled state of all {@link Autofarm autofarms}.
     */
    private void toggleAutofarms() {
        plugin.getConfig().set("autofarms.isEnabled", !plugin.getAutofarmManager().isEnabled());
        plugin.saveConfig();
    }


    /**
     * Gets the amount of {@link ParticleEffect#getAvailableEffects() particle effects}.
     *
     * @return the amount particle effects.
     */
    private int getAmountOfParticles() {
        return ParticleEffect.getAvailableEffects().size();
    }


    /**
     * Gets the amount of {@link Sound#values() sounds}.
     *
     * @return the amount of sounds.
     */
    private int getAmountOfSounds() {
        return Sound.values().length;
    }


    /**
     * Gets the amount of {@link OfflinePlayer enabled players} allowed to use {@link CropClick}.
     *
     * @return the amount of enabled players.
     */
    private int getAmountOfEnabled() {
        int amountOfPlayers = Bukkit.getOfflinePlayers().length;
        int amountOfDisabled = plugin.getPlayersConfig().getDisabledPlayers().size();
        return MathUtils.clamp(amountOfPlayers - amountOfDisabled, 0, 999999);
    }


    /**
     * Gets the amount of {@link Crop renamed crops}.
     *
     * @return the amount of renamed crops.
     */
    private int getAmountOfRenamed() {
        return (int) plugin.getCropManager().getCrops()
                           .stream()
                           .filter(crop -> !crop.getDrop().getName().equals(crop.getName()))
                           .count();
    }


    /**
     * Gets the amount of {@link FarmWorld banished worlds}.
     *
     * @return the amount of banished worlds.
     */
    private int getAmountOfBanished() {
        return (int) plugin.getWorldManager().getWorlds().values().stream()
                           .filter(FarmWorld::isBanished)
                           .count();
    }

}