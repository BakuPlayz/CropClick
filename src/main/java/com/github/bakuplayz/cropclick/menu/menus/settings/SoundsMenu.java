package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.SoundConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
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
 * @see BaseMenu
 * @since 2.0.0
 */
public final class SoundsMenu extends PaginatedMenu {

    /**
     * A variable containing the {@link Crop selected crop}.
     */
    private final Crop crop;

    /**
     * A variable containing all the names of the {@link Sound sounds}.
     */
    private final List<String> sounds;
    private final SoundConfigSection soundSection;


    public SoundsMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
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

        int index = indexOfSound(clicked);
        if (index == -1) {
            return;
        }

        new SoundMenu(
                plugin,
                player,
                crop,
                sounds.get(index)
        ).openMenu();
    }


    /**
     * Finds the index of the {@link Sound#name() sound's name} based on the {@link ItemStack clicked item}.
     *
     * @param clicked the item that was clicked.
     *
     * @return the index of the sound's name, otherwise -1.
     */
    private int indexOfSound(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * Creates a menu {@link ItemStack item} based on the {@link Sound#name() sound's name}.
     *
     * @param sound the name of the sound to base the item on.
     *
     * @return the created menu item.
     */
    private @NotNull ItemStack createMenuItem(@NotNull String sound) {
        boolean isEnabled = soundSection.isEnabled(
                crop.getName(),
                sound
        );
        String status = MessageUtils.getStatusMessage(plugin, isEnabled);
        String name = MessageUtils.beautify(sound, true);

        ItemBuilder item = new ItemBuilder(Material.NOTE_BLOCK)
                .setName(LanguageAPI.Menu.SOUNDS_ITEM_NAME.get(
                        plugin,
                        name,
                        status
                ));

        if (isEnabled) {
            item.setMaterial(Material.LIME_STAINED_GLASS_PANE)
                .setLore(LanguageAPI.Menu.SOUNDS_ITEM_ORDER.get(
                        plugin,
                        soundSection.getOrder(crop.getName(), sound)
                ));
        }

        return item.toItemStack();
    }


    /**
     * Gets all the {@link #sounds sound names} as {@link #menuItems menu items}.
     *
     * @return sounds as menu items.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return sounds.stream()
                     .map(this::createMenuItem)
                     .collect(Collectors.toList());
    }


    /**
     * Gets all the {@link Sound#name() sound names}.
     *
     * @return sound names.
     */
    private @NotNull List<String> getSounds() {
        return Arrays.stream(Sound.values())
                     .map(Sound::name)
                     .collect(Collectors.toList());
    }

}