/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2025 BakuPlayz
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
package com.github.bakuplayz.cropclick.datacontainers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.LoggerContext;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.*;


public final class AbstractDataContainer<D> implements DataContainer<D>, LoggerContext {

    private final static long SAVE_INTERVAL = 30 * 1000 * 20L;

    private final String fileName;

    private final ObjectMapper mapper;

    private final Map<String, D> data;

    private final File file;


    public AbstractDataContainer(@NotNull String fileName) {
        this.file = getNewFileInstance();
        this.mapper = new ObjectMapper();
        this.data = new HashMap<>();
        this.fileName = fileName;

        createIfAbsent();
        setupSave();
    }


    /**
     * Adds the entry to the container, overwriting if the key already exists.
     *
     * @param key    the key to set the entity at.
     * @param entity the entity to add.
     */
    @Override
    public void add(@NotNull String key, @NotNull D entity) {
        data.put(key, entity);
    }


    /**
     * Adds the entry only if the key is not already present.
     *
     * @param key    the key to add.
     * @param entity the entity to add.
     *
     * @return true if the entry was added, false if the key already existed.
     */
    @Override
    public boolean addIfAbsent(@NotNull String key, @NotNull D entity) {
        return data.putIfAbsent(key, entity) == null;
    }


    /**
     * Removes the entry associated with the given key.
     *
     * @param key the key to remove.
     *
     * @return true if the entry was removed, false if the key did not exist.
     */
    @Override
    public boolean remove(@NotNull String key) {
        return data.remove(key) != null;
    }


    /**
     * Gets the entry associated with the given key.
     *
     * @param key the key to get.
     *
     * @return the entity, or null if the key does not exist.
     */
    @Override
    public D getOne(@NotNull String key) {
        return data.get(key);
    }


    /**
     * Gets a collection of all values in the container.
     *
     * @return a collection of all stored entities.
     */
    @NotNull
    @Override
    public Collection<D> getMany() {
        return data.values();
    }


    /**
     * Creates the JSON (backing) file if it doesn't already exist.
     */
    private void createIfAbsent() {
        try {
            Files.createFile(Paths.get(file.getAbsolutePath()));
        } catch (FileAlreadyExistsException e) {
            logDebug("Could not create file: {}, already created.", file.getAbsolutePath());
        } catch (SecurityException e) {
            DATA_CONTAINER_FAILED_CREATE_SECURITY.send(getLogger(), file.getAbsolutePath());
        } catch (IOException | UnsupportedOperationException e) {
            DATA_CONTAINER_FAILED_CREATE.send(getLogger(), file.getAbsolutePath());
        }
    }


    /**
     * Initializes a repeating asynchronous save task that runs at a fixed interval.
     * <p>
     * This task is automatically canceled when {@code CropClick}'s {@code @OnDisable} method is triggered,
     * so no manual cleanup or cancellation is required within this class.
     * </p>
     */
    private void setupSave() {
        Bukkit.getScheduler().runTaskTimer(CropClick.getInstance(), () -> {
            if (!trySave(3)) {
                DATA_CONTAINER_FAILED_SAVE.send(getLogger(), file.getAbsolutePath());
                return;
            }

            DATA_CONTAINER_SUCCESS_SAVE.send(getLogger(), file.getAbsolutePath());
        }, SAVE_INTERVAL, SAVE_INTERVAL);
    }


    /**
     * Attempts to save the data to the file, retrying if it fails,
     * till out of retries.
     *
     * @param tries The number of retry attempts remaining.
     *
     * @return true if the save was successful, false otherwise.
     */
    private boolean trySave(int tries) {
        if (tries == 0) {
            return false;
        }

        try {
            Path temp = Files.createTempFile(UUID.randomUUID().toString(), ".tmp");

            try {
                mapper.writeValue(temp.toFile(), data);
                Files.copy(temp, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                return true;
            } catch (IOException e) {
                return trySave(--tries);
            } finally {
                Files.delete(temp);
            }

        } catch (IOException e) {
            return trySave(--tries);
        }
    }


    /**
     * Builds a new file instance using the plugin data folder and file name.
     *
     * @return a new File pointing to the data save location.
     */
    @NotNull
    private File getNewFileInstance() {
        return new File(CropClick.getInstance().getDataFolder().getAbsolutePath() + "/data", fileName);
    }


 /*   static class AutofarmSerializer implements SQLSerializer<Autofarm> {

        @Override
        public void serialize(@NotNull Autofarm autofarm, @NotNull PreparedStatement stmt) throws SQLException {
            stmt.setString(1, autofarm.getFarmerId().toString());
            stmt.setString(2, autofarm.getOwnerId().toString());
            stmt.setBoolean(3, autofarm.isEnabled());

            stmt.setString(4, autofarm.getCropLocation().toString()); // TODO: should call an toSql?
            stmt.setString(5, autofarm.getContainerLocation().toString()); // TODO: should call an toSql?
            stmt.setString(6, autofarm.getDispenserLocation().toString()); // TODO: should call an toSql?
        }


        @Override
        public Autofarm deserialize(@NotNull ResultSet rs) throws SQLException {
            LocationSerializer locSerializer = new LocationSerializer();

            return new Autofarm(
                    UUID.fromString(rs.getString("farmer_id")),
                    UUID.fromString(rs.getString("owner_id")),
                    rs.getBoolean("is_enabled"),
                    null,
                    null,
                    null
            );
        }

    }

    static class LocationSerializer implements SQLSerializer<Location> {

        @Override
        public void serialize(@NotNull Location data, @NotNull PreparedStatement stmt) throws SQLException {
            stmt.setString(1, data.getWorld().getName());
            stmt.setDouble(2, data.getX());
            stmt.setDouble(3, data.getY());
            stmt.setDouble(4, data.getZ());
        }


        @Override
        public Location deserialize(@NotNull ResultSet rs) throws SQLException {
            return new Location(
                    Bukkit.getWorld(rs.getString("world")),
                    rs.getDouble("x"),
                    rs.getDouble("y"),
                    rs.getDouble("z")
            );
        }

    }
*/

}
