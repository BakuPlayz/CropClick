package com.github.bakuplayz.cropclick.language;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class LanguageAPI {

    public enum Console {
        FILE_RELOAD("Reloading &e%s&7."),
        FILE_SETUP_LOAD("Loading &e%s&7."),
        FILE_SAVE_FAILED("Could not save &e%s&7."),

        FILE_SETUP_FAILED("Could not setup &e%s&7."),

        DATA_STORAGE_LOADING_SETUP("Loading &e%s&7."),

        DATA_STORAGE_FAILED_SAVE_OTHER("Could not save &e%s&7, due to unknown reasons."),

        DATA_STORAGE_FAILED_SAVE_REMOVED("Could not save &e%s&7, due to it being previously removed."),

        DATA_STORAGE_LOADED_DATA("Loaded &e%s's &7data."),

        DATA_STORAGE_FAILED_LOAD("Could not load &e%s&7."),

        DATA_STORAGE_FAILED_SETUP("Could not setup &e%s&7."),

        FAILED_TO_REGISTER_COMMANDS("Commands failed to register, please reload the server."),

        UPDATE_FETCH_FAILED("Update fetch failed! Make sure your online, to keep the plugin up to date."),

        NOT_SUPPORTED_VERSION(
                "This CropClick.jar only supports 1.8 to 1.13.2. In order to run the plugin, please change to the correct jar for your server version.");

        private final String message;


        Console(@NotNull String message) {
            this.message = message;
        }


        /**
         * It sends the message to the console.
         */
        public void send() {
            Bukkit.getConsoleSender().sendMessage(get());
        }


        /**
         * It sends a message to the console.
         *
         * @param value The value to replace the placeholder with.
         */
        public void send(@NotNull String value) {
            Bukkit.getConsoleSender().sendMessage(fastReplace(get(), value));
        }


        /**
         * It returns a string with the prefix and the message.
         *
         * @return The message is being returned.
         */
        @NotNull
        @Contract(" -> new")
        private String get() {
            return ChatColor.translateAlternateColorCodes('&', "[&aCropClick&f] &7" + message);
        }


        /**
         * Replaces the first occurrence of %s in the given message with the given value.
         *
         * @param message The message to be formatted.
         * @param value   The value to be replaced.
         *
         * @return A String.
         */
        @NotNull
        private String fastReplace(@NotNull String message, @NotNull String value) {
            return StringUtils.replace(message, "%s", value, 1);
        }

    }

    public enum Command {
        PLAYER_ONLY_COMMAND(Category.GENERAL, "playerOnlyCommand"),
        PLAYER_LACK_PERMISSION(Category.GENERAL, "playerLackPermission", "%permission%"),

        HELP_DESCRIPTION(Category.HELP, "description"),
        RESET_DESCRIPTION(Category.RESET, "description"),
        RELOAD_DESCRIPTION(Category.RELOAD, "description"),
        DEFAULT_DESCRIPTION(Category.DEFAULT, "description"),
        SETTINGS_DESCRIPTION(Category.SETTINGS, "description"),

        RESET_DELETE(Category.RESET, "delete"),
        RESET_FAILED(Category.RESET, "failed"),
        RESET_SUCCESS(Category.RESET, "success"),

        RELOAD_FAILED(Category.RELOAD, "failed"),
        RELOAD_SUCCESS(Category.RELOAD, "success");

        private enum Category {
            GENERAL,
            HELP,
            RESET,
            RELOAD,
            DEFAULT,
            SETTINGS;


            /**
             * This function returns the name of the enum in lowercase.
             *
             * @return The name of the enum in lowercase.
             */
            @NotNull
            public String getName() {
                return name().toLowerCase();
            }

        }

        private final String placeholder;
        private final String category;
        private final String key;


        Command(@NotNull Category category, @NotNull String key) {
            this.category = category.getName();
            this.placeholder = "";
            this.key = key;
        }


        Command(@NotNull Category category, @NotNull String key, @NotNull String placeholder) {
            this.category = category.getName();
            this.placeholder = placeholder;
            this.key = key;
        }


        /**
         * It gets the message from the language config.
         *
         * @param plugin The plugin instance.
         *
         * @return The message from the language config.
         */
        @NotNull
        public String get(@NotNull CropClick plugin) {
            return plugin.getLanguageConfig().getMessage("command", category, key, true);
        }


        /**
         * It takes a string, and replaces all instances of `%value%` with the value passed to the function.
         *
         * @param plugin The plugin instance.
         * @param value  The value to replace the placeholder with.
         *
         * @return The value of the key in the config.yml file.
         */
        @NotNull
        public String get(@NotNull CropClick plugin, @NotNull String value) {
            return fastReplace(get(plugin), value);
        }


        /**
         * This function sends the message to the sender.
         *
         * @param plugin The plugin instance.
         * @param sender The CommandSender to send the message to.
         */
        public void send(@NotNull CropClick plugin, @NotNull CommandSender sender) {
            sender.sendMessage(get(plugin));
        }


        /**
         * Send a message to a CommandSender.
         *
         * @param plugin The plugin instance.
         * @param sender The CommandSender that will receive the message.
         * @param value  The value to be translated.
         */
        public void send(@NotNull CropClick plugin, @NotNull CommandSender sender, @NotNull String value) {
            sender.sendMessage(get(plugin, value));
        }


        /**
         * Replace the first occurrence of a placeholder with a value.
         *
         * @param message The message to replace the placeholder in.
         * @param value   The value to be replaced.
         *
         * @return A string with the placeholder replaced with the value.
         */
        @NotNull
        private String fastReplace(@NotNull String message, @NotNull String value) {
            return StringUtils.replaceOnce(message, placeholder, value);
        }

    }

    public enum Menu {
        BACK_ITEM_NAME(Category.GENERAL, "backItemName"),
        NEXT_PAGE_ITEM_NAME(Category.GENERAL, "nextPageItemName"),
        PREVIOUS_PAGE_ITEM_NAME(Category.GENERAL, "previousPageItemName"),
        CURRENT_PAGE_ITEM_NAME(Category.GENERAL, "currentPageItemName", "%page%"),
        ENABLED_STATUS(Category.GENERAL, "enabledStatus"),
        DISABLED_STATUS(Category.GENERAL, "disabledStatus"),

        CROP_TITLE(Category.TITLE, "crop"),
        CROPS_TITLE(Category.TITLE, "crops", "%type%"),
        AUTOFARMS_TITLE(Category.TITLE, "autofarms"),

        CROP_INTERACT_TITLE(Category.TITLE, "cropInteract"),
        CONTAINER_INTERACT_TITLE(Category.TITLE, "containerInteract"),
        DISPENSER_INTERACT_TITLE(Category.TITLE, "dispenserInteract"),

        MAIN_TITLE(Category.TITLE, "main"),
        HELP_TITLE(Category.TITLE, "help"),
        UPDATES_TITLE(Category.TITLE, "updates"),
        SETTINGS_TITLE(Category.TITLE, "settings"),

        TOWNY_TITLE(Category.TITLE, "towny"),
        MCMMO_TITLE(Category.TITLE, "mcMMO"),
        MCMMO_CROP_TITLE(Category.TITLE, "mcMMOCrop"),
        ADDONS_TITLE(Category.TITLE, "addons"),
        RESIDENCE_TITLE(Category.TITLE, "residence"),
        JOBS_CROP_TITLE(Category.TITLE, "jobsRebornCrop"),
        JOBS_REBORN_TITLE(Category.TITLE, "jobsReborn"),
        WORLD_GUARD_TITLE(Category.TITLE, "worldGuard"),
        OFFLINE_GROWTH_TITLE(Category.TITLE, "offlineGrowth"),

        SOUNDS_TITLE(Category.TITLE, "sounds"),
        NAME_TITLE(Category.TITLE, "name"),
        TOGGLE_TITLE(Category.TITLE, "toggle"),
        WORLD_TITLE(Category.TITLE, "world"),
        WORLDS_TITLE(Category.TITLE, "worlds"),
        PARTICLES_TITLE(Category.TITLE, "particles"),

        DISPENSER_PREVIEW_TITLE(Category.TITLE, "dispenserPreview", "%id%"),
        CONTAINER_PREVIEW_TITLE(Category.TITLE, "containerPreview", "%id%"),

        MAIN_UPDATES_ITEM_NAME(Category.MAIN, SubCategory.UPDATES, "itemName"),
        MAIN_UPDATES_ITEM_STATUS(Category.MAIN, SubCategory.UPDATES, "itemStatus", "%status%"),
        MAIN_UPDATES_ITEM_TIPS(Category.MAIN, SubCategory.UPDATES, "itemTips"),
        MAIN_HELP_ITEM_NAME(Category.MAIN, SubCategory.HELP, "itemName"),
        MAIN_HELP_ITEM_STATUS(Category.MAIN, SubCategory.HELP, "itemStatus", "%status%"),
        MAIN_HELP_ITEM_TIPS(Category.MAIN, SubCategory.HELP, "itemTips"),
        MAIN_ADDONS_ITEM_NAME(Category.MAIN, SubCategory.ADDONS, "itemName"),
        MAIN_ADDONS_ITEM_STATUS(Category.MAIN, SubCategory.ADDONS, "itemStatus", "%status%"),
        MAIN_ADDONS_ITEM_TIPS(Category.MAIN, SubCategory.ADDONS, "itemTips"),
        MAIN_SETTINGS_ITEM_NAME(Category.MAIN, SubCategory.SETTINGS, "itemName"),
        MAIN_SETTINGS_ITEM_STATUS(Category.MAIN, SubCategory.SETTINGS, "itemStatus", "%status%"),
        MAIN_SETTINGS_ITEM_TIPS(Category.MAIN, SubCategory.SETTINGS, "itemTips"),
        MAIN_CROPS_ITEM_NAME(Category.MAIN, SubCategory.CROPS, "itemName"),
        MAIN_CROPS_ITEM_STATUS(Category.MAIN, SubCategory.CROPS, "itemStatus", "%status%"),
        MAIN_CROPS_ITEM_TIPS(Category.MAIN, SubCategory.CROPS, "itemTips"),
        MAIN_AUTOFARMS_ITEM_NAME(Category.MAIN, SubCategory.AUTOFARMS, "itemName"),
        MAIN_AUTOFARMS_ITEM_STATUS(Category.MAIN, SubCategory.AUTOFARMS, "itemStatus", "%status%"),
        MAIN_AUTOFARMS_ITEM_TIPS(Category.MAIN, SubCategory.AUTOFARMS, "itemTips"),

        SETTINGS_PARTICLES_ITEM_NAME(Category.SETTINGS, SubCategory.PARTICLES, "itemName"),
        SETTINGS_PARTICLES_ITEM_TIPS(Category.SETTINGS, SubCategory.PARTICLES, "itemTips"),
        SETTINGS_PARTICLES_ITEM_STATUS(Category.SETTINGS, SubCategory.PARTICLES, "itemStatus", "%status%"),
        SETTINGS_SOUNDS_ITEM_NAME(Category.SETTINGS, SubCategory.SOUNDS, "itemName"),
        SETTINGS_SOUNDS_ITEM_TIPS(Category.SETTINGS, SubCategory.SOUNDS, "itemTips"),
        SETTINGS_SOUNDS_ITEM_STATUS(Category.SETTINGS, SubCategory.SOUNDS, "itemStatus", "%status%"),
        SETTINGS_TOGGLE_ITEM_NAME(Category.SETTINGS, SubCategory.TOGGLE, "itemName"),
        SETTINGS_TOGGLE_ITEM_TIPS(Category.SETTINGS, SubCategory.TOGGLE, "itemTips"),
        SETTINGS_TOGGLE_ITEM_STATUS(Category.SETTINGS, SubCategory.TOGGLE, "itemStatus", "%status%"),
        SETTINGS_NAME_ITEM_NAME(Category.SETTINGS, SubCategory.NAME, "itemName"),
        SETTINGS_NAME_ITEM_TIPS(Category.SETTINGS, SubCategory.NAME, "itemTips"),
        SETTINGS_NAME_ITEM_STATUS(Category.SETTINGS, SubCategory.NAME, "itemStatus", "%status%"),
        SETTINGS_WORLDS_ITEM_NAME(Category.SETTINGS, SubCategory.WORLDS, "itemName"),
        SETTINGS_WORLDS_ITEM_TIPS(Category.SETTINGS, SubCategory.WORLDS, "itemTips"),
        SETTINGS_WORLDS_ITEM_STATUS(Category.SETTINGS, SubCategory.WORLDS, "itemStatus", "%status%"),

        PARTICLES_ITEM_NAME(Category.PARTICLES, "itemName", "%name%"),
        PARTICLES_ITEM_STATUS(Category.PARTICLES, "itemStatus", "%status%"),

        SOUNDS_ITEM_NAME(Category.SOUNDS, "itemName", "%name%"),
        SOUNDS_ITEM_STATUS(Category.SOUNDS, "itemStatus", "%status%"),

        WORLD_WORLD_ITEM_NAME(Category.WORLD, SubCategory.WORLD, "itemName", "%name%"),
        WORLD_WORLD_ITEM_STATUS(Category.WORLD, SubCategory.WORLD, "itemStatus", "%status%"),
        WORLD_PLAYERS_ITEM_NAME(Category.WORLD, SubCategory.PLAYERS, "itemName"),
        WORLD_PLAYERS_ITEM_STATUS(Category.WORLD, SubCategory.PLAYERS, "itemStatus", "%status%"),
        WORLD_AUTOFARMS_ITEM_NAME(Category.WORLD, SubCategory.AUTOFARMS, "itemName"),
        WORLD_AUTOFARMS_ITEM_STATUS(Category.WORLD, SubCategory.AUTOFARMS, "itemStatus", "%status%"),

        WORLDS_ITEM_NAME(Category.WORLDS, "itemName", "%name%"),
        WORLDS_ITEM_STATUS(Category.WORLDS, "itemStatus", "%status%"),

        TOGGLE_ITEM_NAME(Category.TOGGLE, "itemName", "%name%"),
        TOGGLE_ITEM_STATUS(Category.TOGGLE, "itemStatus", "%status%"),

        NAME_CROP_ITEM_NAME(Category.NAME, SubCategory.CROP, "itemName", "%name%", "%status%"),
        NAME_CROP_DROP_NAME(Category.NAME, SubCategory.CROP, "dropName", "%name%"),
        NAME_SEED_ITEM_NAME(Category.NAME, SubCategory.SEED, "itemName", "%name%", "%status%"),
        NAME_SEED_DROP_NAME(Category.NAME, SubCategory.SEED, "dropName", "%name%"),
        NAME_COLOR_ITEM_NAME(Category.NAME, SubCategory.COLOR_CODE, "itemName"),
        NAME_COLOR_ITEM_CODE(Category.NAME, SubCategory.COLOR_CODE, "itemColor", "%code%", "%color%", "%name%"),
        NAME_RESPONSE_UNCHANGED(Category.NAME, SubCategory.RESPONSE, "unchanged"),
        NAME_RESPONSE_CHANGED(Category.NAME, SubCategory.RESPONSE, "changed", "%name%"),

        CROP_ITEM_NAME(Category.CROP, SubCategory.CROP, "itemName", "%name%", "%status%"),
        CROP_ITEM_DROP_VALUE(Category.CROP, SubCategory.CROP, "itemDropValue", "%value%"),
        CROP_SEED_ITEM_NAME(Category.CROP, SubCategory.SEED, "itemName", "%name%", "%status%"),
        CROP_SEED_ITEM_DROP_VALUE(Category.CROP, SubCategory.SEED, "itemDropValue", "%value%"),
        CROP_LINKABLE_ITEM_NAME(Category.CROP, SubCategory.LINKABLE, "itemName"),
        CROP_LINKABLE_ITEM_STATUS(Category.CROP, SubCategory.LINKABLE, "itemStatus", "%status%"),
        CROP_AT_LEAST_ITEM_NAME(Category.CROP, SubCategory.AT_LEAST, "itemName"),
        CROP_AT_LEAST_ITEM_STATUS(Category.CROP, SubCategory.AT_LEAST, "itemStatus", "%status%"),
        CROP_REPLANT_ITEM_NAME(Category.CROP, SubCategory.REPLANT, "itemName"),
        CROP_REPLANT_ITEM_STATUS(Category.CROP, SubCategory.REPLANT, "itemStatus", "%status%"),
        CROP_ADD_ITEM_NAME(Category.CROP, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        CROP_ADD_ITEM_AFTER(Category.CROP, SubCategory.ADD, "itemAfter", "%value%"),
        CROP_REMOVE_ITEM_NAME(Category.CROP, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),
        CROP_REMOVE_ITEM_AFTER(Category.CROP, SubCategory.REMOVE, "itemAfter", "%value%"),
        CROP_STATUS_ENABLED(Category.CROP, SubCategory.STATUS, "enabled"),
        CROP_STATUS_DISABLED(Category.CROP, SubCategory.STATUS, "disabled"),

        CROPS_ITEM_NAME(Category.CROPS, SubCategory.ITEM, "name", "%name%", "%status%"),
        CROPS_ITEM_DROP_NAME(Category.CROPS, SubCategory.ITEM, "dropName", "%name%"),
        CROPS_ITEM_DROP_VALUE(Category.CROPS, SubCategory.ITEM, "dropValue", "%value%"),
        CROPS_ITEM_PARTICLES(Category.CROPS, SubCategory.ITEM, "particles", "%status%"),
        CROPS_ITEM_SOUNDS(Category.CROPS, SubCategory.ITEM, "sounds", "%status%"),
        CROPS_ITEM_MMO_EXPERIENCE(Category.CROPS, SubCategory.ITEM, "MMOExperience", "%status%"),
        CROPS_ITEM_JOBS_EXPERIENCE(Category.CROPS, SubCategory.ITEM, "jobsExperience", "%status%"),
        CROPS_ITEM_JOBS_POINTS(Category.CROPS, SubCategory.ITEM, "jobsPoints", "%status%"),
        CROPS_ITEM_JOBS_MONEY(Category.CROPS, SubCategory.ITEM, "jobsMoney", "%status%"),
        CROPS_STATUS_ENABLED(Category.CROPS, SubCategory.STATUS, "enabled"),
        CROPS_STATUS_DISABLED(Category.CROPS, SubCategory.STATUS, "disabled"),

        HELP_ITEM_NAME(Category.HELP, "itemName", "%name%"),
        HELP_ITEM_USAGE(Category.HELP, "itemUsage", "%usage%"),
        HELP_ITEM_PERMISSION(Category.HELP, "itemPermission", "%permission%"),
        HELP_ITEM_DESCRIPTION(Category.HELP, "itemDescription", "%description%"),

        AUTOFARM_FORMAT_X(Category.AUTOFARM, SubCategory.FORMAT, "x", "%x%"),
        AUTOFARM_FORMAT_Y(Category.AUTOFARM, SubCategory.FORMAT, "y", "%y%"),
        AUTOFARM_FORMAT_Z(Category.AUTOFARM, SubCategory.FORMAT, "z", "%z%"),
        AUTOFARM_FORMAT_STATE(Category.AUTOFARM, SubCategory.FORMAT, "state", "%state%"),

        AUTOFARM_STATE_UNLINKED(Category.AUTOFARM, SubCategory.STATE, "unlinked"),
        AUTOFARM_STATE_SELECTED(Category.AUTOFARM, SubCategory.STATE, "selected"),

        AUTOFARM_LINK_SUCCESS(Category.AUTOFARM, SubCategory.ACTIONS, "linkSuccess"),
        AUTOFARM_LINK_FAILURE(Category.AUTOFARM, SubCategory.ACTIONS, "linkFailure"),

        AUTOFARM_GLASS_ITEM_NAME_LINKED(Category.AUTOFARM, SubCategory.GLASS, "itemNameLinked"),
        AUTOFARM_GLASS_ITEM_NAME_UNLINKED(Category.AUTOFARM, SubCategory.GLASS, "itemNameUnlinked"),
        AUTOFARM_GLASS_ITEM_NAME_SELECTED(Category.AUTOFARM, SubCategory.GLASS, "itemNameSelected"),

        AUTOFARM_CROP_NAME(Category.AUTOFARM, SubCategory.CROP, "itemName"),
        AUTOFARM_DISPENSER_NAME(Category.AUTOFARM, SubCategory.DISPENSER, "itemName"),
        AUTOFARM_CONTAINER_NAME(Category.AUTOFARM, SubCategory.CONTAINER, "itemName"),

        AUTOFARMS_ITEM_NAME(Category.AUTOFARMS, "itemName", "%name%"),
        AUTOFARMS_ITEM_OWNER(Category.AUTOFARMS, "itemOwner", "%owner%"),
        AUTOFARMS_ITEM_ENABLED(Category.AUTOFARMS, "itemEnabled", "%status%"),

        ADDON_WORLDS_ITEM_NAME(Category.ADDON, SubCategory.WORLDS, "itemName"),
        ADDON_WORLDS_ITEM_TIPS(Category.ADDON, SubCategory.WORLDS, "itemTips"),
        ADDON_WORLDS_ITEM_STATUS(Category.ADDON, SubCategory.WORLDS, "itemStatus", "%status%"),
        ADDON_CROP_SETTINGS_ITEM_NAME(Category.ADDON, SubCategory.CROP_SETTINGS, "itemName"),
        ADDON_CROP_SETTINGS_ITEM_TIPS(Category.ADDON, SubCategory.CROP_SETTINGS, "itemTips"),

        ADDON_JOBS_ITEM_NAME(Category.ADDON, SubCategory.JOBS_REBORN, "itemName"),
        ADDON_JOBS_ITEM_TIPS(Category.ADDON, SubCategory.JOBS_REBORN, "itemTips"),
        ADDON_JOBS_ITEM_STATUS(Category.ADDON, SubCategory.JOBS_REBORN, "itemStatus", "%status%"),
        ADDON_MCMMO_ITEM_NAME(Category.ADDON, SubCategory.MCMMO, "itemName"),
        ADDON_MCMMO_ITEM_TIPS(Category.ADDON, SubCategory.MCMMO, "itemTips"),
        ADDON_MCMMO_ITEM_STATUS(Category.ADDON, SubCategory.MCMMO, "itemStatus", "%status%"),
        ADDON_GROWTH_ITEM_NAME(Category.ADDON, SubCategory.OFFLINE_GROWTH, "itemName"),
        ADDON_GROWTH_ITEM_TIPS(Category.ADDON, SubCategory.OFFLINE_GROWTH, "itemTips"),
        ADDON_GROWTH_ITEM_STATUS(Category.ADDON, SubCategory.OFFLINE_GROWTH, "itemStatus", "%status%"),
        ADDON_RESIDENCE_ITEM_NAME(Category.ADDON, SubCategory.RESIDENCE, "itemName"),
        ADDON_RESIDENCE_ITEM_TIPS(Category.ADDON, SubCategory.RESIDENCE, "itemTips"),
        ADDON_RESIDENCE_ITEM_STATUS(Category.ADDON, SubCategory.RESIDENCE, "itemStatus", "%status%"),
        ADDON_TOWNY_ITEM_NAME(Category.ADDON, SubCategory.TOWNY, "itemName"),
        ADDON_TOWNY_ITEM_TIPS(Category.ADDON, SubCategory.TOWNY, "itemTips"),
        ADDON_TOWNY_ITEM_STATUS(Category.ADDON, SubCategory.TOWNY, "itemStatus", "%status%"),
        ADDON_GUARD_ITEM_NAME(Category.ADDON, SubCategory.WORLD_GUARD, "itemName"),
        ADDON_GUARD_ITEM_TIPS(Category.ADDON, SubCategory.WORLD_GUARD, "itemTips"),
        ADDON_GUARD_ITEM_STATUS(Category.ADDON, SubCategory.WORLD_GUARD, "itemStatus", "%status%"),

        ADDONS_JOBS_ITEM_NAME(Category.ADDONS, SubCategory.JOBS_REBORN, "itemName"),
        ADDONS_JOBS_ITEM_TIPS(Category.ADDONS, SubCategory.JOBS_REBORN, "itemTips"),
        ADDONS_JOBS_ITEM_STATUS(Category.ADDONS, SubCategory.JOBS_REBORN, "itemStatus", "%status%"),
        ADDONS_MCMMO_ITEM_NAME(Category.ADDONS, SubCategory.MCMMO, "itemName"),
        ADDONS_MCMMO_ITEM_TIPS(Category.ADDONS, SubCategory.MCMMO, "itemTips"),
        ADDONS_MCMMO_ITEM_STATUS(Category.ADDONS, SubCategory.MCMMO, "itemStatus", "%status%"),
        ADDONS_GROWTH_ITEM_NAME(Category.ADDONS, SubCategory.OFFLINE_GROWTH, "itemName"),
        ADDONS_GROWTH_ITEM_TIPS(Category.ADDONS, SubCategory.OFFLINE_GROWTH, "itemTips"),
        ADDONS_GROWTH_ITEM_STATUS(Category.ADDONS, SubCategory.OFFLINE_GROWTH, "itemStatus", "%status%"),
        ADDONS_RESIDENCE_ITEM_NAME(Category.ADDONS, SubCategory.RESIDENCE, "itemName"),
        ADDONS_RESIDENCE_ITEM_TIPS(Category.ADDONS, SubCategory.RESIDENCE, "itemTips"),
        ADDONS_RESIDENCE_ITEM_STATUS(Category.ADDONS, SubCategory.RESIDENCE, "itemStatus", "%status%"),
        ADDONS_TOWNY_ITEM_NAME(Category.ADDONS, SubCategory.TOWNY, "itemName"),
        ADDONS_TOWNY_ITEM_TIPS(Category.ADDONS, SubCategory.TOWNY, "itemTips"),
        ADDONS_TOWNY_ITEM_STATUS(Category.ADDONS, SubCategory.TOWNY, "itemStatus", "%status%"),
        ADDONS_GUARD_ITEM_NAME(Category.ADDONS, SubCategory.WORLD_GUARD, "itemName"),
        ADDONS_GUARD_ITEM_TIPS(Category.ADDONS, SubCategory.WORLD_GUARD, "itemTips"),
        ADDONS_GUARD_ITEM_STATUS(Category.ADDONS, SubCategory.WORLD_GUARD, "itemStatus", "%status%"),

        JOBS_CROP_POINTS_ITEM_NAME(Category.JOBS_CROP, SubCategory.POINTS, "itemName"),
        JOBS_CROP_POINTS_ITEM_VALUE(Category.JOBS_CROP, SubCategory.POINTS, "itemValue", "%value%"),
        JOBS_CROP_POINTS_ADD_ITEM_NAME(Category.JOBS_CROP, SubCategory.POINTS_ADD, "itemName", "%amount%", "%type%"),
        JOBS_CROP_POINTS_ADD_ITEM_AFTER(Category.JOBS_CROP, SubCategory.POINTS_ADD, "itemAfter", "%value%"),
        JOBS_CROP_POINTS_REMOVE_ITEM_NAME(Category.JOBS_CROP, SubCategory.POINTS_REMOVE, "itemName", "%amount%", "%type%"),
        JOBS_CROP_POINTS_REMOVE_ITEM_AFTER(Category.JOBS_CROP, SubCategory.POINTS_REMOVE, "itemAfter", "%value%"),
        JOBS_CROP_MONEY_ITEM_NAME(Category.JOBS_CROP, SubCategory.MONEY, "itemName"),
        JOBS_CROP_MONEY_ITEM_VALUE(Category.JOBS_CROP, SubCategory.MONEY, "itemValue", "%value%"),
        JOBS_CROP_MONEY_ADD_ITEM_NAME(Category.JOBS_CROP, SubCategory.MONEY_ADD, "itemName", "%amount%", "%type%"),
        JOBS_CROP_MONEY_ADD_ITEM_AFTER(Category.JOBS_CROP, SubCategory.MONEY_ADD, "itemAfter", "%value%"),
        JOBS_CROP_MONEY_REMOVE_ITEM_NAME(Category.JOBS_CROP, SubCategory.MONEY_REMOVE, "itemName", "%amount%", "%type%"),
        JOBS_CROP_MONEY_REMOVE_ITEM_AFTER(Category.JOBS_CROP, SubCategory.MONEY_REMOVE, "itemAfter", "%value%"),
        JOBS_CROP_EXPERIENCE_ITEM_NAME(Category.JOBS_CROP, SubCategory.EXPERIENCE, "itemName"),
        JOBS_CROP_EXPERIENCE_ITEM_VALUE(Category.JOBS_CROP, SubCategory.EXPERIENCE, "itemValue", "%value%"),
        JOBS_CROP_EXPERIENCE_ADD_ITEM_NAME(Category.JOBS_CROP, SubCategory.EXPERIENCE_ADD, "itemName", "%amount%", "%type%"),
        JOBS_CROP_EXPERIENCE_ADD_ITEM_AFTER(Category.JOBS_CROP, SubCategory.EXPERIENCE_ADD, "itemAfter", "%value%"),
        JOBS_CROP_EXPERIENCE_REMOVE_ITEM_NAME(Category.JOBS_CROP, SubCategory.EXPERIENCE_REMOVE, "itemName", "%amount%", "%type%"),
        JOBS_CROP_EXPERIENCE_REMOVE_ITEM_AFTER(Category.JOBS_CROP, SubCategory.EXPERIENCE_REMOVE, "itemAfter", "%value%"),

        MCMMO_CROP_EXPERIENCE_REASON_ITEM_NAME(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REASON, "itemName"),
        MCMMO_CROP_EXPERIENCE_REASON_ITEM_TIPS(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REASON, "itemTips"),
        MCMMO_CROP_EXPERIENCE_REASON_ITEM_VALUE(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REASON, "itemValue", "%value%"),
        MCMMO_CROP_EXPERIENCE_ITEM_NAME(Category.MCMMO_CROP, SubCategory.EXPERIENCE, "itemName"),
        MCMMO_CROP_EXPERIENCE_ITEM_VALUE(Category.MCMMO_CROP, SubCategory.EXPERIENCE, "itemValue", "%value%"),
        MCMMO_CROP_EXPERIENCE_ADD_ITEM_NAME(Category.MCMMO_CROP, SubCategory.EXPERIENCE_ADD, "itemName", "%amount%", "%type%"),
        MCMMO_CROP_EXPERIENCE_ADD_ITEM_AFTER(Category.MCMMO_CROP, SubCategory.EXPERIENCE_ADD, "itemAfter", "%value%"),
        MCMMO_CROP_EXPERIENCE_REMOVE_ITEM_NAME(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REMOVE, "itemName", "%amount%", "%type%"),
        MCMMO_CROP_EXPERIENCE_REMOVE_ITEM_AFTER(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REMOVE, "itemAfter", "%value%"),

        MCMMO_CROP_REASON_RESPONSE_UNCHANGED(Category.MCMMO_CROP, SubCategory.REASON_RESPONSE, "unchanged"),
        MCMMO_CROP_REASON_RESPONSE_CHANGED(Category.MCMMO_CROP, SubCategory.REASON_RESPONSE, "changed", "%name%"),

        UPDATES_UPDATES_ITEM_NAME(Category.UPDATES, SubCategory.UPDATES, "itemName"),
        UPDATES_UPDATES_ITEM_TIPS(Category.UPDATES, SubCategory.UPDATES, "itemTips"),
        UPDATES_UPDATES_ITEM_STATUS(Category.UPDATES, SubCategory.UPDATES, "itemStatus", "%status%"),
        UPDATES_PLAYER_ITEM_NAME(Category.UPDATES, SubCategory.PLAYER, "itemName"),
        UPDATES_PLAYER_ITEM_TIPS(Category.UPDATES, SubCategory.PLAYER, "itemTips"),
        UPDATES_PLAYER_ITEM_STATUS(Category.UPDATES, SubCategory.PLAYER, "itemStatus", "%status%"),
        UPDATES_CONSOLE_ITEM_NAME(Category.UPDATES, SubCategory.CONSOLE, "itemName"),
        UPDATES_CONSOLE_ITEM_TIPS(Category.UPDATES, SubCategory.CONSOLE, "itemTips"),
        UPDATES_CONSOLE_ITEM_STATUS(Category.UPDATES, SubCategory.CONSOLE, "itemStatus", "%status%");

        private enum Category {
            GENERAL,
            TITLE,
            MAIN,
            CROP,
            CROPS,
            HELP,
            AUTOFARM,
            AUTOFARMS,
            UPDATES,
            ADDON,
            ADDONS,
            SETTINGS,
            WORLD,
            WORLDS,
            PARTICLES,
            SOUNDS,
            TOGGLE,
            NAME,
            JOBS_CROP("jobsCrop"),
            MCMMO_CROP("mcMMOCrop"),
            INTERACT;


            private final String altName;


            Category() {
                this.altName = null;
            }


            Category(@NotNull String altName) {
                this.altName = altName;
            }


            /**
             * If the altName is not null, return the altName, otherwise return the lowercase version of the name.
             *
             * @return The name of the enum.
             */
            public @NotNull String getName() {
                return altName != null ? altName : name().toLowerCase();
            }

        }

        private enum SubCategory {
            ITEM,
            CROP,
            CROP_SETTINGS("cropSettings"),
            SEED,
            CONTAINER,
            DISPENSER,
            STATUS,
            ACTIONS,
            FORMAT,
            STATE,
            REMOVE,
            ADD,
            POINTS,
            GLASS,
            MONEY,
            LINKABLE,
            EXPERIENCE,
            EXPERIENCE_REASON("experienceReason"),
            POINTS_ADD("pointsAdd"),
            POINTS_REMOVE("pointsRemove"),
            MONEY_ADD("moneyAdd"),
            MONEY_REMOVE("moneyRemove"),
            EXPERIENCE_ADD("experienceAdd"),
            EXPERIENCE_REMOVE("experienceRemove"),
            AUTOFARMS,
            CROPS,
            SETTINGS,
            PARTICLES,
            SOUNDS,
            TOGGLE,
            NAME,
            ADDONS,
            HELP,
            UPDATES,
            PLAYER,
            PLAYERS,
            CONSOLE,
            RESPONSE,
            REASON_RESPONSE("reasonResponse"),
            COLOR_CODE("colorCode"),
            WORLD,
            WORLDS,
            AT_LEAST("atLeastOne"),
            REPLANT,
            JOBS_REBORN("jobsReborn"),
            MCMMO("mcMMO"),
            OFFLINE_GROWTH("offlineGrowth"),
            RESIDENCE,
            TOWNY,
            WORLD_GUARD("worldGuard");

            private final String altName;


            SubCategory() {
                this.altName = null;
            }


            SubCategory(@NotNull String altName) {
                this.altName = altName;
            }


            /**
             * If the altName is not null, return the altName, otherwise return the lowercase version of the name.
             *
             * @return The name of the enum.
             */
            public @NotNull String getName() {
                return altName != null ? altName : name().toLowerCase();
            }

        }

        private final String[] placeholders;
        private final String category;
        private final String key;


        Menu(@NotNull Category category, @NotNull String key) {
            this.placeholders = new String[]{};
            this.category = category.getName();
            this.key = key;
        }


        Menu(@NotNull Category category, @NotNull SubCategory subCategory, @NotNull String key) {
            this.category = category.getName() + "." + subCategory.getName();
            this.placeholders = new String[]{};
            this.key = key;
        }


        Menu(@NotNull Category category, @NotNull String key, @NotNull String... placeholders) {
            this.placeholders = placeholders;
            this.category = category.getName();
            this.key = key;
        }


        Menu(@NotNull Category category,
             @NotNull SubCategory subCategory,
             @NotNull String key,
             @NotNull String... placeholders) {
            this.category = category.getName() + "." + subCategory.getName();
            this.placeholders = placeholders;
            this.key = key;
        }


        /**
         * It gets the message from the language config.
         *
         * @param plugin The plugin instance.
         *
         * @return The message from the language config.
         */
        public @NotNull String get(@NotNull CropClick plugin) {
            return plugin.getLanguageConfig().getMessage("menu", category, key, true);
        }


        /**
         * It takes a list of objects, converts them to strings, and then replaces the placeholders in the message with the
         * strings.
         *
         * @param plugin The plugin instance.
         *
         * @return A string.
         */
        @SafeVarargs
        public final <T> @NotNull String get(@NotNull CropClick plugin, @NotNull T @NotNull ... values) {
            String[] valuesAsStrings = Arrays.stream(values)
                                             .map(Object::toString)
                                             .toArray(String[]::new);
            return fastReplace(get(plugin), valuesAsStrings);
        }


        /**
         * It gets the message from the language config, reads it into a list, colors it, and returns it.
         *
         * @param plugin The plugin instance
         *
         * @return A list of strings
         */
        public @NotNull List<String> getAsList(@NotNull CropClick plugin) {
            String message = plugin.getLanguageConfig().getMessage("menu", category, key, false);
            return MessageUtils.readify(message, 4).stream()
                               .map(MessageUtils::colorize)
                               .collect(Collectors.toList());
        }


        /**
         * It returns a list of strings that are the messages in the config file, with the given strings appended to the
         * end.
         *
         * @param plugin The plugin instance.
         *
         * @return A list of strings.
         */
        public @NotNull List<String> getAsList(@NotNull CropClick plugin, String @NotNull ... appendable) {
            List<String> messages = getAsList(plugin);
            messages.add("");
            messages.addAll(Arrays.asList(appendable));
            return messages;
        }


        /**
         * Send the message to the sender.
         *
         * @param plugin The plugin instance.
         * @param sender The CommandSender to send the message to.
         */
        public void send(@NotNull CropClick plugin, @NotNull CommandSender sender) {
            sender.sendMessage(get(plugin));
        }


        /**
         * It gets a list of all the crops, and sends it to the sender.
         *
         * @param plugin The plugin instance.
         * @param sender The CommandSender that is executing the command.
         */
        @SuppressWarnings("unused")
        public void sendAsList(@NotNull CropClick plugin, @NotNull CommandSender sender) {
            getAsList(plugin).forEach(sender::sendMessage);
        }


        /**
         * This function returns a string that is the title of the GUI.
         *
         * @param plugin The plugin instance.
         *
         * @return The title of the GUI.
         */
        public @NotNull String getTitle(@NotNull CropClick plugin) {
            String title = "CropClick: " + get(plugin);
            String error = getErrorMessage(plugin);
            return title.length() < 32 ? title : error;
        }


        /**
         * This function returns the title of the crafting GUI.
         *
         * @param plugin The plugin instance.
         * @param type   The type of the item.
         *
         * @return The title of the GUI.
         */
        public @NotNull String getTitle(@NotNull CropClick plugin, @NotNull String type) {
            if (type.equals("")) {
                return getTitle(plugin);
            }

            String title = "CropClick: " + get(plugin, type);
            String errorMessage = getErrorMessage(plugin);
            return title.length() < 32 ? title : errorMessage;
        }


        /**
         * Returns an unknown issue as a trigger for the language config,
         * to serve us with an error message response.
         *
         * @param plugin The plugin instance.
         *
         * @return The error message from the language config.
         */
        private @NotNull String getErrorMessage(@NotNull CropClick plugin) {
            return plugin.getLanguageConfig().getMessage("title", "error", "message", true);
        }


        /**
         * It replaces the placeholders in a message with the values.
         *
         * @param message The message to replace the placeholders in.
         *
         * @return A string with the placeholders replaced with the values.
         */
        private @NotNull String fastReplace(@NotNull String message, @NotNull String @NotNull ... values) {
            if (placeholders.length != values.length) {
                return message;
            }

            String returned = message;
            for (int i = 0; i < values.length; ++i) {
                returned = StringUtils.replaceOnce(returned, placeholders[i], values[i]);
            }
            return returned;
        }

    }

}