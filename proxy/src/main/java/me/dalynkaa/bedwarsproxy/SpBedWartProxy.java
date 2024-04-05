package me.dalynkaa.bedwarsproxy;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import com.google.inject.Inject;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.PluginDescription;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.messages.MinecraftChannelIdentifier;
import com.velocitypowered.api.proxy.server.ServerInfo;
import me.dalynkaa.bedwarsproxy.data.PlayerServerMovePacket;
import net.kyori.adventure.text.Component;
import org.slf4j.Logger;

import java.net.InetSocketAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@Plugin(
        id = "spbedwarsproxy",
        name = "SpBedWarsProxy",
        version = "1.0"
)
public class SpBedWartProxy {

    @Inject
    private Logger logger;
    @Inject
    private ProxyServer proxyServer;
    public static final MinecraftChannelIdentifier IDENTIFIER = MinecraftChannelIdentifier.from("bedwars:move");
    private final PluginDescription description;

    @Inject
    public SpBedWartProxy(PluginDescription description) {
        this.description = description;
    }

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        logger.info("SpBedWarsProxy has been initialized!");
        proxyServer.getChannelRegistrar().register(IDENTIFIER);
    }

    @Subscribe
    public void onPluginMessageFromPlayer(PluginMessageEvent event) {
        saveConfig();
        if (!Objects.equals(event.getIdentifier().getId(), IDENTIFIER.getId())) {
            return;
        }
        ByteArrayDataInput in = ByteStreams.newDataInput(event.getData());
        // handle packet data
        String subChannel = in.readUTF();
        if (subChannel.equals("move")) {
            String json = in.readUTF();
            PlayerServerMovePacket packet = PlayerServerMovePacket.fromJson(json);
            Player player = proxyServer.getPlayer(packet.getPlayerUUID()).orElse(null);
            proxyServer.getServer(packet.getServerName()).ifPresent(server -> {
                player.sendMessage(Component.text("You have been moved to " + server.getServerInfo().getName()));
                player.createConnectionRequest(server).fireAndForget();
            });
        }
    }

    private void saveConfig() {
        Path dataFolder = description.getSource().get().getParent();
        Path file = Paths.get(dataFolder.toString(), "config.yml");
    }


    public void registerServer(String ip, int port, String serverName) {
        ServerInfo serverInfo = new ServerInfo(serverName, new InetSocketAddress(ip, port));
        proxyServer.registerServer(serverInfo);
    }

    public Logger getLogger() {
        return logger;
    }

    public ProxyServer getProxyServer() {
        return proxyServer;
    }
}
