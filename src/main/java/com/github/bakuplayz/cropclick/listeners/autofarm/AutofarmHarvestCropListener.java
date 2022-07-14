package com.github.bakuplayz.cropclick.listeners.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.autofarm.AutofarmHarvestCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDispenseEvent;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class AutofarmHarvestCropListener implements Listener {

    private final CropManager cropManager;
    private final WorldManager worldManager;
    private final AutofarmManager autofarmManager;

    public AutofarmHarvestCropListener(@NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.worldManager = plugin.getWorldManager();
        this.cropManager = plugin.getCropManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmInteractWithCrop(@NotNull BlockDispenseEvent event) {
        if (event.isCancelled()) return;

        Block block = event.getBlock();
        if (BlockUtil.isAir(block)) {
            return;
        }

        FarmWorld world = worldManager.findByWorld(block.getWorld());
        if (!worldManager.isAccessable(world)) {
            return;
        }

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (!autofarmManager.isUsable(autofarm)) {
            return;
        }

        Crop crop = cropManager.findByBlock(block);
        if (!cropManager.validate(crop, block)) {
            return;
        }

        event.setCancelled(true);

        Bukkit.getPluginManager().callEvent(new AutofarmHarvestCropEvent(crop, block, autofarm));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmHarvestCrop(@NotNull AutofarmHarvestCropEvent event) {
        if (event.isCancelled()) return;

        Crop crop = event.getCrop();
        Autofarm autofarm = event.getAutofarm();
        Container container = autofarm.getContainer();

        if (!crop.hasDrops()) {
            event.setCancelled(true);
            return;
        }

        if (container != null) {
            crop.harvest(container);
            crop.replant(event.getBlock());
        }

    }

}