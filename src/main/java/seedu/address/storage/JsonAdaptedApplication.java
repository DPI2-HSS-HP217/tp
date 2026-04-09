package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Application}.
 */
class JsonAdaptedApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Application's %s field is missing!";
    private static final String NONE_STRING = "";
    private static final Logger logger = Logger.getLogger(JsonAdaptedApplication.class.getName());

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String status;
    private final String role;
    private final String date;
    private final String reminderEvent;
    private final String reminderDate;
    private boolean hasReminder = false;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedApplication} with the given application details.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                    @JsonProperty("email") String email, @JsonProperty("address") String address,
                                    @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("date") String date,
                                    @JsonProperty("role") String role, @JsonProperty("status") String status,
                                    @JsonProperty("reminderEvent") String reminderEvent,
                                    @JsonProperty("reminderDate") String reminderDate) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.date = date;
        this.role = role;
        this.status = status;
        this.reminderEvent = reminderEvent;
        this.reminderDate = reminderDate;
    }

    /**
     * Constructs a {@code JsonAdaptedApplication} with the input Application.
     */
    public JsonAdaptedApplication(Application source) {
        name = source.getName().fullName;
        role = source.getRole().value;
        phone = source.getPhone() != null ? source.getPhone().value : NONE_STRING;
        email = source.getEmail() != null ? source.getEmail().value : NONE_STRING;
        address = source.getAddress() != null ? source.getAddress().value : NONE_STRING;
        date = source.getDate() != null ? source.getDate().value : NONE_STRING;
        status = source.getStatus() != null ? source.getStatus().value : NONE_STRING;

        tags.addAll(source.getTags().stream().map(JsonAdaptedTag::new).collect(Collectors.toList()));

        hasReminder = source.hasReminder();
        reminderEvent = hasReminder ? source.getReminder().getReminderName() : null;
        reminderDate = hasReminder ? source.getReminder().getReminderDate().value : null;
    }

    /**
     * Converts this JSON adapted Application object into the model's {@code Application} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted Application.
     */
    public Application toModelType() throws IllegalValueException {
        final List<Tag> applicationTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            applicationTags.add(tag.toModelType());
        }
        checkMandatoryFields();
        final Name modelName = new Name(name);
        final Role modelRole = new Role(role);

        final Phone modelPhone = parseOptional(phone, Phone::isValidPhone, Phone.MESSAGE_CONSTRAINTS, Phone::new);
        final Email modelEmail = parseOptional(email, Email::isValidEmail, Email.MESSAGE_CONSTRAINTS, Email::new);
        final Date modelDate = parseOptionalDate(date);
        final Address modelAddress = parseOptional(address, Address::isValidAddress, Address.MESSAGE_CONSTRAINTS,
                                        Address::new);
        final Status modelStatus = parseOptionalStatus(status);
        final Set<Tag> modelTags = new HashSet<>(applicationTags);
        final Reminder modelReminder = parseOptionalReminder();

        return new Application(modelName, modelPhone, modelEmail, modelAddress, modelTags, modelDate, modelRole,
                                        modelStatus, modelReminder);
    }

    /**
     * Checks for valid and present Application Name and Role fields.
     *
     * @throws IllegalValueException If Invalid or Missing Application Name and Role fields.
     */
    private void checkMandatoryFields() throws IllegalValueException {
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }
        if (!Role.isValidJobRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
    }

    /**
     * Parses the reminder if present.
     *
     * @return Reminder object, or null if no reminder exists.
     * @throws IllegalValueException If reminder fields are invalid or missing.
     */
    private Reminder parseOptionalReminder() throws IllegalValueException {
        logger.info("JsonAdaptedApplication: Parsing reminder");
        if (hasReminder) {
            if (reminderEvent == null || reminderDate == null) {
                logger.info("Should never reach here, check Code!");
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                                                Reminder.class.getSimpleName()));
            }
            if (!Reminder.isValidReminder(reminderEvent)) {
                logger.warning("Invalid reminder: " + reminderEvent);
                throw new IllegalValueException(Reminder.REMINDER_MESSAGE_CONSTRAINTS);
            }
            if (!Date.isValidDate(reminderDate)) {
                logger.warning("Invalid reminder: " + reminderDate);
                throw new IllegalValueException(Reminder.DATE_MESSAGE_CONSTRAINTS);
            }
            return new Reminder(reminderEvent, reminderDate);
        }
        if (reminderEvent == null ^ reminderDate == null) {
            logger.info("Should never reach here, check Code!");
            if (reminderEvent == null) {
                throw new IllegalValueException(Reminder.REMINDER_MESSAGE_CONSTRAINTS);
            }
            throw new IllegalValueException(Reminder.DATE_MESSAGE_CONSTRAINTS);
        }
        logger.info("No reminder");
        return null;
    }

    /**
     * Parses the date if present
     *
     * @param dateString date string to check and parse
     * @return Date object, or null if date does not exist.
     * @throws IllegalValueException if invalid date or if future date
     */
    private Date parseOptionalDate(String dateString) throws IllegalValueException {
        if (dateString == null || dateString.isEmpty()) {
            logger.info("no date");
            return null;
        }
        if (!Date.isValidDate(dateString)) {
            logger.warning("Invalid date: " + dateString);
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        Date date = new Date(dateString);
        if (!date.hasNoFutureDate()) {
            logger.warning("Future date: " + dateString);
            throw new IllegalValueException(Date.MESSAGE_FUTURE_DATE);
        }
        return date;
    }

    /**
     * Parses status if present else default is "Interested".
     *
     * @param status status to check and parse
     * @return Status object
     * @IllegalValueException if invalid status
     */
    private Status parseOptionalStatus(String status) throws IllegalValueException {
        if (status == null || status.isEmpty()) {
            logger.info("default status: Interested");
            return new Status("");
        }
        if (!Status.isValidStatus(status)) {
            logger.warning("Invalid status: " + status);
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(status);
    }

    /**
     * Parses an optional field.
     *
     * @param value Raw string value.
     * @param validator Function to validate the value.
     * @param errorMessage Error message if validation fails.
     * @param constructor Function to construct the object.
     * @param <T> Type of the parsed object.
     * @return Parsed object, or null if value is empty.
     * @throws IllegalValueException If invalid value is passed.
     */
    private <T> T parseOptional(String value, Predicate<String> validator, String errorMessage,
                                    Function<String, T> constructor) throws IllegalValueException {
        if (value == null || value.isEmpty()) {
            return null;
        }
        if (!validator.test(value)) {
            throw new IllegalValueException(errorMessage);
        }
        return constructor.apply(value);
    }
}
