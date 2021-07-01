package me.manaki.plugin.mysteryblocks.mysteryblock;

public class MysteryData {

    private final String id;
    private final String world;
    private final double x;
    private final double y;
    private final double z;

    public MysteryData(String id, String world, double x, double y, double z) {
        this.id = id;
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public String getId() {
        return id;
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

}
