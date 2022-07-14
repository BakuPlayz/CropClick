package com.github.bakuplayz.cropclick.api;

import com.github.bakuplayz.cropclick.CropClick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class LanguageAPI {

    public enum Console {
        FILE_RELOAD("Reloading &e%s&7."),
        FILE_SETUP_LOAD("Loading &e%s&7."),
        FILE_SETUP_FAILED("Could not setup &e%s&7."),

        DATA_STORAGE_LOADING_SETUP("Loading &e%s&7."),
        DATA_STORAGE_FAILED_SAVE_OTHER("Could not save &e%s&7, due to unknown reasons."),
        DATA_STORAGE_FAILED_SAVE_REMOVED("Could not save &e%s&7, due to it being previously removed."),
        DATA_STORAGE_LOADED_DATA("Loaded &e%s's &7data."),
        DATA_STORAGE_FAILED_LOAD("Could not load &e%s&7."),
        DATA_STORAGE_FAILED_SETUP("Could not setup &e%s&7."),

        FAILED_TO_REGISTER_COMMANDS("Commands failed to register, please reload the server."),

        UPDATE_FETCH_FAILED("Update fetch failed! Make sure your online, to keep the plugin up to date."),
        NOT_SUPPORTED_VERSION("This CropClick.jar only supports 1.8 to 1.13.2. In order to run the plugin, please change to the correct jar for your server version.");

        private final String message;

        Console(String message) {
            this.message = message;
        }

        public void send() {
            Bukkit.getConsoleSender().sendMessage(get());
        }

        public void send(String value) {
            Bukkit.getConsoleSender().sendMessage(String.format(get(), value));
        }

        @Contract(" -> new")
        private @NotNull String get() {
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

        private final String placeholder;
        private final String category;
        private final String key;

        Command(String category,
                String key) {
            this.category = category;
            this.placeholder = "";
            this.key = key;
        }

        Command(String category,
                String key,
                String placeholder) {
            this.placeholder = placeholder;
            this.category = category;
            this.key = key;
        }

        public @NotNull String get(@NotNull CropClick plugin) {
            return plugin.getLanguageConfig().getMessage("command", category, key);
        }

        public @NotNull String get(@NotNull CropClick plugin,
                                   @NotNull String value) {
            return get(plugin).replace(placeholder, value);
        }

        public void send(@NotNull CropClick plugin,
                         @NotNull CommandSender sender) {
            sender.sendMessage(get(plugin));
        }

        public void send(@NotNull CropClick plugin,
                         @NotNull CommandSender sender,
                         @NotNull String value) {
            sender.sendMessage(get(plugin, value));
        }

    }

    public enum Menu {
        NEXT_PAGE_ITEM_NAME("general", "nextPageItemName"),
        PREVIOUS_PAGE_ITEM_NAME("general", "previousPageItemName"),
        CURRENT_PAGE_ITEM_NAME("general", "currentPageItemName", "%page%"),

        CROP_TITLE("title", "crop"),
        CROPS_TITLE("title", "crops"),
        DISPENSER_TITLE("title", "dispenser"),
        CONTAINER_TITLE("title", "container"),
        AUTOFARMS_TITLE("title", "autofarms"),

        MAIN_TITLE("title", "main"),
        HELP_TITLE("title", "help"),
        LINK_TITLE("title", "link"),
        UPDATE_TITLE("title", "update"),
        SETTINGS_TITLE("title", "settings"),

        TOWNY_TITLE("title", "towny"),
        MCMMO_TITLE("title", "mcMMO"),
        RESIDENCE_TITLE("title", "residence"),
        JOBS_REBORN_TITLE("title", "jobsReborn"),
        WORLD_GUARD_TITLE("title", "worldGuard"),

        SOUND_TITLE("title", "sound"),
        EFFECT_TITLE("title", "effect"),
        INTERACT_CROP_TITLE("title", "interactCrop"),
        BLACKLIST_WORLDS_TITLE("title", "blacklistWorlds"),

        HELP_ITEM_NAME("help", "itemName", "%name%"),
        HELP_ITEM_USAGE("help", "itemUsage", "%usage%"),
        HELP_ITEM_DESCRIPTION("help", "itemDescription", "%description%"),
        HELP_ITEM_PERMISSION("help", "itemPermission", "%permission%"),

        CONTAINER_INFO_NAME("container", "infoItemName", "%name%"),
        CONTAINER_CROP_NAME("container", "cropItemName", "%name%"),
        CONTAINER_DISPENSER_NAME("container", "dispenserItemName", "%name%"),
        CONTAINER_CONTAINER_NAME("container", "containerItemName", "%name%"),
        CONTAINER_LINKED_NAME("container", "linkedItemName", "%name%"),
        CONTAINER_UNLINKED_NAME("container", "unlinkedItemName", "%name%")
        ;

        private final String placeholder;
        private final String category;
        private final String key;

        Menu(String category,
             String key) {
            this.category = category;
            this.placeholder = "";
            this.key = key;
        }

        Menu(String category,
             String key,
             String placeholder) {
            this.placeholder = placeholder;
            this.category = category;
            this.key = key;
        }

        public @NotNull String get(@NotNull CropClick plugin) {
            return plugin.getLanguageConfig().getMessage("menu", category, key);
        }

        public @NotNull <T> String get(@NotNull CropClick plugin, @NotNull T value) {
            return get(plugin).replace(placeholder, value.toString());
        }

        public @NotNull String getTitle(@NotNull CropClick plugin) {
            return "CraftingGUI: " + get(plugin);
        }

    }

}