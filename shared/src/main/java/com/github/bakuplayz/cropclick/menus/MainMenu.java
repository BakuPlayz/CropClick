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

package com.github.bakuplayz.cropclick.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPlainMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Main menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class MainMenu extends AbstractPlainMenu {

    private final CropClick plugin;


    public MainMenu(@NotNull CropClick plugin) {
        super(MAIN_TITLE.getTitle(plugin));
        this.plugin = plugin;
    }


    @Override
    public void setItems() {
        setItem(21, new CropsItem(), (item, player) -> new CropsMenu(plugin).open(player));
        setItem(23, new AutofarmsDashboardItem(), (item, player) -> new AutofarmsDashboardMenu(plugin, true).open(player));
        setItem(44, new AddonsItem(), (item, player) -> new AddonsMenu(plugin).open(player));
        setItem(45, new UpdatesItem(), (item, player) -> new UpdatesMenu(plugin).join(player, UpdatesMenu.IDENTIFIER));
        setItem(49, new HelpItem(), (item, player) -> new HelpMenu(plugin, true).open(player));
        setItem(53, new SettingsItem(), (item, player) -> new SettingsMenu(plugin, true).open(player));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    private final class CropsItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.WHEAT);
                setName(MAIN_CROPS_ITEM_NAME.get(plugin));
                setLore(MAIN_CROPS_ITEM_TIPS.getAsAppendList(plugin,
                        MAIN_CROPS_ITEM_STATUS.get(plugin, plugin.getCropManager().getAmountOfCrops()))
                );
            });
        }

    }

    private final class AutofarmsDashboardItem extends ClickableItem {

        @Override
        public CompletableFuture<Void> create() {
            return plugin.getAutofarmManager().getAmountOfFarms().thenAccept(farms -> {
                setMaterial(XMaterial.DISPENSER);
                setName(MAIN_AUTOFARMS_DASHBOARD_ITEM_NAME.get(plugin));
                setLore(MAIN_AUTOFARMS_DASHBOARD_ITEM_TIPS.getAsAppendList(plugin,
                        MAIN_AUTOFARMS_DASHBOARD_ITEM_STATUS.get(plugin, farms))
                );
            });
        }

    }

    private final class UpdatesItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.ANVIL);
                setName(MAIN_UPDATES_ITEM_NAME.get(plugin));
                setLore(MAIN_UPDATES_ITEM_TIPS.getAsAppendList(plugin,
                        MAIN_UPDATES_ITEM_STATE.get(plugin, plugin.getUpdateManager().getInfo().getMessage()))
                );
            });
        }

    }

    private final class HelpItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.BOOK);
                setName(MAIN_HELP_ITEM_NAME.get(plugin));
                setLore(MAIN_HELP_ITEM_TIPS.getAsAppendList(plugin,
                        MAIN_HELP_ITEM_STATUS.get(plugin, plugin.getCommandManager().getAmountOfCommands()))
                );
            });
        }

    }

    private final class AddonsItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.ENDER_CHEST);
                setName(MAIN_ADDONS_ITEM_NAME.get(plugin));
                setLore(MAIN_ADDONS_ITEM_TIPS.getAsAppendList(plugin,
                        MAIN_ADDONS_ITEM_STATUS.get(plugin, plugin.getAddonManager().getAmountOfAddons())));
            });
        }

    }

    private final class SettingsItem extends ClickableItem {

        private final static int AMOUNT_OF_SETTINGS = 6;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.CHEST);
                setName(MAIN_SETTINGS_ITEM_NAME.get(plugin));
                setLore(MAIN_SETTINGS_ITEM_TIPS.getAsAppendList(plugin,
                        MAIN_SETTINGS_ITEM_STATUS.get(plugin, AMOUNT_OF_SETTINGS))
                );
            });
        }

    }

}
