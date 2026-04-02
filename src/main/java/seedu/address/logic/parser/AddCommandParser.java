package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
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

import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Application;
import seedu.address.model.person.Date;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Reminder;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.tag.Tag;
/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = tokenizeArguments(args);
        validatePrefixes(argMultimap);
        Application application = buildApplication(argMultimap);
        return new AddCommand(application);
    }

    private Application buildApplication(ArgumentMultimap argMultimap) throws ParseException {
        Name name = parseName(argMultimap);
        Role role = parseRole(argMultimap);
        Set<Tag> tagList = parseTags(argMultimap);
        Phone phone = parsePhone(argMultimap);
        Email email = parseEmail(argMultimap);
        Address address = parseAddress(argMultimap);
        Date date = parseDate(argMultimap);
        Status status = parseStatus(argMultimap);
        Reminder reminder = parseReminder(argMultimap);

        assert name != null : "company name should not be null";
        assert role != null : "role should not be null";

        return new Application(name, phone, email, address, tagList, date, role, status, reminder);
    }

    /**
     * Validates that the required prefixes are present and that there are no duplicate prefixes
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @throws ParseException if the required prefixes are missing, if there are duplicate prefixes,
     *          or if there is an unexpected preamble
     */
    private void validatePrefixes(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE)
                || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Missing required prefixes or unexpected preamble");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL,
                PREFIX_ADDRESS, PREFIX_DATE, PREFIX_ROLE, PREFIX_STATUS, PREFIX_REMINDER, PREFIX_REMINDER_DATE);
    }

    /**
     * Tokenizes the given argument string using the specified prefixes
     *
     * @param args the argument string to tokenize
     * @return an ArgumentMultimap containing the tokenized arguments
     */
    private ArgumentMultimap tokenizeArguments(String args) {
        assert args != null : "argument string should not be null";
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_ROLE, PREFIX_STATUS, PREFIX_DATE, PREFIX_REMINDER, PREFIX_REMINDER_DATE);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Parses phone number if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Phone object if present, else null
     * @throws ParseException
     */
    private Phone parsePhone(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_PHONE)) {
            logger.info("phone: " + argMultimap.getValue(PREFIX_PHONE).get());
            return ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        } else {
            return null;
        }
    }

    /**
     * Parses email if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Email object if present, else null
     * @throws ParseException
     */
    private Email parseEmail(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_EMAIL)) {
            logger.info("email: " + argMultimap.getValue(PREFIX_EMAIL).get());
            return ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        } else {
            return null;
        }
    }

    /**
     * Parses address if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Address object if present, else null
     * @throws ParseException
     */
    private Address parseAddress(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_ADDRESS)) {
            logger.info("address: " + argMultimap.getValue(PREFIX_ADDRESS).get());
            return ParserUtil.parseAddress(argMultimap.getValue(PREFIX_ADDRESS).get());
        } else {
            return null;
        }
    }

    /**
     * Parses status if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Status object if present, else null
     * @throws ParseException
     */
    private Status parseStatus(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_STATUS)) {
            logger.info("status: " + argMultimap.getValue(PREFIX_STATUS).get());
            return ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        } else {
            return null;
        }
    }

    /**
     * Parses date if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Date object if present, else null
     * @throws ParseException if the date format is invalid or if the date is in the future
     */
    private Date parseDate(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            String dateString = argMultimap.getValue(PREFIX_DATE).get();
            logger.info("date: " + dateString);

            try {
                Date date = ParserUtil.parseDate(dateString);
                if (!date.checkNotFutureDate()) {
                    throw new ParseException(Date.MESSAGE_FUTURE_DATE);
                }
                return date;
            } catch (IllegalArgumentException e) {
                throw new ParseException(Date.MESSAGE_CONSTRAINTS);
            }
        }
        return null;
    }

    /**
     * Parses reminder if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Reminder object if present, else null
     * @throws ParseException
     */
    private Reminder parseReminder(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_REMINDER, PREFIX_REMINDER_DATE)) {

            logger.info("reminder: " + argMultimap.getValue(PREFIX_REMINDER).get());
            logger.info("reminder date: " + argMultimap.getValue(PREFIX_REMINDER_DATE).get());

            return ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER).get(),
                        argMultimap.getValue(PREFIX_REMINDER_DATE).get());
        } else {
            return null;
        }
    }

    /**
     * Parses tags if present
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Set of Tag objects if present
     * @throws ParseException
     */
    private Set<Tag> parseTags(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
    }

    /**
     * Parses name
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Name object
     * @throws ParseException
     */
    private Name parseName(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
    }

    /**
     * Parses role
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Role object
     * @throws ParseException
     */
    private Role parseRole(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get());
    }
}
