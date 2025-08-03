package oort.cloud;

import java.util.Objects;

public class Position {
    private static final Position INITIAL_POSITION = new Position(-1);

    private final int value;

    public Position(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return value == position.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    public Position next() {
        return new Position(value + 1);
    }
}
