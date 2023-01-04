package com.github.bakuplayz.cropclick.menu.menus.particles;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ParticlesMenu;
import com.github.bakuplayz.cropclick.runnables.particles.Particle;
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
public final class ParticleMenu extends BaseMenu {

    public static final int MIN_CHANGE = 1;
    public static final int MAX_CHANGE = 5;

    /**
     * A variable measured in milliseconds.
     */
    public static final int DELAY_MIN_CHANGE = 100;

    /**
     * A variable measured in milliseconds.
     */
    public static final int DELAY_MAX_CHANGE = 500;


    private final Crop crop;
    private final String cropName;
    private final String particleName;
    private final ParticleConfigSection particleSection;


    /**
     * A variable containing the maximum order allowed.
     */
    private int maxOrder;

    /**
     * A variable containing the current order (or index) of the pressed sound.
     */
    private int currentOrder;


    public ParticleMenu(@NotNull CropClick plugin,
                        @NotNull Player player,
                        @NotNull Crop crop,
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

        inventory.setItem(10, getDelayDecreaseItem(DELAY_MAX_CHANGE));
        inventory.setItem(11, getDelayDecreaseItem(DELAY_MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayIncreaseItem(DELAY_MIN_CHANGE));
        inventory.setItem(16, getDelayIncreaseItem(DELAY_MAX_CHANGE));

        inventory.setItem(19, getSpeedDecreaseItem(MAX_CHANGE));
        inventory.setItem(20, getSpeedDecreaseItem(MIN_CHANGE));
        inventory.setItem(22, getSpeedItem());
        inventory.setItem(24, getSpeedIncreaseItem(MIN_CHANGE));
        inventory.setItem(25, getSpeedIncreaseItem(MAX_CHANGE));

        inventory.setItem(28, getAmountDecreaseItem(MAX_CHANGE));
        inventory.setItem(29, getAmountDecreaseItem(MIN_CHANGE));
        inventory.setItem(31, getAmountItem());
        inventory.setItem(33, getAmountIncreaseItem(MIN_CHANGE));
        inventory.setItem(34, getAmountIncreaseItem(MAX_CHANGE));

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
        if (clicked.equals(getDelayIncreaseItem(DELAY_MIN_CHANGE))) {
            increaseParticleDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayIncreaseItem(DELAY_MAX_CHANGE))) {
            increaseParticleDelay(DELAY_MAX_CHANGE);
        }

        if (clicked.equals(getDelayDecreaseItem(DELAY_MIN_CHANGE))) {
            decreaseParticleDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayDecreaseItem(DELAY_MAX_CHANGE))) {
            decreaseParticleDelay(DELAY_MAX_CHANGE);
        }

        // SPEED
        if (clicked.equals(getSpeedIncreaseItem(MIN_CHANGE))) {
            increaseParticleSpeed(MIN_CHANGE);
        }

        if (clicked.equals(getSpeedIncreaseItem(MAX_CHANGE))) {
            increaseParticleSpeed(MAX_CHANGE);
        }

        if (clicked.equals(getSpeedDecreaseItem(MIN_CHANGE))) {
            decreaseParticleSpeed(MIN_CHANGE);
        }

        if (clicked.equals(getSpeedDecreaseItem(MAX_CHANGE))) {
            decreaseParticleSpeed(MAX_CHANGE);
        }

        // AMOUNT
        if (clicked.equals(getAmountIncreaseItem(MIN_CHANGE))) {
            increaseParticleAmount(MIN_CHANGE);
        }

        if (clicked.equals(getAmountIncreaseItem(MAX_CHANGE))) {
            increaseParticleAmount(MAX_CHANGE);
        }

        if (clicked.equals(getAmountDecreaseItem(MIN_CHANGE))) {
            decreaseParticleAmount(MIN_CHANGE);
        }

        if (clicked.equals(getAmountDecreaseItem(MAX_CHANGE))) {
            decreaseParticleAmount(MAX_CHANGE);
        }

