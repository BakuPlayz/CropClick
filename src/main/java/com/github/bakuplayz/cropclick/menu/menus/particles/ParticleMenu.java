package com.github.bakuplayz.cropclick.menu.menus.particles;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ParticlesMenu;
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
public final class ParticleMenu extends Menu {

    private final Crop crop;
    private final CropsConfig cropsConfig;

    private final String particleName;
    private final String cropName;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    public final int MIN_DELAY = 0;
    public final int MAX_DELAY = 5;

    public final int MIN_SPEED = 0;
    public final int MAX_SPEED = 50;

    public final int MIN_AMOUNT = 0;
    public final int MAX_AMOUNT = 20;


    public ParticleMenu(@NotNull CropClick plugin,
                        @NotNull Player player,
                        @NotNull Crop crop,
                        @NotNull String particleName) {
        super(plugin, player, LanguageAPI.Menu.PARTICLE_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.particleName = particleName;
        this.cropName = crop.getName();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(10, getDelayRemoveItem(MAX_CHANGE));
        inventory.setItem(11, getDelayRemoveItem(MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayAddItem(MIN_CHANGE));
        inventory.setItem(16, getDelayAddItem(MAX_CHANGE));

        inventory.setItem(19, getSpeedRemoveItem(MAX_CHANGE));
        inventory.setItem(20, getSpeedRemoveItem(MIN_CHANGE));
        inventory.setItem(22, getSpeedItem());
        inventory.setItem(24, getSpeedAddItem(MIN_CHANGE));
        inventory.setItem(25, getSpeedAddItem(MAX_CHANGE));

        inventory.setItem(28, getAmountRemoveItem(MAX_CHANGE));
        inventory.setItem(29, getAmountRemoveItem(MIN_CHANGE));
        inventory.setItem(31, getAmountItem());
        inventory.setItem(33, getAmountAddItem(MIN_CHANGE));
        inventory.setItem(34, getAmountAddItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new ParticlesMenu(plugin, player, crop));

        // DELAY
        if (clicked.equals(getDelayAddItem(MIN_CHANGE))) {
            addParticleDelay(MIN_CHANGE);
        }

        if (clicked.equals(getDelayAddItem(MAX_CHANGE))) {
            addParticleDelay(MAX_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(MIN_CHANGE))) {
            removeParticleDelay(MIN_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(MAX_CHANGE))) {
            removeParticleDelay(MAX_CHANGE);
        }

        // SPEED
        if (clicked.equals(getSpeedAddItem(MIN_CHANGE))) {
            addParticleSpeed(MIN_CHANGE);
        }

        if (clicked.equals(getSpeedAddItem(MAX_CHANGE))) {
            addParticleSpeed(MAX_CHANGE);
        }

        if (clicked.equals(getSpeedRemoveItem(MIN_CHANGE))) {
            removeParticleSpeed(MIN_CHANGE);
        }

        if (clicked.equals(getSpeedRemoveItem(MAX_CHANGE))) {
            removeParticleSpeed(MAX_CHANGE);
        }

        // AMOUNT
        if (clicked.equals(getAmountAddItem(MIN_CHANGE))) {
            addParticleAmount(MIN_CHANGE);
        }

        if (clicked.equals(getAmountAddItem(MAX_CHANGE))) {
            addParticleAmount(MAX_CHANGE);
        }

        if (clicked.equals(getAmountRemoveItem(MIN_CHANGE))) {
            removeParticleAmount(MIN_CHANGE);
        }

        if (clicked.equals(getAmountRemoveItem(MAX_CHANGE))) {
            removeParticleAmount(MAX_CHANGE);
        }

        updateMenu();
    }


    private @NotNull ItemStack getDelayItem() {
        double delay = cropsConfig.getParticleDelay(
                cropName,
                particleName
        );

        return new ItemUtil(Material.WATCH)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_DELAY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_DELAY_ITEM_VALUE.get(plugin, delay))
                .toItemStack();
    }


    private @NotNull ItemStack getSpeedItem() {
        double speed = cropsConfig.getParticleSpeed(
                cropName,
                particleName
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 4)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_SPEED_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_SPEED_ITEM_VALUE.get(plugin, speed))
                .toItemStack();
    }


    private @NotNull ItemStack getAmountItem() {
        int amount = cropsConfig.getParticleAmount(
                cropName,
                particleName
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_VALUE.get(plugin, amount))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayAddItem(int amount) {
        double beforeValue = cropsConfig.getParticleDelay(
                cropName,
                particleName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_DELAY);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, amount, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayRemoveItem(int amount) {
        double beforeValue = cropsConfig.getParticleDelay(
                cropName,
                particleName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_DELAY);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, amount, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getSpeedAddItem(int amount) {
        double beforeValue = cropsConfig.getParticleSpeed(
                cropName,
                particleName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_SPEED);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, amount, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getSpeedRemoveItem(int amount) {
        double beforeValue = cropsConfig.getParticleSpeed(
                cropName,
                particleName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_SPEED);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, amount, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getAmountAddItem(int amount) {
        double beforeValue = cropsConfig.getParticleAmount(
                cropName,
                particleName
        );
        double afterValue = Math.min(beforeValue + amount, MAX_AMOUNT);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, amount, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    private @NotNull ItemStack getAmountRemoveItem(int amount) {
        double beforeValue = cropsConfig.getParticleAmount(
                cropName,
                particleName
        );
        double afterValue = Math.max(beforeValue - amount, MIN_AMOUNT);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, amount, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    public void addParticleDelay(int delay) {
        int oldDelay = (int) (cropsConfig.getParticleDelay(cropName, particleName) + delay);
        int newDelay = Math.min(oldDelay, MAX_DELAY);
        cropsConfig.setParticleDelay(cropName, particleName, newDelay);
    }


    public void removeParticleDelay(int delay) {
        int oldDelay = (int) (cropsConfig.getParticleDelay(cropName, particleName) - delay);
        int newDelay = Math.max(oldDelay, MIN_DELAY);
        cropsConfig.setParticleDelay(cropName, particleName, newDelay);
    }


    public void addParticleSpeed(int speed) {
        int oldSpeed = (int) (cropsConfig.getParticleSpeed(cropName, particleName) + speed);
        int newSpeed = Math.min(oldSpeed, MAX_SPEED);
        cropsConfig.setParticleSpeed(cropName, particleName, newSpeed);
    }


    public void removeParticleSpeed(int speed) {
        int oldSpeed = (int) (cropsConfig.getParticleSpeed(cropName, particleName) - speed);
        int newSpeed = Math.max(oldSpeed, MIN_SPEED);
        cropsConfig.setParticleSpeed(cropName, particleName, newSpeed);
    }


    public void addParticleAmount(int amount) {
        int oldAmount = cropsConfig.getParticleAmount(cropName, particleName) + amount;
        int newAmount = Math.min(oldAmount, MAX_AMOUNT);
        cropsConfig.setParticleAmount(cropName, particleName, newAmount);
    }


    public void removeParticleAmount(int amount) {
        int oldAmount = cropsConfig.getParticleAmount(cropName, particleName) - amount;
        int newAmount = Math.max(oldAmount, MIN_AMOUNT);
        cropsConfig.setParticleAmount(cropName, particleName, newAmount);
    }

}