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
package com.github.bakuplayz.cropclick.database.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import dev.bakuplayz.spigotstore.registries.json.api.JsonSerializer;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class LocationSerializer implements JsonSerializer<Location> {

    public static void serializeDoublyLocation(@NotNull JsonGenerator generator, @NotNull DoublyLocation location) throws IOException {
        generator.writeObjectFieldStart("singly");
        serializeLocation(generator, location.getSingly());
        generator.writeEndObject();

        generator.writeObjectFieldStart("doubly");
        serializeLocation(generator, location.getDoubly());
        generator.writeEndObject();
    }


    public static void serializeLocation(@NotNull JsonGenerator generator, @NotNull Location location) throws IOException {
        generator.writeNumberField("x", location.getX());
        generator.writeNumberField("y", location.getY());
        generator.writeNumberField("z", location.getZ());
        generator.writeStringField("world", location.getWorld().getName());
    }


    @Override
    public void toJson(@NotNull Location location, @NotNull JsonGenerator generator) throws IOException {
        generator.writeStartObject();

        if (location instanceof DoublyLocation) {
            serializeDoublyLocation(generator, (DoublyLocation) location);
        } else {
            serializeLocation(generator, location);
        }

        generator.writeEndObject();
    }

}
