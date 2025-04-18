/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick.database;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class EntityMapperRegistry {

    private static final Map<Class<?>, EntityMapper<?>> registry = new HashMap<>();


    public static <T> void register(@NotNull Class<T> clazz, @NotNull EntityMapper<T> mapper) {
        registry.put(clazz, mapper);
    }


    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> EntityMapper<T> get(@NotNull Class<T> clazz) {
        EntityMapper<T> mapper = (EntityMapper<T>) registry.get(clazz);

        if (mapper == null) {
            throw new IllegalStateException("No EntityMapper registered for class: " + clazz.getName());
        }

        return mapper;
    }

}
