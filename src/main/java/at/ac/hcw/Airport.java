package at.ac.hcw;

import java.util.Objects;

public class Airport {
    private final String icaoCode;
    private final String name;

    public Airport(String icaoCode, String name) {
        this.icaoCode = icaoCode;
        this.name = name;
    }

    public String getIcaoCode() {
        return icaoCode;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Airport airport)) {
            return false;
        }
        return Objects.equals(icaoCode, airport.icaoCode)
                && Objects.equals(name, airport.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(icaoCode, name);
    }

    @Override
    public String toString() {
        return icaoCode + " (" + name + ")";
    }
}
