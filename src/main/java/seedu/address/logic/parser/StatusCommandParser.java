package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.StatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;

/**
 * Parses input arguments and creates a new StatusCommand object.
 */
public class StatusCommandParser implements Parser<StatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of StatusCommand
     * and returns a StatusCommand object for execution.
     *
     * @param args user input arguments
     * @return StatusCommand object
     * @throws ParseException if the user input does not conform to the expected format
     */
    public StatusCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String[] parts = args.trim().split("s/");

        if (parts.length != 2) {
            throw new ParseException(StatusCommand.MESSAGE_USAGE);
        }

        String namePart = parts[0].replace("n/", "").trim();
        String statusPart = parts[1].trim();

        return new StatusCommand(namePart, new Status(statusPart));
    }
}
