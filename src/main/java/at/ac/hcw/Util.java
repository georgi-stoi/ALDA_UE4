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
        for (int i = 1; i < airports.length; i++) {
            Airport currentAirport = airports[i];
            int j = i - 1;

            while (j >= 0 && airports[j].getName().compareTo(currentAirport.getName()) > 0) {
                airports[j + 1] = airports[j];
                j--;
            }

            airports[j + 1] = currentAirport;
        }
    }

    public static void selectionSort(Airport[] airports) {
        for (int i = 0; i < airports.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < airports.length; j++) {
                if (airports[j].getName().compareTo(airports[minIndex].getName()) < 0) {
                    minIndex = j;
                }
            }

            swap(airports, i, minIndex);
        }
    }

    public static <T> void selectionSortU(T[] elements, Comparator<T> comparator) {
        for (int i = 0; i < elements.length - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < elements.length; j++) {
                if (comparator.compare(elements[j], elements[minIndex]) < 0) {
                    minIndex = j;
                }
            }

            swap(elements, i, minIndex);
        }
    }

    public static void quickSort(Airport[] airports) {
        quickSort(airports, 0, airports.length - 1);
    }

    public static void mergeSort(Airport[] airports) {
        Airport[] tempAirports = new Airport[airports.length];
        mergeSort(airports, tempAirports, 0, airports.length - 1);
    }

    private static void quickSort(Airport[] airports, int left, int right) {
        if (left >= right) {
            return;
        }

        int pivotIndex = partitionDescending(airports, left, right);
        quickSort(airports, left, pivotIndex - 1);
        quickSort(airports, pivotIndex + 1, right);
    }

    private static int partitionDescending(Airport[] airports, int left, int right) {
        Airport pivot = airports[right];
        int greaterElementIndex = left - 1;

        for (int i = left; i < right; i++) {
            if (airports[i].getName().compareTo(pivot.getName()) >= 0) {
                greaterElementIndex++;
                swap(airports, greaterElementIndex, i);
            }
        }

        swap(airports, greaterElementIndex + 1, right);
        return greaterElementIndex + 1;
    }

    private static void mergeSort(Airport[] airports, Airport[] tempAirports, int left, int right) {
        if (left >= right) {
            return;
        }

        int middle = left + (right - left) / 2;
        mergeSort(airports, tempAirports, left, middle);
        mergeSort(airports, tempAirports, middle + 1, right);
        mergeDescending(airports, tempAirports, left, middle, right);
    }

    private static void mergeDescending(Airport[] airports, Airport[] tempAirports, int left, int middle, int right) {
        int leftIndex = left;
        int rightIndex = middle + 1;
        int tempIndex = left;

        while (leftIndex <= middle && rightIndex <= right) {
            if (airports[leftIndex].getName().compareTo(airports[rightIndex].getName()) >= 0) {
                tempAirports[tempIndex] = airports[leftIndex];
                leftIndex++;
            } else {
                tempAirports[tempIndex] = airports[rightIndex];
                rightIndex++;
            }
            tempIndex++;
        }

        while (leftIndex <= middle) {
            tempAirports[tempIndex] = airports[leftIndex];
            leftIndex++;
            tempIndex++;
        }

        while (rightIndex <= right) {
            tempAirports[tempIndex] = airports[rightIndex];
            rightIndex++;
            tempIndex++;
        }

        for (int i = left; i <= right; i++) {
            airports[i] = tempAirports[i];
        }
    }

    private static <T> void swap(T[] elements, int firstIndex, int secondIndex) {
        T temp = elements[firstIndex];
        elements[firstIndex] = elements[secondIndex];
        elements[secondIndex] = temp;
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
