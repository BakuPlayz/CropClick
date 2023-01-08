package com.github.bakuplayz.cropclick.location;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;


/**
 * A conversion adapter for Locations to JSON, and wise versa.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class LocationTypeAdapter implements JsonSerializer<Location>, JsonDeserializer<Location> {

    /**
     * Deserializes any {@link Location location}.
     *
     * @param element the JSON object to deserialize to.
     * @param type    the type to deserialize to.
     * @param context the context of the deserialization.
     *
     * @return the deserialized location.
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
     * Deserializes {@link DoublyLocation doubly locations}.
     *
     * @param element the JSON object to deserialize to.
     *
     * @return the deserialized doubly location.
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
     * Deserializes {@link Location locations}.
     *
     * @param element the JSON object to deserialize to.
     *
     * @return the deserialized location.
     */
    private @NotNull Location deserializeLocation(@NotNull JsonElement element) {
        JsonObject body = element.getAsJsonObject();
        World world = Bukkit.getWorld(
                body.get("world").getAsString()
        );
        return new Location(
                world == null ? Bukkit.getWorlds().get(0) : world,
                body.get("x").getAsDouble(),
                body.get("y").getAsDouble(),
                body.get("z").getAsDouble()
        );
    }


    /**
     * Serializes any {@link Location location}.
     *
     * @param location the object to serialize.
     * @param type     the type to serialize to.
     * @param context  the context of the serialization.
     *
     * @return the serialized location.
     */
    @Override
    public @NotNull JsonElement serialize(Location location, Type type, JsonSerializationContext context) {
        if (location instanceof DoublyLocation) {
            return serializeDoublyLocation((DoublyLocation) location);
        }
        return serializeLocation(location);
    }


    /**
     * Serializes {@link DoublyLocation doubly locations}.
     *
     * @param location the location object to serialize to.
     *
     * @return the serialized doubly location.
     */
    public static @NotNull JsonObject serializeDoublyLocation(@NotNull DoublyLocation location) {
        JsonObject body = new JsonObject();
        JsonObject singly = serializeLocation(location.getSingly());
        JsonObject doubly = serializeLocation(location.getDoubly());
        body.add("singly", singly);
        body.add("doubly", doubly);
        return body;
    }


    /**
     * Serializes {@link Location locations}.
     *
     * @param location the location object to serialize to.
     *
     * @return the serialized location.
     */
    public static @NotNull JsonObject serializeLocation(@NotNull Location location) {
        JsonObject body = new JsonObject();

        assert location.getWorld() != null; // Only here for compiler, since a location cannot exist without a world.

        body.add("world", new JsonPrimitive(location.getWorld().getName()));
        body.add("x", new JsonPrimitive(location.getX()));
        body.add("y", new JsonPrimitive(location.getY()));
        body.add("z", new JsonPrimitive(location.getZ()));
        return body;
    }

}