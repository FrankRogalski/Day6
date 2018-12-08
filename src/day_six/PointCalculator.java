package day_six;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class PointCalculator extends Thread {
    private final List<Point> startingPoints;
    private final long startX;
    private final long startY;
    private final long maxStart;
    private final long maxEnd;
    private final Map<Long, Long> pointValues;
    private final Map<Long, Long> edgePoints;
    private final Logger logger;

    /**
     * Constructor used to submit values to the threads
     * @param startingPoints The points to which the distance should be calculated
     * @param startX The x offset of the middle of the points
     * @param startY The y offset of the middle of the points
     * @param maxStart The start of the range that should be checked
     * @param maxEnd The end of the range that should be checked
     * @param logger A logger to print out useful information
     */
    public PointCalculator(final List<Point> startingPoints, final long startX, final long startY, long maxStart, long maxEnd, final Logger logger) {
        this.startingPoints = startingPoints;
        this.startX = startX;
        this.startY = startY;
        this.maxStart = maxStart;
        this.maxEnd = maxEnd;
        this.logger = logger;

        pointValues = new HashMap<>();
        edgePoints = new HashMap<>();
    }

    /**
     * The execution of the thread in which the value of each point in the assigned thread is calculated and summed up
     */
    @Override
    public void run() {
        // Go through all assigned max values
        for (long max = maxStart; true; max++) {
            final int wall = (int)(max % 4);
            // Go through each point of one side of the spiral
            for (long j = -max; j < max; j++) {
                Point point;
                // based on the side of the spiral create a point
                switch (wall) {
                    case 0:
                        point = new Point(j + startX, -max + startY, -1);
                        break;
                    case 1:
                        point = new Point(max + startX, j + startY, -1);
                        break;
                    case 2:
                        point = new Point(j + startX, max + startY, -1);
                        break;
                    default:
                        point = new Point(-max + startX, j + startY, -1);
                }
                // Calculate the closest starting point to the just created point
                // and increase the counter for the key of that starting point
                final long key = Point.calcNearestPoint(point, startingPoints);
                pointValues.put(key, pointValues.getOrDefault(key, 0L) + 1);
                edgePoints.put(key, edgePoints.getOrDefault(key, 0L) + 1);
            }

            // Check if the calculation is finished an reset the edge values
            if (wall == 0) {
                if (max % 100 == 0)
                    logger.info(String.valueOf(max));
                if (max >= maxEnd)
                    break;
                edgePoints.clear();
            }
        }
    }

    public Map<Long, Long> getEdgePoints() {
        return edgePoints;
    }

    public Map<Long, Long> getPointValues() {
        return pointValues;
    }
}
