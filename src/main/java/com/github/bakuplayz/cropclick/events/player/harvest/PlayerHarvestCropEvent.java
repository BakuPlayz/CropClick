package com.github.bakuplayz.cropclick.events.player.harvest;

import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.events.harvest.HarvestCropEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerHarvestCropEvent extends HarvestCropEvent {

    private final @Getter Player player;

    private @Setter @Getter boolean cancelled;


    public PlayerHarvestCropEvent(@NotNull Crop crop, @NotNull Block block, @NotNull Player player) {
        super(crop, block);
        this.player = player;
    }

}