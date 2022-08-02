package com.github.bakuplayz.cropclick.addons.addon;


import com.bekvon.bukkit.residence.Residence;
import com.bekvon.bukkit.residence.containers.Flags;
import com.bekvon.bukkit.residence.protection.ClaimedResidence;
import com.bekvon.bukkit.residence.protection.FlagPermissions;
import com.bekvon.bukkit.residence.protection.ResidenceManager;
import com.bekvon.bukkit.residence.protection.ResidencePermissions;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class ResidenceAddon extends Addon {

    private Flags flag;


    public ResidenceAddon(@NotNull CropClick plugin) {
        super(plugin, "Residence");

        registerFlag();
    }


    /**
     * Register the flag with the plugin.
     */
    private void registerFlag() {
        FlagPermissions.addFlag("cropclick");
        flag = Flags.getFlag("cropclick");
    }


    /**
     * If the player is in a residence, and that residence or the player has the flag, return true.
     *
     * @param player The player who is trying to use the command.
     * @return A boolean value.
     */
    public boolean regionOrPlayerHasFlag(@NotNull Player player) {
        ResidenceManager manager = Residence.getInstance().getResidenceManager();
        if (manager == null) return false;

        ClaimedResidence claimed = manager.getByLoc(player.getLocation());
        if (claimed == null) return false;

        ResidencePermissions permissions = claimed.getPermissions();
        if (permissions == null) return false;
        if (permissions.getFlags() == null) return false;
        if (permissions.getFlags().isEmpty()) return false;

        boolean residenceHasFlag = permissions.has(flag, true);
        boolean playerHasFlag = permissions.playerHas(player, flag, true);

        return residenceHasFlag || playerHasFlag;
    }

}