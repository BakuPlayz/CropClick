package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.events.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.jetbrains.annotations.NotNull;


/**
 * A utility class for {@link Event events}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class EventUtils {

    /**
     * Returns true if the player is sneaking and left-clicking a block.
     *
     * @param player The player who clicked the block.
     * @param action The action that was performed.
     *
     * @return A boolean value.
     */
    public static boolean isLeftShift(@NotNull Player player, @NotNull Action action) {
        return player.isSneaking() && isLeftClick(action);
    }


    /**
     * Returns true if the action is a right click on a block.
     *
     * @param action The action that was performed.
     *
     * @return A boolean value.
     */
    public static boolean isRightClick(@NotNull Action action) {
        return action == Action.RIGHT_CLICK_BLOCK;
    }


    /**
     * Returns true if the action is a left click on a block.
     *
     * @param action The action that was performed.
     *
     * @return A boolean value.
     */
    public static boolean isLeftClick(@NotNull Action action) {
        return action == Action.LEFT_CLICK_BLOCK;
    }


    /**
     * Returns true if the player is using their main hand, false otherwise.
     *
     * @param event The event that was called.
     *
     * @return The hand that the player is using.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isMainHand(@NotNull PlayerInteractEvent event) {
        return event.getHand() == EquipmentSlot.HAND;
    }

}