        refreshMenu();
    }


    /**
     * Gets the delay {@link ItemStack item}.
     *
     * @return the delay item.
     */
    private @NotNull ItemStack getDelayItem() {
        double delay = particleSection.getDelay(
                cropName,
                particleName
        );

        return new ItemBuilder(Material.CLOCK)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_DELAY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_DELAY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.PARTICLE_DELAY_ITEM_VALUE.get(plugin, delay)
                ))
                .toItemStack();
    }


    /**
     * Gets the speed {@link ItemStack item}.
     *
     * @return the speed item.
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
     * Gets the amount {@link ItemStack item}.
     *
     * @return the amount item.
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
     * Gets the increase order {@link ItemStack item}.
     *
     * @return the increase order item.
     */
    private @NotNull ItemStack getIncreaseOrderItem() {
        int orderAfter = Math.min(currentOrder + 1, maxOrder);

        return new ItemBuilder(Material.HEAVY_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_INCREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_INCREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    /**
     * Gets the decrease order {@link ItemStack item}.
     *
     * @return the decrease order item.
     */
    private @NotNull ItemStack getDecreaseOrderItem() {
        int orderAfter = Math.max(currentOrder - 1, 0);

        return new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_DECREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_DECREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
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
        double delayBefore = particleSection.getDelay(
                cropName,
                particleName
        );
        double delayAfter = Math.min(delayBefore + delay, Particle.MAX_DELAY);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, delay, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, delayAfter))
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
        double delayBefore = particleSection.getDelay(
                cropName,
                particleName
        );
        double delayAfter = Math.max(delayBefore - delay, Particle.MIN_DELAY);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, delay, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    /**
     * Gets the speed increase {@link ItemStack item} based on the provided speed.
     *
     * @param speed the speed to be increased with when clicked.
     *
     * @return the speed increase item.
     */
    private @NotNull ItemStack getSpeedIncreaseItem(int speed) {
        double speedBefore = particleSection.getSpeed(
                cropName,
                particleName
        );
        double speedAfter = Math.min(speedBefore + speed, Particle.MAX_SPEED);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, speed, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, speedAfter))
                .toItemStack();
    }


    /**
     * Gets the speed decreased {@link ItemStack item} based on the provided speed.
     *
     * @param speed the speed to be decreased with when clicked.
     *
     * @return the speed decrease item.
     */
    private @NotNull ItemStack getSpeedDecreaseItem(int speed) {
        double speedBefore = particleSection.getSpeed(
                cropName,
                particleName
        );
        double speedAfter = Math.max(speedBefore - speed, Particle.MIN_SPEED);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, speed, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, speedAfter))
                .toItemStack();
    }


    /**
     * Gets the amount increase {@link ItemStack item} based on the provided amount.
     *
     * @param amount the amount to be increased with when clicked.
     *
     * @return the amount increase item.
     */
    private @NotNull ItemStack getAmountIncreaseItem(int amount) {
        int amountBefore = particleSection.getAmount(
                cropName,
                particleName
        );
        int amountAfter = Math.min(amountBefore + amount, Particle.MAX_AMOUNT);

        return new ItemBuilder(Material.LIME_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, amount, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, amountAfter))
                .toItemStack();
    }


    /**
     * Gets the amount decrease {@link ItemStack item} based on the provided amount.
     *
     * @param amount the amount to be decreased with when clicked.
     *
     * @return the amount decrease item.
     */
    private @NotNull ItemStack getAmountDecreaseItem(int amount) {
        int amountBefore = particleSection.getAmount(
                cropName,
                particleName
        );
        int amountAfter = Math.max(amountBefore - amount, Particle.MIN_AMOUNT);

        return new ItemBuilder(Material.RED_STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, amount, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, amountAfter))
                .toItemStack();
    }


    /**
     * Increases the {@link #particleName current particle} with the provided delay.
     *
     * @param delay the delay to be increased with.
     */
    private void increaseParticleDelay(int delay) {
        double oldDelay = MathUtils.round(
                particleSection.getDelay(cropName, particleName) + delay
        );
        double newDelay = Math.min(oldDelay, Particle.MAX_DELAY);
        particleSection.setDelay(cropName, particleName, newDelay);
    }


    /**
     * Decreases the {@link #particleName current particle} with the provided delay.
     *
     * @param delay the delay to be decreased with.
     */
    private void decreaseParticleDelay(int delay) {
        double oldDelay = MathUtils.round(
                particleSection.getDelay(cropName, particleName) - delay
        );
        double newDelay = Math.max(oldDelay, Particle.MIN_DELAY);
        particleSection.setDelay(cropName, particleName, newDelay);
    }


    /**
     * Increases the {@link #particleName current particle} with the provided speed.
     *
     * @param speed the speed to be increased with.
     */
    private void increaseParticleSpeed(int speed) {
        double oldSpeed = MathUtils.round(
                particleSection.getSpeed(cropName, particleName) + speed
        );
        double newSpeed = Math.min(oldSpeed, Particle.MAX_SPEED);
        particleSection.setSpeed(cropName, particleName, newSpeed);
    }


    /**
     * Decreases the {@link #particleName current particle} with the provided speed.
     *
     * @param speed the speed to be decreased with.
     */
    private void decreaseParticleSpeed(int speed) {
        double oldSpeed = MathUtils.round(
                particleSection.getSpeed(cropName, particleName) - speed
        );
        double newSpeed = Math.max(oldSpeed, Particle.MIN_SPEED);
        particleSection.setSpeed(cropName, particleName, newSpeed);
    }


    /**
     * Increases the {@link #particleName current particle} with the provided amount.
     *
     * @param amount the amount to be increased with.
     */
    private void increaseParticleAmount(int amount) {
        int oldAmount = particleSection.getAmount(cropName, particleName) + amount;
        int newAmount = Math.min(oldAmount, Particle.MAX_AMOUNT);
        particleSection.setAmount(cropName, particleName, newAmount);
    }


    /**
     * Decreases the {@link #particleName current particle} with the provided amount.
     *
     * @param amount the amount to be decreased with.
     */
    private void decreaseParticleAmount(int amount) {
        int oldAmount = particleSection.getAmount(cropName, particleName) - amount;
        int newAmount = Math.max(oldAmount, Particle.MIN_AMOUNT);
        particleSection.setAmount(cropName, particleName, newAmount);
    }

}