package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.*;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.exceptions.CropTypeDuplicateException;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
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
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropManager {

    private final @Getter List<Crop> crops;

    private final CropsConfig cropsConfig;


    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    private final @Getter HashMap<Crop, Long> harvestedCrops;


    public CropManager(@NotNull CropsConfig config) {
        this.harvestedCrops = new HashMap<>();
        this.crops = new ArrayList<>();
        this.cropsConfig = config;

        registerVanillaCrops();
    }


    /**
     * It adds all the vanilla crops to the list of crops.
     */
    private void registerVanillaCrops() {
        if (VersionUtils.supportsBeetroots()) {
            registerCrop(new Beetroot(cropsConfig));
        }
        registerCrop(new Cactus(cropsConfig));
        registerCrop(new Carrot(cropsConfig));
        registerCrop(new CocoaBean(cropsConfig));
        registerCrop(new Melon(cropsConfig));
        registerCrop(new NetherWart(cropsConfig));
        registerCrop(new Pumpkin(cropsConfig));
        registerCrop(new Potato(cropsConfig));
        registerCrop(new SugarCane(cropsConfig));
        registerCrop(new Wheat(cropsConfig));
    }


    /**
     * If the crop type is already registered, throw an exception. Otherwise, register it.
     *
     * @param crop The crop to register.
     */
    public void registerCrop(@NotNull Crop crop)
            throws CropTypeDuplicateException {

        List<Crop> duplicateCrops = getDuplicateCrops(crop);

        if (duplicateCrops.size() > 1) {
            throw new CropTypeDuplicateException(duplicateCrops);
        }

        crops.add(crop);
        addSettings(crop);
    }


    /**
     * It adds the crop and seed to the config if they don't already exist.
     *
     * @param crop The crop to add settings for.
     */
    private void addSettings(@NotNull Crop crop) {
        String cropName = crop.getName();

        if (!cropsConfig.doesCropExist(cropName)) {

            // Drop Settings
            if (crop.hasDrop()) {
                Drop drop = crop.getDrop();
                cropsConfig.setCropDropName(cropName, drop.getName());
                cropsConfig.setCropDropAmount(cropName, drop.getAmount());
                cropsConfig.setCropDropChance(cropName, drop.getChance());
                cropsConfig.setDropAtLeastOne(cropName, crop.dropAtLeastOne());
            }

            // Action Settings
            cropsConfig.setCropReplant(cropName, crop.shouldReplant());
            cropsConfig.setCropHarvestable(cropName, crop.isHarvestable());
            cropsConfig.setCropLinkable(cropName, crop.isLinkable());

            // mcMMO Settings
            cropsConfig.setMcMMOExperience(cropName, 0);
            cropsConfig.setMcMMOExperienceReason(cropName, "You harvested " + cropName + ".");

            // JobsReborn Settings
            cropsConfig.setJobsMoney(cropName, 0);
            cropsConfig.setJobsPoints(cropName, 0);
            cropsConfig.setJobsExperience(cropName, 0);
        }

        if (!crop.hasSeed()) {
            return;
        }

        Seed seed = crop.getSeed();
        String seedName = seed.getName();
        if (!cropsConfig.doesSeedExist(seedName)) {
            
            // Drop Settings
            if (crop.hasDrop()) {
                Drop drop = seed.getDrop();
                cropsConfig.setSeedDropName(seedName, drop.getName());
                cropsConfig.setSeedDropAmount(seedName, drop.getAmount());
                cropsConfig.setSeedDropChance(seedName, drop.getChance());
            }

            cropsConfig.setSeedEnabled(seedName, seed.isEnabled());
        }
    }


    /**
     * It removes a crop from the list of crops.
     *
     * @param crop The crop to register.
     */
    @SuppressWarnings("unused")
    public void unregisterCrop(@NotNull Crop crop) {
        crops.remove(crop);
        removeSettings(crop);
    }


    /**
     * It removes the settings for a crop and its seed from the crops.yml file
     *
     * @param crop The crop to remove
     */
    private void removeSettings(@NotNull Crop crop) {
        String cropName = crop.getName();

        if (cropsConfig.doesCropExist(cropName)) {
            cropsConfig.getConfig().set("crops." + cropName, null);
        }

        if (crop.hasSeed()) {
            Seed seed = crop.getSeed();
            String seedName = seed.getName();
            if (cropsConfig.doesSeedExist(seedName)) {
                cropsConfig.getConfig().set("seeds." + seedName, null);
            }
        }

        cropsConfig.saveConfig();
    }


    /**
     * Remove the settings for the given crop, then add the default back.
     *
     * @param crop The crop to reset the settings for.
     */
    public void resetSettings(@NotNull Crop crop) {
        removeSettings(crop);
        addSettings(crop);
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
    public @Nullable Crop findByBlock(@NotNull Block block) {
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
    @SuppressWarnings("unused")
    public @Nullable Crop findByName(@NotNull String name) {
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
     * Returns the amount of crops.
     *
     * @return The amount of crops.
     */
    public int getAmountOfCrops() {
        return getCrops().size();
    }


    /**
     * Get all the crops that are the same type as the given crop.
     *
     * @param crop The crop to check for duplicates.
     *
     * @return A list of crops that are the same type as the crop passed in.
     */
    private @NotNull List<Crop> getDuplicateCrops(@NotNull Crop crop) {
        return crops.stream()
                    .filter(c -> filterByType(c, crop))
                    .collect(Collectors.toList());
    }

}