package com.github.bakuplayz.cropclick.addons.addon.templates;

import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import org.jetbrains.annotations.NotNull;

public abstract class Addon {

    protected final String name;
    protected final AddonsConfig config;

    public Addon(final @NotNull String name,
                 final @NotNull AddonsConfig config) {
        this.config = config;
        this.name = name;
    }

    public boolean isEnabled() {
        return config.isEnabled(name);
    }
}
