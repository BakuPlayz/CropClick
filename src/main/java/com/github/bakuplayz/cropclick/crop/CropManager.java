package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.crops.ground.*;
import com.github.bakuplayz.cropclick.crop.crops.roof.GlowBerries;
import com.github.bakuplayz.cropclick.crop.crops.tall.*;
import com.github.bakuplayz.cropclick.crop.crops.wall.CocoaBean;
import com.github.bakuplayz.cropclick.crop.exceptions.CropTypeDuplicateException;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
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


    private final CropsConfig cropsConfig;
    private final CropConfigSection cropSection;
    private final SeedConfigSection seedSection;
    private final AddonConfigSection addonSection;

    private final @Getter List<BaseCrop> crops;


    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    private final @Getter HashMap<BaseCrop, Long> harvestedCrops;


    public CropManager(@NotNull CropClick plugin) {
        this.cropsConfig = plugin.getCropsConfig();
        this.addonSection = cropsConfig.getAddonSection();
        this.seedSection = cropsConfig.getSeedSection();
        this.cropSection = cropsConfig.getCropSection();
        this.harvestedCrops = new HashMap<>();
        this.crops = new ArrayList<>();

        registerVanillaCrops();
    }


    /**
     * It adds all the vanilla crops to the list of crops.
     */
    private void registerVanillaCrops() {
        if (VersionUtils.supportsBamboos()) {
            registerCrop(new Bamboo(cropsConfig));
        }

        registerCrop(new Beetroot(cropsConfig));
        registerCrop(new BrownMushroom(cropsConfig));
        registerCrop(new Cactus(cropsConfig));
        registerCrop(new Carrot(cropsConfig));
        registerCrop(new CocoaBean(cropsConfig));
        registerCrop(new Chorus(cropsConfig));

        if (VersionUtils.supportsDripleaves()) {
            registerCrop(new Dripleaf(cropsConfig));
        }

        if (VersionUtils.supportsGlowBerries()) {
            registerCrop(new GlowBerries(cropsConfig));
        }

        registerCrop(new Kelp(cropsConfig));
        registerCrop(new Melon(cropsConfig));
        registerCrop(new NetherWart(cropsConfig));
        registerCrop(new Potato(cropsConfig));
        registerCrop(new Pumpkin(cropsConfig));
        registerCrop(new RedMushroom(cropsConfig));
        registerCrop(new SeaPickle(cropsConfig));
        registerCrop(new SugarCane(cropsConfig));

        if (VersionUtils.supportsSweetBerries()) {
            registerCrop(new SweetBerries(cropsConfig));
        }

        if (VersionUtils.supportsTwistingVines()) {
            registerCrop(new TwistingVines(cropsConfig));
        }

        registerCrop(new Wheat(cropsConfig));
    }


    /**
     * If the crop type is already registered, throw an exception. Otherwise, register it.
     *
     * @param crop The crop to register.
     */
    public void registerCrop(@NotNull BaseCrop crop)
            throws CropTypeDuplicateException {

        List<BaseCrop> duplicateCrops = getDuplicateCrops(crop);

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
    private void addSettings(@NotNull BaseCrop crop) {
        String cropName = crop.getName();

        if (!cropSection.doesExist(cropName)) {
            // Drop Settings
            if (crop.hasDrop()) {
                Drop drop = crop.getDrop();
                cropSection.setDropName(cropName, drop.getName());
                cropSection.setDropAmount(cropName, drop.getAmount());
                cropSection.setDropChance(cropName, drop.getChance());
                cropSection.setDropAtLeastOne(cropName, crop.dropAtLeastOne());
            }

            // Action Settings
            cropSection.setReplant(cropName, crop.shouldReplant());
            cropSection.setHarvestable(cropName, crop.isHarvestable());
            cropSection.setLinkable(cropName, crop.isLinkable());

            // mcMMO Settings
            addonSection.setMcMMOExperience(cropName, 0);
            addonSection.setMcMMOExperienceReason(
                    cropName,
                    "You harvested " + MessageUtils.beautify(cropName, false) + "."
            );

            // JobsReborn Settings
            addonSection.setJobsMoney(cropName, 0);
            addonSection.setJobsPoints(cropName, 0);
            addonSection.setJobsExperience(cropName, 0);
        }

        if (!crop.hasSeed()) {
            return;
        }

        BaseSeed seed = crop.getSeed();
        String seedName = seed.getName();
        if (!seedSection.doesExist(seedName)) {
            // Drop Settings
            if (crop.hasDrop()) {
                Drop drop = seed.getDrop();
                seedSection.setDropName(seedName, drop.getName());
                seedSection.setDropAmount(seedName, drop.getAmount());
                seedSection.setDropChance(seedName, drop.getChance());
            }

            seedSection.setEnabled(seedName, seed.isEnabled());
        }
    }


    /**
     * It removes a crop from the list of crops.
     *
     * @param crop The crop to register.
     */
    @SuppressWarnings("unused")
    public void unregisterCrop(@NotNull BaseCrop crop) {
        crops.remove(crop);
        removeSettings(crop);
    }


    /**
     * It removes the settings for a crop and its seed from the crops.yml file
     *
     * @param crop The crop to remove
     */
    private void removeSettings(@NotNull BaseCrop crop) {
        if (crop.hasSeed()) {
            BaseSeed seed = crop.getSeed();
            String seedName = seed.getName();
            cropsConfig.getConfig().set("seeds." + seedName, null);
        }

        cropsConfig.getConfig().set("crops." + crop.getName(), null);
        cropsConfig.saveConfig();
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
    public boolean validate(BaseCrop crop, Block block) {
        return crop != null && block != null;
    }


    /**
     * If the crop is already clickable (like sweet berries), return true. Otherwise, false.
     *
     * @param crop The crop that is being checked.
     *
     * @return A boolean value.
     */
    public boolean isAlreadyClickable(@NotNull BaseCrop crop) {
        if (crop instanceof SweetBerries) {
            return true;
        }
        return crop instanceof GlowBerries;
    }


    /**
     * Find the first crop that matches the given block, or return null if none are found.
     *
     * @param block The block to find the crop for.
     *
     * @return A crop object.
     */
    public @Nullable BaseCrop findByBlock(@NotNull Block block) {
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
    public @Nullable BaseCrop findByName(@NotNull String name) {
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
    private boolean filterByType(@NotNull BaseCrop crop, @NotNull Block block) {
        if (crop instanceof GlowBerries) {
            return ((GlowBerries) crop).isGlowBerry(block);
        }

        if (crop instanceof Dripleaf) {
            return ((Dripleaf) crop).isDripleaf(block);
        }

        if (crop instanceof Kelp) {
            return ((Kelp) crop).isKelp(block);
        }

        if (crop instanceof Chorus) {
            return ((Chorus) crop).isChorus(block);
        }

        if (crop instanceof BrownMushroom) {
            return ((BrownMushroom) crop).isBrownMushroom(block);
        }

        if (crop instanceof RedMushroom) {
            return ((RedMushroom) crop).isRedMushroom(block);
        }

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
    private boolean filterByType(@NotNull BaseCrop c1, @NotNull BaseCrop c2) {
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
    private @NotNull List<BaseCrop> getDuplicateCrops(@NotNull BaseCrop crop) {
        return crops.stream()
                    .filter(c -> filterByType(c, crop))
                    .collect(Collectors.toList());
    }

}