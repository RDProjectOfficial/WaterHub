package com.rdproject;

import net.md_5.bungee.api.plugin.Plugin;

/**
 * Main Class
 *
 * @author RDProject on 03.06.2023
 */
public final class WaterHubPlugin extends Plugin {

    @Override
    public void onLoad() {
        WaterHub.PLUGIN.load(this);
    }

    @Override
    public void onEnable() {
        WaterHub.PLUGIN.start();
    }

    @Override
    public void onDisable() {
        WaterHub.PLUGIN.stop();
    }

}