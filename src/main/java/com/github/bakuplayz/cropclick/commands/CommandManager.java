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
     * Registers all the commands, by adding them to the commands list.
     */
    private void registerCommands() {
        commands.add(new AutofarmsCommand(plugin));
        commands.add(new HelpCommand(plugin));
        commands.add(new ReloadCommand(plugin));
        commands.add(new ResetCommand(plugin));
        commands.add(new SettingsCommand(plugin));
    }


    /**
     * If the sender is a player, and the command is valid, then perform the command.
     *
     * @param sender The player who sent the command.
     * @param cmd    The command that was executed.
     * @param label  The command label.
     * @param args   The arguments that the player typed in.
     *
     * @return A boolean.
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
     * If the command has only one argument, return a list of all commands that start with the first argument.
     *
     * @param sender The CommandSender who executed the command.
     * @param cmd    The command that was executed.
     * @param alias  The alias that was used to call the command.
     * @param args   The arguments that the player has typed in.
     *
     * @return A list of commands that start with the first argument.
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
     * Returns the number of *actual* commands in the command list.
     *
     * @return The amount of commands in the list.
     */
    public int getAmountOfCommands() {
        return getCommands().size() + 1;
    }

}