package com.github.bakuplayz.cropclick.configs.config;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.Config;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author <a href="https://gitlab.com/hannesblaman">Hannes Blåman</a>
 * @version 2.0.0
 * @since 2.0.0
 */
public final class UsageConfig extends Config {

    public UsageConfig(@NotNull CropClick plugin) {
        super(plugin, "usage.yml");
    }


    public String getLastOpenedIn() {
        return config.getString("last-opened-in");
    }


    public void updateUsageInfo() {
        config.set("last-opened-in", plugin.getDescription().getVersion());
        saveConfig();
    }


    public boolean isNewFormatVersion() {
        String lastOpenedIn = getLastOpenedIn();
        return !lastOpenedIn.startsWith("0") && !lastOpenedIn.startsWith("1");
    }

}