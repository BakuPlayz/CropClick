package com.github.bakuplayz.cropclick.addons.addon.template;

import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import org.jetbrains.annotations.NotNull;

public abstract class Addon {

    protected final String addonName;
    protected final AddonsConfig addonsConfig;

    public Addon(final @NotNull String addonName,
                 final @NotNull AddonsConfig addonConfig) {
        this.addonsConfig = addonConfig;
        this.addonName = addonName;
    }

    public boolean isEnabled() {
        return addonsConfig.isEnabled(addonName);
    }
}
