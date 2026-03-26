package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A Class to represent the date applied for a job application.
 */
public class Date {

    public static final String MESSAGE_CONSTRAINTS =
            "Date should follow the format YYYY-MM-DD (e.g., 2024-12-25), and it should be a valid date.";

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
        value = date;
        this.localDate = parseToLocalDate(date);
    }

    /**
     * Constructs a {@code Date}.
     *
     * @param date A LocalDate object.
     */
    public Date(LocalDate date) {
        requireNonNull(date);
        this.localDate = date;
        value = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String test) {
        if (!test.matches(VALIDATION_REGEX)) {
            return false;
        }

        // Additional validation using LocalDate to catch invalid dates like 2024-02-31
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate.parse(test, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Parses the date string to LocalDate object.
     */
    private LocalDate parseToLocalDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(date, formatter);
    }

    /**
     * Returns the date as a LocalDate object.
     */
    public LocalDate getLocalDate() {
        return localDate;
    }

    /**
     * Returns the date in YYYY-MM-DD format.
     */
    public String toStorageFormat() {
        return localDate.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    @Override
    public String toString() {
        return value; // Returns in YYYY-MM-DD format
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
