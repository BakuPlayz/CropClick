package com.github.bakuplayz.cropclick.collections;

import com.github.bakuplayz.cropclick.yaml.Yamlable;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class IndexedYamlMap<Item> extends IndexedMap<Item> {

    public @NotNull Map<String, Object> toYaml() {
        Map<String, Object> yamlMapped = new ListOrderedMap<>();

        for (Map.Entry<String, Item> entry : map.entrySet()) {
            yamlMapped.put(
                    entry.getKey(),
                    ((Yamlable) entry.getValue()).toYaml()
            );
        }

        return yamlMapped;
    }

}