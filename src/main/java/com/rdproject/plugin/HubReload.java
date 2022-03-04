package com.rdproject.plugin;

import com.rdproject.*;
import com.rdproject.util.*;
import lombok.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;

public class HubReload extends Command {

    public HubReload() {
        super("hubreload");
    }

    @Override
    public void execute(@NonNull CommandSender cs, String[] args) {
        if (cs.hasPermission(WaterHub.config.getString("permission-reload"))) {
            cs.sendMessage(Util.text(WaterHub.getPrefix() + WaterHub.config.getString("config-reloaded")));
            Util.loadconfig();
        } else {
            cs.sendMessage(Util.text(WaterHub.getPrefix() + WaterHub.getNoperm()));
        }
    }

}

