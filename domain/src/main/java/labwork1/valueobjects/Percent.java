package labwork1.valueobjects;

/**
 * A class for maintaining the invariant of percent (must be between 0 and 100)
 * @param value - passport series consists of 4 digits
 */
public record Percent(double value) {
    public Percent {
        if (value < 0 || value > 100)
            throw new IllegalArgumentException("Money must be between 0 and 100");
    }
}
