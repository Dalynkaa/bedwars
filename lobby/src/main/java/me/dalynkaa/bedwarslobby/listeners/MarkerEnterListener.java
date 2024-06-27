package me.dalynkaa.bedwarslobby.listeners;

import com.google.gson.Gson;
import de.tr7zw.nbtapi.NBT;
import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import me.dalynkaa.bedwarslobby.events.MarkerEnterEvent;
import me.dalynkaa.bedwarslobby.events.MarkerExitEvent;
import me.dalynkaa.bedwarslobby.events.MarkerMoveEvent;
import me.dalynkaa.bedwarslobby.proxyUtils.data.player.BPlayer;
import me.dalynkaa.bedwarslobby.utils.BoundingBox;
import me.dalynkaa.bedwarslobby.utils.dtos.markers.MarkerData;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Marker;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MarkerEnterListener implements Listener {

    private final SPBedWarsLobby spBedWars;
    private final Gson gson = new Gson(); // Для сериализации и десериализации JSON

    public MarkerEnterListener(SPBedWarsLobby spBedWars) {
        spBedWars.getServer().getPluginManager().registerEvents(this, spBedWars);
        this.spBedWars = spBedWars;
    }

    @EventHandler
    public void jumpPadListener(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        World playerWorld = player.getWorld();
        Vector playerLocation = player.getLocation().toVector();
        List<Marker> markers = playerWorld.getNearbyEntitiesByType(Marker.class, player.getLocation(), 20, 20, 20).stream().collect(Collectors.toList());

        if (markers.isEmpty()) {
            return;
        }

        // Сначала синхронно получаем данные NBT маркеров
        List<String> rawMarkers = new ArrayList<>();
        List<MarkerData> markerDataList = markers.stream().map(marker -> {
            String readableNBT = NBT.get(marker, nbt -> (String) nbt.getCompound("data").toString());
            rawMarkers.add(readableNBT);
            return MarkerData.parse(readableNBT);
        }).collect(Collectors.toList());

        // Получаем текущие маркеры игрока из NBT
        String currentMarkersJson = NBT.getPersistentData(player, nbt -> {
            String data = nbt.getString("currentMarkers");
            return data;
        });
        List<String> currentMarkers = gson.fromJson(currentMarkersJson, List.class);
        if (currentMarkers == null) {
            currentMarkers = new ArrayList<>();
        }
        Set<String> newCurrentMarkers = new HashSet<>(currentMarkers);

        // Затем переходим на асинхронный поток для обработки данных
        Bukkit.getScheduler().runTaskAsynchronously(spBedWars, () -> {
            boolean markersUpdated = false;

            for (int i = 0; i < markers.size(); i++) {
                Marker marker = markers.get(i);
                MarkerData markerData = markerDataList.get(i);

                if (markerData != null) {
                    BoundingBox bb = markerData.getBoundingBox();
                    if (bb.contains(playerLocation)) {
                        // Если игрок находится в маркере, добавляем его в новый список
                        if (newCurrentMarkers.add(marker.getUniqueId().toString())) {
                            markersUpdated = true;
                            MarkerEnterEvent markerEnterEvent = new MarkerEnterEvent(markerData, BPlayer.getByUUID(player.getUniqueId()), playerWorld, rawMarkers.get(i));
                            Bukkit.getScheduler().runTask(spBedWars, () -> {
                                spBedWars.getServer().getPluginManager().callEvent(markerEnterEvent);
                            });
                        }
                        MarkerMoveEvent markerMoveEvent = new MarkerMoveEvent(markerData, BPlayer.getByUUID(player.getUniqueId()), playerWorld, rawMarkers.get(i));
                        Bukkit.getScheduler().runTask(spBedWars, () -> {
                            spBedWars.getServer().getPluginManager().callEvent(markerMoveEvent);
                        });
                    } else {
                        // Если игрок покинул маркер, удаляем его из нового списка
                        if (newCurrentMarkers.remove(marker.getUniqueId().toString())) {
                            markersUpdated = true;
                            MarkerExitEvent markerExitEvent = new MarkerExitEvent(markerData, BPlayer.getByUUID(player.getUniqueId()), playerWorld, rawMarkers.get(i));
                            Bukkit.getScheduler().runTask(spBedWars, () -> {
                                spBedWars.getServer().getPluginManager().callEvent(markerExitEvent);
                            });
                        }
                    }
                }
            }

            // Если список маркеров обновился, сохраняем его в NBT
            if (markersUpdated) {
                Bukkit.getScheduler().runTask(spBedWars, () -> {
                    String newCurrentMarkersJson = gson.toJson(new ArrayList<>(newCurrentMarkers));

                    NBT.modifyPersistentData(player, nbt -> {
                        nbt.setString("currentMarkers", newCurrentMarkersJson);
                    });
                });
            }
        });
    }

}
