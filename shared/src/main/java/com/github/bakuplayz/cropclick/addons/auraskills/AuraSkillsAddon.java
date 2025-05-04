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
package com.github.bakuplayz.cropclick.addons.auraskills;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the <a href="https://www.spigotmc.org/resources/aurelium-skills-advanced-skills-stats-abilities-and-more.81069/">AuraSkills</a> addon.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.0.0
 */
@Getter
public final class AuraSkillsAddon extends AbstractAddon {

    public static final String NAME = "AuraSkills";

    private AuraSkillsFunctionality functionality;


    public AuraSkillsAddon(@NotNull CropClick plugin) {
        super(plugin, NAME);
    }


    @Override
    public void setup() {
        if (isInstalled()) {
            this.functionality = new AuraSkillsFunctionality(cropsConfig);
        }
    }

}
