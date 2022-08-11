package com.github.bakuplayz.cropclick.utils;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;


public final class MapUtils {

    public static void swap(@NotNull Map<Integer, Object> map, int indexOfFirst, int indexOfSecond) {
        Object oldS = map.get(indexOfFirst);
        Object newS = map.get(indexOfSecond);

        map.put(indexOfFirst, newS);
        map.put(indexOfSecond, oldS);
    }


    public static @NotNull Map<String, Object> fromIntToStringKeyed(
            @NotNull Map<Integer, Object> map,
            Function<Integer, Object> keyMapper
    ) {
        Map<String, Object> stringKeyed = new LinkedHashMap<>();

        for (Integer key : map.keySet()) {
            stringKeyed.put(
                    keyMapper.apply(key).toString(),
                    map.get(key)
            );
        }

        return stringKeyed;
    }

}