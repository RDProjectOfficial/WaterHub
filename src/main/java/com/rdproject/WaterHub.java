package com.rdproject;

import com.rdproject.plugin.*;
import lombok.Getter;
import lombok.SneakyThrows;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.util.logging.Level;

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
        // чек

        // Creating file with our settings...
        file = new File(dataFolder, c);

        // Checking if exists already file or not
        if (!file.exists()) {
            Files.copy(plugin.getResourceAsStream(c), file.toPath());
        }

        // Loading configuration
        loadConfig();
    }

    @SneakyThrows
    public void loadConfig() {
        config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
    }

    private void update() {
        // Boolean check
        if (!config.getBoolean("settings.check-updates")) return;

        // Creating a boolean variable to indicate (in next code lines) whether there are any updates or not
        boolean updatesAvailable = false;

        // Getting the latest version of the plugin from the Spigot API
        try (InputStream inputStream = new URL("https://api.spigotmc.org/legacy/update.php?resource=99826").openConnection().getInputStream()) {
            updatesAvailable = !WaterHub.PLUGIN.getPlugin().getDescription().getVersion().equals(
                    new BufferedReader(new InputStreamReader(inputStream)).readLine());
        } catch (IOException exception) {
            severe("Spigot API is broken. Contact to plugin's developer!", exception);
        }

        // Constant
        final String h = "§8§l----------------------------";

        // Loading update module
        try {
            if (updatesAvailable) {
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
           severe("Found some errors while checking updates!", exception);
        }
    }

    private void log(String text) {
        plugin.getLogger().info(text);
    }

    private void severe(String text, Exception exception) {
        plugin.getLogger().log(Level.SEVERE, text, exception);
    }

}
