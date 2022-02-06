package com.rdproject.waterhub;

import lombok.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;

@SuppressWarnings("ALL")
@Getter
public class HubReload implements Listener {
    @EventHandler
    public void onChat(ChatEvent e) {
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        if (e.isCommand()) {
            if (e.getMessage().equalsIgnoreCase("/hubreload")) {
                if (p.hasPermission("WaterHub.HubReload")) {
                    WaterHub.LoadConfigs();
                    p.sendMessage(WaterHub.cg.getString("Prefix") + "§bConfiguration successfully reloaded!");
                } else {
                    e.setCancelled(true);
                }
            }
        }
    }
}
