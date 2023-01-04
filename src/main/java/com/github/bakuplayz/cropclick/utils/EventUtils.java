package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.events.Event;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
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
     * Checks whether the {@link Player provided player} is sneaking/shifting and left-clicking at the same time.
     *
     * @param player the player to check.
     * @param action the action performed.
     *
     * @return true if it did, otherwise false.
     */
    public static boolean isLeftShift(@NotNull Player player, @NotNull Action action) {
        return player.isSneaking() && isLeftClick(action);
    }


    /**
     * Checks whether the {@link Action provided action} is a left-click.
     *
     * @param action the action to check.
     *
     * @return true if it is, otherwise false.
     */
    public static boolean isLeftClick(@NotNull Action action) {
        return action == Action.LEFT_CLICK_BLOCK;
    }


    /**
     * Checks whether the {@link EquipmentSlot provided hand} is the main hand.
     *
     * @param hand the hand to check.
     *
     * @return true if it is, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public static boolean isMainHand(EquipmentSlot hand) {
        return hand == EquipmentSlot.HAND;
    }

}