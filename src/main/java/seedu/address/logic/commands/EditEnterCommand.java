package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.ParserMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Application;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;
import seedu.address.model.person.SameCompanySameRolePredicate;

/** Command to enter editing mode. */
public class EditEnterCommand extends Command {

    public static final String COMMAND_WORD = "editmode";
    public static final String MESSAGE_ENTER_EDITING_MODE_ACKNOWLEDGEMENT = "Entering Editing mode as requested ...";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":\n"
            + "Edit via INDEX (Enter editing mode for the Application identified by the index number in "
            + "the displayed person list)\n"
            + "Edit via Name and Role (Enter editing mode for the Application with the exact Name and Role) \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: Name (String), Role (String with no numbers) \n"
            + "Example: " + COMMAND_WORD + " n/Goog r/CEO";

    private final Index targetIndex;
    private final Name name;
    private final Role role;
    private final boolean isIndex;

    /**
     * Constructor for an edit mode entry via Index command.
     * @param index
     */
    public EditEnterCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
        this.name = null;
        this.role = null;
        this.isIndex = true;
    }

    /**
     * Constructor for an edit mode entry via name and role. .
     * @param name
     * @param role
     */
    public EditEnterCommand(Name name, Role role) {
        requireNonNull(name);
        requireNonNull(role);
        this.targetIndex = null;
        this.name = name;
        this.role = role;
        this.isIndex = false;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredPersonList();

        Application applicationToEdit;

        if (isIndex) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
            }
            applicationToEdit = lastShownList.get(targetIndex.getZeroBased());
        } else {
            SameCompanySameRolePredicate predicate = new SameCompanySameRolePredicate(name, role);
            applicationToEdit = lastShownList.stream().filter(predicate).findFirst()
                    .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER));
        }

        applicationToEdit.setBeingEdited(true);
        return new CommandResult(MESSAGE_ENTER_EDITING_MODE_ACKNOWLEDGEMENT, false, ParserMode.EDITING, false);
    }


}
