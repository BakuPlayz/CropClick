package com.github.bakuplayz.cropclick.addons.addon;

import com.github.bakuplayz.cropclick.addons.addon.template.Addon;
import com.github.bakuplayz.cropclick.configs.config.AddonsConfig;
import com.gmail.nossr50.api.ExperienceAPI;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class mcMMOAddon extends Addon {

    public mcMMOAddon(final @NotNull AddonsConfig addonsConfig) {
        super("mcMMO", addonsConfig);
    }

    public void addExperience(final @NotNull Player player) {
        ExperienceAPI.addXP(player, "Herbalism", getExperience(), getExperienceReason());
    }

    private int getExperience() {
        return addonsConfig.getConfig().getInt("mcMMO.<cropName>.experience");
    }

    private @NotNull String getExperienceReason() {
        return ""; //configurable from languageConfig
    }
}
