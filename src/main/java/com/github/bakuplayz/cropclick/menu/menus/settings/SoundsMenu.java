package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.menus.sounds.SoundMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
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
 * A class representing the Sounds menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class SoundsMenu extends PaginatedMenu {

    private final BaseCrop crop;

    private final List<String> sounds;
    private final SoundConfigSection soundSection;


    public SoundsMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull BaseCrop crop) {
        super(plugin, player, LanguageAPI.Menu.SOUNDS_TITLE);
        this.soundSection = plugin.getCropsConfig().getSoundSection();
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

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.SOUNDS));
        handlePagination(clicked);

        int index = getIndexOfSound(clicked);
        if (index == -1) {
            return;
        }

        new SoundMenu(
                plugin,
                player,
                crop,
                sounds.get(index)
        ).open();
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
     * @return The index of the sound in the {@link #menuItems menuItems list}.
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
        boolean isEnabled = soundSection.isEnabled(
                crop.getName(),
                sound
        );
        String status = MessageUtils.getEnabledStatus(plugin, isEnabled);
        String name = MessageUtils.beautify(sound, true);

        ItemBuilder item = new ItemBuilder(Material.NOTE_BLOCK)
                .setName(LanguageAPI.Menu.SOUNDS_ITEM_NAME.get(
                        plugin,
                        name,
                        status
                ));

        if (isEnabled) {
            item.setMaterial(Material.STAINED_GLASS_PANE)
                .setLore(LanguageAPI.Menu.SOUNDS_ITEM_ORDER.get(
                        plugin,
                        getOrderOfSound(sound)
                ))
                .setDamage(5);
        }

        return item.toItemStack();
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


    /**
     * It returns the index of the sound in the list of sounds for the crop.
     *
     * @param sound The name of the sound to play.
     *
     * @return The index of the sound in the list of sounds for the crop.
     */
    private int getOrderOfSound(@NotNull String sound) {
        return soundSection.getOrder(crop.getName(), sound);
    }

}