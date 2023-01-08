package com.github.bakuplayz.cropclick.menu.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.ParticleConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.base.PaginatedMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.menus.particles.ParticleMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;
import java.util.stream.Collectors;


/**
 * A class representing the Particles menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see BaseMenu
 * @since 2.0.0
 */
public final class ParticlesMenu extends PaginatedMenu {

    /**
     * A variable containing the {@link Crop selected crop}.
     */
    private final Crop crop;

    /**
     * A variable containing all the names of the {@link ParticleEffect particles}.
     */
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

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.PARTICLES));
        handlePagination(clicked);

        int index = indexOfParticle(clicked);
        if (index == -1) {
            return;
        }

        new ParticleMenu(
                plugin,
                player,
                crop,
                particles.get(index)
        ).openMenu();
    }


    /**
     * Finds the index of the {@link ParticleEffect#name() particle's name} based on the {@link ItemStack clicked item}.
     *
     * @param clicked the item that was clicked.
     *
     * @return the index of the particle's name, otherwise -1.
     */
    private int indexOfParticle(@NotNull ItemStack clicked) {
        return menuItems
                .stream()
                .filter(clicked::equals)
                .mapToInt(item -> menuItems.indexOf(item))
                .findFirst()
                .orElse(-1);
    }


    /**
     * Creates a menu {@link ItemStack item} based on the {@link ParticleEffect#name() particle's name}.
     *
     * @param particle the name of the particle to base the item on.
     *
     * @return the created menu item.
     */
    private @NotNull ItemStack createMenuItem(@NotNull String particle) {
        boolean isEnabled = particleSection.isEnabled(
                crop.getName(),
                particle
        );
        String status = MessageUtils.getStatusMessage(plugin, isEnabled);
        String name = MessageUtils.beautify(particle, true);

        ItemBuilder item = new ItemBuilder(Material.FIREWORK)
                .setName(LanguageAPI.Menu.PARTICLES_ITEM_NAME.get(
                        plugin,
                        name,
                        status
                ));

        if (isEnabled) {
            item.setMaterial(Material.STAINED_GLASS_PANE)
                .setLore(LanguageAPI.Menu.PARTICLES_ITEM_ORDER.get(
                        plugin,
                        particleSection.getOrder(crop.getName(), particle)
                ))
                .setDamage(5);
        }

        return item.toItemStack();
    }


    /**
     * Gets all the {@link #particles particle names} as {@link #menuItems menu items}.
     *
     * @return particles as menu items.
     */
    @Override
    protected @NotNull List<ItemStack> getMenuItems() {
        return particles.stream()
                        .map(this::createMenuItem)
                        .collect(Collectors.toList());
    }


    /**
     * Gets all the {@link ParticleEffect#name() particle names}.
     *
     * @return particle names.
     */
    private @NotNull List<String> getParticles() {
        return ParticleEffect.getAvailableEffects().stream()
                             .map(ParticleEffect::name)
                             .collect(Collectors.toList());
    }

}