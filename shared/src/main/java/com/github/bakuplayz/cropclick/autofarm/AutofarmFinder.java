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
package com.github.bakuplayz.cropclick.autofarm;

import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import lombok.AllArgsConstructor;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@AllArgsConstructor
public final class AutofarmFinder {

    @NotNull
    private final AutofarmDataService service;


    /**
     * Finds the {@link Autofarm autofarm} based on the provided farmerID.
     *
     * @param farmerID the id to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    @Nullable
    public Autofarm findById(String farmerID) {
        return farmerID == null ? null : service.getOne(farmerID).join();
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block crop block}.
     *
     * @param block the crop block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    @Nullable
    public Autofarm findByCrop(@NotNull Block block) {
        return service.getOneByCrop(block.getLocation()).join();
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block dispenser block}.
     *
     * @param block the dispenser block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    @Nullable
    public Autofarm findByDispenser(@NotNull Block block) {
        return service.getOneByDispenser(block.getLocation()).join();
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block container block}.
     *
     * @param block the container block to base the findings on.
     *
     * @return the found autofarm, otherwise null.
     */
    public Autofarm findByContainer(@NotNull Block block) {
        return service.getOneByContainer(block.getLocation()).join();
    }

}
