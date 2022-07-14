package com.github.bakuplayz.cropclick.autofarm.metadata;

import org.bukkit.metadata.LazyMetadataValue;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;

public class AutofarmMetadata extends LazyMetadataValue {

    public AutofarmMetadata(@NotNull Plugin cropClick, Callable<Object> lazyValue) {
        super(cropClick, CacheStrategy.CACHE_AFTER_FIRST_EVAL, lazyValue);
    }

}
