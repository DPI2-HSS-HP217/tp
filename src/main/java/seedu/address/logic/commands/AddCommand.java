package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
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
            + "Required fields: n/NAME r/ROLE and the other fields are optional\n"
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_DATE + "DATE "
            + PREFIX_STATUS + "STATUS "
            + PREFIX_TAG + "[TAGS...] "
            + PREFIX_REMINDER + "REMINDER "
            + PREFIX_REMINDER_DATE + "REMINDER_DATE\n"
            + "Example:\n"
            + "add n/Microsoft p/4258828080 e/jobs@microsoft.com "
            + "a/One Microsoft Way, Redmond, WA "
            + "d/2024-03-16 r/Product Manager "
            + "s/interview t/tech "
            + "u/final interview ud/2024-03-14\n"
            + "Note: both reminder and reminder date must be provided to add a reminder\n";

    public static final String MESSAGE_SUCCESS = "New application added: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON =
            "This application already exists. \nExisting application: %1$s\n"
            + "Use `overwrite` to replace existing application";
    public static final String INVALID_DATE = "OOPS! Invalid date format, use the format (YYYY-MM-DD)";

    private final Logger logger = LogsCenter.getLogger(AddCommand.class);
    private final Application toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Application person) {
        requireNonNull(person);

        assert person.getName() != null : "company name cannot be null";
        assert person.getRole() != null : "role cannot be null";

        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        checkForDuplicate(model);
        model.addPerson(toAdd);
        clearStoredDuplicate();

        assert model.hasPerson(toAdd) : "failed to add application to model";
        logger.info("New application added: " + toAdd);
        return successResult();
    }

    /**
     * Checks and stores if application with same role and name already exists.
     *
     * @param model the model to check for duplicates in
     * @throws CommandException if a duplicate application is found
     */
    private void checkForDuplicate(Model model) throws CommandException {
        if (model.hasPerson(toAdd)) {
            Application existingApplication = model.getFilteredPersonList().stream()
                    .filter(application -> application.isSameApplication(toAdd))
                    .findAny()
                    .orElse(null);

            assert existingApplication != null : "should have found existing application";

            DuplicateApplicationStore.setLastDuplicateApplication(this);

            String existingMessage = String.format(MESSAGE_DUPLICATE_PERSON, formatApplication(existingApplication));
            throw new CommandException(existingMessage);
        }

        logger.info("No duplicate application found for: " + toAdd);
    }

    /**
     * Clears stored duplicate application
     */
    private void clearStoredDuplicate() {
        DuplicateApplicationStore.clear();
    }

    private CommandResult successResult() {
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    public Application getApplication() {
        return toAdd;
    }

    /**
     * Formats existing duplicate application for display in error message
     *
     * @param existingApplication the existing application to format
     * @return a formatted string representation of the existing application
     */
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
