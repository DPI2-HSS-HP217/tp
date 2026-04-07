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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Address;
import seedu.address.model.application.Application;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Name;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Reminder;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    private static final Logger logger = LogsCenter.getLogger(AddCommandParser.class);
    private static final Status DEFAULT_STATUS = new Status("");
    private static final Set<String> VALID_PREFIX = Set.of("n/", "r/", "p/", "a/", "u/", "ud/",
            "e/", "d/", "s/", "t/");

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        if (hasInvalidPrefix(args)) {
            throw new ParseException("Invalid prefix(es):\n " + AddCommand.MESSAGE_USAGE);
        }
        ArgumentMultimap argMultimap = tokenizeArguments(args);
        validatePrefixes(argMultimap);
        Application application = buildApplication(argMultimap);
        return new AddCommand(application);
    }

    private Application buildApplication(ArgumentMultimap argMultimap) throws ParseException {
        Name name = parseRequiredField(argMultimap, PREFIX_NAME, ParserUtil::parseName);
        Role role = parseRequiredField(argMultimap, PREFIX_ROLE, ParserUtil::parseRole);
        Set<Tag> tagList = parseOptionalTags(argMultimap);
        Phone phone = parseIfPresent(argMultimap, PREFIX_PHONE, ParserUtil::parsePhone);
        Email email = parseIfPresent(argMultimap, PREFIX_EMAIL, ParserUtil::parseEmail);
        Address address = parseIfPresent(argMultimap, PREFIX_ADDRESS, ParserUtil::parseAddress);
        Date date = parseIfPresent(argMultimap, PREFIX_DATE, ParserUtil::parseDate);
        Status status = parseStatusIfPresent(argMultimap);
        Reminder reminder = parseReminderIfPresent(argMultimap);

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
        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_ROLE) || !argMultimap.getPreamble().isEmpty()) {
            logger.warning("Missing required prefixes or unexpected preamble");
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_DATE,
                                        PREFIX_ROLE, PREFIX_STATUS, PREFIX_REMINDER, PREFIX_REMINDER_DATE);
    }

    /**
     * Checks if there is any invalid prefix
     *
     * @return true if there is invalid prefix, else return false
     */
    // Solution below adapted from our tp section of
    // FilterCommandParser::containsUnsupportedPrefix
    private boolean hasInvalidPrefix(String args) {
        Pattern pattern = Pattern.compile("(?<=\\s|^)(\\S+/)");
        Matcher matcher = pattern.matcher(args);

        while (matcher.find()) {
            String check = matcher.group(1);
            if (!VALID_PREFIX.contains(check)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Tokenizes the given argument string using the specified prefixes
     *
     * @param args the argument string to tokenize
     * @return an ArgumentMultimap containing the tokenized arguments
     */
    private ArgumentMultimap tokenizeArguments(String args) {
        assert args != null : "argument string should not be null";
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG,
                                        PREFIX_ROLE, PREFIX_STATUS, PREFIX_DATE, PREFIX_REMINDER, PREFIX_REMINDER_DATE);
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
    private interface ParserFunction<T> {
        T parse(String value) throws ParseException;
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
    private <T> T parseIfPresent(ArgumentMultimap argMultimap, Prefix prefix, ParserFunction<T> parser)
                                    throws ParseException {
        assert prefix != null : "prefix cannot be null";
        assert parser != null : "parser function cannot be null";

        if (arePrefixesPresent(argMultimap, prefix)) {
            logger.info(prefix + ": " + argMultimap.getValue(prefix).get());
            return parser.parse(argMultimap.getValue(prefix).get());
        }
        return null;
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
    private <T> T parseRequiredField(ArgumentMultimap argMultimap, Prefix prefix, ParserFunction<T> parser)
                                    throws ParseException {
        logger.info(prefix + ": " + argMultimap.getValue(prefix).get());
        return parser.parse(argMultimap.getValue(prefix).get());
    }

    /**
    * Parses status if present or set to default
    *
    * @param argMultimap the ArgumentMultimap containing the parsed arguments
    * @return a Status object
    * @throws ParseException
    */
    private Status parseStatusIfPresent(ArgumentMultimap argMultimap) throws ParseException {
        if (arePrefixesPresent(argMultimap, PREFIX_STATUS)) {
            logger.info("status: " + argMultimap.getValue(PREFIX_STATUS).get());
            return ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        }
        return DEFAULT_STATUS;
    }

    /**
     * Parses reminder if both reminder name and date is present.
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Reminder object if present, else null
     * @throws ParseException if only reminder name or date is provided
     */
    private Reminder parseReminderIfPresent(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasReminder = arePrefixesPresent(argMultimap, PREFIX_REMINDER);
        boolean hasReminderDate = arePrefixesPresent(argMultimap, PREFIX_REMINDER_DATE);

        if (hasReminder && hasReminderDate) {
            logger.info("reminder: " + argMultimap.getValue(PREFIX_REMINDER).get() + ", date: "
                                            + argMultimap.getValue(PREFIX_REMINDER_DATE).get());

            return ParserUtil.parseReminder(argMultimap.getValue(PREFIX_REMINDER).get(),
                                            argMultimap.getValue(PREFIX_REMINDER_DATE).get());
        }
        if (hasReminder || hasReminderDate) {
            throw new ParseException("Both reminder (u/) and reminder date (ud/) must be provided together.");
        }
        return null;
    }

    /**
     * Parses tags if present
     *
     * @param argMultimap the ArgumentMultimap containing the parsed arguments
     * @return a Set of Tag objects if present
     * @throws ParseException
     */
    private Set<Tag> parseOptionalTags(ArgumentMultimap argMultimap) throws ParseException {
        return ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
    }
}
