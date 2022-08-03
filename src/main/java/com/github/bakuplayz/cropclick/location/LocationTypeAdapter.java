package com.github.bakuplayz.cropclick.location;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    /**
     * It takes a JsonElement, and returns a Location
     *
     * @param element The JsonElement to deserialize.
     * @param type    The type of the object being deserialized.
     * @param context The context of the deserialization.
     *
     * @return A Location object.
     */
    @Override
    public @NotNull Location deserialize(@NotNull JsonElement element, Type type, JsonDeserializationContext context)
            throws JsonParseException {
        if (element.getAsJsonObject().has("doubly")) {
            return deserializeDoublyLocation(element);
        }
        return deserializeLocation(element);
    }


    /**
     * It takes a JsonElement, gets the JsonObject from it, gets the JsonObjects from the JsonObject, deserializes the
     * JsonObjects into Locations, and returns a DoublyLocation.
     *
     * @param element The JsonElement that is being deserialized.
     *
     * @return A DoublyLocation object.
     */
    private @NotNull DoublyLocation deserializeDoublyLocation(@NotNull JsonElement element) {
        JsonObject body = element.getAsJsonObject();
        JsonObject singly = body.get("singly").getAsJsonObject();
        JsonObject doubly = body.get("doubly").getAsJsonObject();
        Location singlyLocation = deserializeLocation(singly);
        Location doublyLocation = deserializeLocation(doubly);
        return new DoublyLocation(singlyLocation, doublyLocation);
    }


    /**
     * It takes a JsonElement, which is a part of the Gson library, and returns a Location object
     *
     * @param element The JsonElement that is being deserialized.
     *
     * @return A Location object
     */
    private @NotNull Location deserializeLocation(@NotNull JsonElement element) {
        JsonObject body = element.getAsJsonObject();
        String worldName = body.get("world").getAsString();
        double x = body.get("x").getAsDouble();
        double y = body.get("y").getAsDouble();
        double z = body.get("z").getAsDouble();
        World world = Bukkit.getWorld(worldName);
        return new Location(world, x, y, z);
    }


    /**
     * This function serializes a location into a JsonElement.
     *
     * @param location The location to serialize.
     * @param type     The type of the object being serialized/deserialized.
     * @param context  The context of the serialization.
     *
     * @return A JsonElement.
     */
    @Override
    public @NotNull JsonElement serialize(Location location, Type type, JsonSerializationContext context) {
        if (location instanceof DoublyLocation) {
            return serializeDoublyLocation((DoublyLocation) location);
        }
        return serializeLocation(location);
    }


    /**
     * It takes a doubly location and returns a JSON object that contains the singly and doubly locations.
     *
     * @param location The location to serialize.
     *
     * @return A JsonObject.
     */
    private @NotNull JsonObject serializeDoublyLocation(@NotNull DoublyLocation location) {
        JsonObject body = new JsonObject();
        JsonObject singly = serializeLocation(location.getSingly());
        JsonObject doubly = serializeLocation(location.getDoubly());
        body.add("singly", singly);
        body.add("doubly", doubly);
        return body;
    }


    /**
     * It takes a Bukkit Location object and returns a JsonObject that contains the world name, x, y, and z coordinates.
     *
     * @param location The location to serialize.
     *
     * @return A JsonObject.
     */
    private @NotNull JsonObject serializeLocation(@NotNull Location location) {
        JsonObject body = new JsonObject();
        body.add("world", new JsonPrimitive(location.getWorld().getName()));
        body.add("x", new JsonPrimitive(location.getX()));
        body.add("y", new JsonPrimitive(location.getY()));
        body.add("z", new JsonPrimitive(location.getZ()));
        return body;
    }

}