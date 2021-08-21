package com.github.bakuplayz.cropclick.autofarm.metadata;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public final class AutofarmMetadata extends LazyMetadataValue {

    public AutofarmMetadata(final @NotNull Plugin plugin,
                            final @NotNull Callable<Object> lazyValue) {
        super(plugin, CacheStrategy.CACHE_AFTER_FIRST_EVAL, lazyValue);
    }

    public Autofarm asAutofarm() {
        return (Autofarm) value();
    }
}
