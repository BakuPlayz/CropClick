package com.github.bakuplayz.cropclick.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.subcommands.HelpCommand;
import com.github.bakuplayz.cropclick.commands.subcommands.ReloadCommand;
import com.github.bakuplayz.cropclick.commands.subcommands.ResetCommand;
import com.github.bakuplayz.cropclick.commands.subcommands.SettingsCommand;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CommandManager implements TabExecutor {

    private final CropClick plugin;

    private final @Getter List<SubCommand> commands = new ArrayList<>();


    public CommandManager(@NotNull CropClick plugin) {
        this.plugin = plugin;

        registerCommands();
    }


    /**
     * Registers all the commands, by adding them to the commands list.
     */
    private void registerCommands() {
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
            new MainMenu(plugin, player).open();
            return true;
        }

        for (SubCommand command : commands) {
            if (!args[0].equalsIgnoreCase(command.getName())) {
                continue;
            }

            if (!command.hasPermission(player)) {
                LanguageAPI.Command.PLAYER_LACK_PERMISSION.send(plugin, player, command.getPermission());
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
        if (args.length != 1) return new ArrayList<>();
        return commands.stream()
                .map(SubCommand::getName)
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