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
import com.github.bakuplayz.cropclick.world.FarmWorld;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public final class FarmWorldSerializer extends JsonSerializer<FarmWorld> {

    @Override
    public void serialize(@NotNull FarmWorld world, @NotNull JsonGenerator generator, @NotNull SerializerProvider provider) throws IOException, JsonProcessingException {
        generator.writeStartObject();
        generator.writeStringField("name", world.getName());
        serializeBanishedAddons(world, generator);
        generator.writeBooleanField("isBanished", world.isBanished());
        generator.writeBooleanField("allowsPlayers", world.allowsPlayers());
        generator.writeBooleanField("allowsAutofarms", world.allowsAutofarms());
        generator.writeEndObject();
    }


    private void serializeBanishedAddons(@NotNull FarmWorld world, @NotNull JsonGenerator generator) throws IOException {
        generator.writeArrayFieldStart("banishedAddons");
        for (String addon : world.getBanishedAddons()) {
            generator.writeString(addon);
        }
        generator.writeEndArray();
    }

}
