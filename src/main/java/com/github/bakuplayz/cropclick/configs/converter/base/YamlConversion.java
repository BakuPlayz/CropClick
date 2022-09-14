package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class YamlConversion {

    private final YamlValueProvider valueProvider;
    private final String targetPath;


    public YamlConversion(YamlValueProvider valueProvider, String targetPath) {
        this.valueProvider = valueProvider;
        this.targetPath = targetPath;
    }


    public void execute(ConfigurationSection source, ConfigurationSection target) {
        Object obj = valueProvider.get(source);
        if (obj == null) {
            return;
        }

        target.set(targetPath, obj);
    }


}