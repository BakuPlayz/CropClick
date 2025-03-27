/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
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

package com.github.bakuplayz.cropclick.commands.subcommands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.LoggerContext;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.configurations.AbstractConfiguration;
import com.github.bakuplayz.cropclick.datacontainers.DataStorage;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Command.*;


/**
 * A class representing the '/crop reset' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@AllArgsConstructor
public final class ResetCommand implements Subcommand, LoggerContext {

    private final CropClick plugin;


    @NotNull
    @Override
    public String getName() {
        return "reset";
    }


    @NotNull
    @Override
    public String getDescription() {
        return RESET_DESCRIPTION.get(plugin);
    }


    /**
     * Performs the '/crop reset' command, resetting all settings to default.
     *
     * @param player the player executing the command.
     * @param args   the arguments passed along the command.
     */
    @Override
    public void perform(@NotNull Player player, String[] args) {
        try {
            deleteConfigs();
            deleteDataStorages();
            RESET_DELETE.send(plugin, player);
        } catch (IOException e) {
            getLogger().severe(e.getMessage());
            RESET_FAILED.send(plugin, player);
        } finally {
            plugin.onReset();
            RESET_SUCCESS.send(plugin, player);
        }
    }


    /**
     * Deletes all the {@link AbstractConfiguration config files}.
     *
     * @throws IOException thrown if any deletion failed.
     */
    private void deleteConfigs() throws IOException {
        File pluginFolder = plugin.getDataFolder();
        Files.deleteIfExists(new File(pluginFolder, "crops.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "config.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "addons.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "players.yml").toPath());
        Files.deleteIfExists(new File(pluginFolder, "language.yml").toPath());
    }


    /**
     * Deletes all the {@link DataStorage data storage files}.
     *
     * @throws IOException thrown if any deletion failed.
     */
    private void deleteDataStorages() throws IOException {
        File pluginFolder = plugin.getDataFolder();
        Files.deleteIfExists(new File(pluginFolder, "worlds.json").toPath());
        Files.deleteIfExists(new File(pluginFolder, "autofarms.json").toPath());
    }

}