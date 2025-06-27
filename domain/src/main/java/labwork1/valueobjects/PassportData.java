package labwork1.valueobjects;

/**
 * A class for maintaining the invariant of passport data (correct passport series and number)
 * @param series - passport series consists of 4 digits
 * @param number - passport numbers consists of 6 digits
 */
public record PassportData(int series, int number) {
    /**
     *
     * @param series
     * @param number
     * @throws IllegalArgumentException
     */
    public PassportData {
        if (!validatePassportSeries(series))
            throw new IllegalArgumentException("Invalid passport series");

        if (!validatePassportNumber(number))
            throw new IllegalArgumentException("Invalid passport number");
    }

    public static boolean validatePassportSeries(int passportSeries) {
        return passportSeries >= 1000 && passportSeries <= 9999;
    }

    public static boolean validatePassportNumber(int passportNumber) {
        return passportNumber >= 100000 && passportNumber <= 999999;
    }
}