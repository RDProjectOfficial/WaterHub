package com.rdproject.waterhub.plugin;

import net.md_5.bungee.api.config.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;
import java.util.*;

import static com.rdproject.waterhub.utils.ConstantsUtil.*;

public class HubListener implements Listener {

    @EventHandler
    public void onChat(ChatEvent e) {
        if (e.isCommand()) {
            String MSGDisServer = cg.getString("MsgDisabledOnThatServer");
            String MSGAlreadyCon = cg.getString("MsgAlreadyConnected");
            String Connected = cg.getString("ConnectedServer");
            ProxiedPlayer player = (ProxiedPlayer) e.getSender();
            List<String> commands = cg.getStringList("Aliases-Hub");
            List<String> denyServers = cg.getStringList("DisabledServers");
            String lobbyServer = cg.getString("LobbyServer");
            ServerInfo sv = plugin.getProxy().getServerInfo(lobbyServer);
            if (commands.contains(e.getMessage())) {
                if (!denyServers.contains(player.getServer().getInfo().getName())) {
                    if (!player.getServer().getInfo().getName().equals(lobbyServer)) {
                        e.setCancelled(true);
                        player.connect(sv);
                        player.sendMessage(formatComponent(PREFIX + Connected.replace("&", "§")));
                    } else {
                        player.sendMessage(formatComponent(PREFIX + MSGAlreadyCon.replace("&", "§")));
                    }
                } else {
                    player.sendMessage(formatComponent(PREFIX + MSGDisServer.replace("&", "§")));
                }
                e.setCancelled(true);
            }
        }
    }

}
