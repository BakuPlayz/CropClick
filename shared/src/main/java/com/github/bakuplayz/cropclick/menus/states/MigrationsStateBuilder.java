package com.github.bakuplayz.cropclick.menus.states;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.CropPlayer;
import com.github.bakuplayz.cropclick.configurations.config.DefaultConfig;
import com.github.bakuplayz.cropclick.menus.MigrationsMenu;
import com.github.bakuplayz.cropclick.menus.settings.ToggleMenu;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

/**
 * A class for creating and handling the {@link MigrationsMenu} state.
 *
 * @author BakuPlayz
 * @version 3.0.0
 * @since 3.0.0
 */
public final class MigrationsStateBuilder {

    @NotNull
    public static MigrationsMenuStateHandler createStateHandler(@NotNull MigrationsMenu menu, @NotNull CropClick plugin) {
        return new MigrationsMenuStateHandler(menu);
    }

    public final static class MigrationsMenuStateHandler extends PaginatedMenuStateHandler<MigrationsMenuState> {


        public MigrationsMenuStateHandler(@NotNull MigrationsMenu observer) {
            super(observer, new MigrationsMenuState());
        }

    }

    @Getter
    @Setter
    public static final class MigrationsMenuState implements PaginatedMenuState {


        private MigrationsMenuState() {
            super();
        }

    }

    public static final class MigrationsMenuStateFlag {


    }

}
