package com.github.bakuplayz.cropclick.crop.exceptions;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import org.jetbrains.annotations.NotNull;

public final class CropAlreadyRegisteredException extends Exception {

    public CropAlreadyRegisteredException(final @NotNull Crop crop) {
        super(crop.getName() + " is already registered.", new Throwable("Duplicate crops are not allowed."));
    }
}
