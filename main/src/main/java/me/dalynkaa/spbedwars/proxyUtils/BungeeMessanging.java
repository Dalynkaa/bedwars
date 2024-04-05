package me.dalynkaa.spbedwars.proxyUtils;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.proxyUtils.data.PlayerServerMoveRegistrator;
import me.dalynkaa.spbedwars.utils.dataclasses.player.BPlayer;

public class BungeeMessanging {
    SPBedWars spBedWars;
    public BungeeMessanging(SPBedWars spBedWars) {
        this.spBedWars = spBedWars;
        spBedWars.getServer().getMessenger().registerOutgoingPluginChannel(spBedWars, "bedwars:move");
    }

    public static void sendServer(BPlayer player, String server) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("move");
        out.writeUTF(new PlayerServerMoveRegistrator(player.getUuid(), server).toJson());
        player.getPlayer().sendPluginMessage(SPBedWars.getInstance(), "bedwars:move", out.toByteArray());
    }
}
