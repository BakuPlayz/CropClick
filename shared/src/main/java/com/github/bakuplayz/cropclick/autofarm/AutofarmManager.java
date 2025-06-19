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

package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.configurations.config.DefaultConfig.ConfigurationKey;


/**
 * A class managing {@link Autofarm Autofarms}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.2.0
 */
public final class AutofarmManager {

    private static final int MAX_AUTOFARMS_FETCH = 10_000;


    private final DefaultConfig config;

    @Getter
    private final AutofarmFinder finder;

    @Getter
    private final AutofarmBlocksCache blocksCache;

    private final AutofarmDataService dataService;


    public AutofarmManager(@NotNull CropClick plugin) {
        this.blocksCache = new AutofarmBlocksCache(plugin);
        this.config = plugin.getConfigManager().getDefaultConfig();
        this.dataService = plugin.getDataManager().getAutofarmService();
        this.finder = new AutofarmFinder(dataService, plugin.getCropManager());
    }


    /**
     * Gets all the {@link Autofarm autofarms}.
     *
     * @return the found autofarms.
     */
    @NotNull
    public CompletableFuture<List<Autofarm>> getAutofarms() {
        return dataService.getMany(0, MAX_AUTOFARMS_FETCH);
    }


    /**
     * TODO: Do something about, I don't like this one.
     * <p>
     * Checks whether the {@link Autofarm provided autofarm} is usable.
     *
     * @param autofarm the autofarm to check.
     *
     * @return true if usable, otherwise false.
     */
    public boolean isUsable(Autofarm autofarm) {
        if (autofarm == null) return false;
        if (!autofarm.isEnabled()) return false;
        return config.getBoolean(ConfigurationKey.AUTOFARMS_ENABLED);
    }


    /**
     * Gets the amount of {@link Autofarm autofarms}.
     *
     * @return the amount of autofarms.
     */
    @NotNull
    public CompletableFuture<Integer> getAmountOfFarms() {
        return dataService.countAll();
    }

}