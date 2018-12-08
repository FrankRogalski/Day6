package day_six;

import java.util.LinkedList;
import java.util.List;

/**
 * A point which stores X and Y coordinates plus a few useful methods
 */
public class Point {
    private long x;
    private long y;
    private long id;

    /**
     * The creation of a point
     * @param x The horizontal distance to the (0, 0) point
     * @param y The vertical distance to the (0, 0) point
     * @param id The id of a point
     */
    public Point(final long x, final long y, final long id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    /**
     * Empty constructor used in the method getPointAvg
     */
    private Point() {
        //no code needed because of method getPointAvg
    }

    /**
     * Get the Average value of the list of points
     * @param points the points of which the average should be calculated
     * @return the point that is closest to the average
     */
    public static Point getPointAvg(final List<Point> points) {
        final Point point = points.stream()
                .reduce(new Point(), Point::add);
        point.x /= points.size();
        point.y /= points.size();
        return point;
    }

    /**
     * Calculates which of the points in the given list is nearest to the single given point
     * @param point The point to which the distance is checked
     * @param points The list of points that distance needs to be checked
     * @return The id of the closest point in the list
     */
    public static long calcNearestPoint(final Point point, final List<Point> points) {
        final List<Long> distances = new LinkedList<>();

        // calculate the closest point
        long min = Long.MAX_VALUE;
        long pointId = -1;
        for (final Point listPoint : points) {
            final long distance = calcDist(point, listPoint);
            distances.add(distance);
            if (distance < min) {
                min = distance;
                pointId = listPoint.getId();
            }
        }

        // If two points can be considered closest, return an error value
        long numMin = 0;
        for (final long distance : distances)
            if (distance == min && ++numMin >= 2)
                    return -1;
        return pointId;
    }

    /**
     * Calculates the Manhattan distance between two points
     * @param point1 The first point from which the distance two the second point will be measured
     * @param point2 The second point to which the distance will be measured
     * @return The distance between the two points
     */
    private static long calcDist(final Point point1, final Point point2) {
        return Math.abs(point1.x - point2.x) + Math.abs(point1.y - point2.y);
    }

    /**
     * Adds the x and y coordinates of two points and returns a new one
     * @param point1 The first point that will be added. The id of this point will be the id of the new point
     * @param point2 The second point that will be added
     * @return A new Point that contains the some of the two given points
     */
    public static Point add(final Point point1, final Point point2) {
        return new Point(point1.x + point2.x, point1.y + point2.y, point1.id);
    }

    public long getId() {
        return id;
    }

    public void setId(final long id) {
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
