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
import com.github.bakuplayz.cropclick.addons.abstracts.AbstractAddon;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.menus.abstracts.states.WorldsStateBuilder;
import com.github.bakuplayz.cropclick.menus.abstracts.states.WorldsStateBuilder.WorldsMenuState;
import com.github.bakuplayz.cropclick.menus.abstracts.states.WorldsStateBuilder.WorldsMenuStateHandler;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.WORLDS_ITEM_NAME;
import static com.github.bakuplayz.cropclick.common.Languages.Menu.WORLDS_TITLE;

/**
 * A class representing the Abstract Worlds menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractWorldsMenu extends AbstractPaginatedMenu<WorldsMenuState, WorldsMenuStateHandler, FarmWorld> {

    private final ItemLoreSupplier loreSupplier;


    protected AbstractWorldsMenu(@NotNull CropClick plugin, @NotNull ItemLoreSupplier supplier) {
        super(WORLDS_TITLE.getTitle(plugin), plugin);
        this.loreSupplier = supplier;
    }


    protected static AbstractAddon getAddon(@NotNull CropClick plugin, @NotNull String addonName) {
        return plugin.getAddonManager().findByName(addonName);
    }


    @Override
    public CompletableFuture<List<FarmWorld>> getFuturePaginationItems() {
        return plugin.getWorldManager().getWorlds();
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull FarmWorld world, int position) {
        return new WorldItem(world, position);
    }


    @Override
    public WorldsMenuStateHandler createStateHandler(@NotNull Player player) {
        return WorldsStateBuilder.createStateHandler(this, plugin);
    }


    @FunctionalInterface
    protected interface ItemLoreSupplier {

        List<String> getLore(@NotNull FarmWorld world);

    }

    @AllArgsConstructor
    private final class WorldItem extends ClickableStateItem<WorldsMenuState> {

        private FarmWorld world;

        private int position;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String name = Messages.beautify(world.getName(), true);

                setMaterial(XMaterial.GRASS_BLOCK);
                setLore(loreSupplier.getLore(world));
                setFlags(Collections.singletonList(position));
                setName(WORLDS_ITEM_NAME.builder(plugin).replace("%name%", name).build());
                setMaterial(world.getName().equals("world_the_end"), XMaterial.END_STONE);
                setMaterial(world.getName().equals("world_nether"), XMaterial.NETHERRACK);
            });
        }


        @Override
        public void update(@NotNull WorldsMenuState state, int flag) {
            setLore(loreSupplier.getLore(world));
        }


    }

}
