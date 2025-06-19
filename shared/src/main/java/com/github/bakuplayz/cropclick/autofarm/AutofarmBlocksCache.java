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

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.metadata.AutofarmMetadata;
import com.github.bakuplayz.cropclick.common.Locations;
import com.github.bakuplayz.cropclick.common.types.DoublyLocation;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.bukkit.block.Block;
import org.bukkit.metadata.MetadataValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
public final class AutofarmBlocksCache {

    private final CropClick plugin;


    /**
     * Checks whether the {@link Block provided block} has the cached {@link Autofarm#getFarmerId() autofarmer ID}.
     *
     * @param block the block to check.
     *
     * @return true if it has, otherwise false.
     */
    public static boolean hasCachedID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(AutofarmMetadata.FARMER_ID_KEY);
        return block.hasMetadata(AutofarmMetadata.FARMER_ID_KEY) && !metas.isEmpty();
    }


    /**
     * Gets the cached {@link Autofarm#getFarmerId() autofarmer ID} based on the {@link Block provided block}.
     *
     * @param block the block to get the cached ID from.
     *
     * @return the found ID, otherwise null.
     */
    @Nullable
    public static String getCachedID(@NotNull Block block) {
        List<MetadataValue> metas = block.getMetadata(AutofarmMetadata.FARMER_ID_KEY);
        return metas.isEmpty() ? null : metas.get(0).asString();
    }


    /**
     * Adds the cached {@link Autofarm#getFarmerId() autofarmer ID} to all the {@link Autofarm autofarm's} components.
     *
     * @param autofarm the autofarm to add the IDs to.
     */
    public void addIDs(Autofarm autofarm) {
        if (autofarm == null) return;

        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        AutofarmMetadata farmerMeta = new AutofarmMetadata(plugin, autofarm::getFarmerId);

        DoublyLocation doublyLocation = Locations.findDoubly(container);
        if (doublyLocation != null) {
            Block singly = doublyLocation.getSingly().getBlock();
            Block doubly = doublyLocation.getDoubly().getBlock();

            singly.setMetadata(AutofarmMetadata.FARMER_ID_KEY, farmerMeta);
            doubly.setMetadata(AutofarmMetadata.FARMER_ID_KEY, farmerMeta);
        } else {
            container.setMetadata(AutofarmMetadata.FARMER_ID_KEY, farmerMeta);
        }

        dispenser.setMetadata(AutofarmMetadata.FARMER_ID_KEY, farmerMeta);
        crop.setMetadata(AutofarmMetadata.FARMER_ID_KEY, farmerMeta);
    }


    /**
     * Removes the cached {@link Autofarm#getFarmerId() autofarmer ID} from all the {@link Autofarm autofarm's} components.
     *
     * @param autofarm the autofarm to remove the IDs to.
     */
    public void removeIDs(Autofarm autofarm) {
        if (autofarm == null) return;

        Block dispenser = autofarm.getDispenserLocation().getBlock();
        Block container = autofarm.getContainerLocation().getBlock();
        Block crop = autofarm.getCropLocation().getBlock();

        DoublyLocation doublyContainer = Locations.findDoubly(container);
        if (doublyContainer != null) {
            Block singly = doublyContainer.getSingly().getBlock();
            Block doubly = doublyContainer.getDoubly().getBlock();

            singly.removeMetadata(AutofarmMetadata.FARMER_ID_KEY, plugin);
            doubly.removeMetadata(AutofarmMetadata.FARMER_ID_KEY, plugin);
        } else {
            container.removeMetadata(AutofarmMetadata.FARMER_ID_KEY, plugin);
        }

        dispenser.removeMetadata(AutofarmMetadata.FARMER_ID_KEY, plugin);
        crop.removeMetadata(AutofarmMetadata.FARMER_ID_KEY, plugin);
    }

}
