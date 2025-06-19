/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
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
package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.addons.auraskills.AuraSkillsAddon;
import com.github.bakuplayz.cropclick.addons.jobsreborn.JobsRebornAddon;
import com.github.bakuplayz.cropclick.addons.mcmmo.MCMMOAddon;
import com.github.bakuplayz.cropclick.addons.residence.ResidenceAddon;
import com.github.bakuplayz.cropclick.addons.towny.TownyAddon;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.Container;
import com.github.bakuplayz.cropclick.common.Blocks;
import com.github.bakuplayz.cropclick.common.Collections;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.CropManager;
import com.github.bakuplayz.cropclick.permissions.PermissionKey;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * A class representing a {@link CropClick} scoped {@link Player}.
 */
public final class CropPlayer {

    private static final Map<UUID, CropPlayer> PLAYERS = new HashMap<>();

    @Getter
    private static final Cache cache = new Cache();

    private final PlayersConfig config;

    @Getter
    private final String playerID;

    @Getter
    private final UUID playerUUID;

    @Getter
    private final OfflinePlayer offlinePlayer;

    @Getter
    private final AddonFeatures addonFeatures;

    @Getter
    private final AutofarmFeatures autofarmFeatures;

    @Getter
    private final PermissionFeatures permissions;


    private CropPlayer(@NotNull UUID playerId) {
        CropClick plugin = CropClick.getInstance();

        this.playerUUID = playerId;
        this.playerID = playerId.toString();
        this.permissions = new PermissionFeatures();
        this.addonFeatures = new AddonFeatures(plugin);
        this.autofarmFeatures = new AutofarmFeatures(plugin);
        this.config = plugin.getConfigManager().getPlayersConfig();
        this.offlinePlayer = Bukkit.getOfflinePlayer(playerUUID).getPlayer();
    }


    @NotNull
    public static CropPlayer fromId(@NotNull UUID playerId) {
        return cache.add(playerId);
    }


    @NotNull
    public static CropPlayer fromId(@NotNull String playerId) {
        return fromId(UUID.fromString(playerId));
    }


    @NotNull
    public static CropPlayer fromPlayer(@NotNull Player player) {
        return fromId(player.getUniqueId());
    }


    @NotNull
    public static CropPlayer fromPlayer(@NotNull OfflinePlayer player) {
        return fromId(player.getUniqueId());
    }


    /**
     * Toggles the plugin for this player.
     */
    public void togglePlugin() {
        config.set(ConfigurationKey.DISABLED_PLAYERS, Collections.toggleItem(config.getDisabledPlayers(), playerID));
    }


    /**
     * Checks whether the provided player id is able to use {@link CropClick}.
     *
     * @return true if able, otherwise false.
     */
    public boolean isPluginEnabled() {
        return !config.getDisabledPlayers().contains(playerID);
    }


    public static final class Cache {

        public CropPlayer remove(@NotNull UUID playerId) {
            return PLAYERS.remove(playerId);
        }


        public CropPlayer add(@NotNull UUID playerId) {
            return PLAYERS.computeIfAbsent(playerId, CropPlayer::new);
        }

    }

    public final class AddonFeatures {

        private final AddonManager addonManager;


        public AddonFeatures(@NotNull CropClick plugin) {
            this.addonManager = plugin.getAddonManager();
        }


        /**
         * Checks if the {@link Player provided player} is allowed to modify the current region.
         *
         * @return true if allowed, otherwise false.
         */
        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        public boolean canModifyRegion() {
            ResidenceAddon residenceAddon = addonManager.getResidenceAddon();
            TownyAddon townyAddon = addonManager.getTownyAddon();

            if (addonManager.isInstalledAndEnabled(townyAddon)) {
                return townyAddon.getFunctionality().canDestroyCrop(offlinePlayer);
            }

            if (addonManager.isInstalledAndEnabled(residenceAddon)) {
                boolean isRegionMember = residenceAddon.getFunctionality().isMemberOfRegion(offlinePlayer);
                boolean hasRegionFlag = residenceAddon.getFunctionality().hasRegionFlag(offlinePlayer.getLocation());
                return isRegionMember || hasRegionFlag;
            }

            return true;
        }


