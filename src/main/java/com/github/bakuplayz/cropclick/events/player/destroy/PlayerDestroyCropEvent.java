package com.github.bakuplayz.cropclick.events.player.destroy;

import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PlayerDestroyCropEvent extends Event {

    private final @Getter Block block;
    private final @Getter Player player;

    public PlayerDestroyCropEvent(final @NotNull Block block,
                                  final @NotNull Player player) {
        this.player = player;
        this.block = block;
    }

}
