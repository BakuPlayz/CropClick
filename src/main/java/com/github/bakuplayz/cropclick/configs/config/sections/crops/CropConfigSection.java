package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropConfigSection extends ConfigSection {

    // TODO: Check through all the comments... I cannot be bothered right now.

    private final CropsConfig cropsConfig;


    public CropConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
    }


    /**
     * This function returns true if the crop exists in the config, and false if it doesn't.
     *
     * @param cropName The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean doesExist(@NotNull String cropName) {
        return config.get("crops." + cropName) != null;
    }


    /**
     * Returns the name of the crop drop item.
     *
     * @param cropName The name of the crop.
     *
     * @return The drop name.
     */
    @Contract("_ -> new")
    public @NotNull String getDropName(@NotNull String cropName) {
        return MessageUtils.colorize(
                config.getString(
                        "crops." + cropName + ".drop.name",
                        cropName
                )
        );
    }


    /**
     * It sets the name of the drop of a crop.
     *
     * @param cropName The name of the crop.
     * @param newName  The new name of the crop.
     */
    public void setDropName(@NotNull String cropName, @NotNull String newName) {
        config.set("crops." + cropName + ".drop.name", newName);
        cropsConfig.saveConfig();
    }


    /**
     * Get the amount of items that a crop will drop when harvested.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of items that will be dropped when the crop is harvested.
     */
    public int getDropAmount(@NotNull String cropName) {
        return getDropAmount(cropName, 0);
    }


    /**
     * Get the amount of items that a crop will drop when harvested.
     *
     * @param cropName The name of the crop.
     * @param def      The default value to return if the value is not found.
     *
     * @return The amount of items that will be dropped when the crop is harvested.
     */
    public int getDropAmount(@NotNull String cropName, int def) {
        return config.getInt("crops." + cropName + ".drop.amount", def);
    }


    /**
     * It sets the amount of items that a crop will drop when harvested
     *
     * @param cropName The name of the crop.
     * @param amount   The amount of the item that will be dropped.
     */
    public void setDropAmount(@NotNull String cropName, int amount) {
        config.set("crops." + cropName + ".drop.amount", amount);
        cropsConfig.saveConfig();
    }


    /**
     * Get the chance of a crop dropping an item.
     *
     * @param cropName The name of the crop.
     *
     * @return The chance of a crop dropping an item.
     */
    public double getDropChance(@NotNull String cropName) {
        return getDropChance(cropName, 0.0);
    }


    /**
     * Get the chance of a crop dropping its item.
     *
     * @param cropName The name of the crop.
     * @param def      The default value to return if the config doesn't have the value.
     *
     * @return The chance of a crop dropping an item.
     */
    public double getDropChance(@NotNull String cropName, double def) {
        return config.getDouble("crops." + cropName + ".drop.chance", def) / 100.0d;
    }


    /**
     * Sets the chance of a crop dropping an item.
     *
     * @param cropName The name of the crop.
     * @param chance   The chance that the crop will drop the item.
     */
    public void setDropChance(@NotNull String cropName, double chance) {
        config.set("crops." + cropName + ".drop.chance", chance);
        cropsConfig.saveConfig();
    }


    /**
     * Returns whether the crop with the given name is harvestable.
     *
     * @param cropName The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean isHarvestable(@NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".isHarvestable", true);
    }


    /**
     * Set the crop harvestable state of the crop with the given name to the given value.
     *
     * @param cropName      The name of the crop.
     * @param isHarvestable Whether the crop can be harvested.
     */
    public void setHarvestable(@NotNull String cropName, boolean isHarvestable) {
        config.set("crops." + cropName + ".isHarvestable", isHarvestable);
        cropsConfig.saveConfig();
    }


    /**
     * It toggles the crop's harvestable state.
     *
     * @param cropName The name of the crop.
     */
    public void toggleHarvest(@NotNull String cropName) {
        setHarvestable(cropName, !isHarvestable(cropName));
    }


    /**
     * Returns whether the crop with the given name is linkable.
     *
     * @param cropName The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean isLinkable(@NotNull String cropName) {
        return isLinkable(cropName, true);
    }


    /**
     * Returns whether the crop with the given name is linkable.
     *
     * @param cropName The name of the crop.
     * @param def      The default value to return if the parameter is not found.
     *
     * @return A boolean value.
     */
    public boolean isLinkable(@NotNull String cropName, boolean def) {
        return config.getBoolean("crops." + cropName + ".isLinkable", def);
    }


    /**
     * It sets the crop linkable value to the value of the linkable variable
     *
     * @param cropName The name of the crop.
     * @param linkable Whether the crop can be linked to a crop.
     */
    public void setLinkable(@NotNull String cropName, boolean linkable) {
        config.set("crops." + cropName + ".isLinkable", linkable);
        cropsConfig.saveConfig();
    }


    /**
     * It toggles the crop's linkable state.
     *
     * @param cropName The name of the crop.
     */
    public void toggleLinkable(@NotNull String cropName) {
        setLinkable(cropName, !isLinkable(cropName));
    }


    /**
     * Returns whether the crop should replant itself after being harvested.
     *
     * @param cropName The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean shouldReplant(@NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".shouldReplant", true);
    }


    /**
     * Sets the replant value of a crop to the given value.
     *
     * @param cropName The name of the crop.
     * @param replant  Whether the crop should replant itself
     */
    public void setReplant(@NotNull String cropName, boolean replant) {
        config.set("crops." + cropName + ".shouldReplant", replant);
        cropsConfig.saveConfig();
    }


    /**
     * Toggle the value of the shouldReplant key for the crop with the given name.
     *
     * @param cropName The name of the crop.
     */
    public void toggleReplant(@NotNull String cropName) {
        setReplant(cropName, !shouldReplant(cropName));
    }


    /**
     * Returns whether the crop should drop at least one item.
     *
     * @param cropName The name of the crop.
     *
     * @return A boolean value.
     */
    public boolean shouldDropAtLeastOne(@NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".atLeastOne", true);
    }


    /**
     * Sets the crop's drop atLeastOne value.
     *
     * @param cropName   The name of the crop.
     * @param atLeastOne If true, the crop will always drop at least one item.
     */
    public void setDropAtLeastOne(@NotNull String cropName, boolean atLeastOne) {
        config.set("crops." + cropName + ".drop.atLeastOne", atLeastOne);
        cropsConfig.saveConfig();
    }


    /**
     * It toggles the dropAtLeastOne boolean for the crop with the given name.
     *
     * @param cropName The name of the crop.
     */
    public void toggleDropAtLeastOne(@NotNull String cropName) {
        setDropAtLeastOne(cropName, !shouldDropAtLeastOne(cropName));
    }

}