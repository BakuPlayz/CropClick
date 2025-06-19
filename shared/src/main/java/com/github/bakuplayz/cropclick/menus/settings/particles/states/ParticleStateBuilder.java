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
package com.github.bakuplayz.cropclick.menus.settings.particles.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.types.Particle;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.settings.particles.ParticleMenu;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link ParticleMenu} state.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ParticleStateBuilder {

    @NotNull
    public static ParticleMenuStateHandler createStateHandler(@NotNull ParticleMenu menu, @NotNull CropClick plugin, @NotNull Crop crop, @NotNull String particleName) {
        return new ParticleMenuStateHandler(menu, plugin, crop, particleName);
    }


    public final static class ParticleMenuStateHandler extends MenuStateHandler<ParticleMenuState, ParticleMenu> {

        private final Crop crop;

        private final String particleName;

        private final CropsConfig cropsConfig;


        private ParticleMenuStateHandler(@NotNull ParticleMenu observer, @NotNull CropClick plugin, @NotNull Crop crop, @NotNull String particleName) {
            super(observer, new ParticleMenuState(plugin, crop, particleName));
            this.cropsConfig = plugin.getConfigManager().getCropsConfig();
            this.particleName = particleName;
            this.crop = crop;
        }


        public void decreaseDelay(int decrement) {
            updateState(state.delay, (state) -> Math.max(state - decrement, Particle.MIN_DELAY), ParticleMenuStateFlag.DELAY);
            updateOrderStatus();
        }


        public void increaseDelay(int increment) {
            updateState(state.delay, (state) -> Math.min(state + increment, Particle.MAX_DELAY), ParticleMenuStateFlag.DELAY);
            updateOrderStatus();
        }


        public void decreaseSpeed(int decrement) {
            updateState(state.speed, (state) -> Math.max(state - decrement, Particle.MIN_SPEED), ParticleMenuStateFlag.SPEED);
            updateOrderStatus();
        }


        public void increaseSpeed(int increment) {
            updateState(state.speed, (state) -> Math.min(state + increment, Particle.MAX_SPEED), ParticleMenuStateFlag.SPEED);
            updateOrderStatus();
        }


        public void decreaseAmount(int decrement) {
            updateState(state.amount, (state) -> Math.max(state - decrement, Particle.MIN_AMOUNT), ParticleMenuStateFlag.AMOUNT);
            updateOrderStatus();
        }


        public void increaseAmount(int increment) {
            updateState(state.amount, (state) -> Math.min(state + increment, Particle.MAX_AMOUNT), ParticleMenuStateFlag.AMOUNT);
            updateOrderStatus();
        }


        public void decreaseOrder() {
            updateState(state.order, (state) -> --state, ParticleMenuStateFlag.ORDER);
        }


        public void increaseOrder() {
            updateState(state.order, (state) -> ++state, ParticleMenuStateFlag.ORDER);
        }


        private void updateOrderStatus() {
            state.setOrder(cropsConfig.getParticleOrder(crop, particleName));
            state.setMaxOrder(cropsConfig.countKeys(ConfigurationKey.PARTICLES, crop.getName()) - 1);
            updateState(state.hasOrder, (s) -> state.getOrder() != -1, ParticleMenuStateFlag.ORDER_STATE);
        }


        @Override
        protected <P> ParticleMenuState onUpdateState(@NotNull P partial, int flag) {
            if (flag == ParticleMenuStateFlag.DELAY) {
                state.setDelay(infer(partial));
                cropsConfig.set(ConfigurationKey.PARTICLE_DELAY, infer(partial), crop.getName(), particleName);
            }

            if (flag == ParticleMenuStateFlag.SPEED) {
                state.setSpeed(infer(partial));
                cropsConfig.set(ConfigurationKey.PARTICLE_SPEED, infer(partial), crop.getName(), particleName);
            }

            if (flag == ParticleMenuStateFlag.AMOUNT) {
                state.setAmount(infer(partial));
                cropsConfig.set(ConfigurationKey.PARTICLE_AMOUNT, infer(partial), crop.getName(), particleName);
            }

            if (flag == ParticleMenuStateFlag.ORDER) {
                cropsConfig.swapParticleOrder(crop, state.order, infer(partial));
                state.setOrder(infer(partial));
            }

            if (flag == ParticleMenuStateFlag.ORDER_STATE) {
                state.setHasOrder(infer(partial));
            }

            return state;
        }

    }

    @Getter
    @Setter
    public static final class ParticleMenuState implements MenuState {

        private long delay;

        private double speed;

        private int amount;

        private int order;

        private int maxOrder;

        private boolean hasOrder;


        private ParticleMenuState(@NotNull CropClick plugin, @NotNull Crop crop, @NotNull String particleName) {
            CropsConfig config = plugin.getConfigManager().getCropsConfig();

            this.amount = config.getInt(ConfigurationKey.PARTICLE_AMOUNT, crop.getName(), particleName);
            this.delay = config.getLong(ConfigurationKey.PARTICLE_DELAY, crop.getName(), particleName);
            this.speed = config.getDouble(ConfigurationKey.PARTICLE_SPEED, crop.getName(), particleName);
            this.maxOrder = config.countKeys(ConfigurationKey.PARTICLES, crop.getName()) - 1;
            this.order = config.getParticleOrder(crop, particleName);
            this.hasOrder = order != -1;
        }


    }

    public final static class ParticleMenuStateFlag {

        public final static int DELAY = 0x1;

        public final static int SPEED = 0x2;

        public final static int AMOUNT = 0x3;

        public final static int ORDER = 0x4;

        public final static int ORDER_STATE = 0x5;

    }

}
