package com.github.bakuplayz.cropclick.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class MapUtils {

    public static void swap(@NotNull Map<Integer, Object> map, int indexOfFirst, int indexOfSecond) {
        Object oldItem = map.get(indexOfFirst);
        Object newItem = map.get(indexOfSecond);

        map.put(indexOfFirst, newItem);
        map.put(indexOfSecond, oldItem);
    }


    public static @NotNull Map<String, Object> fromIntToStringKeyed(@NotNull Map<Integer, Object> map,
                                                                    @NotNull Function<Integer, Object> keyMapper) {
        Map<String, Object> stringKeyed = new LinkedHashMap<>();

        for (Integer key : map.keySet()) {
            stringKeyed.put(
                    keyMapper.apply(key).toString(),
                    map.get(key)
            );
        }

        return stringKeyed;
    }


    public static @NotNull Map<Integer, Object> configListToMap(@NotNull FileConfiguration config,
                                                                @NotNull String basePath,
                                                                @NotNull List<String> items) {
        Map<Integer, Object> map = new HashMap<>();

        for (int i = 0; i < items.size(); i++) {
            String item = items.get(i);
            Map<String, Object> values = config
                    .getConfigurationSection(basePath + "." + item)
                    .getValues(true);
            map.put(i, values);
        }

        return map;
    }

}