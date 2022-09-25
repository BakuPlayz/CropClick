package com.github.bakuplayz.cropclick.yaml;

import java.util.Map;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public interface Yamlable {


    /**
     * It converts the implementing object to a yaml-styled map.
     *
     * @return A HashMap<String, Object>
     */
    Map<String, Object> toYaml();

}