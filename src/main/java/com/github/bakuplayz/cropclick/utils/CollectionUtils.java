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

package com.github.bakuplayz.cropclick.utils;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * A utility class for {@link Collection collections}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CollectionUtils {

    /**
     * Reverses the order of the items in the list.
     *
     * @param list the list to be reversed.
     * @param <E>  the object provided.
     *
     * @return the list with reversed items.
     */
    public static <E> @NotNull List<E> reverseOrder(@NotNull List<E> list) {
        List<E> reversed = new ArrayList<>();

        int size = list.size() - 1;
        for (int i = size; i > 0; i--) {
            reversed.add(list.get(i));
        }

        return reversed;
    }


    /**
     * Toggles the provided item by adding or removing it each call.
     *
     * @param list the list to add/remove to.
     * @param item the item to toggle.
     * @param <E>  the object provided.
     *
     * @return the list with the added or removed item.
     */
    public static <E> @NotNull List<E> toggleItem(@NotNull List<E> list, E item) {
        if (list.contains(item)) {
            list.remove(item);
        } else {
            list.add(item);
        }
        return list;
    }

}