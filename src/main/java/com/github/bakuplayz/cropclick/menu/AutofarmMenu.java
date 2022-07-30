package com.github.bakuplayz.cropclick.menu;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.autofarm.AutofarmState;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

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
    protected final AutofarmManager autofarmManager;

    protected boolean isCropSelected;
    protected boolean isDispenserSelected;
    protected boolean isContainerSelected;

    protected Location cropLocation;
    protected Location dispenserLocation;
    protected Location containerLocation;

    protected ItemStack cropItem;
    protected ItemStack containerItem;
    protected ItemStack dispenserItem;

    protected final boolean isLinked;


    public AutofarmMenu(@NotNull CropClick plugin,
                        @NotNull Player player,
                        @NotNull Block block,
                        @NotNull LanguageAPI.Menu menuTitle) {
        super(plugin, player, menuTitle);
        this.autofarmManager = plugin.getAutofarmManager();
        this.autofarm = autofarmManager.findAutofarm(block);
        this.isLinked = autofarmManager.isLinked(autofarm);
        this.block = block;
    }


    @Override
    public void setMenuItems() {
        assignCrop();
        assignContainer();
        assignDispenser();

        inventory.setItem(13, getSelectedItem());

        inventory.setItem(29, cropItem);
        inventory.setItem(31, dispenserItem);
        inventory.setItem(33, containerItem);

        for (int i = 0; i < 54; ++i) {
            boolean isLeft = i % 9 == 0;
            boolean isRight = i % 9 == 8;
            boolean isBottom = i > 45;
            if (isLeft || isRight || isBottom)
                inventory.setItem(i, getGlassItem());
        }
    }


    // TODO: add comments explaining the code or simplify
    private void assignCrop() {
        this.cropLocation = autofarm == null
                            ? autofarmManager.getSelectedCrop(player)
                            : autofarm.getCropLocation();
        this.isCropSelected = autofarm == null
                && (cropLocation != null
                || autofarmManager.isCropSelected(player, block));
        this.cropItem = getCropItem();
    }


    // TODO: add comments explaining the code or simplify
    private void assignContainer() {
        this.containerLocation = autofarm == null
                                 ? autofarmManager.getSelectedContainer(player)
                                 : autofarm.getContainerLocation();
        this.isContainerSelected = autofarm == null
                && (containerLocation != null
                || autofarmManager.isContainerSelected(player, block));
        this.containerItem = getContainerItem();
    }


    // TODO: add comments explaining the code or simplify
    private void assignDispenser() {
        this.dispenserLocation = autofarm == null
                                 ? autofarmManager.getSelectedDispenser(player)
                                 : autofarm.getDispenserLocation();
        this.isDispenserSelected = autofarm == null
                && (dispenserLocation != null
                || autofarmManager.isDispenserSelected(player, block));
        this.dispenserItem = getDispenserItem();
    }


    public void handleLink() {
        if (!isCropSelected) return;
        if (!isContainerSelected) return;
        if (!isDispenserSelected) return;

        Location crop = autofarmManager.getSelectedCrop(player);
        Location container = autofarmManager.getSelectedContainer(player);
        Location dispenser = autofarmManager.getSelectedDispenser(player);

        assert crop != null;
        assert container != null;
        assert dispenser != null;

        autofarmManager.linkAutofarm(player, crop, container, dispenser);
        autofarmManager.deselectAll(player);

        player.closeInventory();
        LanguageAPI.Menu.AUTOFARM_LINK_SUCCESS.send(plugin, player);
    }


    private @NotNull ItemStack getSelectedItem() {
        return new ItemUtil(Material.ITEM_FRAME)
                .setName(plugin, LanguageAPI.Menu.AUTOFARM_SELECTED_NAME)
                .setLore(getLocationAndState(block.getLocation(), AutofarmState.UNLINKED))
                .toItemStack();
    }


    // TODO: Add a comment what the different ternaries mean B)
    private @NotNull ItemStack getCropItem() {
        return new ItemUtil(Material.WHEAT)
                .setName(plugin, LanguageAPI.Menu.AUTOFARM_CROP_NAME)
                .setMaterial(!isLinked ? Material.STAINED_GLASS_PANE : null)
                .setMaterial(isCropSelected ? Material.STAINED_GLASS_PANE : null)
                .setDamage(!isLinked ? 15 : -1)
                .setDamage(isCropSelected ? 3 : -1)
                .setLore(getLoreByState(cropLocation, AutofarmState.LINKED))
                .setLore(!isLinked
                         ? getLoreByState(cropLocation, AutofarmState.UNLINKED)
                         : null)
                .setLore(isCropSelected
                         ? getLoreByState(cropLocation, AutofarmState.SELECTED)
                         : null)
                .toItemStack();
    }


    // TODO: Add a comment what the different ternaries mean B)
    private @NotNull ItemStack getDispenserItem() {
        return new ItemUtil(Material.DISPENSER)
                .setName(plugin, LanguageAPI.Menu.AUTOFARM_DISPENSER_NAME)
                .setMaterial(!isLinked ? Material.STAINED_GLASS_PANE : null)
                .setMaterial(isDispenserSelected ? Material.STAINED_GLASS_PANE : null)
                .setDamage(!isLinked ? 15 : -1)
                .setDamage(isDispenserSelected ? 3 : -1)
                .setLore(getLoreByState(dispenserLocation, AutofarmState.LINKED))
                .setLore(!isLinked
                         ? getLoreByState(dispenserLocation, AutofarmState.UNLINKED)
                         : null)
                .setLore(isDispenserSelected
                         ? getLoreByState(dispenserLocation, AutofarmState.SELECTED)
                         : null)
                .toItemStack();
    }


    // TODO: Add a comment what the different ternaries mean B)
    private @NotNull ItemStack getContainerItem() {
        return new ItemUtil(Material.CHEST)
                .setName(plugin, LanguageAPI.Menu.AUTOFARM_CONTAINER_NAME)
                .setMaterial(!isLinked ? Material.STAINED_GLASS_PANE : null)
                .setMaterial(isContainerSelected ? Material.STAINED_GLASS_PANE : null)
                .setDamage(!isLinked ? 15 : -1)
                .setDamage(isContainerSelected ? 3 : -1)
                .setLore(getLoreByState(containerLocation, AutofarmState.LINKED))
                .setLore(!isLinked
                         ? getLoreByState(containerLocation, AutofarmState.UNLINKED)
                         : null)
                .setLore(isContainerSelected
                         ? getLoreByState(containerLocation, AutofarmState.SELECTED)
                         : null)
                .toItemStack();
    }


    @NotNull
    private ItemStack getGlassItem() {
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setDamage(4)
                .toItemStack();
    }


    @NotNull
    @Unmodifiable
    private List<String> getLoreByState(Location location, @NotNull AutofarmState state) {
        if (location == null) {
            state = AutofarmState.UNLINKED;
        }

        switch (state) {
            case LINKED:
            case SELECTED:
                return getLocationAndState(location, state);
            case UNLINKED:
                return Collections.singletonList(
                        LanguageAPI.Menu.AUTOFARM_FORMAT_STATE.get(plugin, state.toString())
                );
        }

        return Collections.emptyList();
    }


    @NotNull
    @Unmodifiable
    private List<String> getLocationAndState(@NotNull Location location, @NotNull AutofarmState state) {
        return Arrays.asList(
                LanguageAPI.Menu.AUTOFARM_FORMAT_X.get(plugin, location.getBlockX()),
                LanguageAPI.Menu.AUTOFARM_FORMAT_Y.get(plugin, location.getBlockY()),
                LanguageAPI.Menu.AUTOFARM_FORMAT_Z.get(plugin, location.getBlockZ()),
                LanguageAPI.Menu.AUTOFARM_FORMAT_STATE.get(plugin, state.toString())
        );
    }

}