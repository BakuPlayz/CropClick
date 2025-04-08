package com.github.bakuplayz.cropclick.datacontainers.services.autofarm;

import com.github.bakuplayz.cropclick.autofarm.Autofarm;
import com.github.bakuplayz.cropclick.datacontainers.services.AbstractRemoteDataService;
import com.github.bakuplayz.cropclick.sql.QueryScheduler;
import org.jetbrains.annotations.NotNull;

/**
 * Remote implementation of {@link AutofarmDataService} that communicates with an external database
 * configured within the database configuration files.
 */
public final class RemoteAutofarmService extends AbstractRemoteDataService<Autofarm> implements AutofarmDataService {

    public RemoteAutofarmService(@NotNull QueryScheduler scheduler) {
        super(scheduler, Autofarm.class);
    }


    @NotNull
    @Override
    protected String getTable() {
        return "autofarms";
    }


    @NotNull
    @Override
    protected String getIdentifier() {
        return "farmer_id";
    }

}
