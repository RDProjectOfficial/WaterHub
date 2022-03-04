package com.rdproject.util;

import com.rdproject.*;
import lombok.experimental.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.config.*;

import java.io.*;
import java.util.logging.*;

import static com.rdproject.WaterHub.*;

@UtilityClass
public class Util {
    public TextComponent text(final String message) {
        return new TextComponent(ChatColor.translateAlternateColorCodes('&', message));
    }

    public void startup(final String message, final String version) {
        ProxyServer.getInstance().getLogger().log(Level.INFO, ChatColor.translateAlternateColorCodes('&', "&8[&b" + WaterHub.getPlugin().getDescription().getName() + "&8]&r " + "\n" + message + " Version: " + version));
    }

    public void log(final String message) {
        ProxyServer.getInstance().getLogger().info(ChatColor.translateAlternateColorCodes('&', "&8[&b" + WaterHub.getPlugin().getDescription().getName() + "&8]&r " + message));
    }

    public void update(final int serviceId, final int projectID) {
        new Metrics(WaterHub.getPlugin(), serviceId);
        if (Boolean.TRUE.equals(config.getBoolean("check-updates"))) {
            UpdateUtil updateChecker = new UpdateUtil(WaterHub.getPlugin(), projectID);
            try {
                if (updateChecker.checkupdate()) {
                    log("§8§l----------------------------");
                    log("    §8• §bWaterHub §8•");
                    log("");
                    log("§8× §7Update Available!");
                    log("§8× §7Download it from Spigot!");
                    log("");
                    log("§8§l----------------------------");
                } else {
                    log("§8§l----------------------------");
                    log("    §8• §bWaterHub §8•");
                    log("");
                    log("§8× §7You are using the Latest Version!");
                    log("");
                    log("§8§l----------------------------");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log("&cFound some problems with Update Module!");
            }
        }
    }

    public void loadconfig() {
        try {
            WaterHub.config = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(WaterHub.getPlugin().getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
            Util.log("&cError with loading config.yml!");
        }
    }
}
