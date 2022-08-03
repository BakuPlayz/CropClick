package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class PreviewMenu extends Menu {

    public PreviewMenu(@NotNull CropClick plugin,
                       @NotNull Player player,
                       @NotNull LanguageAPI.Menu menuTitle,
                       @NotNull String titleType) {
        super(plugin, player, menuTitle, titleType);
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        event.setCancelled(true);
    }

}