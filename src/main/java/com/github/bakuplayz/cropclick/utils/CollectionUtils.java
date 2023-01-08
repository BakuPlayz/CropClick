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