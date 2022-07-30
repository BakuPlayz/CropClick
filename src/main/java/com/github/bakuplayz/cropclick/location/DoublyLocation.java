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
    private final @Getter Location singly;

    @JsonAdapter(LocationTypeAdapter.class)
    private final @Getter Location doubly;


    public DoublyLocation(@NotNull Location singly, @NotNull Location doubly) {
        super(singly.getWorld(), singly.getBlockX(), singly.getBlockY(), singly.getBlockZ());
        this.singly = singly;
        this.doubly = doubly;
    }

}