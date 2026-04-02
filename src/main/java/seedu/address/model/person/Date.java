package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Objects;

/**
 * A Class to represent the date applied for a job application.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should follow the format YYYY-MM-DD (e.g., 2024-12-25), and it should be a valid date.";

    public static final String MESSAGE_FUTURE_DATE = "OOPS! The date cannot be in the future.";

    /*
     * Date validation regex for YYYY-MM-DD format
     * - Year: any 4 digits
     * - Month: 01-12
     * - Day: 01-31
     */
    public static final String VALIDATION_REGEX = "^\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])$";

    public final String value;
    private final LocalDate localDate;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date string in YYYY-MM-DD format.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        this.value = date;
        this.localDate = makeLocalDate(date); // guard empty string
    }

    /**
     * Constructs a {@code Date}.
     *
     * @param date A LocalDate object.
     */
    public Date(LocalDate date) {
        requireNonNull(date);
        this.localDate = date;
        this.value = formatDate(date);
    }

    private String formatDate(LocalDate date) {
        return date.format(DateTimeFormatter.ofPattern("uuuu-MM-dd"));
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (isEmpty(test)) {
            return true;
        }

        if (!matchesRegex(test)) {
            return false;
        }

        return isProperDate(test);
    }

    /**
     * Returns true if given string is empty.
     *
     * @param test the string to test
     * @return true if the string is empty, false otherwise
     */
    private static boolean isEmpty(String test) {
        return Objects.equals(test, "");
    }

    /**
     * Returns true if given string matches the date format regex.
     *
     * @param test the string to test
     * @return true if the string matches the date format regex else false
     */
    private static boolean matchesRegex(String test) {
        String trimmedTest = test.trim();
        return trimmedTest.matches(VALIDATION_REGEX);
    }

    /**
     * Returns true if given string can be parsed to a LocalDate object, indicating it is a proper date.
     *
     * @param test the string to test
     * @return true if the string can be parsed to a LocalDate object else false
     */
    private static boolean isProperDate(String test) {
        try {
            LocalDate.parse(test, DateTimeFormatter.ISO_LOCAL_DATE);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Converts date string to LocalDate object if string not empty, else return null.
     *
     * @param date the date string to convert
     * @return the LocalDate object if date string is not empty, else null
     */
    private LocalDate makeLocalDate(String date) {
        return date.isEmpty() ? null : parseToLocalDate(date);
    }

    /**
     * Parses the date string to LocalDate object.
     *
     * @param date the date string to parse
     * @return the LocalDate object parsed from the date string
     */
    private LocalDate parseToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Returns true if this date is not after today's date.
     *
     * @return true if the date is not in the future
     */
    public boolean checkNotFutureDate() {
        if (localDate == null) {
            return true;
        }

        return !isFutureDate();
    }

    /**
     * Checks if date is after today's date.
     *
     * @return true if the date is after today's date
     */
    private boolean isFutureDate() {
        return localDate != null && localDate.isAfter(LocalDate.now());
    }

    /**
     * Returns the date as a LocalDate object.
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return value.equals(otherDate.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
