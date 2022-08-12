package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.menus.particles.ParticleMenu;
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
 * @version 2.0.0
 * @see Menu
 * @since 2.0.0
 */
public final class ParticlesMenu extends PaginatedMenu {

    private final Crop crop;

    private final List<String> particles;
    private final ParticleConfigSection particleSection;


    public ParticlesMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.PARTICLES_TITLE);
        this.particleSection = plugin.getCropsConfig().getParticleSection();
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

        new ParticleMenu(
                plugin,
                player,
                crop,
                particles.get(index)
        ).open();
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
        boolean isEnabled = particleSection.isEnabled(
                crop.getName(),
                particle
        );
        String status = MessageUtils.getEnabledStatus(plugin, isEnabled);
        String name = MessageUtils.beautify(particle, true);

        ItemUtil item = new ItemUtil(Material.FIREWORK)
                .setName(LanguageAPI.Menu.PARTICLES_ITEM_NAME.get(
                        plugin,
                        name,
                        status
                ));

        if (isEnabled) {
            item.setDamage(5)
                .setMaterial(Material.STAINED_GLASS_PANE)
                .setLore(LanguageAPI.Menu.PARTICLES_ITEM_ORDER.get(
                        plugin,
                        getOrderOfParticle(particle)
                ));
        }

        return item.toItemStack();
    }


    /**
     * Get a list of menu items, where each menu item is a particle.
     *
     * @return A list of ItemStacks.
     */
    @Override
    protected @NotNull List<ItemStack> getMenuItems() {
        return particles.stream()
                        .map(this::getMenuItem)
                        .collect(Collectors.toList());
    }


    /**
     * It returns the index of the particle in the list of particles for the crop.
     *
     * @param particle The name of the particle to play.
     *
     * @return The index of the particle in the list of particles for the crop.
     */
    private int getOrderOfParticle(@NotNull String particle) {
        return particleSection.getOrder(crop.getName(), particle);
    }

}