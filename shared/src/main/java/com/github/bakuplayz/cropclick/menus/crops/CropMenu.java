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
package com.github.bakuplayz.cropclick.menus.crops;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractCropMenu;
import com.github.bakuplayz.cropclick.menus.crops.chance.ChanceMenu;
import com.github.bakuplayz.cropclick.menus.crops.states.CropStateBuilder;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;
import static com.github.bakuplayz.cropclick.menus.crops.states.CropStateBuilder.*;

/**
 * A class representing the Crop menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CropMenu extends AbstractCropMenu<CropMenuState, CropMenuStateHandler> {


    public CropMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(CROP_TITLE.getTitle(plugin), plugin, crop);
    }


    @NotNull
    @Override
    public CropMenuStateHandler createStateHandler(@NotNull Player player) {
        return CropStateBuilder.createStateHandler(this, plugin, crop);
    }


    @Override
    public void setItems() {
        setItem(hasSeed ? 10 : 19, new CropDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseCropValue(MAX_CHANGE), CropMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 11 : 20, new CropDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseCropValue(MIN_CHANGE), CropMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 13 : 22, new CropItem(), Arrays.asList(CropMenuStateFlag.CROP_STATE, CropMenuStateFlag.CROP_VALUE));
        setItem(hasSeed ? 15 : 24, new CropIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.incrementCropValue(MIN_CHANGE), CropMenuStateFlag.CROP_VALUE);
        setItem(hasSeed ? 16 : 25, new CropIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.incrementCropValue(MAX_CHANGE), CropMenuStateFlag.CROP_VALUE);

        if (hasSeed) {
            setItem(28, new SeedDecreaseItem(MAX_CHANGE), (item, player) -> stateHandler.decreaseSeedValue(MAX_CHANGE), CropMenuStateFlag.SEED_VALUE);
            setItem(29, new SeedDecreaseItem(MIN_CHANGE), (item, player) -> stateHandler.decreaseSeedValue(MIN_CHANGE), CropMenuStateFlag.SEED_VALUE);
            setItem(31, new SeedItem(), Arrays.asList(CropMenuStateFlag.SEED_STATE, CropMenuStateFlag.SEED_VALUE));
            setItem(33, new SeedIncreaseItem(MIN_CHANGE), (item, player) -> stateHandler.incrementSeedValue(MIN_CHANGE), CropMenuStateFlag.SEED_VALUE);
            setItem(34, new SeedIncreaseItem(MAX_CHANGE), (item, player) -> stateHandler.incrementSeedValue(MAX_CHANGE), CropMenuStateFlag.SEED_VALUE);
        }

        setItem(46, new ChanceItem());
        setItem(47, new LinkableItem(), CropMenuStateFlag.LINKABLE_STATE);
        setItem(49, new CustomBackItem(plugin));
        setItem(51, new ReplantItem(), CropMenuStateFlag.REPLANT_STATE);
        setItem(52, new AtLeastOneItem(), CropMenuStateFlag.AT_LEAST_ONE_STATE);
    }


    private final class CropDecreaseItem extends AbstractCropDecreaseItem {

        public CropDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return CROP_REMOVE_ITEM_NAME.builder(plugin)
                           .replace("%amount%", change)
                           .replace("%type%", "Crop")
                           .build();
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return CROP_REMOVE_ITEM_AFTER.builder(plugin).replace("%value%", value).buildAsList();
        }


        @Override
        protected int getLowerBound() {
            return MIN_VALUE;
        }

    }

    private final class CropItem extends AbstractCropItem {

        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.toggleCropHarvestState();
        }


        @NotNull
        @Override
        protected String getName(boolean isHarvestable) {
            String name = Messages.beautify(cropName, false);
            String status = isHarvestable
                                    ? CROP_STATUS_ENABLED.get(plugin)
                                    : CROP_STATUS_DISABLED.get(plugin);
            return CROP_CROP_ITEM_NAME.builder(plugin)
                           .replace("%name%", name)
                           .replace("%status%", status)
                           .build();
        }


        @NotNull
        protected List<String> getLore(int value) {
            String status = CROP_CROP_ITEM_DROP_VALUE.builder(plugin)
                                    .replace("%value%", value)
                                    .build();
            return CROP_CROP_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }

    }

    private final class CropIncreaseItem extends AbstractCropIncreaseItem {

        public CropIncreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return CROP_ADD_ITEM_NAME.builder(plugin)
                           .replace("%amount%", change)
                           .replace("%type%", "Crop")
                           .build();
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return CROP_ADD_ITEM_AFTER.builder(plugin).replace("%value%", value).buildAsList();
        }


        @Override
        protected int getHigherBound() {
            return MAX_VALUE;
        }

    }

    private final class SeedDecreaseItem extends AbstractSeedDecreaseItem {

        public SeedDecreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return CROP_REMOVE_ITEM_NAME.builder(plugin)
                           .replace("%amount%", change)
                           .replace("%type%", "Seed")
                           .build();
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return CROP_REMOVE_ITEM_AFTER.builder(plugin).replace("%value%", value).buildAsList();
        }


        @Override
        protected int getLowerBound() {
            return MIN_VALUE;
        }

    }

    private final class SeedItem extends AbstractSeedItem {

        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.toggleSeedEnabledState();
        }


        @NotNull
        protected String getName(boolean isEnabled) {
            String name = Messages.beautify(seed.getName(), false);
            String status = Messages.getStatusMessage(plugin, isEnabled);
            return CROP_SEED_ITEM_NAME.builder(plugin)
                           .replace("%name%", name)
                           .replace("%status%", status)
                           .build();
        }


        @NotNull
        protected List<String> getLore(int dropAmount) {
            String status = CROP_SEED_ITEM_DROP_VALUE.builder(plugin)
                                    .replace("%value%", dropAmount)
                                    .build();
            return CROP_SEED_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }

    }

    private final class SeedIncreaseItem extends AbstractSeedIncreaseItem {

        public SeedIncreaseItem(int change) {
            super(change);
        }


        @NotNull
        @Override
        protected String getName() {
            return CROP_ADD_ITEM_NAME.builder(plugin)
                           .replace("%amount%", change)
                           .replace("%type%", "Seed")
                           .build();
        }


        @NotNull
        @Override
        @Unmodifiable
        protected List<String> getLore(int value) {
            return CROP_ADD_ITEM_AFTER.builder(plugin).replace("%value%", value).buildAsList();
        }


        @Override
        protected int getHigherBound() {
            return MAX_VALUE;
        }

    }

    private final class ChanceItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setName(CROP_CHANCE_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.OAK_PRESSURE_PLATE);
                setLore(CROP_CHANCE_ITEM_CROP_STATUS.builder(plugin)
                                .replace("%chance%", getCropDropChance())
                                .buildAsList()
                );

                if (hasSeed) {
                    setLore(CROP_CHANCE_ITEM_CROP_STATUS.builder(plugin)
                                    .replace("%chance%", getCropDropChance())
                                    .build(),
                            CROP_CHANCE_ITEM_SEED_STATUS.builder(plugin)
                                    .replace("%chance%", getSeedDropChance())
                                    .build()
                    );
                }
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> new ChanceMenu(plugin, crop).open(player);
        }


        private int getCropDropChance() {
            return (int) (cropsConfig.getDouble(ConfigurationKey.CROP_DROP_CHANCE, cropName) * 100);
        }


        private int getSeedDropChance() {
            return (int) (cropsConfig.getDouble(ConfigurationKey.SEED_DROP_CHANCE, seed.getName()) * 100);
        }

    }

    private final class LinkableItem extends ClickableStateItem<CropMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.STONE_PRESSURE_PLATE);
                setName(CROP_LINKABLE_ITEM_NAME.get(plugin));
                setLore(getLore(getState().isLinkable()));
            });
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.isLinkable()));
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.toggleLinkableState();
        }


        @NotNull
        private List<String> getLore(boolean isLinkable) {
            String status = CROP_LINKABLE_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", isLinkable)
                                    .build();
            return CROP_LINKABLE_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }

    }

    private final class ReplantItem extends ClickableStateItem<CropMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setName(CROP_REPLANT_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE);
                setLore(getLore(getState().isReplantable()));
            });
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.isReplantable()));
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.toggleReplantState();
        }


        @NotNull
        private List<String> getLore(boolean shouldReplant) {
            String status = CROP_REPLANT_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", shouldReplant)
                                    .build();
            return CROP_REPLANT_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }

    }

    private final class AtLeastOneItem extends ClickableStateItem<CropMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setName(CROP_AT_LEAST_ITEM_NAME.get(plugin));
                setMaterial(XMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE);
                setLore(getLore(getState().isDroppingAtLeastOne()));
            });
        }


        @Override
        public void update(@NotNull CropMenuState state, int flag) {
            setLore(getLore(state.isDroppingAtLeastOne()));
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.toggleAtLeastOneState();
        }


        @NotNull
        private List<String> getLore(boolean shouldDropAtLeastOne) {
            String status = CROP_AT_LEAST_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", shouldDropAtLeastOne)
                                    .build();
            return CROP_AT_LEAST_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }

    }

}
