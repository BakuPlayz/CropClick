package com.github.bakuplayz.cropclick.menus;

import com.cryptomorin.xseries.XSound;
import com.cryptomorin.xseries.particles.XParticle;
import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Maths;
import com.github.bakuplayz.cropclick.common.Versions;
import com.github.bakuplayz.cropclick.menus.abstracts.AbstractPaginatedMenu;
import com.github.bakuplayz.cropclick.menus.settings.*;
import com.github.bakuplayz.cropclick.world.FarmWorld;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.paginated.BasicPaginatedStateHandler;
import com.github.bakuplayz.spigotspin.menu.items.ClickableItem;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;

/**
 * A class representing the Settings menu.
 *
 * @author BakuPlayz
 * @version 2.2.0
 * @since 2.2.0
 */
public final class SettingsMenu extends AbstractPaginatedMenu<BasicPaginatedMenuState, BasicPaginatedStateHandler, Item> {

    private final static List<Integer> ITEM_POSITIONS = Arrays.asList(10, 13, 16, 28, 31, 34);

    private final CropClick plugin;

    private final List<Item> paginatedItems;


    public SettingsMenu(@NotNull CropClick plugin, boolean showBackButton) {
        super(SETTINGS_TITLE.getTitle(plugin), plugin, showBackButton);
        this.paginatedItems = initializePaginationItems();
        this.plugin = plugin;
    }


    @NotNull
    @Override
    public BasicPaginatedStateHandler createStateHandler(@NotNull Player player) {
        return new BasicPaginatedStateHandler(this);
    }


    @Override
    public boolean isFramePosition(int position) {
        return !ITEM_POSITIONS.contains(position);
    }


    @Override
    public List<Item> getPaginationItems() {
        return paginatedItems;
    }


    @NotNull
    @Override
    public Item loadPaginatedItem(@NotNull Item item, int position) {
        return item;
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @NotNull
    private List<Item> initializePaginationItems() {
        List<Item> items = new ArrayList<>(Arrays.asList(
                new ToggleItem(),
                new SoundsItem(),
                new NameItem(),
                new WorldItem(),
                new MigrationsItem()
        ));
        if (Versions.supportsParticles()) {
            items.add(1, new ParticlesItem());
        }
        return items;
    }


    private final class ToggleItem extends ClickableItem {

        private final static int MIN_PLAYER_COUNT = 0;

        private final static int MAX_PLAYERS_COUNT = 999_999;


        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = SETTINGS_TOGGLE_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", getAmountOfEnabled())
                                        .build();
                setPlayer(viewers.get(0));
                setMaterial(XMaterial.PLAYER_HEAD);
                setName(SETTINGS_TOGGLE_ITEM_NAME.get(plugin));
                setLore(SETTINGS_TOGGLE_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> new ToggleMenu(plugin).open(player);
        }


        private int getAmountOfEnabled() {
            int amountOfPlayers = Bukkit.getOfflinePlayers().length;
            int amountOfDisabled = plugin.getConfigManager().getPlayersConfig().getDisabledPlayers().size();
            return Maths.clamp(amountOfPlayers - amountOfDisabled, MIN_PLAYER_COUNT, MAX_PLAYERS_COUNT);
        }

    }

    private final class ParticlesItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = SETTINGS_PARTICLES_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", getAmountOfParticles())
                                        .build();
                setMaterial(XMaterial.FIREWORK_ROCKET);
                setName(SETTINGS_PARTICLES_ITEM_NAME.get(plugin));
                setLore(SETTINGS_PARTICLES_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> new ParticlesCropsMenu(plugin).open(player);
        }


        private int getAmountOfParticles() {
            return (int) Arrays.stream(XParticle.values()).filter(XParticle::isSupported).count();
        }

    }

    private final class SoundsItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = SETTINGS_SOUNDS_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", getAmountOfSounds())
                                        .build();
                setMaterial(XMaterial.NOTE_BLOCK);
                setName(SETTINGS_SOUNDS_ITEM_NAME.get(plugin));
                setLore(SETTINGS_SOUNDS_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> new SoundsCropsMenu(plugin).open(player);
        }


        private int getAmountOfSounds() {
            return (int) Arrays.stream(XSound.values()).filter(XSound::isSupported).count();
        }

    }

    private final class NameItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                String status = SETTINGS_NAME_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", getAmountOfRenamed())
                                        .build();
                setMaterial(XMaterial.NAME_TAG);
                setName(SETTINGS_NAME_ITEM_NAME.get(plugin));
                setLore(SETTINGS_NAME_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> new NamesCropsMenu(plugin).open(player);
        }


        private int getAmountOfRenamed() {
            return (int) plugin.getCropManager().getRegisteredCrops().stream()
                                 .filter(crop -> !crop.getDrop().getName().equals(crop.getName()))
                                 .count();
        }

    }

    private final class WorldItem extends ClickableItem {

        @Override
        public CompletableFuture<Void> create() {

            return plugin.getWorldManager().getWorlds().thenAccept((worlds) -> {
                String status = SETTINGS_WORLDS_ITEM_STATUS.builder(plugin)
                                        .replace("%status%", getAmountOfBanished(worlds))
                                        .build();

                setMaterial(XMaterial.GRASS_BLOCK);
                setName(SETTINGS_WORLDS_ITEM_NAME.get(plugin));
                setLore(SETTINGS_WORLDS_ITEM_TIPS.builder(plugin).append(status).buildAsList());
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (i, player) -> new WorldsMenu(plugin).open(player);
        }


        private long getAmountOfBanished(@NotNull List<FarmWorld> worlds) {
            return worlds.stream().filter(FarmWorld::isBanished).count();
        }

    }

    private final class MigrationsItem extends ClickableItem {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.BOOKSHELF);
                setName(SETTINGS_MIGRATIONS_ITEM_NAME.get(plugin));
                setLore(SETTINGS_MIGRATIONS_ITEM_TIPS.builder(plugin).buildAsList());
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> new MigrationsMenu(plugin).join(player, MigrationsMenu.IDENTIFIER);
        }
    }

}
