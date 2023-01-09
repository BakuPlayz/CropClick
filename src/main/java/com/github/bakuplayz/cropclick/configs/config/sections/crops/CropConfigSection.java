/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.configs.config.sections.crops;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.ConfigSection;
import com.github.bakuplayz.cropclick.crop.Drop;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the crop configuration section.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropConfigSection extends ConfigSection {

    private final CropsConfig cropsConfig;


    public CropConfigSection(@NotNull CropsConfig config) {
        super(config.getConfig());
        this.cropsConfig = config;
    }


    /**
     * Checks whether the {@link Crop provided crop} exists in the {@link CropsConfig crops config}.
     *
     * @param cropName the name of the crop.
     *
     * @return true if it exists, otherwise false.
     */
    public boolean exists(@NotNull String cropName) {
        return config.get("crops." + cropName) != null;
    }


    /**
     * Gets the {@link Drop drop} name for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the crop's drop name.
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
     * Sets the {@link Drop drop} name for the {@link Crop provided crop} to the provided name.
     *
     * @param cropName the name of the crop.
     * @param name     the new name to set.
     */
    public void setDropName(@NotNull String cropName, @NotNull String name) {
        config.set("crops." + cropName + ".drop.name", name);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link Drop drop} amount for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the crop's drop amount.
     */
    public int getDropAmount(@NotNull String cropName) {
        return getDropAmount(cropName, 0);
    }


    /**
     * Gets the {@link Drop drop} name for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     * @param def      the default amount, if non was found.
     *
     * @return the crop's drop name, otherwise the default.
     */
    public int getDropAmount(@NotNull String cropName, int def) {
        return config.getInt("crops." + cropName + ".drop.amount", def);
    }


    /**
     * Sets the {@link Drop drop} amount for the {@link Crop provided crop} to the provided amount.
     *
     * @param cropName the name of the crop.
     * @param amount   the amount to set.
     */
    public void setDropAmount(@NotNull String cropName, int amount) {
        config.set("crops." + cropName + ".drop.amount", amount);
        cropsConfig.saveConfig();
    }


    /**
     * Gets the {@link Drop drop} chance for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     *
     * @return the crop's drop chance.
     */
    public double getDropChance(@NotNull String cropName) {
        return getDropChance(cropName, 0.0);
    }


    /**
     * Gets the {@link Drop drop} chance for the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     * @param def      the default amount, if non was found.
     *
     * @return the crop's drop chance, otherwise the default.
     */
    public double getDropChance(@NotNull String cropName, double def) {
        return config.getDouble("crops." + cropName + ".drop.chance", def) / 100.0d;
    }


    /**
     * Sets the {@link Drop drop} chance for the {@link Crop provided crop} to the provided chance.
     *
     * @param cropName the name of the crop.
     * @param chance   the chance to set.
     */
    public void setDropChance(@NotNull String cropName, double chance) {
        config.set("crops." + cropName + ".drop.chance", chance);
        cropsConfig.saveConfig();
    }


    /**
     * Checks whether the {@link Crop provided crop} is harvestable.
     *
     * @param cropName the name of the crop.
     *
     * @return true if harvestable, otherwise false.
     */
    public boolean isHarvestable(@NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".isHarvestable", true);
    }


    /**
     * Sets the harvestable state of the {@link Crop provided crop} to the provided state.
     *
     * @param cropName      the name of the crop.
     * @param isHarvestable the harvest state to set.
     */
    public void setHarvestable(@NotNull String cropName, boolean isHarvestable) {
        config.set("crops." + cropName + ".isHarvestable", isHarvestable);
        cropsConfig.saveConfig();
    }


    /**
     * Toggles the harvest state of the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     */
    public void toggleHarvest(@NotNull String cropName) {
        setHarvestable(cropName, !isHarvestable(cropName));
    }


    /**
     * Checks whether the {@link Crop provided crop} is linkable.
     *
     * @param cropName the name of the crop.
     *
     * @return true if linkable, otherwise false.
     */
    public boolean isLinkable(@NotNull String cropName) {
        return isLinkable(cropName, true);
    }


    /**
     * Checks whether the {@link Crop provided crop} is linkable.
     *
     * @param cropName the name of the crop.
     * @param def      the default state, if non was found.
     *
     * @return the linkable state, otherwise the default.
     */
    public boolean isLinkable(@NotNull String cropName, boolean def) {
        return config.getBoolean("crops." + cropName + ".isLinkable", def);
    }


    /**
     * Sets the linkable state of the {@link Crop provided crop} to the provided state.
     *
     * @param cropName   the name of the crop.
     * @param isLinkable the linkable state to set.
     */
    public void setLinkable(@NotNull String cropName, boolean isLinkable) {
        config.set("crops." + cropName + ".isLinkable", isLinkable);
        cropsConfig.saveConfig();
    }


    /**
     * Toggles the linkable state of the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     */
    public void toggleLinkable(@NotNull String cropName) {
        setLinkable(cropName, !isLinkable(cropName));
    }


    /**
     * Checks whether the {@link Crop provided crop} should replant.
     *
     * @param cropName the name of the crop.
     *
     * @return true if it should, otherwise false.
     */
    public boolean shouldReplant(@NotNull String cropName) {
        return config.getBoolean("crops." + cropName + ".shouldReplant", true);
    }


    /**
     * Sets the replant state of the {@link Crop provided crop} to the provided state.
     *
     * @param cropName      the name of the crop.
     * @param shouldReplant the replant state to set.
     */
    public void setReplant(@NotNull String cropName, boolean shouldReplant) {
        config.set("crops." + cropName + ".shouldReplant", shouldReplant);
        cropsConfig.saveConfig();
    }


    /**
     * Toggles the replant state of the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     */
    public void toggleReplant(@NotNull String cropName) {
        setReplant(cropName, !shouldReplant(cropName));
    }


    /**
     * Checks whether the {@link Crop provided crop} should drop at least one drop.
     *
     * @param cropName the name of the crop.
     *
     * @return true if it should, otherwise false.
     */
    public boolean shouldDropAtLeastOne(@NotNull String cropName) {
        return shouldDropAtLeastOne(cropName, true);
    }


    /**
     * Checks whether the {@link Crop provided crop} should drop at least one drop.
     *
     * @param cropName the name of the crop.
     * @param def      the default state, if non was found.
     *
     * @return the drop-at-least-one state, otherwise the default.
     */
    public boolean shouldDropAtLeastOne(@NotNull String cropName, boolean def) {
        return config.getBoolean("crops." + cropName + ".drop.atLeastOne", def);
    }


    /**
     * Sets the drop-at-least-one state of the {@link Crop provided crop} to the provided state.
     *
     * @param cropName             the name of the crop.
     * @param shouldDropAtLeastOne the drop at least one state to set.
     */
    public void setDropAtLeastOne(@NotNull String cropName, boolean shouldDropAtLeastOne) {
        config.set("crops." + cropName + ".drop.atLeastOne", shouldDropAtLeastOne);
        cropsConfig.saveConfig();
    }


    /**
     * Toggles the drop-at-least-one state of the {@link Crop provided crop}.
     *
     * @param cropName the name of the crop.
     */
    public void toggleDropAtLeastOne(@NotNull String cropName) {
        setDropAtLeastOne(cropName, !shouldDropAtLeastOne(cropName));
    }

}