        /**
         * Updates the stats of all the {@link AbstractAddon addons}.
         *
         * @param crop the crop to find the effects with.
         */
        public void updateStats(@NotNull Crop crop) {
            MCMMOAddon mcMMOAddon = addonManager.getMcMMOAddon();
            JobsRebornAddon jobsRebornAddon = addonManager.getJobsRebornAddon();
            AuraSkillsAddon auraSkillsAddon = addonManager.getAuraSkillsAddon();

            if (addonManager.isInstalledAndEnabled(jobsRebornAddon)) {
                jobsRebornAddon.getFunctionality().updateStats(offlinePlayer, crop);
            }

            if (addonManager.isInstalledAndEnabled(mcMMOAddon)) {
                mcMMOAddon.getFunctionality().addExperience(offlinePlayer, crop);
            }

            if (addonManager.isInstalledAndEnabled(auraSkillsAddon)) {
                auraSkillsAddon.getFunctionality().addExperience(offlinePlayer, crop);
            }
        }

    }


    public final class AutofarmFeatures {

        private final CropManager cropManager;


        public AutofarmFeatures(@NotNull CropClick plugin) {
            this.cropManager = plugin.getCropManager();
        }


        public Location getSelectedCrop() {
            return config.getSelectedCrop(playerID);
        }


        public Location getSelectedContainer() {
            return config.getSelectedContainer(playerID);
        }


        public Location getSelectedDispenser() {
            return config.getSelectedDispenser(playerID);
        }


        /**
         * Selects the {@link Block provided block}, iff it is a {@link Crop crop} block.
         *
         * @param block the block that was selected.
         */
        public void selectCrop(@NotNull Block block) {
            if (!cropManager.getFinder().isCrop(block)) return;
            config.set(ConfigurationKey.SELECTED_CROP, block.getLocation(), playerID);
        }


        /**
         * Selects the {@link Block provided block}, iff it is a container block.
         *
         * @param block the block that was selected.
         */
        public void selectContainer(@NotNull Block block) {
            if (!Blocks.isAnyType(block, Container.TYPES)) return;
            config.set(ConfigurationKey.SELECTED_CONTAINER, block.getLocation(), playerID);
        }


        /**
         * Selects the {@link Block provided block}, iff it is a {@link Dispenser dispenser} block.
         *
         * @param block the block that was selected.
         */
        public void selectDispenser(@NotNull Block block) {
            if (!Blocks.isSameType(block, XMaterial.DISPENSER)) return;
            config.set(ConfigurationKey.SELECTED_DISPENSER, block.getLocation(), playerID);
        }


        /**
         * Deselects the {@link Block provided block}, iff it is a {@link Crop crop} block.
         *
         * @param block the block that was deselected.
         */
        public void deselectCrop(@NotNull Block block) {
            if (!cropManager.getFinder().isCrop(block)) return;
            config.set(ConfigurationKey.SELECTED_CROP, null, playerID);
        }


        /**
         * Deselects the {@link Block provided block}, iff it is a container block.
         *
         * @param block the block that was deselected.
         */
        public void deselectContainer(@NotNull Block block) {
            if (!Blocks.isAnyType(block, Container.TYPES)) return;
            config.set(ConfigurationKey.SELECTED_CONTAINER, null, playerID);
        }


        /**
         * Deselects the {@link Block provided block}, iff it is a {@link Dispenser dispenser} block.
         *
         * @param block the block that was deselected.
         */
        public void deselectDispenser(@NotNull Block block) {
            if (!Blocks.isSameType(block, XMaterial.DISPENSER)) return;
            config.set(ConfigurationKey.SELECTED_DISPENSER, null, playerID);
        }


