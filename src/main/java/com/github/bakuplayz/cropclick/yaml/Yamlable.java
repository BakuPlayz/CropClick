package com.github.bakuplayz.cropclick.yaml;

import java.util.HashMap;
import java.util.Map;


/**
 * An interface designed to convert the implementing object, to an YAML representation.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Yamlable {


    /**
     * It converts the implementing object to a YAML-styled map.
     *
     * @return A {@link HashMap YAML-formatted HashMap}.
     */
    Map<String, Object> toYaml();

}