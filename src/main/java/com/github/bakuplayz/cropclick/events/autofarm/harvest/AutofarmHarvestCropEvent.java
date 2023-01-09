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

package com.github.bakuplayz.cropclick.events.autofarm.harvest;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.harvest.HarvestCropEvent;
import lombok.Getter;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Autofarm} harvests a {@link Crop crop}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class AutofarmHarvestCropEvent extends HarvestCropEvent {

    private final @Getter Autofarm autofarm;


    public AutofarmHarvestCropEvent(@NotNull Crop crop, @NotNull Block block, @NotNull Autofarm autofarm) {
        super(crop, block);
        this.autofarm = autofarm;
    }

}