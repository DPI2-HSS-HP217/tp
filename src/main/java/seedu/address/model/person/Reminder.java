package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Date.isValidDate;

/**
 * Event class to represent reminder tasks of an application.
 */
public class Reminder {
    private final String reminderName;
    private final Date reminderDate;


    public static final String MESSAGE_CONSTRAINTS = "Reminder should be letters, and it should not be blank";
    public static final String VALIDATION_REGEX = "[A-Za-z][A-Za-z ]*";
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
