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
package com.github.bakuplayz.cropclick.menus.common;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.NAME_RESPONSE_CHANGED;
import static com.github.bakuplayz.cropclick.common.Languages.Menu.NAME_RESPONSE_UNCHANGED;

public final class AnvilMenuFactory {

    public static AnvilGUI.Builder createMenu(@NotNull CropClick plugin, @NotNull Item item, @NotNull String currentValue, @NotNull ValueSetter setter) {
        return new AnvilGUI.Builder()
                       .itemLeft(item.asItemStack())
                       .text(ChatColor.stripColor(currentValue))
                       .itemOutput(item.asItemStack())
                       .onClick((player, stateSnapshot) -> {
                           setter.setValue(stateSnapshot.getText());
                           return Collections.singletonList(AnvilGUI.ResponseAction.close());
                       })
                       .onClose((stateSnapshot) -> {
                           Player player = stateSnapshot.getPlayer();

                           if (currentValue.equals(stateSnapshot.getText())) {
                               NAME_RESPONSE_UNCHANGED.builder(plugin)
                                       .wrap(false)
                                       .sendTo(player);
                               return;
                           }

                           NAME_RESPONSE_CHANGED.builder(plugin)
                                   .replace("%name%", Messages.colorize(stateSnapshot.getText()))
                                   .wrap(false).sendTo(player);
                       }).plugin(plugin);
    }


    @FunctionalInterface
    public interface ValueSetter {

        void setValue(@NotNull String value);

    }

}
