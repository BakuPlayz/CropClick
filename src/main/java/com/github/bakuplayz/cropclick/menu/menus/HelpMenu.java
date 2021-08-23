package com.github.bakuplayz.cropclick.menu.menus;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.github.bakuplayz.cropclick.commands.SubCommand;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class HelpMenu extends Menu {

    private final List<SubCommand> subCommands;

    public HelpMenu(final @NotNull Player player,
                    final @NotNull CropClick plugin) {
        super(player, plugin, LanguageAPI.Menu.HELP_TITLE);
        this.subCommands = plugin.getCommandManager().getSubCommands();
    }

    @Override
    public void setMenuItems() {
        inventory.addItem(getDefaultCommandItem());

        for (SubCommand subCommand : subCommands) {
            inventory.addItem(getSubCommandItem(subCommand));
        }
    }

    @Override
    public void handleMenu(final @NotNull InventoryClickEvent event) {
        if (event.getSlot() > 0) {
            player.closeInventory();
            player.performCommand(subCommands.get(event.getSlot() - 1).getUsage());
            return;
        }

        player.closeInventory();
        player.performCommand("cropclick");
    }

    private @NotNull ItemStack getSubCommandItem(@NotNull SubCommand cmd) {
        return new ItemUtil(Material.BOOK)
                .setDisplayName(LanguageAPI.Menu.HELP_ITEM_NAME.get(plugin, cmd.getName()))
                .setLore(LanguageAPI.Menu.HELP_ITEM_DESCRIPTION.get(plugin, cmd.getDescription()),
                        LanguageAPI.Menu.HELP_ITEM_PERMISSION.get(plugin, cmd.getPermission()),
                        LanguageAPI.Menu.HELP_ITEM_USAGE.get(plugin, cmd.getUsage()))
                .toItemStack();
    }

    private @NotNull ItemStack getDefaultCommandItem() {
        String description = LanguageAPI.Command.DEFAULT_DESCRIPTION.get(plugin);
        return new ItemUtil(Material.BOOK)
                .setDisplayName(LanguageAPI.Menu.HELP_ITEM_NAME.get(plugin, ""))
                .setLore(LanguageAPI.Menu.HELP_ITEM_DESCRIPTION.get(plugin, description),
                        LanguageAPI.Menu.HELP_ITEM_PERMISSION.get(plugin, "cropclick.command.general"),
                        LanguageAPI.Menu.HELP_ITEM_USAGE.get(plugin, "cropclick"))
                .toItemStack();
    }
}
