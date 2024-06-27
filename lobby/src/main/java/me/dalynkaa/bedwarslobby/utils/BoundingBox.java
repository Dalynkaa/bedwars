package me.dalynkaa.bedwarslobby.utils;

import org.bukkit.util.Vector;

public class BoundingBox {

    private final double x1;
    private final double y1;
    private final double z1;
    private final double x2;
    private final double y2;
    private final double z2;

    public BoundingBox(double x1, double y1, double z1, double x2, double y2, double z2) {
        this.x1 = x1;
        this.y1 = y1;
        this.z1 = z1;
        this.x2 = x2;
        this.y2 = y2;
        this.z2 = z2;
    }

    public boolean contains(Vector vector) {
        boolean x = vector.getX() <= x1 && vector.getX() >= x2;
        boolean y = vector.getY() <= y1 && vector.getY() >= y2;
        boolean z = vector.getZ() <= z1 && vector.getZ() >= z2;
        return x && y && z;
    }

    public static BoundingBox of(Vector min, Vector max) {
        return new BoundingBox(max.getX(), max.getY(), max.getZ(), min.getX(), min.getY(), min.getZ());
    }

    @Override
    public String toString() {
        return "BoundingBox{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", z1=" + z1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                ", z2=" + z2 +
                '}';
    }
}
