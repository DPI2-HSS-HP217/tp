package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Creates a new empty address book saved under data/{@code FOLDER_NAME}.json.
 */
public class FolderCommand extends Command {

    public static final String COMMAND_WORD = "folder";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Creates a new empty address book saved under data/<FOLDER_NAME>.json.\n"
            + "Example: " + COMMAND_WORD + " Y1S2";

    private final String folderName;

    /** Creates a {@code FolderCommand} with the given {@code folderName}. */
    public FolderCommand(String folderName) {
        requireNonNull(folderName);
        assert !folderName.isEmpty() : "Folder name should not be empty";
        this.folderName = folderName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("Created and switched to folder: " + folderName, folderName, true);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FolderCommand)) {
            return false;
        }
        FolderCommand o = (FolderCommand) other;
        return folderName.equals(o.folderName);
    }

}
