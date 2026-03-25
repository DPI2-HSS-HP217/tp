package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.time.LocalDate;

import seedu.address.logic.commands.UpcomingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Date;
import seedu.address.model.person.ReminderWithinOffsetPredicate;

/**
 * Parses input arguments and creates a new UpcomingCommand object
 */
public class UpcomingCommandParser {

    private static final int MAX_OFFSET = 9;
    private static final int MIN_OFFSET = 0;

    /**
     * Parses the given {@code String} of arguments in the context of the UpcomingCommand
     * and returns a UpcomingCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpcomingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpcomingCommand.MESSAGE_USAGE));
        }
        long daysOffset = Long.parseLong(trimmedArgs);
        if (daysOffset < MIN_OFFSET || daysOffset > MAX_OFFSET) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpcomingCommand.MESSAGE_DAYS_OUT_OF_RANGE));
        }
        Date date = new Date(LocalDate.now().plusDays(daysOffset));
        int days = (int) daysOffset;

        return new UpcomingCommand(new ReminderWithinOffsetPredicate(date), days);
    }
}
