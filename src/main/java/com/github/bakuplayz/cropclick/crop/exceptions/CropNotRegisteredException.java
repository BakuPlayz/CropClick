package com.github.bakuplayz.cropclick.crop.exceptions;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CropNotRegisteredException extends RuntimeException {

    public CropNotRegisteredException(@NotNull Crop crop) {
        super(crop.getName() + " is not registered.", new Throwable("Cannot remove a crop, that is not registered."));
    }

}