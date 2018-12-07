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
    private final Map<String, Long> pointValues;
    private final Map<String, Long> edgePoints;
    private final Logger logger;

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

    @Override
    public void run() {
        int i = 0;
        for (long max = maxStart; max < maxEnd; max++) {

            for (long j = -max + startX; j < max + startY; j++) {
                Point point;
                switch (i) {
                    case 0:
                        point = new Point(j, -max, "P");
                        break;
                    case 1:
                        point = new Point(max, j, "P");
                        break;
                    case 2:
                        point = new Point(j, max, "P");
                        break;
                    default:
                        point = new Point(-max, j, "P");
                }
                final String key = Point.calcNearestPoint(point, startingPoints);
                pointValues.put(key, pointValues.getOrDefault(key, 0L) + 1);
                edgePoints.put(key, edgePoints.getOrDefault(key, 0L) + 1);
            }

            if (++i % 4 == 0) {
                if (max + 1 >= maxEnd)
                    edgePoints.entrySet().removeIf( entry -> entry.getKey().equals("") || edgePoints.containsKey(entry.getKey()));
                if (max % 100 == 0)
                    logger.info(String.valueOf(max));
                edgePoints.clear();
                i = 0;
            }
        }
    }

    public Map<String, Long> getPointValues() {
        return pointValues;
    }
}
