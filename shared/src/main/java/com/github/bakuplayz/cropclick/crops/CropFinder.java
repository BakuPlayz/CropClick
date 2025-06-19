/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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

import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.crops.roof.GlowBerries;
import com.github.bakuplayz.cropclick.crops.tall.*;
import lombok.AllArgsConstructor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public final class CropFinder {

    private final List<Crop> registeredCrops;


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


}
