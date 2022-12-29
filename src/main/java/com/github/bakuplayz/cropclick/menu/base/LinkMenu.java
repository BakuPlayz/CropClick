package com.github.bakuplayz.cropclick.menu.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.events.player.link.PlayerLinkAutofarmEvent;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.location.DoublyLocation;
import com.github.bakuplayz.cropclick.menu.menus.links.Component;
import com.github.bakuplayz.cropclick.menu.menus.previews.PreviewContainerMenu;
import com.github.bakuplayz.cropclick.menu.menus.previews.PreviewDispenserMenu;
import com.github.bakuplayz.cropclick.utils.AutofarmUtils;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.LocationUtils;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * Represents the base of a Link menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public abstract class LinkMenu extends Menu {

    private final Block block;
    protected final Autofarm autofarm;
    private final AutofarmManager autofarmManager;

    private boolean isCropSelected;
    private boolean isContainerSelected;
    private boolean isDispenserSelected;

    protected boolean isUnlinked;
    protected boolean isUnclaimed;
    private boolean isClickedSelected;

    private Location cropLocation;
    private Location dispenserLocation;
    private Location containerLocation;

    protected ItemStack cropItem;
    protected ItemStack containerItem;
    protected ItemStack dispenserItem;

    private ItemStack claimItem;
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
        this.clickedComponent = clickedComponent;
        this.autofarm = autofarm;
        this.block = block;

        assignCachedMeta();
    }


    @Override
    public void setMenuItems() {
        assignLinked();
        assignClaimed();
        assignClicked();
        assignToggle();
        assignCrop();
        assignContainer();
        assignDispenser();

        if (!isUnclaimed) {
            if (!isUnlinked) {
                inventory.setItem(13, toggleItem);
            }

            inventory.setItem(isUnlinked ? 20 : 29, cropItem);
            inventory.setItem(isUnlinked ? 22 : 31, dispenserItem);
            inventory.setItem(isUnlinked ? 24 : 33, containerItem);
        } else {
            inventory.setItem(22, claimItem);
        }

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
     * Assigns the isUnclaimed whether autofarm is unclaimed, and its
     * item variable, claimItem.
     */
    private void assignClaimed() {
        if (autofarm == null) {
            this.isUnclaimed = false;
            return;
        }

        this.claimItem = getClaimItem();
        this.isUnclaimed = Autofarm.UNKNOWN_OWNER.equals(autofarm.getOwnerID());
    }


    /**
     * Assigns the isUnlinked whether autofarm is unlinked.
     */
    private void assignLinked() {
        this.isUnlinked = !autofarmManager.isLinked(autofarm);
    }


    /**
     * Assign whether the clickedComponent is selected or not.
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
        BlockState containerState = containerLocation.getBlock().getState();

        if (containerState instanceof Chest) {
            new PreviewContainerMenu(
                    plugin,
                    player,
                    autofarm.getShortenedID(),
                    ((Chest) containerState).getInventory()
            ).open();
        } else if (containerState instanceof ShulkerBox) {
            new PreviewContainerMenu(
                    plugin,
                    player,
                    autofarm.getShortenedID(),
                    ((ShulkerBox) containerState).getInventory()
            ).open();
        }
    }


    /**
     * If the player clicks on the claim item, it set the owner of the autofarm to the player.
     *
     * @param clicked The item that was clicked.
     */
    protected void handleUnclaimed(@NotNull ItemStack clicked) {
        if (clicked.equals(claimItem)) {
            autofarm.setOwnerID(player.getUniqueId());
        }
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


    private @NotNull ItemStack getClaimItem() {
        return new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .setName(plugin, LanguageAPI.Menu.LINK_CLAIM_NAME)
                .setLore(LanguageAPI.Menu.LINK_CLAIM_STATUS.getAsList(plugin))
                .toItemStack();
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

        return new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
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
        return new ItemBuilder(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.LINK_CROP_NAME)
                .setLore(getLocationAsLore(cropLocation, Component.CROP))
                .setMaterial(
                        isUnlinked ? Material.BLACK_STAINED_GLASS_PANE : null
                )
                .setMaterial(
                        isCropSelected ? Material.LIGHT_BLUE_STAINED_GLASS_PANE : null
                )
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the container.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getContainerItem() {
        return new ItemBuilder(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.LINK_CONTAINER_NAME)
                .setLore(getLocationAsLore(containerLocation, Component.CONTAINER))
                .setMaterial(
                        containerLocation != null ? containerLocation.getBlock().getType() : null
                )
                .setMaterial(
                        isUnlinked ? Material.BLACK_STAINED_GLASS_PANE : null
                )
                .setMaterial(
                        isContainerSelected ? Material.LIGHT_BLUE_STAINED_GLASS_PANE : null
                )
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the dispenser.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getDispenserItem() {
        return new ItemBuilder(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.LINK_DISPENSER_NAME)
                .setLore(getLocationAsLore(dispenserLocation, Component.DISPENSER))
                .setMaterial(
                        isUnlinked ? Material.BLACK_STAINED_GLASS_PANE : null
                )
                .setMaterial(
                        isDispenserSelected ? Material.LIGHT_BLUE_STAINED_GLASS_PANE : null
                )
                .toItemStack();
    }


    /**
     * It returns an ItemStack that represents the glass, with the connection state.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getGlassItem() {
        return new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName(plugin, LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_LINKED)
                .setMaterial(
                        !isUnlinked ? Material.YELLOW_STAINED_GLASS_PANE : null
                )
                .setMaterial(
                        isClickedSelected ? Material.LIGHT_BLUE_STAINED_GLASS_PANE : null
                )
                .setMaterial(
                        isUnclaimed ? Material.WHITE_STAINED_GLASS_PANE : null
                )
                .setName(isUnlinked
                         ? LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_UNLINKED.get(plugin)
                         : null
                )
                .setName(isClickedSelected
                         ? LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_SELECTED.get(plugin)
                         : null
                )
                .setName(isUnclaimed
                         ? LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_UNCLAIMED.get(plugin)
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
            return getUnlinkedLocationLore();
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

        switch (component) {
            case CROP:
                if (isCropSelected) {
                    return getSelectedLocationLore(location);
                }
                return getLinkedLocationLore(location, component);

            case CONTAINER:
                if (isContainerSelected) {
                    return getSelectedLocationLore(location);
                }
                return getLinkedLocationLore(location, component);

            case DISPENSER:
                if (isDispenserSelected) {
                    return getSelectedLocationLore(location);
                }
                return getLinkedLocationLore(location, component);
        }

        return Collections.emptyList();
    }


    /**
     * It returns a list of strings that contain the X, Y, and Z coordinates of a location.
     *
     * @param location The location of the base.
     *
     * @return A list of strings.
     */
    private @NotNull List<String> getBaseLocationLore(@NotNull Location location) {
        return Arrays.asList(
                LanguageAPI.Menu.LINK_FORMAT_X.get(plugin, location.getBlockX()),
                LanguageAPI.Menu.LINK_FORMAT_Y.get(plugin, location.getBlockY()),
                LanguageAPI.Menu.LINK_FORMAT_Z.get(plugin, location.getBlockZ())
        );
    }


    /**
     * This function returns a list of strings that contains a single string that says the location is unlinked.
     *
     * @return A list of strings.
     */
    @Contract(" -> new")
    private @NotNull @Unmodifiable List<String> getUnlinkedLocationLore() {
        return Collections.singletonList(
                LanguageAPI.Menu.LINK_FORMAT_STATE.get(plugin,
                        LanguageAPI.Menu.LINK_STATES_UNLINKED.get(plugin)
                )
        );
    }


    /**
     * It returns a list of strings that are the base location lore, with the selected state added to the end.
     *
     * @param location The location to get the lore for.
     *
     * @return A list of strings that are the lore of the location.
     */
    private @NotNull
    @Unmodifiable List<String> getSelectedLocationLore(@NotNull Location location) {
        ArrayList<String> baseState = new ArrayList<>(
                getBaseLocationLore(location)
        );

        List<String> selectedState = Collections.singletonList(
                LanguageAPI.Menu.LINK_FORMAT_STATE.get(plugin,
                        LanguageAPI.Menu.LINK_STATES_SELECTED.get(plugin)
                )
        );

        baseState.add("");
        baseState.addAll(selectedState);
        return baseState;
    }


    /**
     * It returns a list of strings that are used to display the location of a linked block.
     *
     * @param location  The location of the block that is being linked.
     * @param component The component that the location is linked to.
     *
     * @return A list of strings.
     */
    private @NotNull List<String> getLinkedLocationLore(@NotNull Location location, @NotNull Component component) {
        switch (component) {
            case CONTAINER:
                return LanguageAPI.Menu.LINK_CONTAINER_TIPS.getAsList(plugin, getBaseLocationLore(location));

            case DISPENSER:
                return LanguageAPI.Menu.LINK_DISPENSER_TIPS.getAsList(plugin, getBaseLocationLore(location));

            case CROP:
                return getBaseLocationLore(location);
        }
        return Collections.emptyList();
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