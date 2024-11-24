/*
 * Copyright (c) 2024 RDProject
 * Project name: WaterHub
 * All rights reserved.
 */

package com.rdproject.waterhub.commands;

import com.rdproject.waterhub.WaterHub;
import com.rdproject.waterhub.utilities.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;

/**
 * Command Class
 *
 * @author RDProject on 22.11.2024
 */
public class HubCommand extends Command {

    public HubCommand() {
        super("hub", "", WaterHub.getConfiguration().getStringList("settings.hub-command.aliases").toArray(new String[0]));
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        Configuration config = WaterHub.getConfiguration();
        if (!(commandSender instanceof ProxiedPlayer)) {
            commandSender.sendMessage(ChatUtil.colorWithPrefix("only-players"));
            return;
        }

        ProxiedPlayer player = (ProxiedPlayer) commandSender;
        if (!player.hasPermission(config.getString("settings.hub-command.permission"))) {
            player.sendMessage(ChatUtil.colorWithPrefix("no-permission")
                    .replace("{permission}", config.getString("settings.hub-command.permission")));
            return;
        }

        String currentServer = player.getServer().getInfo().getName();
        if (config.getStringList("settings.hub.disabled-servers").contains(currentServer)) {
            player.sendMessage(ChatUtil.colorWithPrefix("hub.command-disabled").replace("{current_server}", currentServer));
            return;
        }

        ServerInfo hubServer = WaterHub.getInstance().getProxy().getServerInfo(config.getString("settings.hub.server"));
        if (hubServer == null) {
            player.sendMessage(ChatUtil.colorWithPrefix("hub.server-not-found").replace("{hub_server}", config.getString("settings.hub.server")));
            return;
        }

        if (WaterHub.getInstance().getProxy().getServerInfo(currentServer).equals(hubServer)) {
            player.sendMessage(ChatUtil.colorWithPrefix("hub.already-connected").replace("{hub_server}", hubServer.getName()));
            return;
        }

        player.connect(hubServer);
        player.sendMessage(ChatUtil.colorWithPrefix("hub.connecting").replace("{hub_server}", hubServer.getName()));
    }

}