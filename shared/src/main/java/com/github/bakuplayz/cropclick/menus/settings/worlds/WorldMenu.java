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
package com.github.bakuplayz.cropclick.menus.settings.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder.WorldMenuState;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder.WorldMenuStateFlag;
import com.github.bakuplayz.cropclick.menus.settings.states.WorldStateBuilder.WorldMenuStateHandler;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.common.ViewerMap;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;


/**
 * A class representing the World menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class WorldMenu extends AbstractStateMenu<WorldMenuState, WorldMenuStateHandler> {

    private final CropClick plugin;

    private final FarmWorld world;


    public WorldMenu(@NotNull CropClick plugin, @NotNull FarmWorld world) {
        super(WORLD_TITLE.getTitle(plugin));
        this.plugin = plugin;
        this.world = world;
    }


    @NotNull
    @Override
    public WorldMenuStateHandler createStateHandler(@NotNull Player player) {
        return WorldStateBuilder.createStateHandler(this, world);
    }


    @Override
    public void setItems() {
        setItem(20, new PlayersItem(viewers), WorldMenuStateFlag.PLAYERS);
        setItem(22, new WorldItem(), WorldMenuStateFlag.BANISHED);
        setItem(24, new AutofarmsItem(), WorldMenuStateFlag.AUTOFARMS);
        setItem(49, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @AllArgsConstructor
    private final class PlayersItem extends ClickableStateItem<WorldMenuState> {

        @NotNull
        private final ViewerMap viewers;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setPlayer(viewers.get(0));
                setMaterial(XMaterial.PLAYER_HEAD);
                setLore(getLore(world.allowsPlayers()));
                setName(WORLD_PLAYERS_ITEM_NAME.get(plugin));
            });
        }


        @Override
        public void update(@NotNull WorldMenuState state, int flag) {
            setLore(getLore(state.isPlayersAllowed()));
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> stateHandler.togglePlayersState();
        }


        @NotNull
        private List<String> getLore(boolean state) {
            String status = WORLD_PLAYERS_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", state)
                                    .build();
            return WORLD_PLAYERS_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }

    }

    private final class WorldItem extends ClickableStateItem<WorldMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String name = Messages.beautify(world.getName(), true);

                setMaterial(XMaterial.GRASS_BLOCK);
                setLore(getLore(world.isBanished()));
                setName(WORLDS_ITEM_NAME.builder(plugin).replace("%name%", name).build());
                setMaterial(world.getName().equals("world_the_end"), XMaterial.END_STONE);
                setMaterial(world.getName().equals("world_nether"), XMaterial.NETHERRACK);
            });
        }


        @Override
        public void update(@NotNull WorldMenuState state, int flag) {
            setLore(getLore(state.isBanished()));
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> stateHandler.toggleBanishedState();
        }


        @NotNull
        private List<String> getLore(boolean state) {
            String status = WORLD_WORLD_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", state)
                                    .build();
            return WORLD_WORLD_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }

    }


    private final class AutofarmsItem extends ClickableStateItem<WorldMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.DISPENSER);
                setName(WORLD_AUTOFARMS_ITEM_NAME.get(plugin));
                setLore(getLore(world.allowsAutofarms()));
            });
        }


        @Override
        public void update(@NotNull WorldMenuState state, int flag) {
            setLore(getLore(state.isAutofarmsAllowed()));
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> stateHandler.toggleAutofarmsState();
        }


        @NotNull
        private List<String> getLore(boolean state) {
            String status = WORLD_AUTOFARMS_ITEM_STATUS.builder(plugin)
                                    .replace("%status%", state)
                                    .build();
            return WORLD_AUTOFARMS_ITEM_TIPS.builder(plugin).append(status).buildAsList();
        }


    }

}
