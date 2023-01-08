package com.github.bakuplayz.cropclick.menu.base;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.container.Container;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
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
 * A class representing the base of a link menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @apiNote Will get a rework later.
 * @see Menu
 * @since 2.0.0
 */
public abstract class LinkMenu extends BaseMenu {

    private final Block block;
    protected final Autofarm autofarm;
    private final AutofarmManager autofarmManager;

    /**
     * A variable containing the selection state for the {@link Crop crop}.
     */
    private boolean isCropSelected;

    /**
     * A variable containing the selection state for the {@link Container container}.
     */
    private boolean isContainerSelected;

    /**
     * A variable containing the selection state for the {@link Dispenser dispenser}.
     */
    private boolean isDispenserSelected;

    /**
     * A variable containing the unlinked state for the {@link Autofarm autofarm}.
     */
    protected boolean isUnlinked;

    /**
     * A variable containing the unclaimed state for the {@link Autofarm autofarm}.
     */
    protected boolean isUnclaimed;

    /**
     * A variable containing the selected state for the {@link Component clicked component}.
     */
    private boolean isClickedSelected;

    private Location cropLocation;
    private Location dispenserLocation;
    private Location containerLocation;

    protected ItemStack cropItem;
    protected ItemStack containerItem;
    protected ItemStack dispenserItem;

    private ItemStack claimItem;
    private ItemStack toggleItem;

    /**
     * A variable containing the {@link Component clicked component}.
     */
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

