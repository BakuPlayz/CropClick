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
 * @version 1.6.0
 * @see CropsMenu
 * @see McMMOMenu
 * @see Menu
 * @since 1.6.0
 */
public final class McMMOCropMenu extends Menu {

    private final Crop crop;

    private final CropsConfig cropsConfig;

    private final int MIN_CHANGE = 1;
    private final int MAX_CHANGE = 5;


    public McMMOCropMenu(@NotNull CropClick plugin, Player player, Crop crop) {
        super(plugin, player, LanguageAPI.Menu.MCMMO_CROP_TITLE);
        this.cropsConfig = plugin.getCropsConfig();
        this.crop = crop;
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

        String cropName = crop.getName();
        if (clicked.equals(getExperienceAddItem(MIN_CHANGE))) {
            addMcMMOExperience(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getExperienceAddItem(MAX_CHANGE))) {
            addMcMMOExperience(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MIN_CHANGE))) {
            removeMcMMOExperience(cropName, MIN_CHANGE);
        }

        if (clicked.equals(getExperienceRemoveItem(MAX_CHANGE))) {
            removeMcMMOExperience(cropName, MAX_CHANGE);
        }

        if (clicked.equals(getReasonItem())) {
            getReasonMenu(cropName).open(player);
        }

        updateMenu();
    }


    private @NotNull ItemStack getReasonItem() {
        String reason = cropsConfig.getMcMMOExperienceReason(crop.getName());
        return new ItemUtil(Material.PAPER)
                .setName(plugin, LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_TIPS.getAsList(plugin,
                        LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_REASON_ITEM_VALUE.get(plugin, reason)))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceItem() {
        double experience = cropsConfig.getMcMMOExperience(crop.getName());
        return new ItemUtil(Material.EXP_BOTTLE)
                .setName(plugin, LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_NAME)
                .setLore(LanguageAPI.Menu.MCMMO_CROP_EXPERIENCE_ITEM_VALUE.get(plugin, experience))
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceAddItem(int amount) {
        double beforeValue = cropsConfig.getMcMMOExperience(crop.getName());
        double afterValue = Math.min(beforeValue + amount, 10_000);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_ADD_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(5)
                .toItemStack();
    }


    private @NotNull ItemStack getExperienceRemoveItem(int amount) {
        double beforeValue = cropsConfig.getMcMMOExperience(crop.getName());
        double afterValue = Math.max(beforeValue - amount, 0);
        return new ItemUtil(Material.STAINED_GLASS_PANE)
                .setName(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_NAME.get(plugin, amount, "Experience"))
                .setLore(LanguageAPI.Menu.MCMMO_CROP_REMOVE_ITEM_AFTER.get(plugin, afterValue))
                .setDamage(14)
                .toItemStack();
    }


    private @NotNull AnvilGUI.Builder getReasonMenu(@NotNull String cropName) {
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
     * @param name   The name of the skill to add experience to.
     * @param amount The amount of experience to add.
     */
    public void addMcMMOExperience(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getMcMMOExperience(name) + amount;
        double newAmount = Math.min(oldAmount, 10_000);
        cropsConfig.setMcMMOExperience(name, newAmount);
    }


    /**
     * Removes the specified amount of experience from the specified skill.
     *
     * @param name   The name of the skill to remove experience from.
     * @param amount The amount of experience to remove.
     */
    public void removeMcMMOExperience(@NotNull String name, int amount) {
        double oldAmount = cropsConfig.getMcMMOExperience(name) - amount;
        double newAmount = Math.max(oldAmount, 0);
        cropsConfig.setMcMMOExperience(name, newAmount);
    }

}