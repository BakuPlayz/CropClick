package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public final class LocalAutofarmService {

    private HashMap<UUID, Autofarm> data;


    @NotNull
    public CompletableFuture<List<Autofarm>> getMany() {
        return CompletableFuture.completedFuture((List<Autofarm>) data.values());
    }


    @NotNull
    public CompletableFuture<Autofarm> getOne(@NotNull String id) {
        return CompletableFuture.completedFuture(
                data.values().stream()
                        .filter(a -> a.getFarmerId().toString().equals(id))
                        .findAny().orElse(null)
        );
    }

}
