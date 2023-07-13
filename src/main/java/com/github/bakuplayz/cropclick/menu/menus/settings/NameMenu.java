/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2023 BakuPlayz
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

package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * A class representing the Name menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class NameMenu extends BaseMenu {

    /**
     * A variable containing the {@link Crop selected crop}.
     */
    private final Crop crop;

    private final CropConfigSection cropSection;
    private final SeedConfigSection seedSection;

    /**
     * A variable containing all the color codes.
     */
    private final Map<Character, String> colorCodes;


    public NameMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.NAME_TITLE);
        this.cropSection = plugin.getCropsConfig().getCropSection();
        this.seedSection = plugin.getCropsConfig().getSeedSection();
        this.colorCodes = getColorCodes();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        if (crop.hasSeed()) {
            inventory.setItem(31, getSeedItem());
            inventory.setItem(13, getCropItem());
        } else {
            inventory.setItem(22, getCropItem());
        }

        inventory.setItem(47, getColorItem(0));
        inventory.setItem(51, getColorItem(11));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.NAME));

        if (clicked.equals(getCropItem())) {
            createChangeNameMenu(true).open(player);
        }

        if (!crop.hasSeed()) {
            return;
        }

        if (clicked.equals(getSeedItem())) {
            createChangeNameMenu(false).open(player);
        }
    }


    /**
     * Gets the crop {@link ItemStack item}.
     *
     * @return the crop item.
     */
    private @NotNull ItemStack getCropItem() {
        String name = MessageUtils.beautify(crop.getName(), false);
        String status = crop.isHarvestable()
                        ? LanguageAPI.Menu.CROP_STATUS_ENABLED.get(plugin)
                        : LanguageAPI.Menu.CROP_STATUS_DISABLED.get(plugin);

        return new ItemBuilder(crop.getMenuType())
                .setName(LanguageAPI.Menu.NAME_CROP_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.NAME_CROP_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.NAME_CROP_ITEM_DROP_NAME.get(plugin, getDropName(true))
                ))
                .setMaterial(!crop.isHarvestable(), Material.RED_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the seed {@link ItemStack item}.
     *
     * @return the seed item.
     */
    private @NotNull ItemStack getSeedItem() {
        Seed seed = crop.getSeed();

        assert seed != null; // Only here for the compiler.

        String name = MessageUtils.beautify(seed.getName(), false);
        String status = MessageUtils.getStatusMessage(plugin, seed.isEnabled());

        return new ItemBuilder(seed.getMenuType())
                .setName(LanguageAPI.Menu.NAME_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.NAME_SEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.NAME_SEED_ITEM_DROP_NAME.get(plugin, getDropName(false))
                ))
                .setMaterial(!seed.isEnabled(), Material.RED_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the color codes {@link ItemStack item}.
     *
     * @param startIndex the index at which to begin the looping.
     *
     * @return the color codes item.
     */
    @SuppressWarnings("deprecation")
    private @NotNull ItemStack getColorItem(int startIndex) {
        Material sign = VersionUtils.between(0.0, 13.9)
                        ? Material.LEGACY_SIGN
                        : Material.OAK_SIGN;

        return new ItemBuilder(sign)
                .setName(plugin, LanguageAPI.Menu.NAME_COLOR_ITEM_NAME)
                .setLore(getCodesAsLore(startIndex))
                .toItemStack();
    }


    /**
     * Creates the change name {@link AnvilGUI anvil menu}.
     *
     * @param isCrop true if a crop, otherwise false.
     *
     * @return the change name menu.
     */
    private @NotNull AnvilGUI.Builder createChangeNameMenu(boolean isCrop) {
        String currentName = getDropName(isCrop);

        return new AnvilGUI.Builder()
                .text(ChatColor.stripColor(currentName))
                .itemLeft(isCrop ? getCropItem() : getSeedItem())
                .onClick((player, stateSnapshot) -> {
                    if (isCrop) {
                        cropSection.setDropName(crop.getName(), stateSnapshot.getText());
                    } else {
                        assert crop.getSeed() != null; // Only here for the compiler.
                        seedSection.setDropName(crop.hasSeed() ? crop.getSeed().getName() : "", stateSnapshot.getText());
                    }

                    return AnvilGUI.Response.close();
                }).onClose((stateSnapshot) -> {
                    String newName = getDropName(isCrop);

                    stateSnapshot.getPlayer().sendMessage(
                            currentName.equals(newName)
                            ? LanguageAPI.Menu.NAME_RESPONSE_UNCHANGED.get(plugin)
                            : LanguageAPI.Menu.NAME_RESPONSE_CHANGED.get(plugin, newName)
                    );
                }).plugin(plugin);
    }


    /**
     * Gets the drop's name of either the {@link Crop} or {@link Seed}.
     *
     * @param isCrop true if a crop, otherwise false.
     *
     * @return the drop's name.
     */
    private @NotNull String getDropName(boolean isCrop) {
        if (isCrop) {
            return cropSection.getDropName(crop.getName());
        }
        assert crop.getSeed() != null; // Only here for the compiler.
        return seedSection.getDropName(crop.getSeed().getName());
    }


    /**
     * Gets all the {@link ChatColor#name() color codes}.
     *
     * @return color codes.
     */
    private @NotNull Map<Character, String> getColorCodes() {
        return Arrays.stream(ChatColor.values())
                     .collect(Collectors.toMap(
                             ChatColor::getChar,
                             color -> MessageUtils.beautify(color.name(), true)
                     ));
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
                         .map(colorMap -> MessageUtils.colorize(
                                 LanguageAPI.Menu.NAME_COLOR_ITEM_CODE.get(
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