package eu.imposdev.modularcore.util;

import eu.imposdev.modularcore.ModularCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public final class UpdateChecker {

    private final Logger logger;

    private String url = "https://imposdev.eu/repo/eu/imposdev/modularcore/";
    private String id = "version";

    private boolean isAvailable;

    public UpdateChecker() {
        this.logger = LoggerFactory.getLogger(UpdateChecker.class);
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void check() {
        isAvailable = checkUpdate();
    }

    private boolean checkUpdate() {
        this.logger.info("Check for updates...");
        try {
            String localVersion = ModularCore.getInstance().getVersion();
            HttpsURLConnection connection = (HttpsURLConnection) new URL(url + id).openConnection();
            connection.setRequestMethod("GET");
            String raw = new BufferedReader(new InputStreamReader(connection.getInputStream())).readLine();

            String remoteVersion;
            if (raw.contains("-")) {
                remoteVersion = raw.split("-")[0].trim();
            } else {
                remoteVersion = raw;
            }

            if (!localVersion.equalsIgnoreCase(remoteVersion))
                return true;

        } catch (IOException e) {
            return false;
        }
        return false;
    }

}
