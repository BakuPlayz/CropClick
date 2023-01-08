package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.configs.Config;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menu.menus.links.Component;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.CollectionUtils;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;


/**
 * A class representing the YAML file: 'players.yml'.
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
     * Selects the {@link Block provided block}, if it is a {@link Crop crop} block.
     *
     * @param player the player who selected the block.
     * @param block  the block that was selected.
     */
    public void selectCrop(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isCrop(plugin.getCropManager(), block)) return;
        config.set(player.getUniqueId() + ".crop", block.getLocation());
        saveConfig();
    }


    /**
     * Selects the {@link Block provided block}, if it is a {@link Container container} block.
     *
     * @param player the player who selected the block.
     * @param block  the block that was selected.
     */
    public void selectContainer(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isContainer(block)) return;
        config.set(player.getUniqueId() + ".container", block.getLocation());
        saveConfig();
    }


    /**
     * Selects the {@link Block provided block}, if it is a {@link Dispenser dispenser} block.
     *
     * @param player the player who selected the block.
     * @param block  the block that was selected.
     */
    public void selectDispenser(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isDispenser(block)) return;
        config.set(player.getUniqueId() + ".dispenser", block.getLocation());
        saveConfig();
    }


    /**
     * Deselects the {@link Block provided block}, if it is a {@link Crop crop} block.
     *
     * @param player the player who deselected the block.
     * @param block  the block that was deselected.
     */
    public void deselectCrop(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isCrop(plugin.getCropManager(), block)) return;
        config.set(player.getUniqueId() + ".crop", null);
        saveConfig();
    }


    /**
     * Deselects the {@link Block provided block}, if it is a {@link Container container} block.
     *
     * @param player the player who deselected the block.
     * @param block  the block that was deselected.
     */
    public void deselectContainer(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isContainer(block)) return;
        config.set(player.getUniqueId() + ".container", null);
        saveConfig();
    }


    /**
     * Deselects the {@link Block provided block}, if it is a {@link Dispenser dispenser} block.
     *
     * @param player the player who deselected the block.
     * @param block  the block that was deselected.
     */
    public void deselectDispenser(@NotNull Player player, @NotNull Block block) {
        if (!AutofarmUtils.isDispenser(block)) return;
        config.set(player.getUniqueId() + ".dispenser", null);
        saveConfig();
    }


    /**
     * Deselects all the {@link Player provided player's} selected {@link Component autofarm components}.
     *
     * @param player the player to deselect all the components for.
     */
    public void deselectComponents(@NotNull Player player) {
        Location crop = getSelectedCrop(player);
        Location container = getSelectedContainer(player);
        Location dispenser = getSelectedDispenser(player);

        config.set(player.getUniqueId().toString(), null);

        for (String path : config.getKeys(true)) {
            String[] sections = path.split("\\.");
            String playerID = sections[0];

            if (!path.equals("crops")
                    && !path.equals("container")
                    && !path.equals("dispenser")) {
                continue;
            }

            if (crop == config.get(playerID + ".crop")) {
                config.set(playerID + ".crop", null);
            }

            if (container == config.get(playerID + ".container")) {
                config.set(playerID + ".container", null);
            }

            if (dispenser == config.get(playerID + ".dispenser")) {
                config.set(playerID + ".dispenser", null);
            }
        }

        saveConfig();
    }


    /**
     * Gets the {@link Crop selected crop} for the {@link Player provided player}.
     *
     * @param player the player to get the selected location from.
     *
     * @return the selected crop's location, otherwise null.
     */
    public @Nullable Location getSelectedCrop(@NotNull Player player) {
        return (Location) config.get(player.getUniqueId() + ".crop", null);
    }


    /**
     * Gets the {@link Container selected container} for the {@link Player provided player}.
     *
     * @param player the player to get the selected location from.
     *
     * @return the selected container's location, otherwise null.
     */
    public @Nullable Location getSelectedContainer(@NotNull Player player) {
        return (Location) config.get(player.getUniqueId() + ".container", null);
    }


    /**
     * Gets the {@link Dispenser selected dispenser} for the {@link Player provided player}.
     *
     * @param player the player to get the selected location from
     *
     * @return the selected dispenser's location, otherwise null.
     */
    public @Nullable Location getSelectedDispenser(@NotNull Player player) {
        return (Location) config.get(player.getUniqueId() + ".dispenser", null);
    }


    /**
     * Checks whether the {@link Block provided crop block} is selected by the {@link Player provided player}.
     *
     * @param player the player to check.
     * @param block  the crop block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isCropSelected(@NotNull Player player, @NotNull Block block) {
        if (AutofarmUtils.isCrop(plugin.getCropManager(), block)) {
            Location crop = getSelectedCrop(player);
            return crop != null && crop.equals(block.getLocation());
        }
        return false;
    }


    /**
     * Checks whether the {@link Block provided container block} is selected by the {@link Player provided player}.
     *
     * @param player the player to check.
     * @param block  the container block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isContainerSelected(@NotNull Player player, @NotNull Block block) {
        if (AutofarmUtils.isContainer(block)) {
            Location container = getSelectedContainer(player);
            return container != null && container.equals(block.getLocation());
        }
        return false;
    }


    /**
     * Checks whether the {@link Block provided dispenser block} is selected by the {@link Player provided player}.
     *
     * @param player the player to check.
     * @param block  the dispenser block to check.
     *
     * @return true if selected, otherwise false.
     */
    public boolean isDispenserSelected(@NotNull Player player, @NotNull Block block) {
        if (AutofarmUtils.isDispenser(block)) {
            Location dispenser = getSelectedDispenser(player);
            return dispenser != null && dispenser.equals(block.getLocation());
        }
        return false;
    }


    /**
     * Checks whether the {@link OfflinePlayer provided player} is able to use {@link CropClick}.
     *
     * @param player the player to check.
     *
     * @return true if able, otherwise false.
     */
    public boolean isEnabled(@NotNull OfflinePlayer player) {
        return !getDisabledPlayers().contains(player.getUniqueId().toString());
    }


    /**
     * Gets all the {@link OfflinePlayer disabled players' IDs}, meaning all the {@link OfflinePlayer players} who are unable to use {@link CropClick}.
     *
     * @return the disabled players.
     */
    public @NotNull List<String> getDisabledPlayers() {
        return config.getStringList("disabled");
    }


    /**
     * Toggles the {@link OfflinePlayer provided player} based on its ID.
     *
     * @param playerID the player to toggle.
     */
    public void togglePlayer(@NotNull String playerID) {
        config.set("disabled", CollectionUtils.toggleItem(
                getDisabledPlayers(),
                playerID
        ));
        saveConfig();
    }

}