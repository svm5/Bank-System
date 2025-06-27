package labwork1.valueobjects;

/**
 * A class for maintaining the invariant of money (not negative amount)
 * @param value - amount of money
 */
public record Money(double value) {
    public Money {
        if (value < 0)
            throw new IllegalArgumentException("Money must be non-negative");
    }
}
