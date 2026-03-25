package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.function.Predicate;

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
        return other instanceof ReminderWithinOffsetPredicate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
