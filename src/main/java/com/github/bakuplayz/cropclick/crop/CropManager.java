package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.*;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.crop.exceptions.CropTypeDuplicateException;
import com.github.bakuplayz.cropclick.utils.VersionUtil;
import lombok.Getter;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CropManager {

    private final @Getter List<Crop> crops;


    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to obsolete a duplication issue with crops.
     */
    private final @Getter HashMap<Crop, Long> harvestedCrops;


    public CropManager(@NotNull CropsConfig config) {
        harvestedCrops = new HashMap<>();
        crops = new ArrayList<>();
        registerVanillaCrops(config);
    }


    /**
     * It adds all the vanilla crops to the list of crops.
     *
     * @param config The config file for the crop.
     */
    private void registerVanillaCrops(@NotNull CropsConfig config) {
        if (VersionUtil.supportsBeetroots()) {
            crops.add(new Beetroot(config));
        }
        crops.add(new Cactus(config));
        crops.add(new Carrot(config));
        crops.add(new CocoaBean(config));
        crops.add(new Melon(config));
        crops.add(new Pumpkin(config));
        crops.add(new Potato(config));
        crops.add(new SugarCane(config));
        crops.add(new Wheat(config));
    }


    /**
     * If the crop type is already registered, throw an exception. Otherwise, register it.
     *
     * @param crop The crop to register.
     */
    @SuppressWarnings("unused")
    public void registerCrop(@NotNull Crop crop)
            throws CropTypeDuplicateException {

        List<Crop> foundCrops = crops.stream()
                .filter(c -> filterByType(c, crop))
                .collect(Collectors.toList());

        if (foundCrops.size() > 1) {
            throw new CropTypeDuplicateException(foundCrops);
        }

        crops.add(crop);
    }


    /**
     * It removes a crop from the list of crops.
     *
     * @param crop The crop to register.
     */
    @SuppressWarnings("unused")
    public void unregisterCrop(@NotNull Crop crop) {
        crops.remove(crop);
    }


    /**
     * Return true if any of the crops in the list match the block type.
     *
     * @param block The block to check.
     *
     * @return A boolean value.
     */
    public boolean isCrop(@NotNull Block block) {
        return crops.stream().anyMatch(crop -> filterByType(crop, block));
    }


    /**
     * If the crop and block are not null, return true.
     *
     * @param crop  The crop that is being validated.
     * @param block The block that was broken.
     *
     * @return A boolean value.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean validate(Crop crop, Block block) {
        return crop != null && block != null;
    }


    /**
     * Find the first crop that matches the given block, or return null if none are found.
     *
     * @param block The block to find the crop for.
     *
     * @return A crop object.
     */
    @Nullable
    public Crop findByBlock(@NotNull Block block) {
        return crops.stream()
                .filter(crop -> filterByType(crop, block))
                .findFirst().orElse(null);
    }


    /**
     * Return the first crop whose name matches the given name, or null if no crop matches.
     *
     * @param name The name of the crop to find.
     *
     * @return A crop with the name specified in the parameter.
     */
    @Nullable
    @SuppressWarnings("unused")
    public Crop findByName(@NotNull String name) {
        return crops.stream()
                .filter(crop -> crop.getName().equals(name))
                .findFirst().orElse(null);
    }


    /**
     * This function returns true if the crop's clickable type is the same as the block's type.
     *
     * @param crop  The crop that is being checked.
     * @param block The block that was clicked.
     *
     * @return A boolean value.
     */
    private boolean filterByType(@NotNull Crop crop, @NotNull Block block) {
        return crop.getClickableType() == block.getType();
    }


    /**
     * This function returns true if the two crops are of the same type.
     *
     * @param c1 The crop that is being compared to c2.
     * @param c2 The crop that is being clicked on.
     *
     * @return A boolean value.
     */
    private boolean filterByType(@NotNull Crop c1, @NotNull Crop c2) {
        return c1.getClickableType() == c2.getClickableType();
    }


    /**
     * Returns the *real* amount of crops.
     *
     * @return The amount of crops.
     */
    public int getAmountOfCrops() {
        return getCrops().size() + 1;
    }

}