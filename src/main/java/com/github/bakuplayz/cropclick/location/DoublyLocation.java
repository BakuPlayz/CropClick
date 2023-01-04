package com.github.bakuplayz.cropclick.location;

import com.google.gson.annotations.JsonAdapter;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;


/**
 * A location that contains two positions, viz. a doubly location.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
@ToString
public final class DoublyLocation extends Location {

    @JsonAdapter(LocationTypeAdapter.class)
    private final @Getter Location singly;

    @JsonAdapter(LocationTypeAdapter.class)
    private final @Getter Location doubly;

    private final transient @Getter double doublyX;
    private final transient @Getter double doublyY;
    private final transient @Getter double doublyZ;

    private final transient @Getter int doublyBlockX;
    private final transient @Getter int doublyBlockY;
    private final transient @Getter int doublyBlockZ;

    private final transient @Getter float doublyYaw;
    private final transient @Getter float doublyPitch;

    private final transient @Getter Block doublyBlock;
    private final transient @Getter Chunk doublyChunk;
    private final transient @Getter World doublyWorld;
    private final transient @Getter Vector doublyDirection;


    public DoublyLocation(@NotNull Location singly, @NotNull Location doubly) {
        super(singly.getWorld(), singly.getBlockX(), singly.getBlockY(), singly.getBlockZ());
        this.singly = singly;
        this.doubly = doubly;
        this.doublyX = getDoubly().getX();
        this.doublyY = getDoubly().getY();
        this.doublyZ = getDoubly().getZ();
        this.doublyYaw = getDoubly().getYaw();
        this.doublyBlock = getDoubly().getBlock();
        this.doublyWorld = getDoubly().getWorld();
        this.doublyChunk = getDoubly().getChunk();
        this.doublyPitch = getDoubly().getPitch();
        this.doublyBlockX = getDoubly().getBlockX();
        this.doublyBlockY = getDoubly().getBlockY();
        this.doublyBlockZ = getDoubly().getBlockZ();
        this.doublyDirection = getDoubly().getDirection();
    }

}