        /**
         * Deselects all the provided player's id selected autofarm components,
         * and deselects the components for every other player that also has any
         * of these components selected.
         */
        public void deselectComponents() {
            Location crop = config.getSelectedCrop(playerID);
            Location container = config.getSelectedContainer(playerID);
            Location dispenser = config.getSelectedDispenser(playerID);

            config.setWithoutSave(ConfigurationKey.SELECTED_PLAYER, null, playerID);

            config.getKeys(ConfigurationKey.ALL_PLAYERS).forEach(keys -> {
                if (keys.equals("disabled")) return;
                
                CropPlayer other = CropPlayer.fromId(keys.split("\\.")[0]);

                if (crop != null && crop == config.getSelectedCrop(other.playerID)) {
                    other.getAutofarmFeatures().deselectCrop(crop.getBlock());
                }

                if (container != null && container == config.getSelectedContainer(other.playerID)) {
                    other.getAutofarmFeatures().deselectContainer(container.getBlock());
                }

                if (dispenser != null && dispenser == config.getSelectedDispenser(other.playerID)) {
                    other.getAutofarmFeatures().deselectDispenser(dispenser.getBlock());
                }
            });

            config.save();
        }


        /**
         * Checks whether the {@link Block provided crop block} is selected by the player.
         *
         * @param block the crop block to check.
         *
         * @return true if selected, otherwise false.
         */
        public boolean isCropSelected(@NotNull Block block) {
            if (cropManager.getFinder().isCrop(block)) {
                Location crop = config.getSelectedCrop(playerID);
                return crop != null && crop.equals(block.getLocation());
            }
            return false;
        }


        /**
         * Checks whether the {@link Block provided container block} is selected by the player.
         *
         * @param block the container block to check.
         *
         * @return true if selected, otherwise false.
         */
        public boolean isContainerSelected(@NotNull Block block) {
            if (Blocks.isAnyType(block, Container.TYPES)) {
                Location container = config.getSelectedContainer(playerID);
                return container != null && container.equals(block.getLocation());
            }
            return false;
        }


        /**
         * Checks whether the {@link Block provided dispenser block} is selected by the player.
         *
         * @param block the dispenser block to check.
         *
         * @return true if selected, otherwise false.
         */
        public boolean isDispenserSelected(@NotNull Block block) {
            if (Blocks.isSameType(block, XMaterial.DISPENSER)) {
                Location dispenser = config.getSelectedDispenser(playerID);
                return dispenser != null && dispenser.equals(block.getLocation());
            }
            return false;
        }

    }


    public final class PermissionFeatures {

        /**
         * Checks whether the {@link Player current player} is allowed to unlink own autofarm and others {@link Autofarm autofarms}.
         *
         * @param autofarm the autofarm to check.
         *
         * @return true if allowed, otherwise false.
         */
        public boolean canUnlink(@NotNull Autofarm autofarm) {
            if (!playerID.equals(autofarm.getFarmerId().toString())) {
                return has(PermissionKey.AUTOFARM_UNLINK_OTHERS);
            }
            return has(PermissionKey.AUTOFARM_UNLINK);
        }


        public boolean canUpdate(@NotNull Autofarm autofarm) {
            if (!playerID.equals(autofarm.getFarmerId().toString())) {
                return has(PermissionKey.AUTOFARM_UPDATE_OTHERS);
            }
            return has(PermissionKey.AUTOFARM_UPDATE);
        }


        public boolean canInteractAt(Autofarm autofarm) {
            if (autofarm != null && !playerID.equals(autofarm.getFarmerId().toString())) {
                return has(PermissionKey.AUTOFARM_INTERACT_OTHERS);
            }
            return has(PermissionKey.AUTOFARM_INTERACT);
        }


        public boolean has(@NotNull PermissionKey key, Object @NotNull ... args) {
            return offlinePlayer.getPlayer().hasPermission(key.getPermission(args));
        }

    }

}
