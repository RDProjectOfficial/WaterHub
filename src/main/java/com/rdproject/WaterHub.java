package com.rdproject;

import com.rdproject.plugin.*;
import com.rdproject.util.*;
import lombok.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.*;

import java.io.*;
import java.nio.file.*;

public final class WaterHub extends Plugin {

    @Getter @Setter private static WaterHub plugin;
    public static Configuration config;
    @Getter @Setter private static String prefix;
    @Getter @Setter private static String noperm;

    void getconfig() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists())
            try {
                InputStream in = plugin.getResourceAsStream("config.yml");
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
                Util.log("&cError with creating config.yml!");
            }
    }

    @Override
    public void onEnable() {
        setPlugin(this);
        getconfig();
        Util.loadconfig();
        setPrefix(config.getString("prefix"));
        setNoperm(config.getString("no-permission"));
        getProxy().getPluginManager().registerListener(WaterHub.getPlugin(), new HubListener());
        getProxy().getPluginManager().registerCommand(WaterHub.getPlugin(), new HubReload());
        Util.startup(" \\ \\        /       |                |   |         |     \n" +
                "  \\ \\  \\   /  _` |  __|   _ \\   __|  |   |  |   |  __ \\  \n" +
                "   \\ \\  \\ /  (   |  |     __/  |     ___ |  |   |  |   | \n" +
                "    \\_/\\_/  \\__,_| \\__| \\___| _|    _|  _| \\__,_| _.__/", "1.6-RELEASE");
        Util.update(14208, 99826);
    }

}