package com.rdproject.waterhub;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.*;

import java.io.*;
import java.nio.file.*;

@SuppressWarnings("ALL")
public class WaterHub extends Plugin {

    public static Configuration cg;
    private static WaterHub plugin;
    public static final String DEV = "ArtemYTO";
    public static final String DEV_COMPANY = "RDProject";
    public static final String PLUGIN_NAME = "WaterHub";
    public static final String LIST = "§8§l----------------------------";
    public static final String STARTUP_MESSAGE = "\n" + " \\ \\        /       |                |   |         |     \n" +
            "  \\ \\  \\   /  _` |  __|   _ \\   __|  |   |  |   |  __ \\  \n" +
            "   \\ \\  \\ /  (   |  |     __/  |     ___ |  |   |  |   | \n" +
            "    \\_/\\_/  \\__,_| \\__| \\___| _|    _|  _| \\__,_| _.__/" + " Version 1.0";

    @Override
    public void onEnable() {
        new Metrics(this, 14208);
        plugin = this;

        LoadListeners();
        getConfigs();
        LoadConfigs();
        getLogger().info(STARTUP_MESSAGE);

        UpdateChecker updateChecker = new UpdateChecker(this, 99826);
        try {
            if (updateChecker.checkForUpdates()) {
                getLogger().info(LIST);
                getLogger().info("    §8• §bWaterHub §8•");
                getLogger().info("");
                getLogger().info("§8× §7Update Available!");
                getLogger().info("§8× §7Download it from Spigot!");
                getLogger().info("");
                getLogger().info(LIST);
            } else  {
                getLogger().info(LIST);
                getLogger().info("    §8• §bWaterHub §8•");
                getLogger().info("");
                getLogger().info("§8× §7You are using the Latest Version!");
                getLogger().info("");
                getLogger().info(LIST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getConfigs() {
        if (!getDataFolder().exists())
            getDataFolder().mkdir();
        File file = new File(getDataFolder(), "config.yml");
        if (!file.exists())
            try {
                InputStream in = getResourceAsStream("config.yml");
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void LoadConfigs() {
        try {
            cg = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void LoadListeners() {
        ProxyServer.getInstance().getPluginManager().registerListener(this, new HubCommand());
    }

    public static WaterHub getInstance() {
        return plugin;
    }
}
