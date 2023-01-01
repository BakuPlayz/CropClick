package com.github.bakuplayz.cropclick.menu.menus.sounds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.settings.SoundsMenu;
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
public final class SoundMenu extends Menu {

    public static final int MIN_DELAY = 0; // in milliseconds
    public static final int MAX_DELAY = 5000; // in milliseconds

    public static final int DELAY_MIN_CHANGE = 100; // in milliseconds
    public static final int DELAY_MAX_CHANGE = 500; // in milliseconds

    public static final int MIN_VOLUME = 0; // range in blocks
    public static final int MAX_VOLUME = 500; // range in blocks

    public static final int VOLUME_MIN_CHANGE = 1;
    public static final int VOLUME_MAX_CHANGE = 5;

    public static final int MIN_PITCH = 0;
    public static final int MAX_PITCH = 2;

    public static final double PITCH_MIN_CHANGE = 0.1;
    public static final double PITCH_MAX_CHANGE = 0.2;


    private final BaseCrop crop;
    private final String cropName;
    private final String soundName;
    private final SoundConfigSection soundSection;

    private int maxOrder;
    private int currentOrder;


    public SoundMenu(@NotNull CropClick plugin,
                     @NotNull Player player,
                     @NotNull BaseCrop crop,
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
        this.maxOrder = soundSection.getSounds(cropName).size() - 1;

        inventory.setItem(10, getDelayRemoveItem(DELAY_MAX_CHANGE));
        inventory.setItem(11, getDelayRemoveItem(DELAY_MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayAddItem(DELAY_MIN_CHANGE));
        inventory.setItem(16, getDelayAddItem(DELAY_MAX_CHANGE));

        inventory.setItem(19, getVolumeRemoveItem(VOLUME_MAX_CHANGE));
        inventory.setItem(20, getVolumeRemoveItem(VOLUME_MIN_CHANGE));
        inventory.setItem(22, getVolumeItem());
        inventory.setItem(24, getVolumeAddItem(VOLUME_MIN_CHANGE));
        inventory.setItem(25, getVolumeAddItem(VOLUME_MAX_CHANGE));

        inventory.setItem(28, getPitchRemoveItem(PITCH_MAX_CHANGE));
        inventory.setItem(29, getPitchRemoveItem(PITCH_MIN_CHANGE));
        inventory.setItem(31, getPitchItem());
        inventory.setItem(33, getPitchAddItem(PITCH_MIN_CHANGE));
        inventory.setItem(34, getPitchAddItem(PITCH_MAX_CHANGE));

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
        if (clicked.equals(getDelayAddItem(DELAY_MIN_CHANGE))) {
            addSoundDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayAddItem(DELAY_MAX_CHANGE))) {
            addSoundDelay(DELAY_MAX_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(DELAY_MIN_CHANGE))) {
            removeSoundDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(DELAY_MAX_CHANGE))) {
            removeSoundDelay(DELAY_MAX_CHANGE);
        }

        // VOLUME
        if (clicked.equals(getVolumeAddItem(VOLUME_MIN_CHANGE))) {
            increaseVolume(VOLUME_MIN_CHANGE);
        }

        if (clicked.equals(getVolumeAddItem(VOLUME_MAX_CHANGE))) {
            increaseVolume(VOLUME_MAX_CHANGE);
        }

        if (clicked.equals(getVolumeRemoveItem(VOLUME_MIN_CHANGE))) {
            decreaseVolume(VOLUME_MIN_CHANGE);
        }

        if (clicked.equals(getVolumeRemoveItem(VOLUME_MAX_CHANGE))) {
            decreaseVolume(VOLUME_MAX_CHANGE);
        }

        // PITCH
        if (clicked.equals(getPitchAddItem(PITCH_MIN_CHANGE))) {
            increasePitch(PITCH_MIN_CHANGE);
        }

        if (clicked.equals(getPitchAddItem(PITCH_MAX_CHANGE))) {
            increasePitch(PITCH_MAX_CHANGE);
        }

