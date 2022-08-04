package com.github.bakuplayz.cropclick.worlds;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
@ToString
@EqualsAndHashCode
public final class FarmWorld {

    private final @Getter String name;

    private @Setter @Getter @Accessors(fluent = true) boolean isBanished;
    private @Setter @Getter @Accessors(fluent = true) boolean allowsPlayers;
    private @Setter @Getter @Accessors(fluent = true) boolean allowsAutofarms;

    private final @Getter List<Addon> banishedAddons;


    public FarmWorld(@NotNull World world) {
        this.banishedAddons = new ArrayList<>();
        this.name = world.getName();
        this.allowsAutofarms = true;
        this.allowsPlayers = true;
    }


    public FarmWorld(@NotNull String name,
                     boolean isBanished,
                     boolean allowsPlayers,
                     boolean allowsAutofarms,
                     List<Addon> banishedAddons) {
        this.allowsAutofarms = allowsAutofarms;
        this.banishedAddons = banishedAddons;
        this.allowsPlayers = allowsPlayers;
        this.isBanished = isBanished;
        this.name = name;
    }


    /**
     * If the addon is banished, remove it from the banished list, otherwise add it to the banished list.
     *
     * @param manager The AddonManager instance that you're using.
     * @param name    The name of the addon to toggle.
     */
    public void toggleAddon(@NotNull AddonManager manager, @NotNull String name) {
        Addon addon = manager.findByName(name);
        if (addon == null) {
            return;
        }

        if (banishedAddons.contains(addon)) {
            banishedAddons.remove(addon);
        } else {
            banishedAddons.add(addon);
        }
    }


    /**
     * If the addon is not found or is in the banishedAddons, it is banished.
     *
     * @param manager The AddonManager instance
     * @param name    The name of the addon to check
     *
     * @return A boolean value.
     */
    public boolean isBanishedAddon(@NotNull AddonManager manager, @NotNull String name) {
        Addon addon = manager.findByName(name);
        if (addon == null) {
            return false;
        }
        return banishedAddons.contains(addon);
    }

}