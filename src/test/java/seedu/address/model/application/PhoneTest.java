package seedu.address.model.application;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PhoneTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Phone(null));
    }

    @Test
    public void constructor_validPhone_throwsIllegalArgumentException() {
        assertTrue(Phone.isValidPhone("")); // valid
    }

    @Test
    public void isValidPhone() {
        // null phone number
        assertThrows(NullPointerException.class, () -> Phone.isValidPhone(null));

        // invalid phone numbers
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers (boundary value analysis)
        assertFalse(Phone.isValidPhone("phone")); // non-numeric (equivalence paritioning)
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits (equivalence paritioning)
        assertFalse(Phone.isValidPhone("9312  1534")); // consecutive space within digits (equivalence paritioning)
        assertFalse(Phone.isValidPhone("12")); // exactly 2 number (boundary value analysis)
        assertFalse(Phone.isValidPhone("1")); // exactly 1 number (boundary value analysis)
        assertFalse(Phone.isValidPhone("984$%890")); // number with invalid symbols (equivalence paritioning)
        assertFalse(Phone.isValidPhone("+(123)-7890123456 7890123 45 67 2")); // 33 character (boundary value analysis)


        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers (boundary value analysis)
        assertTrue(Phone.isValidPhone("9114")); // 1 more than the minimum numbers (boundary value analysis)
        assertTrue(Phone.isValidPhone("93121534")); // (equivalence paritioning)
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers (equivalence paritioning)
        assertTrue(Phone.isValidPhone("9312 1534")); // 1 space within digits (equivalence paritioning)
        assertTrue(Phone.isValidPhone("+65111111")); // + symbol allowed (equivalence paritioning)
        assertTrue(Phone.isValidPhone("+1 (123) 456-7890")); // hyphens, parentheses, plus and spaces allowed
         //exactly 32 character (boundary value analysis)
        assertTrue(Phone.isValidPhone("1234567890123456 7890123 45 67 2"));
    }

    @Test
    public void equals() {
        Phone phone = new Phone("999");

        // same values -> returns true
        assertTrue(phone.equals(new Phone("999")));

        // same object -> returns true
        assertTrue(phone.equals(phone));

        // null -> returns false
        assertFalse(phone.equals(null));

        // different types -> returns false
        assertFalse(phone.equals(5.0f));

        // different values -> returns false
        assertFalse(phone.equals(new Phone("995")));
    }
}
