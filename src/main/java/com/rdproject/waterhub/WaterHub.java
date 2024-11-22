/*
 * Copyright (c) 2024 RDProject
 * Project name: WaterHub
 * All rights reserved.
 */

package com.rdproject.waterhub;

import com.rdproject.waterhub.commands.HubCommand;
import com.rdproject.waterhub.commands.HubReloadCommand;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.bstats.bungeecord.Metrics;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.logging.Level;

/**
 * Main Class
 *
 * @author RDProject on 22.11.2024
 */
public class WaterHub extends Plugin {

    private static WaterHub instance;
    private static Configuration configuration;

    private Metrics metrics;

    public static WaterHub getInstance() {
        return instance;
    }

    public static Configuration getConfiguration() {
        return configuration;
    }

    @Override
    public void onEnable() {
        instance = this;
        metrics = new Metrics(this, 14208);
        loadConfiguration();
        getProxy().getPluginManager().registerCommand(this, new HubCommand());
        getProxy().getPluginManager().registerCommand(this, new HubReloadCommand());
        checkUpdates();
        getLogger().info("Plugin loaded!");
    }

    @Override
    public void onDisable() {
        metrics.shutdown();
    }

    public static void loadConfiguration() {
        File configFile = new File(WaterHub.getInstance().getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            try {
                WaterHub.getInstance().getDataFolder().mkdirs();
                Files.copy(WaterHub.getInstance().getResourceAsStream(configFile.getName()), configFile.toPath());
            } catch (IOException exception) {
                WaterHub.getInstance().getLogger().log(Level.SEVERE, "Configuration create failed!", exception);
            }
        }

        try {
            configuration = ConfigurationProvider.getProvider(YamlConfiguration.class).load(configFile);
            WaterHub.getInstance().getLogger().info("Configuration loaded!");
        } catch (IOException exception) {
            WaterHub.getInstance().getLogger().log(Level.SEVERE, "Configuration load failed!", exception);
        }
    }

    private void checkUpdates() {
        if (!configuration.getBoolean("settings.check-updates")) return;
        this.getProxy().getScheduler().runAsync(this, () -> {
            try (InputStream is = new URL("https://api.spigotmc.org/legacy/update.php?resource=99826/~").openStream(); Scanner scanner = new Scanner(is)) {
                if (scanner.hasNext())
                    if (getDescription().getVersion().equals(scanner.next())) {
                        getLogger().info("There is not a new update available.");
                    } else {
                        getLogger().info("There is a new update available.");
                    }
            } catch (IOException exception) {
                getLogger().info("Unable to check for updates: " + exception.getMessage());
            }
        });
    }

}