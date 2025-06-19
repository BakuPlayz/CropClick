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

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public final class FarmWorldDeserializer extends JsonDeserializer<FarmWorld> {

    @NotNull
    @Override
    public FarmWorld deserialize(@NotNull JsonParser parser, @NotNull DeserializationContext ctx) throws IOException, JsonProcessingException {
        JsonNode node = parser.getCodec().readTree(parser);
        return FarmWorld.createBasic(
                node.get("name").asText(),
                node.get("isBanished").asBoolean(),
                node.get("allowsPlayers").asBoolean(),
                node.get("allowsAutofarms").asBoolean(),
                deserializeAddons(node)
        );
    }


    @NotNull
    private List<String> deserializeAddons(@NotNull JsonNode node) {
        List<String> addons = new ArrayList<>();

        JsonNode banishedAddons = node.get("banishedAddons");
        if (banishedAddons.isArray()) {
            banishedAddons.forEach(addon -> addons.add(addon.asText()));
        }

        return addons;
    }

}
