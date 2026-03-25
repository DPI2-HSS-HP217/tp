package seedu.address.logic.parser;

import seedu.address.logic.commands.FolderCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code FolderCommand}.
 * Used for both "folder" (createNew=true) and "toggle" (createNew=false).
 */
public class FolderCommandParser implements Parser<FolderCommand> {

    private static final String VALID_FOLDER_NAME_REGEX = "[\\w\\-]+";

    private final boolean createNew;

    /**
     * @param createNew true if parsing a "folder" command, false if parsing a "toggle" command
     */
    public FolderCommandParser(boolean createNew) {
        this.createNew = createNew;
    }

    @Override
    public FolderCommand parse(String args) throws ParseException {
        String name = args.trim();
        if (name.isEmpty()) {
            String usage = createNew ? FolderCommand.MESSAGE_USAGE_FOLDER : FolderCommand.MESSAGE_USAGE_TOGGLE;
            throw new ParseException("Folder name cannot be empty.\n" + usage);
        }
        if (!name.matches(VALID_FOLDER_NAME_REGEX)) {
            throw new ParseException("Folder name can only contain letters, numbers, underscores, and hyphens.");
        }
        return new FolderCommand(name, createNew);
    }

}
