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
import com.github.bakuplayz.cropclick.common.MessageUtils;
import com.github.bakuplayz.cropclick.configurations.config.PlayersConfig;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.cropclick.menus.states.ToggleStateBuilder;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.common.ViewState;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.language.LanguageAPI.Menu.*;
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

    private final PlayersConfig playersConfig;


    public ToggleMenu(@NotNull CropClick plugin) {
        super(TOGGLE_TITLE.getTitle(plugin), plugin);
        this.playersConfig = plugin.getPlayersConfig();
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
        return new PlayerItem(playerId, position);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull String playerId, int position) {
        return (item, player) -> stateHandler.togglePlayer(playerId, position);
    }


    @NotNull
    @Override
    public ToggleMenuStateHandler createStateHandler() {
        return ToggleStateBuilder.createStateHandler(this);
    }


    @AllArgsConstructor
    private final class PlayerItem extends ClickableStateItem<ToggleMenuState> {

        @NotNull
        @Setter(AccessLevel.PRIVATE)
        private String playerId;

        private int position;


        @Override
        public void create() {
            OfflinePlayer player = Bukkit.getOfflinePlayer(UUID.fromString(playerId));

            setPlayer(player);
            setPlayerId(playerId);
            setLore(getLore(playerId));
            setFlags(Collections.singletonList(position));
            setMaterial(!playersConfig.isEnabled(playerId), XMaterial.GRAY_STAINED_GLASS_PANE);
            setViewState(playersConfig.isEnabled(playerId) ? ViewState.VISIBLE : ViewState.DISABLED);
            setName(TOGGLE_ITEM_NAME.get(plugin, player.getName() != null ? player.getName() : playerId));
        }


        @Override
        public void update(@NotNull ToggleMenuState state, int flag) {
            setLore(getLore(playerId));
            setMaterial(!playersConfig.isEnabled(playerId), XMaterial.GRAY_STAINED_GLASS_PANE);
            setViewState(playersConfig.isEnabled(playerId) ? ViewState.VISIBLE : ViewState.DISABLED);
        }


        @NotNull
        @Unmodifiable
        private List<String> getLore(@NotNull String playerId) {
            return Collections.singletonList(TOGGLE_ITEM_STATUS.get(plugin,
                    MessageUtils.getStatusMessage(plugin, playersConfig.isEnabled(playerId)))
            );
        }

    }

}
