package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Switches the active address book to an existing folder (JSON file).
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to an existing address book at data/<FOLDER_NAME>.json.\n"
            + "Example: " + COMMAND_WORD + " Y1S2";

    private final String folderName;

    /** Creates a {@code ToggleCommand} with the given {@code folderName}. */
    public ToggleCommand(String folderName) {
        requireNonNull(folderName);
        assert !folderName.isEmpty() : "Folder name should not be empty";
        this.folderName = folderName;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult("Switched to folder: " + folderName, folderName, false);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ToggleCommand)) {
            return false;
        }
        ToggleCommand o = (ToggleCommand) other;
        return folderName.equals(o.folderName);
    }

}
