package com.github.bakuplayz.cropclick.menu.menus.addons.additional;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.configs.config.CropsConfig;
import com.github.bakuplayz.cropclick.crop.crops.base.Crop;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.menu.Menu;
import com.github.bakuplayz.cropclick.menu.menus.addons.McMMOMenu;
import com.github.bakuplayz.cropclick.menu.menus.main.CropsMenu;
import com.github.bakuplayz.cropclick.menu.states.CropMenuState;
import com.github.bakuplayz.cropclick.utils.ItemUtil;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 2.0.0
 * @see CropsMenu
 * @see McMMOMenu
 * @see Menu
 * @since 2.0.0
 */
public final class McMMOCropMenu extends Menu {

    private final String cropName;
    private final CropsConfig cropsConfig;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;

    public final int EXPERIENCE_MIN = 0;
    public final int EXPERIENCE_MAX = 10_000;


    public McMMOCropMenu(@NotNull CropClick plugin, @NotNull Player player, @NotNull Crop crop) {
        super(plugin, player, LanguageAPI.Menu.MCMMO_CROP_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
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

        updateMenu();
    }


    private @NotNull ItemStack getReasonItem() {
        String reason = cropsConfig.getMcMMOExperienceReason(cropName);
        return new ItemUtil(Material.PAPER)
                .setName(plugin, LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_VALUE.get(plugin, reason)))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceItem() {
        double experience = cropsConfig.getMcMMOExperience(cropName);
        return new ItemUtil(Material.EXP_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceAddItem(int amount) {
        double beforeValue = cropsConfig.getMcMMOExperience(cropName);
        double afterValue = Math.min(beforeValue + amount, EXPERIENCE_MAX);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceRemoveItem(int amount) {
        double beforeValue = cropsConfig.getMcMMOExperience(cropName);
        double afterValue = Math.max(beforeValue - amount, EXPERIENCE_MIN);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    private @NotNull AnvilGUI.Builder getReasonMenu() {
        String currentReason = cropsConfig.getMcMMOExperienceReason(cropName);
        return new AnvilGUI.Builder()
                .text(ChatColor.stripColor(currentReason))
                .itemLeft(getReasonItem())
                .onComplete((player, text) -> {
                    cropsConfig.setMcMMOExperienceReason(cropName, text);
                    return AnvilGUI.Response.close();
                })
                .onClose((player) -> {
                    String newReason = cropsConfig.getMcMMOExperienceReason(cropName);
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
        double oldAmount = cropsConfig.getMcMMOExperience(cropName) + amount;
        double newAmount = Math.min(oldAmount, EXPERIENCE_MAX);
        cropsConfig.setMcMMOExperience(cropName, newAmount);
    }


    /**
     * Removes the specified amount of experience from the specified skill.
     *
     * @param amount The amount of experience to remove.
     */
    public void removeMcMMOExperience(int amount) {
        double oldAmount = cropsConfig.getMcMMOExperience(cropName) - amount;
        double newAmount = Math.max(oldAmount, EXPERIENCE_MIN);
        cropsConfig.setMcMMOExperience(cropName, newAmount);
    }

}