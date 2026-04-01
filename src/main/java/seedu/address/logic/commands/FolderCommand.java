package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Switches the active address book to a different folder (JSON file).
 * "folder" creates a new empty address book; "toggle" loads an existing one.
 */
public class FolderCommand extends Command {

    public static final String COMMAND_WORD_FOLDER = "folder";
    public static final String COMMAND_WORD_TOGGLE = "toggle";

    public static final String MESSAGE_USAGE_FOLDER = COMMAND_WORD_FOLDER
                                    + ": Creates a new empty OfferFlow folder saved under data/<FOLDER_NAME>.json.\n"
                                    + "Example: " + COMMAND_WORD_FOLDER + " Y1S2";

    public static final String MESSAGE_USAGE_TOGGLE = COMMAND_WORD_TOGGLE
                                    + ": Switches to an existing OfferFlow folder at data/<FOLDER_NAME>.json.\n"
                                    + "Example: " + COMMAND_WORD_TOGGLE + " Y1S2";

    private final String folderName;
    private final boolean createNew;

    /**
     * @param folderName the name of the folder to switch to
     * @param createNew  true for "folder" (start empty), false for "toggle"
     */
    public FolderCommand(String folderName, boolean createNew) {
        requireNonNull(folderName);
        this.folderName = folderName;
        this.createNew = createNew;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        String action = createNew ? "Created and switched to" : "Switched to";
        return new CommandResult(action + " folder: " + folderName, folderName, createNew);
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
        return createNew == o.createNew && folderName.equals(o.folderName);
    }

}
