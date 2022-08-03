package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.menus.interacts.Component;
import com.github.bakuplayz.cropclick.menu.menus.previews.PreviewContainerMenu;
import com.github.bakuplayz.cropclick.menu.menus.previews.PreviewDispenserMenu;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.Dispenser;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public abstract class AutofarmMenu extends Menu {

    protected final Block block;

    protected final Autofarm autofarm;
    private final AutofarmManager autofarmManager;

    protected boolean isCropSelected;
    protected boolean isDispenserSelected;
    protected boolean isContainerSelected;

    protected boolean isUnlinked;
    protected boolean isClickedSelected;

    protected Location cropLocation;
    protected Location dispenserLocation;
    protected Location containerLocation;

    protected ItemStack cropItem;
    protected ItemStack containerItem;
    protected ItemStack dispenserItem;

    private final Component clickedComponent;


    public AutofarmMenu(@NotNull CropClick plugin,
                        @NotNull Player player,
                        @NotNull Block block,
                        @NotNull LanguageAPI.Menu menuTitle,
                        @NotNull Component clickedComponent) {
        super(plugin, player, menuTitle);
        this.autofarmManager = plugin.getAutofarmManager();
        this.autofarm = autofarmManager.findAutofarm(block);
        this.isUnlinked = !autofarmManager.isLinked(autofarm);
        this.clickedComponent = clickedComponent;
        this.block = block;
    }


    @Override
    public void setMenuItems() {
        assignClicked();
        assignCrop();
        assignContainer();
        assignDispenser();

        inventory.setItem(20, cropItem);
        inventory.setItem(22, dispenserItem);
        inventory.setItem(24, containerItem);

        for (int i = 0; i < 54; ++i) {
            boolean isLeft = i % 9 == 0;
            boolean isRight = i % 9 == 8;
            boolean isBottom = i > 45;
            if (isLeft || isRight || isBottom) {
                inventory.setItem(i, getGlassItem());
            }
        }
    }


    /**
     * Assign wheaten the clickedComponent is selected or not.
     */
    private void assignClicked() {
        this.isClickedSelected = isClickedSelected(clickedComponent);
    }


    /**
     * Assigns the crop location, whether it's selected or linked, and the crop item.
     */
    private void assignCrop() {
        this.cropLocation = getSelectedOrLinked(Component.CROP);
        this.isCropSelected = isSelectedAndNotLinked(Component.CROP);
        this.cropItem = getCropItem();
    }


    /**
     * Assigns the container location, whether it's selected or linked, and the container item.
     */
    private void assignContainer() {
        this.containerLocation = getSelectedOrLinked(Component.CONTAINER);
        this.isContainerSelected = isSelectedAndNotLinked(Component.CONTAINER);
        this.containerItem = getContainerItem();
    }


    /**
     * Assigns the dispenser location, whether it's selected or linked, and the dispenser item.
     */
    private void assignDispenser() {
        this.dispenserLocation = getSelectedOrLinked(Component.DISPENSER);
        this.isDispenserSelected = isSelectedAndNotLinked(Component.DISPENSER);
        this.dispenserItem = getDispenserItem();
    }


    /**
     * Open a preview menu of the dispenser's inventory.
     */
    protected void openDispenser() {
        Dispenser dispenser = (Dispenser) dispenserLocation.getBlock().getState();
        PreviewDispenserMenu previewMenu = new PreviewDispenserMenu(plugin, player, autofarm.getShortenedID(), dispenser.getInventory());
        previewMenu.open();
    }


    /**
     * Open a preview menu of the container's inventory.
     */
    protected void openContainer() {
        Chest chest = (Chest) containerLocation.getBlock().getState();
        PreviewContainerMenu previewMenu = new PreviewContainerMenu(plugin, player, autofarm.getShortenedID(), chest.getInventory());
        previewMenu.open();
    }


    /**
     * If the player has selected a crop, container, and dispenser, then link them together and deselect all of them.
     */
    protected void handleLink() {
        if (!isCropSelected) return;
        if (!isContainerSelected) return;
        if (!isDispenserSelected) return;

        Location crop = autofarmManager.getSelectedCrop(player);
        Location container = autofarmManager.getSelectedContainer(player);
        Location dispenser = autofarmManager.getSelectedDispenser(player);

        assert crop != null; // Only here for the compiler.
        assert container != null; // Only here for the compiler.
        assert dispenser != null; // Only here for the compiler.

        autofarmManager.deselectAll(player);
        autofarmManager.linkAutofarm(player, crop, container, dispenser);

        player.closeInventory();
        LanguageAPI.Menu.AUTOFARM_LINK_SUCCESS.send(plugin, player);
    }


    /**
     * It returns an ItemStack that represents the crop.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getCropItem() {
        return new ItemUtil(Material.WHEAT).setName(plugin, LanguageAPI.Menu.AUTOFARM_CROP_NAME)
                                           .setLore(getLocationAsLore(cropLocation, Component.CROP)).setMaterial(
                        isCropSelected || isUnlinked ? Material.STAINED_GLASS_PANE : null)
                                           .setDamage(isUnlinked ? 15 : -1).setDamage(isCropSelected ? 3 : -1)
                                           .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the container.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getContainerItem() {
        return new ItemUtil(Material.CHEST).setName(plugin, LanguageAPI.Menu.AUTOFARM_CONTAINER_NAME)
                                           .setLore(getLocationAsLore(containerLocation, Component.CONTAINER))
                                           .setMaterial(isContainerSelected || isUnlinked ? Material.STAINED_GLASS_PANE
                                                                                          : null)
                                           .setDamage(isUnlinked ? 15 : -1).setDamage(isContainerSelected ? 3 : -1)
                                           .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the dispenser.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getDispenserItem() {
        return new ItemUtil(Material.DISPENSER).setName(plugin, LanguageAPI.Menu.AUTOFARM_DISPENSER_NAME)
                                               .setLore(getLocationAsLore(dispenserLocation, Component.DISPENSER))
                                               .setMaterial(
                                                       isDispenserSelected || isUnlinked ? Material.STAINED_GLASS_PANE
                                                                                         : null)
                                               .setDamage(isUnlinked ? 15 : -1).setDamage(isDispenserSelected ? 3 : -1)
                                               .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the glass, with the connection state.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getGlassItem() {
        return new ItemUtil(Material.STAINED_GLASS_PANE).setDamage(isUnlinked ? 15 : 4)
                                                        .setDamage(isClickedSelected ? 3 : -1)
                                                        .setName(isUnlinked ? ChatColor.GRAY + "*" : null)
                                                        .setName(isClickedSelected ? ChatColor.AQUA + "**" : null)
                                                        .setName(!isUnlinked ? ChatColor.YELLOW + "***" : null)
                                                        .toItemStack();
    }


    /**
     * If the player has an autofarm selected, return the location of the component they clicked on. If they don't have an
     * autofarm selected, return the location of the component they linked.
     *
     * @param component The component that is being linked.
     *
     * @return The location of the selected component.
     */
    private @Nullable Location getSelectedOrLinked(@NotNull Component component) {
        if (component == Component.CROP) {
            if (autofarm == null) {
                return autofarmManager.getSelectedCrop(player);
            }
            return autofarm.getCropLocation();
        }

        if (component == Component.CONTAINER) {
            if (autofarm == null) {
                return autofarmManager.getSelectedContainer(player);
            }
            return autofarm.getContainerLocation();
        }

        if (component == Component.DISPENSER) {
            if (autofarm == null) {
                return autofarmManager.getSelectedDispenser(player);
            }
            return autofarm.getDispenserLocation();
        }

        return null;
    }


    /**
     * It takes a location and returns a list of strings that represent the location.
     *
     * @param location The location to format.
     *
     * @return A list of strings.
     */
    private @NotNull List<String> getLocationAsLore(Location location, @NotNull Component component) {
        if (location == null) {
            return Collections.singletonList(LanguageAPI.Menu.AUTOFARM_FORMAT_STATE.get(plugin, LanguageAPI.Menu.AUTOFARM_STATE_UNLINKED.get(plugin)));
        }

        ArrayList<String> locationAsLore = new ArrayList<>(Arrays.asList(LanguageAPI.Menu.AUTOFARM_FORMAT_X.get(plugin, location.getBlockX()), LanguageAPI.Menu.AUTOFARM_FORMAT_Y.get(plugin, location.getBlockY()), LanguageAPI.Menu.AUTOFARM_FORMAT_Z.get(plugin, location.getBlockZ())));

        List<String> selectedState = Collections.singletonList(LanguageAPI.Menu.AUTOFARM_FORMAT_STATE.get(plugin, LanguageAPI.Menu.AUTOFARM_STATE_SELECTED.get(plugin)));

        switch (component) {
            case CROP:
                if (isCropSelected) {
                    locationAsLore.addAll(selectedState);
                    return locationAsLore;
                }
                break;

            case CONTAINER:
                if (isContainerSelected) {
                    locationAsLore.addAll(selectedState);
                    return locationAsLore;
                }
                break;

            case DISPENSER:
                if (isDispenserSelected) {
                    locationAsLore.addAll(selectedState);
                    return locationAsLore;
                }
                break;
        }

        return locationAsLore;
    }


    /**
     * If the autofarm is not null, return false. If the autofarm is linked, return false. If the component is a crop,
     * return true if the crop location is not null. If the component is a container, return true if the container location
     * is not null. If the component is a dispenser, return true if the dispenser location is not null-
     *
     * @param component The component that is being checked.
     *
     * @return A boolean value.
     */
    private boolean isSelectedAndNotLinked(@NotNull Component component) {
        if (autofarm != null) {
            return false;
        }

        if (!isUnlinked) {
            return false;
        }

        switch (component) {
            case CROP:
                return cropLocation != null;

            case CONTAINER:
                return containerLocation != null;

            case DISPENSER:
                return dispenserLocation != null;
        }

        return false;
    }


    /**
     * If the player clicked the currently selected the block, return true.
     *
     * @param component The component that was clicked.
     *
     * @return A boolean value.
     */
    private boolean isClickedSelected(@NotNull Component component) {
        switch (component) {
            case CROP:
                return autofarmManager.isCropSelected(player, block);

            case CONTAINER:
                return autofarmManager.isContainerSelected(player, block);

            case DISPENSER:
                return autofarmManager.isDispenserSelected(player, block);
        }

        return false;
    }

}