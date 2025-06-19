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
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Abstract Crops menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public abstract class AbstractCropsMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, Crop> {

    private final ItemLoreSupplier loreSupplier;


    public AbstractCropsMenu(@NotNull CropClick plugin, @NotNull ItemLoreSupplier supplier) {
        super(CROPS_TITLE.getTitle(plugin), plugin);
        this.loreSupplier = supplier;
    }


    @Override
    public List<Crop> getPaginationItems() {
        return plugin.getCropManager().getRegisteredCrops();
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull Crop crop, int position) {
        return new CropItem(crop);
    }


    @Override
    public BasicPaginatedStateHandler createStateHandler(@NotNull Player player) {
        return new BasicPaginatedStateHandler(this);
    }


    @FunctionalInterface
    public interface ItemLoreSupplier {

        List<String> getLore(@NotNull Crop crop);

    }

    @AllArgsConstructor
    private class CropItem extends ClickableItem {

        private final Crop crop;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String name = Messages.beautify(crop.getName(), false);
                String status = crop.isHarvestable()
                                        ? CROPS_STATUS_ENABLED.get(plugin)
                                        : CROPS_STATUS_DISABLED.get(plugin);

                setMaterial(crop.getMenuType());
                setLore(loreSupplier.getLore(crop));
                setName(CROPS_ITEM_NAME.get(plugin, name, status));
                setMaterial(!crop.isHarvestable(), XMaterial.GRAY_STAINED_GLASS_PANE);
            });
        }
    }

}
