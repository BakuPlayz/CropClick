package com.github.bakuplayz.cropclick.utils;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class IndexedMap<T> {

    private final Map<String, T> map;
    private final List<String> list;


    public IndexedMap() {
        this.map = new LinkedHashMap<>();
        this.list = new ArrayList<>();
    }


    public void put(@NotNull String name, T item) {
        if (!hasKey(name)) {
            list.add(name);
        }

        map.put(name, item);
    }


    public void swap(int indexOfFirst, int indexOfSecond) {
        String firstKey = list.get(indexOfFirst);
        String secondKey = list.get(indexOfSecond);

        T oldItem = map.get(firstKey);
        T newItem = map.get(secondKey);

        map.put(firstKey, newItem);
        map.put(secondKey, oldItem);

        list.set(indexOfFirst, secondKey);
        list.set(indexOfSecond, firstKey);
    }


    //TODO: add new item when not found....
    public T get(@NotNull String name) {
        return map.get(name);
    }


    public T getOrInit(@NotNull String name, T item) {
        if (!hasKey(name)) {
            put(name, item);
        }

        return map.get(name);
    }


    public void remove(@NotNull String name) {
        map.remove(name);
        list.remove(name);
    }


    public List<String> toList() {
        return list;
    }


    @Contract(" -> new")
    public @NotNull Map<String, T> toMap() {
        return new LinkedHashMap<>(map);
    }


    public int indexOf(@NotNull String name) {
        if (!hasKey(name)) {
            return -1;
        }

        return list.indexOf(name);
    }


    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean hasKey(@NotNull String name) {
        return map.containsKey(name);
    }

}