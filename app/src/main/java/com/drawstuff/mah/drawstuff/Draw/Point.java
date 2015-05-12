package com.drawstuff.mah.drawstuff.Draw;

/**
 * @author greg
 * @since 6/26/13
 */
public class Point {
    int x;
    int y;

    // Required default constructor for Firebase serialization / deserialization
    @SuppressWarnings("unused")
    private Point() {
    }

    public Point(long x, long y) {
        this.x = (int) x;
        this.y = (int) y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
