package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.ReminderWithinOffsetPredicate;

/**
 * Filters applications by a supported field and updates the current filtered list.
 */
public class UpcomingCommand extends Command {

    public static final String COMMAND_WORD = "upcoming";
    public static final String COMMAND_WORD_WITH_SLASH = "/upcoming";

    public static final String MESSAGE_USAGE = COMMAND_WORD_WITH_SLASH + ": Sets reminder to display all applications "
            + "within the specified number of days from the current date, then displays one such notifcation. \n"
            + "Parameters: [DAYS_OFFSET]...\n"
            + "Example: " + COMMAND_WORD_WITH_SLASH + " 9";
    public static final String MESSAGE_NO_MATCHES = "No upcoming applications in %d days";
    public static final String MESSAGE_MATCHES_FOUND = "Found %d matching application(s)";

    private final int daysOffset;
    private final ReminderWithinOffsetPredicate predicate;

    public UpcomingCommand(ReminderWithinOffsetPredicate predicate, int days) {
        this.predicate = predicate;
        this.daysOffset = days;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int matchCount = model.getFilteredPersonList().size();
        if (matchCount == 0) {
            return new CommandResult(String.format(MESSAGE_NO_MATCHES, daysOffset));
        }
        return new CommandResult(String.format(MESSAGE_MATCHES_FOUND, matchCount));
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