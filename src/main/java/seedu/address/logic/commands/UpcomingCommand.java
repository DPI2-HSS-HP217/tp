package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.ReminderWithinOffsetPredicate;

/**
 * Filters applications by a supported field and updates the current filtered list.
 */
public class UpcomingCommand extends Command {

    public static final int MIN_OFFSET = 0;
    public static final int MAX_OFFSET = 7;

    public static final String COMMAND_WORD = "upcoming";
    public static final String COMMAND_WORD_WITH_SLASH = "/upcoming";

    public static final String MESSAGE_USAGE = COMMAND_WORD_WITH_SLASH + ": Sets reminder to display all applications "
            + "within the specified number of days from the current date, then displays one such notifcation. \n"
            + "Parameters: [DAYS_OFFSET]...\n"
            + "Example: " + COMMAND_WORD_WITH_SLASH + " 9";
    public static final String MESSAGE_NO_MATCHES = "No upcoming applications in %d days";
    public static final String MESSAGE_MATCHES_FOUND = "There are %d application(s) due in %d days.";
    public static final String MESSAGE_INVALID_ARGS = "Days parameter must be an "
            + "an integer between " + MIN_OFFSET + " and " + MAX_OFFSET + " inclusive in digit form, e.g 5.";

    private final int daysOffset;
    private final ReminderWithinOffsetPredicate predicate;

    /**
     *  Constructs an upcomingcommand.
     * @param predicate ReminderWithinOffset predicate
     * @param days Denotes a range of dates for applications with reminders which the above predicate
     *             filters for, with the range being [today's date, today's date + days]
     */
    public UpcomingCommand(ReminderWithinOffsetPredicate predicate, int days) {
        this.predicate = predicate;
        this.daysOffset = days;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int matchCount = model.getFilteredPersonList().size();
        model.setReminderOffset(daysOffset);
        if (matchCount == 0) {
            return new CommandResult(String.format(MESSAGE_NO_MATCHES, daysOffset));
        }
        return new CommandResult(String.format(MESSAGE_MATCHES_FOUND, matchCount, daysOffset));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UpcomingCommand)) {
            return false;
        }

        UpcomingCommand otherUpcomingCommand = (UpcomingCommand) other;
        return (otherUpcomingCommand.daysOffset == this.daysOffset)
                && (otherUpcomingCommand.predicate.equals(this.predicate));
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("days offset", daysOffset)
                .add("predicate", predicate)
                .toString();
    }
}
