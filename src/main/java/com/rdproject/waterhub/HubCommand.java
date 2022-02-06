package com.rdproject.waterhub;

import lombok.*;
import net.md_5.bungee.api.config.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;
import java.util.*;

import static com.rdproject.waterhub.WaterHub.*;

@SuppressWarnings("ALL")
@Getter
public class HubCommand implements Listener {
    @EventHandler
    public void onJoin(PostLoginEvent e) {
        ProxiedPlayer p = e.getPlayer();
        if (p.getName().equals(DEV)) {
            p.sendMessage("§8m§l----------------------------");
            p.sendMessage(" §8• §bWaterHub §8•");
            p.sendMessage("");
            p.sendMessage("§8× §7This server using WaterHub!");
            p.sendMessage("§8× §7Version: §b" + WaterHub.getInstance().getDescription().getVersion());
            p.sendMessage("§8× §7Author: §b " + DEV_COMPANY);
            p.sendMessage("");
            p.sendMessage("§8m§l----------------------------");
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
                    }
                }
            }
        }
    }
}