package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Address;
import seedu.address.model.application.Application;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Name;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Role;
import seedu.address.model.application.SameCompanySameRolePredicate;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

/**
 * Removes reminder of the specified Application.
 */
public class RemoveReminderCommand extends Command {

    public static final String COMMAND_WORD = "rmr";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ":\n"
            + "Remove Reminder via INDEX (Remove the reminder of the Application identified "
            + "by the index number in the displayed application list)\n"
            + "Remove Reminder via Name and Role (Deletes the reminder of Application with the exact Name and Role) \n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "Parameters: Name (String), Role (String with no numbers) \n"
            + "Example: " + COMMAND_WORD + " n/Google r/CEO";

    public static final String MESSAGE_REMOVE_REMINDER_SUCCESS = "Removed Reminder: %1$s";
    public static final String NO_REMINDER_ERROR_MSG = "Target Application has no reminder!";
    private final Logger logger = LogsCenter.getLogger(RemoveReminderCommand.class);

    private final boolean isIndexDelete;
    private final Index targetIndex;
    private final Name name;
    private final Role role;

    /**
     * Constructs a Remove Reminder via Index Command.
     *
     * @param targetIndex List Index of targeted Application.
     */
    public RemoveReminderCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
        this.name = null;
        this.role = null;
        isIndexDelete = true;
    }

    /**
     * Constructs a Remove Reminder via Reference Command.
     *
     * @param name Company Name of Application.
     * @param role Role of Application.
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
            logger.info("Index reminder removal");
            applicationToEdit = getTargetApplicationByIndex(model);
        } else {
            logger.info("Application Name and Role reminder removal");
            applicationToEdit = getTargetApplicationByApplication(model);
        }
        if (!applicationToEdit.hasReminder()) {
            throw new CommandException(NO_REMINDER_ERROR_MSG);
        }
        EditCommand.EditApplicationDescriptor editApplicationDescriptor = new EditCommand.EditApplicationDescriptor();
        Application removedReminder = createEditedApplication(applicationToEdit, editApplicationDescriptor);
        model.setApplication(applicationToEdit, removedReminder);
        model.updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
        assert !removedReminder.hasReminder() : "Reminder not removed";
        return new CommandResult(String.format(MESSAGE_REMOVE_REMINDER_SUCCESS, Messages.format(removedReminder)));
    }

    /**
     * Creates and returns a {@code Application} with the details of {@code applicationToEdit}
     * edited with {@code editApplicationDescriptor} without reminder.
     */
    private static Application createEditedApplication(Application applicationToEdit,
                                    EditCommand.EditApplicationDescriptor editApplicationDescriptor) {
        assert applicationToEdit != null;
        Name updatedName = editApplicationDescriptor.getName().orElse(applicationToEdit.getName());
        Phone updatedPhone = editApplicationDescriptor.getPhone().orElse(applicationToEdit.getPhone());
        Email updatedEmail = editApplicationDescriptor.getEmail().orElse(applicationToEdit.getEmail());
        Address updatedAddress = editApplicationDescriptor.getAddress().orElse(applicationToEdit.getAddress());
        Set<Tag> updatedTags = editApplicationDescriptor.getTags().orElse(applicationToEdit.getTags());
        Date updatedDate = editApplicationDescriptor.getDate().orElse(applicationToEdit.getDate());
        Role updatedRole = editApplicationDescriptor.getRole().orElse(applicationToEdit.getRole());
        Status updatedStatus = editApplicationDescriptor.getStatus().orElse(applicationToEdit.getStatus());

        return new Application(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedDate,
                                        updatedRole, updatedStatus);
    }

    /**
     * Returns Application via specified index.
     *
     * @param model Current model.
     * @return Application of specified index in the current filtered list.
     * @throws CommandException If specified Application Index do not exist or is invalid.
     */
    private Application getTargetApplicationByIndex(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        assert targetIndex != null;
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
        }
        return lastShownList.get(targetIndex.getZeroBased());
    }

    /**
     * Returns Application via specified Name and Role.
     *
     * @param model Current model.
     * @throws CommandException If specified Application cannot be found or is invalid.
     */
    private Application getTargetApplicationByApplication(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();
        SameCompanySameRolePredicate predicate = new SameCompanySameRolePredicate(name, role);

        return lastShownList.stream()
                .filter(predicate)
                .findFirst()
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
            return name.equals(otherRemovalCommand.name) && role.equals(otherRemovalCommand.role);
        }
    }

    @Override
    public String toString() {
        if (isIndexDelete) {
            return new ToStringBuilder(this).add("targetIndex", targetIndex).toString();
        } else {
            return new ToStringBuilder(this).add("targetName", name).add("targetRole", role).toString();
        }
    }
}
