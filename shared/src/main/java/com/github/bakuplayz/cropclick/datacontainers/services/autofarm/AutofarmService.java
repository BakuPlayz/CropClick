package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface AutofarmService {

    @NotNull
    CompletableFuture<List<Autofarm>> getMany(int start);


    @NotNull
    CompletableFuture<Autofarm> getOne(@NotNull String id);


    @NotNull
    CompletableFuture<Boolean> insertOne(@NotNull Autofarm autofarm);


    @NotNull
    CompletableFuture<Boolean> deleteOne(@NotNull String id);


    @NotNull
    CompletableFuture<Boolean> updateOne(@NotNull String id, @NotNull Autofarm autofarm);

}
