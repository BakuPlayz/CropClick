package com.github.bakuplayz.cropclick.autofarm;

import com.google.gson.annotations.JsonAdapter;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.jetbrains.annotations.NotNull;

import javax.xml.stream.Location;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@ToString
@EqualsAndHashCode
public final class DoublyLocation {

    @JsonAdapter(LocationTypeAdapter.class)
    private final Location one;

    @JsonAdapter(LocationTypeAdapter.class)
    private final Location two;


    public DoublyLocation(@NotNull Location one, @NotNull Location two) {
        this.one = one;
        this.two = two;
    }

}