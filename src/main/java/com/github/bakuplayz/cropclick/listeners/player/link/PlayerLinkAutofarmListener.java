package com.github.bakuplayz.cropclick.listeners.player.link;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.metadata.AutofarmMetadata;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public final class PlayerLinkAutofarmListener implements Listener {

    private final CropClick plugin;
    private final AutofarmManager manager;
    private final AutofarmDataStorage dataStorage;

    public PlayerLinkAutofarmListener(final @NotNull CropClick plugin) {
        this.dataStorage = plugin.getAutofarmDataStorage();
        this.manager = plugin.getAutofarmManager();
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onAutofarmLink(final @NotNull PlayerLinkAutofarmEvent event) {
        Autofarm autofarm = event.getAutofarm();
        Player player = event.getPlayer();

        if (!player.hasPermission("cropclick.autofarmer.link")) {
            return;
        }

        if (!manager.isEnabled()) {
            event.setCancelled(true);
            return;
        }

        AutofarmMetadata instanceValue = new AutofarmMetadata(plugin, event::getAutofarm);
        AutofarmMetadata farmerIDValue = new AutofarmMetadata(plugin, autofarm::getFarmer);

        Block crop = autofarm.getCropLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block dispenser = autofarm.getDispenserLocation().getBlock();

        crop.setMetadata("farmerID", farmerIDValue);
        container.setMetadata("farmerID", farmerIDValue);
        dispenser.setMetadata("autofarm", instanceValue);

        dataStorage.addAutofarm(autofarm);
    }
}
