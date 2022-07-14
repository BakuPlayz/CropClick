package com.github.bakuplayz.cropclick.menu.menus.interacts;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class ContainerMenu extends Menu {

    private final Block block;
    private final Autofarm autofarm;
    private final Container container;


    public ContainerMenu(@NotNull Player player, Container container, @NotNull Block block, @NotNull CropClick plugin) {
        super(player, plugin, LanguageAPI.Menu.CONTAINER_TITLE);
        this.autofarm = plugin.getAutofarmManager().findAutofarm(block);
        this.container = container;
        this.block = block;
    }

    @Override
    public void setMenuItems() {
        inventory.setItem(13, getInformationItem());

        inventory.setItem(29, getCropItem());
        inventory.setItem(31, getDispenserItem());
        inventory.setItem(33, getContainerItem());

        for (int i = 0; i < 54; ++i) {
            boolean isEdge = i % 9 == 0 ^ i % 9 == 8;
            boolean isBottom = i > (54 - 9);
            if (isEdge || isBottom)
                inventory.setItem(i, getGlassItem());
        }
    }

    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();
    }

    private @NotNull ItemStack getInformationItem() {
        return new ItemUtil(Material.ITEM_FRAME)
                .setName(plugin, LanguageAPI.Menu.CONTAINER_INFO_NAME)
                .toItemStack();
    }

    private @NotNull ItemStack getCropItem() {
        Location loc = autofarm.getCropLocation();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        return new ItemUtil(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.CONTAINER_CROP_NAME)
                .toItemStack();
    }

    private @NotNull ItemStack getDispenserItem() {
        return new ItemUtil(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.CONTAINER_DISPENSER_NAME)
                .toItemStack();
    }

    private @NotNull ItemStack getContainerItem() {
        return new ItemUtil(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.CONTAINER_CONTAINER_NAME)
                .toItemStack();
    }

    private @NotNull ItemStack getGlassItem() {
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setDamage(15)
                .toItemStack();
    }

}
