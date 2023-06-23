package com.rdproject.util;

import java.io.*;
import java.net.*;

import com.rdproject.WaterHub;
import lombok.*;

/**
 * Utility Class
 *
 * @author RDProject on 23.06.2023
 */
public class UpdateUtil {

    // Variables
    private final URL checkURL;
    private String newVersion;

    // Constant
    private final String version = WaterHub.PLUGIN.getPlugin().getDescription().getVersion();

    @SneakyThrows
    public UpdateUtil() {
        this.newVersion = version;
        this.checkURL = new URL("https://api.spigotmc.org/legacy/update.php?resource=99826");
    }

    @SneakyThrows
    public boolean checkUpdate() {
        final URLConnection con = this.checkURL.openConnection();
        this.newVersion = (new BufferedReader(new InputStreamReader(con.getInputStream()))).readLine();
        return !version.equals(this.newVersion);
    }

}