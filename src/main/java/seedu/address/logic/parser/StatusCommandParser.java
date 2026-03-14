package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.StatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;

public class StatusCommandParser implements Parser<StatusCommand> {

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