        assignCachedID();
    }


    /**
     * Sets the {@link LinkMenu LinkMenu's} menu items.
     */
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
     * Assigns the {@link Autofarm#getFarmerID() farmer ID} if not found.
     */
    private void assignCachedID() {
        if (autofarm == null) {
            return;
        }

        if (!AutofarmUtils.hasCachedID(autofarm)) {
            AutofarmUtils.addCachedID(plugin, autofarm);
        }
    }


    /**
     * Assigns whether the {@link #autofarm} is unclaimed or claimed to the variable {@link #isUnclaimed}.
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
     * Assigns whether the {@link #autofarm} is unlinked or linked to the variable {@link #isUnlinked}.
     */
    private void assignLinked() {
        this.isUnlinked = !autofarmManager.isLinked(autofarm);
    }


    /**
     * Assigns whether the {@link #clickedComponent clicked component} is unselected or selected to the variable {@link #isClickedSelected}.
     */
    private void assignClicked() {
        this.isClickedSelected = isClickedSelected(clickedComponent);
    }


    /**
     * Assigns the {@link #toggleItem toggle item}.
     */
    private void assignToggle() {
        if (autofarm == null) {
            return;
        }

        this.toggleItem = getToggleItem();
    }


    /**
     * Assigns the {@link #cropLocation crop location}, {@link #isCropSelected crop selected} and {@link #cropItem crop item}.
     */
    private void assignCrop() {
        this.cropLocation = getSelectedOrLinked(Component.CROP);
        this.isCropSelected = isSelectedAndNotLinked(Component.CROP);
        this.cropItem = getCropItem();
    }


    /**
     * Assigns the {@link #containerLocation container location}, {@link #isContainerSelected container selected} and {@link #containerItem container item}.
     */
    private void assignContainer() {
        this.containerLocation = getSelectedOrLinked(Component.CONTAINER);
        this.isContainerSelected = isSelectedAndNotLinked(Component.CONTAINER);
        this.containerItem = getContainerItem();
    }


    /**
     * Assigns the {@link #dispenserLocation dispenser location}, {@link #isDispenserSelected dispenser selected} and {@link #dispenserItem dispenser item}.
     */
    private void assignDispenser() {
        this.dispenserLocation = getSelectedOrLinked(Component.DISPENSER);
        this.isDispenserSelected = isSelectedAndNotLinked(Component.DISPENSER);
        this.dispenserItem = getDispenserItem();
    }


    /**
     * Opens the {@link PreviewMenu preview menu} of the {@link PreviewDispenserMenu#inventory dispenser's inventory}.
     */
    protected void openDispenser() {
        Dispenser dispenser = (Dispenser) dispenserLocation.getBlock().getState();
        PreviewDispenserMenu previewMenu = new PreviewDispenserMenu(
                plugin,
                player,
                autofarm.getShortenedID(),
                dispenser.getInventory()
        );
        previewMenu.openMenu();
    }


    /**
     * Opens the {@link PreviewMenu preview menu} of the {@link PreviewContainerMenu#inventory container's inventory}.
     */
    protected void openContainer() {
        BlockState containerState = containerLocation.getBlock().getState();

        if (containerState instanceof Chest) {
            new PreviewContainerMenu(
                    plugin,
                    player,
                    autofarm.getShortenedID(),
                    ((Chest) containerState).getInventory()
            ).openMenu();
        } else if (containerState instanceof ShulkerBox) {
            new PreviewContainerMenu(
                    plugin,
                    player,
                    autofarm.getShortenedID(),
                    ((ShulkerBox) containerState).getInventory()
            ).openMenu();
        }
    }


    /**
     * Handles unclaimed {@link Autofarm autofarms} if {@link #isUnclaimed}.
     *
     * @param clicked the item that was clicked.
     */
    protected void handleUnclaimed(@NotNull ItemStack clicked) {
        if (!clicked.equals(claimItem)) {
            return;
        }
        autofarm.setOwnerID(player.getUniqueId());
    }


    /**
     * Handles linking {@link Autofarm autofarms} if {@link #isUnlinked}.
     */
    protected void handleLink() {
        if (!isCropSelected) return;
        if (!isContainerSelected) return;
        if (!isDispenserSelected) return;

        Location crop = autofarmManager.getSelectedCrop(player);
        if (crop == null) {
            return;
        }

        Location container = autofarmManager.getSelectedContainer(player);
        if (container == null) {
            return;
        }

        Location dispenser = autofarmManager.getSelectedDispenser(player);
        if (dispenser == null) {
            return;
        }

        autofarmManager.deselectComponents(player);
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
     * Handles selecting a {@link Component component}.
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
     * Handles {@link PreviewMenu preview menus}.
     *
     * @param clicked the item that was clicked.
     */
    protected void handlePreviews(@NotNull ItemStack clicked) {
        if (clicked.equals(dispenserItem)) {
            openDispenser();
        } else if (clicked.equals(containerItem)) {
            openContainer();
        }
    }


    /**
     * Handles toggling of {@link Autofarm the autofarm} inside the menu.
     *
     * @param clicked the item that was clicked.
     */
    protected void handleToggle(@NotNull ItemStack clicked) {
        if (clicked.equals(toggleItem)) {
            autofarm.isEnabled(!autofarm.isEnabled());
        }
    }


    /**
     * Gets the claim {@link ItemStack item}.
     *
     * @return the claim item.
     */
    private @NotNull ItemStack getClaimItem() {
        return new ItemBuilder(Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .setName(plugin, LanguageAPI.Menu.LINK_CLAIM_NAME)
                .setLore(LanguageAPI.Menu.LINK_CLAIM_STATUS.getAsList(plugin))
                .toItemStack();
    }


    /**
     * Gets the toggle {@link ItemStack item}.
     *
     * @return the toggle item.
     */
    private @NotNull ItemStack getToggleItem() {
        String status = MessageUtils.getStatusMessage(
                plugin,
                autofarm.isEnabled()
        );

        return new ItemBuilder(Material.LIGHT_WEIGHTED_PRESSURE_PLATE)
                .setName(plugin, LanguageAPI.Menu.LINK_TOGGLE_NAME)
                .setLore(LanguageAPI.Menu.LINK_TOGGLE_STATUS.get(plugin, status))
                .toItemStack();
    }


    /**
     * Gets the crop {@link ItemStack item}.
     *
     * @return the crop item.
     */
    private @NotNull ItemStack getCropItem() {
        return new ItemBuilder(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.LINK_CROP_NAME)
                .setLore(getLocationAsLore(cropLocation, Component.CROP))
                .setMaterial(isUnlinked, Material.BLACK_STAINED_GLASS_PANE)
                .setMaterial(isCropSelected, Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the container {@link ItemStack item}.
     *
     * @return the container item.
     */
    private @NotNull ItemStack getContainerItem() {
        return new ItemBuilder(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.LINK_CONTAINER_NAME)
                .setLore(getLocationAsLore(containerLocation, Component.CONTAINER))
                .setMaterial(
                        containerLocation != null ? containerLocation.getBlock().getType() : null
                )
                .setMaterial(isUnlinked, Material.BLACK_STAINED_GLASS_PANE)
                .setMaterial(isContainerSelected, Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the dispenser {@link ItemStack item}.
     *
     * @return the dispenser item.
     */
    private @NotNull ItemStack getDispenserItem() {
        return new ItemBuilder(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.LINK_DISPENSER_NAME)
                .setLore(getLocationAsLore(dispenserLocation, Component.DISPENSER))
                .setMaterial(isUnlinked, Material.BLACK_STAINED_GLASS_PANE)
                .setMaterial(isDispenserSelected, Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .toItemStack();
    }


    /**
     * Gets the glass {@link ItemStack item}.
     *
     * @return the glass item.
     */
    private @NotNull ItemStack getGlassItem() {
        return new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName(plugin, LanguageAPI.Menu.LINK_GLASS_ITEM_NAME_LINKED)
                .setMaterial(!isUnlinked, Material.YELLOW_STAINED_GLASS_PANE)
                .setMaterial(isClickedSelected, Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                .setMaterial(isUnclaimed, Material.WHITE_STAINED_GLASS_PANE)
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
     * Gets the location of either the selected or linked {@link Component component}.
     *
     * @param component the component to check.
     *
     * @return the selected location or linked location.
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
     * Gets the {@link Location provided location} as a lore.
     *
     * @param location  the location to make into a lore.
     * @param component the component to get the lore from.
     *
     * @return the provided location as lore.
     */
    private @NotNull List<String> getLocationAsLore(Location location, @NotNull Component component) {
        if (location == null) {
            return getUnlinkedLocationLore();
        }

        if (location instanceof DoublyLocation) {
            DoublyLocation doublyLocation = LocationUtils.findDoubly(location);

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
     * Gets the base lore for the {@link Location provided location}.
     *
     * @param location the location to make into a lore.
     *
     * @return the base lore for the provided location.
     */
    private @NotNull List<String> getBaseLocationLore(@NotNull Location location) {
        return Arrays.asList(
                LanguageAPI.Menu.LINK_FORMAT_X.get(plugin, location.getBlockX()),
                LanguageAPI.Menu.LINK_FORMAT_Y.get(plugin, location.getBlockY()),
                LanguageAPI.Menu.LINK_FORMAT_Z.get(plugin, location.getBlockZ())
        );
    }


    /**
     * Gets the unlinked lore for {@link Location any location}.
     *
     * @return the unlinked lore for any location.
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
     * Gets the selected lore for the {@link Location provided location}.
     *
     * @param location the location to make into a lore.
     *
     * @return the selected lore for the provided location.
     */
    private @NotNull @Unmodifiable List<String> getSelectedLocationLore(@NotNull Location location) {
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
     * Gets the linked lore for the {@link Location provided location}.
     *
     * @param location  the location to make into a lore.
     * @param component the component to get the lore from.
     *
     * @return the linked lore for the provided location.
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
     * Checks whether a {@link Component component} is selected and not linked.
     *
     * @param component the component to check.
     *
     * @return true if it is, otherwise false.
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
     * Checks whether the {@link Component component} is selected.
     *
     * @param component the component to check.
     *
     * @return true if it is, otherwise false.
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