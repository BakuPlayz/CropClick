package com.github.bakuplayz.cropclick;

import com.github.bakuplayz.cropclick.addons.AddonManager;
import com.github.bakuplayz.cropclick.autofarm.AutofarmManager;
import com.github.bakuplayz.cropclick.crop.CropManager;
import com.github.bakuplayz.cropclick.update.UpdateManager;
import com.github.bakuplayz.cropclick.worlds.WorldManager;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 * @since 1.6.0
 */
public final class CropClickAPI {

    /**
     * Gets the CropManager, which controls all the crops.
     */
    private final @Getter CropManager cropManager;

    /**
     * Gets the AutofarmManager, which controls all the active and inactive autofarms.
     */
    private final @Getter AutofarmManager autofarmManager;

    /**
     * Gets the UpdateManager, which handles the plugin updates.
     */
    private final @Getter UpdateManager updateManager;

    /**
     * Gets the WorldManager, which controls all the active and inactive worlds.
     */
    private final @Getter WorldManager worldManager;

    /**
     * Gets the AddonManager, which controls all the active and inactive addons.
     */
    private final @Getter AddonManager addonManager;


    @SuppressWarnings("unused")
    public CropClickAPI() {
        this(CropClick.getPlugin());
    }


    public CropClickAPI(@NotNull CropClick plugin) {
        this.cropManager = plugin.getCropManager();
        this.worldManager = plugin.getWorldManager();
        this.addonManager = plugin.getAddonManager();
        this.updateManager = plugin.getUpdateManager();
        this.autofarmManager = plugin.getAutofarmManager();
    }

}