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
package com.github.bakuplayz.cropclick.menus.addons.auraskills;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.auraskills.AuraSkillsAddon;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractWorldsMenu;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.WORLDS_ITEM_AURA_SKILLS_TIPS;
import static com.github.bakuplayz.cropclick.common.Languages.Menu.WORLDS_ITEM_STATUS;

/**
 * A class representing the Worlds menu scoped for AuraSkills.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class WorldsMenu extends AbstractWorldsMenu {

    public WorldsMenu(@NotNull CropClick plugin) {
        super(plugin, (world) -> getItemLore(plugin, world));
    }


    @NotNull
    @Unmodifiable
    private static List<String> getItemLore(@NotNull CropClick plugin, @NotNull FarmWorld world) {
        boolean isBanished = world.isAddonBanished(getAddon(plugin, AuraSkillsAddon.NAME));
        return WORLDS_ITEM_AURA_SKILLS_TIPS.getAsAppendList(plugin, WORLDS_ITEM_STATUS.get(plugin, isBanished));
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull FarmWorld world, int position) {
        return (item, player) -> stateHandler.toggleWorld(world, AuraSkillsAddon.NAME, position);
    }

}
