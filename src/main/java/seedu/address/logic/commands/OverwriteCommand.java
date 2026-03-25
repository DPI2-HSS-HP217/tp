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

        if (!DuplicateApplicationStore.hasLastDuplicateApplication()) {
            throw new DuplicateApplicationException(MESSAGE_NO_DUPLICATE);
        }

        AddCommand duplicateApplication = DuplicateApplicationStore.getLastDuplicateApplication();
        Application replacementApplication = duplicateApplication.getApplication();

        List<Application> currentList = model.getFilteredPersonList();
        Application existingApplication = currentList.stream()
                .filter(application -> application.isSameApplication(replacementApplication))
                .findAny()
                .orElse(null);

        if (existingApplication == null) {
            DuplicateApplicationStore.clear();
            throw new DuplicateApplicationException("No duplicate application found.");
        }

        model.deletePerson(existingApplication);
        model.addPerson(replacementApplication);
        DuplicateApplicationStore.clear();

        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(replacementApplication)));
    }
}
