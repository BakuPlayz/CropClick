package com.github.bakuplayz.cropclick.menu.menus.sounds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.settings.SoundsMenu;
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
 * @since 1.6.0
 */
public final class SoundMenu extends Menu {

    private final Crop crop;
    private final CropsConfig cropsConfig;

    private final String soundName;
    private final String cropName;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    public final int MIN_DELAY = 0;
    public final int MAX_DELAY = 5;

    public final int MIN_VOLUME = 0;
    public final int MAX_VOLUME = 50;

    public final int MIN_PITCH = 0;
    public final int MAX_PITCH = 20;


    public SoundMenu(@NotNull CropClick plugin,
                     @NotNull Player player,
                     @NotNull Crop crop,
                     @NotNull String soundName) {
        super(plugin, player, LanguageAPI.Menu.SOUND_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.cropName = crop.getName();
        this.soundName = soundName;
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(10, getDelayRemoveItem(MAX_CHANGE));
        inventory.setItem(11, getDelayRemoveItem(MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayAddItem(MIN_CHANGE));
        inventory.setItem(16, getDelayAddItem(MAX_CHANGE));

        inventory.setItem(19, getVolumeRemoveItem(MAX_CHANGE));
        inventory.setItem(20, getVolumeRemoveItem(MIN_CHANGE));
        inventory.setItem(22, getVolumeItem());
        inventory.setItem(24, getVolumeAddItem(MIN_CHANGE));
        inventory.setItem(25, getVolumeAddItem(MAX_CHANGE));

        inventory.setItem(28, getPitchRemoveItem(MAX_CHANGE));
        inventory.setItem(29, getPitchRemoveItem(MIN_CHANGE));
        inventory.setItem(31, getPitchItem());
        inventory.setItem(33, getPitchAddItem(MIN_CHANGE));
        inventory.setItem(34, getPitchAddItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new SoundsMenu(plugin, player, crop));

        // DELAY
        if (clicked.equals(getDelayAddItem(MIN_CHANGE))) {
            addSoundDelay(MIN_CHANGE);
        }

        if (clicked.equals(getDelayAddItem(MAX_CHANGE))) {
            addSoundDelay(MAX_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(MIN_CHANGE))) {
            removeSoundDelay(MIN_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(MAX_CHANGE))) {
            removeSoundDelay(MAX_CHANGE);
        }

        // VOLUME
        if (clicked.equals(getVolumeAddItem(MIN_CHANGE))) {
            increaseVolume(MIN_CHANGE);
        }

        if (clicked.equals(getVolumeAddItem(MAX_CHANGE))) {
            increaseVolume(MAX_CHANGE);
        }

        if (clicked.equals(getVolumeRemoveItem(MIN_CHANGE))) {
            decreaseVolume(MIN_CHANGE);
        }

        if (clicked.equals(getVolumeRemoveItem(MAX_CHANGE))) {
            decreaseVolume(MAX_CHANGE);
        }

        // PITCH
        if (clicked.equals(getPitchAddItem(MIN_CHANGE))) {
            increasePitch(MIN_CHANGE);
        }

        if (clicked.equals(getPitchAddItem(MAX_CHANGE))) {
            increasePitch(MAX_CHANGE);
        }

        if (clicked.equals(getPitchRemoveItem(MIN_CHANGE))) {
            decreasePitch(MIN_CHANGE);
        }

        if (clicked.equals(getPitchRemoveItem(MAX_CHANGE))) {
            decreasePitch(MAX_CHANGE);
        }

        updateMenu();
    }


    private @NotNull ItemStack getDelayItem() {
        double delay = cropsConfig.getSoundDelay(
                cropName,
                soundName
        );

        return new ItemUtil(Material.WATCH)
                .setName(plugin, LanguageAPI.Menu.SOUND_DELAY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_DELAY_ITEM_VALUE.get(plugin, delay))
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeItem() {
        double volume = cropsConfig.getSoundVolume(
                cropName,
                soundName
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 4)
                .setName(plugin, LanguageAPI.Menu.SOUND_VOLUME_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_VOLUME_ITEM_VALUE.get(plugin, volume))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchItem() {
        double pitch = cropsConfig.getSoundPitch(
                cropName,
                soundName
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(plugin, LanguageAPI.Menu.SOUND_PITCH_ITEM_NAME)
                .setLore(LanguageAPI.Menu.SOUND_PITCH_ITEM_VALUE.get(plugin, pitch))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayAddItem(int amount) {
        double beforeValue = cropsConfig.getSoundDelay(
                cropName,
                soundName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_DELAY);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, amount, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayRemoveItem(int amount) {
        double beforeValue = cropsConfig.getSoundDelay(
                cropName,
                soundName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_DELAY);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, amount, "Delay"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeAddItem(int amount) {
        double beforeValue = cropsConfig.getSoundVolume(
                cropName,
                soundName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_VOLUME);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, amount, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getVolumeRemoveItem(int amount) {
        double beforeValue = cropsConfig.getSoundVolume(
                cropName,
                soundName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_VOLUME);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, amount, "Volume"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchAddItem(int amount) {
        double beforeValue = cropsConfig.getSoundPitch(
                cropName,
                soundName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_PITCH);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.SOUND_ADD_ITEM_NAME.get(plugin, amount, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getPitchRemoveItem(int amount) {
        double beforeValue = cropsConfig.getSoundPitch(
                cropName,
                soundName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_PITCH);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.SOUND_REMOVE_ITEM_NAME.get(plugin, amount, "Pitch"))
                .setLore(LanguageAPI.Menu.SOUND_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    public void addSoundDelay(int delay) {
        int oldDelay = (int) (cropsConfig.getSoundDelay(cropName, soundName) + delay);
        int newDelay = Math.min(oldDelay, MAX_DELAY);
        cropsConfig.setSoundDelay(cropName, soundName, newDelay);
    }


    public void removeSoundDelay(int delay) {
        int oldDelay = (int) (cropsConfig.getSoundDelay(cropName, soundName) - delay);
        int newDelay = Math.max(oldDelay, MIN_DELAY);
        cropsConfig.setSoundDelay(cropName, soundName, newDelay);
    }


    public void increaseVolume(int volume) {
        int oldVolume = (int) (cropsConfig.getSoundVolume(cropName, soundName) + volume);
        int newVolume = Math.min(oldVolume, MAX_VOLUME);
        cropsConfig.setSoundVolume(cropName, soundName, newVolume);
    }


    public void decreaseVolume(int volume) {
        int oldVolume = (int) (cropsConfig.getSoundVolume(cropName, soundName) - volume);
        int newVolume = Math.max(oldVolume, MIN_VOLUME);
        cropsConfig.setSoundVolume(cropName, soundName, newVolume);
    }


    public void increasePitch(int pitch) {
        int oldPitch = (int) (cropsConfig.getSoundPitch(cropName, soundName) + pitch);
        int newPitch = Math.min(oldPitch, MAX_PITCH);
        cropsConfig.setSoundPitch(cropName, soundName, newPitch);
    }


    public void decreasePitch(int pitch) {
        int oldPitch = (int) (cropsConfig.getSoundPitch(cropName, soundName) - pitch);
        int newPitch = Math.max(oldPitch, MIN_PITCH);
        cropsConfig.setSoundPitch(cropName, soundName, newPitch);
    }

}