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

package com.github.bakuplayz.cropclick.menu.menus.links;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.menu.base.LinkMenu;
import org.bukkit.block.Dispenser;


/**
 * An enumeration representing all the different components in an {@link Autofarm autofarm}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see DispenserLinkMenu
 * @see ContainerLinkMenu
 * @see CropLinkMenu
 * @see LinkMenu
 * @since 2.0.0
 */
public enum Component {

    /**
     * The {@link Crop} component of the {@link Autofarm}.
     */
    CROP,

    /**
     * The {@link Container} component of the {@link Autofarm}.
     */
    CONTAINER,

    /**
     * The {@link Dispenser} component of the {@link Autofarm}.
     */
    DISPENSER

}