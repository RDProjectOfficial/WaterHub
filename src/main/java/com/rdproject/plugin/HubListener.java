package com.rdproject.plugin;

import com.rdproject.*;
import com.rdproject.util.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.event.*;

/**
 * Listener Class
 *
 * @author RDProject on 23.06.2023
 */
public class HubListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent event) {
        // Checking is command or not?
        if (event.isCommand()) {
            // Constants
            final Configuration configuration = WaterHub.PLUGIN.getConfig();
            final ProxiedPlayer player = (ProxiedPlayer) event.getSender();

            // Contractions
            final String s = "messages.server.";
            final String l = "settings.lobby.";

            // Main code
            if (configuration.getStringList(l + "aliases").contains(event.getMessage())) {
                // Constant
                final String name = player.getServer().getInfo().getName();

                // Checking disabled servers...
                if (!configuration.getStringList(l + "disabled").contains(name)) {
                    // Constant
                    final String lobby = configuration.getString(l + "server");

                    if (lobby == null || lobby.isEmpty()) {
                        event.setCancelled(true);
                        ChatUtil.sendMessage(player, configuration.getString(s + "not-found"));
                        return;
                    }

                    // Checking player is there in this server or not?
                    if (!name.equals(lobby)) {
                        // Connecting to lobby...
                        player.connect(WaterHub.PLUGIN.getPlugin().getProxy().getServerInfo(lobby));

                        // Sending message to player
                        ChatUtil.sendMessage(player, configuration.getString(s + "connected"));
                    } else {
                        // Sending message to player
                        ChatUtil.sendMessage(player, configuration.getString(s + "already"));
                    }
                } else {
                    // Sending message to player
                    ChatUtil.sendMessage(player, configuration.getString(s + "disabled"));
                }

                // Need to avoid bugs
                event.setCancelled(true);
            }
        }
    }

}
