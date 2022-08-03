package com.github.bakuplayz.cropclick.addons.addon.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@ToString
@EqualsAndHashCode
public abstract class Addon {

    protected final @Getter String name;

    protected transient final CropClick plugin;
    protected transient final AddonsConfig addonsConfig;


    public Addon(@NotNull CropClick plugin, @NotNull String name) {
        this.addonsConfig = plugin.getAddonsConfig();
        this.plugin = plugin;
        this.name = name;
    }


    /**
     * Returns true if the addon is enabled, false otherwise.
     *
     * @return A boolean value.
     */
    public boolean isEnabled() {
        return addonsConfig.isEnabled(name);
    }

}