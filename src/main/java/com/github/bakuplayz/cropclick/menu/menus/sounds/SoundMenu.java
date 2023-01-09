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

package com.github.bakuplayz.cropclick.menu.menus.sounds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.SoundsMenu;
import com.github.bakuplayz.cropclick.runnables.sounds.Sound;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the Sound menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class SoundMenu extends BaseMenu {

    /**
     * A variable measured in milliseconds.
     */
    private static final int DELAY_MIN_CHANGE = 100;

    /**
     * A variable measured in milliseconds.
     */
    private static final int DELAY_MAX_CHANGE = 500;

    /**
     * A variable measured in range-of-blocks.
     */
    private static final int VOLUME_MIN_CHANGE = 1;

    /**
     * A variable measured in range-of-blocks.
     */
    private static final int VOLUME_MAX_CHANGE = 5;

    private static final double PITCH_MIN_CHANGE = 0.1;
    private static final double PITCH_MAX_CHANGE = 0.2;


    private final Crop crop;
    private final String cropName;
    private final String soundName;
    private final SoundConfigSection soundSection;


    /**
     * A variable containing the maximum order allowed.
     */
    private int maxOrder;

    /**
     * A variable containing the current order (or index) of the pressed sound.
     */
    private int currentOrder;


    public SoundMenu(@NotNull CropClick plugin,
                     @NotNull Player player,
                     @NotNull Crop crop,
                     @NotNull String soundName) {
        super(plugin, player, LanguageAPI.Menu.SOUND_TITLE);
        this.soundSection = plugin.getCropsConfig().getSoundSection();
        this.cropName = crop.getName();
        this.soundName = soundName;
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        this.currentOrder = soundSection.getOrder(cropName, soundName);
        this.maxOrder = soundSection.getAmountOfSounds(cropName) - 1;

        inventory.setItem(10, getDelayDecreaseItem(DELAY_MAX_CHANGE));
        inventory.setItem(11, getDelayDecreaseItem(DELAY_MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayIncreaseItem(DELAY_MIN_CHANGE));
        inventory.setItem(16, getDelayIncreaseItem(DELAY_MAX_CHANGE));

        inventory.setItem(19, getVolumeDecreaseItem(VOLUME_MAX_CHANGE));
        inventory.setItem(20, getVolumeDecreaseItem(VOLUME_MIN_CHANGE));
        inventory.setItem(22, getVolumeItem());
        inventory.setItem(24, getVolumeIncreaseItem(VOLUME_MIN_CHANGE));
        inventory.setItem(25, getVolumeIncreaseItem(VOLUME_MAX_CHANGE));

        inventory.setItem(28, getPitchDecreaseItem(PITCH_MAX_CHANGE));
        inventory.setItem(29, getPitchDecreaseItem(PITCH_MIN_CHANGE));
        inventory.setItem(31, getPitchItem());
        inventory.setItem(33, getPitchIncreaseItem(PITCH_MIN_CHANGE));
        inventory.setItem(34, getPitchIncreaseItem(PITCH_MAX_CHANGE));

        setBackItem();

        if (currentOrder == -1) {
            return;
        }

        if (currentOrder != 0) {
            inventory.setItem(47, getDecreaseOrderItem());
        }

        if (currentOrder != maxOrder) {
            inventory.setItem(51, getIncreaseOrderItem());
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new SoundsMenu(plugin, player, crop));

        // ORDER
        if (clicked.equals(getIncreaseOrderItem())) {
            soundSection.swapOrder(cropName, currentOrder, ++currentOrder);
        }

        if (clicked.equals(getDecreaseOrderItem())) {
            soundSection.swapOrder(cropName, currentOrder, --currentOrder);
        }

        // DELAY
        if (clicked.equals(getDelayIncreaseItem(DELAY_MIN_CHANGE))) {
            increaseSoundDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayIncreaseItem(DELAY_MAX_CHANGE))) {
            increaseSoundDelay(DELAY_MAX_CHANGE);
        }

        if (clicked.equals(getDelayDecreaseItem(DELAY_MIN_CHANGE))) {
            decreaseSoundDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayDecreaseItem(DELAY_MAX_CHANGE))) {
            decreaseSoundDelay(DELAY_MAX_CHANGE);
        }

        // VOLUME
        if (clicked.equals(getVolumeIncreaseItem(VOLUME_MIN_CHANGE))) {
            increaseVolume(VOLUME_MIN_CHANGE);
        }

        if (clicked.equals(getVolumeIncreaseItem(VOLUME_MAX_CHANGE))) {
            increaseVolume(VOLUME_MAX_CHANGE);
        }

        if (clicked.equals(getVolumeDecreaseItem(VOLUME_MIN_CHANGE))) {
            decreaseVolume(VOLUME_MIN_CHANGE);
        }

        if (clicked.equals(getVolumeDecreaseItem(VOLUME_MAX_CHANGE))) {
            decreaseVolume(VOLUME_MAX_CHANGE);
        }

        // PITCH
        if (clicked.equals(getPitchIncreaseItem(PITCH_MIN_CHANGE))) {
            increasePitch(PITCH_MIN_CHANGE);
        }

        if (clicked.equals(getPitchIncreaseItem(PITCH_MAX_CHANGE))) {
            increasePitch(PITCH_MAX_CHANGE);
        }

        if (clicked.equals(getPitchDecreaseItem(PITCH_MIN_CHANGE))) {
            decreasePitch(PITCH_MIN_CHANGE);
        }

        if (clicked.equals(getPitchDecreaseItem(PITCH_MAX_CHANGE))) {
            decreasePitch(PITCH_MAX_CHANGE);
        }

        refreshMenu();
    }


    /**
     * Gets the delay {@link ItemStack item}.
     *
     * @return the delay item.
     */
    private @NotNull ItemStack getDelayItem() {
        double delay = soundSection.getDelay(
                cropName,
                soundName
        );

        return new ItemBuilder(Material.CLOCK)
                .setName(plugin, LanguageAPI.Menu.SOUND_DELAY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_DELAY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SOUND_DELAY_ITEM_VALUE.get(plugin, delay)
                ))
                .toItemStack();
    }


    /**
     * Gets the volume {@link ItemStack item}.
     *
     * @return the volume item.
     */
    private @NotNull ItemStack getVolumeItem() {
        double volume = soundSection.getVolume(
                cropName,
                soundName
        );

        return new ItemBuilder(Material.NOTE_BLOCK)
                .setName(plugin, LanguageAPI.Menu.SOUND_VOLUME_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_VOLUME_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SOUND_VOLUME_ITEM_VALUE.get(plugin, volume)
                ))
                .toItemStack();
    }


    /**
     * Gets the pitch {@link ItemStack item}.
     *
     * @return the pitch item.
     */
    private @NotNull ItemStack getPitchItem() {
        double pitch = soundSection.getPitch(
                cropName,
                soundName
        );

        return new ItemBuilder(Material.TRIPWIRE_HOOK)
                .setName(plugin, LanguageAPI.Menu.SOUND_PITCH_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_PITCH_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.SOUND_PITCH_ITEM_VALUE.get(plugin, pitch)
                ))
                .toItemStack();
    }


    /**
     * Gets the increase order {@link ItemStack item}.
     *
     * @return the increase order item.
     */
    private @NotNull ItemStack getIncreaseOrderItem() {
        int orderAfter = Math.min(currentOrder + 1, maxOrder);

        return new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.SOUND_INCREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_INCREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    /**
     * Gets the decrease order {@link ItemStack item}.
     *
     * @return the decrease order item.
     */
    private @NotNull ItemStack getDecreaseOrderItem() {
        int orderAfter = Math.max(currentOrder - 1, 0);

        return new ItemBuilder(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.SOUND_DECREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_DECREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    /**
     * Gets the delay increase {@link ItemStack item} based on the provided delay.
     *
     * @param delay the delay to be increased with when clicked.
     *
     * @return the delay increase item.
     */
    private @NotNull ItemStack getDelayIncreaseItem(int delay) {
        double delayBefore = soundSection.getDelay(
                cropName,
                soundName
        );
        double delayAfter = Math.min(delayBefore + delay, Sound.MAX_DELAY);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, delay, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    /**
     * Gets the delay decrease {@link ItemStack item} based on the provided delay.
     *
     * @param delay the delay to be decreased with when clicked.
     *
     * @return the delay decrease item.
     */
    private @NotNull ItemStack getDelayDecreaseItem(int delay) {
        double delayBefore = soundSection.getDelay(
                cropName,
                soundName
        );
        double delayAfter = Math.max(delayBefore - delay, Sound.MIN_DELAY);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, delay, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    /**
     * Gets the volume increase {@link ItemStack item} based on the provided volume.
     *
     * @param volume the volume to be increase with when clicked.
     *
     * @return the volume increase item.
     */
    private @NotNull ItemStack getVolumeIncreaseItem(int volume) {
        double volumeBefore = soundSection.getVolume(
                cropName,
                soundName
        );
        double volumeAfter = Math.min(volumeBefore + volume, Sound.MAX_VOLUME);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, volume, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, volumeAfter))
                .toItemStack();
    }


    /**
     * Gets the volume decrease {@link ItemStack item} based on the provided volume.
     *
     * @param volume the volume to be decreased with when clicked.
     *
     * @return the volume decrease item.
     */
    private @NotNull ItemStack getVolumeDecreaseItem(int volume) {
        double volumeBefore = soundSection.getVolume(
                cropName,
                soundName
        );
        double volumeAfter = Math.max(volumeBefore - volume, Sound.MIN_VOLUME);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, volume, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, volumeAfter))
                .toItemStack();
    }


    /**
     * Gets the pitch increase {@link ItemStack item} based on the provided pitch.
     *
     * @param pitch the pitch to be increased with when clicked.
     *
     * @return the pitch increase item.
     */
    private @NotNull ItemStack getPitchIncreaseItem(double pitch) {
        double pitchBefore = soundSection.getPitch(
                cropName,
                soundName
        );
        double pitchAfter = MathUtils.round(
                Math.min(pitchBefore + pitch, Sound.MAX_PITCH)
        );

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, pitch, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, pitchAfter))
                .toItemStack();
    }


    /**
     * Gets the pitch decrease {@link ItemStack item} based on the provided pitch.
     *
     * @param pitch the pitch to be decreased with when clicked.
     *
     * @return the pitch decrease item.
     */
    private @NotNull ItemStack getPitchDecreaseItem(double pitch) {
        double pitchBefore = soundSection.getPitch(
                cropName,
                soundName
        );
        double pitchAfter = MathUtils.round(
                Math.max(pitchBefore - pitch, Sound.MIN_PITCH)
        );

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, pitch, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, pitchAfter))
                .toItemStack();
    }


    /**
     * Increases the {@link #soundName current sound} with the provided delay.
     *
     * @param delay the delay to be increased with.
     */
    private void increaseSoundDelay(int delay) {
        double oldDelay = MathUtils.round(
                soundSection.getDelay(cropName, soundName) + delay
        );
        double newDelay = Math.min(oldDelay, Sound.MAX_DELAY);
        soundSection.setDelay(cropName, soundName, newDelay);
    }


    /**
     * Decreases the {@link #soundName current sound} with the provided delay.
     *
     * @param delay the delay to be decreased with.
     */
    private void decreaseSoundDelay(int delay) {
        double oldDelay = MathUtils.round(
                soundSection.getDelay(cropName, soundName) - delay
        );
        double newDelay = Math.max(oldDelay, Sound.MIN_DELAY);
        soundSection.setDelay(cropName, soundName, newDelay);
    }


    /**
     * Increases the {@link #soundName current sound} with the provided volume.
     *
     * @param volume the volume to be increased with.
     */
    private void increaseVolume(int volume) {
        double oldVolume = MathUtils.round(
                soundSection.getVolume(cropName, soundName) + volume
        );
        double newVolume = Math.min(oldVolume, Sound.MAX_VOLUME);
        soundSection.setVolume(cropName, soundName, newVolume);
    }


    /**
     * Decreases the {@link #soundName current sound} with the provided volume.
     *
     * @param volume the volume to be decreased with.
     */
    private void decreaseVolume(int volume) {
        double oldVolume = MathUtils.round(
                soundSection.getVolume(cropName, soundName) - volume
        );
        double newVolume = Math.max(oldVolume, Sound.MIN_VOLUME);
        soundSection.setVolume(cropName, soundName, newVolume);
    }


    /**
     * Increases the {@link #soundName current sound} with the provided pitch.
     *
     * @param pitch the pitch to be increased with.
     */
    private void increasePitch(double pitch) {
        double oldPitch = MathUtils.round(
                soundSection.getPitch(cropName, soundName) + pitch
        );
        double newPitch = Math.min(oldPitch, Sound.MAX_PITCH);
        soundSection.setPitch(cropName, soundName, newPitch);
    }


    /**
     * Decreases the {@link #soundName current sound} with the provided pitch.
     *
     * @param pitch the pitch to be decreased with.
     */
    private void decreasePitch(double pitch) {
        double oldPitch = MathUtils.round(
                soundSection.getPitch(cropName, soundName) - pitch
        );
        double newPitch = Math.max(oldPitch, Sound.MIN_PITCH);
        soundSection.setPitch(cropName, soundName, newPitch);
    }

}