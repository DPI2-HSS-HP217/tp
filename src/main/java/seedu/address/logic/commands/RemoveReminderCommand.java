package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Address;
import seedu.address.model.person.Application;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.SameCompanySameRolePredicate;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;

/**
 * Command Class to remove reminder of the specified Applciation.
 */
public class RemoveReminderCommand extends Command {

    public static final String COMMAND_WORD = "rmr";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ":\n"
            + "Remove Reminder via INDEX (Remove the reminder of the Application identified "
            + "by the index number in the displayed application list)\n"
            + "Remove Reminder via Name and Role (Deletes the reminder of Application with the exact Name and Role) \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: Name (String), Role (String with no numbers) \n"
            + "Example: " + COMMAND_WORD + " n/Google r/CEO";


    public static final String MESSAGE_REMOVE_REMINDER_SUCCESS = "Removed Reminder: %1$s";

    private final boolean isIndexDelete;
    private final Index targetIndex;
    private final Name name;
    private final Role role;

    /**
     * Constructor for a Delete via Index command.
     * @param targetIndex
     */
    public RemoveReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.name = null;
        this.role = null;
        isIndexDelete = true;
    }

    /**
     * Constructor for a Delete via Application command.
     *
     * @param name
     * @param role
     */
    public RemoveReminderCommand(Name name, Role role) {
        this.targetIndex = null;
        this.name = name;
        this.role = role;
        isIndexDelete = false;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        Application applicationToEdit;
        if (isIndexDelete) {
            applicationToEdit = getTargetApplicationByIndex(model);
        } else {
            applicationToEdit = getTargetApplicationByApplication(model);
        }
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        Application removedReminder = createEditedPerson(applicationToEdit, editPersonDescriptor);
        model.setPerson(applicationToEdit, removedReminder);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_REMINDER_SUCCESS, Messages.format(removedReminder)));
    }


    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Application createEditedPerson(Application personToEdit,
                                                  EditCommand.EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        Date updatedDate = editPersonDescriptor.getDate().orElse(personToEdit.getDate());
        Role updatedRole = editPersonDescriptor.getRole().orElse(personToEdit.getRole());
        Status updatedStatus = editPersonDescriptor.getStatus().orElse(personToEdit.getStatus());

        return new Application(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedTags, updatedDate, updatedRole, updatedStatus);
    }


    /**
     * Execute a delete via Index command.
     *
     * @param model current model.
     * @return result of the command execution.
     * @throws CommandException if command is invalid.
     */
    public Application getTargetApplicationByIndex(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }
        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Execute a delete via Application command.
     *
     * @param model current model.
     * @return result of the command execution.
     * @throws CommandException if command is invalid.
     */
    public Application getTargetApplicationByApplication(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredPersonList();
        SameCompanySameRolePredicate predicate = new SameCompanySameRolePredicate(name, role);

        return lastShownList.stream().filter(predicate).findFirst()
                .orElseThrow(() -> new CommandException(Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER));
    }



    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RemoveReminderCommand)) {
            return false;
        }

        RemoveReminderCommand otherRemovalCommand = (RemoveReminderCommand) other;

        if (isIndexDelete != otherRemovalCommand.isIndexDelete) {
            return false;
        }

        if (isIndexDelete) {
            return targetIndex.equals(otherRemovalCommand.targetIndex);
        } else {
            return name.equals(otherRemovalCommand.name)
                    && role.equals(otherRemovalCommand.role);
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
