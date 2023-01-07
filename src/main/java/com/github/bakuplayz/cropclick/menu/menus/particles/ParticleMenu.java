package com.github.bakuplayz.cropclick.menu.menus.particles;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ParticlesMenu;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MathUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * A class representing the Particle menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ParticleMenu extends Menu {

    public static final int MIN_CHANGE = 1;
    public static final int MAX_CHANGE = 5;

    public static final int DELAY_MIN_CHANGE = 100; // in milliseconds
    public static final int DELAY_MAX_CHANGE = 500; // in milliseconds

    public static final int MIN_DELAY = 0; // in milliseconds
    public static final int MAX_DELAY = 5000; // in milliseconds

    public static final int MIN_SPEED = 0;
    public static final int MAX_SPEED = 50;

    public static final int MIN_AMOUNT = 0;
    public static final int MAX_AMOUNT = 20;


    private final BaseCrop crop;
    private final String cropName;
    private final String particleName;
    private final ParticleConfigSection particleSection;

    private int maxOrder;
    private int currentOrder;


    public ParticleMenu(@NotNull CropClick plugin,
                        @NotNull Player player,
                        @NotNull BaseCrop crop,
                        @NotNull String particleName) {
        super(plugin, player, LanguageAPI.Menu.PARTICLE_TITLE);
        this.particleSection = plugin.getCropsConfig().getParticleSection();
        this.particleName = particleName;
        this.cropName = crop.getName();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        List<String> particles = particleSection.getParticles(cropName);
        this.currentOrder = particles.indexOf(particleName);
        this.maxOrder = particles.size() - 1;

        inventory.setItem(10, getDelayRemoveItem(ParticleMenu.DELAY_MAX_CHANGE));
        inventory.setItem(11, getDelayRemoveItem(ParticleMenu.DELAY_MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayAddItem(ParticleMenu.DELAY_MIN_CHANGE));
        inventory.setItem(16, getDelayAddItem(ParticleMenu.DELAY_MAX_CHANGE));

        inventory.setItem(19, getSpeedRemoveItem(ParticleMenu.MAX_CHANGE));
        inventory.setItem(20, getSpeedRemoveItem(ParticleMenu.MIN_CHANGE));
        inventory.setItem(22, getSpeedItem());
        inventory.setItem(24, getSpeedAddItem(ParticleMenu.MIN_CHANGE));
        inventory.setItem(25, getSpeedAddItem(ParticleMenu.MAX_CHANGE));

        inventory.setItem(28, getAmountRemoveItem(ParticleMenu.MAX_CHANGE));
        inventory.setItem(29, getAmountRemoveItem(ParticleMenu.MIN_CHANGE));
        inventory.setItem(31, getAmountItem());
        inventory.setItem(33, getAmountAddItem(ParticleMenu.MIN_CHANGE));
        inventory.setItem(34, getAmountAddItem(ParticleMenu.MAX_CHANGE));

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

        handleBack(clicked, new ParticlesMenu(plugin, player, crop));

        // ORDER
        if (clicked.equals(getIncreaseOrderItem())) {
            particleSection.swapOrder(cropName, currentOrder, ++currentOrder);
        }

        if (clicked.equals(getDecreaseOrderItem())) {
            particleSection.swapOrder(cropName, currentOrder, --currentOrder);
        }

        // DELAY
        if (clicked.equals(getDelayAddItem(ParticleMenu.DELAY_MIN_CHANGE))) {
            addParticleDelay(ParticleMenu.DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayAddItem(ParticleMenu.DELAY_MAX_CHANGE))) {
            addParticleDelay(ParticleMenu.DELAY_MAX_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(ParticleMenu.DELAY_MIN_CHANGE))) {
            removeParticleDelay(ParticleMenu.DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(ParticleMenu.DELAY_MAX_CHANGE))) {
            removeParticleDelay(ParticleMenu.DELAY_MAX_CHANGE);
        }

        // SPEED
        if (clicked.equals(getSpeedAddItem(ParticleMenu.MIN_CHANGE))) {
            addParticleSpeed(ParticleMenu.MIN_CHANGE);
        }

        if (clicked.equals(getSpeedAddItem(ParticleMenu.MAX_CHANGE))) {
            addParticleSpeed(ParticleMenu.MAX_CHANGE);
        }

        if (clicked.equals(getSpeedRemoveItem(ParticleMenu.MIN_CHANGE))) {
            removeParticleSpeed(ParticleMenu.MIN_CHANGE);
        }

        if (clicked.equals(getSpeedRemoveItem(ParticleMenu.MAX_CHANGE))) {
            removeParticleSpeed(ParticleMenu.MAX_CHANGE);
        }

        // AMOUNT
        if (clicked.equals(getAmountAddItem(ParticleMenu.MIN_CHANGE))) {
            addParticleAmount(ParticleMenu.MIN_CHANGE);
        }

        if (clicked.equals(getAmountAddItem(ParticleMenu.MAX_CHANGE))) {
            addParticleAmount(ParticleMenu.MAX_CHANGE);
        }

        if (clicked.equals(getAmountRemoveItem(ParticleMenu.MIN_CHANGE))) {
            removeParticleAmount(ParticleMenu.MIN_CHANGE);
        }

        if (clicked.equals(getAmountRemoveItem(ParticleMenu.MAX_CHANGE))) {
            removeParticleAmount(ParticleMenu.MAX_CHANGE);
        }

        refresh();
    }


    /**
     * It creates an item representing the delay at which particles should be displayed at.
     *
     * @return The delay at which particles should be displayed at.
     */
    private @NotNull ItemStack getDelayItem() {
        double delay = particleSection.getDelay(
                cropName,
                particleName
        );

        return new ItemBuilder(Material.WATCH)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_DELAY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_DELAY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.PARTICLE_DELAY_ITEM_VALUE.get(plugin, delay)
                ))
                .toItemStack();
    }


    /**
     * It creates an item representing the speed of particles to be displayed.
     *
     * @return The speed of particles to be displayed.
     */
    private @NotNull ItemStack getSpeedItem() {
        double speed = particleSection.getSpeed(
                cropName,
                particleName
        );

        return new ItemBuilder(Material.FEATHER)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_SPEED_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_SPEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.PARTICLE_SPEED_ITEM_VALUE.get(plugin, speed)
                ))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of particles to be displayed.
     *
     * @return The amount of particles to be displayed.
     */
    private @NotNull ItemStack getAmountItem() {
        int amount = particleSection.getAmount(
                cropName,
                particleName
        );

        return new ItemBuilder(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_VALUE.get(plugin, amount)
                ))
                .toItemStack();
    }


    /**
     * It creates an item that increases the order of the particle.
     *
     * @return An item representing an increase in order for the particle.
     */
    private @NotNull ItemStack getIncreaseOrderItem() {
        int orderAfter = Math.min(currentOrder + 1, maxOrder);

        return new ItemBuilder(Material.IRON_PLATE)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_INCREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_INCREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    /**
     * It creates an item that decreases the order of the particle.
     *
     * @return An item representing a decrease in order for the particle.
     */
    private @NotNull ItemStack getDecreaseOrderItem() {
        int orderAfter = Math.max(currentOrder - 1, 0);

        return new ItemBuilder(Material.GOLD_PLATE)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_DECREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_DECREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of delay to add.
     *
     * @param delayChange the given delay to add.
     *
     * @return An item representing the delay of particles to add.
     */
    private @NotNull ItemStack getDelayAddItem(int delayChange) {
        double delayBefore = particleSection.getDelay(
                cropName,
                particleName
        );
        double delayAfter = Math.min(delayBefore + delayChange, ParticleMenu.MAX_DELAY);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, delayChange, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of delay to remove.
     *
     * @param delayChange the given delay to remove.
     *
     * @return An item representing the delay of particles to remove.
     */
    private @NotNull ItemStack getDelayRemoveItem(int delayChange) {
        double delayBefore = particleSection.getDelay(
                cropName,
                particleName
        );
        double delayAfter = Math.max(delayBefore - delayChange, ParticleMenu.MIN_DELAY);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, delayChange, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of speed to add.
     *
     * @param speedChange the given speed to add.
     *
     * @return An item representing the speed of particles to add.
     */
    private @NotNull ItemStack getSpeedAddItem(int speedChange) {
        double speedBefore = particleSection.getSpeed(
                cropName,
                particleName
        );
        double speedAfter = Math.min(speedBefore + speedChange, ParticleMenu.MAX_SPEED);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, speedChange, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, speedAfter))
                .toItemStack();
    }


    /**
     * It creates an item representing the amount of speed to remove.
     *
     * @param speedChange the given speed to remove.
     *
     * @return An item representing the speed of particles to remove.
     */
    private @NotNull ItemStack getSpeedRemoveItem(int speedChange) {
        double speedBefore = particleSection.getSpeed(
                cropName,
                particleName
        );
        double speedAfter = Math.max(speedBefore - speedChange, ParticleMenu.MIN_SPEED);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, speedChange, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, speedAfter))
                .toItemStack();
    }


    /**
     * It creates an item representing the add amount.
     *
     * @param amountChange the given amount to add.
     *
     * @return An item representing the amount of particles to add.
     */
    private @NotNull ItemStack getAmountAddItem(int amountChange) {
        int amountBefore = particleSection.getAmount(
                cropName,
                particleName
        );
        int amountAfter = Math.min(amountBefore + amountChange, ParticleMenu.MAX_AMOUNT);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, amountChange, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, amountAfter))
                .toItemStack();
    }


    /**
     * It creates an item representing the remove amount.
     *
     * @param amountChange the given amount to remove.
     *
     * @return An item representing the amount of particles to remove.
     */
    private @NotNull ItemStack getAmountRemoveItem(int amountChange) {
        int amountBefore = particleSection.getAmount(
                cropName,
                particleName
        );
        int amountAfter = Math.max(amountBefore - amountChange, ParticleMenu.MIN_AMOUNT);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, amountChange, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, amountAfter))
                .toItemStack();
    }


    /**
     * Adds the given delay from the current delay of the particle.
     *
     * @param delay The delay of the particle.
     */
    public void addParticleDelay(int delay) {
        double oldDelay = MathUtils.round(
                particleSection.getDelay(cropName, particleName) + delay
        );
        double newDelay = Math.min(oldDelay, ParticleMenu.MAX_DELAY);
        particleSection.setDelay(cropName, particleName, newDelay);
    }


    /**
     * Removes the given delay from the current delay of the particle.
     *
     * @param delay The delay of the particle.
     */
    public void removeParticleDelay(int delay) {
        double oldDelay = MathUtils.round(
                particleSection.getDelay(cropName, particleName) - delay
        );
        double newDelay = Math.max(oldDelay, ParticleMenu.MIN_DELAY);
        particleSection.setDelay(cropName, particleName, newDelay);
    }


    /**
     * Adds the given speed to the current speed of the particle.
     *
     * @param speed The speed of the particle.
     */
    public void addParticleSpeed(int speed) {
        double oldSpeed = MathUtils.round(
                particleSection.getSpeed(cropName, particleName) + speed
        );
        double newSpeed = Math.min(oldSpeed, ParticleMenu.MAX_SPEED);
        particleSection.setSpeed(cropName, particleName, newSpeed);
    }


    /**
     * Removes the given speed from the current speed of the particle.
     *
     * @param speed The speed of the particle.
     */
    public void removeParticleSpeed(int speed) {
        double oldSpeed = MathUtils.round(
                particleSection.getSpeed(cropName, particleName) - speed
        );
        double newSpeed = Math.max(oldSpeed, ParticleMenu.MIN_SPEED);
        particleSection.setSpeed(cropName, particleName, newSpeed);
    }


    /**
     * Adds the given amount of particles from the given crop and particle name.
     *
     * @param amount The amount of particles to add.
     */
    public void addParticleAmount(int amount) {
        int oldAmount = particleSection.getAmount(cropName, particleName) + amount;
        int newAmount = Math.min(oldAmount, ParticleMenu.MAX_AMOUNT);
        particleSection.setAmount(cropName, particleName, newAmount);
    }


    /**
     * Removes the given amount of particles from the given crop and particle name.
     *
     * @param amount The amount of particles to remove.
     */
    public void removeParticleAmount(int amount) {
        int oldAmount = particleSection.getAmount(cropName, particleName) - amount;
        int newAmount = Math.max(oldAmount, ParticleMenu.MIN_AMOUNT);
        particleSection.setAmount(cropName, particleName, newAmount);
    }

}