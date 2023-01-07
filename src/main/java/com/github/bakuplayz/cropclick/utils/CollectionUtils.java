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
     * Return a new list with the elements of the given list in reverse order.
     *
     * @param list The list to be reversed.
     *
     * @return A new list with the same elements as the original list, but in reverse order.
     */
    public static <E> @NotNull List<E> reverseOrder(@NotNull List<E> list) {
        List<E> reversed = new ArrayList<>();

        int size = list.size() - 1;
        for (int i = size; i > 0; i--) {
            reversed.add(list.get(i));
        }

        return reversed;
    }

}