package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.BlockUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmManager {

    private final @NotNull CropClick plugin;
    private final @NotNull CropManager cropManager;
    private final @NotNull PlayersConfig playersConfig;
    private final @NotNull AutofarmDataStorage farmStorage;


    public AutofarmManager(@NotNull CropClick plugin) {
        this.playersConfig = plugin.getPlayersConfig();
        this.cropManager = plugin.getCropManager();
        this.farmStorage = plugin.getFarmData();
        this.plugin = plugin;
    }


    /**
     * Selects a crop for the player.
     *
     * @param player The player who is selecting the crop.
     * @param block  The block that the player clicked on.
     */
    public void selectCrop(@NotNull Player player, @NotNull Block block) {
        playersConfig.selectCrop(player, block);
    }


    /**
     * Selects a container for a player.
     *
     * @param player The player who is selecting the container.
     * @param block  The block that the player is selecting.
     */
    public void selectContainer(@NotNull Player player, @NotNull Block block) {
        playersConfig.selectContainer(player, block);
    }


    /**
     * Selects a dispenser for the player.
     *
     * @param player The player who is selecting the dispenser.
     * @param block  The dispenser block that was selected.
     */
    public void selectDispenser(@NotNull Player player, @NotNull Block block) {
        playersConfig.selectDispenser(player, block);
    }


    /**
     * It deselects a crop for a player.
     *
     * @param player The player who is deselecting the crop.
     * @param block  The block that the player clicked on.
     */
    public void deselectCrop(@NotNull Player player, @NotNull Block block) {
        playersConfig.deselectCrop(player, block);
    }


    /**
     * Deselects a container for a player.
     *
     * @param player The player who is selecting the container.
     * @param block  The block that the player is trying to select.
     */
    public void deselectContainer(@NotNull Player player, @NotNull Block block) {
        playersConfig.deselectContainer(player, block);
    }


    /**
     * Deselects a dispenser for a player.
     *
     * @param player The player who is selecting the dispenser.
     * @param block  The block that the player is selecting.
     */
    public void deselectDispenser(@NotNull Player player, @NotNull Block block) {
        playersConfig.deselectDispenser(player, block);
    }


    /**
     * Deselects all the selected items for a player.
     *
     * @param player The player to deselect all the items for.
     */
    public void deselectAll(@NotNull Player player) {
        playersConfig.deselectAll(player);
    }


    /**
     * Find the farm that the given block is a part of.
     *
     * @param block The block to check.
     *
     * @return The autofarm object that is associated with the block.
     */
    public @Nullable Autofarm findAutofarm(@NotNull Block block) {
        if (BlockUtils.isAir(block)) {
            return null;
        }

        if (hasCachedID(block)) {
            String farmerID = getCachedID(block);
            return farmStorage.findFarmById(farmerID);
        }

        if (AutofarmUtils.isDispenser(block)) {
            return farmStorage.findFarmByDispenser(block);
        }

        if (AutofarmUtils.isContainer(block)) {
            return farmStorage.findFarmByContainer(block);
        }

        if (AutofarmUtils.isCrop(cropManager, block)) {
            return farmStorage.findFarmByCrop(block);
        }

        if (BlockUtils.isPlantableSurface(block)) {
            Block blockAbove = block.getRelative(BlockFace.UP);
            if (AutofarmUtils.isCrop(cropManager, blockAbove)) {
                return farmStorage.findFarmByCrop(blockAbove);
            }
        }

        return null;
    }


    /**
     * Returns the selected crop of the player, or null if the player has no selected crop.
     *
     * @param player The player to get the selected crop for.
     *
     * @return A Location object.
     */
    public @Nullable Location getSelectedCrop(@NotNull Player player) {
        return playersConfig.getSelectedCrop(player);
    }


    /**
     * Returns the location of the container that the player has selected, or null if the player has not selected a
     * container.
     *
     * @param player The player who's selected container you want to get.
     *
     * @return A Location object.
     */
    public @Nullable Location getSelectedContainer(@NotNull Player player) {
        return playersConfig.getSelectedContainer(player);
    }


    /**
     * `getSelectedDispenser` returns the location of the dispenser that the player has selected, or null if the player has
     * not selected a dispenser.
     *
     * @param player The player to get the selected dispenser for.
     *
     * @return A Location object.
     */
    public @Nullable Location getSelectedDispenser(@NotNull Player player) {
        return playersConfig.getSelectedDispenser(player);
    }


    /**
     * Get the cached ID of a block, or null if it doesn't exist.
     *
     * @param block The block to get the ID of.
     *
     * @return The ID of the block.
     */
    private @Nullable String getCachedID(@NotNull Block block) {
        return AutofarmUtils.getMetaValue(block);
    }


    /**
     * Returns true if the block has a cached ID.
     *
     * @param block The block that is being checked.
     *
     * @return A boolean value.
     */
    private boolean hasCachedID(@NotNull Block block) {
        return AutofarmUtils.componentHasMeta(block);
    }


    /**
     * Returns true if the given player has the given block selected as a crop.
     *
     * @param player The player who is selecting the crop.
     * @param block  The block that the player is trying to harvest.
     *
     * @return A boolean value.
     */
    public boolean isCropSelected(@NotNull Player player, @NotNull Block block) {
        return playersConfig.isCropSelected(player, block);
    }


    /**
     * Returns true if the given player has the given block selected.
     *
     * @param player The player who is selecting the container.
     * @param block  The block that the player is interacting with.
     *
     * @return A boolean value.
     */
    public boolean isContainerSelected(@NotNull Player player, @NotNull Block block) {
        return playersConfig.isContainerSelected(player, block);
    }


    /**
     * Returns true if the given dispenser is selected by the given player.
     *
     * @param player The player who is selecting the dispenser.
     * @param block  The block that the player is trying to select.
     *
     * @return A boolean value.
     */
    public boolean isDispenserSelected(@NotNull Player player, @NotNull Block block) {
        return playersConfig.isDispenserSelected(player, block);
    }


    /**
     * Returns true if the given autofarm is linked to a farm.
     *
     * @param autofarm The autofarm object that you want to check if it's linked.
     *
     * @return A boolean value.
     */
    public boolean isLinked(Autofarm autofarm) {
        if (autofarm == null) return false;
        return autofarm.isLinked();
    }


    /**
     * If the autofarm is not null, linked, enabled, and has a container, then return true.
     *
     * @param autofarm The autofarm object that is being checked.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean isUsable(Autofarm autofarm) {
        if (autofarm == null) return false;
        if (!autofarm.isLinked()) return false;
        if (!autofarm.isEnabled()) return false;
        if (!autofarm.hasContainer()) return false;
        return isEnabled();
    }


    /**
     * If the block is a dispenser, container, or crop, return true.
     *
     * @param block The block that is being checked.
     *
     * @return A boolean value.
     */
    public boolean isComponent(@NotNull Block block) {
        if (AutofarmUtils.isDispenser(block)) return true;
        if (AutofarmUtils.isContainer(block)) return true;
        return AutofarmUtils.isCrop(cropManager, block);
    }


    /**
     * This function returns a boolean value that is the value of the key 'autofarms.isEnabled' in the config.yml file.
     *
     * @return A boolean value.
     */
    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("autofarms.isEnabled", true);
    }


    /**
     * This function returns the amount of farms in the farmStorage.
     *
     * @return The amount of farms in the farmStorage.
     */
    public int getAmountOfFarms() {
        return farmStorage.getFarms().size();
    }


    /**
     * This function returns a list of all the farms in the farm storage.
     *
     * @return A list of all the farms in the farmStorage.
     */
    public @NotNull List<Autofarm> getAutofarms() {
        return new ArrayList<>(farmStorage.getFarms().values());
    }

}