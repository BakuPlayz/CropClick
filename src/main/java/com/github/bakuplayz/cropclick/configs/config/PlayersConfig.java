package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayersConfig extends Config {

    public PlayersConfig(@NotNull CropClick plugin) {
        super(plugin, "players.yml");
    }


    /**
     * If the block is a crop, set the player's crop location to the block's location.
     *
     * @param player The player who is selecting the crop.
     * @param block  The block that the player clicked on.
     */
    public void selectCrop(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isCrop(plugin.getCropManager(), block)) return;
        config.set(player.getUniqueId() + ".crop", block.getLocation());
        saveConfig();
    }


    /**
     * If the block is a container, set the player's container location to the block's location.
     *
     * @param player The player who is selecting the container.
     * @param block  The block that the player clicked on.
     */
    public void selectContainer(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isContainer(block)) return;
        config.set(player.getUniqueId() + ".container", block.getLocation());
        saveConfig();
    }


    /**
     * If the block is a dispenser, save the location of the dispenser in the config.
     *
     * @param player The player who is selecting the dispenser.
     * @param block  The block that the player clicked on.
     */
    public void selectDispenser(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isDispenser(block)) return;
        config.set(player.getUniqueId() + ".dispenser", block.getLocation());
        saveConfig();
    }


    /**
     * If the block is a crop, set the player's crop to null.
     *
     * @param player The player who is deselecting the crop.
     * @param block  The block that the player is trying to select.
     */
    public void deselectCrop(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isCrop(plugin.getCropManager(), block)) return;
        config.set(player.getUniqueId() + ".crop", null);
        saveConfig();
    }


    /**
     * If the block is a container, set the player's container to null.
     *
     * @param player The player who is selecting the container.
     * @param block  The block that the player is trying to select.
     */
    public void deselectContainer(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isContainer(block)) return;
        config.set(player.getUniqueId() + ".container", null);
        saveConfig();
    }


    /**
     * If the block is a dispenser, remove the dispenser from the player's config.
     *
     * @param player The player who is selecting the dispenser.
     * @param block  The block that the player is trying to select.
     */
    public void deselectDispenser(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isDispenser(block)) return;
        config.set(player.getUniqueId() + ".dispenser", null);
        saveConfig();
    }


    /**
     * It removes all the links between the player's selected blocks and the other players' selected blocks, if they clash.
     *
     * @param player The player whose selections are being cleared.
     */
    public void deselectAll(@NotNull Player player) {
        Location crop = getSelectedCrop(player);
        Location container = getSelectedContainer(player);
        Location dispenser = getSelectedDispenser(player);

        config.set(player.getUniqueId().toString(), null);

        assert crop != null;
        assert container != null;
        assert dispenser != null;

        for (String path : config.getKeys(true)) {
            String[] sections = path.split("\\.");
            String playerID = sections[0];
            if (path.equals("crop")) nullifyLinked(crop, "crop", playerID);
            if (path.equals("container")) nullifyLinked(container, "container", playerID);
            if (path.equals("dispenser")) nullifyLinked(dispenser, "dispenser", playerID);
        }

        saveConfig();
    }


    // TODO: Explain function :) and a better name


    /**
     * If the location is the same as the location in the config, set the location in the config to null.
     *
     * @param location The location to check if it's linked to the player.
     * @param key      The key of the location you want to nullify.
     * @param playerID The player's UUID.
     */
    private void nullifyLinked(@NotNull Location location,
                               @NotNull String key,
                               @NotNull String playerID) {
        Location loc = (Location) config.get(playerID + "." + key);
        if (location.equals(loc)) {
            config.set(playerID + "." + key, null);
        }
    }


    /**
     * This function returns the location of the crop that the player has selected, or null if the player has not selected
     * a crop.
     *
     * @param player The player whose crop you want to get.
     *
     * @return A location.
     */
    public @Nullable Location getSelectedCrop(@NotNull Player player) {
        return (Location) config.get(player.getUniqueId() + ".crop");
    }


    /**
     * This function returns the location of the container that the player has selected, or null if the player has not
     * selected a container.
     *
     * @param player The player whose container you want to get.
     *
     * @return A Location.
     */
    public @Nullable Location getSelectedContainer(@NotNull Player player) {
        return (Location) config.get(player.getUniqueId() + ".container");
    }


    /**
     * This function returns the location of the dispenser that the player has selected, or null if the player has not
     * selected a dispenser.
     *
     * @param player The player whose dispenser you want to get.
     *
     * @return A Location.
     */
    public @Nullable Location getSelectedDispenser(@NotNull Player player) {
        return (Location) config.get(player.getUniqueId() + ".dispenser");
    }


    /**
     * If the block is a crop, and the player has a crop selected, and the block is the same as the player's selected crop,
     * then return true. Otherwise, if not found return false.
     *
     * @param player The player who is selecting the crop.
     * @param block  The block that the player is looking at.
     *
     * @return A boolean value.
     */
    public boolean isCropSelected(@NotNull Player player, @NotNull Block block) {
        if (AutofarmUtils.isCrop(plugin.getCropManager(), block)) {
            Location crop = (Location) config.get(player.getUniqueId() + ".crop");
            return crop != null && crop.equals(block.getLocation());
        }
        return false;
    }


    /**
     * If the block is a container, and the player has a container selected, and the container selected is the same as the
     * block, then return true. Otherwise, if not found return false.
     *
     * @param player The player who is using the plugin.
     * @param block  The block that is being checked.
     *
     * @return A boolean value.
     */
    public boolean isContainerSelected(@NotNull Player player, @NotNull Block block) {
        if (AutofarmUtils.isContainer(block)) {
            Location container = (Location) config.get(player.getUniqueId() + ".container");
            return container != null && container.equals(block.getLocation());
        }
        return false;
    }


    /**
     * If the block is a dispenser, return true if the player has selected it, otherwise return false.
     *
     * @param player The player who is selecting the dispenser
     * @param block  The block that the player is looking at.
     *
     * @return A boolean value.
     */
    public boolean isDispenserSelected(@NotNull Player player, @NotNull Block block) {
        if (AutofarmUtils.isDispenser(block)) {
            Location dispenser = (Location) config.get(player.getUniqueId() + ".dispenser");
            return dispenser != null && dispenser.equals(block.getLocation());
        }
        return false;
    }


    /**
     * Returns true if the player is not in the list of disabled players.
     *
     * @param playerID The player's UUID
     *
     * @return A boolean value.
     */
    public boolean isEnabled(@NotNull String playerID) {
        return !getDisabledPlayers().contains(playerID);
    }


    /**
     * It returns a list of all the players who have the plugin disabled.
     *
     * @return A list of strings.
     */
    public @NotNull List<String> getDisabledPlayers() {
        return config.getStringList("disabled");
    }


    /**
     * Toggle the player's ability on/off to use the plugin.
     *
     * @param playerID The player's UUID.
     */
    public void togglePlayer(@NotNull String playerID) {
        List<String> toggles = getDisabledPlayers();
        if (toggles.contains(playerID)) {
            toggles.remove(playerID);
        } else {
            toggles.add(playerID);
        }
        config.set("disabled", toggles);
        saveConfig();
    }

}