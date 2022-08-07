package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @see Menu
 * @since 1.6.0
 */
public final class ParticlesMenu extends PaginatedMenu {

    private final Crop crop;
    private final CropsConfig cropsConfig;

    private final List<String> particles;


    public ParticlesMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.PARTICLES_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.particles = getParticles();
        this.crop = crop;
    }


    @Override
    public void setMenuItems() {
        this.menuItems = getMenuItems();

        setPaginatedItems();
        setPageItems();
        setBackItems();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.PARTICLES));
        handlePagination(clicked);

        int index = getIndexOfParticle(clicked);
        if (index == -1) {
            return;
        }

        String particle = particles.get(index);
        cropsConfig.toggleParticleCrop(crop.getName(), particle);

        updateMenu();
    }


    /**
     * It returns a list of all the particle names.
     *
     * @return A list of all the particle effects.
     */
    private @NotNull List<String> getParticles() {
        return Arrays.stream(ParticleEffect.values())
                     .map(ParticleEffect::name)
                     .collect(Collectors.toList());
    }


    /**
     * "Get the index of the particle item that was clicked on."
     * <p>
     * The first thing we do is create a stream of all the items in the menu. Then we filter the stream to only contain the
     * item that was clicked on. Then we map the stream to only contain the index of the item that was clicked on. Finally,
     * we find the first item in the stream and return it. If there is no item in the stream, we return -1.
     * </p>
     *
     * @param clicked The item that was clicked.
     *
     * @return The index of the particle in the menuItems list.
     */
    private int getIndexOfParticle(@NotNull ItemStack clicked) {
        return menuItems
                .stream()
                .filter(clicked::equals)
                .mapToInt(item -> menuItems.indexOf(item))
                .findFirst()
                .orElse(-1);
    }


    /**
     * It creates an item with the name of the particle and a lore that says whether the particle is active or not.
     *
     * @param particle The name of the particle.
     *
     * @return An ItemStack.
     */
    private @NotNull ItemStack getMenuItem(@NotNull String particle) {
        String name = MessageUtils.beautify(particle, true);
        String status = MessageUtils.getEnabledStatus(
                plugin,
                cropsConfig.getCropParticles(crop.getName()).contains(particle)
        );

        return new ItemUtil(Material.FIREWORK)
                .setName(LanguageAPI.Menu.PARTICLES_ITEM_NAME.get(plugin, name))
                .setLore(LanguageAPI.Menu.PARTICLES_ITEM_STATUS.get(plugin, status))
                .toItemStack();
    }


    /**
     * Get a list of menu items, where each menu item is a particle.
     *
     * @return A list of ItemStacks.
     */
    protected @NotNull List<ItemStack> getMenuItems() {
        return particles.stream()
                        .map(this::getMenuItem)
                        .collect(Collectors.toList());
    }

}