package me.dalynkaa.bedwarslobby.listeners;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.events.MarkerMoveEvent;
import me.dalynkaa.bedwarslobby.proxyUtils.data.enums.MessageType;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.utils.dtos.markers.ActionMarkerData;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.metadata.FixedMetadataValue;

public class JumpPadListener implements Listener {

    private final SPBedWarsLobby plugin;

    public JumpPadListener(SPBedWarsLobby spBedWars) {
        spBedWars.getServer().getPluginManager().registerEvents(this, spBedWars);
        this.plugin = spBedWars;
    }

    @EventHandler
    public void jumpPadListener(MarkerMoveEvent event) {
        BPlayer bPlayer = event.getPlayer();
        Player player = bPlayer.getPlayer();
        Location playerLocation = player.getLocation().add(0, -1, 0);

        boolean isJumping = playerLocation.getBlock().getType() == Material.AIR;

        if (!player.hasMetadata("hasJumped")) {
            player.setMetadata("hasJumped", new FixedMetadataValue(plugin, false));
        }

        boolean hasJumped = player.getMetadata("hasJumped").get(0).asBoolean();

        if (isJumping && !hasJumped) {
            if (!event.getMarkerData().getId().equals("action")) {
                return;
            }
            ActionMarkerData actionMarkerData = ActionMarkerData.parse(event.getRawMarkerData());
            if (actionMarkerData == null) {
                return;
            }
            if (!actionMarkerData.getAction().equals("force_jump")) {
                return;
            }
            player.setVelocity(actionMarkerData.getForce());
            bPlayer.sendMessage("Jumped", MessageType.SUCCESS);

            player.setMetadata("hasJumped", new FixedMetadataValue(plugin, true));
        } else if (!isJumping) {

            player.setMetadata("hasJumped", new FixedMetadataValue(plugin, false));
        }
    }
}
