package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.sql.QueryScheduler;
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
    public CompletableFuture<List<Autofarm>> getMany(int start) {
        return new SelectQuery<Autofarm>("autofarms")
                       .limit(start, 1000)
                       .fetchAll(scheduler, Autofarm.class);
    }


    @NotNull
    public CompletableFuture<Autofarm> getOne(@NotNull String id) {
        return new SelectQuery<Autofarm>("autofarms")
                       .where("farmer_id", "=", id)
                       .fetchOne(scheduler, Autofarm.class);
    }


    public CompletableFuture<Boolean> insertOne(@NotNull Autofarm autofarm) {
        return new InsertQuery<>("autofarms", Autofarm.class)
                       .values(autofarm)
                       .execute(scheduler);
    }


    public CompletableFuture<Boolean> deleteOne(@NotNull String id) {
        return new DeleteQuery("autofarms")
                       .where("farmer_id", "=", id)
                       .execute(scheduler);
    }


    public CompletableFuture<Boolean> updateOne(@NotNull String id, @NotNull Autofarm autofarm) {
        return new UpdateQuery<>("autofarms", Autofarm.class)
                       .where("farmer_id", "=", id)
                       .setAll(autofarm)
                       .execute(scheduler);
    }

}
