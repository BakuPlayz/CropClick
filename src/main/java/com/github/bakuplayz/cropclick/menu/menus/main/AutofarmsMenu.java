package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
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
 * @see Menu
 * @since 2.0.0
 */
public final class AutofarmsMenu extends PaginatedMenu {

    private final List<Autofarm> autofarms;

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

        if (menuState == AutofarmsMenuState.MENU_REDIRECT) {
            setBackItems();
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handlePagination(clicked);

        if (menuState == AutofarmsMenuState.MENU_REDIRECT) {
            handleBack(clicked, new MainMenu(plugin, player));
        }

        int index = getIndexOfFarm(clicked);
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
        ).open();
    }


    private int getIndexOfFarm(@NotNull ItemStack clicked) {
        return menuItems.stream()
                        .filter(clicked::equals)
                        .mapToInt(item -> menuItems.indexOf(item))
                        .findFirst()
                        .orElse(-1);
    }


    @SuppressWarnings("ConstantConditions")
    private @NotNull ItemStack getMenuItem(@NotNull Autofarm farm) {
        String status = farm.isEnabled()
                        ? LanguageAPI.Menu.GENERAL_ENABLED_STATUS.get(plugin)
                        : LanguageAPI.Menu.GENERAL_DISABLED_STATUS.get(plugin);
        OfflinePlayer player = Bukkit.getOfflinePlayer(farm.getOwnerID());

        return new ItemBuilder(Material.DISPENSER)
                .setName(LanguageAPI.Menu.AUTOFARMS_ITEM_NAME.get(plugin, farm.getShortenedID(), status))
                .setLore(LanguageAPI.Menu.AUTOFARMS_ITEM_OWNER.get(plugin,
                        player.getUniqueId().equals(Autofarm.UNKNOWN_OWNER)
                        ? LanguageAPI.Menu.AUTOFARMS_ITEM_OWNER_UNCLAIMED.get(plugin)
                        : player.getName()
                ))
                .toItemStack();
    }


    protected @NotNull List<ItemStack> getMenuItems() {
        return autofarms.stream()
                        .map(this::getMenuItem)
                        .collect(Collectors.toList());
    }


    @NotNull
    private List<Autofarm> getAutofarms(@NotNull CropClick plugin, @NotNull Player player) {
        return plugin.getAutofarmManager().getAutofarms().stream()
                     .filter(autofarm -> PermissionUtils.canUnlinkOthersFarm(player, autofarm))
                     .collect(Collectors.toList());
    }

}