        if (clicked.equals(getPitchRemoveItem(PITCH_MIN_CHANGE))) {
            decreasePitch(PITCH_MIN_CHANGE);
        }

        if (clicked.equals(getPitchRemoveItem(PITCH_MAX_CHANGE))) {
            decreasePitch(PITCH_MAX_CHANGE);
        }

        refresh();
    }


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


    private @NotNull ItemStack getIncreaseOrderItem() {
        int orderAfter = Math.min(currentOrder + 1, maxOrder);

        return new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.SOUND_INCREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_INCREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDecreaseOrderItem() {
        int orderAfter = Math.max(currentOrder - 1, 0);

        return new ItemBuilder(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.SOUND_DECREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_DECREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayAddItem(int delayChange) {
        double delayBefore = soundSection.getDelay(
                cropName,
                soundName
        );
        double delayAfter = Math.min(delayBefore + delayChange, MAX_DELAY);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, delayChange, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayRemoveItem(int delayChange) {
        double delayBefore = soundSection.getDelay(
                cropName,
                soundName
        );
        double delayAfter = Math.max(delayBefore - delayChange, MIN_DELAY);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, delayChange, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeAddItem(int volumeChange) {
        double volumeBefore = soundSection.getVolume(
                cropName,
                soundName
        );
        double volumeAfter = Math.min(volumeBefore + volumeChange, MAX_VOLUME);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, volumeChange, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, volumeAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeRemoveItem(int volumeChange) {
        double volumeBefore = soundSection.getVolume(
                cropName,
                soundName
        );
        double volumeAfter = Math.max(volumeBefore - volumeChange, MIN_VOLUME);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, volumeChange, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, volumeAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchAddItem(double pitchChange) {
        double pitchBefore = soundSection.getPitch(
                cropName,
                soundName
        );
        double pitchAfter = MathUtils.round(
                Math.min(pitchBefore + pitchChange, MAX_PITCH)
        );

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, pitchChange, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, pitchAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchRemoveItem(double pitchChange) {
        double pitchBefore = soundSection.getPitch(
                cropName,
                soundName
        );
        double pitchAfter = MathUtils.round(
                Math.max(pitchBefore - pitchChange, MIN_PITCH)
        );

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, pitchChange, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, pitchAfter))
                .toItemStack();
    }


    private void addSoundDelay(int delay) {
        double oldDelay = MathUtils.round(
                soundSection.getDelay(cropName, soundName) + delay
        );
        double newDelay = Math.min(oldDelay, MAX_DELAY);
        soundSection.setDelay(cropName, soundName, newDelay);
    }


    private void removeSoundDelay(int delay) {
        double oldDelay = MathUtils.round(
                soundSection.getDelay(cropName, soundName) - delay
        );
        double newDelay = Math.max(oldDelay, MIN_DELAY);
        soundSection.setDelay(cropName, soundName, newDelay);
    }


    private void increaseVolume(int volume) {
        double oldVolume = MathUtils.round(
                soundSection.getVolume(cropName, soundName) + volume
        );
        double newVolume = Math.min(oldVolume, MAX_VOLUME);
        soundSection.setVolume(cropName, soundName, newVolume);
    }


    private void decreaseVolume(int volume) {
        double oldVolume = MathUtils.round(
                soundSection.getVolume(cropName, soundName) - volume
        );
        double newVolume = Math.max(oldVolume, MIN_VOLUME);
        soundSection.setVolume(cropName, soundName, newVolume);
    }


    private void increasePitch(double pitch) {
        double oldPitch = MathUtils.round(
                soundSection.getPitch(cropName, soundName) + pitch
        );
        double newPitch = Math.min(oldPitch, MAX_PITCH);
        soundSection.setPitch(cropName, soundName, newPitch);
    }


    private void decreasePitch(double pitch) {
        double oldPitch = MathUtils.round(
                soundSection.getPitch(cropName, soundName) - pitch
        );
        double newPitch = Math.max(oldPitch, MIN_PITCH);
        soundSection.setPitch(cropName, soundName, newPitch);
    }

}