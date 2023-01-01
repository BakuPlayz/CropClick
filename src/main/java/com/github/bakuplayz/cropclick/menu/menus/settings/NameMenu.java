package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.CropConfigSection;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SeedConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.crop.seeds.base.BaseSeed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
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
public final class NameMenu extends Menu {

    private final BaseCrop crop;
    private final boolean hasSeed;

    private final CropConfigSection cropSection;
    private final SeedConfigSection seedSection;

    private final Map<Character, String> colorCodes;


    public NameMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull BaseCrop crop) {
        super(plugin, player, LanguageAPI.Menu.NAME_TITLE);
        this.cropSection = plugin.getCropsConfig().getCropSection();
        this.seedSection = plugin.getCropsConfig().getSeedSection();
        this.colorCodes = getColorCodes();
        this.hasSeed = crop.hasSeed();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        if (hasSeed) {
            inventory.setItem(31, getSeedItem());
            inventory.setItem(13, getCropItem());
        } else {
            inventory.setItem(22, getCropItem());
        }

        inventory.setItem(47, getCodesItem(0));
        inventory.setItem(51, getCodesItem(11));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.NAME));

        if (clicked.equals(getCropItem())) {
            getNameChangeMenu(true).open(player);
        }

        if (!hasSeed) return;

        if (clicked.equals(getSeedItem())) {
            getNameChangeMenu(false).open(player);
        }
    }


    /**
     * It creates an ItemStack with the name of the crop, the drop value, and the enabled status.
     *
     * @return An ItemStack.
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
                .setMaterial(crop.isHarvestable() ? null : Material.RED_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * It creates an ItemStack with the name of seed crop, the drop value, and the enabled status.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getSeedItem() {
        BaseSeed seed = crop.getSeed();
        String name = MessageUtils.beautify(seed.getName(), false);
        String status = MessageUtils.getEnabledStatus(plugin, seed.isEnabled());

        return new ItemBuilder(seed.getMenuType())
                .setName(LanguageAPI.Menu.NAME_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.NAME_SEED_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.NAME_SEED_ITEM_DROP_NAME.get(plugin, getDropName(false))
                ))
                .setMaterial(seed.isEnabled() ? null : Material.RED_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * It creates a sign item with the color codes on it.
     *
     * @param start The starting index of the color codes to display.
     *
     * @return A new ItemStack with the material of a sign, with the name of the item being the color code item name, and
     * the lore being the color codes.
     */
    @SuppressWarnings("deprecation")
    private @NotNull ItemStack getCodesItem(int start) {
        return new ItemBuilder(Material.SIGN)
                .setName(plugin, LanguageAPI.Menu.NAME_COLOR_ITEM_NAME)
                .setLore(getCodesLore(start))
                .toItemStack();
    }


    /**
     * It creates an anvil menu that allows the player to change the name of the crop or seed.
     *
     * @param isCrop Whether the item is a crop or a seed.
     *
     * @return AnvilGUI.Builder.
     */
    private @NotNull AnvilGUI.Builder getNameChangeMenu(boolean isCrop) {
        String currentName = getDropName(isCrop);

        return new AnvilGUI.Builder()
                .text(ChatColor.stripColor(currentName))
                .itemLeft(isCrop ? getCropItem() : getSeedItem())
                .onComplete((player, text) -> {

                    if (isCrop) {
                        cropSection.setDropName(crop.getName(), text);
                    } else {
                        seedSection.setDropName(crop.getSeed().getName(), text);
                    }

                    return AnvilGUI.Response.close();
                }).onClose((player) -> {
                    String newName = getDropName(isCrop);

                    player.sendMessage(
                            currentName.equals(newName)
                            ? LanguageAPI.Menu.NAME_RESPONSE_UNCHANGED.get(plugin)
                            : LanguageAPI.Menu.NAME_RESPONSE_CHANGED.get(plugin, newName)
                    );
                }).plugin(plugin);
    }


    /**
     * If the crop is a crop, return the crop's drop name, otherwise return the seed's drop name.
     *
     * @param isCrop Whether the drop is a crop or a seed.
     *
     * @return The name of the drop.
     */
    private @NotNull String getDropName(boolean isCrop) {
        return isCrop
               ? cropSection.getDropName(crop.getName())
               : seedSection.getDropName(crop.getSeed().getName());
    }


    /**
     * It returns a map of all the chat colors and their corresponding color codes.
     *
     * @return A map of all the color codes.
     */
    private @NotNull Map<Character, String> getColorCodes() {
        return Arrays.stream(ChatColor.values())
                     .collect(Collectors.toMap(
                             ChatColor::getChar,
                             color -> MessageUtils.beautify(color.name(), true)
                     ));
    }


    /**
     * It takes a start index, and returns a list of 11 color codes, starting at the start index.
     *
     * @param start The starting index of the list of codes to display.
     *
     * @return A list of strings that are color codes.
     */
    private List<String> getCodesLore(int start) {
        return colorCodes.entrySet().stream()
                         .map(colorMap -> MessageUtils.colorize(
                                 LanguageAPI.Menu.NAME_COLOR_ITEM_CODE.get(
                                         plugin,
                                         colorMap.getKey(),
                                         colorMap.getKey(),
                                         colorMap.getValue())
                         ).replace("#", "&")) // Some wizardry formatting
                         .skip(start).limit(11)
                         .collect(Collectors.toList());
    }

}