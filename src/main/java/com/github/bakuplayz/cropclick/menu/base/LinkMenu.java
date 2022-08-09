package com.github.bakuplayz.cropclick.menu.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.links.Component;
import com.github.bakuplayz.cropclick.menu.menus.previews.PreviewContainerMenu;
import com.github.bakuplayz.cropclick.menu.menus.previews.PreviewDispenserMenu;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Bukkit;
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
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class LinkMenu extends Menu {

    private final AutofarmManager autofarmManager;
    private final Autofarm autofarm;
    private final Block block;

    private boolean isCropSelected;
    private boolean isContainerSelected;
    private boolean isDispenserSelected;

    protected boolean isUnlinked;
    private boolean isClickedSelected;

    private Location cropLocation;
    private Location dispenserLocation;
    private Location containerLocation;

    protected ItemStack cropItem;
    protected ItemStack containerItem;
    protected ItemStack dispenserItem;

    private ItemStack toggleItem;

    private final Component clickedComponent;


    public LinkMenu(@NotNull CropClick plugin,
                    @NotNull Player player,
                    @NotNull Block block,
                    Autofarm autofarm,
                    @NotNull LanguageAPI.Menu menuTitle,
                    @NotNull Component clickedComponent) {
        super(plugin, player, menuTitle);
        this.autofarmManager = plugin.getAutofarmManager();
        this.isUnlinked = !autofarmManager.isLinked(autofarm);
        this.clickedComponent = clickedComponent;
        this.autofarm = autofarm;
        this.block = block;

        assignCachedMeta();
    }


    @Override
    public void setMenuItems() {
        assignClicked();
        assignToggle();
        assignCrop();
        assignContainer();
        assignDispenser();

        if (!isUnlinked) {
            inventory.setItem(13, toggleItem);
        }

        inventory.setItem(isUnlinked ? 20 : 29, cropItem);
        inventory.setItem(isUnlinked ? 22 : 31, dispenserItem);
        inventory.setItem(isUnlinked ? 24 : 33, containerItem);

        for (int i = 0; i < 54; ++i) {
            boolean isBottomSide = i > 45;
            boolean isLeftSide = i % 9 == 0;
            boolean isRightSide = i % 9 == 8;
            if (isBottomSide || isLeftSide || isRightSide) {
                inventory.setItem(i, getGlassItem());
            }
        }
    }


    /**
     * If the autofarm is present and has no cached id, assign it one.
     */
    private void assignCachedMeta() {
        if (autofarm == null) {
            return;
        }

        if (!AutofarmUtils.hasMeta(autofarm)) {
            AutofarmUtils.addMeta(plugin, autofarm);
        }
    }


    /**
     * Assign wheaten the clickedComponent is selected or not.
     */
    private void assignClicked() {
        this.isClickedSelected = isClickedSelected(clickedComponent);
    }


    /**
     * Assigns the toggle item to the toggle item variable.
     */
    private void assignToggle() {
        if (autofarm == null) {
            return;
        }

        this.toggleItem = getToggleItem();
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
        PreviewDispenserMenu previewMenu = new PreviewDispenserMenu(
                plugin,
                player,
                autofarm.getShortenedID(),
                dispenser.getInventory()
        );
        previewMenu.open();
    }


    /**
     * Open a preview menu of the container's inventory.
     */
    protected void openContainer() {
        Chest chest = (Chest) containerLocation.getBlock().getState();
        PreviewContainerMenu previewMenu = new PreviewContainerMenu(
                plugin,
                player,
                autofarm.getShortenedID(),
                chest.getInventory()
        );
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
        player.closeInventory();

        Autofarm autofarm = new Autofarm(
                player,
                crop,
                container,
                dispenser
        );

        if (!autofarm.isComponentsPresent(autofarmManager)) {
            LanguageAPI.Menu.LINK_ACTION_FAILURE.send(plugin, player);
            return;
        }

        Bukkit.getPluginManager().callEvent(
                new PlayerLinkAutofarmEvent(player, autofarm)
        );
    }


    /**
     * If the clicked block is selected, deselect it, otherwise select it - if it is already selected.
     */
    protected void handleSelect() {
        switch (clickedComponent) {
            case DISPENSER:
                if (isClickedSelected) {
                    autofarmManager.deselectDispenser(player, block);
                } else {
                    autofarmManager.selectDispenser(player, block);
                }
                break;

            case CONTAINER:
                if (isClickedSelected) {
                    autofarmManager.deselectContainer(player, block);
                } else {
                    autofarmManager.selectContainer(player, block);
                }
                break;

            case CROP:
                if (isClickedSelected) {
                    autofarmManager.deselectCrop(player, block);
                } else {
                    autofarmManager.selectCrop(player, block);
                }
                break;
        }
    }


    /**
     * If the player clicks on the dispenser item, open the dispenser. If the player clicks on the container item, open the
     * container.
     *
     * @param clicked The item that was clicked.
     */
    protected void handlePreviews(@NotNull ItemStack clicked) {
        if (clicked.equals(dispenserItem)) {
            openDispenser();
        } else if (clicked.equals(containerItem)) {
            openContainer();
        }
    }


    /**
     * If the player clicks on the toggle item, toggle the autofarm and update the menu.
     *
     * @param clicked The item that was clicked.
     */
    protected void handleToggle(@NotNull ItemStack clicked) {
        if (clicked.equals(toggleItem)) {
            autofarm.isEnabled(!autofarm.isEnabled());
        }
    }


    /**
     * It returns an ItemStack that represents the Autofarm state.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getToggleItem() {
        String status = MessageUtils.getEnabledStatus(
                plugin,
                autofarm.isEnabled()
        );

        return new ItemUtil(Material.IRON_PLATE)
                .setName(plugin, LanguageAPI.Menu.LINK_TOGGLE_NAME)
                .setLore(LanguageAPI.Menu.LINK_TOGGLE_STATUS.get(plugin, status))
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the crop.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getCropItem() {
        return new ItemUtil(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.LINK_CROP_NAME)
                .setLore(getLocationAsLore(cropLocation, Component.CROP))
                .setMaterial(
                        isCropSelected || isUnlinked ? Material.STAINED_GLASS_PANE : null
                )
                .setDamage(isUnlinked ? 15 : -1)
                .setDamage(isCropSelected ? 3 : -1)
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the container.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getContainerItem() {
        return new ItemUtil(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.LINK_CONTAINER_NAME)
                .setLore(getLocationAsLore(containerLocation, Component.CONTAINER))
                .setMaterial(
                        isContainerSelected || isUnlinked ? Material.STAINED_GLASS_PANE : null
                )
                .setDamage(isUnlinked ? 15 : -1)
                .setDamage(isContainerSelected ? 3 : -1)
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the dispenser.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getDispenserItem() {
        return new ItemUtil(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.LINK_DISPENSER_NAME)
                .setLore(getLocationAsLore(dispenserLocation, Component.DISPENSER))
                .setMaterial(
                        isDispenserSelected || isUnlinked ? Material.STAINED_GLASS_PANE : null
                )
                .setDamage(isUnlinked ? 15 : -1)
                .setDamage(isDispenserSelected ? 3 : -1)
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the glass, with the connection state.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getGlassItem() {
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setDamage(isUnlinked ? 15 : 4)
                .setDamage(isClickedSelected ? 3 : -1)
                .setName(plugin, LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_LINKED)
                .setName(isUnlinked
                         ? LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_UNLINKED.get(plugin)
                         : null
                )
                .setName(isClickedSelected
                         ? LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_SELECTED.get(plugin)
                         : null
                )
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
            return Collections.singletonList(
                    LanguageAPI.Menu.LINK_FORMAT_STATE.get(plugin,
                            LanguageAPI.Menu.LINK_STATES_UNLINKED.get(plugin)
                    )
            );
        }

        if (location instanceof DoublyLocation) {
            DoublyLocation doublyLocation = LocationUtils.getAsDoubly(location);

            assert doublyLocation != null;  // Only here for the compiler.

            Location clicked = block.getLocation();
            Location doubly = doublyLocation.getDoubly();
            if (clicked.equals(doubly)) {
                location = doubly;
            }
        }

        ArrayList<String> locationAsLore = new ArrayList<>(
                Arrays.asList(
                        LanguageAPI.Menu.LINK_FORMAT_X.get(plugin, location.getBlockX()),
                        LanguageAPI.Menu.LINK_FORMAT_Y.get(plugin, location.getBlockY()),
                        LanguageAPI.Menu.LINK_FORMAT_Z.get(plugin, location.getBlockZ())
                )
        );

        List<String> selectedState = Collections.singletonList(
                LanguageAPI.Menu.LINK_FORMAT_STATE.get(plugin,
                        LanguageAPI.Menu.LINK_STATES_SELECTED.get(plugin)
                )
        );

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