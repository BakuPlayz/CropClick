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
package com.github.bakuplayz.cropclick.menus.crops.chance;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.MessageUtils;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.crops.states.ChanceStateBuilder;
import com.github.bakuplayz.cropclick.menus.crops.states.ChanceStateBuilder.ChanceMenuState;
import com.github.bakuplayz.cropclick.menus.crops.states.ChanceStateBuilder.ChanceMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Chance menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ChanceMenu extends AbstractCropMenu<ChanceMenuState, ChanceMenuStateHandler> {


    public ChanceMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(DROP_CHANCE_TITLE.getTitle(plugin), plugin, crop);
    }


    @NotNull
    @Override
    public ChanceMenuStateHandler createStateHandler() {
        return ChanceStateBuilder.createStateHandler(this, plugin, crop);
    }


    @Override
    public void setItems() {
        setItem(hasSeed ? 10 : 19, new CropDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseCropValue(MAX_CHANGE), AbstractMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 11 : 20, new CropDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseCropValue(MIN_CHANGE), AbstractMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 13 : 22, new CropItem(), (item, player) -> stateHandler.toggleCropHarvestState(), Arrays.asList(AbstractMenuStateFlag.CROP_VALUE, AbstractMenuStateFlag.CROP_STATE));
        setItem(hasSeed ? 15 : 24, new CropIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseCropValue(MIN_CHANGE), AbstractMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 16 : 25, new CropIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseCropValue(MAX_CHANGE), AbstractMenuStateFlag.CROP_VALUE);

        if (hasSeed) {
            setItem(28, new SeedDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseSeedValue(MAX_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
            setItem(29, new SeedDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseSeedValue(MIN_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
            setItem(31, new SeedItem(), (item, player) -> stateHandler.toggleSeedEnabledState(), Arrays.asList(AbstractMenuStateFlag.SEED_VALUE, AbstractMenuStateFlag.SEED_STATE));
            setItem(33, new SeedIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseSeedValue(MIN_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
            setItem(34, new SeedIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseSeedValue(MAX_CHANGE), AbstractMenuStateFlag.SEED_VALUE);
        }

        setItem(49, new CustomBackItem(plugin));
    }


    private final class CropDecreaseItem extends AbstractCropDecreaseItem {

        public CropDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, change, "Crop");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getLowerBound() {
            return ChanceStateBuilder.MIN_VALUE;
        }

    }

    private final class CropItem extends AbstractCropItem {
        
        @NotNull
        @Override
        protected String getName(boolean isHarvestable) {
            String name = MessageUtils.beautify(cropName, false);
            String status = isHarvestable
                                    ? CROP_STATUS_ENABLED.get(plugin)
                                    : CROP_STATUS_DISABLED.get(plugin);

            return DROP_CHANCE_CROP_ITEM_NAME.get(plugin, name, status);
        }


        @NotNull
        @Override
        protected List<String> getLore(int value) {
            return DROP_CHANCE_CROP_ITEM_TIPS.getAsAppendList(plugin,
                    DROP_CHANCE_CROP_ITEM_DROP_CHANCE.get(plugin, value)
            );
        }

    }

    private final class CropIncreaseItem extends AbstractCropIncreaseItem {

        public CropIncreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_ADD_ITEM_NAME.get(plugin, change, "Crop");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getHigherBound() {
            return ChanceStateBuilder.MAX_VALUE;
        }

    }

    private final class SeedDecreaseItem extends AbstractSeedDecreaseItem {

        public SeedDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_REMOVE_ITEM_NAME.get(plugin, change, "Seed");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_REMOVE_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getLowerBound() {
            return ChanceStateBuilder.MIN_VALUE;
        }

    }

    private final class SeedItem extends AbstractSeedItem {


        @NotNull
        @Override
        protected String getName(boolean state) {
            String name = MessageUtils.beautify(seed.getName(), false);
            String status = MessageUtils.getStatusMessage(plugin, state);
            return DROP_CHANCE_CROP_ITEM_NAME.get(plugin, name, status);
        }


        @Override
        protected @NotNull List<String> getLore(int value) {
            return DROP_CHANCE_CROP_ITEM_TIPS.getAsAppendList(plugin,
                    DROP_CHANCE_CROP_ITEM_DROP_CHANCE.get(plugin, value)
            );
        }

    }

    private final class SeedIncreaseItem extends AbstractSeedIncreaseItem {

        public SeedIncreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return DROP_CHANCE_ADD_ITEM_NAME.get(plugin, change, "Seed");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return DROP_CHANCE_ADD_ITEM_AFTER.getAsList(plugin, value);
        }


        @Override
        protected int getHigherBound() {
            return ChanceStateBuilder.MAX_VALUE;
        }

    }

}
