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
package com.github.bakuplayz.cropclick.menus.abstracts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing the Crop menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractCropMenu<S extends AbstractCropMenu.AbstractCropMenuState, SH extends MenuStateHandler<S, ?>> extends AbstractStateMenu<S, SH> {

    protected final static int MIN_CHANGE = 1;

    protected final static int MAX_CHANGE = 5;


    protected final Crop crop;

    protected final Seed seed;

    protected final String cropName;

    protected final boolean hasSeed;

    protected final CropClick plugin;

    protected final CropsConfig cropsConfig;


    public AbstractCropMenu(@NotNull String title, @NotNull CropClick plugin, @NotNull Crop crop) {
        super(title);
        this.crop = crop;
        this.plugin = plugin;
        this.seed = crop.getSeed();
        this.hasSeed = crop.hasSeed();
        this.cropName = crop.getName();
        this.cropsConfig = plugin.getConfigManager().getCropsConfig();
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @Getter
    @Setter
    public static abstract class AbstractCropMenuState implements MenuState {

        protected int cropValue;

        protected int seedValue;

        protected boolean isSeedEnabled;

        protected boolean isCropHarvestable;

    }

    public final static class AbstractMenuStateFlag {

        public final static int CROP_STATE = 0x1;

        public final static int SEED_STATE = 0x2;

        public final static int CROP_VALUE = 0x3;

        public final static int SEED_VALUE = 0x4;

    }

    @AllArgsConstructor
    protected abstract class AbstractDecreaseItem extends ClickableStateItem<S> {

        protected final int change;


        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setName(getName());
                setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
                setLore(getLore(getAfterValue(getStateValue(getState()))));
            });
        }


        @Override
        public final void update(@NotNull S state, int flag) {
            setLore(getLore(getAfterValue(getStateValue(state))));
        }


        protected abstract String getName();


        protected abstract List<String> getLore(int value);


        protected abstract int getStateValue(@NotNull S state);


        protected abstract int getLowerBound();


        private int getAfterValue(int beforeValue) {
            return Math.max(beforeValue - change, getLowerBound());
        }

    }

    @AllArgsConstructor
    protected abstract class AbstractIncreaseItem extends ClickableStateItem<S> {


        protected final int change;


        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setName(getName());
                setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
                setLore(getLore(getAfterValue(getStateValue(getState()))));
            });
        }


        @Override
        public final void update(@NotNull S state, int flag) {
            setLore(getLore(getAfterValue(getStateValue(state))));
        }


        protected abstract String getName();


        protected abstract List<String> getLore(int value);


        protected abstract int getStateValue(@NotNull S state);


        protected abstract int getHigherBound();


        private int getAfterValue(int beforeValue) {
            return Math.min(beforeValue + change, getHigherBound());
        }

    }

    protected abstract class AbstractCropDecreaseItem extends AbstractDecreaseItem {

        public AbstractCropDecreaseItem(int change) {
            super(change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getCropValue();
        }

    }

    @AllArgsConstructor
    protected abstract class AbstractCropItem extends ClickableStateItem<S> {

        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(crop.getMenuType());
                setName(getName(crop.isHarvestable()));
                setLore(getLore(getState().getCropValue()));
                setMaterial(!crop.isHarvestable(), XMaterial.GRAY_STAINED_GLASS_PANE);
            });
        }


        @Override
        public final void update(@NotNull AbstractCropMenuState state, int flag) {
            setLore(getLore(state.getCropValue()));
            setName(getName(state.isCropHarvestable()));
            setMaterial(state.isCropHarvestable() ? crop.getMenuType() : XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        protected abstract String getName(boolean state);


        @NotNull
        protected abstract List<String> getLore(int value);

    }

    protected abstract class AbstractCropIncreaseItem extends AbstractIncreaseItem {

        public AbstractCropIncreaseItem(int change) {
            super(change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getCropValue();
        }

    }

    protected abstract class AbstractSeedDecreaseItem extends AbstractDecreaseItem {

        public AbstractSeedDecreaseItem(int change) {
            super(change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getSeedValue();
        }

    }

    @AllArgsConstructor
    protected abstract class AbstractSeedItem extends ClickableStateItem<S> {

        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(seed.getMenuType());
                setName(getName(seed.isEnabled()));
                setLore(getLore(getState().getSeedValue()));
                setMaterial(!seed.isEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            });
        }


        @Override
        public final void update(@NotNull AbstractCropMenuState state, int flag) {
            setName(getName(state.isSeedEnabled()));
            setLore(getLore(state.getSeedValue()));
            setMaterial(state.isSeedEnabled() ? seed.getMenuType() : XMaterial.GRAY_STAINED_GLASS_PANE);
        }


        @NotNull
        protected abstract String getName(boolean state);


        @NotNull
        protected abstract List<String> getLore(int value);

    }

    protected abstract class AbstractSeedIncreaseItem extends AbstractIncreaseItem {

        public AbstractSeedIncreaseItem(int change) {
            super(change);
        }


        @Override
        public int getStateValue(@NotNull S state) {
            return state.getSeedValue();
        }

    }

}
