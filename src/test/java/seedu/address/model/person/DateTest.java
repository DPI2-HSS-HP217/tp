package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate1 = "";
        String invalidDate2 = "avs";
        String invalidDate3 = "2025";
        String invalidDate4 = "2026-13-12";
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate1));
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate2));
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate3));
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate4));
    }

    @Test
    public void isValidDate() {
        // null email
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null));

        // blank email
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only

        assertFalse(Date.isValidDate("as"));
        assertFalse(Date.isValidDate("2025-"));
        assertFalse(Date.isValidDate("2026-13-12"));
        assertFalse(Date.isValidDate("2026-12-1"));
        assertFalse(Date.isValidDate("2026-12-"));
        assertTrue(Date.isValidDate("2026-12-12"));
    }

    //    @Test
    //    public void equals() {
    //        Date date = new Date("2026-01-01");
    //
    //        // same values -> returns true
    //        assertTrue(date.equals(new Date("2026-12-12")));
    //
    //        // same object -> returns true
    //        assertTrue(date.equals(date));
    //
    //        // null -> returns false
    //        assertFalse(date.equals(null));
    //
    //        // different types -> returns false
    //        assertFalse(date.equals(5.0f));
    //
    //        // different values -> returns false
    //        assertFalse(date.equals(new Email("other.valid@email")));
    //    }

}
