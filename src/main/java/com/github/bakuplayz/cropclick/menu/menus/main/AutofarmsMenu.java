package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.menu.menus.links.DispenserLinkMenu;
import com.github.bakuplayz.cropclick.menu.states.AutofarmsMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.PermissionUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;


/**
 * A class representing the Autofarms menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class AutofarmsMenu extends PaginatedMenu {

    /**
     * A variable containing all the registered {@link Autofarm autofarms}.
     */
    private final List<Autofarm> autofarms;

    /**
     * A variable containing the state or menu to return to when clicking the {@link #getBackItem() back item}.
     */
    private final AutofarmsMenuState menuState;


    public AutofarmsMenu(@NotNull CropClick plugin, @NotNull Player player, AutofarmsMenuState state) {
        super(plugin, player, LanguageAPI.Menu.AUTOFARMS_TITLE);
        this.autofarms = getAutofarms(plugin, player);
        this.menuItems = getMenuItems();
        this.menuState = state;
    }


    @Override
    public void setMenuItems() {
        setPaginatedItems();
        setPageItems();

        if (menuState == AutofarmsMenuState.MENU) {
            setBackItems();
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handlePagination(clicked);

        if (menuState == AutofarmsMenuState.MENU) {
            handleBack(clicked, new MainMenu(plugin, player));
            return;
        }

        int index = indexOfFarm(clicked);
        if (index == -1) {
            return;
        }

        Autofarm autofarm = autofarms.get(index);
        Location dispenser = autofarm.getDispenserLocation();
        new DispenserLinkMenu(
                plugin,
                player,
                dispenser.getBlock(),
                autofarm,
                menuState
        ).openMenu();
    }


    /**
     * Finds the index of the {@link Autofarm autofarm} based on the {@link ItemStack clicked item}.
     *
     * @param clicked the item that was clicked.
     *
     * @return the index of the autofarm, otherwise -1.
     */
    private int indexOfFarm(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    /**
     * Creates a menu {@link ItemStack item} based on the {@link Autofarm provided autofarm}.
     *
     * @param autofarm the autofarm to base the item on.
     *
     * @return the created menu item.
     */
    private @NotNull ItemStack createMenuItem(@NotNull Autofarm autofarm) {
        String status = autofarm.isEnabled()
                        ? LanguageAPI.Menu.GENERAL_ENABLED_STATUS.get(plugin)
                        : LanguageAPI.Menu.GENERAL_DISABLED_STATUS.get(plugin);
        OfflinePlayer player = Bukkit.getOfflinePlayer(autofarm.getOwnerID());

        return new ItemBuilder(Material.DISPENSER)
                .setName(LanguageAPI.Menu.AUTOFARMS_ITEM_NAME.get(plugin, autofarm.getShortenedID(), status))
                .setLore(LanguageAPI.Menu.AUTOFARMS_ITEM_OWNER.get(plugin,
                        player.getUniqueId().equals(Autofarm.UNKNOWN_OWNER)
                        ? LanguageAPI.Menu.AUTOFARMS_ITEM_OWNER_UNCLAIMED.get(plugin)
                        : player.getName()
                ))
                .toItemStack();
    }


    /**
     * Gets all the {@link #autofarms} as {@link #menuItems menu items}.
     *
     * @return autofarms as menu items.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return autofarms.stream()
                        .map(this::createMenuItem)
                        .collect(Collectors.toList());
    }


    /**
     * Gets all the registered {@link Autofarm autofarms}.
     *
     * @return registered autofarms.
     */
    private List<Autofarm> getAutofarms(@NotNull CropClick plugin, @NotNull Player player) {
        return plugin.getAutofarmManager().getAutofarms().stream()
                     .filter(autofarm -> PermissionUtils.canUnlinkOthersFarm(player, autofarm))
                     .collect(Collectors.toList());
    }

}