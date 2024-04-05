package me.dalynkaa.bedwarslobby.listeners;

import me.dalynkaa.bedwarslobby.SPBedWarsLobby;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class JumpPadListener implements Listener {

    public JumpPadListener(SPBedWarsLobby spBedWars) {
        spBedWars.getServer().getPluginManager().registerEvents(this, spBedWars);
    }

    @EventHandler
    public void jumpPadListener(PlayerMoveEvent event) {
        Location location = event.getPlayer().getLocation();
        Player player = event.getPlayer();
        if (location.add(0, -1, 0).getBlock().getType().equals(Material.RED_GLAZED_TERRACOTTA)) {
            // push player only when player jumps on the jump pad
            if (event.getPlayer().isOnGround()) {
                return;
            }
            player.setGliding(true);
            location.getWorld().spawnParticle(Particle.FLAME, location, 10, 0.5, 0, 0.5, 0.1);
            player.playSound(player, Sound.ITEM_TRIDENT_RIPTIDE_2, 1, 1);
            event.getPlayer().setVelocity(event.getPlayer().getLocation().getDirection().multiply(2).setY(1));
        }
    }
}
