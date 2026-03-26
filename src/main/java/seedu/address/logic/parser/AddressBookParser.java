package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.Locale;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditEnterCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FolderCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.OverwriteCommand;
import seedu.address.logic.commands.RemoveReminderCommand;
import seedu.address.logic.commands.StatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.DuplicateApplicationStore;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord").toLowerCase(Locale.ROOT);
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable
        // lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the
        // code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        Command command;

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            command = new AddCommandParser().parse(arguments);
            break;

        case DeleteCommand.COMMAND_WORD:
            command = new DeleteCommandParser().parse(arguments);
            break;

        case StatusCommand.COMMAND_WORD:
            command = new StatusCommandParser().parse(arguments);
            break;

        case ClearCommand.COMMAND_WORD:
            command = new ClearCommand();
            break;

        case FindCommand.COMMAND_WORD:
            command = new FindCommandParser().parse(arguments);
            break;

        case FilterCommand.COMMAND_WORD:
            command = new FilterCommandParser().parse(arguments);
            break;

        case ListCommand.COMMAND_WORD:
            command = new ListCommand();
            break;

        case ExitCommand.COMMAND_WORD:
            command = new ExitCommand();
            break;

        case HelpCommand.COMMAND_WORD:
            command = new HelpCommand();
            break;

        case EditEnterCommand.COMMAND_WORD:
            command = new EditEnterCommandParser().parse(arguments);
            break;

        case OverwriteCommand.COMMAND_WORD:
            command = new OverwriteCommand();
            break;

        case FolderCommand.COMMAND_WORD_FOLDER:
            return new FolderCommandParser(true).parse(arguments);

        case FolderCommand.COMMAND_WORD_TOGGLE:
            return new FolderCommandParser(false).parse(arguments);

        case RemoveReminderCommand.COMMAND_WORD:
            return new RemoveReminderCommandParser().parse(arguments);

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        if (!(command instanceof OverwriteCommand)) {
            if (DuplicateApplicationStore.hasLastDuplicateApplication()) {
                DuplicateApplicationStore.clear();
            }
        }
        return command;
    }
}
