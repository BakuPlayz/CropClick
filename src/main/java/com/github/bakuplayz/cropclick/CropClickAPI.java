package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import lombok.Getter;

public final class CropClickAPI {

    // Might need to change the comments.

    private final CropClick plugin = CropClick.getPlugin();

    /**
     * Gets the CropManager, which controls all the crops.
     * */
    private final @Getter CropManager cropManager = plugin.getCropManager();

    /**
     * Gets the AutofarmManager, which controls all the active and inactive autofarms.
     * */
    private final @Getter AutofarmManager autofarmManager = plugin.getAutofarmManager();
}
