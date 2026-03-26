package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Application;

/**
 * Filters applications by a supported field and update the current filtered list.
 */
public class FilterCommand extends Command {

    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " /n /<keyword>\n" + COMMAND_WORD
                                    + " /d /<YYYY-MM-DD>\n" + COMMAND_WORD + " /s /<status>\n"
                                    + COMMAND_WORD + " /t /<tag>";
    public static final String MESSAGE_NO_MATCHES = "No matching applications found.";
    public static final String MESSAGE_MATCHES_FOUND = "Found %d matching application(s)";

    private final Predicate<Application> predicate;

    public FilterCommand(Predicate<Application> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int matchCount = model.getFilteredPersonList().size();
        if (matchCount == 0) {
            return new CommandResult(MESSAGE_NO_MATCHES);
        }
        return new CommandResult(String.format(MESSAGE_MATCHES_FOUND, matchCount));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherFilterCommand = (FilterCommand) other;
        return predicate.equals(otherFilterCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("predicate", predicate).toString();
    }
}
