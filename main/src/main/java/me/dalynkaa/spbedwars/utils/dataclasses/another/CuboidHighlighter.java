package me.dalynkaa.spbedwars.utils.dataclasses.another;

import com.destroystokyo.paper.ParticleBuilder;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class CuboidHighlighter {
    Location location;
    Color color;
    public CuboidHighlighter(Location location, Color color){
        this.location = location;
        this.color = color;
    }
    public void spawnParticle() {
        Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(this.color.getRed(), this.color.getGreen(), this.color.getBlue()), (float) 0.5);
        new ParticleBuilder(Particle.REDSTONE)
                .location(this.location)
                .count(1)
                .data(dustOptions)
                .spawn();
    }

}
