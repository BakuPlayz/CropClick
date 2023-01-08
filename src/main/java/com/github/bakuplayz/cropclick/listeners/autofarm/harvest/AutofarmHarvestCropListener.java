package com.github.bakuplayz.cropclick.listeners.autofarm.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.events.autofarm.harvest.AutofarmHarvestCropEvent;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.material.Directional;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * A listener handling all the harvest {@link Crop crop} events caused by a {@link Autofarm}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmHarvestCropListener implements Listener {

    private final CropClick plugin;

    private final CropManager cropManager;
    private final WorldManager worldManager;
    private final AutofarmManager autofarmManager;

    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    private final HashMap<Crop, Long> harvestedCrops;


    public AutofarmHarvestCropListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.worldManager = plugin.getWorldManager();
        this.cropManager = plugin.getCropManager();
        this.plugin = plugin;
        this.harvestedCrops = cropManager.getHarvestedCrops();
    }


    /**
     * Handles all the {@link Dispenser dispenser} interact at {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onDispenserInteractAtCrop(@NotNull BlockDispenseEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (BlockUtils.isAir(block)) {
            return;
        }

        FarmWorld world = worldManager.findByWorld(block.getWorld());
        if (!worldManager.isAccessible(world)) {
            return;
        }

        if (!world.allowsAutofarms()) {
            return;
        }

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (!autofarmManager.isUsable(autofarm)) {
            return;
        }

        if (!autofarm.isEnabled()) {
            return;
        }

        if (AutofarmUtils.hasCachedID(block)) {
            AutofarmUtils.addCachedID(plugin, autofarm);
        }

        Block facing = findDispenserFacing(block);
        Crop crop = cropManager.findByBlock(facing);
        if (crop == null) {
            return;
        }

        if (harvestedCrops.containsKey(crop)) {
            return;
        }

        if (!crop.isHarvestable()) {
            return;
        }

        if (!crop.isHarvestAge(facing)) {
            return;
        }

        harvestedCrops.put(crop, System.nanoTime());

        Bukkit.getPluginManager().callEvent(
                new AutofarmHarvestCropEvent(crop, facing, autofarm)
        );
    }


    /**
     * Handles all the {@link Autofarm autofarm} harvest {@link Crop crop} events.
     *
     * @param event the event that was fired.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmHarvestCrop(@NotNull AutofarmHarvestCropEvent event) {
        if (event.isCancelled()) return;

        Crop crop = event.getCrop();
        Block block = event.getBlock();
        Autofarm autofarm = event.getAutofarm();
        Container container = autofarm.getContainer();

        harvestedCrops.remove(crop);

        if (container == null) {
            return;
        }

        boolean wasHarvested;
        if (crop instanceof TallCrop) {
            wasHarvested = ((TallCrop) crop).harvestAll(container, block, crop);
        } else {
            wasHarvested = crop.harvest(container);
        }

        if (!wasHarvested) {
            event.setCancelled(true);
            return;
        }

        crop.replant(block);

        if (plugin.isDebugging()) {
            plugin.getLogger()
                  .info(String.format("%s (Autofarm): Called the harvest event!", autofarm.getShortenedID()));
        }
    }


    /**
     * Get the block that the dispenser is facing.
     *
     * @param block The block that the dispenser is on.
     *
     * @return The block that the dispenser is facing.
     */
    private @NotNull Block findDispenserFacing(@NotNull Block block) {
        return block.getRelative(
                ((Directional) block.getState().getData()).getFacing()
        );
    }

}