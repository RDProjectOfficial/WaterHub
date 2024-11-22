/*
 * Copyright (c) 2024 RDProject
 * Project name: WaterHub
 * All rights reserved.
 */

package com.rdproject.waterhub.commands;

import com.rdproject.waterhub.WaterHub;
import com.rdproject.waterhub.utilities.ChatUtil;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;

/**
 * Command Class
 *
 * @author RDProject on 22.11.2024
 */
public class HubReloadCommand extends Command {

    public HubReloadCommand() {
        super("hub-reload");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission(WaterHub.getConfiguration().getString("settings.reload-command-permission"))) {
            commandSender.sendMessage(ChatUtil.colorWithPrefix("no-permission"));
            return;
        }
        ChatUtil.COLORED_STRINGS.clear();
        WaterHub.loadConfiguration();
        commandSender.sendMessage(ChatUtil.colorWithPrefix("config-reloaded"));
    }

}