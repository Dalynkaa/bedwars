package me.dalynkaa.spbedwars.mainListener;

import me.dalynkaa.spbedwars.SPBedWars;
import me.dalynkaa.spbedwars.utils.Logger;
import me.dalynkaa.spbedwars.utils.dataclasses.game.BWGame;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class TntListener implements Listener {
    public TntListener(SPBedWars main) {
        main.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void tntPlaceListener(BlockPlaceEvent event) {
        if (!event.getBlock().getType().equals(Material.TNT)) {
            return;
        }
        if (!event.getPlayer().getInventory().getItemInMainHand().getType().equals(Material.TNT)) {
            return;
        }
        if (!event.getPlayer().getInventory().getItemInMainHand().getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("tnt"))) {
            return;
        }
        Location placedLocation = event.getBlock().getLocation().add(0.5, 1, 0.5);
        placedLocation.getWorld().spawn(placedLocation, TNTPrimed.class);
        event.setCancelled(true);
        event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
    }

    @EventHandler
    public void fireBallLaunch(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if (!event.getAction().isRightClick()) {
            return;
        }
        if (item == null) {
            return;
        }
        if (!item.getType().equals(Material.FIRE_CHARGE)) {
            return;
        }
        if (!item.getItemMeta().getPersistentDataContainer().has(NamespacedKey.fromString("fireball"))) {
            return;
        }
        Player player = event.getPlayer();
        Fireball fireball = player.launchProjectile(Fireball.class);
        fireball.setYield(0);
        fireball.setIsIncendiary(true);
        fireball.setVelocity(player.getLocation().getDirection().multiply(1));
        event.setCancelled(true);
        player.setCooldown(Material.FIRE_CHARGE, 20);
        item.setAmount(item.getAmount() - 1);
    }

    @EventHandler
    public void projectileHitEvent(ProjectileHitEvent event) {
        if (event.getEntity() instanceof Fireball) {
            Fireball fireball = (Fireball) event.getEntity();
            fireball.getWorld().createExplosion(fireball.getLocation(), 5, false, true, fireball);
        }
    }

    @EventHandler
    public void fireballExplode(ExplosionPrimeEvent event) {
        if (event.getEntity() instanceof Fireball) {
            Logger.debug("Fireball explode - 1");
            event.setRadius(5);
            event.setFire(false);
        }
    }

    @EventHandler
    public void blockExplode(BlockExplodeEvent event) {
        Logger.debug(event.getBlock().getType() + " - block type");
        BWGame game = BWGame.getGameByWorld(event.getBlock().getWorld());
        event.setCancelled(true);
        for (Block block : event.blockList()) {
            if (game.getPlacedBlocks().contains(block)) {
                if (block.getType().equals(Material.GLASS)) {
                    continue;
                }
                block.setType(Material.AIR);
            }
        }
    }

    @EventHandler
    public void onTntExplode(EntityExplodeEvent event) {
        BWGame game = BWGame.getGameByWorld(event.getLocation().getWorld());
        Location location = event.getLocation();
        event.setCancelled(true);
        Logger.debug(event.getEntityType().toString() + " - entity type");
        if (event.getEntityType().equals(EntityType.PRIMED_TNT)) {
            for (Block block : event.blockList()) {
                if (game.getPlacedBlocks().contains(block)) {
                    if (block.getType().equals(Material.GLASS)) {
                        continue;
                    }
                    block.setType(Material.AIR);
                }
            }
            double maxDamageDistance = 5.0;
            for (Entity entity : event.getLocation().getNearbyEntitiesByType(Player.class, 5)) {
                Player player = (Player) entity;
                Location playerLocation = player.getLocation();
                double distance = location.distance(playerLocation);
                if (distance < maxDamageDistance) {
                    double power = 6 / (distance + 1);
                    Vector vector = playerLocation.toVector().subtract(location.toVector()).normalize().add(new Vector(0, 1, 0)).multiply(power);
                    player.setVelocity(vector);
                    double damage = (maxDamageDistance - distance) / maxDamageDistance * 10.0;
                    player.damage(damage);

                }
            }
        } else if (event.getEntity() instanceof Fireball) {
            for (Block block : event.blockList()) {
                if (game.getPlacedBlocks().contains(block)) {
                    if (block.getType().equals(Material.GLASS)) {
                        continue;
                    }
                    block.setType(Material.AIR);
                }
            }
            double maxDamageDistance = 5.0;
            for (Entity entity : event.getLocation().getNearbyEntitiesByType(Player.class, 5)) {
                Player player = (Player) entity;
                Location playerLocation = player.getLocation();
                double distance = location.distance(playerLocation);
                if (distance < maxDamageDistance) {
                    double power = 10 / (distance + 1);
                    Vector vector = playerLocation.toVector().subtract(location.toVector()).normalize().add(new Vector(0, 2, 0)).multiply(power);
                    player.setVelocity(vector);
                    double damage = (maxDamageDistance - distance) / maxDamageDistance * 10.0;
                    player.damage(damage);

                }
            }
        }
    }
}
