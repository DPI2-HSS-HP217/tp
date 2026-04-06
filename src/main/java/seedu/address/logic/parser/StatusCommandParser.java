package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.stream.Stream;

import seedu.address.logic.commands.StatusCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;

/**
 * Parses input arguments and creates a new StatusCommand object.
 */
public class StatusCommandParser implements Parser<StatusCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatusCommand
     * and returns a StatusCommand object for execution.
     */
    public StatusCommand parse(String args) throws ParseException {
        requireNonNull(args);

        try {
            ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                    PREFIX_NAME, PREFIX_ROLE, PREFIX_STATUS,
                    PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DATE);

            if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE, PREFIX_STATUS)
                    || !argMultimap.getPreamble().isEmpty()
                    || containsUnsupportedPrefixes(argMultimap)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
            }

            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_ROLE, PREFIX_STATUS);

            Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
            Role role = ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
            Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());

            return new StatusCommand(name.fullName, role.value, status);
        } catch (ParseException pe) {
            throw new ParseException(pe.getMessage(), pe);
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if any prefix unsupported by the status command is present.
     */
    private static boolean containsUnsupportedPrefixes(ArgumentMultimap argumentMultimap) {
        return Stream.of(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG, PREFIX_DATE)
                .anyMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
