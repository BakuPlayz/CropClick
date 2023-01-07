package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the YAML file: 'usage.yml'.
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class UsageConfig extends Config {

    public UsageConfig(@NotNull CropClick plugin) {
        super(plugin, "usage.yml");
    }


    /**
     * Updates the usage information, used to handle legacy configuration.
     */
    public void updateUsageInfo() {
        config.set("last-opened-in", plugin.getDescription().getVersion());
        saveConfig();
    }


    /**
     * Checks whether the current configuration format is the latest.
     *
     * @return true if it is, otherwise false.
     */
    public boolean isNewFormatVersion() {
        String lastOpenedIn = config.getString("last-opened-in", "");
        return !lastOpenedIn.startsWith("0") && !lastOpenedIn.startsWith("1");
    }

}