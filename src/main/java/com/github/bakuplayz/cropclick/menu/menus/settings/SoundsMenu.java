package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Menu
 * @since 1.6.0
 */
public final class SoundsMenu extends PaginatedMenu {

    private final Crop crop;
    private final CropsConfig cropsConfig;

    private final List<String> sounds;


    public SoundsMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.SOUNDS_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.sounds = getSounds();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        this.menuItems = getMenuItems();

        setPaginatedItems();
        setPageItems();
        setBackItems();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.SOUNDS));
        handlePagination(clicked);

        int index = getIndexOfSound(clicked);
        if (index == -1) {
            return;
        }

        String sound = sounds.get(index);
        cropsConfig.toggleSoundCrop(crop.getName(), sound);

        updateMenu();
    }


    /**
     * "Get the index of the sound item that was clicked on."
     * <p>
     * The first thing we do is create a stream of all the items in the menu. Then we filter the stream to only contain the
     * item that was clicked on. Then we map the stream to only contain the index of the item that was clicked on. Finally,
     * we find the first item in the stream and return it. If there is no item in the stream, we return -1.
     * </p>
     *
     * @param clicked The item that was clicked.
     *
     * @return The index of the sound in the menuItems list.
     */
    private int getIndexOfSound(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * It creates an item for the menu that represents a sound.
     *
     * @param sound The sound to get the item for.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getMenuItem(@NotNull String sound) {
        String soundName = MessageUtils.beautify(sound, true);
        String soundStatus = MessageUtils.getEnabledStatus(
                plugin,
                cropsConfig.getCropSounds(crop.getName()).contains(sound)
        );

        return new ItemUtil(Material.NOTE_BLOCK)
                .setName(LanguageAPI.Menu.SOUNDS_ITEM_NAME.get(plugin, soundName))
                .setLore(LanguageAPI.Menu.SOUNDS_ITEM_STATUS.get(plugin, soundStatus))
                .toItemStack();
    }


    /**
     * Get a list of menu items by mapping each sound to a menu item.
     *
     * @return A list of ItemStacks.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return sounds.stream()
                     .map(this::getMenuItem)
                     .collect(Collectors.toList());
    }


    /**
     * Get a list of all the sound names.
     *
     * @return A list of all the sounds in the Sound enum.
     */
    private @NotNull List<String> getSounds() {
        return Arrays.stream(Sound.values())
                     .map(Sound::name)
                     .collect(Collectors.toList());
    }

}