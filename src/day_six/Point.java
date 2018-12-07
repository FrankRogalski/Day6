package day_six;

import java.util.List;

public class Point {
    private long x;
    private long y;
    private String id;

    public Point(final long x, final long y, final String id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public Point() {

    }

    public static Point getPointAvg(final List<Point> points) {
        final Point point = points.stream().reduce(new Point(), Point::add);
        point.x /= points.size();
        point.y /= points.size();
        return point;
    }

    public static String calcNearestPoint(final Point point, final List<Point> points) {
        long min = Long.MAX_VALUE;
        String pointName = "";
        for (final Point listPoint : points) {
            final Long dist = calcDist(point, listPoint);
            if (dist < min) {
                min = dist;
                pointName = listPoint.getId();
            } else if (dist == min)
                return "";
        }
        return pointName;
    }

    private static long calcDist(final Point point1, final Point point2) {
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }

    public static Point add(final Point point1, final Point point2) {
        return new Point(point1.x + point2.x, point1.y + point2.y, point1.id);
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public long getX() {
        return x;
    }

    public void setX(final long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(final long y) {
        this.y = y;
    }
}
