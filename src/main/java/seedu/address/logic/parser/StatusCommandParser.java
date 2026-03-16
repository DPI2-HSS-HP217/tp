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
     * Parses the given {@code String} of arguments in the context of the StatusCommand
     * and returns a StatusCommand object for execution.
     */
    public StatusCommand parse(String args) throws ParseException {
        requireNonNull(args);

        String name = null;
        String role = null;
        String status = null;

        int nameIndex = args.indexOf("n/");
        int roleIndex = args.indexOf("r/");
        int statusIndex = args.indexOf("s/");

        if (nameIndex == -1 || roleIndex == -1 || statusIndex == -1) {
            throw new ParseException(StatusCommand.MESSAGE_USAGE);
        }

        name = args.substring(nameIndex + 2, roleIndex).trim();
        role = args.substring(roleIndex + 2, statusIndex).trim();
        status = args.substring(statusIndex + 2).trim();

        return new StatusCommand(name, role, new Status(status));
    }
}
