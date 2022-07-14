package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import lombok.Getter;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class CropClickAPI {

    // TODO: Update comments to make them more human readable.

    /**
     * Gets the CropManager, which controls all the crops.
     */
    private final @Getter CropManager cropManager;

    /**
     * Gets the AutofarmManager, which controls all the active and inactive autofarms.
     */
    private final @Getter AutofarmManager autofarmManager;

    /**
     * Gets the WorldManager, which controls all the active and inactive worlds.
     */
    private final @Getter WorldManager worldManager;

    /**
     * Gets the AddonManager, which controls all the active and inactive addons.
     */
    private final @Getter AddonManager addonManager;


    public CropClickAPI() {
        CropClick plugin = CropClick.getPlugin();
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.autofarmManager = plugin.getAutofarmManager();
    }

}