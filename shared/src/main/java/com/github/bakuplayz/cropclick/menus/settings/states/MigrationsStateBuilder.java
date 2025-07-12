package com.github.bakuplayz.cropclick.menus.settings.states;

import com.github.bakuplayz.cropclick.configurations.config.UsageConfig;
import com.github.bakuplayz.cropclick.datacontainers.DataServiceManager;
import com.github.bakuplayz.cropclick.datacontainers.migration.MigrationStatus;
import com.github.bakuplayz.cropclick.menus.settings.MigrationsMenu;
import com.github.bakuplayz.spigotspin.menu.common.paginated.PaginatedMenuState;
import com.github.bakuplayz.spigotspin.menu.common.state.MenuStateHandler;
import dev.bakuplayz.spigotstore.persistence.yaml.observers.ValueObserver;
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


    public final static class MigrationsMenuStateHandler extends MenuStateHandler<MigrationsMenuState, MigrationsMenu> implements ValueObserver {

        private final DataServiceManager manager;


        public MigrationsMenuStateHandler(@NotNull MigrationsMenu observer, @NotNull DataServiceManager manager, @NotNull UsageConfig config) {
            super(observer, new MigrationsMenuState(config));
            this.manager = manager;
        }


        public void startMigration() {
            updateState(state.history, (state) -> state, MigrationsMenuStateFlag.HISTORY);
        }


        @Override
        public <T> void onValueChanged(@NotNull T value) {
            state.history.setStatus(infer(value));
        }


        @Override
        protected <P> MigrationsMenuState onUpdateState(@NotNull P partialState, int flag) {
            if (flag == MigrationsMenuStateFlag.HISTORY && state.history.status.canStart()) {
                state.setHistory(infer(partialState));
                manager.getMigrationService().start();
            }

            return state;
        }
    }

    @Getter
    @Setter
    public static final class MigrationsMenuState extends PaginatedMenuState {

        private MigrationHistory history;


        private MigrationsMenuState(@NotNull UsageConfig config) {
            this.history = new MigrationHistory(
                    config.getLong(ConfigurationKey.TIMESTAMP),
                    config.getEnum(ConfigurationKey.STATUS, MigrationStatus.class)
            );
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

        public final static int HISTORY = 0x0000001;

    }

}
