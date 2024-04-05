package me.dalynkaa.bedwarslobby.utils;

import java.util.UUID;

public class UUIDUtils {
    public static UUID getUUID(String uuid, UUID def){
        if (uuid == null){
            return def;
        }else {
            return UUID.fromString(uuid);
        }
    }
    public static String UUIDtoString(UUID uuid, String def){
        if (uuid == null){
            return def;
        }else {
            return uuid.toString();
        }
    }
}
