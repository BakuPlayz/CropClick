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
package com.github.bakuplayz.cropclick.menus.settings.particles;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.settings.particles.states.ParticleStateBuilder;
import com.github.bakuplayz.cropclick.menus.settings.particles.states.ParticleStateBuilder.ParticleMenuState;
import com.github.bakuplayz.cropclick.menus.settings.particles.states.ParticleStateBuilder.ParticleMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.settings.particles.states.ParticleStateBuilder.ParticleMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.models.Particle;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.common.ViewState;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;

/**
 * A class representing the Particle menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ParticleMenu extends AbstractStateMenu<ParticleMenuState, ParticleMenuStateHandler> {

    /**
     * A variable measured in milliseconds.
     */
    private static final int DELAY_MIN_CHANGE = 100;

    /**
     * A variable measured in milliseconds.
     */
    private static final int DELAY_MAX_CHANGE = 500;

    private static final int SPEED_MIN_CHANGE = 1;

    private static final int SPEED_MAX_CHANGE = 5;

    private static final int AMOUNT_MIN_CHANGE = 1;

    private static final int AMOUNT_MAX_CHANGE = 5;

    private static final int ORDER_MIN = 0;


    private final CropClick plugin;

    private final Crop crop;

    private final String particleName;


    public ParticleMenu(@NotNull CropClick plugin, @NotNull Crop crop, @NotNull String particleName) {
        super(PARTICLE_TITLE.getTitle(plugin));
        this.crop = crop;
        this.plugin = plugin;
        this.particleName = particleName;
    }


    @NotNull
    @Override
    public ParticleMenuStateHandler createStateHandler() {
        return ParticleStateBuilder.createStateHandler(this, plugin, crop, particleName);
    }


    @Override
    public void setItems() {
        setItem(10, new DelayDecreaseItem(DELAY_MAX_CHANGE), (item, player) -> stateHandler.decreaseDelay(DELAY_MAX_CHANGE), ParticleMenuStateFlag.DELAY);
        setItem(11, new DelayDecreaseItem(DELAY_MIN_CHANGE), (item, player) -> stateHandler.decreaseDelay(DELAY_MIN_CHANGE), ParticleMenuStateFlag.DELAY);
        setItem(13, new DelayItem(), ParticleMenuStateFlag.DELAY);
        setItem(15, new DelayIncreaseItem(DELAY_MIN_CHANGE), (item, player) -> stateHandler.increaseDelay(DELAY_MIN_CHANGE), ParticleMenuStateFlag.DELAY);
        setItem(16, new DelayIncreaseItem(DELAY_MAX_CHANGE), (item, player) -> stateHandler.increaseDelay(DELAY_MAX_CHANGE), ParticleMenuStateFlag.DELAY);

        setItem(19, new SpeedDecreaseItem(SPEED_MAX_CHANGE), (item, player) -> stateHandler.decreaseSpeed(SPEED_MAX_CHANGE), ParticleMenuStateFlag.SPEED);
        setItem(20, new SpeedDecreaseItem(SPEED_MIN_CHANGE), (item, player) -> stateHandler.decreaseSpeed(SPEED_MIN_CHANGE), ParticleMenuStateFlag.SPEED);
        setItem(22, new SpeedItem(), ParticleMenuStateFlag.SPEED);
        setItem(24, new SpeedIncreaseItem(SPEED_MIN_CHANGE), (item, player) -> stateHandler.increaseSpeed(SPEED_MIN_CHANGE), ParticleMenuStateFlag.SPEED);
        setItem(25, new SpeedIncreaseItem(SPEED_MAX_CHANGE), (item, player) -> stateHandler.increaseSpeed(SPEED_MAX_CHANGE), ParticleMenuStateFlag.SPEED);

        setItem(28, new AmountDecreaseItem(AMOUNT_MAX_CHANGE), (item, player) -> stateHandler.decreaseAmount(AMOUNT_MAX_CHANGE), ParticleMenuStateFlag.AMOUNT);
        setItem(29, new AmountDecreaseItem(AMOUNT_MIN_CHANGE), (item, player) -> stateHandler.decreaseAmount(AMOUNT_MIN_CHANGE), ParticleMenuStateFlag.AMOUNT);
        setItem(31, new AmountItem(), ParticleMenuStateFlag.AMOUNT);
        setItem(33, new PitchIncreaseItem(AMOUNT_MIN_CHANGE), (item, player) -> stateHandler.increaseAmount(AMOUNT_MIN_CHANGE), ParticleMenuStateFlag.AMOUNT);
        setItem(34, new PitchIncreaseItem(AMOUNT_MAX_CHANGE), (item, player) -> stateHandler.increaseAmount(AMOUNT_MAX_CHANGE), ParticleMenuStateFlag.AMOUNT);

        setItem(47, new DecreaseOrderItem(), (item, player) -> stateHandler.decreaseOrder(), Arrays.asList(ParticleMenuStateFlag.ORDER, ParticleMenuStateFlag.ORDER_STATE));
        setItem(49, new CustomBackItem(plugin));
        setItem(51, new IncreaseOrderItem(), (item, player) -> stateHandler.increaseOrder(), Arrays.asList(ParticleMenuStateFlag.ORDER, ParticleMenuStateFlag.ORDER_STATE));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @AllArgsConstructor
    private final class DelayDecreaseItem extends ClickableStateItem<ParticleMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
            setName(PARTICLE_REMOVE_ITEM_NAME.get(plugin, change, "Delay"));
            setLore(getLore(getState().getDelay()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getDelay()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double delay) {
            return PARTICLE_REMOVE_ITEM_AFTER.getAsList(plugin, getAfterValue(delay));
        }


        private double getAfterValue(double beforeValue) {
            return Math.max(beforeValue - change, Particle.MIN_DELAY);
        }

    }

    private final class DelayItem extends ClickableStateItem<ParticleMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.CLOCK);
            setName(PARTICLE_DELAY_ITEM_NAME.get(plugin));
            setLore(getLore(getState().getDelay()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getDelay()));
        }


        @NotNull
        private List<String> getLore(double delay) {
            return PARTICLE_DELAY_ITEM_TIPS.getAsAppendList(plugin,
                    PARTICLE_DELAY_ITEM_VALUE.get(plugin, delay)
            );
        }

    }

    @AllArgsConstructor
    private final class DelayIncreaseItem extends ClickableStateItem<ParticleMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
            setName(PARTICLE_ADD_ITEM_NAME.get(plugin, change, "Delay"));
            setLore(getLore(getState().getDelay()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getDelay()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double delay) {
            return PARTICLE_ADD_ITEM_AFTER.getAsList(plugin, getAfterValue(delay));
        }


        private double getAfterValue(double beforeValue) {
            return Math.min(beforeValue + change, Particle.MAX_DELAY);
        }

    }

    @AllArgsConstructor
    private final class SpeedDecreaseItem extends ClickableStateItem<ParticleMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
            setName(PARTICLE_REMOVE_ITEM_NAME.get(plugin, change, "Speed"));
            setLore(getLore(getState().getSpeed()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getSpeed()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double speed) {
            return PARTICLE_REMOVE_ITEM_AFTER.getAsList(plugin, getAfterValue(speed));
        }


        private double getAfterValue(double beforeValue) {
            return Math.max(beforeValue - change, Particle.MIN_SPEED);
        }

    }

    private final class SpeedItem extends ClickableStateItem<ParticleMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.FEATHER);
            setName(PARTICLE_SPEED_ITEM_NAME.get(plugin));
            setLore(getLore(getState().getSpeed()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getSpeed()));
        }


        @NotNull
        private List<String> getLore(double speed) {
            return PARTICLE_SPEED_ITEM_TIPS.getAsAppendList(plugin,
                    PARTICLE_SPEED_ITEM_VALUE.get(plugin, speed)
            );
        }

    }

    @AllArgsConstructor
    private final class SpeedIncreaseItem extends ClickableStateItem<ParticleMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
            setName(PARTICLE_ADD_ITEM_NAME.get(plugin, change, "Speed"));
            setLore(getLore(getState().getSpeed()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getSpeed()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(double speed) {
            return PARTICLE_ADD_ITEM_AFTER.getAsList(plugin, getAfterValue(speed));
        }


        private double getAfterValue(double beforeValue) {
            return Math.min(beforeValue + change, Particle.MAX_SPEED);
        }

    }

    @AllArgsConstructor
    private final class AmountDecreaseItem extends ClickableStateItem<ParticleMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
            setName(PARTICLE_REMOVE_ITEM_NAME.get(plugin, change, "Amount"));
            setLore(getLore(getState().getAmount()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getAmount()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(int amount) {
            return PARTICLE_REMOVE_ITEM_AFTER.getAsList(plugin, getAfterValue(amount));
        }


        private int getAfterValue(int beforeValue) {
            return Math.max(beforeValue - change, Particle.MIN_AMOUNT);
        }

    }

    private final class AmountItem extends ClickableStateItem<ParticleMenuState> {

        @Override
        public void create() {
            setMaterial(XMaterial.CHEST);
            setName(PARTICLE_AMOUNT_ITEM_NAME.get(plugin));
            setLore(getLore(getState().getAmount()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getAmount()));
        }


        @NotNull
        private List<String> getLore(int amount) {
            return PARTICLE_AMOUNT_ITEM_TIPS.getAsAppendList(plugin,
                    PARTICLE_AMOUNT_ITEM_VALUE.get(plugin, amount)
            );
        }

    }

    @AllArgsConstructor
    private final class PitchIncreaseItem extends ClickableStateItem<ParticleMenuState> {

        private final int change;


        @Override
        public void create() {
            setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
            setName(PARTICLE_ADD_ITEM_NAME.get(plugin, change, "Amount"));
            setLore(getLore(getState().getAmount()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getAmount()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(int amount) {
            return PARTICLE_ADD_ITEM_AFTER.getAsList(plugin, getAfterValue(amount));
        }


        private double getAfterValue(double beforeValue) {
            return Math.min(beforeValue + change, Particle.MAX_AMOUNT);
        }

    }

    private final class IncreaseOrderItem extends ClickableStateItem<ParticleMenuState> {

        @Override
        public void create() {
            setName(PARTICLE_INCREASE_ORDER_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE);
            setLore(getLore(getState().getOrder(), getState().getMaxOrder()));
            setViewState(getViewState(getState().getOrder(), getState().getMaxOrder()));
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getOrder(), state.getMaxOrder()));
            setViewState(getViewState(state.getOrder(), state.getMaxOrder()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(int order, int maxOrder) {
            return PARTICLE_INCREASE_ORDER_ITEM_AFTER.getAsList(plugin, getAfterValue(order, maxOrder));
        }


        private int getAfterValue(int beforeOrder, int maxOrder) {
            return Math.min(beforeOrder + 1, maxOrder);
        }


        private ViewState getViewState(int order, int maxOrder) {
            if (order == -1) {
                return ViewState.INVISIBLE;
            }

            return maxOrder > order ? ViewState.VISIBLE : ViewState.INVISIBLE;
        }

    }

    private final class DecreaseOrderItem extends ClickableStateItem<ParticleMenuState> {

        @Override
        public void create() {
            setLore(getLore(getState().getOrder()));
            setViewState(getViewState(getState().getOrder()));
            setName(PARTICLE_DECREASE_ORDER_ITEM_NAME.get(plugin));
            setMaterial(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE);
        }


        @Override
        public void update(@NotNull ParticleMenuState state, int flag) {
            setLore(getLore(state.getOrder()));
            setViewState(getViewState(state.getOrder()));
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(int order) {
            return PARTICLE_DECREASE_ORDER_ITEM_AFTER.getAsList(plugin, getAfterValue(order));
        }


        private int getAfterValue(int afterOrder) {
            return Math.max(afterOrder - 1, ORDER_MIN);
        }


        private ViewState getViewState(int order) {
            return order > 0 ? ViewState.VISIBLE : ViewState.INVISIBLE;
        }

    }

}
