package co.id.telkom.epuskesmas.utils;

import java.net.Inet4Address;

public class ServerUtils {

    // Get Server IP
    public String getServerIP() {
        String serverIp = "";
        String serverIpEnv = System.getenv("SERVER_PUBLIC_IP");

        if (serverIpEnv != null && !serverIpEnv.isEmpty()) {
            serverIp = serverIpEnv;
        } else {
            try {
                serverIp = Inet4Address.getLocalHost().getHostAddress();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return serverIp;
    }

    // Get Server Port
    public String getServerPort() {
        String serverPort = "";
        String serverPortEnv = System.getenv("SERVER_PUBLIC_PORT");

        if (serverPortEnv != null && !serverPortEnv.isEmpty()) {
            serverPort = serverPortEnv;
        } else {
            // TODO
            // - Need to get port dynamicly assign by application
            serverPort = "8080";
        }

        return serverPort;
    }

    // Get Base URL
    public String getBaseURL(String fallbackScheme) {
        String serverBaseURL = "";

        if (fallbackScheme.isEmpty()) {
            fallbackScheme = "http";
        }

        switch (getServerPort()) {
            case "80":
                serverBaseURL = "http://" + getServerIP();
                break;

            case "443":
                serverBaseURL = "https://" + getServerIP();
                break;

            default:
                serverBaseURL = fallbackScheme + "://" + getServerIP() + ":" + getServerPort();
        }

        return serverBaseURL;
    }
}
