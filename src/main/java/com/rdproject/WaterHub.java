package com.rdproject;

import com.rdproject.plugin.HubListener;
import com.rdproject.plugin.HubReload;
import com.rdproject.util.Metrics;
import com.rdproject.util.UpdateUtil;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;

/**
 * Enum Class
 *
 * @author RDProject on 03.06.2023
 */
public enum WaterHub {
    PLUGIN;

    // Constants
    @Getter private WaterHubPlugin plugin;
    @Getter private Configuration config;

    // File variable
    private File file;

    public void load(@NotNull final WaterHubPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        this.init();
        log("Plugin has been started!");
    }

    public void stop() {
        log("Plugin has been stopped!");
    }

    private void init() {
        // Loading config
        config();

        // Constant
        final PluginManager pluginManager = ProxyServer.getInstance().getPluginManager();

        // Loading functions
        pluginManager.registerCommand(plugin, new HubReload());
        pluginManager.registerListener(plugin, new HubListener());

        // Checking updates...
        update();
    }

    @SneakyThrows
    private void config() {
        // Constants
        final File dataFolder = plugin.getDataFolder();
        final String c = "config.yml";

        // Checking if exists dataFolder or not?
        if (!dataFolder.exists()) {
            dataFolder.mkdir();
        }

        // Creating file with our settings...
        file = new File(dataFolder, c);

        // Checking if exists already file or not
        if (!file.exists()) {
            InputStream in = plugin.getResourceAsStream(c);
            Files.copy(in, file.toPath());
        }

        // Loading configuration
        loadConfig();
    }

    @SneakyThrows
    public void loadConfig() {
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    private void update() {
        // Enabling metrics...
        new Metrics(plugin, 14208);
        if (config.getBoolean("settings.check-updates")) {
            // Constant
            final String h = "§8§l----------------------------";

            // Loading Update Module
            try {
                if (new UpdateUtil().checkUpdate()) {
                    log(h);
                    log("§8× §7Update Available!");
                    log("§8× §7Download it from Spigot!");
                    log(h);
                } else {
                    log(h);
                    log("§8× §7You are using the Latest Version!");
                    log(h);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
                log("Found some errors while checking updates!");
            }
        }
    }

    public void log(String text) {
        ProxyServer.getInstance().getLogger().info("[WaterHub] " + text);
    }

}
