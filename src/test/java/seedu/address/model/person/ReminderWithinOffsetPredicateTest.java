package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class ReminderWithinOffsetPredicateTest {

    @Test
    public void equals() {
        LocalDate sampleDate = LocalDate.of(2026, 4, 1);
        Date firstPredicateDate = new Date(sampleDate);
        Date secondPredicateDate = new Date(sampleDate.plusDays(4));

        ReminderWithinOffsetPredicate firstPredicate = new ReminderWithinOffsetPredicate(firstPredicateDate);
        ReminderWithinOffsetPredicate secondPredicate = new ReminderWithinOffsetPredicate(secondPredicateDate);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ReminderWithinOffsetPredicate firstPredicateCopy = new ReminderWithinOffsetPredicate(firstPredicateDate);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_dateByDate_returnsTrue() {
        LocalDate sampleDate = LocalDate.now().plusDays(2);
        // On the same date
        ReminderWithinOffsetPredicate predicate = new ReminderWithinOffsetPredicate(new Date(sampleDate));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob")
                .withReminder("test", sampleDate.toString()).buildWithReminder()));

        // Before the date
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob")
                .withReminder("test", sampleDate.minusDays(1).toString()).buildWithReminder()));
    }

    @Test
    public void test_dateNotByDate_returnsFalse() {

        // After the same date
        LocalDate sampleDate = LocalDate.now();
        ReminderWithinOffsetPredicate predicate = new ReminderWithinOffsetPredicate(new Date(sampleDate));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob")
                .withReminder("test", sampleDate.plusDays(2).toString()).buildWithReminder()));
        // Date is overdue
        LocalDate sampleDate2 = LocalDate.now().minusDays(1);
        ReminderWithinOffsetPredicate predicate2 = new ReminderWithinOffsetPredicate(new Date(sampleDate2));
        assertFalse(predicate2.test(new PersonBuilder().withName("Alice Bob")
                .withReminder("test", sampleDate2.toString()).buildWithReminder()));
    }

    @Test
    public void toStringMethod() {
        LocalDate sampleDate = LocalDate.of(2026, 4, 1);
        Date actualDate = new Date(sampleDate);
        ReminderWithinOffsetPredicate predicate = new ReminderWithinOffsetPredicate(actualDate);

        String expected = ReminderWithinOffsetPredicate.class.getCanonicalName() + "{date=" + actualDate + "}";
        assertEquals(expected, predicate.toString());
    }
}
