package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.ToggleCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code ToggleCommand}.
 */
public class ToggleCommandParser implements Parser<ToggleCommand> {

    private static final String VALID_FOLDER_NAME_REGEX = "[\\w\\-]+";

    @Override
    public ToggleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String name = args.trim();
        if (name.isEmpty()) {
            throw new ParseException("Folder name cannot be empty.\n" + ToggleCommand.MESSAGE_USAGE);
        }
        if (!name.matches(VALID_FOLDER_NAME_REGEX)) {
            throw new ParseException("Folder name can only contain letters, numbers, underscores, and hyphens.");
        }
        return new ToggleCommand(name);
    }

}
