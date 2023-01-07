package com.github.bakuplayz.cropclick.crop.exceptions;

import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


/**
 * A class representing an exception for duplicated {@link Crop crop} types.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CropTypeDuplicateException extends RuntimeException {

    public CropTypeDuplicateException() {
        super("The provided crop was already registered.", new Throwable("Crops cannot have the same click type."));
    }

}