package at.ac.hcw;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Comparator;

public final class Util {
    private static final String STATIONS_FILE = "stations.csv";
    private static final int INITIAL_STATIONS_ARRAY_SIZE = 6000;

    public static void insertionSort(Airport[] airports) {
        // TODO: implement insertion sort
    }

    public static void selectionSort(Airport[] airports) {
        // TODO: implement selection sort
    }

    public static <T> void selectionSortU(T[] elements, Comparator<T> comparator) {
        // TODO: implement generic selection sort
    }

    public static void quickSort(Airport[] airports) {
        // TODO: implement quick sort
    }

    public static void mergeSort(Airport[] airports) {
        // TODO: implement merge sort
    }

    public static Airport[] parseStations() {
        Airport[] parsedStations = new Airport[INITIAL_STATIONS_ARRAY_SIZE];
        int stationCount = 0;

        try (InputStream inputStream = Util.class.getClassLoader().getResourceAsStream(STATIONS_FILE)) {
            if (inputStream == null) {
                throw new IllegalStateException("Resource not found: " + STATIONS_FILE);
            }

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                String line;

                while ((line = reader.readLine()) != null) {
                    String trimmedLine = line.trim();

                    if (trimmedLine.isEmpty() || trimmedLine.startsWith("#")) {
                        continue;
                    }

                    String[] parts = trimmedLine.split(";", 2);
                    if (parts.length < 2) {
                        continue;
                    }

                    String icaoCode = parts[0].trim();
                    String airportName = parts[1].trim();

                    if (stationCount >= parsedStations.length) {
                        throw new IllegalStateException(
                                "Too many stations for initial array size of " + INITIAL_STATIONS_ARRAY_SIZE);
                    }

                    parsedStations[stationCount] = new Airport(icaoCode, airportName);
                    stationCount++;
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not parse " + STATIONS_FILE, e);
        }

        Airport[] stations = new Airport[stationCount];
        System.arraycopy(parsedStations, 0, stations, 0, stationCount);
        return stations;
    }
}
