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

import com.cryptomorin.xseries.particles.XParticle;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Particles menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class ParticlesMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, String> {

    private final Crop crop;

    private final CropsConfig config;


    public ParticlesMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(SOUNDS_TITLE.getTitle(plugin), plugin);
        this.config = plugin.getConfigManager().getCropsConfig();
        this.crop = crop;
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler(@NotNull Player player) {
        return new BasicPaginatedStateHandler(this);
    }


    @Override
    public List<String> getPaginationItems() {
        return Arrays.stream(XParticle.values())
                       .filter(XParticle::isSupported)
                       .map(XParticle::name)
                       .collect(Collectors.toList());
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull String particle, int position) {
        return new ParticleItem(particle);
    }


    @NotNull
    @Override
    public ItemAction getPaginatedItemAction(@NotNull String particle, int position) {
        return (item, player) -> new ParticleMenu(plugin, crop, particle).open(player);
    }


    private class ParticleItem extends ClickableItem {

        private final String particle;

        private final int order;

        private final boolean isEnabled;


        public ParticleItem(@NotNull String particle) {
            this.order = config.getParticleOrder(crop, particle);
            this.isEnabled = order != -1;
            this.particle = particle;
        }


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = Messages.getStatusMessage(plugin, isEnabled);
                String name = Messages.beautify(particle, true);

                setMaterial(XMaterial.FIREWORK_ROCKET);
                setName(PARTICLES_ITEM_NAME.get(plugin, name, status));
                setMaterial(isEnabled, XMaterial.LIME_STAINED_GLASS_PANE);

                if (isEnabled) {
                    setLore(PARTICLES_ITEM_ORDER.get(plugin, order));
                }
            });
        }

    }

}
