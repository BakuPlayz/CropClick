package com.github.bakuplayz.cropclick.sql.query;

import com.github.bakuplayz.cropclick.sql.Column;
import org.jetbrains.annotations.NotNull;

public final class UpdateQuery<T> extends BaseQuery {

    private final Column<String>[] columns;


    @SafeVarargs
    public UpdateQuery(@NotNull String table, @NotNull Column<String>... columns) {
        this.columns = columns;
        query.append("UPDATE ").append(table).append(" SET ");
    }


    public UpdateQuery<T> values(@NotNull T instance) {
        for (Column<String> column : columns) {
            parameters.add(column.getGetter().apply(instance));
        }
        return this;
    }


    @Override
    public UpdateQuery<T> where(@NotNull Column<?> column, @NotNull String operator, Object value) {
        super.where(column, operator, value);
        return this;
    }

}
