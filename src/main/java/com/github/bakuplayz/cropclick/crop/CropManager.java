package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.*;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.crop.exceptions.CropAlreadyRegisteredException;
import com.github.bakuplayz.cropclick.crop.exceptions.CropNotRegisteredException;
import com.github.bakuplayz.cropclick.crop.exceptions.CropTypeDuplicateException;
import com.github.bakuplayz.cropclick.utils.VersionUtil;
import lombok.Getter;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CropManager {

    private final @Getter List<Crop> crops = new ArrayList<>();

    public CropManager(@NotNull CropsConfig config) {
        registerVanillaCrops(config);
    }

    private void registerVanillaCrops(@NotNull CropsConfig config) {
        if (VersionUtil.supportsBeetroots()) {
            crops.add(new Beetroot(config));
        }
        crops.add(new Cactus(config));
        crops.add(new Carrot(config));
        crops.add(new CocoaBean(config));
        crops.add(new Potato(config));
        crops.add(new SugarCane(config));
        crops.add(new Wheat(config));
    }

    public void registerCrop(@NotNull Crop crop)
            throws CropAlreadyRegisteredException, CropTypeDuplicateException {
        if (crops.contains(crop)) {
            throw new CropAlreadyRegisteredException(crop);
        }

        List<Crop> foundCrops = crops.stream()
                .filter(c -> filterByType(c, crop))
                .collect(Collectors.toList());

        if (foundCrops.size() > 1) {
            throw new CropTypeDuplicateException(foundCrops);
        }

        crops.add(crop);
    }

    public void unregisterCrop(@NotNull Crop crop)
            throws CropNotRegisteredException {
        if (!crops.contains(crop)) {
            throw new CropNotRegisteredException(crop);
        }

        crops.remove(crop);
    }

    public boolean isCrop(@NotNull Block block) {
        return crops.stream().anyMatch(crop -> filterByType(crop, block));
    }

    // TODO: Maybe rename?
    public boolean validate(Crop crop, Block block) {
        if (crop == null) return false;
        if (block == null) return false;
        return crop.isHarvestable(block);
    }

    public @Nullable Crop findByBlock(@NotNull Block block) {
        return crops.stream()
                .filter(crop -> filterByType(crop, block))
                .findFirst()
                .orElse(null);
    }

    private boolean filterByType(@NotNull Crop crop, @NotNull Block block) {
        return crop.getClickableType() == block.getType();
    }

    private boolean filterByType(@NotNull Crop c1, @NotNull Crop c2) {
        return c1.getClickableType() == c2.getClickableType();
    }

}