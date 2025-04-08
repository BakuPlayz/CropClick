package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractLocalDataService;
import org.jetbrains.annotations.NotNull;

/**
 * Local in-memory implementation of {@link AutofarmDataService}, used when there
 * is no database configured.
 */
public final class LocalAutofarmService extends AbstractLocalDataService<Autofarm> implements AutofarmDataService {

    // DataContainer<Autofarm> container = new DataContainer("autofarms.json");
    // container.add(getIdentifier(autofarm), autofarm);
    // container.remove(getIdentifier(autofarm));
    // container.removeIf(farm->...);
    // container.getOrDefault(...);


    @Override
    protected String getIdentifier(@NotNull Autofarm autofarm) {
        return autofarm.getFarmerId().toString();
    }

}
