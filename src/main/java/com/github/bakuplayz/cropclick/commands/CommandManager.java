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

    private final @Getter List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(final @NotNull CropClick plugin) {
        this.plugin = plugin;

        registerSubCommands();
    }

    private void registerSubCommands() {
        subCommands.add(new HelpCommand(plugin));
        subCommands.add(new ReloadCommand(plugin));
        subCommands.add(new ResetCommand(plugin));
        subCommands.add(new SettingsCommand(plugin));
    }

    @Override
    public boolean onCommand(final @NotNull CommandSender sender,
                             final @NotNull Command command,
                             final @NotNull String label,
                             final String[] args) {
        if (!(sender instanceof Player)) {
            LanguageAPI.Command.PLAYER_ONLY_COMMAND.send(plugin, sender);
            return true;
        }

        Player player = (Player) sender;

        if (args.length == 0) {
            new MainMenu(player, plugin).open();
        }

        for (SubCommand subCommand : getSubCommands()) {
            if (args[0].equalsIgnoreCase(subCommand.getName())) {
                if (!subCommand.hasPermission(player)) {
                    LanguageAPI.Command.PLAYER_LACK_PERMISSION.send(plugin, player, subCommand.getPermission());
                    return true;
                }
                subCommand.perform(player, args);
                return true;
            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(final @NotNull CommandSender sender,
                                      final @NotNull Command command,
                                      final @NotNull String alias,
                                      final String @NotNull [] args) {
        if (args.length != 1) return new ArrayList<>();
        return getSubCommands().stream()
                .map(SubCommand::getName)
                .filter(subCommand -> subCommand.startsWith(args[0]))
                .sorted().collect(Collectors.toList());
    }
}
