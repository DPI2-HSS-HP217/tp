package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Date.isValidDate;

import java.util.Objects;

/**
 * Event class to represent reminder tasks of an application.
 */
public class Reminder {
    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z ]*";
    public static final String MESSAGE_CONSTRAINTS = "Reminder should be letters, and it should not be blank";

    private final String reminderName;
    private final Date reminderDate;

    /**
     * Event with a deadline.
     * @param reminderName event Description.
     * @param reminderDate event date.
     */
    public Reminder(String reminderName, String reminderDate) {
        requireNonNull(reminderName, reminderDate);
        checkArgument(isValidReminder(reminderName), MESSAGE_CONSTRAINTS);
        checkArgument(isValidDate(reminderDate), MESSAGE_CONSTRAINTS);
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
        return reminderDate.getLocalDate().isBefore(otherDate.getLocalDate())
                || reminderDate.getLocalDate().isEqual(otherDate.getLocalDate());
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
