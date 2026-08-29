/**
 * CropClick - "A Spigot plugin aimed at making your farming faster, and more customizable."
 * <p>
 * Copyright (C) 2024 BakuPlayz
 * <p>
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.bakuplayz.cropclick.common;

import com.github.bakuplayz.cropclick.CropClick;
import com.github.bakuplayz.cropclick.common.Languages.Menu;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.*;
import java.util.stream.Collectors;

public class MessageBuilder {


    private final Menu menu;

    private final String rawMessage;

    private final CropClick plugin;

    private final List<String> appended = new ArrayList<>();

    private final Map<String, String> replacements = new HashMap<>();

    private int wrapWords = 4;

    private boolean wrap = true;

    private boolean colorize = true;


    private MessageBuilder(@NotNull CropClick plugin, @NotNull Menu menu) {
        this.plugin = plugin;
        this.menu = menu;
        this.rawMessage = null;
    }


    private MessageBuilder(@NotNull CropClick plugin, @NotNull String message) {
        this.plugin = plugin;
        this.menu = null;
        this.rawMessage = message;
    }


    @NotNull
    public static MessageBuilder of(@NotNull CropClick plugin, @NotNull Menu menu) {
        return new MessageBuilder(plugin, menu);
    }


    @NotNull
    public static MessageBuilder of(@NotNull CropClick plugin, @NotNull String message) {
        return new MessageBuilder(plugin, message);
    }


    public MessageBuilder replace(@NotNull String key, @NotNull Object value) {
        replacements.put(key, value.toString());
        return this;
    }


    public MessageBuilder append(String... lines) {
        appended.addAll(Arrays.asList(lines));
        return this;
    }


    public MessageBuilder append(List<String> lines) {
        appended.addAll(lines);
        return this;
    }


    public MessageBuilder wrap(boolean wrap) {
        this.wrap = wrap;
        return this;
    }


    public MessageBuilder wrap(int wordsPerLine) {
        this.wrapWords = wordsPerLine;
        this.wrap = true;
        return this;
    }


    public MessageBuilder colorize(boolean colorize) {
        this.colorize = colorize;
        return this;
    }


    public String build() {
        return formatRaw();
    }


    public List<String> buildAsList() {
        String formatted = colorize ? Messages.colorize(formatRaw()) : formatRaw();
        List<String> lines = wrap ? Messages.readify(formatted, wrapWords) : Collections.singletonList(formatted);

        if (!appended.isEmpty()) {
            lines = new ArrayList<>(lines);
            lines.add("");
            lines.addAll(
                    appended.stream()
                            .map(colorize ? Messages::colorize : s -> s)
                            .collect(Collectors.toList())
            );
        }

        return lines;
    }


    public void sendTo(@NotNull CommandSender sender) {
        buildAsList().forEach(sender::sendMessage);
    }


    private String formatRaw(String @NotNull ... overrideValues) {
        String raw = this.rawMessage != null
                             ? this.rawMessage
                             : plugin.getConfigManager()
                                       .getLanguageConfig()
                                       .getMessage("menu", menu.getCategory(), menu.getKey(), true);

        String[] keys;
        String[] values;

        if (overrideValues.length > 0) {
            keys = menu != null ? menu.getPlaceholders() : new String[0];
            values = overrideValues;
        } else {
            keys = replacements.keySet().toArray(new String[0]);
            values = Arrays.stream(keys).map(replacements::get).toArray(String[]::new);
        }

        for (int i = 0; i < keys.length && i < values.length; i++) {
            raw = Strings.replace(raw, keys[i], values[i]);
        }

        return raw;
    }

}
