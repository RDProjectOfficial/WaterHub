package com.rdproject.plugin;

import com.rdproject.*;
import com.rdproject.util.*;
import lombok.*;
import net.md_5.bungee.api.config.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;
import java.util.*;

public class HubListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.isCommand()) {
            ProxiedPlayer p = (ProxiedPlayer) e.getSender();
            List<String> commands = WaterHub.config.getStringList("aliases-hub");
            List<String> denyServers = WaterHub.config.getStringList("disabled-server");
            List<String> lobbyServer = WaterHub.config.getStringList("random.lobby-server");
            String lobby = WaterHub.config.getString("lobby-server");
            if (commands.contains(e.getMessage())) {
                    if (!denyServers.contains(p.getServer().getInfo().getName())) {
                        if (!p.getServer().getInfo().getName().equals(getRandom(lobbyServer)) || p.getServer().getInfo().getName().equals(lobby)) {
                            e.setCancelled(true);
                            if (WaterHub.config.getBoolean("random.toggle")) {
                                ServerInfo sv = WaterHub.getPlugin().getProxy().getServerInfo(getRandom(lobbyServer));
                                if (sv != null) {
                                    p.connect(sv);
                                    p.sendMessage(Util.text(WaterHub.getPrefix() + WaterHub.config.getString("connected-server-random")));
                                } else {
                                    p.sendMessage(Util.text(WaterHub.getPrefix() + WaterHub.config.getString("server-is-null")));
                                }
                            } else {
                                ServerInfo sv = WaterHub.getPlugin().getProxy().getServerInfo(lobby);
                                p.connect(sv);
                                p.sendMessage(Util.text(WaterHub.getPrefix() + WaterHub.config.getString("connected-server")));
                            }
                        } else {
                            p.sendMessage(Util.text(WaterHub.getPrefix() + WaterHub.config.getString("already-connected")));
                        }
                    } else {
                        p.sendMessage(Util.text(WaterHub.getPrefix() + WaterHub.config.getString("disabled-on-that-server")));
                    }
                    e.setCancelled(true);
                }
        }
    }

    private String getRandom(@NonNull List<String> list) {
        Random random = new Random();
        synchronized (WaterHub.getPlugin()) {
            return list.get(random.nextInt(list.size()));
        }
    }

}
