package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
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


/**
 * A manager controlling all the {@link Crop crops}.
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


    /**
     * A variable containing all the registered crops.
     */
    private final @Getter List<Crop> crops;


    /**
     * A {@link HashMap map} of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    private final @Getter HashMap<Crop, Long> harvestedCrops;


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
     * Registers all the {@link Crop vanilla crops}.
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
     * Registers the {@link Crop provided crop}.
     *
     * @param crop the crop to register.
     *
     * @throws CropTypeDuplicateException the exception thrown when the crop is already registered.
     */
    public void registerCrop(@NotNull Crop crop)
            throws CropTypeDuplicateException {

        if (isAlreadyRegistered(crop)) {
            throw new CropTypeDuplicateException();
        }

        crops.add(crop);
        addSettings(crop);
    }


    /**
     * Unregister the {@link Crop provided crop}.
     *
     * @param crop the crop to unregister.
     */
    public void unregisterCrop(@NotNull Crop crop) {
        crops.remove(crop);
        removeSettings(crop);
    }


    /**
     * TODO: Move to config
     *
     * @param crop
     */
    private void addSettings(@NotNull Crop crop) {
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
     * TODO: Move to config.
     *
     * @param crop
     */
    private void removeSettings(@NotNull Crop crop) {
        if (crop.hasSeed()) {
            BaseSeed seed = crop.getSeed();
            String seedName = seed.getName();
            cropsConfig.getConfig().set("seeds." + seedName, null);
        }

        cropsConfig.getConfig().set("crops." + crop.getName(), null);
        cropsConfig.saveConfig();
    }


    /**
     * Finds the {@link Crop crop} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the found crop, otherwise false.
     */
    public @Nullable Crop findByBlock(@NotNull Block block) {
        return crops.stream()
                    .filter(crop -> filterByType(crop, block))
                    .findFirst().orElse(null);
    }


    /**
     * Finds the {@link Crop crop} based on the provided name.
     *
     * @param name the name to base the findings on.
     *
     * @return the found crop, otherwise false.
     */
    public @Nullable Crop findByName(@NotNull String name) {
        return crops.stream()
                    .filter(crop -> crop.getName().equals(name))
                    .findFirst().orElse(null);
    }


    /**
     * Filters searches based on the {@link Crop crop's} type matching with the {@link Block block's} type.
     *
     * @param crop  the crop to check.
     * @param block the block to check.
     *
     * @return true if they match, otherwise false.
     */
    private boolean filterByType(@NotNull Crop crop, @NotNull Block block) {
        if (crop instanceof GlowBerries) {
            return ((GlowBerries) crop).isGlowBerry(block);
        }

        if (crop instanceof Dripleaf) {
            return ((Dripleaf) crop).isDripleafType(block);
        }

        if (crop instanceof Kelp) {
            return ((Kelp) crop).isKelpType(block);
        }

        if (crop instanceof Chorus) {
            return ((Chorus) crop).isChorusType(block);
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
     * Filters searches based on the {@link Crop first crop's} type matching with the {@link Crop second crop's} type.
     *
     * @param first  the first crop to check.
     * @param second the second crop to check.
     *
     * @return true if they match, otherwise false.
     */
    private boolean filterByType(@NotNull Crop first, @NotNull Crop second) {
        return first.getClickableType() == second.getClickableType();
    }


    /**
     * Checks whether the {@link Crop provided crop} is already clickable in game.
     *
     * @param crop the crop to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isAlreadyClickable(@NotNull Crop crop) {
        if (crop instanceof SweetBerries) {
            return true;
        }
        return crop instanceof GlowBerries;
    }


    /**
     * Checks whether the {@link Crop provided crop} is already registered.
     *
     * @param crop the crop to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isAlreadyRegistered(@NotNull Crop crop) {
        return crops.stream().anyMatch(c -> filterByType(c, crop));
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Crop crop}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isCrop(@NotNull Block block) {
        return crops.stream().anyMatch(crop -> filterByType(crop, block));
    }


    /**
     * Gets the amount of {@link #crops registred crops}.
     *
     * @return the amount of crops.
     */
    public int getAmountOfCrops() {
        return getCrops().size();
    }

}