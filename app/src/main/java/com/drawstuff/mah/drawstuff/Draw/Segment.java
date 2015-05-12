package com.drawstuff.mah.drawstuff.Draw;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Long.parseLong;

/**
 * @author greg
 * @since 6/26/13
 */
public class Segment {

    private List<Point> points = new ArrayList<Point>();
    private String color;

    // Required default constructor for Firebase serialization / deserialization
    @SuppressWarnings("unused")
    private Segment() {
    }

    public Segment(String color) {
        this.color = color;
    }

    public void addPoint(long x, long y) {
        Point p = new Point(x, y);
        points.add(p);
    }

    public List<Point> getPoints() {
        return points;
    }

    public long getColor() {
        return parseLong(color);
    }
}
