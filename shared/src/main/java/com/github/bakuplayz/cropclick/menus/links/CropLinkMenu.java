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
package com.github.bakuplayz.cropclick.menus.links;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractLinkMenu;
import com.github.bakuplayz.cropclick.menus.abstracts.states.LinkMenuStateBuilder.LinkContext;
import org.bukkit.block.Block;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.CROP_LINK_TITLE;

/**
 * A class representing the Crop Link menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CropLinkMenu extends AbstractLinkMenu {

    public CropLinkMenu(@NotNull CropClick plugin, Autofarm autofarm, @NotNull Block block, boolean showBackButton) {
        super(plugin, CROP_LINK_TITLE.getTitle(plugin), autofarm, block, showBackButton);
    }


    @Override
    public LinkContext getContext() {
        return LinkContext.CROP;
    }

}
