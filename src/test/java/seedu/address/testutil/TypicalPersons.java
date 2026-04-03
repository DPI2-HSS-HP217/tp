package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STATUS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.person.Application;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Application ALICE = new PersonBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withDate("2024-01-15")
            .withRole("Software Engineer")
            .withStatus("")
            .withTags("friends")
            .build();

    public static final Application BENSON = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withDate("2024-02-20")
            .withRole("Project Manager")
            .withStatus("interview")
            .withTags("owesMoney", "friends")
            .build();

    public static final Application BENSON_WITH_REMINDER_INTERVIEW = new PersonBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withDate("2024-02-20")
            .withRole("Project Manager")
            .withStatus("interview")
            .withTags("owesMoney", "friends")
            .withReminder("second interview", "2026-12-12")
            .buildWithReminder();

    public static final Application HENSON_WITH_REMINDER_INTERVIEW_TODAY = new PersonBuilder().withName("Henson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com")
            .withPhone("98765432")
            .withDate("2024-02-20")
            .withRole("Project Manager")
            .withStatus("interview")
            .withTags("owesMoney", "friends")
            .withReminder("second interview", LocalDate.now().toString())
            .buildWithReminder();

    public static final Application CARL = new PersonBuilder().withName("Carl Kurz")
            .withPhone("95352563")
            .withEmail("heinz@example.com")
            .withAddress("wall street")
            .withDate("2024-03-10")
            .withRole("Data Scientist")
            .withStatus("pending")
            .build();

    public static final Application DANIEL = new PersonBuilder().withName("Daniel Meier")
            .withPhone("87652533")
            .withEmail("cornelia@example.com")
            .withAddress("10th street")
            .withDate("2024-01-05")
            .withRole("UX Designer")
            .withStatus("rejected")
            .withTags("friends")
            .build();

    public static final Application ELLE = new PersonBuilder().withName("Elle Meyer")
            .withPhone("9482224")
            .withEmail("werner@example.com")
            .withAddress("michegan ave")
            .withDate("2024-04-12")
            .withRole("Product Manager")
            .withStatus("offered")
            .build();

    public static final Application FIONA = new PersonBuilder().withName("Fiona Kunz")
            .withPhone("9482427")
            .withEmail("lydia@example.com")
            .withAddress("little tokyo")
            .withDate("2024-03-22")
            .withRole("DevOps Engineer")
            .withStatus("applied")
            .build();

    public static final Application GEORGE = new PersonBuilder().withName("George Best")
            .withPhone("9482442")
            .withEmail("anna@example.com")
            .withAddress("4th street")
            .withDate("2024-02-28")
            .withRole("QA Engineer")
            .withStatus("interview")
            .build();

    // Manually added
    public static final Application HOON = new PersonBuilder().withName("Hoon Meier")
            .withPhone("8482424")
            .withEmail("stefan@example.com")
            .withAddress("little india")
            .withDate("2024-05-01")
            .withRole("Frontend Developer")
            .withStatus("interview")
            .build();

    public static final Application IDA = new PersonBuilder().withName("Ida Mueller")
            .withPhone("8482131")
            .withEmail("hans@example.com")
            .withAddress("chicago ave")
            .withDate("2024-05-15")
            .withRole("Backend Developer")
            .withStatus("applied")
            .build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Application AMY = new PersonBuilder()
            .withName(VALID_NAME_AMY)
            .withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY)
            .withAddress(VALID_ADDRESS_AMY)
            .withDate(VALID_DATE_AMY)
            .withRole(VALID_ROLE_AMY)
            .withStatus(VALID_STATUS_AMY)
            .withTags(VALID_TAG_FRIEND)
            .build();

    public static final Application BOB = new PersonBuilder()
            .withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB)
            .withAddress(VALID_ADDRESS_BOB)
            .withDate(VALID_DATE_BOB)
            .withRole(VALID_ROLE_BOB)
            .withStatus(VALID_STATUS_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Application person : getTypicalPersons()) {
            ab.addPerson(person);
        }
        return ab;
    }

    public static List<Application> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON_WITH_REMINDER_INTERVIEW, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
