package seedu.address.storage;

import java.util.ArrayList;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
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
 * Jackson-friendly version of {@link Application}.
 */
class JsonAdaptedApplication {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    public static final String NONESTRING = "";

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String status;
    private final String role;
    private final String date;
    private final String reminderEvent;
    private final String reminderDate;
    private Boolean hasReminder = false;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     * Overloaded version of the constructor to support reminder and not require much refactoring.
     */
    @JsonCreator
    public JsonAdaptedApplication(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                                  @JsonProperty("email") String email, @JsonProperty("address") String address,
                                  @JsonProperty("tags") List<JsonAdaptedTag> tags,
                                  @JsonProperty("date") String date,
                                  @JsonProperty("role") String role,
                                  @JsonProperty("status") String status,
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

    public JsonAdaptedApplication(Application source) {
        name = source.getName().fullName;
        role = source.getRole().value;
        phone = source.getPhone() != null ? source.getPhone().value : NONESTRING;
        email = source.getEmail() != null ? source.getEmail().value : NONESTRING;
        address = source.getAddress() != null ? source.getAddress().value : NONESTRING;
        date = source.getDate() != null ? source.getDate().value : NONESTRING;
        status = source.getStatus() != null ? source.getStatus().value : NONESTRING;

        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));

        hasReminder = source.hasReminder();
        reminderEvent = hasReminder ? source.getReminder().getReminderName() : null;
        reminderDate = hasReminder ? source.getReminder().getReminderDate().value : null;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Application toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        checkMandatoryFields();
        final Name modelName = new Name(name);
        final Role modelRole = new Role(role);

        final Phone modelPhone = parseOptional(phone, Phone::isValidPhone, Phone.MESSAGE_CONSTRAINTS, Phone::new);
        final Email modelEmail = parseOptional(email, Email::isValidEmail, Email.MESSAGE_CONSTRAINTS, Email::new);
        final Date modelDate = parseOptional(date, Date::isValidDate, Date.MESSAGE_CONSTRAINTS, Date::new);
        final Address modelAddress = parseOptional(address, Address::isValidAddress,
                    Address.MESSAGE_CONSTRAINTS, Address::new);
        final Status modelStatus = parseOptional(status, Status::isValidStatus,
                    Status.MESSAGE_CONSTRAINTS, Status::new);
        final Set<Tag> modelTags = new HashSet<>(personTags);
        final Reminder modelReminder = parseOptionalReminder();

        return new Application(modelName, modelPhone, modelEmail, modelAddress, modelTags,
                modelDate, modelRole, modelStatus, modelReminder);
    }

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

    private Reminder parseOptionalReminder() throws IllegalValueException {
        if (hasReminder) {
            if (reminderEvent == null || reminderDate == null) {
                throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                        Reminder.class.getSimpleName()));
            }
            if (!Reminder.isValidReminder(reminderEvent)) {
                throw new IllegalValueException(Reminder.MESSAGE_CONSTRAINTS);
            }
            if (!Date.isValidDate(reminderDate)) {
                throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
            }
            return new Reminder(reminderEvent, reminderDate);
        }
        return null;
    }

    private <T> T parseOptional(
            String value,
            Predicate<String> validator,
            String errorMessage,
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
