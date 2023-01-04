package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MenuUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * A class representing the Help menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class HelpMenu extends BaseMenu {

    /**
     * A variable checking if the menu request to {@link HelpMenu} was a redirect.
     */
    private final boolean isRedirected;

    /**
     * A variable containing all the {@link Subcommand commands}.
     */
    private final List<Subcommand> commands;


    public HelpMenu(@NotNull CropClick plugin, @NotNull Player player, boolean isRedirected) {
        super(plugin, player, LanguageAPI.Menu.HELP_TITLE);
        this.commands = plugin.getCommandManager().getCommands();
        this.isRedirected = isRedirected;
    }


    @Override
    public void setMenuItems() {
        inventory.addItem(getDefaultCommandItem());

        for (Subcommand subCommand : commands) {
            inventory.addItem(getSubCommandItem(subCommand));
        }

        if (isRedirected) {
            setBackItem();
        }
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        if (MenuUtils.isAir(clicked)) {
            return;
        }

        if (isRedirected) {
            handleBack(clicked, new MainMenu(plugin, player));
        }

        if (event.getSlot() > 0) {
            player.closeInventory();
            player.performCommand(
                    commands.get(event.getSlot() - 1).getUsage()
            );
            return;
        }

        player.closeInventory();
        player.performCommand("crop");
    }


    /**
     * Gets the subcommand {@link ItemStack item}.
     *
     * @param command the subcommand to base the item on.
     *
     * @return the subcommand item.
     */
    private @NotNull ItemStack getSubCommandItem(@NotNull Subcommand command) {
        return new ItemBuilder(Material.BOOK)
                .setName(LanguageAPI.Menu.HELP_ITEM_NAME.get(plugin, command.getName()))
                .setLore(LanguageAPI.Menu.HELP_ITEM_DESCRIPTION.get(plugin, command.getDescription()),
                        LanguageAPI.Menu.HELP_ITEM_PERMISSION.get(plugin, command.getPermission()),
                        LanguageAPI.Menu.HELP_ITEM_USAGE.get(plugin, command.getUsage()))
                .toItemStack();
    }


    /**
     * Gets the default command {@link ItemStack item}.
     *
     * @return the default command item.
     */
    private @NotNull ItemStack getDefaultCommandItem() {
        String description = LanguageAPI.Command.DEFAULT_DESCRIPTION.get(plugin);
        return new ItemBuilder(Material.BOOK)
                .setName(LanguageAPI.Menu.HELP_ITEM_NAME.get(plugin, ""))
                .setLore(LanguageAPI.Menu.HELP_ITEM_DESCRIPTION.get(plugin, description),
                        LanguageAPI.Menu.HELP_ITEM_PERMISSION.get(plugin, "cropclick.command.general"),
                        LanguageAPI.Menu.HELP_ITEM_USAGE.get(plugin, "cropclick"))
                .toItemStack();
    }

}