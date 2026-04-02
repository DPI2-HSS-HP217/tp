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
        assert duplicateApplication != null : "duplicate application should not be null";
        Application replacementApplication = duplicateApplication.getApplication();
        assert replacementApplication != null : "replacement application should not be null";

        Application existingApplication = findExistingApplication(model, replacementApplication);

        if (existingApplication == null) {
            handleNoExistingApplication();
        }

        assert existingApplication != null : "existing application should not be null inorder to overwrite";
        overwriteApplication(model, existingApplication, replacementApplication);

        return successResult(replacementApplication);

    }

    /**
     * Checks if there is a stored duplicate application to overwrite.
     *
     * @throws DuplicateApplicationException if no stored duplicate application exists
     */
    private void checkDuplicateExist() throws DuplicateApplicationException {
        if (!DuplicateApplicationStore.hasLastDuplicateApplication()) {
            throw new DuplicateApplicationException(MESSAGE_NO_DUPLICATE);
        }
    }

    /**
     * Retrieves the stored duplicate application for overwriting.
     *
     * @return the AddCommand for the stored duplicate application
     */
    private AddCommand getStoredDuplicate() {
        return DuplicateApplicationStore.getLastDuplicateApplication();
    }

    /**
     * Finds the existing duplicate application with same name and role as new application.
     *
     * @param model the model to search for existing application
     * @param newApplication the new application to find a duplicate of
     * @return the existing application if found
     */
    private Application findExistingApplication(Model model, Application newApplication) {
        assert newApplication != null : "new application should not be null";
        List<Application> currentList = model.getFilteredPersonList();

        return currentList.stream()
                .filter(application -> application.isSameApplication(newApplication))
                .findAny()
                .orElse(null);
    }

    /**
     * Handles the case where no existing application found
     * Clears the stored duplicate application
     *
     * @throws DuplicateApplicationException to indicate no existing application found
     */
    private void handleNoExistingApplication() throws DuplicateApplicationException {
        DuplicateApplicationStore.clear();
        throw new DuplicateApplicationException("No duplicate application found.");
    }

    /**
     * Overwrites the existing application with the new application
     *
     * @param model the model to update with the overwritten application
     * @param oldApplication the existing application to be overwritten
     * @param newApplication the new application to overwrite with
     */
    private void overwriteApplication(Model model, Application oldApplication, Application newApplication) {
        model.deletePerson(oldApplication);
        model.addPerson(newApplication);
        DuplicateApplicationStore.clear();
    }

    private CommandResult successResult(Application newApplication) {
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(newApplication)));
    }
}
