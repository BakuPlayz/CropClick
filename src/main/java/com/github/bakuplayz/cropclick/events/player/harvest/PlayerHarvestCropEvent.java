package com.github.bakuplayz.cropclick.events.player.harvest;

import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.events.harvest.HarvestCropEvent;
import lombok.Getter;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * An event called when a {@link Player} harvests a {@link BaseCrop crop}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class PlayerHarvestCropEvent extends HarvestCropEvent {

    private final @Getter Player player;


    public PlayerHarvestCropEvent(@NotNull BaseCrop crop, @NotNull Block block, @NotNull Player player) {
        super(crop, block);
        this.player = player;
    }

}