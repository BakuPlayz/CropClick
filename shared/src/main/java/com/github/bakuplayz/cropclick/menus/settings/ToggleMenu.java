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
package com.github.bakuplayz.cropclick.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.cropclick.menus.states.ToggleStateBuilder;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.common.ViewState;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;
import static com.github.bakuplayz.cropclick.menus.states.ToggleStateBuilder.ToggleMenuState;
import static com.github.bakuplayz.cropclick.menus.states.ToggleStateBuilder.ToggleMenuStateHandler;

/**
 * A class representing the Toggle menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ToggleMenu extends AbstractPaginatedMenu<ToggleMenuState, ToggleMenuStateHandler, String> {


    public ToggleMenu(@NotNull CropClick plugin) {
        super(TOGGLE_TITLE.getTitle(plugin), plugin);
    }


    @Override
    public List<String> getPaginationItems() {
        return Arrays.stream(Bukkit.getOfflinePlayers())
                       .map(OfflinePlayer::getUniqueId)
                       .map(Object::toString)
                       .collect(Collectors.toList());
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull String playerId, int position) {
        CropPlayer player = CropPlayer.fromPlayer(
                Bukkit.getOfflinePlayer(UUID.fromString(playerId))
        );
        return new PlayerItem(player, position);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull String playerId, int position) {
        return (item, player) -> stateHandler.togglePlayer(playerId, position);
    }


    @NotNull
    @Override
    public ToggleMenuStateHandler createStateHandler(@NotNull Player player) {
        return ToggleStateBuilder.createStateHandler(this);
    }


    @AllArgsConstructor
    private final class PlayerItem extends ClickableStateItem<ToggleMenuState> {

        @NotNull
        private CropPlayer player;

        private int position;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setLore(getLore());
                setPlayer(player.getOfflinePlayer());
                setFlags(Collections.singletonList(position));
                setMaterial(!player.isPluginEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
                setViewState(player.isPluginEnabled() ? ViewState.VISIBLE : ViewState.DISABLED);
                setName(TOGGLE_ITEM_NAME.get(plugin,
                        player.getOfflinePlayer().getName() != null
                                ? player.getOfflinePlayer().getName()
                                : player.getPlayerId())
                );
            });
        }


        @Override
        public void update(@NotNull ToggleMenuState state, int flag) {
            setLore(getLore());
            setMaterial(!player.isPluginEnabled(), XMaterial.GRAY_STAINED_GLASS_PANE);
            setViewState(player.isPluginEnabled() ? ViewState.VISIBLE : ViewState.DISABLED);
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore() {
            return Collections.singletonList(TOGGLE_ITEM_STATUS.get(plugin,
                    Messages.getStatusMessage(plugin, player.isPluginEnabled()))
            );
        }

    }

}
