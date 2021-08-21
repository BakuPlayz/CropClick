package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.metadata.AutofarmMetadata;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class AutofarmManager {

    private final @NotNull CropClick plugin;
    private final @NotNull AutofarmDataStorage autofarmStorage;

    public AutofarmManager(final @NotNull CropClick plugin) {
        this.autofarmStorage = plugin.getAutofarmDataStorage();
        this.plugin = plugin;
    }

    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("isEnabled.autofarm");
    }

    // TODO: Change the method name to add or keep the current. (Maybe remove this)
    public void linkAutofarm(final @NotNull Player player,
                             final @NotNull Location cropLocation,
                             final @NotNull Location containerLocation,
                             final @NotNull Location dispenserLocation) {
        Autofarm autofarm = new Autofarm(player, cropLocation, containerLocation, dispenserLocation);
        Bukkit.getPluginManager().callEvent(new PlayerLinkAutofarmEvent(player, autofarm));
    }

    // TODO: Change the method name to add or keep the current.
    public void linkAutofarm(final @NotNull Player player,
                             final @NotNull Autofarm autofarm) {
        Bukkit.getPluginManager().callEvent(new PlayerLinkAutofarmEvent(player, autofarm));
    }

    // TODO: Change the method name to remove or keep the current.
    public void unlinkAutofarm(final @NotNull Player player,
                               final @NotNull Autofarm autofarm) {
        Bukkit.getPluginManager().callEvent(new PlayerUnlinkAutofarmEvent(player, autofarm));
    }

    public @Nullable Autofarm findAutofarm(final @NotNull Block block) {
        if (block.hasMetadata("farmerID")) {
            List<MetadataValue> values = block.getMetadata("farmerID");
            return getAutofarm(values.get(0).asString());
        }
        if (block.hasMetadata("autofarm")) return getCachedAutofarm(block);
        return block.getType() != Material.DISPENSER ? null : getAutofarm(block.getLocation());
    }

    private @Nullable Autofarm getAutofarm(final @NotNull String farmerID) {
        return autofarmStorage.getAutofarm(farmerID);
    }

    private @Nullable Autofarm getAutofarm(final @NotNull Location location) {
        return autofarmStorage.getAutofarm(location);
    }

    private @Nullable Autofarm getCachedAutofarm(final @NotNull Block block) {
        List<MetadataValue> values = block.getMetadata("autofarm");
        return ((AutofarmMetadata) values.get(0)).asAutofarm();
    }
}
