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
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import dev.bakuplayz.spigotstore.registries.json.api.JsonDeserializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

public final class LocationDeserializer implements JsonDeserializer<Location> {

    @NotNull
    public static Location deserializeLocation(@NotNull JsonNode node) {
        World world = Bukkit.getWorld(node.get("world").asText());
        return new Location(
                world == null ? Bukkit.getWorld("world") : world,
                node.get("x").asDouble(),
                node.get("y").asDouble(),
                node.get("z").asDouble()
        );
    }


    @NotNull
    @Override
    public Location fromJson(@NotNull JsonNode node) {
        if (node.has("doubly")) {
            return deserializeDoublyLocation(node);
        }
        return deserializeLocation(node);
    }


    @NotNull
    private DoublyLocation deserializeDoublyLocation(@NotNull JsonNode node) {
        Location singlyLocation = deserializeLocation(node.get("singly"));
        Location doublyLocation = deserializeLocation(node.get("doubly"));
        return new DoublyLocation(singlyLocation, doublyLocation);
    }

}
