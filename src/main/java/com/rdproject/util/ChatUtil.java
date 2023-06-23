package com.rdproject.util;

import com.rdproject.WaterHub;
import lombok.experimental.UtilityClass;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import org.jetbrains.annotations.NotNull;

/**
 * Utility Class
 *
 * @author RDProject on 23.06.2023
 */
@UtilityClass
public class ChatUtil {

    public void sendMessage(@NotNull CommandSender commandSender, String text) {
        commandSender.sendMessage(new TextComponent(ChatColor.translateAlternateColorCodes('&', (WaterHub.PLUGIN.getConfig().getString("messages.prefix") + text))));
    }

}