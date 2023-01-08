package com.github.bakuplayz.cropclick.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.subcommands.*;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.permissions.command.CommandPermission;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * A manager handling commands.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class CommandManager implements TabExecutor {

    private final CropClick plugin;

    /**
     * A variable containing all the registered commands.
     */
    private final @Getter List<Subcommand> commands;


    public CommandManager(@NotNull CropClick plugin) {
        this.commands = new ArrayList<>();
        this.plugin = plugin;

        registerCommands();
    }


    /**
     * Register all the {@link #commands}.
     */
    private void registerCommands() {
        commands.add(new AutofarmsCommand(plugin));
        commands.add(new HelpCommand(plugin));
        commands.add(new ReloadCommand(plugin));
        commands.add(new ResetCommand(plugin));
        commands.add(new SettingsCommand(plugin));
    }


    /**
     * Checks for {@link CommandSender senders} performing commands.
     *
     * @param sender the sender sending the command.
     * @param cmd    the command that was sent.
     * @param label  the label of the command.
     * @param args   the arguments passed along the command.
     *
     * @return true if the command executed successfully, otherwise false.
     */
    @Override
    public boolean onCommand(@NotNull CommandSender sender,
                             @NotNull Command cmd,
                             @NotNull String label,
                             String[] args) {
        if (!(sender instanceof Player)) {
            LanguageAPI.Command.PLAYER_ONLY_COMMAND.send(plugin, sender);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            if (!PermissionUtils.canPlayerExecuteGeneralCommand(player)) {
                LanguageAPI.Command.PLAYER_LACK_PERMISSION.send(
                        plugin, player, CommandPermission.GENERAL_COMMAND.getName()
                );
                return true;
            }

            new MainMenu(plugin, player).openMenu();
            return true;
        }

        for (Subcommand command : commands) {
            if (!args[0].equalsIgnoreCase(command.getName())) {
                continue;
            }

            if (!command.hasPermission(player)) {
                LanguageAPI.Command.PLAYER_LACK_PERMISSION.send(
                        plugin, player, command.getPermission()
                );
                return true;
            }

            command.perform(player, args);
            return true;
        }

        return true;
    }


    /**
     * Checks for {@link CommandSender senders} tab completing commands.
     *
     * @param sender the sender tab completing the command.
     * @param cmd    the command to tab complete.
     * @param alias  the alternative name for the command.
     * @param args   the arguments passed along the command.
     *
     * @return the tab completed suggestions.
     */
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender,
                                      @NotNull Command cmd,
                                      @NotNull String alias,
                                      String @NotNull [] args) {
        if (args.length != 1) {
            return Collections.emptyList();
        }

        return commands.stream()
                       .map(Subcommand::getName)
                       .filter(command -> command.startsWith(args[0]))
                       .sorted().collect(Collectors.toList());
    }


    /**
     * Gets the amount of {@link #commands}.
     *
     * @return the amount of commands.
     */
    public int getAmountOfCommands() {
        return getCommands().size() + 1;
    }

}