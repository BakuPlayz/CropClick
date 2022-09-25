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

    private final CropsConfig cropsConfig;


    public CropConfigSection(@NotNull CropsConfig cropsConfig) {
        super(cropsConfig.getConfig());
        this.cropsConfig = cropsConfig;
    }


    /**
     * It returns true if the crop, with the given name, exists in the config.
     *
     * @param cropName The name of the crop.
     *
     * @return The existing state of the crop in the config.
     */
    public boolean doesExist(@NotNull String cropName) {
        return config.get("crops." + cropName) != null;
    }


    /**
     * It returns the name of the crop's drop or crop name, if the drop name is not existent.
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
     * It sets the name of the drop to the given name.
     *
     * @param cropName The name of the crop.
     * @param newName  The new name of the crop.
     */
    public void setDropName(@NotNull String cropName, @NotNull String newName) {
        config.set("crops." + cropName + ".drop.name", newName);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the amount of drop that should be dropped when a crop is harvested.
     *
     * @param cropName The name of the crop.
     *
     * @return The amount of drop that will be dropped.
     */
    public int getDropAmount(@NotNull String cropName) {
        return getDropAmount(cropName, 0);
    }


    /**
     * It gets the amount of drop that should be dropped when a crop is harvested.
     *
     * @param cropName The name of the crop.
     * @param def      The default value to return if the value is not found.
     *
     * @return The amount of drop that will be dropped.
     */
    public int getDropAmount(@NotNull String cropName, int def) {
        return config.getInt("crops." + cropName + ".drop.amount", def);
    }


    /**
     * It sets the amount of drop should be dropped when a crop is harvested.
     *
     * @param cropName The name of the crop.
     * @param amount   The amount of the drop that should drop.
     */
    public void setDropAmount(@NotNull String cropName, int amount) {
        config.set("crops." + cropName + ".drop.amount", amount);
        cropsConfig.saveConfig();
    }


    /**
     * It gets the chance of a crop dropping drops (as decimal eg. 0.2).
     *
     * @param cropName The name of the crop.
     *
     * @return The chance of a crop dropping drops (as decimal).
     */
    public double getDropChance(@NotNull String cropName) {
        return getDropChance(cropName, 0.0);
    }


    /**
     * It gets the chance of a crop dropping drops (as decimal eg. 0.2).
     *
     * @param cropName The name of the crop.
     * @param def      The default value to return if the config doesn't have the value.
     *
     * @return The chance of a crop dropping a drops (as decimal).
     */
    public double getDropChance(@NotNull String cropName, double def) {
        return config.getDouble("crops." + cropName + ".drop.chance", def) / 100.0d;
    }


    /**
     * It sets the chance of a crop dropping a drop (as decimal eg. 0.2).
     *
     * @param cropName The name of the crop.
     * @param chance   The chance of a crop dropping drops (as decimal).
     */
    public void setDropChance(@NotNull String cropName, double chance) {
        config.set("crops." + cropName + ".drop.chance", chance);
        cropsConfig.saveConfig();
    }


    /**
     * It returns whether the crop, with the given name, is harvestable.
     *
     * @param cropName The name of the crop.
     *
     * @return The harvestable state of the crop.
     */
    public boolean isHarvestable(@NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".isHarvestable", true);
    }


    /**
     * It sets the crop harvestable state of the crop, with the given name, to the given value.
     *
     * @param cropName      The name of the crop.
     * @param isHarvestable Whether the crop can be harvested.
     */
    public void setHarvestable(@NotNull String cropName, boolean isHarvestable) {
        config.set("crops." + cropName + ".isHarvestable", isHarvestable);
        cropsConfig.saveConfig();
    }


    /**
     * It toggles the crop's, with the given name, harvestable state.
     *
     * @param cropName The name of the crop.
     */
    public void toggleHarvest(@NotNull String cropName) {
        setHarvestable(cropName, !isHarvestable(cropName));
    }


    /**
     * It returns whether the crop, with the given name, is linkable.
     *
     * @param cropName The name of the crop.
     *
     * @return The linkable state of the crop.
     */
    public boolean isLinkable(@NotNull String cropName) {
        return isLinkable(cropName, true);
    }


    /**
     * It returns whether the crop, with the given name, is linkable.
     *
     * @param cropName The name of the crop.
     * @param def      The default value to return if the parameter is not found.
     *
     * @return The linkable state of the crop.
     */
    public boolean isLinkable(@NotNull String cropName, boolean def) {
        return config.getBoolean("crops." + cropName + ".isLinkable", def);
    }


    /**
     * It sets the crop's, with the given name, linkable state to the given value.
     *
     * @param cropName The name of the crop.
     * @param linkable Whether the crop can be linked to a crop.
     */
    public void setLinkable(@NotNull String cropName, boolean linkable) {
        config.set("crops." + cropName + ".isLinkable", linkable);
        cropsConfig.saveConfig();
    }


    /**
     * It toggles the crop's, with the given name, linkable state.
     *
     * @param cropName The name of the crop.
     */
    public void toggleLinkable(@NotNull String cropName) {
        setLinkable(cropName, !isLinkable(cropName));
    }


    /**
     * It returns whether the crop, with the given name, should replant itself after being harvested.
     *
     * @param cropName The name of the crop.
     *
     * @return The shouldReplant state of the crop.
     */
    public boolean shouldReplant(@NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".shouldReplant", true);
    }


    /**
     * It sets the crop's, with the given name, replant state to the given value.
     *
     * @param cropName The name of the crop.
     * @param replant  Whether the crop should replant itself.
     */
    public void setReplant(@NotNull String cropName, boolean replant) {
        config.set("crops." + cropName + ".shouldReplant", replant);
        cropsConfig.saveConfig();
    }


    /**
     * It toggles the crop's, with the given name, shouldReplant state.
     *
     * @param cropName The name of the crop.
     */
    public void toggleReplant(@NotNull String cropName) {
        setReplant(cropName, !shouldReplant(cropName));
    }


    /**
     * It returns whether the crop, with the given name, should drop at least one drop.
     *
     * @param cropName The name of the crop.
     *
     * @return The drop's atLeastOne state of the crop.
     */
    public boolean shouldDropAtLeastOne(@NotNull String cropName) {
        return shouldDropAtLeastOne(cropName, true);
    }


    /**
     * It returns whether the crop, with the given name, should drop at least one drop.
     *
     * @param cropName The name of the crop.
     * @param def      The default value to return if the config doesn't have the value.
     *
     * @return The drop's atLeastOne state of the crop.
     */
    public boolean shouldDropAtLeastOne(@NotNull String cropName, boolean def) {
        return config.getBoolean("crops." + cropName + ".drop.atLeastOne", def);
    }


    /**
     * It sets the crop's, with the given name, drop's atLeastOne state.
     *
     * @param cropName   The name of the crop.
     * @param atLeastOne If true, the crop will always drop at least one drop.
     */
    public void setDropAtLeastOne(@NotNull String cropName, boolean atLeastOne) {
        config.set("crops." + cropName + ".drop.atLeastOne", atLeastOne);
        cropsConfig.saveConfig();
    }


    /**
     * It toggles the crop's, with the given name, drop's atLeastOne state.
     *
     * @param cropName The name of the crop.
     */
    public void toggleDropAtLeastOne(@NotNull String cropName) {
        setDropAtLeastOne(cropName, !shouldDropAtLeastOne(cropName));
    }

}