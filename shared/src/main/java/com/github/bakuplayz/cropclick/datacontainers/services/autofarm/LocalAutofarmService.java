package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractLocalDataService;
import org.jetbrains.annotations.NotNull;

/**
 * Local in-memory implementation of {@link AutofarmDataService}, used when there
 * is no database configured.
 */
public final class LocalAutofarmService extends AbstractLocalDataService<Autofarm> implements AutofarmDataService {

    public LocalAutofarmService(@NotNull CropClick plugin) {
        super("autofarms.json", plugin);
    }


    @Override
    protected String getIdentifier(@NotNull Autofarm autofarm) {
        return autofarm.getFarmerId().toString();
    }

}
