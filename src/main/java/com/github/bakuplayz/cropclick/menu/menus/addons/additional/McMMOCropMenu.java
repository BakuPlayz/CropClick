package com.github.bakuplayz.cropclick.menu.menus.addons.additional;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.sections.crops.AddonConfigSection;
import com.github.bakuplayz.cropclick.crop.crops.base.BaseCrop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.base.Menu;
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
 * @see Menu
 * @since 2.0.0
 */
public final class McMMOCropMenu extends Menu {

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    private final int EXPERIENCE_MIN = 0;
    private final int EXPERIENCE_MAX = 10_000;


    private final String cropName;
    private final AddonConfigSection addonSection;


    public McMMOCropMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull BaseCrop crop) {
        super(plugin, player, LanguageAPI.Menu.MCMMO_CROP_TITLE);
        this.addonSection = plugin.getCropsConfig().getAddonSection();
        this.cropName = crop.getName();
    }


    @Override
    public void setMenuItems() {
        inventory.setItem(13, getReasonItem());

        inventory.setItem(28, getExperienceRemoveItem(MAX_CHANGE));
        inventory.setItem(29, getExperienceRemoveItem(MIN_CHANGE));
        inventory.setItem(31, getExperienceItem());
        inventory.setItem(33, getExperienceAddItem(MIN_CHANGE));
        inventory.setItem(34, getExperienceAddItem(MAX_CHANGE));

        setBackItem();
    }


    @Override
    public void handleMenu(@NotNull InventoryClickEvent event) {
        ItemStack clicked = event.getCurrentItem();

        assert clicked != null; // Only here for the compiler.

        handleBack(clicked, new CropsMenu(plugin, player, CropMenuState.MCMMO));

        if (clicked.equals(getExperienceAddItem(MIN_CHANGE))) {
            addMcMMOExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceAddItem(MAX_CHANGE))) {
            addMcMMOExperience(MAX_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MIN_CHANGE))) {
            removeMcMMOExperience(MIN_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MAX_CHANGE))) {
            removeMcMMOExperience(MAX_CHANGE);
        }

        if (clicked.equals(getReasonItem())) {
            getReasonMenu().open(player);
        }

        refresh();
    }


    /**
     * It creates an item that displays the reason why the crop gives experience.
     *
     * @return The item that will be used to display the reason for the experience.
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
     * It returns an ItemStack that represents the experience that the crop will give you on harvest.
     *
     * @return An ItemStack that represents the experience the crop will give you.
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
     * It creates an item that adds experience to the crop.
     *
     * @param amount The amount of experience to add to the crop.
     *
     * @return An ItemStack that will be used to add experience to the crop.
     */
    private @NotNull ItemStack getExperienceAddItem(int amount) {
        double beforeValue = addonSection.getMcMMOExperience(cropName);
        double afterValue = Math.min(beforeValue + amount, EXPERIENCE_MAX);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 5)
                .setName(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * It creates an item that removes experience to the crop.
     *
     * @param amount The amount of experience to remove to the crop.
     *
     * @return An ItemStack that will be used to remove experience to the crop.
     */
    private @NotNull ItemStack getExperienceRemoveItem(int amount) {
        double beforeValue = addonSection.getMcMMOExperience(cropName);
        double afterValue = Math.max(beforeValue - amount, EXPERIENCE_MIN);

        return new ItemBuilder(Material.STAINED_GLASS_PANE, (short) 14)
                .setName(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .toItemStack();
    }


    /**
     * It creates a menu that allows the player to change the reason for the McMMO experience.
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
     * Adds the given amount of experience to the given skill, but caps it at 10,000.
     *
     * @param amount The amount of experience to add.
     */
    public void addMcMMOExperience(int amount) {
        double oldAmount = addonSection.getMcMMOExperience(cropName) + amount;
        double newAmount = Math.min(oldAmount, EXPERIENCE_MAX);
        addonSection.setMcMMOExperience(cropName, newAmount);
    }


    /**
     * Removes the specified amount of experience from the specified skill.
     *
     * @param amount The amount of experience to remove.
     */
    public void removeMcMMOExperience(int amount) {
        double oldAmount = addonSection.getMcMMOExperience(cropName) - amount;
        double newAmount = Math.max(oldAmount, EXPERIENCE_MIN);
        addonSection.setMcMMOExperience(cropName, newAmount);
    }

}