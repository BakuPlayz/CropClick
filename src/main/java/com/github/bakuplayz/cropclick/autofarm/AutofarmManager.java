package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.PlayersConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.events.player.link.PlayerUnlinkAutofarmEvent;
import com.github.bakuplayz.cropclick.utils.AutofarmUtil;
import com.github.bakuplayz.cropclick.utils.BlockUtil;
import com.github.bakuplayz.cropclick.utils.PermissionUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class AutofarmManager {

    private final @NotNull CropClick plugin;
    private final @NotNull CropManager cropManager;
    private final @NotNull PlayersConfig playersConfig;
    private final @NotNull AutofarmDataStorage farmStorage;

    private final String cacheName = "farmerID";


    public AutofarmManager(@NotNull CropClick plugin) {
        this.farmStorage = plugin.getFarmData();
        this.cropManager = plugin.getCropManager();
        this.playersConfig = plugin.getPlayersConfig();
        this.plugin = plugin;
    }


    /**
     * This function links an autofarm to a player.
     *
     * @param player            The player who is linking the autofarm.
     * @param cropLocation      The location of the crop block
     * @param containerLocation The location of the chest that will store the crops
     * @param dispenserLocation The location of the dispenser that will be used to plant the crops.
     */
    public void linkAutofarm(@NotNull Player player,
                             @NotNull Location cropLocation,
                             @NotNull Location containerLocation,
                             @NotNull Location dispenserLocation) {
        Autofarm farm = new Autofarm(
                player,
                cropLocation,
                containerLocation,
                dispenserLocation
        );
        if (isEnabled() && PermissionUtil.canLink(player)) {
            Bukkit.getPluginManager().callEvent(new PlayerLinkAutofarmEvent(player, farm));
        }
    }


    /**
     * Link an autofarm to a player.
     *
     * @param player   The player who is linking the autofarm.
     * @param autofarm The autofarm object that you want to link to the player.
     */
    @SuppressWarnings("unused")
    public void linkAutofarm(@NotNull Player player, @NotNull Autofarm autofarm) {
        linkAutofarm(
                player,
                autofarm.getCropLocation(),
                autofarm.getContainerLocation(),
                autofarm.getDispenserLocation()
        );
    }


    /**
     * This function unlinks an autofarm and calls the PlayerUnlinkAutofarmEvent.
     *
     * @param player            The player who is unlinking the autofarm
     * @param cropLocation      The location of the crop block
     * @param containerLocation The location of the chest that the crops will be placed in.
     * @param dispenserLocation The location of the dispenser that will be used to plant the crops.
     */
    @SuppressWarnings("unused")
    public void unlinkAutofarm(@NotNull Player player,
                               @NotNull Location cropLocation,
                               @NotNull Location containerLocation,
                               @NotNull Location dispenserLocation) {
        Autofarm farm = new Autofarm(
                player,
                cropLocation,
                containerLocation,
                dispenserLocation
        );
        Bukkit.getPluginManager().callEvent(new PlayerUnlinkAutofarmEvent(player, farm));
    }


    /**
     * This function unlinks an autofarm from a player.
     *
     * @param player   The player who is unlinking the autofarm.
     * @param autofarm The autofarm that the player is unlinking from.
     */
    public void unlinkAutofarm(@NotNull Player player, @NotNull Autofarm autofarm) {
        Bukkit.getPluginManager().callEvent(new PlayerUnlinkAutofarmEvent(player, autofarm));
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
     * "Find the farm that the given block is a part of."
     * <p>
     * The first thing we do is check if the block is air. If it is, we return null.
     * </p>
     *
     * @param block The block to check.
     *
     * @return The autofarm object that is associated with the block.
     */
    public @Nullable Autofarm findAutofarm(@NotNull Block block) {
        if (BlockUtil.isAir(block)) {
            return null;
        }

        if (hasCachedID(block)) {
            String farmerID = getCachedID(block);
            return farmStorage.findFarmById(farmerID);
        }

        if (AutofarmUtil.isDispenser(block)) {
            return farmStorage.findFarmByDispenser(block);
        }

        if (AutofarmUtil.isContainer(block)) {
            return farmStorage.findFarmByContainer(block);
        }

        // TODO: needs to check the block over in case of crop... (and one around...)
        if (AutofarmUtil.isCrop(cropManager, block)) {
            return farmStorage.findFarmByCrop(block);
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
     * "Get the cached ID of a block, or null if it doesn't exist."
     * <p>
     * The first line of the function is the function declaration. It's a private function, so it can only be called from
     * within the class. It returns a String, and it's called getCachedID. The block parameter is a Block, and it's not
     * null.
     * </p>
     *
     * @param block The block to get the ID of.
     *
     * @return The ID of the block.
     */
    private @Nullable String getCachedID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(cacheName);
        return metas.isEmpty() ? null : metas.get(0).asString();
    }


    /**
     * If the block has metadata with the key `cacheName` and that metadata is not empty, return true.
     *
     * @param block The block to check.
     *
     * @return A boolean value.
     */
    private boolean hasCachedID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(cacheName);
        return block.hasMetadata(cacheName) && !metas.isEmpty();
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
        if (AutofarmUtil.isDispenser(block)) return true;
        if (AutofarmUtil.isContainer(block)) return true;
        return AutofarmUtil.isCrop(cropManager, block);
    }


    /**
     * This function returns a boolean value that is the value of the key 'autofarm.isEnabled' in the config.yml file.
     *
     * @return A boolean value.
     */
    public boolean isEnabled() {
        return plugin.getConfig().getBoolean("autofarm.isEnabled");
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