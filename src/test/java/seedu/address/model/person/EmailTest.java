package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class EmailTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidEmail_throwsIllegalArgumentException() {
        String invalidEmail = "";
        assertThrows(IllegalArgumentException.class, () -> new Email(invalidEmail));
    }

    @Test
    public void isValidEmail() {
        // null email
        assertThrows(NullPointerException.class, () -> Email.isValidEmail(null)); // (equivalence paritioning)

        // blank email
        assertFalse(Email.isValidEmail("")); // empty string (equivalence paritioning)
        assertFalse(Email.isValidEmail(" ")); // spaces only (equivalence paritioning)

        // missing parts
        assertFalse(Email.isValidEmail("@example.com")); // missing local part (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjackexample.com")); // missing '@' symbol (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@")); // missing domain name (equivalence paritioning)

        // invalid parts
        assertFalse(Email.isValidEmail("peterjack@-")); // invalid domain name

        // underscore in domain name (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@exam_ple.com"));

        // spaces in local part (equivalence paritioning)
        assertFalse(Email.isValidEmail("peter jack@example.com"));

        // spaces in domain name (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@exam ple.com"));

        // leading space (equivalence paritioning)
        assertFalse(Email.isValidEmail(" peterjack@example.com"));

        // trailing space (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@example.com "));

        // double '@' symbol (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@@example.com"));

        // '@' symbol in local part (equivalence paritioning)
        assertFalse(Email.isValidEmail("peter@jack@example.com"));

        // local part starts with a hyphen (equivalence paritioning)
        assertFalse(Email.isValidEmail("-peterjack@example.com"));

        // local part ends with a hyphen (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack-@example.com"));

        // local part has two consecutive periods (equivalence paritioning)
        assertFalse(Email.isValidEmail("peter..jack@example.com"));

        // '@' symbol in domain name (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@example@com"));

        // domain name starts with a period (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@.example.com"));

        // domain name ends with a period (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@example.com."));

        // domain name starts with a hyphen (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@-example.com"));

        // domain name ends with a hyphen (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@example.com-"));

        // top level domain has less than two chars (equivalence paritioning)
        assertFalse(Email.isValidEmail("peterjack@example.c"));

        // valid email
        // underscore in local part (equivalence paritioning)
        assertTrue(Email.isValidEmail("PeterJack_1190@example.com"));

        // period in local part (equivalence paritioning)
        assertTrue(Email.isValidEmail("PeterJack.1190@example.com"));

        // '+' symbol in local part (equivalence paritioning)
        assertTrue(Email.isValidEmail("PeterJack+1190@example.com"));

        // hyphen in local part (equivalence paritioning)
        assertTrue(Email.isValidEmail("PeterJack-1190@example.com"));

        // minimal (equivalence paritioning)
        assertTrue(Email.isValidEmail("a@bc"));

        // alphabets only (equivalence paritioning)
        assertTrue(Email.isValidEmail("test@localhost"));

        // numeric local part and domain name (equivalence paritioning)
        assertTrue(Email.isValidEmail("123@145"));

        // mixture of alphanumeric and special characters (equivalence paritioning)
        assertTrue(Email.isValidEmail("a1+be.d@example1.com"));

        // long domain name (equivalence paritioning)
        assertTrue(Email.isValidEmail("peter_jack@very-very-very-long-example.com"));

        // long local part (equivalence paritioning)
        assertTrue(Email.isValidEmail("if.you.dream.it_you.can.do.it@example.com"));

        // more than one period in domain (equivalence paritioning)
        assertTrue(Email.isValidEmail("e1234567@u.nus.edu"));
    }

    @Test
    public void equals() {
        Email email = new Email("valid@email");

        // same values -> returns true
        assertTrue(email.equals(new Email("valid@email")));

        // same object -> returns true
        assertTrue(email.equals(email));

        // null -> returns false
        assertFalse(email.equals(null));

        // different types -> returns false
        assertFalse(email.equals(5.0f));

        // different values -> returns false
        assertFalse(email.equals(new Email("other.valid@email")));
    }
}
