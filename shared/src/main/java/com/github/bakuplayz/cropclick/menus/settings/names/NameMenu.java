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
package com.github.bakuplayz.cropclick.menus.settings.names;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig;
import com.github.bakuplayz.cropclick.configurations.config.CropsConfig.ConfigurationKey;
import com.github.bakuplayz.cropclick.crops.Crop;
import com.github.bakuplayz.cropclick.crops.seeds.Seed;
import com.github.bakuplayz.cropclick.menus.common.AnvilMenuFactory;
import com.github.bakuplayz.cropclick.menus.common.AnvilMenuFactory.ValueSetter;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractPlainMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Name menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class NameMenu extends AbstractPlainMenu {


    private final Crop crop;

    private final CropClick plugin;

    private final CropsConfig cropsConfig;


    public NameMenu(@NotNull CropClick plugin, @NotNull Crop crop) {
        super(NAME_TITLE.getTitle(plugin));
        this.cropsConfig = plugin.getConfigManager().getCropsConfig();
        this.plugin = plugin;
        this.crop = crop;
    }


    @Override
    public void setItems() {
        if (crop.hasSeed()) {
            setItem(13, new CropItem(), (item, player) -> {
                String cropName = crop.getName();
                ValueSetter setter = (text) -> cropsConfig.set(ConfigurationKey.CROP_DROP_NAME, text, cropName);
                AnvilMenuFactory.createMenu(plugin, item, cropsConfig.getStringOrDefault(ConfigurationKey.CROP_DROP_NAME, cropName, cropName), setter).open(player);
            });
            setItem(31, new SeedItem(), (item, player) -> {
                String seedName = crop.getSeed().getName();
                ValueSetter setter = (text) -> cropsConfig.set(ConfigurationKey.SEED_DROP_NAME, text, seedName);
                AnvilMenuFactory.createMenu(plugin, item, cropsConfig.getStringOrDefault(ConfigurationKey.SEED_DROP_NAME, seedName, seedName), setter).open(player);
            });
        } else {
            setItem(22, new CropItem(), (item, player) -> {
                String cropName = crop.getName();
                ValueSetter setter = (text) -> cropsConfig.set(ConfigurationKey.CROP_DROP_NAME, text, cropName);
                AnvilMenuFactory.createMenu(plugin, item, cropsConfig.getStringOrDefault(ConfigurationKey.CROP_DROP_NAME, cropName, cropName), setter).open(player);
            });
        }

        setItem(47, new ColorItem(plugin, 0));
        setItem(49, new CustomBackItem(plugin));
        setItem(51, new ColorItem(plugin, 11));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @AllArgsConstructor
    private static final class ColorItem extends Item {

        private final static Map<Character, String> colorCodes = getColorCodes();

        private final CropClick plugin;

        private final int startIndex;


        /**
         * Gets all the {@link ChatColor#name() color codes}.
         *
         * @return color codes.
         */
        @NotNull
        private static Map<Character, String> getColorCodes() {
            return Arrays.stream(ChatColor.values())
                           .collect(Collectors.toMap(
                                   ChatColor::getChar,
                                   color -> Messages.beautify(color.name(), true)
                           ));
        }


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.OAK_SIGN);
                setLore(getCodesAsLore(startIndex));
                setName(NAME_COLOR_ITEM_NAME.get(plugin));
            });
        }


        /**
         * Gets color codes in lore format.
         *
         * @param startIndex the index at which to begin the looping.
         *
         * @return color codes as a lore.
         */
        private List<String> getCodesAsLore(int startIndex) {
            return colorCodes.entrySet().stream()
                           .map(colorMap -> Messages.colorize(
                                   NAME_COLOR_ITEM_CODE.get(
                                           plugin,
                                           colorMap.getKey(),
                                           colorMap.getKey(),
                                           colorMap.getValue())
                           ).replace("#", "&")) // Some wizardry formatting
                           .skip(startIndex)
                           .limit(11)
                           .collect(Collectors.toList());
        }

    }

    private final class CropItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String name = Messages.beautify(crop.getName(), false);
                String status = crop.isHarvestable()
                                        ? CROP_STATUS_ENABLED.get(plugin)
                                        : CROP_STATUS_DISABLED.get(plugin);
                String currentName = cropsConfig.getStringOrDefault(ConfigurationKey.CROP_DROP_NAME, name, crop.getName());

                setMaterial(crop.getMenuType());
                setMaterial(!crop.isHarvestable(), XMaterial.RED_STAINED_GLASS_PANE);
                setName(NAME_CROP_ITEM_NAME.get(plugin, name, status));
                setLore(NAME_CROP_ITEM_TIPS.getAsAppendList(plugin,
                        NAME_CROP_ITEM_DROP_NAME.get(plugin, currentName.isEmpty() ? name : currentName))
                );
            });
        }

    }

    private final class SeedItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                Seed seed = crop.getSeed();
                String name = Messages.beautify(seed.getName(), false);
                String status = Messages.getStatusMessage(plugin, seed.isEnabled());
                String currentName = cropsConfig.getStringOrDefault(ConfigurationKey.SEED_DROP_NAME, name, seed.getName());

                setMaterial(seed.getMenuType());
                setMaterial(!seed.isEnabled(), XMaterial.RED_STAINED_GLASS_PANE);
                setName(NAME_SEED_ITEM_NAME.get(plugin, name, status));
                setLore(NAME_SEED_ITEM_TIPS.getAsAppendList(plugin,
                        NAME_SEED_ITEM_DROP_NAME.get(plugin, currentName.isEmpty() ? name : currentName))
                );
            });
        }

    }

}
