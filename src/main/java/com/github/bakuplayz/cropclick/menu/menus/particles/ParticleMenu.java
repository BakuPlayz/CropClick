package com.github.bakuplayz.cropclick.menu.menus.particles;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.settings.ParticlesMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MathUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class ParticleMenu extends Menu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int DELAY_MIN_CHANGE = 100; // in milliseconds
    private final int DELAY_MAX_CHANGE = 500; // in milliseconds

    private final int DELAY_MIN = 0; // in milliseconds
    private final int DELAY_MAX = 5000; // in milliseconds

    private final int MIN_SPEED = 0;
    private final int MAX_SPEED = 50;

    private final int MIN_AMOUNT = 0;
    private final int MAX_AMOUNT = 20;


    private final Crop crop;
    private final String cropName;
    private final String particleName;
    private final ParticleConfigSection particleSection;

    private int maxOrder;
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

        inventory.setItem(10, getDelayRemoveItem(DELAY_MAX_CHANGE));
        inventory.setItem(11, getDelayRemoveItem(DELAY_MIN_CHANGE));
        inventory.setItem(13, getDelayItem());
        inventory.setItem(15, getDelayAddItem(DELAY_MIN_CHANGE));
        inventory.setItem(16, getDelayAddItem(DELAY_MAX_CHANGE));

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

        handleBack(clicked, new ParticlesMenu(plugin, player, crop));

        // ORDER
        if (clicked.equals(getIncreaseOrderItem())) {
            particleSection.swapOrder(cropName, currentOrder, ++currentOrder);
        }

        if (clicked.equals(getDecreaseOrderItem())) {
            particleSection.swapOrder(cropName, currentOrder, --currentOrder);
        }

        // DELAY
        if (clicked.equals(getDelayAddItem(DELAY_MIN_CHANGE))) {
            addParticleDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayAddItem(DELAY_MAX_CHANGE))) {
            addParticleDelay(DELAY_MAX_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(DELAY_MIN_CHANGE))) {
            removeParticleDelay(DELAY_MIN_CHANGE);
        }

        if (clicked.equals(getDelayRemoveItem(DELAY_MAX_CHANGE))) {
            removeParticleDelay(DELAY_MAX_CHANGE);
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
        double delay = particleSection.getDelay(
                cropName,
                particleName
        );

        return new ItemUtil(Material.CLOCK)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_DELAY_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_DELAY_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.PARTICLE_DELAY_ITEM_VALUE.get(plugin, delay)
                ))
                .toItemStack();
    }


    private @NotNull ItemStack getSpeedItem() {
        double speed = particleSection.getSpeed(
                cropName,
                particleName
        );

        return new ItemUtil(Material.GREEN_STAINED_GLASS_PANE)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_SPEED_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_SPEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.PARTICLE_SPEED_ITEM_VALUE.get(plugin, speed)
                ))
                .toItemStack();
    }


    private @NotNull ItemStack getAmountItem() {
        int amount = particleSection.getAmount(
                cropName,
                particleName
        );

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.PARTICLE_AMOUNT_ITEM_VALUE.get(plugin, amount)
                ))
                .toItemStack();
    }


    private @NotNull ItemStack getIncreaseOrderItem() {
        int orderAfter = Math.min(currentOrder + 1, maxOrder);

        return new ItemUtil(Material.IRON_PLATE)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_INCREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_INCREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDecreaseOrderItem() {
        int orderAfter = Math.max(currentOrder - 1, 0);

        return new ItemUtil(Material.GOLD_PLATE)
                .setName(plugin, LanguageAPI.Menu.PARTICLE_DECREASE_ORDER_ITEM_NAME)
                .setLore(LanguageAPI.Menu.PARTICLE_DECREASE_ORDER_ITEM_AFTER.get(plugin, orderAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayAddItem(int delayChange) {
        double delayBefore = particleSection.getDelay(
                cropName,
                particleName
        );
        double delayAfter = Math.min(delayBefore + delayChange, DELAY_MAX);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, delayChange, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getDelayRemoveItem(int delayChange) {
        double delayBefore = particleSection.getDelay(
                cropName,
                particleName
        );
        double delayAfter = Math.max(delayBefore - delayChange, DELAY_MIN);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, delayChange, "Delay"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, delayAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getSpeedAddItem(int speedChange) {
        double speedBefore = particleSection.getSpeed(
                cropName,
                particleName
        );
        double speedAfter = Math.min(speedBefore + speedChange, MAX_SPEED);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, speedChange, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, speedAfter))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getSpeedRemoveItem(int speedChange) {
        double speedBefore = particleSection.getSpeed(
                cropName,
                particleName
        );
        double speedAfter = Math.max(speedBefore - speedChange, MIN_SPEED);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, speedChange, "Speed"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, speedAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getAmountAddItem(int amountChange) {
        int amountBefore = particleSection.getAmount(
                cropName,
                particleName
        );
        int amountAfter = Math.min(amountBefore + amountChange, MAX_AMOUNT);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.PARTICLE_ADD_ITEM_NAME.get(plugin, amountChange, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_ADD_ITEM_AFTER.get(plugin, amountAfter))
                .toItemStack();
    }


    private @NotNull ItemStack getAmountRemoveItem(int amountChange) {
        int amountBefore = particleSection.getAmount(
                cropName,
                particleName
        );
        int amountAfter = Math.max(amountBefore - amountChange, MIN_AMOUNT);

        return new ItemUtil(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_NAME.get(plugin, amountChange, "Amount"))
                .setLore(LanguageAPI.Menu.PARTICLE_REMOVE_ITEM_AFTER.get(plugin, amountAfter))
                .toItemStack();
    }


    public void addParticleDelay(int delay) {
        double oldDelay = MathUtil.round(
                particleSection.getDelay(cropName, particleName) + delay,
                2
        );
        double newDelay = Math.min(oldDelay, DELAY_MAX);
        particleSection.setDelay(cropName, particleName, newDelay);
    }


    public void removeParticleDelay(int delay) {
        double oldDelay = MathUtil.round(
                particleSection.getDelay(cropName, particleName) - delay,
                2
        );
        double newDelay = Math.max(oldDelay, DELAY_MIN);
        particleSection.setDelay(cropName, particleName, newDelay);
    }


    public void addParticleSpeed(int speed) {
        double oldSpeed = MathUtil.round(
                particleSection.getSpeed(cropName, particleName) + speed,
                2
        );
        double newSpeed = Math.min(oldSpeed, MAX_SPEED);
        particleSection.setSpeed(cropName, particleName, newSpeed);
    }


    public void removeParticleSpeed(int speed) {
        double oldSpeed = MathUtil.round(
                particleSection.getSpeed(cropName, particleName) - speed,
                2
        );
        double newSpeed = Math.max(oldSpeed, MIN_SPEED);
        particleSection.setSpeed(cropName, particleName, newSpeed);
    }


    public void addParticleAmount(int amount) {
        int oldAmount = particleSection.getAmount(cropName, particleName) + amount;
        int newAmount = Math.min(oldAmount, MAX_AMOUNT);
        particleSection.setAmount(cropName, particleName, newAmount);
    }


    public void removeParticleAmount(int amount) {
        int oldAmount = particleSection.getAmount(cropName, particleName) - amount;
        int newAmount = Math.max(oldAmount, MIN_AMOUNT);
        particleSection.setAmount(cropName, particleName, newAmount);
    }

}