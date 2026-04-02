package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Application;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;
import seedu.address.model.person.SameCompanySameRolePredicate;

/**
 * Deletes an Application via it's displayed index in the address book or via it's Company Name and Role.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":\n"
            + "Delete via INDEX (Deletes the Application identified by the index number in the displayed person list)\n"
            + "Delete via Name and Role (Deletes the Application with the exact Name and Role) \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: Name (String), Role (String with no numbers) \n"
            + "Example: " + COMMAND_WORD + " n/Goog r/CEO";


    public static final String MESSAGE_DELETE_APPLICATION_SUCCESS = "Deleted Application: %1$s";

    private final boolean isIndexDelete;
    private final Index targetIndex;
    private final Name name;
    private final Role role;

    /**
     * Constructs a DeleteCommand via Index.
     *
     * @param targetIndex Application index.
     */
    public DeleteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.name = null;
        this.role = null;
        isIndexDelete = true;
    }

    /**
     * Constructs a DeleteCommand via Company Name and Role or Application.
     *
     * @param name Company Name.
     * @param role Company Job Role.
     */
    public DeleteCommand(Name name, Role role) {
        this.targetIndex = null;
        this.name = name;
        this.role = role;
        isIndexDelete = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (isIndexDelete) {
            return executeDeleteByIndex(model);
        } else {
            return executeDeleteByApplication(model);
        }
    }

    /**
     * Executes a Delete via Index command.
     *
     * @param model current model.
     * @return result of the command execution.
     * @throws CommandException If specified Application Index is invalid.
     */
    public CommandResult executeDeleteByIndex(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }
        Application personToDelete = lastShownList.get(targetIndex.getZeroBased());
        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_APPLICATION_SUCCESS, Messages.format(personToDelete)));
    }

    /**
     * Executes a delete via Application Name and role command.
     *
     * @param model Current model.
     * @return Result of the command execution.
     * @throws CommandException If Target Application is invalid or cannot be found.
     */
    public CommandResult executeDeleteByApplication(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredPersonList();
        SameCompanySameRolePredicate predicate = new SameCompanySameRolePredicate(name, role);
        Application personToDelete = lastShownList.stream().filter(predicate).findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER));

        model.deletePerson(personToDelete);
        return new CommandResult(String.format(MESSAGE_DELETE_APPLICATION_SUCCESS, Messages.format(personToDelete)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;

        if (isIndexDelete != otherDeleteCommand.isIndexDelete) {
            return false;
        }

        if (isIndexDelete) {
            return targetIndex.equals(otherDeleteCommand.targetIndex);
        } else {
            return name.equals(otherDeleteCommand.name)
                    && role.equals(otherDeleteCommand.role);
        }
    }

    @Override
    public String toString() {
        if (isIndexDelete) {
            return new ToStringBuilder(this)
                    .add("targetIndex", targetIndex)
                    .toString();
        } else {
            return new ToStringBuilder(this)
                    .add("targetName", name)
                    .add("targetRole", role)
                    .toString();
        }
    }
}
