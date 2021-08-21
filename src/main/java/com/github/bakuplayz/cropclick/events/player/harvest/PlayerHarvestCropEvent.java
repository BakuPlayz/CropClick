package com.github.bakuplayz.cropclick.events.player.harvest;

import com.github.bakuplayz.cropclick.crop.crops.templates.Crop;
import com.github.bakuplayz.cropclick.events.HarvestCropEvent;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class PlayerHarvestCropEvent extends HarvestCropEvent {

    private final @Getter Player player;
    private final @Getter Action action;

    public PlayerHarvestCropEvent(final @NotNull Crop crop,
                                  final @NotNull Block block,
                                  final @NotNull Action action,
                                  final @NotNull Player player) {
        super(crop, block);
        this.action = action;
        this.player = player;
    }
}
