package com.github.bakuplayz.cropclick.autofarm.metadata;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import org.bukkit.metadata.LazyMetadataValue;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Callable;


/**
 * A class representing a {@link Autofarm Autofarm's} metadata.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmMetadata extends LazyMetadataValue {

    public AutofarmMetadata(@NotNull CropClick plugin, Callable<Object> lazyValue) {
        super(plugin, CacheStrategy.CACHE_AFTER_FIRST_EVAL, lazyValue);
    }

}