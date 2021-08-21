package com.github.bakuplayz.cropclick.crop.exceptions;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import org.jetbrains.annotations.NotNull;

public final class CropNotRegisteredException extends Exception {

    public CropNotRegisteredException(final @NotNull Crop crop) {
        super(crop.getName() + " is not registered.", new Throwable("Cannot remove a crop that doesn't exist."));
    }
}
