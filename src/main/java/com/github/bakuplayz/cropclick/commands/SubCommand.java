package com.github.bakuplayz.cropclick.commands;

import org.bukkit.entity.Player;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public abstract class SubCommand {

    public abstract String getName(); //should be sat in the constructor or check by name is better

    public abstract String getDescription(); //should be sat in the constructor or check by name is better

    public abstract String getUsage(); //should be sat in the constructor or check by name is better

    public abstract String getPermission(); //should be sat in the constructor or check by name is better

    public abstract boolean hasPermission(Player player);
    // Some solution to make this removable from the other files, however this should also be @Overrwritten in some cases.

    public abstract void perform(Player player, String[] args);
}