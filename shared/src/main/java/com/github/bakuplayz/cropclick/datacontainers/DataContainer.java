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

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.sql.SQLSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class DataContainer<D> implements DataService<D> {

    private final DataService<D> service;


    public DataContainer(@NotNull DataService<D> service) {
        this.service = service;
    }


    @Override
    public List<D> getMany() {
        return service.getMany();
    }


    public D getOne(@NotNull UUID id) {
        return service.getOne(id);
    }


    @Override
    public void save() {
        service.save();
    }


    // Meant for local files, aka not for db.
    static class LocalAutofarmService implements DataService<Autofarm> {

        private HashMap<UUID, Autofarm> data;


        @Override
        public List<Autofarm> getMany() {
            return (List<Autofarm>) data.values();
        }


        @Override
        public Autofarm getOne(@NotNull UUID id) {
            return data.values().stream().filter(a -> a.getFarmerId() == id).findAny().orElse(null);
        }


        @Override
        public void save() {

        }
    }

    static class AutofarmSerializer implements SQLSerializer<Autofarm> {

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

    static class RemoteAutofarmService implements DataService<Autofarm> {

        private final Connection connection;

        private final AutofarmSerializer autofarmSerializer;


        public RemoteAutofarmService(Connection connection) {
            this.connection = connection;
            this.autofarmSerializer = new AutofarmSerializer();
        }


        @Override
        public List<Autofarm> getMany() {
            return null;
        }


        @Override
        public Autofarm getOne(@NotNull UUID id) {
            String query = "SELECT * FROM autofarms WHERE farmer_id = ?";

            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, id.toString());
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    return autofarmSerializer.deserialize(rs);
                }
            } catch (SQLException e) {
                return null;
            }
            return null;
        }


        @Override
        public void save() {

        }
    }

    // DataService<D> service = new LocalAutofarmService();
    // DataContainer<AutoFarm> autofarms = new DataContainer(service);
    //

}
