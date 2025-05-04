package com.github.bakuplayz.cropclick.database.query.queries;

import com.github.bakuplayz.cropclick.database.EntityMapper;
import com.github.bakuplayz.cropclick.database.EntityMapperRegistry;
import com.github.bakuplayz.cropclick.database.QueryScheduler;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.CompletableFuture;

/**
 * A class representing a typed SQL update query, where the type
 * T is the type to update.
 *
 * @param <T> the type to update according to.
 */
public final class UpdateQuery<T> extends BaseQuery {

    private final Class<T> clazz;


    public UpdateQuery(@NotNull String table, @NotNull Class<T> clazz) {
        query.append("UPDATE ").append(table).append(" SET ");
        this.clazz = clazz;
    }


    /**
     * Queues the query into the execution queue, and will be executed
     * once a database worker is available.
     *
     * @param scheduler the scheduler to handle the scheduling of this task.
     *
     * @return the future response, true iff successful otherwise false.
     */
    @NotNull
    public CompletableFuture<Boolean> queue(@NotNull QueryScheduler scheduler) {
        return super.baseQueue(scheduler);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public UpdateQuery<T> where(@NotNull String column, @NotNull String operator, @NotNull Object value) {
        super.where(column, operator, value);
        return this;
    }


    /**
     * Sets the provided column to the provided value. Observe that calling
     * this multiple times causes unexpected behaviour, as only one set can
     * be in a query at once.
     * <p></p>
     * Equivalent to doing the following query:
     * "UPDATE (table) SET (column) = (value)".
     *
     * @param column the column to update the value at.
     * @param value  the value to change to.
     *
     * @return the current query instance (this).
     */
    public UpdateQuery<T> set(@NotNull String column, @NotNull Object value) {
        query.append(column).append(" = ").append(value);
        return this;
    }


    /**
     * Sets all columns to the values from the provided instance,
     * i.e. resetting all the values to the provided instance values.
     * <p></p>
     * Equivalent to doing the following query:
     * "UPDATE (table) SET (column) = (value), (column2) = (value2), ..."
     *
     * @param instance the instance whose filed values will be used for the update.
     *
     * @return the current query instance (this).
     */
    public UpdateQuery<T> setAll(@NotNull T instance) {
        StringJoiner entries = new StringJoiner(", ");

        EntityMapper<T> mapper = EntityMapperRegistry.get(clazz);
        List<Object> values = mapper.getValues(instance);
        List<String> columns = mapper.getColumns();

        for (int i = 0; i < columns.size(); ++i) {
            entries.add(columns.get(i) + " = " + values.get(i));
        }

        query.append(entries);
        return this;
    }

}
