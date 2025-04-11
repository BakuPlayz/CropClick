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
package com.github.bakuplayz.cropclick.datacontainers;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.LoggerContext;
import lombok.AccessLevel;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Console.*;


public final class AbstractDataContainer<D> implements DataContainer<D>, LoggerContext {

    private final String fileName;

    private final HashMap<String, D> data;

    private final Class<D> clazz;

    @Setter(AccessLevel.PRIVATE)
    private File file;
    // TODO: Create a saving thread for files...


    public AbstractDataContainer(@NotNull String fileName, @NotNull Class<D> clazz) {
        this.data = new HashMap<>();
        this.fileName = fileName;
        this.clazz = clazz;
        createIfAbsent();
        setupSave();
    }


    @Override
    public void add(@NotNull String key, @NotNull D entity) {
        data.put(key, entity);
    }


    @Override
    public boolean addIfAbsent(@NotNull String key, @NotNull D entity) {
        return data.putIfAbsent(key, entity) == null;
    }


    @Override
    public boolean remove(@NotNull String key) {
        return data.remove(key) != null;
    }


    @Override
    public D getOne(@NotNull String key) {
        return data.get(key);
    }


    @NotNull
    @Override
    public Collection<D> getMany() {
        return data.values();
    }


    private void createIfAbsent() {
        setFile(getNewFileInstance());
        try {
            Files.createFile(Paths.get(file.getAbsolutePath()));
        } catch (FileAlreadyExistsException e) {
            logDebug("Could not create file: {}, already existed.", file.getAbsolutePath());
        } catch (IOException e) {
            DATA_CONTAINER_FAILED_CREATE.send(getLogger(), file.getAbsolutePath());
        } catch (SecurityException e) {
            DATA_CONTAINER_FAILED_CREATE_SECURITY.send(getLogger(), file.getAbsolutePath());
        }
    }


    private void setupSave() {
        Bukkit.getScheduler().runTaskLaterAsynchronously(CropClick.getInstance(), () -> {
            if (!trySave(3)) {
                DATA_CONTAINER_FAILED_SAVE.send(getLogger(), file.getAbsolutePath());
            }
        }, 30 * 1000L);
    }


    private boolean trySave(int tries) {
        if (tries == 0) {
            return false;
        }

        try {
            Path temp = Files.createTempFile(UUID.randomUUID().toString(), ".tmp");

            try {
                // TODO: See if jackson can handle this in a better way will be bad behaviour
                //       now when trying to save just the model class and not a collection of it.
                Files.write(
                        temp,
                        ModelRegistry.get(clazz).toJSONString().getBytes(StandardCharsets.UTF_8),
                        StandardOpenOption.TRUNCATE_EXISTING
                );
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
