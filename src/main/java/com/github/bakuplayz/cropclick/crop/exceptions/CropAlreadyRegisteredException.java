package com.github.bakuplayz.cropclick.crop.exceptions;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CropAlreadyRegisteredException extends RuntimeException {

    public CropAlreadyRegisteredException(@NotNull Crop crop) {
        super(crop.getName() + " is already registered.", new Throwable("Cannot register the same crop, multiple times."));
    }

}