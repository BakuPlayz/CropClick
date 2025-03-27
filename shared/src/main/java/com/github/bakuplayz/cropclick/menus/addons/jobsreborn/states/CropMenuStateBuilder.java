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
package com.github.bakuplayz.cropclick.menus.addons.jobsreborn.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu.AbstractCropMenuState;
import com.github.bakuplayz.cropclick.menus.addons.jobsreborn.CropMenu;
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
public final class CropMenuStateBuilder {

    public final static int MIN_VALUE = 0;

    public final static int MAX_VALUE = 10_000;


    @NotNull
    public static CropMenuStateHandler createStateHandler(@NotNull CropMenu menu, @NotNull CropClick plugin, @NotNull Crop crop) {
        return new CropMenuStateHandler(menu, plugin, crop);
    }


    public static class CropMenuStateHandler extends MenuStateHandler<CropMenuState, CropMenu> {

        private final Crop crop;

        private final CropsConfig cropsConfig;


        private CropMenuStateHandler(@NotNull CropMenu observer, @NotNull CropClick plugin, @NotNull Crop crop) {
            super(observer, new CropMenuState(plugin, crop));
            this.cropsConfig = plugin.getCropsConfig();
            this.crop = crop;
        }


        public void decreaseMoneyValue(int decrement) {
            updateState(state.getMoney(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.MONEY_VALUE);
        }


        public void increaseMoneyValue(int increment) {
            updateState(state.getMoney(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.MONEY_VALUE);
        }


        public void decreasePointsValue(int decrement) {
            updateState(state.getPoints(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.POINTS_VALUE);
        }


        public void increasePointsValue(int increment) {
            updateState(state.getPoints(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.POINTS_VALUE);
        }


        public void decreaseExperienceValue(int decrement) {
            updateState(state.getExperience(), (state) -> Math.max(state - decrement, MIN_VALUE), CropMenuStateFlag.EXPERIENCE_VALUE);
        }


        public void increaseExperienceValue(int increment) {
            updateState(state.getExperience(), (state) -> Math.min(state + increment, MAX_VALUE), CropMenuStateFlag.EXPERIENCE_VALUE);
        }


        @Override
        protected <P> CropMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == CropMenuStateFlag.MONEY_VALUE) {
                state.setMoney(infer(partial));
                cropsConfig.set(ConfigurationKey.JOBS_MONEY, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.POINTS_VALUE) {
                state.setPoints(infer(partial));
                cropsConfig.set(ConfigurationKey.JOBS_POINTS, infer(partial), crop.getName());
            }

            if (flag == CropMenuStateFlag.EXPERIENCE_VALUE) {
                state.setExperience(infer(partial));
                cropsConfig.set(ConfigurationKey.JOBS_EXPERIENCE, infer(partial), crop.getName());
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class CropMenuState extends AbstractCropMenuState {

        private int points;

        private int money;

        private int experience;


        private CropMenuState(@NotNull CropClick plugin, @NotNull Crop crop) {
            this.money = plugin.getCropsConfig().get(ConfigurationKey.JOBS_MONEY, crop.getName());
            this.points = plugin.getCropsConfig().get(ConfigurationKey.JOBS_POINTS, crop.getName());
            this.experience = plugin.getCropsConfig().get(ConfigurationKey.JOBS_EXPERIENCE, crop.getName());
        }

    }

    public static final class CropMenuStateFlag {

        public final static int MONEY_VALUE = 0x1;

        public final static int POINTS_VALUE = 0x2;

        public final static int EXPERIENCE_VALUE = 0x3;

    }

}
