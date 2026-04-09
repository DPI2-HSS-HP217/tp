package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.EditExitCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input for editing mode.
 */
public class AddressBookEditingParser extends AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookEditingParser.class);

    /**
     * Parses user input into command for execution. Overrides parent class
     * parsing to reflect the only commands available during editing mode.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public Command parseCommand(String userInput) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {

            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments + "; Full input: " + userInput.trim());

        if (commandWord.equals(EditExitCommand.COMMAND_WORD)) {
            return new EditExitCommand();
        }
        // "edit " appended for one reason: ArgumentTokenizer expects a preamble / command word
        // and will ignore it. EditCommand's new syntax has no preamble, so this causes
        // the first arg to be ignored. This hack-y method isn't ideal, but minimizes how much
        // of the code has to be reworked to account for this ONE unique case.
        // Better to adapt one unique format to the standard than the opposite I assume?
        return new EditCommandParser().parse("edit " + userInput.trim());

    }

}
