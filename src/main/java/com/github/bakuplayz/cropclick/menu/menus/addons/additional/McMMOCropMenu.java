package com.github.bakuplayz.cropclick.menu.menus.addons.additional;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.addons.addon.McMMOAddon;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.BaseMenu;
import com.github.bakuplayz.cropclick.menu.menus.addons.McMMOMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemBuilder;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * A class representing the mcMMO Crop menu.
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see CropsMenu
 * @see McMMOMenu
 * @see BaseMenu
 * @since 2.0.0
 */
public final class McMMOCropMenu extends BaseMenu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int EXPERIENCE_MIN = 0;
    private final int EXPERIENCE_MAX = 10_000;


    private final String cropName;
    private final AddonConfigSection addonSection;


    public McMMOCropMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.MCMMO_CROP_TITLE);
        this.addonSection = plugin.getCropsConfig().getAddonSection();
        this.cropName = crop.getName();
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(13, getReasonItem());

        inventory.setItem(28, getExperienceDecreaseItem(MAX_CHANGE));
        inventory.setItem(29, getExperienceDecreaseItem(MIN_CHANGE));
        inventory.setItem(31, getExperienceItem());
        inventory.setItem(33, getExperienceIncreaseItem(MIN_CHANGE));
        inventory.setItem(34, getExperienceIncreaseItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.MCMMO));

        if (clicked.equals(getExperienceIncreaseItem(MIN_CHANGE))) {
            increaseExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceIncreaseItem(MAX_CHANGE))) {
            increaseExperience(MAX_CHANGE);
        }

        if (clicked.equals(getExperienceDecreaseItem(MIN_CHANGE))) {
            decreaseExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceDecreaseItem(MAX_CHANGE))) {
            decreaseExperience(MAX_CHANGE);
        }

        if (clicked.equals(getReasonItem())) {
            getReasonMenu().open(player);
        }

        refreshMenu();
    }


    /**
     * Gets the reason {@link ItemStack item}.
     *
     * @return the reason item.
     */
    private @NotNull ItemStack getReasonItem() {
        String reason = addonSection.getMcMMOExperienceReason(cropName);

        return new ItemBuilder(Material.PAPER)
                .setName(plugin, LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_VALUE.get(plugin, reason)))
                .toItemStack();
    }


    /**
     * Gets the experience {@link ItemStack item}.
     *
     * @return the experience item.
     */
    private @NotNull ItemStack getExperienceItem() {
        double experience = addonSection.getMcMMOExperience(cropName);

        return new ItemBuilder(Material.EXP_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience)
                ))
                .toItemStack();
    }


    /**
     * Gets the experience increase {@link ItemStack item} based on the provided experience.
     *
     * @param experience the experience to be increased with when clicked.
     *
     * @return the experience increase item.
     */
    private @NotNull ItemStack getExperienceIncreaseItem(int experience) {
        double beforeValue = addonSection.getMcMMOExperience(cropName);
        double afterValue = Math.min(beforeValue + experience, EXPERIENCE_MAX);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_NAME.get(plugin, experience, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Gets the experience decrease {@link ItemStack item} based on the provided experience.
     *
     * @param experience the experience to be decreased with when clicked.
     *
     * @return the experience decrease item.
     */
    private @NotNull ItemStack getExperienceDecreaseItem(int experience) {
        double beforeValue = addonSection.getMcMMOExperience(cropName);
        double afterValue = Math.max(beforeValue - experience, EXPERIENCE_MIN);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_NAME.get(plugin, experience, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * Creates the reason change {@link AnvilGUI anvil menu}.
     *
     * @return the reason menu.
     */
    private @NotNull AnvilGUI.Builder getReasonMenu() {
        String currentReason = addonSection.getMcMMOExperienceReason(cropName);
        return new AnvilGUI.Builder()
                .text(ChatColor.stripColor(currentReason))
                .itemLeft(getReasonItem())
                .onComplete((player, text) -> {
                    addonSection.setMcMMOExperienceReason(cropName, text);
                    return AnvilGUI.Response.close();
                })
                .onClose((player) -> {
                    String newReason = addonSection.getMcMMOExperienceReason(cropName);
                    player.sendMessage(
                            currentReason.equals(newReason)
                            ? LanguageAPI.Menu.MCMMO_CROP_REASON_RESPONSE_UNCHANGED.get(plugin)
                            : LanguageAPI.Menu.MCMMO_CROP_REASON_RESPONSE_CHANGED.get(plugin, newReason)
                    );
                })
                .plugin(plugin);
    }


    /**
     * Increases the {@link McMMOAddon mcMMO} experience with the provided experience.
     *
     * @param experience the experience to be increased with.
     */
    private void increaseExperience(int experience) {
        double oldAmount = addonSection.getMcMMOExperience(cropName) + experience;
        double newAmount = Math.min(oldAmount, EXPERIENCE_MAX);
        addonSection.setMcMMOExperience(cropName, newAmount);
    }


    /**
     * Decreases the {@link McMMOAddon mcMMO} experience with the provided experience.
     *
     * @param experience the experience to be decreased with.
     */
    private void decreaseExperience(int experience) {
        double oldAmount = addonSection.getMcMMOExperience(cropName) - experience;
        double newAmount = Math.max(oldAmount, EXPERIENCE_MIN);
        addonSection.setMcMMOExperience(cropName, newAmount);
    }

}