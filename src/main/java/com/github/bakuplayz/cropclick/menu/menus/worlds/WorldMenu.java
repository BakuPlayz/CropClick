package com.github.bakuplayz.cropclick.menu.menus.worlds;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.settings.WorldsMenu;
import com.github.bakuplayz.cropclick.menu.states.WorldMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.worlds.FarmWorld;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class WorldMenu extends Menu {

    private final FarmWorld world;


    public WorldMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull FarmWorld world) {
        super(plugin, player, LanguageAPI.Menu.WORLD_TITLE);
        this.world = world;
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(20, getPlayersItem());
        inventory.setItem(22, getWorldItem());
        inventory.setItem(24, getAutofarmsItem());

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new WorldsMenu(plugin, player, WorldMenuState.SETTINGS));

        if (clicked.equals(getWorldItem())) {
            world.isBanished(!world.isBanished());
        }

        if (clicked.equals(getAutofarmsItem())) {
            world.allowsAutofarms(!world.allowsAutofarms());
        }

        if (clicked.equals(getPlayersItem())) {
            world.allowsPlayers(!world.allowsPlayers());
        }

        updateMenu();
    }


    /**
     * This function returns an ItemStack with the material of the player's head, with the name of the "Players", and the lore of
     * the item being the status of whether the world allows players.
     *
     * @return An ItemStack.
     */
    //TODO: Get as skull item with the player's head.
    private @NotNull ItemStack getPlayersItem() {
        return new ItemUtil(Material.PLAYER_HEAD)
                .setName(plugin, LanguageAPI.Menu.WORLD_PLAYERS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.WORLD_PLAYERS_ITEM_STATUS.get(plugin,
                        world.allowsPlayers()
                )).toItemStack();
    }


    /**
     * This function returns an ItemStack with the material of dispenser, with the name of the "Autofarms", and the lore of
     * the item being the status of whether the world allows autofarms.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getAutofarmsItem() {
        return new ItemUtil(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.WORLD_AUTOFARMS_ITEM_NAME)
                .setLore(LanguageAPI.Menu.WORLD_AUTOFARMS_ITEM_STATUS.get(plugin,
                        world.allowsAutofarms()
                )).toItemStack();
    }


    /**
     * It creates an ItemStack that represents the world.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getWorldItem() {
        String name = MessageUtils.beautify(world.getName(), true);
        
        return new ItemUtil(Material.GRASS_BLOCK)
                .setName(LanguageAPI.Menu.WORLD_WORLD_ITEM_NAME.get(plugin, name))
                .setMaterial(name.contains("End") ? Material.END_STONE : null)
                .setMaterial(name.contains("Nether") ? Material.NETHERRACK : null)
                .setLore(LanguageAPI.Menu.WORLD_WORLD_ITEM_STATUS.get(plugin, world.isBanished()))
                .toItemStack();
    }

}