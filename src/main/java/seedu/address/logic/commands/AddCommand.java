package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Application;
import seedu.address.model.person.DuplicateApplicationStore;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a company application.\n"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_DATE + "DATE "
            + PREFIX_STATUS + "STATUS "
            + PREFIX_TAG + "[TAGS...]\n"
            + "Example:\n"
            + "add n/Microsoft p/4258828080 e/jobs@microsoft.com "
            + "a/One Microsoft Way, Redmond, WA "
            + "d/2024-03-16 r/Product Manager "
            + "s/interviewed t/tech";

    public static final String MESSAGE_SUCCESS = "New application added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This application already exists. \nExisting application: %1$s\n"
            + "Use `overwrite` to replace existing application";
    public static final String INVALID_DATE = "OOPS! Invalid date format, use the format (YYYY-MM-DD)";


    private final Application toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Application person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkForDuplicate(model);
        model.addPerson(toAdd);
        clearStoredDuplicate();

        return successResult();
    }

    private void checkForDuplicate(Model model) throws CommandException {
        if (model.hasPerson(toAdd)) {
            Application existingApplication = model.getFilteredPersonList().stream()
                    .filter(application -> application.isSameApplication(toAdd))
                    .findAny()
                    .orElse(null);
            DuplicateApplicationStore.setLastDuplicateApplication(this);

            String existingMessage = String.format(MESSAGE_DUPLICATE_PERSON, formatApplication(existingApplication));
            throw new CommandException(existingMessage);
        }
    }

    private void clearStoredDuplicate() {
        DuplicateApplicationStore.clear();
    }

    private CommandResult successResult() {
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    public Application getApplication() {
        return toAdd;
    }

    private String formatApplication(Application existingApplication) {
        if (existingApplication == null) {
            return "No existing application found.";
        }
        return Messages.format(existingApplication);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
