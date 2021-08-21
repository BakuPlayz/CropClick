package com.github.bakuplayz.cropclick.events.player.plant;

import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class PlayerPlantCropEvent extends Event {

    private final @Getter Block block;
    private final @Getter Player player;

    public PlayerPlantCropEvent(final @NotNull Block block,
                                final @NotNull Player player){
        this.player = player;
        this.block = block;
    }
}
