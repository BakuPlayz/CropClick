package com.github.bakuplayz.cropclick.location;

import com.google.gson.annotations.JsonAdapter;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@ToString
public final class DoublyLocation extends Location {

    @JsonAdapter(LocationTypeAdapter.class)
    private final @Getter Location one;

    @JsonAdapter(LocationTypeAdapter.class)
    private final @Getter Location two;


    public DoublyLocation(@NotNull Location one, @NotNull Location two) {
        super(one.getWorld(), one.getBlockX(), one.getBlockY(), one.getBlockZ());
        this.one = one;
        this.two = two;
    }

}