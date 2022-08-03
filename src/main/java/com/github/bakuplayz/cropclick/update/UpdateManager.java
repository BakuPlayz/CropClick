package com.github.bakuplayz.cropclick.update;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.language.LanguageAPI;
import com.github.bakuplayz.cropclick.utils.MessageUtils;
import com.github.bakuplayz.cropclick.utils.Param;
import com.github.bakuplayz.cropclick.utils.RequestUtil;
import com.github.bakuplayz.cropclick.utils.VersionUtils;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;


/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class UpdateManager {

    private String message;
    private final CropClick plugin;

    private final int INTERVAL_COOLDOWN = 20 * 60 * 30; // (30 minutes in ticks)
    private final String URL = "https://api.github.com/repos/Bakuplayz/CropClick/releases";


    public UpdateManager(@NotNull CropClick plugin) {
        this.plugin = plugin;
        this.message = "";

        //startInterval();
    }


    public void sendAlert(@NotNull CommandSender sender) {
        sender.sendMessage(MessageUtils.colorize(message));
    }


    private void startInterval() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::fetch, 0, INTERVAL_COOLDOWN);
    }


    private void fetch() {
        try {
            JsonElement response = new RequestUtil(URL)
                    .setDefaultHeaders()
                    .setParams(new Param("serverVersion", VersionUtils.getServerVersion()),
                            new Param("pluginVersion", getCurrentVersion()))
                    .post(true)
                    .getResponse();

            if (response.isJsonNull()) {
                this.message = "";
                return;
            }

            JsonArray updates = response.getAsJsonArray();
            JsonObject update = updates.get(0).getAsJsonObject();
            JsonElement message = update.get("message");
            this.message = !message.isJsonNull() ? message.getAsString() : "";
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.UPDATE_FETCH_FAILED.send();
        }

    }


    public boolean isUpdated() {
        return message.equals("");
    }


    public @NotNull String getCurrentVersion() {
        return plugin.getDescription().getVersion();
    }

}