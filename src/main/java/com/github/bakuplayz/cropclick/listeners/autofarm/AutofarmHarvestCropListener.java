package com.github.bakuplayz.cropclick.listeners.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.Container;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.autofarm.AutofarmHarvestCropEvent;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
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
    private final AutofarmManager autofarmManager;

    public AutofarmHarvestCropListener(final @NotNull CropClick plugin) {
        this.autofarmManager = plugin.getAutofarmManager();
        this.cropManager = plugin.getCropManager();
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmerInteractWithCrop(final @NotNull BlockDispenseEvent event) {
        Block block = event.getBlock();
        if (BlockUtil.isAir(block)) return;

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (!autofarmManager.isAutofarmValid(autofarm)) return;

        Crop crop = cropManager.getCrop(block);
        if (!cropManager.isCropValid(crop, block)) return;

        Bukkit.getPluginManager().callEvent(new AutofarmHarvestCropEvent(crop, block, autofarm));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmerHarvestCrop(final @NotNull AutofarmHarvestCropEvent event) {
        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        Crop crop = event.getCrop();
        Autofarm autofarm = event.getAutofarm();
        Container container = autofarm.getContainer();

        if (!crop.willDrop()) return;

        if (container != null) {
            crop.harvest(container.getInventory());
            crop.replant(event.getBlock());
        }

    }

}