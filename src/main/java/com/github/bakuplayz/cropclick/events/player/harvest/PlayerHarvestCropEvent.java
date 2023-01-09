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

package com.github.bakuplayz.cropclick.events.player.harvest;

import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.harvest.HarvestCropEvent;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Player} harvests a {@link Crop crop}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerHarvestCropEvent extends HarvestCropEvent {

    private final @Getter Player player;


    public PlayerHarvestCropEvent(@NotNull Crop crop, @NotNull Block block, @NotNull Player player) {
        super(crop, block);
        this.player = player;
    }

}