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

package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.crops.ground.*;
import com.github.bakuplayz.cropclick.crop.crops.tall.*;
import com.github.bakuplayz.cropclick.crop.crops.wall.CocoaBean;
import com.github.bakuplayz.cropclick.crop.exceptions.CropTypeDuplicateException;
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
        this.harvestedCrops = new HashMap<>();
        this.crops = new ArrayList<>();

        registerVanillaCrops();
    }


    /**
     * Registers all the {@link Crop vanilla crops}.
     */
    private void registerVanillaCrops() {
        if (VersionUtils.supportsBeetroots()) {
            registerCrop(new Beetroot(cropsConfig));
        }

        registerCrop(new BrownMushroom(cropsConfig));
        registerCrop(new Cactus(cropsConfig));
        registerCrop(new Carrot(cropsConfig));
        registerCrop(new CocoaBean(cropsConfig));

        if (VersionUtils.supportsChorus()) {
            registerCrop(new Chorus(cropsConfig));
        }

        registerCrop(new Melon(cropsConfig));
        registerCrop(new NetherWart(cropsConfig));
        registerCrop(new Potato(cropsConfig));
        registerCrop(new Pumpkin(cropsConfig));
        registerCrop(new RedMushroom(cropsConfig));
        registerCrop(new SugarCane(cropsConfig));

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
        cropsConfig.addSettings(crop);
    }


    /**
     * Unregister the {@link Crop provided crop}.
     *
     * @param crop the crop to unregister.
     */
    public void unregisterCrop(@NotNull Crop crop) {
        crops.remove(crop);
        cropsConfig.removeSettings(crop);
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
     * Filters searches based on the {@link Crop first crop's} clickable type matching with the {@link Crop second crop's} clickable type.
     *
     * @param first  the first crop to check.
     * @param second the second crop to check.
     *
     * @return true if they match, otherwise false.
     */
    private boolean filterByType(@NotNull Crop first, @NotNull Crop second) {

        // Specific check for mushrooms as they have the same click type.
        if (first instanceof BrownMushroom && second instanceof RedMushroom) {
            return false;
        }

        return first.getClickableType() == second.getClickableType();
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