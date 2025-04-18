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

import com.cryptomorin.xseries.XMaterial;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.addons.auraskills.AuraSkillsAddon;
import com.github.bakuplayz.cropclick.addons.jobsreborn.JobsRebornAddon;
import com.github.bakuplayz.cropclick.addons.mcmmo.MCMMOAddon;
import com.github.bakuplayz.cropclick.addons.residence.ResidenceAddon;
import com.github.bakuplayz.cropclick.addons.towny.TownyAddon;
import com.github.bakuplayz.cropclick.addons.worldguard.WorldGuardAddon;
import com.github.bakuplayz.cropclick.autofarms.ContainerComponent;
import com.github.bakuplayz.cropclick.common.BlockUtils;
import com.github.bakuplayz.cropclick.common.CollectionUtils;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

/**
 * A class representing a {@link CropClick} scoped {@link Player}.
 */
public final class CropPlayer {

    private final OfflinePlayer player;

    @Getter
    private final String playerId;

    private final PlayersConfig config;

    private final AddonManager addonManager;


    public CropPlayer(@NotNull Player player) {
        this(player.getUniqueId().toString());
    }


    public CropPlayer(@NotNull String playerId) {
        this.playerId = playerId;
        this.config = CropClick.getInstance().getPlayersConfig();
        this.addonManager = CropClick.getInstance().getAddonManager();
        this.player = Bukkit.getOfflinePlayer(UUID.fromString(playerId));
    }


    /**
     * Toggles the plugin for this player.
     */
    public void togglePlugin() {
        config.set(
                ConfigurationKey.DISABLED_PLAYERS,
                CollectionUtils.toggleItem(config.getDisabledPlayers(), playerId)
        );
    }


    /**
     * Checks whether the provided player id is able to use {@link CropClick}.
     *
     * @return true if able, otherwise false.
     */
    public boolean isPluginEnabled() {
        return !config.getDisabledPlayers().contains(playerId);
    }


    /**
     * Selects the {@link Block provided block}, iff it is a {@link Crop crop} block.
     *
     * @param block the block that was selected.
     */
    public void selectCrop(@NotNull Block block) {
        if (!CropClick.getInstance().getCropManager().isCrop(block)) return;
        config.set(ConfigurationKey.SELECTED_CROP, block.getLocation(), playerId);
    }


    /**
     * Selects the {@link Block provided block}, iff it is a container block.
     *
     * @param block the block that was selected.
     */
    public void selectContainer(@NotNull Block block) {
        if (!BlockUtils.isAnyType(block, ContainerComponent.getTypes())) return;
        config.set(ConfigurationKey.SELECTED_CONTAINER, block.getLocation(), playerId);
    }


    /**
     * Selects the {@link Block provided block}, iff it is a {@link Dispenser dispenser} block.
     *
     * @param block the block that was selected.
     */
    public void selectDispenser(@NotNull Block block) {
        if (BlockUtils.isSameType(block, XMaterial.DISPENSER)) return;
        config.set(ConfigurationKey.SELECTED_DISPENSER, block.getLocation(), playerId);
    }


    /**
     * Deselects the {@link Block provided block}, iff it is a {@link Crop crop} block.
     *
     * @param block the block that was deselected.
     */
    public void deselectCrop(@NotNull Block block) {
        if (!CropClick.getInstance().getCropManager().isCrop(block)) return;
        config.set(ConfigurationKey.SELECTED_CROP, null, playerId);
    }


    /**
     * Deselects the {@link Block provided block}, iff it is a container block.
     *
     * @param block the block that was deselected.
     */
    public void deselectContainer(@NotNull Block block) {
        if (!BlockUtils.isAnyType(block, ContainerComponent.getTypes())) return;
        config.set(ConfigurationKey.SELECTED_CONTAINER, null, playerId);
    }


    /**
     * Deselects the {@link Block provided block}, iff it is a {@link Dispenser dispenser} block.
     *
     * @param block the block that was deselected.
     */
    public void deselectDispenser(@NotNull Block block) {
        if (!BlockUtils.isSameType(block, XMaterial.DISPENSER)) return;
        config.set(ConfigurationKey.SELECTED_DISPENSER, null, playerId);
    }


    /**
     * Deselects all the provided player's id selected autofarm components,
     * and deselects the components for every other player that also has any
     * of these components selected.
     *
     * @param playerId the player to deselect all the components for.
     */
    public void deselectComponents(@NotNull String playerId) {
        Location crop = config.getSelectedCrop(playerId);
        Location container = config.getSelectedContainer(playerId);
        Location dispenser = config.getSelectedDispenser(playerId);

        config.setWithoutSave(ConfigurationKey.SELECTED_PLAYER, null, playerId);

        config.getKeys(ConfigurationKey.ALL_PLAYERS).forEach(keys -> {
            CropPlayer other = new CropPlayer(keys.split("\\.")[0]);

            if (crop != null && crop == config.getSelectedCrop(other.getPlayerId())) {
                other.deselectCrop(crop.getBlock());
            }

            if (container != null && container == config.getSelectedContainer(other.getPlayerId())) {
                other.deselectContainer(container.getBlock());
            }

            if (dispenser != null && dispenser == config.getSelectedDispenser(other.getPlayerId())) {
                other.deselectDispenser(dispenser.getBlock());
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
        if (CropClick.getInstance().getCropManager().isCrop(block)) {
            Location crop = config.getSelectedCrop(playerId);
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
        if (BlockUtils.isAnyType(block, ContainerComponent.getTypes())) {
            Location container = config.getSelectedContainer(playerId);
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
        if (BlockUtils.isSameType(block, XMaterial.DISPENSER)) {
            Location dispenser = config.getSelectedDispenser(playerId);
            return dispenser != null && dispenser.equals(block.getLocation());
        }
        return false;
    }


    /**
     * Checks if the {@link Player provided player} is allowed to modify the current region.
     *
     * @return true if allowed, otherwise false.
     */
    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    public boolean canModifyRegion() {
        WorldGuardAddon worldGuardAddon = addonManager.getWorldGuardAddon();
        ResidenceAddon residenceAddon = addonManager.getResidenceAddon();
        TownyAddon townyAddon = addonManager.getTownyAddon();

        if (addonManager.isInstalledAndEnabled(townyAddon)) {
            return townyAddon.getFunctionality().canDestroyCrop(player);
        }

        if (addonManager.isInstalledAndEnabled(residenceAddon)) {
            boolean isRegionMember = residenceAddon.getFunctionality().isMemberOfRegion(player);
            boolean hasRegionFlag = residenceAddon.getFunctionality().hasRegionFlag(player.getLocation());
            return isRegionMember || hasRegionFlag;
        }

        if (addonManager.isInstalledAndEnabled(worldGuardAddon)) {
            return worldGuardAddon.getFunctionality().regionAllowsPlayer(player);
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
            jobsRebornAddon.getFunctionality().updateStats(player, crop);
        }

        if (addonManager.isInstalledAndEnabled(mcMMOAddon)) {
            mcMMOAddon.getFunctionality().addExperience(player, crop);
        }

        if (addonManager.isInstalledAndEnabled(auraSkillsAddon)) {
            auraSkillsAddon.getFunctionality().addExperience(player, crop);
        }
    }


}
