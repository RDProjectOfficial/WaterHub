package com.rdproject.waterhub;

import lombok.*;
import net.md_5.bungee.api.chat.*;
import net.md_5.bungee.api.connection.*;
import net.md_5.bungee.api.event.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.event.*;

import static com.rdproject.waterhub.WaterHub.*;

@SuppressWarnings("ALL")
@Getter
public class HubAuthor implements Listener {
    @EventHandler
    public void onChat(ChatEvent e) {
        TextComponent system = new TextComponent("");
        system.setText(WaterHub.cg.getString("Prefix") + "§7This server using §b" + PLUGIN_NAME + " §8× §b" + DEV_COMPANY);
        system.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, "https://github.com/RDProjectOfficial"));
        system.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("§8• §cClick to Open URL!").create()));
        ProxiedPlayer p = (ProxiedPlayer) e.getSender();
        if (e.isCommand()) {
            if (e.getMessage().equalsIgnoreCase("/hubsystem")) {
                p.sendMessage(system);
            } else {
                e.setCancelled(true);
            }
        }
    }
}