package me.dalynkaa.bedwarslobby.listeners;

import com.destroystokyo.paper.event.player.PlayerJumpEvent;
import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.utils.Logger;
import me.dalynkaa.bedwarslobby.utils.dtos.markers.ActionRestriktMarkerData;
import me.dalynkaa.bedwarslobby.utils.dtos.markers.MarkerData;
import me.dalynkaa.bedwarslobby.utils.dtos.markers.enums.RESTRICT_ACTION;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.List;

public class RestrictedActionsListener implements Listener {

    public RestrictedActionsListener(SPBedWarsLobby spBedWarsLobby) {
        spBedWarsLobby.getServer().getPluginManager().registerEvents(this, spBedWarsLobby);
    }

    @EventHandler
    public void restrictedActionListener(PlayerToggleSneakEvent event) {
        BPlayer bPlayer = BPlayer.getByUUID(event.getPlayer().getUniqueId());
        if (bPlayer == null) {
            return;
        }
        List<MarkerData> markers = bPlayer.getMarkersList();
        for (MarkerData markerData : markers) {
            if (!markerData.getId().equals("action_restrikt")) {
                Logger.debug("Marker is not action_restrikt");
                continue;
            }
            ActionRestriktMarkerData actionRestriktMarkerData = markerData.getActionRestriktMarkerData();
            if (actionRestriktMarkerData == null) {
                Logger.debug("ActionRestriktMarkerData is null");
                continue;
            }
            if (!actionRestriktMarkerData.getAction().equals(RESTRICT_ACTION.SNEAK)) {
                Logger.debug("Action is not sneaking");
                continue;
            }
            if (event.isSneaking()) {
                Logger.debug("Player is sneaking");
                event.getPlayer().setSneaking(false);
                event.setCancelled(true);
            }
            return;
        }
    }

    @EventHandler
    public void restrictedActionListener(PlayerJumpEvent event) {
        BPlayer bPlayer = BPlayer.getByUUID(event.getPlayer().getUniqueId());
        if (bPlayer == null) {
            return;
        }
        List<MarkerData> markers = bPlayer.getMarkersList();
        for (MarkerData markerData : markers) {
            if (!markerData.getId().equals("action_restrikt")) {
                continue;
            }
            ActionRestriktMarkerData actionRestriktMarkerData = markerData.getActionRestriktMarkerData();
            if (actionRestriktMarkerData == null) {
                continue;
            }
            if (!actionRestriktMarkerData.getAction().equals(RESTRICT_ACTION.JUMP)) {
                continue;
            }
            event.setCancelled(true);
            return;
        }

    }
}
