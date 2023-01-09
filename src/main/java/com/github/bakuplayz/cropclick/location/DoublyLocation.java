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

package com.github.bakuplayz.cropclick.location;

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

    private final @Getter Location singly;

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