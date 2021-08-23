package com.github.bakuplayz.cropclick.listeners.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.events.autofarm.AutofarmHarvestCropEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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
        if (block == null) return;
        if (block.getType() == null) return;
        if (block.getType() == Material.AIR) return;

        Crop crop = cropManager.getCrop(block);
        if (crop == null) return;
        if (!crop.isEnabled()) return;
        if (crop.getDrops() == null) return;
        if (crop.getDropAmount() < 0) return;
        if (crop.getDropChance() < 0) return;
        if (crop.getHarvestAge() != crop.getCurrentAge(block)) return;

        Autofarm autofarm = autofarmManager.findAutofarm(block);
        if (autofarm == null) return;
        if (!autofarm.isLinked()) return;
        if (!autofarm.isEnabled()) return;

        Bukkit.getPluginManager().callEvent(new AutofarmHarvestCropEvent(crop, block, autofarm));
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmerHarvestCrop(final @NotNull AutofarmHarvestCropEvent event) {
        Autofarm autofarm = event.getAutofarm();
        Block block = event.getBlock();
        Crop crop = event.getCrop();

        if (!autofarmManager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        if (crop.getDropChance() > crop.getRandomDropChance()) return;

        crop.harvest(autofarm.getContainer());
        crop.replant(block);
    }
}
