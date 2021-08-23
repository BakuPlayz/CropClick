package com.github.bakuplayz.cropclick.crop;

import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.*;
import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.crop.exceptions.CropAlreadyRegisteredException;
import com.github.bakuplayz.cropclick.crop.exceptions.CropNotRegisteredException;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public final class CropManager {

    private final @Getter List<Crop> crops = new ArrayList<>();

    public CropManager(final @NotNull CropsConfig cropsConfig) {
        registerVanillaCrops(cropsConfig);
    }

    private void registerVanillaCrops(final @NotNull CropsConfig cropsConfig) {
        crops.add(new Beetroot(cropsConfig));
        crops.add(new Cactus(cropsConfig));
        crops.add(new Carrot(cropsConfig));
        crops.add(new CocoaBean(cropsConfig));
        crops.add(new Potato(cropsConfig));
        crops.add(new SugarCane(cropsConfig));
        crops.add(new Wheat(cropsConfig));
    }

    public void registerCrop(final @NotNull Crop crop)
            throws CropAlreadyRegisteredException {
        if (crops.contains(crop)) throw new CropAlreadyRegisteredException(crop);
        crops.add(crop);
    }

    public void unregisterCrop(final @NotNull Crop crop)
            throws CropNotRegisteredException {
        if (!crops.contains(crop)) throw new CropNotRegisteredException(crop);
        crops.remove(crop);
    }

    public boolean isCrop(final @NotNull Block block) {
        return crops.stream().anyMatch(crop -> crop.getClickableType() == block.getType());
    }

    public @Nullable Crop getCrop(final @NotNull Block block) {
        return crops.stream().filter(crop -> crop.getClickableType() == block.getType())
                .findFirst().orElse(null);
    }
}
