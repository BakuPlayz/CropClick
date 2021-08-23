package com.github.bakuplayz.cropclick.datastorages.datastorage;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datastorages.DataStorage;
import com.google.common.base.Preconditions;
import com.google.gson.JsonElement;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public final class AutofarmDataStorage extends DataStorage {

    private List<Autofarm> autofarms = new ArrayList<>();

    public AutofarmDataStorage(final @NotNull CropClick plugin) {
        super("autofarms.json", plugin);
    }

    // Should be called once every 10 minutes or so, also before shutdown.
    public void saveAutofarms() {
        for (Autofarm autofarm : autofarms) {
            String data = gson.toJson(autofarm);
            JsonElement autofarmAsObj = parser.parse(data);
            fileData.add(autofarm.getFarmerID(), autofarmAsObj.getAsJsonObject());
        }
        saveData();
    }

    public void addAutofarm(final @NotNull Autofarm autofarm) {
        autofarms.add(autofarm);
    }

    public void removeAutofarm(final @NotNull Autofarm autofarm) {
        autofarms.remove(autofarm);
    }

    public void removeUnlinkedAutofarms() {
        this.autofarms = autofarms.stream()
                .filter(autofarm -> !autofarm.isLinked())
                .collect(Collectors.toList());
    }

    public @Nullable Autofarm getAutofarm(final @NotNull Location location) {
        return autofarms.stream()
                .filter(autofarm -> autofarm.getDispenserLocation() == location)
                .findFirst().orElse(null);
    }

    public @Nullable Autofarm getAutofarm(final @NotNull String farmerId) {
        return gson.fromJson(fileData.get(farmerId), Autofarm.class);
    }

    public @NotNull List<Autofarm> getAutofarms(final int start, final int maxLimit) {
        Preconditions.checkArgument(maxLimit < start, "MaxLimit cannot be less than start.");
        Preconditions.checkArgument(start < 0, "Start cannot be less than zero.");

        return autofarms.stream()
                .skip(start).limit(maxLimit)
                .collect(Collectors.toList());

        /* int fixedLimit = start + maxLimit;
        if (fixedLimit > autofarms.size()) {
            fixedLimit = autofarms.size();
        }

        return autofarms.subList(start, fixedLimit);*/
    }
}
