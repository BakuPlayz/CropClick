package com.github.bakuplayz.cropclick.addons.addon.templates;

import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

public abstract class Addon {

    protected final @Getter String name;
    protected transient final AddonsConfig addonsConfig;

    public Addon(@NotNull String name,
                 @NotNull AddonsConfig config) {
        this.addonsConfig = config;
        this.name = name;
    }

    public boolean isEnabled() {
        return false;//addonsConfig.isEnabled(name);
    }

}