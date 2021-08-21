package com.github.bakuplayz.cropclick.events.player.interact;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

public final class PlayerInteractAtAutofarmEvent extends Event implements Cancellable {

    private final @Getter Player player;
    private final @Getter Autofarm autofarm; // might not be required.

    private @Setter @Getter boolean cancelled;

    // Should be called once a Player presses a crop, dispenser or container.

    public PlayerInteractAtAutofarmEvent(final @NotNull Player player,
                                         final @NotNull Autofarm autofarm) {
        this.autofarm = autofarm;
        this.player = player;
    }
}
