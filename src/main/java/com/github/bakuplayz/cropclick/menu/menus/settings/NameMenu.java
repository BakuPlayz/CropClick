package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.CocoaBean;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.crop.seeds.base.Seed;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class NameMenu extends Menu {

    private final Crop crop;
    private final boolean hasSeed;

    private final CropsConfig cropsConfig;

    private final Map<Character, String> colorCodes;


    public NameMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.NAME_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
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
        String status = MessageUtils.getEnabledStatus(plugin, crop.isHarvestable());

        return new ItemUtil(crop.getMenuType())
                .setName(LanguageAPI.Menu.NAME_CROP_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.NAME_CROP_DROP_NAME.get(plugin, getDropName(true)))
                .setDamage(crop instanceof CocoaBean ? 3 : -1)
                .setDamage(crop.isHarvestable() ? -1 : 15)
                .setMaterial(crop.isHarvestable() ? null : Material.STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * It creates an ItemStack with the name of seed crop, the drop value, and the enabled status.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getSeedItem() {
        Seed seed = crop.getSeed();
        String name = MessageUtils.beautify(seed.getName(), false);
        String status = MessageUtils.getEnabledStatus(plugin, seed.isEnabled());

        return new ItemUtil(seed.getMenuType())
                .setName(LanguageAPI.Menu.NAME_SEED_ITEM_NAME.get(plugin, name, status))
                .setLore(LanguageAPI.Menu.NAME_SEED_DROP_NAME.get(plugin, getDropName(false)))
                .setMaterial(seed.isEnabled() ? null : Material.STAINED_GLASS_PANE)
                .setDamage(seed.isEnabled() ? -1 : 15).toItemStack();
    }


    /**
     * It creates a sign item with the color codes on it.
     *
     * @param start The starting index of the color codes to display.
     *
     * @return A new ItemStack with the material of a sign, with the name of the item being the color code item name, and
     * the lore being the color codes.
     */
    // TODO: Make more readable
    private @NotNull ItemStack getCodesItem(int start) {
        return new ItemUtil(Material.SIGN)
                .setName(plugin, LanguageAPI.Menu.NAME_COLOR_ITEM_NAME)
                .setLore(colorCodes.entrySet().stream()
                                   .map(colorMap -> MessageUtils.colorize(
                                           LanguageAPI.Menu.NAME_COLOR_ITEM_CODE.get(
                                                   plugin,
                                                   colorMap.getKey(),
                                                   colorMap.getKey(),
                                                   colorMap.getValue())
                                   ).replace("#", "&")) // Some wizardry formatting
                                   .skip(start).limit(11)
                                   .collect(Collectors.toList())
                ).toItemStack();
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
                        cropsConfig.setCropDropName(crop.getName(), text);
                        return AnvilGUI.Response.close();
                    }
                    cropsConfig.setSeedDropName(crop.getSeed().getName(), text);
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
               ? cropsConfig.getCropDropName(crop.getName())
               : cropsConfig.getSeedDropName(crop.getSeed().getName());
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

}