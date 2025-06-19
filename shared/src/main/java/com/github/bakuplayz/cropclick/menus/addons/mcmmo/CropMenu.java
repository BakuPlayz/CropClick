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
package com.github.bakuplayz.cropclick.menus.addons.mcmmo;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder.CropMenuState;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder.CropMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.addons.mcmmo.states.CropMenuStateBuilder.CropMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.common.AnvilMenuFactory;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.actions.ClickableAction;
import com.github.bakuplayz.spigotspin.menu.items.state.StateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Crop menu scoped for mcMMO.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CropMenu extends AbstractCropMenu<CropMenuState, CropMenuStateHandler> {


    public CropMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(AURA_SKILLS_TITLE.getTitle(plugin), plugin, crop);
    }


    @NotNull
    @Override
    public CropMenuStateHandler createStateHandler(@NotNull Player player) {
        return CropMenuStateBuilder.createStateHandler(this, plugin, crop);
    }


    @Override
    public void setItems() {
        setItem(13, new ExperienceReasonItem(), getReasonAction());
        setItem(19, new ExperienceDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseExperienceValue(MAX_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(20, new ExperienceDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseExperienceValue(MIN_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(22, new ExperienceItem(), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(24, new ExperienceIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.increaseExperienceValue(MIN_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(25, new ExperienceIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.increaseExperienceValue(MAX_CHANGE), CropMenuStateFlag.EXPERIENCE_VALUE);
        setItem(49, new CustomBackItem(plugin));
    }


    @NotNull
    private ClickableAction<ExperienceReasonItem> getReasonAction() {
        return (item, player) -> AnvilMenuFactory.createMenu(
                plugin, item,
                cropsConfig.getString(ConfigurationKey.MCMMO_REASON, crop.getName()),
                (text) -> cropsConfig.set(ConfigurationKey.MCMMO_REASON, text, crop.getName())
        ).open(player);
    }


    private final class ExperienceDecreaseItem extends AbstractDecreaseItem {

        public ExperienceDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return MCMMO_CROP_REMOVE_ITEM_NAME.get(plugin, change, "Experience");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return MCMMO_CROP_REMOVE_ITEM_AFTER.getAsList(plugin, value);
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


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.EXPERIENCE_BOTTLE);
                setLore(getLore(getState().getExperience()));
                setName(MCMMO_CROP_EXPERIENCE_ITEM_NAME.get(plugin));
            });
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.getExperience()));
        }


        private @NotNull List<String> getLore(int value) {
            return MCMMO_CROP_EXPERIENCE_ITEM_TIPS.getAsAppendList(plugin,
                    MCMMO_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, value)
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
            return MCMMO_CROP_ADD_ITEM_NAME.get(plugin, change, "Experience");
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return MCMMO_CROP_ADD_ITEM_AFTER.getAsList(plugin, value);
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

    private final class ExperienceReasonItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.PAPER);
                setName(MCMMO_CROP_EXPERIENCE_REASON_ITEM_NAME.get(plugin));
                setLore(MCMMO_CROP_EXPERIENCE_REASON_ITEM_TIPS.getAsAppendList(plugin,
                        MCMMO_CROP_EXPERIENCE_REASON_ITEM_VALUE.get(plugin, stateHandler.getState().getReason()))
                );
            });
        }

    }

}
