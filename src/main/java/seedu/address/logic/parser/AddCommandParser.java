package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Application;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = tokenizeArguments(args);
        checkRequiredPrefixes(argMultimap);
        checkNoDuplicatePrefixes(argMultimap);

        Name name = parseName(argMultimap);
        Phone phone = parsePhone(argMultimap);
        Email email = parseEmail(argMultimap);
        Address address = parseAddress(argMultimap);
        Set<Tag> tagList = parseTags(argMultimap);
        Date date = parseDate(argMultimap);
        Status status = parseStatus(argMultimap);
        Role role = parseRole(argMultimap);

        Application application = new Application(name, phone, email, address, tagList, date, role, status);

        return new AddCommand(application);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    private ArgumentMultimap tokenizeArguments(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DATE);
    }

    private void checkRequiredPrefixes(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ADDRESS, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_DATE,
                    PREFIX_ROLE, PREFIX_STATUS)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
    }

    private void checkNoDuplicatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DATE,
                PREFIX_ROLE, PREFIX_STATUS);
    }

    private Name parseName(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
    }

    private Role parseRole(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
    }

    private Date parseDate(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
    }

    private Email parseEmail(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
    }

    private Address parseAddress(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
    }

    private Set<Tag> parseTags(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
    }

    private Status parseStatus(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
    }

    private Phone parsePhone(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
    }
}
