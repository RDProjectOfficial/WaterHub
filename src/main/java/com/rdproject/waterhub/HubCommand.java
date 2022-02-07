package com.rdproject.waterhub;

import net.md_5.bungee.api.config.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;
import java.util.*;

import static com.rdproject.waterhub.WaterHub.*;

@SuppressWarnings("ALL")
public class HubCommand implements Listener {
    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if (p.getName().equals(DEV)) {
            p.sendMessage("§8§l----------------------------");
            p.sendMessage(" §8• §bWaterHub §8•");
            p.sendMessage("");
            p.sendMessage("§8× §7This server using WaterHub!");
            p.sendMessage("§8× §7Version: §b" + VERSION);
            p.sendMessage("§8× §7Author: §b " + DEV_COMPANY);
            p.sendMessage("");
            p.sendMessage("§8§l----------------------------");
        }
    }

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.isCommand()) {
            ProxiedPlayer player = (ProxiedPlayer) e.getSender();
            List<String> commands = WaterHub.cg.getStringList("Aliases-Hub");
            List<String> denyServers = WaterHub.cg.getStringList("DisabledServers");
            String lobbyServer = WaterHub.cg.getString("LobbyServer");
            ServerInfo sv = WaterHub.getInstance().getProxy().getServerInfo(lobbyServer);
            if (commands.contains(e.getMessage())) {
                if (!denyServers.contains(player.getServer().getInfo().getName())) {
                    if (!player.getServer().getInfo().getName().equals(lobbyServer)) {
                        e.setCancelled(true);
                        player.connect(sv);
                        player.sendMessage(WaterHub.cg.getString("Prefix") + WaterHub.cg.getString("ConnectedServer").replace("&", "§"));
                    } else {
                        player.sendMessage(WaterHub.cg.getString("Prefix") + WaterHub.cg.getString("MsgAlreadyConnected").replace("&", "§"));
                    }
                } else {
                    player.sendMessage(WaterHub.cg.getString("Prefix") + WaterHub.cg.getString("MsgDisabledOnThatServer").replace("&", "§"));;
                }
                e.setCancelled(true);
            }
            if (e.getMessage().equalsIgnoreCase("/hubreload")) {
                if (player.hasPermission("WaterHub.HubReload")) {
                    WaterHub.LoadConfigs();
                    player.sendMessage(WaterHub.cg.getString("Prefix") + "§bConfiguration successfully reloaded!");
                } else {
                }
                e.setCancelled(true);
            }
        }
    }
}
