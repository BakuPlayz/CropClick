package com.github.bakuplayz.cropclick.api;

import com.github.bakuplayz.cropclick.CropClick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public final class LanguageAPI {

    public enum Console {
        FILE_RELOAD("Reloading &e%s&7."),
        FILE_SETUP_LOAD("Loading &e%s&7."),
        FILE_SETUP_FAILED("Could not setup &e%s&7."),

        DATA_STORAGE_LOADING_SETUP("Loading &e%s&7."),
        DATA_STORAGE_FAILED_SAVE("Could not save &e%s&7."),
        DATA_STORAGE_FETCHED_DATA("Fetched &e%s's &7data."),
        DATA_STORAGE_FAILED_FETCH("Could not fetch &e%s&7."),
        DATA_STORAGE_FAILED_SETUP("Could not setup &e%s&7."),

        FAILED_TO_REGISTER_COMMANDS("Commands failed to register, please reload the server."),

        UPDATE_FETCH_FAILED("Update fetch failed! Make sure your online, to keep the plugin up to date."),
        NOT_SUPPORTED_VERSION("This CropClick.jar only supports 1.8 to 1.13.2. In order to run the plugin, please change to the correct jar for your server version.");

        private final String message;

        Console(final String message) {
            this.message = message;
        }

        public void sendMessage() {
            Bukkit.getConsoleSender().sendMessage(getMessage());
        }

        public void sendMessage(final String replacement) {
            Bukkit.getConsoleSender().sendMessage(String.format(getMessage(), replacement));
        }

        @Contract(" -> new")
        private @NotNull String getMessage() {
            return ChatColor.translateAlternateColorCodes('&', "[&aCropClick&f] &7" + message);
        }
    }

    public enum Command {
        HELP_DESCRIPTION("help", "description"),
        RESET_DESCRIPTION("reset", "description"),
        RELOAD_DESCRIPTION("reload", "description"),
        DEFAULT_DESCRIPTION("default", "description"),
        SETTINGS_DESCRIPTION("settings", "description"),

        RESET_DELETE("reset", "delete"),
        RESET_FAILED("reset", "failed"),
        RESET_SUCCESS("reset", "success"),

        RELOAD_FAILED("reload", "failed"),
        RELOAD_SUCCESS("reload", "success"),

        PLAYER_ONLY_COMMAND("general", "playerOnlyCommand"),
        PLAYER_LACK_PERMISSION("general", "playerLackPermission", "%permission%");

        private final String key;
        private final String subCategory;

        private String oldValue = "";

        Command(final String subCategory, final String key) {
            this.key = key;
            this.subCategory = subCategory;
        }

        Command(final String subCategory, final String key, final String oldValue) {
            this(subCategory, key);
            this.oldValue = oldValue;
        }

        public @NotNull String getMessage(final @NotNull CropClick plugin) {
            return plugin.getLanguageConfig().getMessage("command", subCategory, key);
        }

        public @NotNull String getMessage(final @NotNull CropClick plugin, final @NotNull String newValue) {
            return getMessage(plugin).replace(oldValue, newValue);
        }

        public void sendMessage(final @NotNull CropClick plugin, final @NotNull CommandSender sender) {
            sender.sendMessage(getMessage(plugin));
        }

        public void sendMessage(final @NotNull CropClick plugin, final @NotNull CommandSender sender, final @NotNull String newValue) {
            sender.sendMessage(getMessage(plugin, newValue));
        }
    }

    public enum Menu {
        HELP_ITEM_NAME("help", "itemName", "%name%"),
        HELP_ITEM_USAGE("help", "itemUsage", "%usage%"),
        HELP_ITEM_DESCRIPTION("help", "itemDescription", "%description%"),
        HELP_ITEM_PERMISSION("help", "itemPermission", "%permission%");

        private final String key;
        private final String subCategory;

        private String oldValue = "";

        Menu(final String subCategory, final String key) {
            this.key = key;
            this.subCategory = subCategory;
        }

        Menu(final String subCategory, final String key, final String oldValue) {
            this(subCategory, key);
            this.oldValue = oldValue;
        }

        public @NotNull String getMessage(final @NotNull CropClick plugin) {
            return plugin.getLanguageConfig().getMessage("command", subCategory, key);
        }

        public @NotNull String getMessage(final @NotNull CropClick plugin, final @NotNull String newValue) {
            return getMessage(plugin).replace(oldValue, newValue);
        }
    }
}
