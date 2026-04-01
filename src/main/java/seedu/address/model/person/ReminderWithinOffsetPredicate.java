package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Checks if an application has a reminder, and if that reminders falls within the
 * range of dates specified by an offset in terms of days from today's date
 */
public class ReminderWithinOffsetPredicate implements Predicate<Application> {
    private Date date;

    public ReminderWithinOffsetPredicate(Date date) {
        this.date = date;
    }

    @Override
    public boolean test(Application application) {
        return application.hasReminderByDate(this.date);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ReminderWithinOffsetPredicate)) {
            return false;
        }

        ReminderWithinOffsetPredicate otherReminderWithinOffsetPredicate = (ReminderWithinOffsetPredicate) other;
        return date.equals(otherReminderWithinOffsetPredicate.date);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("date", date).toString();
    }
}
