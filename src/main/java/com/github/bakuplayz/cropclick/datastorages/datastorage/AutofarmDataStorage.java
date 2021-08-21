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

public final class AutofarmDataStorage extends DataStorage {

    private final List<Autofarm> autofarms = new ArrayList<>();

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

    public @Nullable Autofarm getAutofarm(final @NotNull Location location) {
        for (Autofarm autofarm : autofarms) {
            if (autofarm.getDispenserLocation() == location) return autofarm;
        }
        return null;
    }

    /* Pick the most efficient one:
        for (Autofarm autofarm : autofarms) {
            if (autofarm.getFarmerID().equals(farmerID)) return autofarm;
        }
        return null;*/
    public @Nullable Autofarm getAutofarm(final @NotNull String farmerID) {
        return gson.fromJson(fileData.get(farmerID), Autofarm.class);
    }

    public @NotNull List<Autofarm> getAutofarms(final int start, final int maxItemLimit) {
        Preconditions.checkArgument(maxItemLimit < start, "MaxItemLimit cannot be less than start.");
        Preconditions.checkArgument(start < 0, "Start cannot be less than zero.");

        int fixedLimit = start + maxItemLimit;
        if (fixedLimit > autofarms.size()) {
            fixedLimit = autofarms.size();
        }

        return autofarms.subList(start, fixedLimit);
    }
}
