package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CompanyContainsKeywordPredicate;
import seedu.address.model.person.DateMatchesPredicate;
import seedu.address.model.person.StatusMatchesPredicate;
import seedu.address.model.person.TagMatchesPredicate;
import seedu.address.model.tag.Tag;

/**
 * Parses input arguments and creates a new {@code FilterCommand}.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_UNKNOWN_FILTER_COMMAND = "Sorry I don't understand";
    public static final String MESSAGE_UNKNOWN_FILTER_TYPE =
            "OOPS! Unknown filter type. Use company, deadline, applied, status, or tag.";
    public static final String MESSAGE_INVALID_DATE_FORMAT = "Invalid date format. Use YYYY-MM-DD.";
    public static final String MESSAGE_INVALID_COMPANY_FORMAT =
            "OOPS! Invalid format, use format: /filter /company /<keyword>";
    public static final String MESSAGE_INVALID_APPLIED_FORMAT =
            "OOPS! Invalid format, use format: /filter /applied /<YYYY-MM-DD>";
    public static final String MESSAGE_INVALID_STATUS_FORMAT =
            "OOPS! Invalid format, use format: /filter /status /<status>";
    public static final String MESSAGE_INVALID_TAG_FORMAT =
            "OOPS! Invalid format, use format: /filter /tag /<tag>";

    private static final Pattern FILTER_ARGUMENTS_FORMAT = Pattern.compile("^/(?<type>\\S+)(?<value>.*)$");

    @Override
    public FilterCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(MESSAGE_UNKNOWN_FILTER_COMMAND);
        }

        Matcher matcher = FILTER_ARGUMENTS_FORMAT.matcher(trimmedArgs);
        if (!matcher.matches()) {
            throw new ParseException(MESSAGE_UNKNOWN_FILTER_COMMAND);
        }

        String filterType = matcher.group("type").toLowerCase(Locale.ROOT);
        String rawValue = matcher.group("value").trim();

        switch (filterType) {
        case "company":
            return parseCompanyFilter(rawValue);
        case "applied":
            return parseAppliedFilter(rawValue);
        case "status":
            return parseStatusFilter(rawValue);
        case "tag":
            return parseTagFilter(rawValue);
        default:
            throw new ParseException(MESSAGE_UNKNOWN_FILTER_TYPE);
        }
    }

    private FilterCommand parseCompanyFilter(String rawValue) throws ParseException {
        String companyKeyword = extractValue(rawValue);
        if (companyKeyword.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_COMPANY_FORMAT);
        }
        return new FilterCommand(new CompanyContainsKeywordPredicate(companyKeyword));
    }

    private FilterCommand parseAppliedFilter(String rawValue) throws ParseException {
        String appliedDate = extractValue(rawValue);
        if (appliedDate.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_APPLIED_FORMAT);
        }
        if (!ParserUtil.isParsableDate(appliedDate)) {
            throw new ParseException(MESSAGE_INVALID_DATE_FORMAT);
        }
        return new FilterCommand(new DateMatchesPredicate(appliedDate));
    }

    private FilterCommand parseStatusFilter(String rawValue) throws ParseException {
        String status = extractValue(rawValue);
        if (status.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_STATUS_FORMAT);
        }
        return new FilterCommand(new StatusMatchesPredicate(status));
    }

    private FilterCommand parseTagFilter(String rawValue) throws ParseException {
        String tag = extractValue(rawValue);
        if (tag.isEmpty()) {
            throw new ParseException(MESSAGE_INVALID_TAG_FORMAT);
        }
        if (!Tag.isValidTagName(tag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new FilterCommand(new TagMatchesPredicate(tag));
    }

    private String extractValue(String rawValue) {
        if (rawValue.isEmpty()) {
            return "";
        }

        if (rawValue.startsWith("/")) {
            return rawValue.substring(1).trim();
        }

        return rawValue.trim();
    }
}
