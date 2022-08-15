package com.github.bakuplayz.cropclick.collections;

import org.apache.commons.collections4.map.ListOrderedMap;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public class IndexedMap<E> {

    protected final ListOrderedMap<String, E> map;


    public IndexedMap() {
        this.map = new ListOrderedMap<>();
    }


    public void put(@NotNull String name, E item) {
        map.put(name, item);
    }


    public void remove(@NotNull String name) {
        map.remove(name);
    }


    public void swap(int indexOfFirst, int indexOfSecond) {
        String firstKey = map.get(indexOfFirst);
        String secondKey = map.get(indexOfSecond);

        E oldItem = map.get(firstKey);
        E newItem = map.get(secondKey);

        map.put(indexOfSecond, firstKey, oldItem);
        map.put(indexOfFirst, secondKey, newItem);
    }


    public E get(@NotNull String name) {
        return map.get(name);
    }


    public E getOrDefault(@NotNull String name, E item) {
        return map.getOrDefault(name, item);
    }


    public E getOrInit(@NotNull String name, E item) {
        if (!hasKey(name)) {
            put(name, item);
        }

        return map.get(name);
    }


    public List<String> toList() {
        return map.asList();
    }


    public ListOrderedMap<String, E> toMap() {
        return map;
    }


    public int indexOf(@NotNull String name) {
        if (!hasKey(name)) {
            return -1;
        }

        return map.indexOf(name);
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasKey(@NotNull String name) {
        return map.containsKey(name);
    }

}