package day_six;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.logging.Logger;

/**
 * The Main class of the program and starting point of all
 */
public class Main {
    private static Logger logger = Logger.getLogger(Main.class.getName());

    /**
     * The starting point of the Program
     * @param args input parameters of the User
     * @throws FileNotFoundException The path the user submitted was incorrect
     */
    public static void main(String[] args) throws FileNotFoundException {
        // Validate the inputs
        if (args.length < 3)
            throw new IllegalArgumentException("All Three arguments are needed");
        final long threads = Long.parseLong(args[1]);
        final long maxValue = Long.parseLong(args[0]);
        if (maxValue % threads != 0)
            throw new IllegalArgumentException("Maxvalue must be cleanly divisible by the number of threads");


        final List<Point> points = getPoints(args[2]);
        final Point avg = Point.getPointAvg(points);
        final long threadSum = maxValue / threads;
        final List<PointCalculator> pointCalculators = new LinkedList<>();
        long oldEnd = -1;

        for (int i = 0; i < threads; i++) {
            // Calculate the Area each thread should work on
            final long start = oldEnd + 1;
            long end = maxValue - (maxValue - (i * threadSum + threadSum));
            end = 4 * (end / 4) - 1;
            oldEnd = end;

            // Initialize and start the threads
            final PointCalculator pointCalculator = new PointCalculator(points, avg.getX(), avg.getY(), start, end, logger);
            pointCalculators.add(pointCalculator);
            pointCalculator.start();
        }

        // Wait for all threads to finish
        for (final PointCalculator pointCalculator : pointCalculators)
            try {
                pointCalculator.join();
            } catch (InterruptedException ex) {
                logger.info(ex.getMessage());
                Thread.currentThread().interrupt();
            }

        // Sum up the results of all threads
        Map<Long, Long> pointValues = new HashMap<>();
        for (final PointCalculator pointCalculator : pointCalculators)
            for (final long key : pointCalculator.getPointValues().keySet())
                pointValues.put(key, pointValues.getOrDefault(key, 0L) + pointCalculator.getPointValues().get(key));

        // Remove all keys which areas touch the edge
        Map<Long, Long> edgePoints = ((LinkedList<PointCalculator>) pointCalculators).getLast().getEdgePoints();
        pointValues.entrySet().removeIf(entry -> edgePoints.containsKey(entry.getKey()) || entry.getKey() == -1);

        // Find the entry with the largest area and print it
        final long erg = pointValues.values().stream().reduce(0L, Math::max);
        logger.info(String.valueOf(erg));
    }

    /**
     * Creates a List of Points which are specified in the given File
     * @param path The path of the File containing the point definitions
     * @return Returns a List of Points which are specified in the given File
     * @throws FileNotFoundException The path the user submitted was incorrect
     */
    private static List<Point> getPoints(final String path) throws FileNotFoundException {
        final List<Point> points = new LinkedList<>();
        final File file = new File(path);
        try (final Scanner scanner = new Scanner(file)) {
            long id = 0;
            while (scanner.hasNext()) {
                final String line = scanner.nextLine();
                final String[] numbers = line.split(", ");
                points.add(new Point(Long.valueOf(numbers[0]), Long.valueOf(numbers[1]), id++));
            }
        }
        return points;
    }
}
