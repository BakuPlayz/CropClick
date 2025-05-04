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
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.tasks.CleanupTask;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


public final class AbstractDataContainer<D> implements DataContainer<D> {

    private final static long SAVE_INTERVAL = 30 * 1000 * 20L;

    private final File file;

    private final String fileName;

    private final ObjectMapper mapper;

    private final Map<String, D> data;

    private final CropClick plugin;


    public AbstractDataContainer(@NotNull String fileName, @NotNull CropClick plugin) {
        this.mapper = plugin.getDatabaseManager().getJsonMapper();
        this.file = getNewFileInstance();
        this.data = new HashMap<>();
        this.fileName = fileName;
        this.plugin = plugin;

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


    public int countAll() {
        return data.values().size();
    }


    public void reset() {
        try {
            Files.delete(Paths.get(file.getAbsolutePath()));
            createIfAbsent();
        } catch (NoSuchFileException e) {
            Log.debug("Could not delete file: {}, not found.", file.getAbsolutePath());
        } catch (SecurityException e) {
            Log.severe("Could not remove file {}, due to security policy.", file.getAbsolutePath());
        } catch (IOException | UnsupportedOperationException e) {
            Log.severe("Could not remove file {}, due to unknown reasons.", file.getAbsolutePath());
        }
    }


    /**
     * Creates the JSON (backing) file if it doesn't already exist.
     */
    private void createIfAbsent() {
        try {
            Files.createFile(Paths.get(file.getAbsolutePath()));
        } catch (FileAlreadyExistsException e) {
            Log.debug("Could not create file {}, already created.", file.getAbsolutePath());
        } catch (SecurityException e) {
            Log.severe("Could not create file {}, due to security policy.", file.getAbsolutePath());
        } catch (IOException | UnsupportedOperationException e) {
            Log.severe("Could not create file {}, due to unknown reasons.", file.getAbsolutePath());
        }
    }


    /**
     * Initializes a repeating asynchronous save task that runs at a fixed interval.
     */
    private void setupSave() {
        plugin.getTaskScheduler().scheduleRepeatingTask((CleanupTask) () -> {
            if (!trySave(3)) {
                Log.severe("Could not save file {}, due to unknown reasons.", file.getAbsolutePath());
                return;
            }

            Log.info("Successfully saved file {}.", file.getAbsolutePath());
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
        return new File(plugin.getDataFolder().getAbsolutePath() + "/data", fileName);
    }

}
