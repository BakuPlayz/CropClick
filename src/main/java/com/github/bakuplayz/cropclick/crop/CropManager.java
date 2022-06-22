package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.*;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.crop.exceptions.CropAlreadyRegisteredException;
import com.github.bakuplayz.cropclick.crop.exceptions.CropNotRegisteredException;
import lombok.Getter;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class CropManager {

    private final @Getter List<Crop> crops = new ArrayList<>();

    public CropManager(final @NotNull CropsConfig config) {
        registerVanillaCrops(config);
    }

    private void registerVanillaCrops(final @NotNull CropsConfig config) {
        crops.add(new Beetroot(config));
        crops.add(new Cactus(config));
        crops.add(new Carrot(config));
        crops.add(new CocoaBean(config));
        crops.add(new Potato(config));
        crops.add(new SugarCane(config));
        crops.add(new Wheat(config));
    }

    public void registerCrop(final @NotNull Crop crop)
            throws CropAlreadyRegisteredException {
        if (crops.contains(crop)) {
            throw new CropAlreadyRegisteredException(crop);
        }

        crops.add(crop);
    }

    public void unregisterCrop(final @NotNull Crop crop)
            throws CropNotRegisteredException {
        if (!crops.contains(crop)) {
            throw new CropNotRegisteredException(crop);
        }

        crops.remove(crop);
    }

    public boolean isCrop(final @NotNull Block block) {
        return crops.stream().anyMatch(crop -> crop.getClickableType() == block.getType());
    }

    public boolean isCropValid(final Crop crop,
                               final Block block) {
        if (crop == null) return false;
        if (!isCrop(block)) return false;
        return crop.isHarvestable(block);
    }

    public @Nullable Crop getCrop(final @NotNull Block block) {
        return crops.stream()
                .filter(crop -> crop.getClickableType() == block.getType())
                .findFirst()
                .orElse(null);
    }

}