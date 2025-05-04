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
package com.github.bakuplayz.cropclick.mappers;

import com.github.bakuplayz.cropclick.Component;
import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.crops.CropAgeComponent;
import org.jetbrains.annotations.NotNull;

/**
 * A class representing a reflection ComponentMapper, which
 * maps the right to the correct component based on the given
 * Minecraft versions (0-12.9: legacy, above: latest).
 */
public final class ComponentMapper {

    private final static String VERSION = Versions.between(0.0, 12.9) ? "legacy" : "latest";


    @NotNull
    public static CropAgeComponent getAge() {
        return (CropAgeComponent) getMappedComponent("crops", "CropAge");
    }


    @NotNull
    public static ContainerComponent getContainer() {
        return (ContainerComponent) getMappedComponent("autofarms", "Container");
    }


    @NotNull
    @SuppressWarnings("unchecked")
    private static <T extends Component> Component getMappedComponent(@NotNull String packagePath, @NotNull String className) {
        try {
            return (T) Class.forName(String.format("com.github.bakuplayz.cropclick.%s.%s.%s", VERSION, packagePath, className))
                               .getDeclaredConstructor()
                               .newInstance();
        } catch (ClassNotFoundException exception) {
            throw new IllegalStateException("CropClick does not support this server version.", exception);
        } catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to instantiate container wrapper.", exception);
        }
    }

}
