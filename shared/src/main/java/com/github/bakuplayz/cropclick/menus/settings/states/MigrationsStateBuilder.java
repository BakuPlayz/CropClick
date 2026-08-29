package com.github.bakuplayz.cropclick.menus.settings.states;

import com.github.bakuplayz.cropclick.configurations.config.UsageConfig;
import com.github.bakuplayz.cropclick.datacontainers.DataServiceManager;
import com.github.bakuplayz.cropclick.datacontainers.migration.MigrationStatus;
import com.github.bakuplayz.cropclick.menus.settings.MigrationsMenu;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import dev.bakuplayz.spigotstore.persistence.yaml.api.PersistentYamlKey;
import dev.bakuplayz.spigotstore.persistence.yaml.observers.PersistentYamlValueObserver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import static com.github.bakuplayz.cropclick.configurations.config.UsageConfig.ConfigurationKey;


/**
 * A class for creating and handling the {@link MigrationsMenu} state.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class MigrationsStateBuilder {

    @NotNull
    public static MigrationsMenuStateHandler createStateHandler(@NotNull MigrationsMenu menu, @NotNull DataServiceManager manager, @NotNull UsageConfig config) {
        return new MigrationsMenuStateHandler(menu, manager, config);
    }


    public final static class MigrationsMenuStateHandler extends MenuStateHandler<MigrationsMenuState, MigrationsMenu> implements PersistentYamlValueObserver {

        private final DataServiceManager manager;


        public MigrationsMenuStateHandler(@NotNull MigrationsMenu observer, @NotNull DataServiceManager manager, @NotNull UsageConfig config) {
            super(observer, new MigrationsMenuState(config));
            this.manager = manager;
        }


        public void startMigration() {
            notifyStateChange(state.history, MigrationsMenuStateFlag.MIGRATION_HISTORY);
        }


        @Override
        protected <P> MigrationsMenuState onUpdateState(@NotNull P partialState, int flag) {
            if (flag == MigrationsMenuStateFlag.MIGRATION_HISTORY && state.history.status.canStart()) {
                state.setHistory(infer(partialState));
                manager.getMigrationService().start();
            }

            return state;
        }


        @Override
        public <T> void onValueChanged(@NotNull PersistentYamlKey key, T value) {
            if (key == ConfigurationKey.DATABASES_MIGRATION_STATUS) {
                state.history.setStatus(infer(value));
            } else if (key == ConfigurationKey.DATABASES_MIGRATION_CONNECTED) {
                updateState(state, (state) -> {
                    state.setMigrationConnected(infer(value));
                    return state;
                }, MigrationsMenuStateFlag.MIGRATION_CONNECTED);
            } else if (key == ConfigurationKey.DATABASES_DEFAULT_CONNECTED) {
                updateState(state, (state) -> {
                    state.setDefaultConnected(infer(value));
                    return state;
                }, MigrationsMenuStateFlag.DEFAULT_CONNECTED);
            }
        }

    }

    @Getter
    @Setter
    public static final class MigrationsMenuState extends PaginatedMenuState {

        private MigrationHistory history;

        private boolean defaultConnected;

        private boolean migrationConnected;


        private MigrationsMenuState(@NotNull UsageConfig config) {
            this.history = new MigrationHistory(
                    config.getLong(ConfigurationKey.DATABASES_MIGRATION_TIMESTAMP),
                    config.getEnum(ConfigurationKey.DATABASES_MIGRATION_STATUS, MigrationStatus.class)
            );
            this.defaultConnected = config.getBoolean(ConfigurationKey.DATABASES_DEFAULT_CONNECTED);
            this.migrationConnected = config.getBoolean(ConfigurationKey.DATABASES_MIGRATION_CONNECTED);
        }

    }

    @Getter
    @Setter
    @AllArgsConstructor
    public static final class MigrationHistory {

        private long timestamp;

        @NotNull
        private MigrationStatus status;

    }

    public static final class MigrationsMenuStateFlag {

        public final static int MIGRATION_HISTORY = 0x0000001;

        public final static int DEFAULT_CONNECTED = 0x0000002;

        public final static int MIGRATION_CONNECTED = 0x0000002;


    }

}
