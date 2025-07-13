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

import com.github.bakuplayz.cropclick.common.Autofarms;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.datacontainers.services.autofarm.AutofarmDataService;
import lombok.AllArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

/**
 * A class responsible for locating and retrieving {@link Autofarm Autofarms} based on
 * identifiers such as crop block, container block, etc.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.2.0
 */
@AllArgsConstructor
public final class AutofarmFinder {

    @NotNull
    private final AutofarmDataService service;

    @NotNull
    private final CropManager cropManager;


    /**
     * Finds the {@link Autofarm autofarm} based on the {@link Block provided block}.
     *
     * @param block the block to base the findings on.
     *
     * @return the found autofarm, otherwise CompletableFuture of null.
     */
    @NotNull
    public CompletableFuture<Autofarm> findByBlock(@NotNull Block block) {
        if (Blocks.isAir(block)) {
            return CompletableFuture.completedFuture(null);
        }

        if (AutofarmBlocksCache.hasCachedID(block)) {
            String farmerID = AutofarmBlocksCache.getCachedID(block);
            return findById(farmerID);
        }

        if (Autofarms.isDispenser(block)) {
            return findByDispenser(block);
        }

        if (Autofarms.isContainer(block)) {
            return findByContainer(block);
        }

        if (Autofarms.isCrop(cropManager, block)) {
            return findByCrop(block);
        }

        Block blockAbove = block.getRelative(BlockFace.UP);
        if (Autofarms.isCrop(cropManager, blockAbove)) {
            return findByCrop(blockAbove);
        }

        return CompletableFuture.completedFuture(null);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided farmerID.
     *
     * @param farmerID the id to base the findings on.
     *
     * @return the found autofarm, otherwise CompletableFuture of null.
     */
    @NotNull
    public CompletableFuture<Autofarm> findById(String farmerID) {
        return farmerID == null ? CompletableFuture.completedFuture(null) : service.getOne(farmerID);
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block crop block}.
     *
     * @param block the crop block to base the findings on.
     *
     * @return the found autofarm, otherwise CompletableFuture of null.
     */
    @NotNull
    public CompletableFuture<Autofarm> findByCrop(@NotNull Block block) {
        return service.getOneByCrop(block.getLocation());
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block dispenser block}.
     *
     * @param block the dispenser block to base the findings on.
     *
     * @return the found autofarm, otherwise CompletableFuture of null.
     */
    @NotNull
    public CompletableFuture<Autofarm> findByDispenser(@NotNull Block block) {
        return service.getOneByDispenser(block.getLocation());
    }


    /**
     * Finds the {@link Autofarm autofarm} based on the provided {@link Block container block}.
     *
     * @param block the container block to base the findings on.
     *
     * @return the found autofarm, otherwise CompletableFuture of null.
     */
    @NotNull
    public CompletableFuture<Autofarm> findByContainer(@NotNull Block block) {
        return service.getOneByContainer(block.getLocation());
    }

}
