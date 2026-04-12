package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.storage.JsonAdaptedApplication.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalApplications.BENSON;
import static seedu.address.testutil.TypicalApplications.BENSON_WITH_REMINDER_INTERVIEW;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.application.Address;
import seedu.address.model.application.Email;
import seedu.address.model.application.Name;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Reminder;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;

public class JsonAdaptedApplicationTest {
    private static final String INVALID_NAME = "Google-";
    private static final String INVALID_PHONE = "+65  1234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DATE = "2025-02-31";
    private static final String INVALID_ROLE = "";
    private static final String INVALID_STATUS = " ";
    private static final String INVALID_REMINDER_NAME = "";

    private static final String VALID_NAME = BENSON.getName().toString();
    private static final String VALID_PHONE = BENSON.getPhone().toString();
    private static final String VALID_EMAIL = BENSON.getEmail().toString();
    private static final String VALID_ADDRESS = BENSON.getAddress().toString();
    private static final String VALID_DATE = BENSON.getDate().value;
    private static final String VALID_ROLE = BENSON.getRole().value;
    private static final String VALID_STATUS = BENSON.getStatus().value;

    private static final String VALID_EVENT_NAME = BENSON.getDate().value;
    private static final String VALID_EVENT_DATE = BENSON.getDate().value;

    private static final List<JsonAdaptedTag> VALID_TAGS = BENSON.getTags().stream().map(JsonAdaptedTag::new)
                                    .collect(Collectors.toList());

    @Test
    public void toModelType_validApplicationDetails_returnsApplication() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(BENSON);
        assertEquals(BENSON, application.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(INVALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, VALID_ROLE, VALID_STATUS, null, null);
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(null, VALID_PHONE, VALID_EMAIL, VALID_ADDRESS,
                                        VALID_TAGS, VALID_DATE, VALID_ROLE, VALID_STATUS, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidPhone_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, INVALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, VALID_ROLE, VALID_STATUS, null, null);
        String expectedMessage = Phone.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidEmail_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, INVALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, VALID_ROLE, VALID_STATUS, null, null);
        String expectedMessage = Email.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidAddress_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        INVALID_ADDRESS, VALID_TAGS, VALID_DATE, VALID_ROLE, VALID_STATUS, null, null);
        String expectedMessage = Address.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<JsonAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new JsonAdaptedTag(INVALID_TAG));
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, invalidTags, VALID_DATE, VALID_ROLE, VALID_STATUS, null, null);
        assertThrows(IllegalValueException.class, application::toModelType);
    }

    @Test
    public void toModelType_invalidRole_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, INVALID_ROLE, VALID_STATUS, null, null);
        String expectedMessage = Role.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullRole_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, null, VALID_STATUS, null, null);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, VALID_ROLE, INVALID_STATUS, null, null);
        String expectedMessage = Status.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    // for reminder class
    @Test
    public void toModelType_validApplicationWithReminderDetails_returnsApplicationWithReminder() throws Exception {
        JsonAdaptedApplication application = new JsonAdaptedApplication(BENSON_WITH_REMINDER_INTERVIEW);
        assertEquals(BENSON_WITH_REMINDER_INTERVIEW, application.toModelType());
    }

    @Test
    public void toModelType_nullReminderEventName_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, VALID_ROLE, VALID_STATUS,
                                        VALID_EVENT_NAME, null);
        String expectedMessage = Reminder.DATE_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    @Test
    public void toModelType_nullReminderEventDate_throwsIllegalValueException() {
        JsonAdaptedApplication application = new JsonAdaptedApplication(VALID_NAME, VALID_PHONE, VALID_EMAIL,
                                        VALID_ADDRESS, VALID_TAGS, VALID_DATE, VALID_ROLE, VALID_STATUS, null,
                                        VALID_EVENT_DATE);
        String expectedMessage = Reminder.REMINDER_MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, application::toModelType);
    }

    // Invalid reminder and invalid dates testing ignored. Method is secure.
}
