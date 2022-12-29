package com.github.bakuplayz.cropclick.collections;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * A class representing an indexed map.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class IndexedMap<E> {

    /**
     * A variable handling the internal mapping.
     */
    protected final ListOrderedMap<String, E> map;


    public IndexedMap() {
        this.map = new ListOrderedMap<>();
    }


    /**
     * Puts an item into the map.
     *
     * @param key  the key to map.
     * @param item the item to map to the key.
     */
    public void put(@NotNull String key, E item) {
        map.put(key, item);
    }


    /**
     * Removes an item inside the map.
     *
     * @param key the key of the item.
     */
    public void remove(@NotNull String key) {
        map.remove(key);
    }


    /**
     * Swaps two items in the map.
     *
     * @param indexOfFirst  the index of the first item.
     * @param indexOfSecond the index of the second item.
     */
    public void swap(int indexOfFirst, int indexOfSecond) {
        String firstKey = map.get(indexOfFirst);
        String secondKey = map.get(indexOfSecond);

        E oldItem = map.get(firstKey);
        E newItem = map.get(secondKey);

        map.put(indexOfSecond, firstKey, oldItem);
        map.put(indexOfFirst, secondKey, newItem);
    }


    /**
     * Gets an item in the map, using its key.
     *
     * @param key the mapped key.
     *
     * @return the found item.
     */
    public E get(@NotNull String key) {
        return map.get(key);
    }


    /**
     * Converts the map to a list.
     *
     * @return the map as a list.
     */
    public List<String> toList() {
        return map.asList();
    }


    /**
     * Gets the underlying map.
     *
     * @return the map.
     */
    @SuppressWarnings("unused")
    public ListOrderedMap<String, E> toMap() {
        return map;
    }


    /**
     * Finds the index of a key in the map.
     *
     * @param key the mapped key.
     *
     * @return the index of the key.
     */
    public int indexOf(@NotNull String key) {
        if (!hasKey(key)) {
            return -1;
        }

        return map.indexOf(key);
    }


    /**
     * Checks whether the map has the key
     *
     * @param key the key to check.
     *
     * @return true if it was found, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasKey(@NotNull String key) {
        return map.containsKey(key);
    }

}