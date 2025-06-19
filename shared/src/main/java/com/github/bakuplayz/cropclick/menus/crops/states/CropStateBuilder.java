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
package com.github.bakuplayz.cropclick.menus.crops.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.crops.CropMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link CropMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CropStateBuilder {

    public final static int MIN_VALUE = 0;

    public final static int MAX_VALUE = 576;


    @NotNull
    public static CropMenuStateHandler createStateHandler(@NotNull CropMenu menu, @NotNull CropClick plugin, @NotNull Crop crop) {
        return new CropMenuStateHandler(menu, plugin, crop);
    }


    public final static class CropMenuStateHandler extends MenuStateHandler<CropMenuState, CropMenu> {

        private final Crop crop;

        private final CropsConfig cropsConfig;


        private CropMenuStateHandler(@NotNull CropMenu observer, @NotNull CropClick plugin, @NotNull Crop crop) {
            super(observer, new CropMenuState(plugin.getConfigManager().getCropsConfig(), crop));
            this.cropsConfig = plugin.getConfigManager().getCropsConfig();
            this.crop = crop;
        }


        public void decreaseCropValue(int decrement) {
            updateState(state.getCropValue(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.CROP_VALUE);
        }


        public void incrementCropValue(int increment) {
            updateState(state.getCropValue(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.CROP_VALUE);
        }


        public void toggleCropHarvestState() {
            updateState(state.isCropHarvestable, (state) -> !state, CropMenuStateFlag.CROP_STATE);
        }


        public void decreaseSeedValue(int decrement) {
            updateState(state.getSeedValue(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.SEED_VALUE);
        }


        public void incrementSeedValue(int increment) {
            updateState(state.getSeedValue(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.SEED_VALUE);
        }


        public void toggleSeedEnabledState() {
            updateState(state.isSeedEnabled, (state) -> !state, CropMenuStateFlag.SEED_STATE);
        }


        public void toggleLinkableState() {
            updateState(state.isLinkable, (state) -> !state, CropMenuStateFlag.LINKABLE_STATE);
        }


        public void toggleReplantState() {
            updateState(state.isReplantable, (state) -> !state, CropMenuStateFlag.REPLANT_STATE);
        }


        public void toggleAtLeastOneState() {
            updateState(state.isDroppingAtLeastOne, (state) -> !state, CropMenuStateFlag.AT_LEAST_ONE_STATE);
        }


        @Override
        protected <P> CropMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == CropMenuStateFlag.CROP_VALUE) {
                state.setCropValue(infer(partial));
                cropsConfig.set(ConfigurationKey.CROP_DROP_AMOUNT, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.CROP_STATE) {
                state.setCropHarvestable(infer(partial));
                cropsConfig.set(ConfigurationKey.CROP_HARVESTABLE, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.SEED_VALUE) {
                state.setSeedValue(infer(partial));
                cropsConfig.set(ConfigurationKey.SEED_DROP_AMOUNT, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.SEED_STATE) {
                state.setSeedEnabled(infer(partial));
                cropsConfig.set(ConfigurationKey.SEED_ENABLED, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.LINKABLE_STATE) {
                state.setLinkable(infer(partial));
                cropsConfig.set(ConfigurationKey.CROP_LINKABLE, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.REPLANT_STATE) {
                state.setReplantable(infer(partial));
                cropsConfig.set(ConfigurationKey.CROP_SHOULD_REPLANT, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.AT_LEAST_ONE_STATE) {
                state.setDroppingAtLeastOne(infer(partial));
                cropsConfig.set(ConfigurationKey.CROP_DROP_AT_LEAST_ONE, infer(partial), crop.getName());
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class CropMenuState extends AbstractCropMenu.AbstractCropMenuState {

        private boolean isSeedEnabled;

        private boolean isCropHarvestable;

        private boolean isLinkable;

        private boolean isReplantable;

        private boolean isDroppingAtLeastOne;


        private CropMenuState(@NotNull CropsConfig config, @NotNull Crop crop) {
            this.isLinkable = crop.isLinkable();
            this.isReplantable = crop.shouldReplant();
            this.isCropHarvestable = crop.isHarvestable();
            this.isDroppingAtLeastOne = crop.dropAtLeastOne();
            this.isSeedEnabled = crop.hasSeed() && crop.getSeed().isEnabled();
            this.cropValue = config.getInt(ConfigurationKey.CROP_DROP_AMOUNT, crop.getName());
            this.seedValue = config.getInt(ConfigurationKey.SEED_DROP_AMOUNT, crop.hasSeed() ? crop.getSeed().getName() : "");
        }

    }

    public static final class CropMenuStateFlag {

        public final static int CROP_VALUE = 0x1;

        public final static int SEED_VALUE = 0x2;

        public final static int CROP_STATE = 0x3;

        public final static int SEED_STATE = 0x4;

        public final static int LINKABLE_STATE = 0x5;

        public final static int REPLANT_STATE = 0x6;

        public static final int AT_LEAST_ONE_STATE = 0x7;

    }

}
