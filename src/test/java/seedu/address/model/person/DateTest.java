package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {


    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Email(null)); // (equivalence paritioning)
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        String invalidDate1 = ""; // empty date (equivalence paritioning)
        String invalidDate2 = "avs"; // letters not allowed (equivalence paritioning)
        String invalidDate3 = "2025"; // not YYYY-MM-DD (equivalence paritioning)
        String invalidDate4 = "2026-13-12"; // month between only 1 to 12 (equivalence paritioning)
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate1));
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate2));
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate3));
        assertThrows(IllegalArgumentException.class, () -> new Date(invalidDate4));
    }

    @Test
    public void isValidDate() {
        // null email
        assertThrows(NullPointerException.class, () -> Date.isValidDate(null)); // (equivalence paritioning)

        // blank email
        assertFalse(Date.isValidDate("")); // empty string (equivalence paritioning)
        assertFalse(Date.isValidDate(" ")); // spaces only (equivalence paritioning)

        assertFalse(Date.isValidDate("as")); // letters not allowed (equivalence paritioning)
        assertFalse(Date.isValidDate("2025-")); // not YYYY-MM-DD (equivalence paritioning)
        // month between//only 1 to 12 (equivalence paritioning)
        assertFalse(Date.isValidDate("2026-13-12"));
        assertFalse(Date.isValidDate("2026-12-1")); // day should be 2 digits (equivalence paritioning)
        assertFalse(Date.isValidDate("2026-12-")); // not YYYY-MM-DD (equivalence paritioning)
        assertFalse(Date.isValidDate("2026/12/12")); // wrong format (/) (equivalence paritioning)
        assertFalse(Date.isValidDate("202#$-12-12")); // symbols not allowed (equivalence paritioning)
        assertFalse(Date.isValidDate("2026-02-30")); // invalid date (equivalence paritioning)
        assertTrue(Date.isValidDate("2026-12-12")); // (equivalence paritioning)
        assertTrue(Date.isValidDate("2026-01-12")); // (boundary value analysis)
        assertTrue(Date.isValidDate("2026-12-12")); // (boundary value analysis)
        assertTrue(Date.isValidDate("2026-10-31")); // (boundary value analysis)
    }
}
