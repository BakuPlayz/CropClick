package com.github.bakuplayz.cropclick.commands;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.subcommands.HelpCommand;
import com.github.bakuplayz.cropclick.commands.subcommands.ReloadCommand;
import com.github.bakuplayz.cropclick.commands.subcommands.ResetCommand;
import com.github.bakuplayz.cropclick.commands.subcommands.SettingsCommand;
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
 */
public final class CommandManager implements TabExecutor {

    private final CropClick plugin;

    private final @Getter List<SubCommand> commands = new ArrayList<>();

    public CommandManager(@NotNull CropClick plugin) {
        this.plugin = plugin;

        registerCommands();
    }

    private void registerCommands() {
        commands.add(new HelpCommand(plugin));
        commands.add(new ReloadCommand(plugin));
        commands.add(new ResetCommand(plugin));
        commands.add(new SettingsCommand(plugin));
    }

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
            new MainMenu(player, plugin).open();
            return true;
        }

        for (SubCommand command : commands) {
            if (!args[0].equalsIgnoreCase(command.getName())) continue;

            if (!command.hasPermission(player)) {
                LanguageAPI.Command.PLAYER_LACK_PERMISSION.send(plugin, player, command.getPermission());
                return true;
            }

            command.perform(player, args);
            return true;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender,
                                      @NotNull Command cmd,
                                      @NotNull String alias,
                                      String @NotNull [] args) {
        return args.length != 1
               ? new ArrayList<>()
               : commands.stream()
                       .map(SubCommand::getName)
                       .filter(command -> command.startsWith(args[0]))
                       .sorted().collect(Collectors.toList());
    }

}