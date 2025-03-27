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
package com.github.bakuplayz.cropclick.menus.addons.jobsreborn;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.addons.jobsreborn.states.CropMenuStateBuilder;
import com.github.bakuplayz.cropclick.menus.addons.jobsreborn.states.CropMenuStateBuilder.CropMenuState;
import com.github.bakuplayz.cropclick.menus.addons.jobsreborn.states.CropMenuStateBuilder.CropMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.addons.jobsreborn.states.CropMenuStateBuilder.CropMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.items.state.StateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Crop menu scoped for JobsReborn.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CropMenu extends AbstractCropMenu<CropMenuState, CropMenuStateHandler> {


    public CropMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(JOBS_CROP_TITLE.getTitle(plugin), plugin, crop);
    }


    @NotNull
    @Override
    public CropMenuStateHandler createStateHandler() {
        return CropMenuStateBuilder.createStateHandler(this, plugin, crop);
    }


    @Override
    public void setItems() {
        setItem(10, new MoneyDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseMoneyValue(MAX_CHANGE), CropMenuStateFlag.MONEY_VALUE);
        setItem(11, new MoneyDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseMoneyValue(MIN_CHANGE), CropMenuStateFlag.MONEY_VALUE);
        setItem(13, new MoneyItem(), CropMenuStateFlag.MONEY_VALUE);
        setItem(15, new MoneyIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseMoneyValue(MIN_CHANGE), CropMenuStateFlag.MONEY_VALUE);
        setItem(16, new MoneyIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseMoneyValue(MAX_CHANGE), CropMenuStateFlag.MONEY_VALUE);

        setItem(19, new PointsDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreasePointsValue(MAX_CHANGE), CropMenuStateFlag.POINTS_VALUE);
        setItem(20, new PointsDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreasePointsValue(MIN_CHANGE), CropMenuStateFlag.POINTS_VALUE);
        setItem(22, new PointsItem(), CropMenuStateFlag.POINTS_VALUE);
        setItem(24, new PointsIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increasePointsValue(MIN_CHANGE), CropMenuStateFlag.POINTS_VALUE);
        setItem(25, new PointsIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increasePointsValue(MAX_CHANGE), CropMenuStateFlag.POINTS_VALUE);

        setItem(28, new ExperienceDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseExperienceValue(MAX_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(29, new ExperienceDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseExperienceValue(MIN_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(31, new ExperienceItem(), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(33, new ExperienceIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseExperienceValue(MIN_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(34, new ExperienceIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseExperienceValue(MAX_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);

        setItem(49, new CustomBackItem(plugin));
    }


    private final class MoneyDecreaseItem extends AbstractDecreaseItem {

        public MoneyDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, change, "Money");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return JOBS_CROP_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getMoney();
        }


        @Override
        protected int getLowerBound() {
            return CropMenuStateBuilder.MIN_VALUE;
        }

    }

    private final class MoneyItem extends StateItem<CropMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.GOLD_INGOT);
            setLore(getLore(getState().getMoney()));
            setName(JOBS_CROP_MONEY_ITEM_NAME.get(plugin));
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.getMoney()));
        }


        private @NotNull List<String> getLore(int value) {
            return JOBS_CROP_MONEY_ITEM_TIPS.getAsAppendList(plugin,
                    JOBS_CROP_MONEY_ITEM_VALUE.get(plugin, value)
            );
        }

    }

    private final class MoneyIncreaseItem extends AbstractIncreaseItem {

        public MoneyIncreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return JOBS_CROP_ADD_ITEM_NAME.get(plugin, change, "Money");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return JOBS_CROP_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getMoney();
        }


        @Override
        protected int getHigherBound() {
            return CropMenuStateBuilder.MAX_VALUE;
        }

    }

    private final class PointsDecreaseItem extends AbstractDecreaseItem {

        public PointsDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, change, "Points");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return JOBS_CROP_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getPoints();
        }


        @Override
        protected int getLowerBound() {
            return CropMenuStateBuilder.MIN_VALUE;
        }

    }

    private final class PointsItem extends StateItem<CropMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.GOLD_NUGGET);
            setName(JOBS_CROP_POINTS_ITEM_NAME.get(plugin));
            setLore(getLore(getState().getPoints()));
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.getPoints()));
        }


        private @NotNull List<String> getLore(int value) {
            return JOBS_CROP_POINTS_ITEM_TIPS.getAsAppendList(plugin,
                    JOBS_CROP_POINTS_ITEM_VALUE.get(plugin, value)
            );
        }

    }

    private final class PointsIncreaseItem extends AbstractIncreaseItem {

        public PointsIncreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return JOBS_CROP_ADD_ITEM_NAME.get(plugin, change, "Points");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return JOBS_CROP_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getPoints();
        }


        @Override
        protected int getHigherBound() {
            return CropMenuStateBuilder.MAX_VALUE;
        }

    }

    private final class ExperienceDecreaseItem extends AbstractDecreaseItem {

        public ExperienceDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return JOBS_CROP_REMOVE_ITEM_NAME.get(plugin, change, "Experience");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return JOBS_CROP_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getExperience();
        }


        @Override
        protected int getLowerBound() {
            return CropMenuStateBuilder.MIN_VALUE;
        }

    }

    private final class ExperienceItem extends StateItem<CropMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.EXPERIENCE_BOTTLE);
            setName(JOBS_CROP_EXPERIENCE_ITEM_NAME.get(plugin));
            setLore(getLore(getState().getExperience()));
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.getExperience()));
        }


        private @NotNull List<String> getLore(int value) {
            return JOBS_CROP_EXPERIENCE_ITEM_TIPS.getAsAppendList(plugin,
                    JOBS_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, value)
            );
        }

    }

    private final class ExperienceIncreaseItem extends AbstractIncreaseItem {

        public ExperienceIncreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return JOBS_CROP_ADD_ITEM_NAME.get(plugin, change, "Experience");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return JOBS_CROP_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getStateValue(@NotNull CropMenuState state) {
            return state.getExperience();
        }


        @Override
        protected int getHigherBound() {
            return CropMenuStateBuilder.MAX_VALUE;
        }

    }

}
