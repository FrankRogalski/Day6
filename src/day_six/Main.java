package day_six;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    public static void main(String[] args) {
        final List<Point> points = getPoints();
        final Point avg = Point.getPointAvg(points);

        final PointCalculator pointCalculator = new PointCalculator(points, avg.getX(), avg.getY(), 0, 100, logger);
        pointCalculator.run();
        Map<Long, Long> pointValues = pointCalculator.getPointValues();
        final long erg = pointValues.values().stream().reduce(0L, Math::max);
        logger.info(String.valueOf(erg));

        /*final long threads = 8;
        final long maxValue = 10000;
        final long threadSum = maxValue / threads;
        final List<PointCalculator> pointCalculators = new LinkedList<>();
        for (int i = 0; i < threads; i++) {
            final long start = i * threadSum;
            final long end = maxValue - (maxValue - (i * threadSum + threadSum));
            final PointCalculator pointCalculator = new PointCalculator(points, avg.getX(), avg.getY(), start, end, logger);
            pointCalculators.add(pointCalculator);
            pointCalculator.start();
        }

        for (final PointCalculator pointCalculator : pointCalculators)
            try {
                pointCalculator.join();
            } catch (InterruptedException ex) {
                logger.info(ex.getMessage());
                Thread.currentThread().interrupt();
            }

        Map<Long, Long> pointValues = new HashMap<>();
        for (final PointCalculator pointCalculator : pointCalculators)
            for (final long key: pointCalculator.getPointValues().keySet())
                pointValues.put(key, pointValues.getOrDefault(key, 0L) + pointCalculator.getPointValues().get(key));

        final long erg = pointValues.values().stream().reduce(0L, Math::max);
        logger.info(String.valueOf(erg));*/
    }

    private static List<Point> getPoints() {
        final List<Point> points = new LinkedList<>();
        final File file = new File("E:\\EigeneEntwicklungen\\Python\\VSCode\\AdventOfCode\\Day6\\Day6.txt");
        try (final Scanner scanner = new Scanner(file)) {
            long id = 0;
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                final String[] numbers = line.split(", ");
                points.add(new Point(Long.valueOf(numbers[0]), Long.valueOf(numbers[1]), id++));
            }
        } catch (FileNotFoundException ex) {
            logger.info(ex.getMessage());
        }
        return points;
    }
}
