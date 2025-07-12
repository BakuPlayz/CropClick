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

import com.fasterxml.jackson.databind.JsonNode;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import dev.bakuplayz.spigotstore.registries.json.api.JsonDeserializer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public final class AutofarmDeserializer implements JsonDeserializer<Autofarm> {

    @NotNull
    @Override
    public Autofarm fromJson(@NotNull JsonNode node) {
        return Autofarm.createBasic(
                UUID.fromString(node.get("farmerId").asText()),
                UUID.fromString(node.get("ownerId").asText()),
                node.get("isEnabled").asBoolean(),
                LocationDeserializer.deserializeLocation(node.get("crop")),
                LocationDeserializer.deserializeLocation(node.get("container")),
                LocationDeserializer.deserializeLocation(node.get("dispenser"))
        );
    }
    
}
