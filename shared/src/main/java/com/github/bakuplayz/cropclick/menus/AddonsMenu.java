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
package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.menus.addons.*;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPlainMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Addons menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class AddonsMenu extends AbstractPlainMenu {

    private final AddonsState state;

    private final CropClick plugin;


    public AddonsMenu(@NotNull CropClick plugin) {
        super(ADDONS_TITLE.getTitle(plugin));
        this.state = new AddonsState(plugin);
        this.plugin = plugin;
    }


    @Override
    public void setItems() {
        setItem(10, new JobsItem(), (ignored, player) -> {
            if (state.isJobsInstalled()) new JobsRebornMenu(plugin).open(player);
        });
        setItem(12, new MMOItem(), (ignored, player) -> {
            if (state.isMmoInstalled()) new McMMOMenu(plugin).open(player);
        });
        setItem(14, new GrowthItem(), (ignored, player) -> {
            if (state.isGrowthInstalled()) new OfflineGrowthMenu(plugin).open(player);
        });
        setItem(16, new ResidenceItem(), (ignored, player) -> {
            if (state.isResidenceInstalled()) new ResidenceMenu(plugin).open(player);
        });
        setItem(28, new TownyItem(), (ignored, player) -> {
            if (state.isTownyInstalled()) new TownyMenu(plugin).open(player);
        });
        setItem(30, new SkillsItem(), (ignored, player) -> {
            if (state.isAuraSkillsInstalled()) new AuraSkillsMenu(plugin).open(player);
        });
        setItem(49, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @Getter
    private final static class AddonsState {

        private final boolean mmoInstalled;

        private final boolean mmoEnabled;

        private final boolean jobsInstalled;

        private final boolean jobsEnabled;

        private final boolean townyInstalled;

        private final boolean townyEnabled;

        private final boolean growthInstalled;

        private final boolean growthEnabled;

        private final boolean residenceInstalled;

        private final boolean residenceEnabled;

        private final boolean auraSkillsInstalled;

        private final boolean auraSkillsEnabled;


        public AddonsState(@NotNull CropClick plugin) {
            AddonManager addonManager = plugin.getAddonManager();
            this.mmoInstalled = addonManager.getMcMMOAddon().isInstalled();
            this.mmoEnabled = addonManager.getMcMMOAddon().isEnabled();
            this.townyInstalled = addonManager.getTownyAddon().isInstalled();
            this.townyEnabled = addonManager.getTownyAddon().isEnabled();
            this.jobsInstalled = addonManager.getJobsRebornAddon().isInstalled();
            this.jobsEnabled = addonManager.getJobsRebornAddon().isEnabled();
            this.residenceInstalled = addonManager.getResidenceAddon().isInstalled();
            this.residenceEnabled = addonManager.getResidenceAddon().isEnabled();
            this.growthInstalled = addonManager.getOfflineGrowthAddon().isInstalled();
            this.growthEnabled = addonManager.getOfflineGrowthAddon().isEnabled();
            this.auraSkillsInstalled = addonManager.getAuraSkillsAddon().isInstalled();
            this.auraSkillsEnabled = addonManager.getAuraSkillsAddon().isEnabled();
        }

    }

    private final class JobsItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = ADDONS_JOBS_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", state.isJobsEnabled())
                                        .build();

                setName(ADDONS_JOBS_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.STONE_HOE);
                setMaterial(!state.isJobsEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
                setMaterial(!state.isJobsInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
                setLore(ADDONS_JOBS_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }

    }

    private final class MMOItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = ADDONS_MCMMO_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", state.isMmoEnabled())
                                        .build();

                setName(ADDONS_MCMMO_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.GOLDEN_SWORD);
                setMaterial(!state.isMmoEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
                setMaterial(!state.isMmoInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
                setLore(ADDONS_MCMMO_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }

    }

    private final class GrowthItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = ADDONS_GROWTH_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", state.isGrowthEnabled())
                                        .build();

                setName(ADDONS_GROWTH_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.TALL_GRASS);
                setMaterial(!state.isGrowthEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
                setMaterial(!state.isGrowthInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
                setLore(ADDONS_GROWTH_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }

    }

    private final class ResidenceItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = ADDONS_RESIDENCE_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", state.isResidenceEnabled())
                                        .build();

                setName(ADDONS_RESIDENCE_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.OAK_FENCE);
                setMaterial(!state.isResidenceEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
                setMaterial(!state.isResidenceInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
                setLore(ADDONS_RESIDENCE_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }

    }

    private final class TownyItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = ADDONS_TOWNY_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", state.isTownyEnabled())
                                        .build();

                setName(ADDONS_TOWNY_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.OAK_FENCE_GATE);
                setMaterial(!state.isTownyEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
                setMaterial(!state.isTownyInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
                setLore(ADDONS_TOWNY_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }

    }

    private final class SkillsItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = ADDONS_SKILLS_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", state.isAuraSkillsEnabled())
                                        .build();

                setName(ADDONS_SKILLS_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.DIAMOND_AXE);
                setMaterial(!state.isAuraSkillsEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
                setMaterial(!state.isAuraSkillsInstalled(), XMaterial.ORANGE_STAINED_GLASS_PANE);
                setLore(ADDONS_SKILLS_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }

    }

}
