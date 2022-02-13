package com.github.bakuplayz.cropclick.utils;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.api.LanguageAPI;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.maven.artifact.versioning.DefaultArtifactVersion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * (DESCRIPTION)
 *
 * @author BakuPlayz
 * @version 1.6.0
 */
public final class UpdateUtil {

    private final CropClick plugin;

    private String latestURL;
    private String latestName;
    private String latestBody;
    private String latestVersion;

    private final String currentVersion;

    public UpdateUtil(final @NotNull CropClick plugin) {
        this.currentVersion = plugin.getDescription().getVersion();
        this.latestVersion = currentVersion;
        this.latestName = "";
        this.latestBody = "";
        this.plugin = plugin;

        fetchUpdate();
    }

    public void sendUpdateAlert(final @NotNull CommandSender sender) {
        sender.sendMessage(colorize("&7---------- &aCropClick&7 ----------"));
        sender.sendMessage(colorize("&7Status: &aNew update found!"));
        sender.sendMessage(colorize("&7----------- &aDetails&7 -------------"));
        sender.sendMessage(colorize("&7Name: &a" + latestName));
        sender.sendMessage(colorize("&7Version: &a" + latestVersion));
        sender.sendMessage(colorize("&7---------- &aUpdate Log&7 -----------"));

        for (String line : latestBody.split("\n")) {
            sender.sendMessage(colorize("&7" + line));
        }

        sender.sendMessage(colorize("&7----------- &aUpdate Url&7 ----------"));
        sender.sendMessage(colorize("&a" + latestURL));
        sender.sendMessage(colorize("&7---------------------------------"));
    }

    public void startUpdateInterval() {
        Bukkit.getScheduler().runTaskTimer(plugin, this::fetchUpdate, 0, 20 * 60 * 30);
    }

    private void fetchUpdate() {
        try {
            URL url = new URL("https://api.github.com/repos/Bakuplayz/CropClick/releases");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            InputStreamReader reader = new InputStreamReader(connection.getInputStream());

            JsonParser jsonParser = new JsonParser();
            JsonArray jsonArray = jsonParser.parse(reader).getAsJsonArray();
            JsonObject fetchedData = jsonArray.get(0).getAsJsonObject();

            this.latestBody = fetchedData.get("body").getAsString();
            this.latestName = fetchedData.get("name").getAsString();
            this.latestURL = fetchedData.get("html_url").getAsString();
            this.latestVersion = fetchedData.get("tag_name").getAsString().replace("v", "");

            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
            LanguageAPI.Console.UPDATE_FETCH_FAILED.send();
        }
    }

    public boolean hasNewUpdate() {
        if (!isUpdateForVersion()) return false;
        DefaultArtifactVersion latest = new DefaultArtifactVersion(latestVersion);
        DefaultArtifactVersion current = new DefaultArtifactVersion(currentVersion);
        return latest.compareTo(current) > 0;
    }

    private boolean isUpdateForVersion() {
        return latestName.endsWith("(for 1.8 - 1.12)") ^ latestName.endsWith("(for 1.8 - latest)");
    }

    @Contract("_ -> new")
    private @NotNull String colorize(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
