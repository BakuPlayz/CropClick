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
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.settings.particles.ParticleMenu;
import com.github.bakuplayz.cropclick.models.Particle;
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


    public static class ParticleMenuStateHandler extends MenuStateHandler<ParticleMenuState, ParticleMenu> {

        private final Crop crop;

        private final String particleName;

        private final CropsConfig cropsConfig;


        private ParticleMenuStateHandler(@NotNull ParticleMenu observer, @NotNull CropClick plugin, @NotNull Crop crop, @NotNull String particleName) {
            super(observer, new ParticleMenuState(plugin, crop, particleName));
            this.cropsConfig = plugin.getCropsConfig();
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
            state.setOrder(particleConfigSection.getOrder(crop.getName(), particleName));
            state.setMaxOrder(particleConfigSection.getAmountOfParticles(crop.getName()) - 1);
            updateState(state.hasOrder, (state) -> particleConfigSection.getOrder(crop.getName(), particleName) != -1, ParticleMenuStateFlag.ORDER_STATE);
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
                particleConfigSection.swapOrder(crop.getName(), state.order, infer(partial));
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

        private double delay;

        private double speed;

        private int amount;

        private int order;

        private int maxOrder;

        private boolean hasOrder;


        private ParticleMenuState(@NotNull CropClick plugin, @NotNull Crop crop, @NotNull String particleName) {
            ParticleConfigSection particleSection = plugin.getCropsConfig().getParticleSection();

            this.order = particleSection.getOrder(crop.getName(), particleName);
            this.delay = particleSection.getDelay(crop.getName(), particleName);
            this.speed = particleSection.getSpeed(crop.getName(), particleName);
            this.amount = particleSection.getAmount(crop.getName(), particleName);
            this.maxOrder = particleSection.getAmountOfParticles(crop.getName()) - 1;
            this.hasOrder = particleSection.getOrder(crop.getName(), particleName) != -1;
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
