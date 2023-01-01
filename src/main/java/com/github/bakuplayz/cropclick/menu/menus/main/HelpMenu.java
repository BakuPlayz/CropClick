package com.github.bakuplayz.cropclick.menu.menus.main;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.commands.Subcommand;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
import com.github.bakuplayz.cropclick.menu.menus.MainMenu;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MenuUtil;
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
public final class HelpMenu extends Menu {

    private final boolean isRedirected;

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

        if (MenuUtil.isAir(clicked)) {
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
     * It creates an ItemStack with a book as the material, sets the name to the subcommand's name, and sets the lore to
     * the subcommand's description, permission, and usage.
     *
     * @param cmd The subcommand to get the item for.
     *
     * @return An ItemStack representing a command.
     */
    private @NotNull ItemStack getSubCommandItem(@NotNull Subcommand cmd) {
        return new ItemBuilder(Material.BOOK)
                .setName(LanguageAPI.Menu.HELP_ITEM_NAME.get(plugin, cmd.getName()))
                .setLore(LanguageAPI.Menu.HELP_ITEM_DESCRIPTION.get(plugin, cmd.getDescription()),
                        LanguageAPI.Menu.HELP_ITEM_PERMISSION.get(plugin, cmd.getPermission()),
                        LanguageAPI.Menu.HELP_ITEM_USAGE.get(plugin, cmd.getUsage()))
                .toItemStack();
    }


    /**
     * It creates an ItemStack with a book that shows how to use the default command.
     *
     * @return An ItemStack representing the default command.
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