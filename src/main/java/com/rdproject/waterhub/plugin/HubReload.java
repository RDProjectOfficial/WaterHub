package com.rdproject.waterhub.plugin;

import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;

import static com.rdproject.waterhub.plugin.LoaderUtil.*;
import static com.rdproject.waterhub.utils.ConstantsUtil.*;

public class HubReload extends Command {

    public HubReload() {
        super("hubreload");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        String CFG = cg.getString("Config-Reloaded");
        if (sender.hasPermission("WaterHub.HubReload")) {
            sender.sendMessage(formatComponent(PREFIX + CFG));
            LoadConfig();
        } else {
            sender.sendMessage(formatComponent(NOPERM));
        }
    }
}

