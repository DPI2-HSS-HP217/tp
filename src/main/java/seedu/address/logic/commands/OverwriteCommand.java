package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Application;
import seedu.address.model.person.DuplicateApplicationStore;
import seedu.address.model.person.exceptions.DuplicateApplicationException;


/**
 * Overwrites the last duplicate application.
 */
public class OverwriteCommand extends Command {

    public static final String COMMAND_WORD = "overwrite";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Replaces existing application\n";

    public static final String MESSAGE_SUCCESS = "Application updated to the new application: %1$s";
    public static final String MESSAGE_NO_DUPLICATE = "No duplicate application to overwrite.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkDuplicateExist();
        AddCommand duplicateApplication = getStoredDuplicate();
        Application replacementApplication = duplicateApplication.getApplication();

        Application existingApplication = findExistingApplication(model, replacementApplication);

        if (existingApplication == null) {
            handleNoExistingApplication();
        }

        overwriteApplication(model, existingApplication, replacementApplication);

        return successResult(replacementApplication);

    }

    private void checkDuplicateExist() throws DuplicateApplicationException {
        if (!DuplicateApplicationStore.hasLastDuplicateApplication()) {
            throw new DuplicateApplicationException(MESSAGE_NO_DUPLICATE);
        }
    }

    private AddCommand getStoredDuplicate() {
        return DuplicateApplicationStore.getLastDuplicateApplication();
    }

    private Application findExistingApplication(Model model, Application newApplication) {
        List<Application> currentList = model.getFilteredPersonList();
        return currentList.stream()
                .filter(application -> application.isSameApplication(newApplication))
                .findAny()
                .orElse(null);
    }

    private void handleNoExistingApplication() throws DuplicateApplicationException {
        DuplicateApplicationStore.clear();
        throw new DuplicateApplicationException("No duplicate application found.");
    }

    private void overwriteApplication(Model model, Application oldApplication, Application newApplication) {
        model.deletePerson(oldApplication);
        model.addPerson(newApplication);
        DuplicateApplicationStore.clear();
    }

    private CommandResult successResult(Application newApplication) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newApplication)));
    }
}
