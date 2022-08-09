package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.base.Addon;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.gmail.nossr50.api.ExperienceAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @since 2.0.0
 */
public final class McMMOAddon extends Addon {

    private final CropsConfig cropsConfig;


    public McMMOAddon(@NotNull CropClick plugin) {
        super(plugin, "mcMMO");
        this.cropsConfig = plugin.getCropsConfig();
    }


    /**
     * Add experience to a player for harvesting a crop.
     *
     * @param player The player to add experience to.
     * @param crop   The crop that was harvested.
     */
    public void addExperience(@NotNull Player player, @NotNull Crop crop) {
        ExperienceAPI.addXP(player,
                "Herbalism",
                getExperience(crop.getName()),
                getExperienceReason(crop.getName())
        );
    }


    /**
     * Get the experience for the crop with the passed name.
     *
     * @param name The name of the crop.
     *
     * @return The experience of the crop.
     */
    private int getExperience(@NotNull String name) {
        return cropsConfig.getMcMMOExperience(name);
    }


    /**
     * Get the experience reason for the crop with the passed name.
     *
     * @param name The name of the crop.
     *
     * @return The experience reason for the crop.
     */
    private @NotNull String getExperienceReason(@NotNull String name) {
        return cropsConfig.getMcMMOExperienceReason(name);
    }

}