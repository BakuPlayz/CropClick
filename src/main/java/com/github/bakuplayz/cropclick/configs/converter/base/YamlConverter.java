package com.github.bakuplayz.cropclick.configs.converter.base;

import org.bukkit.configuration.ConfigurationSection;

import java.util.ArrayList;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class YamlConverter {

    private final List<YamlConversion> conversions;


    public YamlConverter() {
        conversions = new ArrayList<>();
    }


    public void addConversion(YamlConversion conversion) {
        conversions.add(conversion);
    }


    public void addConversion(YamlValueProvider valueProvider, String targetPath) {
        addConversion(new YamlConversion(valueProvider, targetPath));
    }


    public void execute(ConfigurationSection source, ConfigurationSection target) {
        for (YamlConversion conversion : conversions) {
            conversion.execute(source, target);
        }
    }

}