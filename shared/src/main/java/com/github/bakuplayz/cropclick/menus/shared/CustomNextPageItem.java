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
package com.github.bakuplayz.cropclick.menus.shared;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.spigotspin.SpigotSpin;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginationContext;
import com.github.bakuplayz.spigotspin.menu.items.paginated.NextPageItem;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.GENERAL_NEXT_PAGE_ITEM_NAME;

/**
 * A class representing the {@link NextPageItem} scoped for
 * CropClick menu usage.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class CustomNextPageItem<S extends PaginatedMenuState> extends NextPageItem<S> {

    public CustomNextPageItem(@NotNull PaginationContext context) {
        super(context);
    }


    @Override
    public CompletableFuture<Void> create() {
        return super.create().thenAccept(s -> setName(
                GENERAL_NEXT_PAGE_ITEM_NAME.get((CropClick) SpigotSpin.Plugin.REF.getPlugin()))
        );
    }

}
