/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.bakuplayz.cropclick.common;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.LanguageConfig;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


/**
 * A class handling all the shown messages, found in the {@link Menu menus} and {@link Command commands}.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class Languages {

    /**
     * An enumeration for handling all the Command messages.
     */
    public enum Command {

        COMMAND_NOT_FOUND(Category.GENERAL, "commandNotFound", "%command%"),
        PLAYER_ONLY_COMMAND(Category.GENERAL, "playerOnlyCommand"),
        PLAYER_LACK_PERMISSION(Category.GENERAL, "playerLackPermission", "%permission%"),

        HELP_DESCRIPTION(Category.HELP, "description"),
        RESET_DESCRIPTION(Category.RESET, "description"),
        RELOAD_DESCRIPTION(Category.RELOAD, "description"),
        DEFAULT_DESCRIPTION(Category.DEFAULT, "description"),
        AUTOFARM_DESCRIPTION(Category.AUTOFARM, "description"),
        SETTINGS_DESCRIPTION(Category.SETTINGS, "description"),

        RESET_DELETE(Category.RESET, "delete"),
        RESET_FAILED(Category.RESET, "failed"),
        RESET_SUCCESS(Category.RESET, "success"),

        RELOAD_FAILED(Category.RELOAD, "failed"),
        RELOAD_SUCCESS(Category.RELOAD, "success");


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
         * Gets the message from the {@link LanguageConfig language config}.
         *
         * @param plugin the plugin instance.
         *
         * @return the message found in the config.
         */
        @NotNull
        public String get(@NotNull CropClick plugin) {
            return plugin.getConfigManager().getLanguageConfig().getMessage("command", category, key, true);
        }


        /**
         * Gets the message from the {@link LanguageConfig language config}, replacing the {@link #placeholder} with the provided value.
         *
         * @param plugin the plugin instance.
         * @param value  the replacement value.
         *
         * @return the message found in the config, replaced with the provided value.
         */
        @NotNull
        public String get(@NotNull CropClick plugin, @NotNull String value) {
            return Strings.replace(get(plugin), placeholder, value);
        }


        /**
         * Sends the message to the {@link CommandSender sender}.
         *
         * @param plugin the plugin instance.
         * @param sender the sender to send the message to.
         */
        public void send(@NotNull CropClick plugin, @NotNull CommandSender sender) {
            sender.sendMessage(get(plugin));
        }


        /**
         * Sends the message to the {@link CommandSender sender}, replacing the {@link #placeholder} with the provided value.
         *
         * @param plugin the plugin instance.
         * @param sender the sender to send the message to.
         * @param value  the value to replace the placeholder with.
         */
        public void send(@NotNull CropClick plugin, @NotNull CommandSender sender, @NotNull String value) {
            sender.sendMessage(get(plugin, value));
        }


        /**
         * An enumeration for handling {@link LanguageConfig config} categories.
         */
        private enum Category {

            GENERAL,
            AUTOFARM,
            HELP,
            RESET,
            RELOAD,
            DEFAULT,
            SETTINGS;


            /**
             * Gets the category name.
             *
             * @return the name of the category.
             */
            @NotNull
            public String getName() {
                return name().toLowerCase();
            }

        }

    }

    /**
     * An enumeration for handling all the Menu messages.
     */
    @Getter
    public enum Menu {

        MAIN_TITLE(Category.TITLE, "main"),
        ADDONS_TITLE(Category.TITLE, "addons"),
        HELP_TITLE(Category.TITLE, "help"),
        NAME_TITLE(Category.TITLE, "name"),
        SETTINGS_TITLE(Category.TITLE, "settings"),
        TOGGLE_TITLE(Category.TITLE, "toggle"),
        UPDATES_TITLE(Category.TITLE, "updates"),
        MIGRATIONS_TITLE(Category.TITLE, "migrations"),
        MANAGE_AUTOFARMS_TITLE(Category.TITLE, "manageAutofarms"),

        AUTOFARMS_DASHBOARD_TITLE(Category.TITLE, "autofarmsDashboard"),
        DISPENSER_PREVIEW_TITLE(Category.TITLE, "dispenserPreview", "%id%"),
        CONTAINER_PREVIEW_TITLE(Category.TITLE, "containerPreview", "%id%"),

        CROP_TITLE(Category.TITLE, "crop"),
        CROPS_TITLE(Category.TITLE, "crops", "%type%"),
        DROP_CHANCE_TITLE(Category.TITLE, "dropChance"),

        CROP_LINK_TITLE(Category.TITLE, "cropLink"),
        DISPENSER_LINK_TITLE(Category.TITLE, "dispenserLink"),
        CONTAINER_LINK_TITLE(Category.TITLE, "containerLink"),

        PARTICLES_TITLE(Category.TITLE, "particles"),
        PARTICLE_TITLE(Category.TITLE, "particle"),

        SOUNDS_TITLE(Category.TITLE, "sounds"),
        SOUND_TITLE(Category.TITLE, "sound"),

        WORLD_TITLE(Category.TITLE, "world"),
        WORLDS_TITLE(Category.TITLE, "worlds"),

        MCMMO_CROP_TITLE(Category.TITLE, "mcMMOCrop"),
        MCMMO_TITLE(Category.TITLE, "mcMMO"),
        AURA_SKILLS_CROP_TITLE(Category.TITLE, "auraSkillsCrop"),
        AURA_SKILLS_TITLE(Category.TITLE, "auraSkills"),
        JOBS_CROP_TITLE(Category.TITLE, "jobsRebornCrop"),
        JOBS_REBORN_TITLE(Category.TITLE, "jobsReborn"),
        WORLD_GUARD_TITLE(Category.TITLE, "worldGuard"),
        TOWNY_TITLE(Category.TITLE, "towny"),
        RESIDENCE_TITLE(Category.TITLE, "residence"),
        OFFLINE_GROWTH_TITLE(Category.TITLE, "offlineGrowth"),

        GENERAL_BACK_ITEM_NAME(Category.GENERAL, "backItemName"),
        GENERAL_CURRENT_PAGE_ITEM_NAME(Category.GENERAL, SubCategory.PAGINATION, "currentPageItemName", "%page%"),
        GENERAL_DISABLED_STATUS(Category.GENERAL, SubCategory.STATUS, "disabled"),
        GENERAL_ENABLED_STATUS(Category.GENERAL, SubCategory.STATUS, "enabled"),
        GENERAL_NEXT_PAGE_ITEM_NAME(Category.GENERAL, SubCategory.PAGINATION, "nextPageItemName"),
        GENERAL_PREVIOUS_PAGE_ITEM_NAME(Category.GENERAL, SubCategory.PAGINATION, "previousPageItemName"),

        GENERAL_STATES_NEW_UPDATE(Category.GENERAL, SubCategory.UPDATE_STATES, "newUpdate"),
        GENERAL_STATES_FAILED_TO_FETCH(Category.GENERAL, SubCategory.UPDATE_STATES, "failedToFetch"),
        GENERAL_STATES_NO_UPDATE_FOUND(Category.GENERAL, SubCategory.UPDATE_STATES, "noUpdateFound"),
        GENERAL_STATES_NOT_YET_FETCHED(Category.GENERAL, SubCategory.UPDATE_STATES, "notYetFetched"),
        GENERAL_STATES_UP_TO_DATE(Category.GENERAL, SubCategory.UPDATE_STATES, "upToDate"),

        ADDONS_GROWTH_ITEM_NAME(Category.ADDONS, SubCategory.OFFLINE_GROWTH, "itemName"),
        ADDONS_GROWTH_ITEM_STATUS(Category.ADDONS, SubCategory.OFFLINE_GROWTH, "itemStatus", "%status%"),
        ADDONS_GROWTH_ITEM_TIPS(Category.ADDONS, SubCategory.OFFLINE_GROWTH, "itemTips"),

        ADDONS_GUARD_ITEM_NAME(Category.ADDONS, SubCategory.WORLD_GUARD, "itemName"),
        ADDONS_GUARD_ITEM_STATUS(Category.ADDONS, SubCategory.WORLD_GUARD, "itemStatus", "%status%"),
        ADDONS_GUARD_ITEM_TIPS(Category.ADDONS, SubCategory.WORLD_GUARD, "itemTips"),

        ADDONS_JOBS_ITEM_NAME(Category.ADDONS, SubCategory.JOBS_REBORN, "itemName"),
        ADDONS_JOBS_ITEM_STATUS(Category.ADDONS, SubCategory.JOBS_REBORN, "itemStatus", "%status%"),
        ADDONS_JOBS_ITEM_TIPS(Category.ADDONS, SubCategory.JOBS_REBORN, "itemTips"),

        ADDONS_MCMMO_ITEM_NAME(Category.ADDONS, SubCategory.MCMMO, "itemName"),
        ADDONS_MCMMO_ITEM_STATUS(Category.ADDONS, SubCategory.MCMMO, "itemStatus", "%status%"),
        ADDONS_MCMMO_ITEM_TIPS(Category.ADDONS, SubCategory.MCMMO, "itemTips"),

        ADDONS_SKILLS_ITEM_NAME(Category.ADDONS, SubCategory.AURA_SKILLS, "itemName"),
        ADDONS_SKILLS_ITEM_STATUS(Category.ADDONS, SubCategory.AURA_SKILLS, "itemStatus", "%status%"),
        ADDONS_SKILLS_ITEM_TIPS(Category.ADDONS, SubCategory.AURA_SKILLS, "itemTips"),

        ADDONS_RESIDENCE_ITEM_NAME(Category.ADDONS, SubCategory.RESIDENCE, "itemName"),
        ADDONS_RESIDENCE_ITEM_STATUS(Category.ADDONS, SubCategory.RESIDENCE, "itemStatus", "%status%"),
        ADDONS_RESIDENCE_ITEM_TIPS(Category.ADDONS, SubCategory.RESIDENCE, "itemTips"),

        ADDONS_TOWNY_ITEM_NAME(Category.ADDONS, SubCategory.TOWNY, "itemName"),
        ADDONS_TOWNY_ITEM_STATUS(Category.ADDONS, SubCategory.TOWNY, "itemStatus", "%status%"),
        ADDONS_TOWNY_ITEM_TIPS(Category.ADDONS, SubCategory.TOWNY, "itemTips"),

        ADDON_CROP_SETTINGS_ITEM_NAME(Category.ADDON, SubCategory.CROP_SETTINGS, "itemName"),
        ADDON_CROP_SETTINGS_ITEM_TIPS(Category.ADDON, SubCategory.CROP_SETTINGS, "itemTips"),

        ADDON_GROWTH_ITEM_NAME(Category.ADDON, SubCategory.OFFLINE_GROWTH, "itemName", "%status%"),
        ADDON_GROWTH_ITEM_TIPS(Category.ADDON, SubCategory.OFFLINE_GROWTH, "itemTips"),

        ADDON_GUARD_ITEM_NAME(Category.ADDON, SubCategory.WORLD_GUARD, "itemName", "%status%"),
        ADDON_GUARD_ITEM_TIPS(Category.ADDON, SubCategory.WORLD_GUARD, "itemTips"),

        ADDON_JOBS_ITEM_NAME(Category.ADDON, SubCategory.JOBS_REBORN, "itemName", "%status%"),
        ADDON_JOBS_ITEM_TIPS(Category.ADDON, SubCategory.JOBS_REBORN, "itemTips"),

        ADDON_MCMMO_ITEM_NAME(Category.ADDON, SubCategory.MCMMO, "itemName", "%status%"),
        ADDON_MCMMO_ITEM_TIPS(Category.ADDON, SubCategory.MCMMO, "itemTips"),

        ADDON_SKILLS_ITEM_NAME(Category.ADDON, SubCategory.AURA_SKILLS, "itemName", "%status%"),
        ADDON_SKILLS_ITEM_TIPS(Category.ADDON, SubCategory.AURA_SKILLS, "itemTips"),

        ADDON_RESIDENCE_ITEM_NAME(Category.ADDON, SubCategory.RESIDENCE, "itemName", "%status%"),
        ADDON_RESIDENCE_ITEM_TIPS(Category.ADDON, SubCategory.RESIDENCE, "itemTips"),

        ADDON_TOWNY_ITEM_NAME(Category.ADDON, SubCategory.TOWNY, "itemName", "%status%"),
        ADDON_TOWNY_ITEM_TIPS(Category.ADDON, SubCategory.TOWNY, "itemTips"),

        ADDON_WORLDS_ITEM_NAME(Category.ADDON, SubCategory.WORLDS, "itemName"),
        ADDON_WORLDS_ITEM_STATUS(Category.ADDON, SubCategory.WORLDS, "itemStatus", "%status%"),
        ADDON_WORLDS_ITEM_TIPS(Category.ADDON, SubCategory.WORLDS, "itemTips"),

        MANAGE_AUTOFARMS_ITEM_NAME(Category.MANAGE_AUTOFARMS, "itemName", "%name%", "%status%"),
        MANAGE_AUTOFARMS_ITEM_OWNER(Category.MANAGE_AUTOFARMS, "itemOwner", "%owner%"),
        MANAGE_AUTOFARMS_ITEM_OWNER_UNCLAIMED(Category.MANAGE_AUTOFARMS, "itemOwnerUnclaimed"),

        AUTOFARMS_DASHBOARD_LINK_ITEM_NAME(Category.AUTOFARMS_DASHBOARD, SubCategory.LINK, "itemName"),
        AUTOFARMS_DASHBOARD_LINK_ITEM_TIPS(Category.AUTOFARMS_DASHBOARD, SubCategory.LINK, "itemTips"),
        AUTOFARMS_DASHBOARD_LINK_ITEM_STATUS(Category.AUTOFARMS_DASHBOARD, SubCategory.LINK, "itemStatus", "%status%"),
        AUTOFARMS_DASHBOARD_MANAGE_AUTOFARMS_ITEM_NAME(Category.AUTOFARMS_DASHBOARD, SubCategory.MANAGE_AUTOFARMS, "itemName"),
        AUTOFARMS_DASHBOARD_MANAGE_AUTOFARMS_ITEM_TIPS(Category.AUTOFARMS_DASHBOARD, SubCategory.MANAGE_AUTOFARMS, "itemTips"),
        AUTOFARMS_DASHBOARD_MANAGE_AUTOFARMS_ITEM_STATUS(Category.AUTOFARMS_DASHBOARD, SubCategory.MANAGE_AUTOFARMS, "itemStatus", "%status%"),
        AUTOFARMS_DASHBOARD_TOGGLE_ITEM_NAME(Category.AUTOFARMS_DASHBOARD, SubCategory.TOGGLE, "itemName"),
        AUTOFARMS_DASHBOARD_TOGGLE_ITEM_TIPS(Category.AUTOFARMS_DASHBOARD, SubCategory.TOGGLE, "itemTips"),
        AUTOFARMS_DASHBOARD_TOGGLE_ITEM_STATUS(Category.AUTOFARMS_DASHBOARD, SubCategory.TOGGLE, "itemStatus", "%status%"),

        CROPS_ITEM_DROP_NAME(Category.CROPS, SubCategory.ITEM, "dropName", "%name%"),
        CROPS_ITEM_DROP_VALUE(Category.CROPS, SubCategory.ITEM, "dropValue", "%value%"),
        CROPS_ITEM_JOBS_EXPERIENCE(Category.CROPS, SubCategory.ITEM, "jobsExperience", "%status%"),
        CROPS_ITEM_JOBS_MONEY(Category.CROPS, SubCategory.ITEM, "jobsMoney", "%status%"),
        CROPS_ITEM_JOBS_POINTS(Category.CROPS, SubCategory.ITEM, "jobsPoints", "%status%"),
        CROPS_ITEM_MMO_EXPERIENCE(Category.CROPS, SubCategory.ITEM, "mcMMOExperience", "%status%"),
        CROPS_ITEM_AURA_SKILLS_EXPERIENCE(Category.CROPS, SubCategory.ITEM, "auraSkillsExperience", "%status%"),
        CROPS_ITEM_NAME(Category.CROPS, SubCategory.ITEM, "name", "%name%", "%status%"),
        CROPS_ITEM_PARTICLES(Category.CROPS, SubCategory.ITEM, "particles", "%status%"),
        CROPS_ITEM_SOUNDS(Category.CROPS, SubCategory.ITEM, "sounds", "%status%"),
        CROPS_STATUS_DISABLED(Category.CROPS, SubCategory.STATUS, "disabled"),
        CROPS_STATUS_ENABLED(Category.CROPS, SubCategory.STATUS, "enabled"),

        CROP_CHANCE_ITEM_CROP_STATUS(Category.CROP, SubCategory.DROP_CHANCE, "itemCropStatus", "%chance%"),
        CROP_CHANCE_ITEM_NAME(Category.CROP, SubCategory.DROP_CHANCE, "itemName"),
        CROP_CHANCE_ITEM_SEED_STATUS(Category.CROP, SubCategory.DROP_CHANCE, "itemSeedStatus", "%chance%"),
        CROP_CROP_ITEM_DROP_VALUE(Category.CROP, SubCategory.CROP, "itemDropValue", "%value%"),
        CROP_CROP_ITEM_NAME(Category.CROP, SubCategory.CROP, "itemName", "%name%", "%status%"),
        CROP_CROP_ITEM_TIPS(Category.CROP, SubCategory.CROP, "itemTips"),
        CROP_AT_LEAST_ITEM_NAME(Category.CROP, SubCategory.AT_LEAST, "itemName"),
        CROP_AT_LEAST_ITEM_TIPS(Category.CROP, SubCategory.AT_LEAST, "itemTips"),
        CROP_AT_LEAST_ITEM_STATUS(Category.CROP, SubCategory.AT_LEAST, "itemStatus", "%status%"),
        CROP_LINKABLE_ITEM_NAME(Category.CROP, SubCategory.LINKABLE, "itemName"),
        CROP_LINKABLE_ITEM_TIPS(Category.CROP, SubCategory.LINKABLE, "itemTips"),
        CROP_LINKABLE_ITEM_STATUS(Category.CROP, SubCategory.LINKABLE, "itemStatus", "%status%"),
        CROP_REPLANT_ITEM_NAME(Category.CROP, SubCategory.REPLANT, "itemName"),
        CROP_REPLANT_ITEM_TIPS(Category.CROP, SubCategory.REPLANT, "itemTips"),
        CROP_REPLANT_ITEM_STATUS(Category.CROP, SubCategory.REPLANT, "itemStatus", "%status%"),
        CROP_SEED_ITEM_DROP_VALUE(Category.CROP, SubCategory.SEED, "itemDropValue", "%value%"),
        CROP_SEED_ITEM_NAME(Category.CROP, SubCategory.SEED, "itemName", "%name%", "%status%"),
        CROP_SEED_ITEM_TIPS(Category.CROP, SubCategory.SEED, "itemTips"),
        CROP_ADD_ITEM_AFTER(Category.CROP, SubCategory.ADD, "itemAfter", "%value%"),
        CROP_ADD_ITEM_NAME(Category.CROP, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        CROP_REMOVE_ITEM_AFTER(Category.CROP, SubCategory.REMOVE, "itemAfter", "%value%"),
        CROP_REMOVE_ITEM_NAME(Category.CROP, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),
        CROP_STATUS_DISABLED(Category.CROP, SubCategory.STATUS, "disabled"),
        CROP_STATUS_ENABLED(Category.CROP, SubCategory.STATUS, "enabled"),

        DROP_CHANCE_CROP_ITEM_NAME(Category.DROP_CHANCE, SubCategory.CROP, "itemName", "%name%", "%status%"),
        DROP_CHANCE_CROP_ITEM_TIPS(Category.DROP_CHANCE, SubCategory.CROP, "itemTips"),
        DROP_CHANCE_CROP_ITEM_DROP_CHANCE(Category.DROP_CHANCE, SubCategory.CROP, "itemDropChance", "%chance%"),
        DROP_CHANCE_SEED_ITEM_NAME(Category.DROP_CHANCE, SubCategory.SEED, "itemName", "%name%", "%status%"),
        DROP_CHANCE_SEED_ITEM_TIPS(Category.DROP_CHANCE, SubCategory.SEED, "itemTips"),
        DROP_CHANCE_SEED_ITEM_DROP_CHANCE(Category.DROP_CHANCE, SubCategory.SEED, "itemDropChance", "%chance%"),
        DROP_CHANCE_ADD_ITEM_AFTER(Category.DROP_CHANCE, SubCategory.ADD, "itemAfter", "%value%"),
        DROP_CHANCE_ADD_ITEM_NAME(Category.DROP_CHANCE, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        DROP_CHANCE_REMOVE_ITEM_AFTER(Category.DROP_CHANCE, SubCategory.REMOVE, "itemAfter", "%value%"),
        DROP_CHANCE_REMOVE_ITEM_NAME(Category.DROP_CHANCE, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),
        DROP_CHANCE_STATUS_DISABLED(Category.DROP_CHANCE, SubCategory.STATUS, "disabled"),
        DROP_CHANCE_STATUS_ENABLED(Category.DROP_CHANCE, SubCategory.STATUS, "enabled"),

        HELP_ITEM_DESCRIPTION(Category.HELP, "itemDescription", "%description%"),
        HELP_ITEM_NAME(Category.HELP, "itemName", "%name%"),
        HELP_ITEM_PERMISSION(Category.HELP, "itemPermission", "%permission%"),
        HELP_ITEM_USAGE(Category.HELP, "itemUsage", "%usage%"),

        JOBS_CROP_EXPERIENCE_ITEM_NAME(Category.JOBS_CROP, SubCategory.EXPERIENCE, "itemName"),
        JOBS_CROP_EXPERIENCE_ITEM_TIPS(Category.JOBS_CROP, SubCategory.EXPERIENCE, "itemTips"),
        JOBS_CROP_EXPERIENCE_ITEM_VALUE(Category.JOBS_CROP, SubCategory.EXPERIENCE, "itemValue", "%value%"),
        JOBS_CROP_MONEY_ITEM_NAME(Category.JOBS_CROP, SubCategory.MONEY, "itemName"),
        JOBS_CROP_MONEY_ITEM_TIPS(Category.JOBS_CROP, SubCategory.MONEY, "itemTips"),
        JOBS_CROP_MONEY_ITEM_VALUE(Category.JOBS_CROP, SubCategory.MONEY, "itemValue", "%value%"),
        JOBS_CROP_POINTS_ITEM_NAME(Category.JOBS_CROP, SubCategory.POINTS, "itemName"),
        JOBS_CROP_POINTS_ITEM_TIPS(Category.JOBS_CROP, SubCategory.POINTS, "itemTips"),
        JOBS_CROP_POINTS_ITEM_VALUE(Category.JOBS_CROP, SubCategory.POINTS, "itemValue", "%value%"),
        JOBS_CROP_ADD_ITEM_AFTER(Category.JOBS_CROP, SubCategory.ADD, "itemAfter", "%value%"),
        JOBS_CROP_ADD_ITEM_NAME(Category.JOBS_CROP, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        JOBS_CROP_REMOVE_ITEM_AFTER(Category.JOBS_CROP, SubCategory.REMOVE, "itemAfter", "%value%"),
        JOBS_CROP_REMOVE_ITEM_NAME(Category.JOBS_CROP, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),

        LINK_ACTION_FAILURE(Category.LINK, SubCategory.ACTIONS, "linkFailure"),
        LINK_ACTION_SUCCESS(Category.LINK, SubCategory.ACTIONS, "linkSuccess"),
        UNLINK_ACTION_SUCCESS(Category.LINK, SubCategory.ACTIONS, "unlinkSuccess"),
        LINK_CROP_NAME(Category.LINK, SubCategory.CROP, "itemName"),
        LINK_CONTAINER_NAME(Category.LINK, SubCategory.CONTAINER, "itemName"),
        LINK_CONTAINER_TIPS(Category.LINK, SubCategory.CONTAINER, "itemTips"),
        LINK_DISPENSER_NAME(Category.LINK, SubCategory.DISPENSER, "itemName"),
        LINK_DISPENSER_TIPS(Category.LINK, SubCategory.DISPENSER, "itemTips"),
        LINK_FORMAT_STATE(Category.LINK, SubCategory.FORMAT, "state", "%state%"),
        LINK_FORMAT_X(Category.LINK, SubCategory.FORMAT, "x", "%x%"),
        LINK_FORMAT_Y(Category.LINK, SubCategory.FORMAT, "y", "%y%"),

        LINK_FORMAT_Z(Category.LINK, SubCategory.FORMAT, "z", "%z%"),
        LINK_GLASS_ITEM_NAME_LINKED(Category.LINK, SubCategory.GLASS, "itemNameLinked"),
        LINK_GLASS_ITEM_NAME_SELECTED(Category.LINK, SubCategory.GLASS, "itemNameSelected"),
        LINK_GLASS_ITEM_NAME_UNLINKED(Category.LINK, SubCategory.GLASS, "itemNameUnlinked"),
        LINK_GLASS_ITEM_NAME_UNCLAIMED(Category.LINK, SubCategory.GLASS, "itemNameUnclaimed"),
        LINK_STATES_SELECTED(Category.LINK, SubCategory.STATES, "selected"),
        LINK_STATES_UNLINKED(Category.LINK, SubCategory.STATES, "unlinked"),
        LINK_TOGGLE_NAME(Category.LINK, SubCategory.TOGGLE, "itemName"),
        LINK_TOGGLE_STATUS(Category.LINK, SubCategory.TOGGLE, "itemStatus", "%status%"),
        LINK_CLAIM_NAME(Category.LINK, SubCategory.CLAIM, "itemName"),
        LINK_CLAIM_STATUS(Category.LINK, SubCategory.CLAIM, "itemStatus"),

        MAIN_AUTOFARMS_DASHBOARD_ITEM_NAME(Category.MAIN, SubCategory.AUTOFARMS_DASHBOARD, "itemName"),
        MAIN_AUTOFARMS_DASHBOARD_ITEM_STATUS(Category.MAIN, SubCategory.AUTOFARMS_DASHBOARD, "itemStatus", "%status%"),
        MAIN_AUTOFARMS_DASHBOARD_ITEM_TIPS(Category.MAIN, SubCategory.AUTOFARMS_DASHBOARD, "itemTips"),
        MAIN_ADDONS_ITEM_NAME(Category.MAIN, SubCategory.ADDONS, "itemName"),
        MAIN_ADDONS_ITEM_STATUS(Category.MAIN, SubCategory.ADDONS, "itemStatus", "%status%"),
        MAIN_ADDONS_ITEM_TIPS(Category.MAIN, SubCategory.ADDONS, "itemTips"),
        MAIN_CROPS_ITEM_NAME(Category.MAIN, SubCategory.CROPS, "itemName"),
        MAIN_CROPS_ITEM_STATUS(Category.MAIN, SubCategory.CROPS, "itemStatus", "%status%"),
        MAIN_CROPS_ITEM_TIPS(Category.MAIN, SubCategory.CROPS, "itemTips"),
        MAIN_HELP_ITEM_NAME(Category.MAIN, SubCategory.HELP, "itemName"),
        MAIN_HELP_ITEM_STATUS(Category.MAIN, SubCategory.HELP, "itemStatus", "%status%"),
        MAIN_HELP_ITEM_TIPS(Category.MAIN, SubCategory.HELP, "itemTips"),
        MAIN_SETTINGS_ITEM_NAME(Category.MAIN, SubCategory.SETTINGS, "itemName"),
        MAIN_SETTINGS_ITEM_STATUS(Category.MAIN, SubCategory.SETTINGS, "itemStatus", "%status%"),
        MAIN_SETTINGS_ITEM_TIPS(Category.MAIN, SubCategory.SETTINGS, "itemTips"),
        MAIN_UPDATES_ITEM_NAME(Category.MAIN, SubCategory.UPDATES, "itemName"),
        MAIN_UPDATES_ITEM_STATE(Category.MAIN, SubCategory.UPDATES, "itemState", "%state%"),
        MAIN_UPDATES_ITEM_TIPS(Category.MAIN, SubCategory.UPDATES, "itemTips"),

        MCMMO_CROP_EXPERIENCE_ITEM_NAME(Category.MCMMO_CROP, SubCategory.EXPERIENCE, "itemName"),
        MCMMO_CROP_EXPERIENCE_ITEM_TIPS(Category.MCMMO_CROP, SubCategory.EXPERIENCE, "itemTips"),
        MCMMO_CROP_EXPERIENCE_ITEM_VALUE(Category.MCMMO_CROP, SubCategory.EXPERIENCE, "itemValue", "%value%"),
        MCMMO_CROP_EXPERIENCE_REASON_ITEM_NAME(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REASON, "itemName"),
        MCMMO_CROP_EXPERIENCE_REASON_ITEM_TIPS(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REASON, "itemTips"),
        MCMMO_CROP_EXPERIENCE_REASON_ITEM_VALUE(Category.MCMMO_CROP, SubCategory.EXPERIENCE_REASON, "itemValue", "%value%"),
        MCMMO_CROP_REASON_RESPONSE_CHANGED(Category.MCMMO_CROP, SubCategory.REASON_RESPONSE, "changed", "%name%"),
        MCMMO_CROP_REASON_RESPONSE_UNCHANGED(Category.MCMMO_CROP, SubCategory.REASON_RESPONSE, "unchanged"),
        MCMMO_CROP_ADD_ITEM_AFTER(Category.MCMMO_CROP, SubCategory.ADD, "itemAfter", "%value%"),
        MCMMO_CROP_ADD_ITEM_NAME(Category.MCMMO_CROP, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        MCMMO_CROP_REMOVE_ITEM_AFTER(Category.MCMMO_CROP, SubCategory.REMOVE, "itemAfter", "%value%"),
        MCMMO_CROP_REMOVE_ITEM_NAME(Category.MCMMO_CROP, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),

        AURA_SKILLS_CROP_EXPERIENCE_ITEM_NAME(Category.AURA_SKILLS_CROP, SubCategory.EXPERIENCE, "itemName"),
        AURA_SKILLS_CROP_EXPERIENCE_ITEM_TIPS(Category.AURA_SKILLS_CROP, SubCategory.EXPERIENCE, "itemTips"),
        AURA_SKILLS_CROP_EXPERIENCE_ITEM_VALUE(Category.AURA_SKILLS_CROP, SubCategory.EXPERIENCE, "itemValue", "%value%"),
        AURA_SKILLS_CROP_ADD_ITEM_AFTER(Category.AURA_SKILLS_CROP, SubCategory.ADD, "itemAfter", "%value%"),
        AURA_SKILLS_CROP_ADD_ITEM_NAME(Category.AURA_SKILLS_CROP, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        AURA_SKILLS_CROP_REMOVE_ITEM_AFTER(Category.AURA_SKILLS_CROP, SubCategory.REMOVE, "itemAfter", "%value%"),
        AURA_SKILLS_CROP_REMOVE_ITEM_NAME(Category.AURA_SKILLS_CROP, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),

        NAME_COLOR_ITEM_CODE(Category.NAME, SubCategory.COLOR_CODE, "itemColor", "%code%", "%color%", "%name%"),
        NAME_COLOR_ITEM_NAME(Category.NAME, SubCategory.COLOR_CODE, "itemName"),

        NAME_CROP_ITEM_NAME(Category.NAME, SubCategory.CROP, "itemName", "%name%", "%status%"),
        NAME_CROP_ITEM_TIPS(Category.NAME, SubCategory.CROP, "itemTips"),
        NAME_CROP_ITEM_DROP_NAME(Category.NAME, SubCategory.CROP, "itemDropName", "%name%"),
        NAME_SEED_ITEM_NAME(Category.NAME, SubCategory.SEED, "itemName", "%name%", "%status%"),
        NAME_SEED_ITEM_TIPS(Category.NAME, SubCategory.SEED, "itemTips"),
        NAME_SEED_ITEM_DROP_NAME(Category.NAME, SubCategory.SEED, "itemDropName", "%name%"),
        NAME_RESPONSE_CHANGED(Category.NAME, SubCategory.RESPONSE, "changed", "%name%"),
        NAME_RESPONSE_UNCHANGED(Category.NAME, SubCategory.RESPONSE, "unchanged"),

        PARTICLES_ITEM_NAME(Category.PARTICLES, "itemName", "%name%", "%status%"),
        PARTICLES_ITEM_ORDER(Category.PARTICLES, "itemOrder", "%order%"),

        PARTICLE_ADD_ITEM_AFTER(Category.PARTICLE, SubCategory.ADD, "itemAfter", "%value%"),
        PARTICLE_ADD_ITEM_NAME(Category.PARTICLE, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        PARTICLE_REMOVE_ITEM_AFTER(Category.PARTICLE, SubCategory.REMOVE, "itemAfter", "%value%"),
        PARTICLE_REMOVE_ITEM_NAME(Category.PARTICLE, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),
        PARTICLE_AMOUNT_ITEM_NAME(Category.PARTICLE, SubCategory.AMOUNT, "itemName"),
        PARTICLE_AMOUNT_ITEM_TIPS(Category.PARTICLE, SubCategory.AMOUNT, "itemTips"),
        PARTICLE_AMOUNT_ITEM_VALUE(Category.PARTICLE, SubCategory.AMOUNT, "itemValue", "%value%"),
        PARTICLE_DELAY_ITEM_NAME(Category.PARTICLE, SubCategory.DELAY, "itemName"),
        PARTICLE_DELAY_ITEM_TIPS(Category.PARTICLE, SubCategory.DELAY, "itemTips"),
        PARTICLE_DELAY_ITEM_VALUE(Category.PARTICLE, SubCategory.DELAY, "itemValue", "%value%"),
        PARTICLE_SPEED_ITEM_NAME(Category.PARTICLE, SubCategory.SPEED, "itemName"),
        PARTICLE_SPEED_ITEM_TIPS(Category.PARTICLE, SubCategory.SPEED, "itemTips"),
        PARTICLE_SPEED_ITEM_VALUE(Category.PARTICLE, SubCategory.SPEED, "itemValue", "%value%"),
        PARTICLE_INCREASE_ORDER_ITEM_NAME(Category.PARTICLE, SubCategory.INCREASE_ORDER, "itemName"),
        PARTICLE_INCREASE_ORDER_ITEM_AFTER(Category.PARTICLE, SubCategory.INCREASE_ORDER, "itemAfter", "%value%"),
        PARTICLE_DECREASE_ORDER_ITEM_NAME(Category.PARTICLE, SubCategory.DECREASE_ORDER, "itemName"),
        PARTICLE_DECREASE_ORDER_ITEM_AFTER(Category.PARTICLE, SubCategory.DECREASE_ORDER, "itemAfter", "%value%"),

        SETTINGS_AUTOFARMS_ITEM_NAME(Category.SETTINGS, SubCategory.AUTOFARMS, "itemName"),
        SETTINGS_AUTOFARMS_ITEM_STATUS(Category.SETTINGS, SubCategory.AUTOFARMS, "itemStatus", "%status%"),
        SETTINGS_AUTOFARMS_ITEM_TIPS(Category.SETTINGS, SubCategory.AUTOFARMS, "itemTips"),
        SETTINGS_NAME_ITEM_NAME(Category.SETTINGS, SubCategory.NAME, "itemName"),
        SETTINGS_NAME_ITEM_STATUS(Category.SETTINGS, SubCategory.NAME, "itemStatus", "%status%"),
        SETTINGS_NAME_ITEM_TIPS(Category.SETTINGS, SubCategory.NAME, "itemTips"),
        SETTINGS_PARTICLES_ITEM_NAME(Category.SETTINGS, SubCategory.PARTICLES, "itemName"),
        SETTINGS_PARTICLES_ITEM_STATUS(Category.SETTINGS, SubCategory.PARTICLES, "itemStatus", "%status%"),
        SETTINGS_PARTICLES_ITEM_TIPS(Category.SETTINGS, SubCategory.PARTICLES, "itemTips"),
        SETTINGS_SOUNDS_ITEM_NAME(Category.SETTINGS, SubCategory.SOUNDS, "itemName"),
        SETTINGS_SOUNDS_ITEM_STATUS(Category.SETTINGS, SubCategory.SOUNDS, "itemStatus", "%status%"),
        SETTINGS_SOUNDS_ITEM_TIPS(Category.SETTINGS, SubCategory.SOUNDS, "itemTips"),
        SETTINGS_TOGGLE_ITEM_NAME(Category.SETTINGS, SubCategory.TOGGLE, "itemName"),
        SETTINGS_TOGGLE_ITEM_STATUS(Category.SETTINGS, SubCategory.TOGGLE, "itemStatus", "%status%"),
        SETTINGS_TOGGLE_ITEM_TIPS(Category.SETTINGS, SubCategory.TOGGLE, "itemTips"),
        SETTINGS_WORLDS_ITEM_NAME(Category.SETTINGS, SubCategory.WORLDS, "itemName"),
        SETTINGS_WORLDS_ITEM_STATUS(Category.SETTINGS, SubCategory.WORLDS, "itemStatus", "%status%"),
        SETTINGS_WORLDS_ITEM_TIPS(Category.SETTINGS, SubCategory.WORLDS, "itemTips"),
        SETTINGS_MIGRATIONS_ITEM_NAME(Category.SETTINGS, SubCategory.MIGRATIONS, "itemName"),
        SETTINGS_MIGRATIONS_ITEM_TIPS(Category.SETTINGS, SubCategory.MIGRATIONS, "itemTips"),

        SOUNDS_ITEM_NAME(Category.SOUNDS, "itemName", "%name%", "%status%"),
        SOUNDS_ITEM_ORDER(Category.SOUNDS, "itemOrder", "%order%"),

        SOUND_ADD_ITEM_AFTER(Category.SOUND, SubCategory.ADD, "itemAfter", "%value%"),
        SOUND_ADD_ITEM_NAME(Category.SOUND, SubCategory.ADD, "itemName", "%amount%", "%type%"),
        SOUND_REMOVE_ITEM_AFTER(Category.SOUND, SubCategory.REMOVE, "itemAfter", "%value%"),
        SOUND_REMOVE_ITEM_NAME(Category.SOUND, SubCategory.REMOVE, "itemName", "%amount%", "%type%"),
        SOUND_DELAY_ITEM_NAME(Category.SOUND, SubCategory.DELAY, "itemName"),
        SOUND_DELAY_ITEM_TIPS(Category.SOUND, SubCategory.DELAY, "itemTips"),
        SOUND_DELAY_ITEM_VALUE(Category.SOUND, SubCategory.DELAY, "itemValue", "%value%"),
        SOUND_PITCH_ITEM_NAME(Category.SOUND, SubCategory.PITCH, "itemName"),
        SOUND_PITCH_ITEM_TIPS(Category.SOUND, SubCategory.PITCH, "itemTips"),
        SOUND_PITCH_ITEM_VALUE(Category.SOUND, SubCategory.PITCH, "itemValue", "%value%"),
        SOUND_VOLUME_ITEM_NAME(Category.SOUND, SubCategory.VOLUME, "itemName"),
        SOUND_VOLUME_ITEM_TIPS(Category.SOUND, SubCategory.VOLUME, "itemTips"),
        SOUND_VOLUME_ITEM_VALUE(Category.SOUND, SubCategory.VOLUME, "itemValue", "%value%"),
        SOUND_INCREASE_ORDER_ITEM_NAME(Category.SOUND, SubCategory.INCREASE_ORDER, "itemName"),
        SOUND_INCREASE_ORDER_ITEM_AFTER(Category.SOUND, SubCategory.INCREASE_ORDER, "itemAfter", "%value%"),
        SOUND_DECREASE_ORDER_ITEM_NAME(Category.SOUND, SubCategory.DECREASE_ORDER, "itemName"),
        SOUND_DECREASE_ORDER_ITEM_AFTER(Category.SOUND, SubCategory.DECREASE_ORDER, "itemAfter", "%value%"),

        TOGGLE_ITEM_NAME(Category.TOGGLE, "itemName", "%name%"),
        TOGGLE_ITEM_STATUS(Category.TOGGLE, "itemStatus", "%status%"),

        UPDATES_CONSOLE_ITEM_NAME(Category.UPDATES, SubCategory.CONSOLE, "itemName"),
        UPDATES_CONSOLE_ITEM_STATUS(Category.UPDATES, SubCategory.CONSOLE, "itemStatus", "%status%"),
        UPDATES_CONSOLE_ITEM_TIPS(Category.UPDATES, SubCategory.CONSOLE, "itemTips"),
        UPDATES_PLAYER_ITEM_NAME(Category.UPDATES, SubCategory.PLAYER, "itemName"),
        UPDATES_PLAYER_ITEM_STATUS(Category.UPDATES, SubCategory.PLAYER, "itemStatus", "%status%"),
        UPDATES_PLAYER_ITEM_TIPS(Category.UPDATES, SubCategory.PLAYER, "itemTips"),
        UPDATES_UPDATES_ITEM_NAME(Category.UPDATES, SubCategory.UPDATES, "itemName"),
        UPDATES_UPDATES_ITEM_STATE(Category.UPDATES, SubCategory.UPDATES, "itemState", "%state%"),
        UPDATES_UPDATES_ITEM_TIPS(Category.UPDATES, SubCategory.UPDATES, "itemTips"),

        MIGRATIONS_PROGRESS_ITEM_NAME(Category.MIGRATIONS, SubCategory.PROGRESS, "itemName"),
        MIGRATIONS_PROGRESS_ITEM_TIPS(Category.MIGRATIONS, SubCategory.PROGRESS, "itemTips"),
        MIGRATIONS_PROGRESS_ITEM_STATUS(Category.MIGRATIONS, SubCategory.PROGRESS, "itemStatus", "%status%"),
        MIGRATIONS_DEFAULT_ITEM_NAME(Category.MIGRATIONS, SubCategory.DEFAULT, "itemName"),
        MIGRATIONS_DEFAULT_ITEM_TIPS(Category.MIGRATIONS, SubCategory.DEFAULT, "itemTips", "%user%", "%host%", "%port%", "%dialect%"),
        MIGRATIONS_DEFAULT_ITEM_STATUS(Category.MIGRATIONS, SubCategory.DEFAULT, "itemStatus", "%status%"),
        MIGRATIONS_MIGRATION_ITEM_NAME(Category.MIGRATIONS, SubCategory.MIGRATION, "itemName"),
        MIGRATIONS_MIGRATION_ITEM_TIPS(Category.MIGRATIONS, SubCategory.MIGRATION, "itemTips", "%user%", "%host%", "%port%", "%dialect%"),
        MIGRATIONS_MIGRATION_ITEM_STATUS(Category.MIGRATIONS, SubCategory.MIGRATION, "itemStatus", "%status%"),

        MIGRATIONS_CONNECTION_STATES_CONNECTED(Category.MIGRATIONS, SubCategory.CONNECTION_STATES, "connected"),
        MIGRATIONS_CONNECTION_STATES_DISCONNECTED(Category.MIGRATIONS, SubCategory.CONNECTION_STATES, "disconnected"),

        MIGRATIONS_STATES_NOT_INITIALIZED(Category.MIGRATIONS, SubCategory.STATES, "notInitialized"),
        MIGRATIONS_STATES_COMPLETED(Category.MIGRATIONS, SubCategory.STATES, "completed"),
        MIGRATIONS_STATES_FAILED(Category.MIGRATIONS, SubCategory.STATES, "failed"),
        MIGRATIONS_STATES_IN_PROGRESS(Category.MIGRATIONS, SubCategory.STATES, "inProgress"),

        WORLDS_ITEM_NAME(Category.WORLDS, "itemName", "%name%"),
        WORLDS_ITEM_STATUS(Category.WORLDS, "itemStatus", "%status%"),
        WORLDS_ITEM_JOBS_TIPS(Category.WORLDS, "itemJobsTips"),
        WORLDS_ITEM_MCMMO_TIPS(Category.WORLDS, "itemMcMMOTips"),
        WORLDS_ITEM_GROWTH_TIPS(Category.WORLDS, "itemOfflineGrowthTips"),
        WORLDS_ITEM_AURA_SKILLS_TIPS(Category.WORLDS, "itemAuraSkillsTips"),
        WORLDS_ITEM_RESIDENCE_TIPS(Category.WORLDS, "itemResidenceTips"),
        WORLDS_ITEM_TOWNY_TIPS(Category.WORLDS, "itemTownyTips"),
        WORLDS_ITEM_GUARD_TIPS(Category.WORLDS, "itemWorldGuardTips"),

        WORLD_AUTOFARMS_ITEM_NAME(Category.WORLD, SubCategory.AUTOFARMS, "itemName"),
        WORLD_AUTOFARMS_ITEM_TIPS(Category.WORLD, SubCategory.AUTOFARMS, "itemTips"),
        WORLD_AUTOFARMS_ITEM_STATUS(Category.WORLD, SubCategory.AUTOFARMS, "itemStatus", "%status%"),
        WORLD_PLAYERS_ITEM_NAME(Category.WORLD, SubCategory.PLAYERS, "itemName"),
        WORLD_PLAYERS_ITEM_TIPS(Category.WORLD, SubCategory.PLAYERS, "itemTips"),
        WORLD_PLAYERS_ITEM_STATUS(Category.WORLD, SubCategory.PLAYERS, "itemStatus", "%status%"),
        WORLD_WORLD_ITEM_NAME(Category.WORLD, SubCategory.WORLD, "itemName", "%name%"),
        WORLD_WORLD_ITEM_TIPS(Category.WORLD, SubCategory.WORLD, "itemTips"),
        WORLD_WORLD_ITEM_STATUS(Category.WORLD, SubCategory.WORLD, "itemStatus", "%status%");

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


        @NotNull
        public MessageBuilder builder(@NotNull CropClick plugin) {
            return MessageBuilder.of(plugin, this);
        }


        @NotNull
        public String get(@NotNull CropClick plugin) {
            return builder(plugin).build();
        }


        @NotNull
        public String getTitle(@NotNull CropClick plugin) {
            return "CropClick: " + builder(plugin).build();
        }


        @NotNull
        public String getTitleWithId(@NotNull CropClick plugin, @NotNull String id) {
            return "CropClick: " + builder(plugin).replace("%id%", id).build();
        }


        /**
         * An enumeration for handling {@link LanguageConfig config} categories.
         */
        private enum Category {

            ADDON,
            ADDONS,
            AUTOFARMS_DASHBOARD("autofarmsDashboard"),
            MANAGE_AUTOFARMS("manageAutofarms"),
            CROP,
            CROPS,
            DROP_CHANCE("dropChance"),
            GENERAL,
            HELP,
            JOBS_CROP("jobsCrop"),
            LINK,
            MAIN,
            MCMMO_CROP("mcMMOCrop"),
            AURA_SKILLS_CROP("auraSkillsCrop"),
            NAME,
            PARTICLE,
            PARTICLES,
            SETTINGS,
            SOUND,
            SOUNDS,
            TITLE,
            TOGGLE,
            UPDATES,
            MIGRATIONS,
            WORLD,
            WORLDS;


            private final String altName;


            Category() {
                this.altName = null;
            }


            Category(@NotNull String altName) {
                this.altName = altName;
            }


            /**
             * Gets the {@link #altName alternative name} or category name, if not found.
             *
             * @return the alternative name, otherwise the category name.
             */
            @NotNull
            public String getName() {
                return altName != null ? altName : name().toLowerCase();
            }

        }


        /**
         * An enumeration for handling {@link LanguageConfig config} subcategories.
         */
        private enum SubCategory {

            ACTIONS,
            ADD,
            ADDONS,
            AMOUNT,
            AT_LEAST("atLeastOne"),
            AUTOFARMS,
            MANAGE_AUTOFARMS("manageAutofarms"),
            LINK,
            AURA_SKILLS("auraSkills"),
            COLOR_CODE("colorCode"),
            CONSOLE,
            CONTAINER,
            CLAIM,
            CROP,
            CROPS,
            CROP_SETTINGS("cropSettings"),
            DELAY,
            DISPENSER,
            DROP_CHANCE("dropChance"),
            EXPERIENCE,
            EXPERIENCE_REASON("experienceReason"),
            FORMAT,
            INCREASE_ORDER("increaseOrder"),
            DECREASE_ORDER("decreaseOrder"),
            GLASS,
            HELP,
            ITEM,
            JOBS_REBORN("jobsReborn"),
            LINKABLE,
            MCMMO("mcMMO"),
            MONEY,
            NAME,
            OFFLINE_GROWTH("offlineGrowth"),
            PAGINATION,
            PARTICLES,
            PITCH,
            PLAYER,
            PLAYERS,
            POINTS,
            REASON_RESPONSE("reasonResponse"),
            REMOVE,
            REPLANT,
            RESIDENCE,
            RESPONSE,
            SEED,
            SETTINGS,
            SOUNDS,
            SPEED,
            STATES,
            CONNECTION_STATES("connectionStates"),
            STATUS,
            TOGGLE,
            TOWNY,
            UPDATES,
            PROGRESS,
            DEFAULT,
            MIGRATION,
            MIGRATIONS,
            UPDATE_STATES("updateStates"),
            AUTOFARMS_DASHBOARD("autofarmsDashboard"),
            VOLUME,
            WORLD,
            WORLDS,
            WORLD_GUARD("worldGuard");


            private final String altName;


            SubCategory() {
                this.altName = null;
            }


            SubCategory(@NotNull String altName) {
                this.altName = altName;
            }


            /**
             * Gets the {@link #altName alternative name} or category name, if not found.
             *
             * @return the alternative name, otherwise the category name.
             */
            @NotNull
            public String getName() {
                return altName != null ? altName : name().toLowerCase();
            }

        }

    }

}