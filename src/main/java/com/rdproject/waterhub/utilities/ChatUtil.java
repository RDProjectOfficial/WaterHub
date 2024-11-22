/*
 * Copyright (c) 2024 RDProject
 * Project name: WaterHub
 * All rights reserved.
 */

package com.rdproject.waterhub.utilities;

import com.rdproject.waterhub.WaterHub;
import net.md_5.bungee.api.ChatColor;

import java.util.HashMap;

/**
 * Utility Class
 *
 * @author RDProject on 22.11.2024
 */
public class ChatUtil {

    public static final HashMap<String, String> COLORED_STRINGS = new HashMap<>();

    private static String color(String path) {
        return COLORED_STRINGS.computeIfAbsent(path, it -> ChatColor.translateAlternateColorCodes('&',
                WaterHub.getConfiguration().getString("messages." + path)));
    }

    public static String colorWithPrefix(String path) {
        return color("prefix") + color(path);
    }

}