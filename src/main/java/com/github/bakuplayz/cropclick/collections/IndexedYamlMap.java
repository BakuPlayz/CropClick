package com.github.bakuplayz.cropclick.collections;

import com.github.bakuplayz.cropclick.yaml.Yamlable;
import org.apache.commons.collections4.map.ListOrderedMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;


/**
 * A class representing an indexable YAML map.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class IndexedYamlMap<E> extends IndexedMap<E> {

    /**
     * Converts the map to a YAML-styled {@link Map}.
     *
     * @return the map as an YAML-styled {@link Map}.
     */
    public @NotNull Map<String, Object> toYaml() {
        Map<String, Object> yamlMapped = new ListOrderedMap<>();

        for (Map.Entry<String, E> entry : map.entrySet()) {
            yamlMapped.put(
                    entry.getKey(),
                    ((Yamlable) entry.getValue()).toYaml()
            );
        }

        return yamlMapped;
    }

}