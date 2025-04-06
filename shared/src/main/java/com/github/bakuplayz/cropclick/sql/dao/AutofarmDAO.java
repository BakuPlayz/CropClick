package com.github.bakuplayz.cropclick.sql.dao;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.common.Maps;
import com.github.bakuplayz.cropclick.sql.Column;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;

public final class AutofarmDAO {

    // private static final COLUMNS = Set<String>(...);

    private static final Map<String, Column<Autofarm>> COLUMNS = Maps.of(
            "farmer_id", new Column<Autofarm>("farmer_id", (farm) -> farm.getFarmerId().toString()),
            "owner_id", new Column<Autofarm>("owner_id", String.class, (farm) -> farm.getOwnerId().toString()),
            "is_enabled", new Column<Autofarm>("is_enabled", Boolean.class, Autofarm::isEnabled),
            "crop_location", new Column<>("crop_location", Object.class),
            "dispenser_location", new Column<>("dispenser_location", Object.class),
            "container_location", new Column<>("container_location", Object.class)
    );


    @NotNull
    public static Collection<Column<String>> getColumns() {
        return COLUMNS.values();
    }


    public static Column<String> getColumn(@NotNull String name) {
        return COLUMNS.get(name);
    }

}
