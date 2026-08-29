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
import com.github.bakuplayz.cropclick.menus.abstracts.states.AddonMenuStateBuilder;
import com.github.bakuplayz.cropclick.menus.abstracts.states.AddonMenuStateBuilder.AddonMenuState;
import com.github.bakuplayz.cropclick.menus.abstracts.states.AddonMenuStateBuilder.AddonMenuStateHandler;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractStateMenu;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Addon menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractAddonMenu extends AbstractStateMenu<AddonMenuState, AddonMenuStateHandler> {


    protected final CropClick plugin;


    protected final AbstractAddon addon;


    public AbstractAddonMenu(@NotNull String title, @NotNull CropClick plugin, @NotNull String addonName) {
        super(title);

        this.plugin = plugin;
        this.addon = plugin.getAddonManager().findByName(addonName);
    }


    @Override
    public AddonMenuStateHandler createStateHandler(@NotNull Player player) {
        return AddonMenuStateBuilder.createStateHandler(this, plugin, addon);
    }


    protected abstract class AbstractToggleItem extends ClickableStateItem<AddonMenuState> {

        @Override
        public void update(@NotNull AddonMenuState state, int flag) {
            setMaterial(state.isAddonEnabled() ? getMaterial() : XMaterial.GRAY_STAINED_GLASS_PANE);
            setName(getName());
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.toggleAddon();
        }


        protected abstract String getName();


        protected abstract XMaterial getMaterial();

    }

    public final class WorldsItem extends ClickableItem {

        @Override
        public CompletableFuture<Void> create() {
            return plugin.getWorldManager().getWorlds().thenAccept(worlds -> {
                String status = ADDON_WORLDS_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", addon == null ? 0 : worlds.stream().filter(w -> w.isAddonBanished(addon)).count())
                                        .build();

                setMaterial(XMaterial.GRASS_BLOCK);
                setName(ADDON_WORLDS_ITEM_NAME.get(plugin));
                setLore(ADDON_WORLDS_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }

    }

    public final class CropsItem extends ClickableItem {


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.WHEAT);
                setName(ADDON_CROP_SETTINGS_ITEM_NAME.get(plugin));
                setLore(ADDON_CROP_SETTINGS_ITEM_TIPS.builder(plugin).buildAsList());
            });
        }

    }


}
