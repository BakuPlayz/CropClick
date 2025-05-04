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

package com.github.bakuplayz.cropclick.crops;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.CropAPI;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.common.exceptions.CropTypeDuplicateException;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.ground.*;
import com.github.bakuplayz.cropclick.crops.roof.GlowBerries;
import com.github.bakuplayz.cropclick.crops.tall.*;
import com.github.bakuplayz.cropclick.crops.wall.CocoaBean;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 * A manager controlling all the {@link Crop crops}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropManager implements CropAPI {

    private final CropsConfig cropsConfig;


    /**
     * A variable containing all the registered crops.
     */
    @Getter
    private final List<Crop> registeredCrops;


    /**
     * A map of the crops that have been harvested and the time they were harvested,
     * in order to render a duplication issue, with crops, obsolete.
     */
    @Getter
    private final HashMap<Crop, Long> harvestedCrops;

    @Getter
    private final CropArguments cropArguments;


    public CropManager(@NotNull CropClick plugin) {
        this.cropArguments = new CropArguments(plugin.getConfigManager().getCropsConfig(), plugin.getTaskScheduler());
        this.cropsConfig = plugin.getConfigManager().getCropsConfig();
        this.registeredCrops = new ArrayList<>();
        this.harvestedCrops = new HashMap<>();

        registerVanillaCrops();
    }


    /**
     * Registers all the {@link Crop vanilla crops}.
     */
    private void registerVanillaCrops() {
        if (Versions.supportsBamboos()) {
            registerCrop(new Bamboo(cropArguments));
        }

        if (Versions.supportsBeetroots()) {
            registerCrop(new Beetroot(cropArguments));
        }

        registerCrop(new BrownMushroom(cropArguments));
        registerCrop(new Cactus(cropArguments));
        registerCrop(new Carrot(cropArguments));
        registerCrop(new CocoaBean(cropArguments));

        if (Versions.supportsChorus()) {
            registerCrop(new Chorus(cropArguments));
        }

        if (Versions.supportsDripleaves()) {
            registerCrop(new Dripleaf(cropArguments));
        }

        if (Versions.supportsGlowBerries()) {
            registerCrop(new GlowBerries(cropArguments));
        }

        if (Versions.supportsKelp()) {
            registerCrop(new Kelp(cropArguments));
        }

        registerCrop(new Melon(cropArguments));
        registerCrop(new NetherWart(cropArguments));

        if (Versions.supportsPitcherPlants()) {
            registerCrop(new PitcherPlant(cropArguments));
        }

        registerCrop(new Potato(cropArguments));
        registerCrop(new Pumpkin(cropArguments));
        registerCrop(new RedMushroom(cropArguments));

        if (Versions.supportsSeaPickle()) {
            registerCrop(new SeaPickle(cropArguments));
        }

        registerCrop(new SugarCane(cropArguments));

        if (Versions.supportsSweetBerries()) {
            registerCrop(new SweetBerries(cropArguments));
        }

        if (Versions.supportsTorchFlowers()) {
            registerCrop(new Torchflower(cropArguments));
        }

        if (Versions.supportsTwistingVines()) {
            registerCrop(new TwistingVines(cropArguments));
        }

        registerCrop(new Wheat(cropArguments));
    }


    /**
     * Registers the {@link Crop provided crop}.
     *
     * @param crop the crop to register.
     *
     * @throws CropTypeDuplicateException the exception thrown when the crop is already registered.
     */
    public void registerCrop(@NotNull Crop crop) throws CropTypeDuplicateException {
        if (isAlreadyRegistered(crop)) {
            throw new CropTypeDuplicateException();
        }

        registeredCrops.add(crop);
        cropsConfig.addSettings(crop);
    }


    /**
     * Unregister the {@link Crop provided crop}.
     *
     * @param crop the crop to unregister.
     */
    public void unregisterCrop(@NotNull Crop crop) {
        registeredCrops.remove(crop);
        cropsConfig.removeSettings(crop);
    }


    /**
     * Finds the {@link Crop crop} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the found crop, otherwise false.
     */
    @Nullable
    public Crop findByBlock(@NotNull Block block) {
        return registeredCrops.stream()
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
    @Nullable
    public Crop findByName(@NotNull String name) {
        return registeredCrops.stream()
                       .filter(crop -> crop.getName().equals(name))
                       .findFirst().orElse(null);
    }


    /**
     * Checks whether the {@link Block provided block} is a {@link Crop crop}.
     *
     * @param block the block to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isCrop(@NotNull Block block) {
        return registeredCrops.stream().anyMatch(crop -> filterByType(crop, block));
    }


    /**
     * Finds the {@link Crop crop} based on the {@link Location provided location}.
     *
     * @param location the location to base the findings on.
     *
     * @return the found crop, otherwise false.
     */
    @Nullable
    public Crop findByLocation(Location location) {
        if (location == null) return null;
        return findByBlock(location.getBlock());
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
            return ((GlowBerries) crop).isGlowBerriesType(block);
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

        boolean isMaterialMatch = crop.getClickableType().parseMaterial() == block.getType();
        if (!isMaterialMatch && Versions.isLegacy()) {
            isMaterialMatch = Arrays.asList(crop.getClickableType().getLegacy()).contains(block.getType().name());
        }

        return isMaterialMatch;
    }


    /**
     * Filters searches based on the {@link Crop first crop's} clickable type matching with the {@link Crop second crop's} clickable type.
     *
     * @param first  the first crop to check.
     * @param second the second crop to check.
     *
     * @return true if they match, otherwise false.
     */
    private boolean filterByType(@NotNull Crop first, @NotNull Crop second) {
        if (isMushroomSameMushroom(first, second)) {
            return false;
        }

        return first.getClickableType() == second.getClickableType();
    }


    /**
     * Specifically checks if the first crop is a mushroom and is not a mushroom
     * of another type than itself.
     *
     * @param first  the first potential mushroom to test.
     * @param second the second potential mushroom to test.
     *
     * @return true iff same mushroom type, otherwise false.
     */
    private boolean isMushroomSameMushroom(@NotNull Crop first, @NotNull Crop second) {
        if (first instanceof BrownMushroom && second instanceof RedMushroom) return false;
        return !(first instanceof RedMushroom) || !(second instanceof BrownMushroom);
    }


    /**
     * Checks whether the {@link Crop provided crop} is already clickable in game.
     *
     * @param crop the crop to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isAlreadyClickable(@NotNull Crop crop) {
        return crop instanceof SweetBerries || crop instanceof GlowBerries;
    }


    /**
     * Checks whether the {@link Crop provided crop} is already registered,
     * by comparing their clickable types.
     *
     * @param crop the crop to check.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isAlreadyRegistered(@NotNull Crop crop) {
        return registeredCrops.stream().anyMatch(c -> filterByType(c, crop));
    }


    /**
     * Gets the amount of {@link #registeredCrops registred crops}.
     *
     * @return the amount of crops.
     */
    public int getAmountOfCrops() {
        return getRegisteredCrops().size();
    }

}