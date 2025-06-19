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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class AutofarmSerializer extends JsonSerializer<Autofarm> {

    @Override
    public void serialize(@NotNull Autofarm autofarm, @NotNull JsonGenerator generator, @NotNull SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();

        generator.writeStringField("farmerId", autofarm.getFarmerId().toString());
        generator.writeStringField("ownerId", autofarm.getOwnerId().toString());
        generator.writeBooleanField("isEnabled", autofarm.isEnabled());
        serializeLocation("crop", autofarm.getCropLocation(), generator);
        serializeLocation("container", autofarm.getContainerLocation(), generator);
        serializeLocation("dispenser", autofarm.getDispenserLocation(), generator);

        generator.writeEndObject();
    }


    private void serializeLocation(@NotNull String name, @NotNull Location location, @NotNull JsonGenerator generator) throws IOException {
        generator.writeObjectFieldStart(name);
        if (location instanceof DoublyLocation) {
            LocationSerializer.serializeDoublyLocation(generator, (DoublyLocation) location);
        } else {
            LocationSerializer.serializeLocation(generator, location);
        }
        generator.writeEndObject();
    }

}
