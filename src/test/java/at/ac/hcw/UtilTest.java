package at.ac.hcw;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UtilTest {

    @Test
    void insertionSortSortsAirportsByNameAscending() {
        Airport[] airports = Util.parseStations();
        Airport[] originalAirports = airports.clone();

        Util.insertionSort(airports);

        assertSortedByNameAscending(airports);
        assertContainsSameAirports(originalAirports, airports);
    }

    @Test
    void selectionSortSortsAirportsByNameAscending() {
        Airport[] airports = Util.parseStations();
        Airport[] originalAirports = airports.clone();

        Util.selectionSort(airports);

        assertSortedByNameAscending(airports);
        assertContainsSameAirports(originalAirports, airports);
    }

    @Test
    void quickSortSortsAirportsByNameDescending() {
        Airport[] airports = Util.parseStations();
        Airport[] originalAirports = airports.clone();

        Util.quickSort(airports);

        assertSortedByNameDescending(airports);
        assertContainsSameAirports(originalAirports, airports);
    }

    @Test
    void mergeSortSortsAirportsByNameDescending() {
        Airport[] airports = Util.parseStations();
        Airport[] originalAirports = airports.clone();

        Util.mergeSort(airports);

        assertSortedByNameDescending(airports);
        assertContainsSameAirports(originalAirports, airports);
    }

    @Test
    void selectionSortUSortsStringsWithLambdaComparator() {
        String[] values = {"Wien", "Graz", "Linz", "Innsbruck"};

        Util.selectionSortU(values, String::compareTo);

        assertArrayEquals(new String[] {"Graz", "Innsbruck", "Linz", "Wien"}, values);
    }

    @Test
    void selectionSortUSortsAirportsByNameAscendingWithLambdaComparator() {
        Airport[] airports = Util.parseStations();
        Airport[] originalAirports = airports.clone();

        Util.selectionSortU(airports, Comparator.comparing(Airport::getName));

        assertSortedByNameAscending(airports);
        assertContainsSameAirports(originalAirports, airports);
    }

    private void assertSortedByNameAscending(Airport[] airports) {
        for (int i = 0; i < airports.length - 1; i++) {
            assertTrue(
                    airports[i].getName().compareTo(airports[i + 1].getName()) <= 0,
                    "Array is not sorted ascending at index " + i);
        }
    }

    private void assertSortedByNameDescending(Airport[] airports) {
        for (int i = 0; i < airports.length - 1; i++) {
            assertTrue(
                    airports[i].getName().compareTo(airports[i + 1].getName()) >= 0,
                    "Array is not sorted descending at index " + i);
        }
    }

    private void assertContainsSameAirports(Airport[] expected, Airport[] actual) {
        Airport[] expectedCopy = expected.clone();
        Airport[] actualCopy = actual.clone();

        Arrays.sort(expectedCopy, Comparator.comparing(Airport::getIcaoCode));
        Arrays.sort(actualCopy, Comparator.comparing(Airport::getIcaoCode));

        assertArrayEquals(expectedCopy, actualCopy);
    }
}
