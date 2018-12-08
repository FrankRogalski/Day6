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
        for (long max = maxStart; true; max++) {
            for (long j = -max; j < max; j++) {
                Point point;
                switch (i) {
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
                final long key = Point.calcNearestPoint(point, startingPoints);
                pointValues.put(key, pointValues.getOrDefault(key, 0L) + 1);
                edgePoints.put(key, edgePoints.getOrDefault(key, 0L) + 1);
            }

            if (max % 100 == 0)
                logger.info(String.valueOf(max));
            if (++i % 4 == 0) {
                if (max >= maxEnd) {
                    pointValues.entrySet()
                            .removeIf( entry -> edgePoints.containsKey(entry.getKey()) || entry.getKey() == -1);
                    break;
                }
                edgePoints.clear();
                i = 0;
            }
        }
    }

    public Map<Long, Long> getPointValues() {
        return pointValues;
    }
}
