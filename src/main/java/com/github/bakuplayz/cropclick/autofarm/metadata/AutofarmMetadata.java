/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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