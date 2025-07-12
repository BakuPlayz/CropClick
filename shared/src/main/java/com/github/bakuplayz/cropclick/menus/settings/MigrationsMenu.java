package com.github.bakuplayz.cropclick.menus.settings;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Messages;
import com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig;
import com.github.bakuplayz.cropclick.configurations.config.UsageConfig;
import com.github.bakuplayz.cropclick.menus.settings.states.MigrationsStateBuilder;
import com.github.bakuplayz.cropclick.menus.shared.CustomBackItem;
import com.github.bakuplayz.spigotspin.menu.abstracts.AbstractSharedMenu;
import com.github.bakuplayz.spigotspin.menu.common.SizeType;
import com.github.bakuplayz.spigotspin.menu.items.Item;
import com.github.bakuplayz.spigotspin.menu.items.actions.ItemAction;
import com.github.bakuplayz.spigotspin.menu.items.state.ClickableStateItem;
import com.github.bakuplayz.spigotspin.menu.items.state.StateItem;
import com.github.bakuplayz.spigotspin.utils.XMaterial;
import dev.bakuplayz.spigotstore.persistence.sql.core.DatabaseDialect;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.github.bakuplayz.cropclick.common.Languages.Menu.*;
import static com.github.bakuplayz.cropclick.configurations.config.DatabaseConfig.ConfigurationKey;
import static com.github.bakuplayz.cropclick.menus.settings.states.MigrationsStateBuilder.*;


/**
 * A class representing the Migrations menu.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class MigrationsMenu extends AbstractSharedMenu<MigrationsMenuState, MigrationsMenuStateHandler> {

    public final static String IDENTIFIER = "MIGRATIONS_MENU_" + UUID.randomUUID();


    private final CropClick plugin;

    private final UsageConfig usageConfig;

    private final DatabaseConfig databaseConfig;


    public MigrationsMenu(@NotNull CropClick plugin) {
        super(MIGRATIONS_TITLE.getTitle(plugin));
        this.plugin = plugin;
        this.usageConfig = plugin.getConfigManager().getUsageConfig();
        this.databaseConfig = plugin.getConfigManager().getDatabaseConfig();
    }


    @NotNull
    @Override
    public MigrationsMenuStateHandler createStateHandler(@NotNull Player player) {
        MigrationsMenuStateHandler handler = MigrationsStateBuilder.createStateHandler(this, plugin.getDataManager(), usageConfig);
        usageConfig.registerObserver(UsageConfig.ConfigurationKey.STATUS, handler);
        return handler;
    }


    @Override
    public void setItems() {
        setItem(20, new DefaultItem());
        setItem(22, new ProgressItem(), MigrationsMenuStateFlag.HISTORY);
        setItem(24, new MigrationItem());
        setItem(49, new CustomBackItem(plugin));
    }


    @Override
    public SizeType getSizeType() {
        return SizeType.DOUBLE_CHEST;
    }


    @Override
    public boolean isFramePosition(int position) {
        boolean isLeft = position % 9 == 0;
        boolean isRight = position % 9 == 8;
        boolean isTop = (position / 9.0d) <= 1.0d;
        return isLeft || isRight || isTop;
    }


    @NotNull
    @Override
    public Item getFrameItem(int position) {
        return new GlassItem();
    }


    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }


    private static final class GlassItem extends StateItem<MigrationsMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                updateItem(getState());
                setFlags(Collections.singletonList(MigrationsMenuStateFlag.HISTORY));
            });
        }


        @Override
        public void update(@NotNull MigrationsMenuState state, int position) {
            updateItem(state);
        }


        private void updateItem(@NotNull MigrationsMenuState state) {
            switch (state.getHistory().getStatus()) {
                case FAILED:
                    setName(Messages.colorize("&c-"));
                    setMaterial(XMaterial.RED_STAINED_GLASS_PANE);
                    break;
                case IN_PROGRESS:
                    setName(Messages.colorize("&e**"));
                    setMaterial(XMaterial.YELLOW_STAINED_GLASS_PANE);
                    break;
                case COMPLETED:
                    setName(Messages.colorize("&a***"));
                    setMaterial(XMaterial.LIME_STAINED_GLASS_PANE);
                    break;
                default:
                    setName(Messages.colorize("&7*"));
                    setMaterial(XMaterial.GRAY_STAINED_GLASS_PANE);
                    break;
            }
        }

    }

    private final class ProgressItem extends ClickableStateItem<MigrationsMenuState> {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.CLOCK);
                setLore(getLore(getState()));
                setName(MIGRATIONS_PROGRESS_ITEM_NAME.get(plugin));
            });
        }


        @NotNull
        @Override
        public ItemAction getAction() {
            return (item, player) -> stateHandler.startMigration();
        }


        @Override
        public void update(@NotNull MigrationsMenuState state, int position) {
            setLore(getLore(state));
        }


        @NotNull
        private List<String> getLore(@NotNull MigrationsMenuState state) {
            return MIGRATIONS_PROGRESS_ITEM_TIPS.getAsAppendList(plugin,
                    MIGRATIONS_PROGRESS_ITEM_STATUS.get(plugin, getStatus(state))
            );
        }


        @NotNull
        private String getStatus(@NotNull MigrationsMenuState state) {
            switch (state.getHistory().getStatus()) {
                case COMPLETED:
                    return MIGRATIONS_STATES_COMPLETED.get(plugin);
                case FAILED:
                    return MIGRATIONS_STATES_FAILED.get(plugin);
                case IN_PROGRESS:
                    return MIGRATIONS_STATES_IN_PROGRESS.get(plugin);
                default:
                    return MIGRATIONS_STATES_NOT_INITIALIZED.get(plugin);
            }
        }

    }

    private final class DefaultItem extends Item {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.CHEST);
                setName(MIGRATIONS_DEFAULT_ITEM_NAME.get(plugin));
                setLore(MIGRATIONS_DEFAULT_ITEM_TIPS.getAsList(plugin,
                        databaseConfig.getString(ConfigurationKey.DEFAULT_DATABASE),
                        databaseConfig.getString(ConfigurationKey.DEFAULT_HOST),
                        databaseConfig.getInt(ConfigurationKey.DEFAULT_PORT),
                        Messages.beautify(databaseConfig.getEnum(ConfigurationKey.DEFAULT_DIALECT, DatabaseDialect.class).getDialectName(), false))
                );
            });
        }

    }

    private final class MigrationItem extends Item {

        @NotNull
        @Override
        public CompletableFuture<Void> create() {
            return createSync(() -> {
                setMaterial(XMaterial.CHEST_MINECART);
                setName(MIGRATIONS_MIGRATION_ITEM_NAME.get(plugin));
                setLore(MIGRATIONS_MIGRATION_ITEM_TIPS.getAsList(plugin,
                        databaseConfig.getString(ConfigurationKey.MIGRATION_DATABASE),
                        databaseConfig.getString(ConfigurationKey.MIGRATION_HOST),
                        databaseConfig.getInt(ConfigurationKey.MIGRATION_PORT),
                        Messages.beautify(databaseConfig.getEnum(ConfigurationKey.MIGRATION_DIALECT, DatabaseDialect.class).getDialectName(), false))
                );
            });
        }

    }

}
