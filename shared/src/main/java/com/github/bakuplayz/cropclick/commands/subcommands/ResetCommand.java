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
import com.github.bakuplayz.cropclick.Log;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.configurations.AbstractConfiguration;
import com.github.bakuplayz.cropclick.configurations.Configuration;
import com.github.bakuplayz.cropclick.datacontainers.services.DataService;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Command.*;


/**
 * A class representing the '/crop reset' command.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@AllArgsConstructor
public final class ResetCommand implements Subcommand {

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
            resetConfigs();
            deleteDataStorages();
            RESET_DELETE.send(plugin, player);
        } catch (IOException e) {
            Log.severe(e.getMessage());
            RESET_FAILED.send(plugin, player);
        } finally {
            RESET_SUCCESS.send(plugin, player);
        }
    }


    /**
     * Resets all the {@link AbstractConfiguration config files}.
     *
     * @throws IOException thrown if any deletion failed.
     */
    private void resetConfigs() throws IOException {
        plugin.getConfigManager().getAll().forEach(Configuration::reset);
    }


    /**
     * Deletes all the {@link DataService data service files}.
     *
     * @throws IOException thrown if any deletion failed.
     */
    private void deleteDataStorages() throws IOException {
        plugin.getDataManager().getAll().forEach(DataService::reset);
    }


}