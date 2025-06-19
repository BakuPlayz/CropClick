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

import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A factory class for easily creating {@link Container containers}.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class Container {

    public static XMaterial[] TYPES = new XMaterial[]{
            XMaterial.CHEST,
            XMaterial.BARREL,
            XMaterial.SHULKER_BOX,
            XMaterial.WHITE_SHULKER_BOX,
            XMaterial.ORANGE_SHULKER_BOX,
            XMaterial.MAGENTA_SHULKER_BOX,
            XMaterial.LIGHT_BLUE_SHULKER_BOX,
            XMaterial.YELLOW_SHULKER_BOX,
            XMaterial.LIME_SHULKER_BOX,
            XMaterial.PINK_SHULKER_BOX,
            XMaterial.GRAY_SHULKER_BOX,
            XMaterial.LIGHT_GRAY_SHULKER_BOX,
            XMaterial.CYAN_SHULKER_BOX,
            XMaterial.PURPLE_SHULKER_BOX,
            XMaterial.BLUE_SHULKER_BOX,
            XMaterial.BROWN_SHULKER_BOX,
            XMaterial.GREEN_SHULKER_BOX,
            XMaterial.RED_SHULKER_BOX,
            XMaterial.BLACK_SHULKER_BOX
    };

    @NotNull
    private final Inventory inventory;


    @NotNull
    public static Container fromPlayer(@NotNull CropPlayer player) {
        return new Container(player.getOfflinePlayer().getPlayer().getInventory());
    }


    @Nullable
    public static Container fromBlock(@NotNull Block block) {
        BlockState state = block.getState();

        if (state instanceof Chest) {
            return new Container(((Chest) state).getInventory());
        }

        if (Versions.supportsShulkers() && state instanceof org.bukkit.block.ShulkerBox) {
            return new Container(((org.bukkit.block.ShulkerBox) state).getInventory());
        }

        if (Versions.supportsBarrel() && state instanceof org.bukkit.block.Barrel) {
            return new Container(((org.bukkit.block.Barrel) state).getInventory());
        }

        if (Blocks.isDoubleChest(block)) {
            return new Container(((Chest) state).getInventory());
        }

        if (state instanceof org.bukkit.block.Container) {
            return new Container(((org.bukkit.block.Container) state).getInventory());
        }

        return null;
    }

}