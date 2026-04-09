package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.FolderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code FolderCommand}.
 */
public class FolderCommandParser implements Parser<FolderCommand> {

    private static final String VALID_FOLDER_NAME_REGEX = "[\\w\\-]+";

    @Override
    public FolderCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String name = args.trim();
        if (name.isEmpty()) {
            throw new ParseException("Folder name cannot be empty.\n" + FolderCommand.MESSAGE_USAGE);
        }
        if (!name.matches(VALID_FOLDER_NAME_REGEX)) {
            throw new ParseException("Folder name can only contain letters, numbers, underscores, and hyphens.");
        }
        return new FolderCommand(name);
    }

}
