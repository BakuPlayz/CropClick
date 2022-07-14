package com.github.bakuplayz.cropclick.crop.exceptions;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

public class CropTypeDuplicateException extends RuntimeException {

    public CropTypeDuplicateException(@NotNull List<Crop> crops) {
        super(CropTypeDuplicateException.getCropNames(crops) + " has the same click type.", new Throwable("Crops cannot have the same click type."));
    }

    // Using static to get around the super constructor issue.
    private static String getCropNames(@NotNull List<Crop> crops) {
        return crops.stream()
                .map(Crop::getName)
                .collect(Collectors.toList())
                .toString();
    }

}
