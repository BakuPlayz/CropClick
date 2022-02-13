package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import lombok.Getter;

public final class CropClickAPI {

    // TODO: Update comments to make them more human readable.

    private final CropClick plugin = CropClick.getPlugin();

    /**
     * Gets the CropManager, which controls all the crops.
     * */
    private final @Getter CropManager cropManager = plugin.getCropManager();

    /**
     * Gets the AutofarmManager, which controls all the active and inactive autofarms.
     * */
    private final @Getter AutofarmManager autofarmManager = plugin.getAutofarmManager();

    /**
     * Gets the WorldManager, which controls all the active and inactive worlds.
     */
    private final @Getter WorldManager worldManager = plugin.getWorldManager();
}
