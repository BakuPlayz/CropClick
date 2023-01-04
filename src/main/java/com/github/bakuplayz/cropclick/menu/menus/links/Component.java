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