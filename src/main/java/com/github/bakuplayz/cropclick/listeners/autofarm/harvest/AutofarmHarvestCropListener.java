package com.github.bakuplayz.cropclick.listeners.autofarm.harvest;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.base.RoofCrop;
import com.github.bakuplayz.cropclick.crop.crops.base.TallCrop;
import com.github.bakuplayz.cropclick.crop.crops.ground.SeaPickle;
import com.github.bakuplayz.cropclick.events.autofarm.harvest.AutofarmHarvestCropEvent;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.bukkit.material.Directional;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;


/**
 * (DESCRIPTION)
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
     * If the block is a crop, cancel the event and call a new event.
     *
     * @param event The event that is being called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmInteractWithCrop(@NotNull BlockDispenseEvent event) {
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

        if (AutofarmUtils.componentHasMeta(block)) {
            AutofarmUtils.addMeta(plugin, autofarm);
        }

        Block facing = findDispenserFacing(block);
        Crop crop = cropManager.findByBlock(facing);
        if (!cropManager.validate(crop, facing)) {
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
     * If the crop has a drop, harvest it and replant it.
     *
     * @param event The event that is being called.
     */
    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmHarvestCrop(@NotNull AutofarmHarvestCropEvent event) {
        if (event.isCancelled()) return;

        Crop crop = event.getCrop();
        Block block = event.getBlock();
        Autofarm autofarm = event.getAutofarm();
        Container container = autofarm.getContainer();

        if (container == null) {
            return;
        }

        if (crop instanceof TallCrop) {
            TallCrop tallCrop = (TallCrop) crop;
            tallCrop.harvestAll(container, block, crop);

        } else if (crop instanceof RoofCrop) {
            RoofCrop roofCrop = (RoofCrop) crop;
            roofCrop.harvestAll(container, block, crop);

        } else if (crop instanceof SeaPickle) {
            SeaPickle seaPickle = (SeaPickle) crop;
            seaPickle.harvestAll(container, block, crop);

        } else {
            crop.harvest(container);
        }

        crop.replant(block);

        System.out.println("Autofarm -- Harvest");

        harvestedCrops.remove(crop);
    }


    /**
     * Get the block that the dispenser is facing.
     *
     * @param block The block that the dispenser is on.
     *
     * @return The block that the dispenser is facing.
     */
    private Block findDispenserFacing(@NotNull Block block) {
        return block.getRelative(
                ((Directional) block.getState().getData()).getFacing()
        );
    }

}