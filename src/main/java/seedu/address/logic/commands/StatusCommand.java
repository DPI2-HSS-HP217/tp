package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Application;
import seedu.address.model.person.Status;

/**
 * Updates the status of an application by company name.
 */
public class StatusCommand extends Command {

    public static final String COMMAND_WORD = "status";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates an application's status.\n"
                                    + "Parameters: n/NAME r/ROLE s/STATUS\n"
                                    + "Example: status n/Google r/CEO s/Interviewing";

    public static final String MESSAGE_SUCCESS = "Updated status: %1$s";

    private final String name;
    private final String role;
    private final Status status;

    /**
     * Creates a StatusCommand to update the status.
     */
    public StatusCommand(String name, String role, Status status) {
        requireNonNull(name);
        requireNonNull(status);
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public Status getStatus() {
        return status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Application> applicationList = model.getFilteredPersonList();

        Application target = null;

        for (Application app : applicationList) {
            if (app.getName().fullName.equalsIgnoreCase(name) && app.getRole().value.equalsIgnoreCase(role)) {
                target = app;
                break;
            }
        }

        if (target == null) {
            throw new CommandException("Application not found.");
        }

        Application updatedApplication = new Application(target.getName(), target.getPhone(), target.getEmail(),
                                        target.getAddress(), target.getTags(), target.getDate(), target.getRole(),
                                        status);

        model.setPerson(target, updatedApplication);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(updatedApplication)));
    }
}
