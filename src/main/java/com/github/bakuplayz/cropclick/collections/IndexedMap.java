/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
     * Puts the provided item into the {@link #map} with the provided key.
     *
     * @param key  the key to map.
     * @param item the item to map to the key.
     */
    public void put(@NotNull String key, E item) {
        map.put(key, item);
    }


    /**
     * Removes the provided item inside the {@link #map} based on the key.
     *
     * @param key the key of the item.
     */
    public void remove(@NotNull String key) {
        map.remove(key);
    }


    /**
     * Swaps two items in the {@link #map}.
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
     * Gets the item in the {@link #map} based on its key.
     *
     * @param key the mapped key.
     *
     * @return the found item.
     */
    public E get(@NotNull String key) {
        return map.get(key);
    }


    /**
     * Converts the {@link #map} to a string list.
     *
     * @return the map as a list.
     */
    public List<String> toStringList() {
        return map.asList();
    }


    /**
     * Gets the {@link #map underlying map}.
     *
     * @return the underlying map.
     */
    @SuppressWarnings("unused")
    public ListOrderedMap<String, E> toStringMap() {
        return map;
    }


    /**
     * Finds the index of a key in the {@link #map}.
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
     * Checks whether the {@link #map} has the provided key.
     *
     * @param key the key to check.
     *
     * @return true if found, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasKey(@NotNull String key) {
        return map.containsKey(key);
    }

}