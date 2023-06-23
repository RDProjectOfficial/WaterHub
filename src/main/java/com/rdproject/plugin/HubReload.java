package com.rdproject.plugin;

import com.rdproject.*;
import com.rdproject.util.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.Configuration;
import org.jetbrains.annotations.NotNull;

/**
 * Command Class
 *
 * @author RDProject on 23.06.2023
 */
public class HubReload extends Command {

    public HubReload() {
        super("hubreload");
    }

    @Override
    public void execute(@NotNull CommandSender sender, String[] args) {
        // Constants
        final Configuration configuration = WaterHub.PLUGIN.getConfig();
        final String m = "messages.";

        // Permission check
        if (!sender.hasPermission(configuration.getString("settings.permission"))) {
            ChatUtil.sendMessage(sender, configuration.getString(m + "no-permission"));
            return;
        }

        // Sending message to sender
        ChatUtil.sendMessage(sender, configuration.getString(m + "config-reloaded"));

        // Reloading configuration
        WaterHub.PLUGIN.loadConfig();
    }

}

