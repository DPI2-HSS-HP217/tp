package seedu.address.model.person;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Event class to represent reminder tasks of an application.
 */
public class Reminder {
    private final String reminderName;
    private final Date reminderDate;

    /**
     * Event with a deadline.
     * @param reminderName event Description.
     * @param reminderDate event date.
     */
    public Reminder(String reminderName, String reminderDate) {
        this.reminderName = reminderName;
        this.reminderDate = new Date(reminderDate);
    }

    public String getReminderName() {
        return reminderName;
    }

    public Date getReminderDate() {
        return reminderDate;
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(reminderName, reminderDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Upcoming", reminderName)
                .add("Date", reminderDate).toString();
    }

}
