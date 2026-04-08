package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.application.Address;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Name;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Reminder;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String DEFAULT_STATUS = "";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        assert trimmedName != null : "trimmed name can never be null";
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (trimmedTag.isEmpty()) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            String trimmedTag = tagName.trim();
            if (trimmedTag.isEmpty()) {
                continue;
            }
            tagSet.add(parseTag(trimmedTag));
        }
        return tagSet;
    }

    /**
     * Parses a {@code String date} into a {@code Date}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code date} is invalid format or future date.
     */
    public static Date parseDate(String date) throws ParseException {
        requireNonNull(date);
        String trimmedDate = date.trim();
        validateDateFormat(trimmedDate);
        Date parsedDate = new Date(trimmedDate);
        assert parsedDate != null : "date can never be null";
        validateNotFutureDate(parsedDate);
        return parsedDate;
    }

    /**
     * Checks that date follows YYYY-MM-DD and is a valid date
     *
     * @param date the date to check
     * @throws ParseException if invalid format for date
     */
    private static void validateDateFormat(String date) throws ParseException {
        if (!Date.isValidDate(date)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Checks that date is not a future date
     *
     * @param date the date to check
     * @throws ParseException if date is a future date
     */
    private static void validateNotFutureDate(Date date) throws ParseException {
        if (!date.hasNoFutureDate()) {
            throw new ParseException(Date.MESSAGE_FUTURE_DATE);
        }
    }

    /**
     * Parses a {@code String role} into a {@code Role}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code role} is invalid.
     */
    public static Role parseRole(String role) throws ParseException {
        requireNonNull(role);
        String trimmedRole = role.trim();
        assert trimmedRole != null : "trimmed role can never be null";
        if (!Role.isValidJobRole(trimmedRole)) {
            throw new ParseException(Role.MESSAGE_CONSTRAINTS);
        }
        return new Role(trimmedRole);
    }

    /**
     * Parses a {@code String status} into a {@code Status} or default status if not present.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @param status is the status to be parsed
     * @throws ParseException if the given {@code status} is invalid.
     */
    public static Status parseStatus(String status) throws ParseException {
        if (status == null || status.trim().isEmpty()) {
            return new Status(DEFAULT_STATUS);
        }
        String trimmedStatus = status.trim();
        if (!Status.isValidStatus(trimmedStatus)) {
            throw new ParseException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    /**
     * Parses reminder name and date if both present and valid.
     *
     * @param reminderName reminder name to parse
     * @param reminderDate reminder date to parse
     * @return Reminder object if both provided and valid, null if both absent
     * @throws ParseException if only reminder name or reminder date provided
     */
    public static Reminder parseReminder(String reminderName, String reminderDate) throws ParseException {
        if (isReminderInvalid(reminderName, reminderDate)) {
            return null;
        }

        checkBothFieldPresent(reminderName, reminderDate);
        String trimmedName = validateReminderName(reminderName);
        String trimmedDate = validateReminderDate(reminderDate);

        return new Reminder(trimmedName, trimmedDate);
    }

    /**
     * Checks that both reminder name and reminder date is not given/absent
     *
     * @param reminderName check is reminder name is not present
     * @param reminderDate check is reminder date is not present
     * @return true is both reminder name and reminder date not present
     */
    private static boolean isReminderInvalid(String reminderName, String reminderDate) {
        boolean hasName = reminderName != null && !reminderName.trim().isEmpty();
        boolean hasDate = reminderDate != null && !reminderDate.trim().isEmpty();
        return !hasName && !hasDate;
    }

    /**
     * Checks to throw exception if only one of the 2 fields are present
     *
     * @param reminderName reminder name
     * @param reminderDate reminder date
     * @throws ParseException if either field is empty
     */
    private static void checkBothFieldPresent(String reminderName, String reminderDate) throws ParseException {
        boolean hasName = reminderName != null && !reminderName.trim().isEmpty();
        boolean hasDate = reminderDate != null && !reminderDate.trim().isEmpty();

        if (!hasName) {
            throw new ParseException("Reminder name cannot be empty when reminder date is provided.");
        }
        if (!hasDate) {
            throw new ParseException("Reminder date cannot be empty when reminder name is provided.");
        }
    }

    /**
     * Validates that the reminder name is valid
     *
     * @param reminderName is the reminder name to check
     * @return the valid reminder name
     * @throws ParseException if reminder name is invalid
     */
    private static String validateReminderName(String reminderName) throws ParseException {
        String trimmedName = reminderName.trim();
        if (!Reminder.isValidReminder(trimmedName)) {
            throw new ParseException(Reminder.REMINDER_MESSAGE_CONSTRAINTS);
        }
        return trimmedName;
    }

    /**
     * Validates that the reminder date is valid
     *
     * @param reminderDate is the reminder date to check
     * @return the valid reminder date
     * @throws ParseException if reminder date is invalid
     */
    private static String validateReminderDate(String reminderDate) throws ParseException {
        String trimmedDate = reminderDate.trim();
        if (!Date.isValidDate(trimmedDate)) {
            throw new ParseException(Date.MESSAGE_CONSTRAINTS);
        }
        return trimmedDate;
    }
}
