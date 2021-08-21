package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public abstract class PaginatedMenu extends Menu {

    protected int page = 0;
    protected int itemIndex = 0;
    protected final int MAX_ITEMS_PER_PAGE = 45;

    public PaginatedMenu(@NotNull Player player,
                         @NotNull CropClick plugin) {
        super(player, plugin);
    }
}
