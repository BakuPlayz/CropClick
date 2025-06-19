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

package com.github.bakuplayz.cropclick.crops.seeds.ground;

import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.Drop;
import com.github.bakuplayz.cropclick.crops.seeds.AbstractSeed;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;


/**
 * A class that represents a wheat seed.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Seed
 * @since 2.0.0
 */
public final class WheatSeed extends AbstractSeed {

    public WheatSeed(@NotNull CropsConfig config) {
        super(config);
    }


    /**
     * Gets the name of the {@link AbstractSeed seed}.
     *
     * @return the name of the seed.
     */
    @NotNull
    @Override
    public String getName() {
        return "wheatSeed";
    }


    /**
     * Gets the {@link AbstractSeed seed's} drop.
     *
     * @return the seed's drop.
     */
    @NotNull
    @Override
    public Drop getDrop() {
        return createDrop(3, 0.80);
    }


    /**
     * Gets the {@link Seed seed's} menu type.
     *
     * @return the seed's menu type.
     */
    @NotNull
    @Override
    public XMaterial getMenuType() {
        return XMaterial.WHEAT_SEEDS;
    }

}