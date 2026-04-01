package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;


public class ReminderTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Reminder(null, null));
    }

    @Test
    public void constructor_invalidReminder_throwsIllegalArgumentException() {
        String validReminderName = "test";
        String validReminderDate = "2026-12-12";

        String invalidReminderName1 = " ";
        String invalidReminderName2 = "";
        String invalidReminderDate1 = "lol";
        String invalidReminderDate2 = "2026-32-01";
        String invalidReminderDate3 = "2026-32-1";

        assertThrows(IllegalArgumentException.class, () -> new Reminder(validReminderName, invalidReminderDate1));
        assertThrows(IllegalArgumentException.class, () -> new Reminder(validReminderName, invalidReminderDate2));
        assertThrows(IllegalArgumentException.class, () -> new Reminder(validReminderName, invalidReminderDate3));

        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidReminderName1, validReminderDate));
        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidReminderName2, validReminderDate));

        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidReminderName2, invalidReminderDate1));
        assertThrows(IllegalArgumentException.class, () -> new Reminder(invalidReminderName2, invalidReminderDate2));
    }


    @Test
    public void equals() {
        String validReminderName = "test";
        String validReminderDate = "2026-12-12";
        Reminder reminder = new Reminder(validReminderName, validReminderDate);

        // same values -> returns False
        assertFalse(reminder.equals(new Reminder("test", "2026-12-12")));

        // same object -> returns true
        assertTrue(reminder.equals(reminder));

        // null -> returns false
        assertFalse(reminder.equals(null));

        // different types -> returns false
        assertFalse(reminder.equals(5.0f));

    }

    @Test
    public void isByDate_withinRange_success() {
        //Reminder due today
        String validReminderName = "test";
        String firstValidReminderDate = LocalDate.now().toString();
        Reminder firstReminder = new Reminder(validReminderName, firstValidReminderDate);
        //other date equals reminder date
        assertTrue(firstReminder.isByDate(new Date(LocalDate.now())));
        //other date ahead of reminder date
        assertTrue(firstReminder.isByDate(new Date(LocalDate.now().plusDays(1))));

        //Reminder due in future
        String secondValidReminderDate = LocalDate.now().plusDays(2).toString();
        Reminder secondReminder = new Reminder(validReminderName, secondValidReminderDate);
        //other date equals reminder date
        assertTrue(secondReminder.isByDate(new Date(LocalDate.now().plusDays(2))));
        //other date ahead of reminder date
        assertTrue(secondReminder.isByDate(new Date(LocalDate.now().plusDays(3))));
    }

    @Test
    public void isByDate_outsideRange_failure() {
        //Reminder overdue
        String validReminderName = "test";
        String firstValidReminderDate = LocalDate.now().minusDays(1).toString();
        Reminder firstReminder = new Reminder(validReminderName, firstValidReminderDate);
        //other date equals reminder date
        assertFalse(firstReminder.isByDate(new Date(LocalDate.now())));
        //other date ahead of reminder date
        assertFalse(firstReminder.isByDate(new Date(LocalDate.now().plusDays(1))));

        //Reminder due in future
        String secondValidReminderDate = LocalDate.now().plusDays(2).toString();
        Reminder secondReminder = new Reminder(validReminderName, secondValidReminderDate);
        //other date behind reminder date
        assertFalse(secondReminder.isByDate(new Date(LocalDate.now().plusDays(1))));
    }
}

