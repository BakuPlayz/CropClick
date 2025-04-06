package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.sql.QueryScheduler;
import com.github.bakuplayz.cropclick.sql.dao.AutofarmDAO;
import com.github.bakuplayz.cropclick.sql.query.DeleteQuery;
import com.github.bakuplayz.cropclick.sql.query.InsertQuery;
import com.github.bakuplayz.cropclick.sql.query.SelectQuery;
import com.github.bakuplayz.cropclick.sql.query.UpdateQuery;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
public final class RemoteAutofarmService {

    @NotNull
    private final QueryScheduler scheduler;


    @NotNull
    public CompletableFuture<List<Autofarm>> getMany() {
        return new SelectQuery<Autofarm>("autofarms")
                .limit(1000)
                .fetchAll(scheduler, Autofarm.class);
    }


    @NotNull
    public CompletableFuture<Autofarm> getOne(@NotNull String id) {
        return new SelectQuery<Autofarm>("autofarms")
                .where(AutofarmDAO.getColumn("farmer_id"), "=", id)
                .fetchOne(scheduler, Autofarm.class);
    }


    public CompletableFuture<Boolean> insertOne(@NotNull Autofarm autofarm) {
        return new InsertQuery<Autofarm>("autofarms")
                .values(autofarm)
                .execute(scheduler);
    }


    public CompletableFuture<Boolean> deleteOne(@NotNull String id) {
        return new DeleteQuery("autofarms")
                .where(AutofarmDAO.getColumn("farmer_id"), "=", id)
                .execute(scheduler);
    }


    public CompletableFuture<Boolean> updateOne(@NotNull String id, @NotNull Autofarm autofarm) {
        return new UpdateQuery<Autofarm>("autofarms")
                .where(AutofarmDAO.getColumn("farmer_id"), "=", id)
                .values(autofarm)
                .execute(scheduler);
    }

}
