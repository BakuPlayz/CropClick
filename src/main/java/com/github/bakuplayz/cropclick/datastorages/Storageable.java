package com.github.bakuplayz.cropclick.datastorages;

/**
 * An interface for handling data fetching and data saving.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Storageable {

    /**
     * Fetches the data according to the implementing object.
     */
    void fetchData();

    /**
     * Saves the data according to the implementing object.
     */
    void saveData();

}