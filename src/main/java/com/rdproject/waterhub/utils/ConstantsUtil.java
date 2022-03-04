package com.rdproject.waterhub.utils;

import com.rdproject.waterhub.*;
import lombok.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.config.*;

public class ConstantsUtil {

    public static Configuration cg;
    public static @Getter WaterHub plugin;
    public static final String VERSION = "1.4-HOTFIX";
    public static final String LIST = "§8§l----------------------------";
    public static final String STARTUP_MESSAGE = "\n" + " \\ \\        /       |                |   |         |     \n" +
            "  \\ \\  \\   /  _` |  __|   _ \\   __|  |   |  |   |  __ \\  \n" +
            "   \\ \\  \\ /  (   |  |     __/  |     ___ |  |   |  |   | \n" +
            "    \\_/\\_/  \\__,_| \\__| \\___| _|    _|  _| \\__,_| _.__/" + " Version " + VERSION;

    public static String PREFIX;
    public static String NOPERM;

    public static TextComponent formatComponent(String message) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
    }

}
