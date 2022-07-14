package com.github.bakuplayz.cropclick.events.player.link;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datastorages.datastorage.AutofarmDataStorage;
import com.github.bakuplayz.cropclick.events.Event;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class PlayerUpdateAutofarmEvent extends Event implements Cancellable {

    private final @Getter Player player;
    private final @Getter Autofarm newAutofarm;
    private final @Getter Autofarm oldAutofarm;

    private @Setter @Getter boolean cancelled;

    public PlayerUpdateAutofarmEvent(@NotNull Player player,
                                     @NotNull Autofarm oldAutofarm,
                                     @NotNull Autofarm newAutofarm) {
        this.oldAutofarm = oldAutofarm;
        this.newAutofarm = newAutofarm;
        this.player = player;
    }

    public PlayerUpdateAutofarmEvent(@NotNull Player player,
                                     @NotNull String farmerID,
                                     @NotNull Autofarm newAutofarm,
                                     @NotNull AutofarmDataStorage dataStorage) {
        this.oldAutofarm = dataStorage.findFarmById(farmerID);
        this.newAutofarm = newAutofarm;
        this.player = player;
    }

}
