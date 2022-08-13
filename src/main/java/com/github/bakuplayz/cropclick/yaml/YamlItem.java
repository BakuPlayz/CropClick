package com.github.bakuplayz.cropclick.yaml;

import lombok.EqualsAndHashCode;

import java.util.Map;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@EqualsAndHashCode
public abstract class YamlItem {

    public abstract Map<String, Object> toYaml();

    public abstract boolean isEnabled();
    
}