package dev.justinf.mczombies.game.map;

public class MapOffset {

    private final double x;
    private final double y;
    private final double z;
    private final float pitch;
    private final float yaw;

    public MapOffset(double x, double y, double z, float pitch, float yaw) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.pitch = pitch;
        this.yaw = yaw;
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

    public float getPitch() {
        return pitch;
    }

    public float getYaw() {
        return yaw;
    }
}