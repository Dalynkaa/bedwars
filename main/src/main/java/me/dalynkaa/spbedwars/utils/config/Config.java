package me.dalynkaa.spbedwars.utils.config;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.dataclasses.enums.ServerType;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.UUID;

public class Config {


    public static ServerType getServerType() {
        FileConfiguration config = SPBedWars.getInstance().getConfig();
        return ServerType.valueOf(config.getString("server.type", ServerType.CLASSIC.toString()));
    }

    ;

    public static String getServerName() {
        FileConfiguration config = SPBedWars.getInstance().getConfig();
        return config.getString("server.name", "empty");
    }

    ;

    public static UUID getServerId() {
        FileConfiguration config = SPBedWars.getInstance().getConfig();
        String sevrerId = config.getString("server.id", null);
        UUID serverUUID;
        if (sevrerId == null) {
            serverUUID = UUID.randomUUID();
            config.set("server.id", serverUUID.toString());
            SPBedWars.getInstance().saveConfig();
        } else {
            serverUUID = UUID.fromString(sevrerId);
        }
        return serverUUID;
    }

    ;

    public static Boolean getServerEdit() {
        FileConfiguration config = SPBedWars.getInstance().getConfig();
        return config.getBoolean("server.edit", false);
    }

    public static void setServerEdit(Boolean edit) {
        FileConfiguration config = SPBedWars.getInstance().getConfig();
        config.set("server.edit", edit);
        SPBedWars.getInstance().saveConfig();
    }
}
