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
        Name name = parseRequiredField(argMultimap, PREFIX_NAME, ParserUtil::parseName);
        Role role = parseRequiredField(argMultimap, PREFIX_ROLE, ParserUtil::parseRole);
        Set<Tag> tagList = parseTags(argMultimap);
        Phone phone = parseOptionalField(argMultimap, PREFIX_PHONE, ParserUtil::parsePhone);
        Email email = parseOptionalField(argMultimap, PREFIX_EMAIL, ParserUtil::parseEmail);
        Address address = parseOptionalField(argMultimap, PREFIX_ADDRESS, ParserUtil::parseAddress);
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
     * Creates a functional interface for parser that throw ParseException.
     */
    @FunctionalInterface
    private interface ParserFunction<T, R> {
        R parse(T t) throws ParseException;
    }

    /**
     * Parses required field (name and role)
     *
     * @param <T> the type of the field to parse
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @param prefix the prefix of the field
     * @param parser a function used to parse the field
     * @return the parsed field
     * @throws ParseException
     */
    private <T> T parseRequiredField(ArgumentMultimap argMultimap, Prefix prefix, ParserFunction<String, T> parser)
            throws ParseException {
        logger.info(prefix + ": " + argMultimap.getValue(prefix).get());
        return parser.parse(argMultimap.getValue(prefix).get());
    }

    /**
     * Parses an optional field if the prefix is present, else returns null
     *
     * @param <T> the type of the field to parse
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @param prefix the prefix of the field
     * @param parser a function used to parse the field
     * @return the parsed field if prefix present, else null
     * @throws ParseException
     */
    private <T> T parseOptionalField(ArgumentMultimap argMultimap, Prefix prefix, ParserFunction<String, T> parser)
            throws ParseException {
        if (arePrefixesPresent(argMultimap, prefix)) {
            logger.info(prefix + ": " + argMultimap.getValue(prefix).get());
            return parser.parse(argMultimap.getValue(prefix).get());
        }
        return null;
    }

    /**
     * Parses status if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Status object
     * @throws ParseException
     */
    private Status parseStatus(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_STATUS)) {
            String statusValue = argMultimap.getValue(PREFIX_STATUS).get();
            logger.info("status: " + statusValue);
            if (statusValue == null || statusValue.trim().isEmpty()) {
                return new Status("");
            }
            return ParserUtil.parseStatus(statusValue);
        }
        return new Status("");
    }

    /**
     * Parses date if present else return null
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Date object if present, else null
     * @throws ParseException if the date format is invalid or if the date is in the future
     */
    private Date parseDate(ArgumentMultimap argMultimap) throws ParseException {
        if (!arePrefixesPresent(argMultimap, PREFIX_DATE)) {
            return null;
        }
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

    /**
     * Parses reminder if both reminder name and date is present.
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Reminder object if present, else null
     * @throws ParseException if only reminder name or date is provided
     */
    private Reminder parseReminder(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasReminderName = arePrefixesPresent(argMultimap, PREFIX_REMINDER);
        boolean hasReminderDate = arePrefixesPresent(argMultimap, PREFIX_REMINDER_DATE);
        if (hasReminderName && hasReminderDate) {
            String reminderName = argMultimap.getValue(PREFIX_REMINDER).get();
            String reminderDate = argMultimap.getValue(PREFIX_REMINDER_DATE).get();
            logger.info("reminder: " + reminderName);
            logger.info("reminder date: " + reminderDate);

            validateReminderFields(reminderName, reminderDate);
            logger.info("reminder: " + reminderName + ", reminder date: " + reminderDate);
            return ParserUtil.parseReminder(reminderName, reminderDate);
        }

        if (hasReminderName || hasReminderDate) {
            throw new ParseException(
                    "Both reminder (u/) and reminder date (ud/) must be provided to add a reminder");
        }
        return null;
    }

    /**
     * Checks both reminder name and date is present
     *
     * @param reminderName name of the reminder
     * @param reminderDate date of the reminder
     * @throws ParseException if reminder name or date is missing
     */
    private void validateReminderFields(String reminderName, String reminderDate) throws ParseException {
        if (reminderName == null || reminderName.isEmpty()) {
            throw new ParseException("Reminder name cannot be empty");
        }
        if (reminderDate == null || reminderDate.isEmpty()) {
            throw new ParseException("Reminder date cannot be empty");
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
}
