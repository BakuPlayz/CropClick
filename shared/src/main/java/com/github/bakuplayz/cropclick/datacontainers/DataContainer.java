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

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public final class DataContainer {


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


}
