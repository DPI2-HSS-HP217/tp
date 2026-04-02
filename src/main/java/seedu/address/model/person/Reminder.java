package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Date.isValidDate;

import java.time.LocalDate;
import java.util.Objects;

/**
 * Represents a Reminder if an Application has one.
 */
public class Reminder {
    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z ]*";
    public static final String REMINDER_MESSAGE_CONSTRAINTS =
            "Reminder should be letters, and it should not be blank";
    public static final String DATE_MESSAGE_CONSTRAINTS =
            "Date should follow the format YYYY-MM-DD (e.g., 2024-12-25), and it should be a valid date.";


    private final String reminderName;
    private final Date reminderDate;

    /**
     * Constructs a Reminder.
     * Reminder must have Both Name description and Date.
     *
     * @param reminderName Reminder Description.
     * @param reminderDate Reminder Date.
     */
    public Reminder(String reminderName, String reminderDate) {
        requireNonNull(reminderName, reminderDate);
        checkArgument(isValidReminder(reminderName), REMINDER_MESSAGE_CONSTRAINTS);
        checkArgument(isValidDate(reminderDate), DATE_MESSAGE_CONSTRAINTS);
        this.reminderName = reminderName;
        this.reminderDate = new Date(reminderDate);
    }

    public String getReminderName() {
        return reminderName;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    public static boolean isValidReminder(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Checks if this Reminder's date is due by the other date provided - meaning
     * it is before, or equal to the date provided
     * @param otherDate other Date to compare to
     * @return if this reminder's date is due by the other date
     */
    public boolean isByDate(Date otherDate) {
        boolean isNotOverdue = reminderDate.getLocalDate().isAfter(LocalDate.now())
                || reminderDate.getLocalDate().isEqual(LocalDate.now());
        boolean isWithinRange = reminderDate.getLocalDate().isBefore(otherDate.getLocalDate())
                || reminderDate.getLocalDate().isEqual(otherDate.getLocalDate());
        return isNotOverdue && isWithinRange;
    }

    /**
     * Returns String representation of a whether a reminder is due or not.
     */
    public String getStyleClass() {
        boolean isDue = isByDate(new Date(LocalDate.now()));
        return isDue ? "due" : "notDue";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Reminder)) {
            return false;
        }
        Reminder otherReminder = (Reminder) other;
        return reminderName.equals(otherReminder.reminderName)
                && reminderDate.equals(otherReminder.reminderDate);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(reminderName, reminderDate);
    }

    @Override
    public String toString() {
        return new StringBuilder()
                .append("[Upcoming: ")
                .append(reminderName)
                .append(", Date: ")
                .append(reminderDate)
                .append("]")
                .toString();
    }

}
