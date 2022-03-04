package com.rdproject.util;

import java.io.*;
import java.net.*;

import lombok.*;
import net.md_5.bungee.api.plugin.*;

public class UpdateUtil {

    private URL checkURL;

    private String newVersion;

    private final Plugin plugin;

    public UpdateUtil(@NonNull final Plugin plugin, final int projectID) {
        this.plugin = plugin;
        this.newVersion = plugin.getDescription().getVersion();
        try {
            this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=" + projectID);
        } catch (MalformedURLException ignored) { }
    }

    public boolean checkupdate() throws IOException {
        URLConnection con = this.checkURL.openConnection();
        this.newVersion = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
        return !this.plugin.getDescription().getVersion().equals(this.newVersion);
    }
}