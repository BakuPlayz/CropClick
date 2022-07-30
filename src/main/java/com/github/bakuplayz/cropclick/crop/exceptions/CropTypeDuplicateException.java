package com.github.bakuplayz.cropclick.crop.exceptions;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CropTypeDuplicateException extends RuntimeException {

    public CropTypeDuplicateException(@NotNull List<Crop> crops) {
        super(CropTypeDuplicateException.getCropNames(crops) + " has the same click type.", new Throwable("Crops cannot have the same click type."));
    }


    /**
     * Get the names of all the crops in a list, and join them together into a single string.
     * <p>
     * Using static as a workaround for the super constructor issue.
     * </p>
     *
     * @param crops The list of crops to get the names of.
     *
     * @return A string of the names of the crops.
     */
    private static String getCropNames(@NotNull List<Crop> crops) {
        return crops.stream()
                .map(Crop::getName)
                .collect(Collectors.joining());
    }

}