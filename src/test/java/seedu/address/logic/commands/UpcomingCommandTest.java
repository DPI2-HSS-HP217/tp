package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.HENSON_WITH_REMINDER_INTERVIEW_TODAY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.time.LocalDate;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Date;
import seedu.address.model.person.ReminderWithinOffsetPredicate;

/**
 * Contains integration tests for {@code UpcomingCommand}.
 */
public class UpcomingCommandTest {
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        int daysOffset = 2;
        Date firstSampleDate = new Date(LocalDate.of(2026, 4, 2));
        Date secondSampleDate = new Date(LocalDate.of(2026, 4, 4));
        ReminderWithinOffsetPredicate firstPredicate = new ReminderWithinOffsetPredicate(firstSampleDate);
        ReminderWithinOffsetPredicate secondPredicate = new ReminderWithinOffsetPredicate(secondSampleDate);

        UpcomingCommand upcomingFirstCommand = new UpcomingCommand(firstPredicate, daysOffset);
        UpcomingCommand upcomingSecondCommand = new UpcomingCommand(secondPredicate, daysOffset);

        assertTrue(upcomingFirstCommand.equals(upcomingFirstCommand));
        assertTrue(upcomingFirstCommand.equals(new UpcomingCommand(firstPredicate, daysOffset)));
        assertFalse(upcomingFirstCommand.equals(1));
        assertFalse(upcomingFirstCommand.equals(null));
        assertFalse(upcomingFirstCommand.equals(upcomingSecondCommand));
    }

    @Test
    public void execute_singleMatchFound() {
        expectedModel.addPerson(HENSON_WITH_REMINDER_INTERVIEW_TODAY);
        model.addPerson(HENSON_WITH_REMINDER_INTERVIEW_TODAY);
        int daysOffset = 4;
        Date firstSampleDate = new Date(LocalDate.now().plusDays(daysOffset));
        ReminderWithinOffsetPredicate predicate = new ReminderWithinOffsetPredicate(firstSampleDate);
        UpcomingCommand command = new UpcomingCommand(predicate, daysOffset);
        expectedModel.setReminderOffset(daysOffset);


        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model,
                String.format(UpcomingCommand.MESSAGE_MATCHES_FOUND, 1, daysOffset), expectedModel);
        assertEquals(Collections.singletonList(HENSON_WITH_REMINDER_INTERVIEW_TODAY), model.getFilteredPersonList());
        expectedModel.deletePerson(HENSON_WITH_REMINDER_INTERVIEW_TODAY);
        model.deletePerson(HENSON_WITH_REMINDER_INTERVIEW_TODAY);
    }

    @Test
    public void execute_noMatchFound() {
        int daysOffset = 4;
        Date firstSampleDate = new Date(LocalDate.of(2024, 2, 18));
        ReminderWithinOffsetPredicate predicate = new ReminderWithinOffsetPredicate(firstSampleDate);
        UpcomingCommand command = new UpcomingCommand(predicate, daysOffset);
        expectedModel.setReminderOffset(4);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, String.format(UpcomingCommand.MESSAGE_NO_MATCHES, 4), expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredPersonList());
    }

    @Test
    public void toStringMethod() {
        int daysOffset = 4;
        Date firstSampleDate = new Date(LocalDate.of(2024, 2, 18));
        ReminderWithinOffsetPredicate predicate = new ReminderWithinOffsetPredicate(firstSampleDate);
        UpcomingCommand command = new UpcomingCommand(predicate, daysOffset);
        String expected = UpcomingCommand.class.getCanonicalName() + "{days offset=" + daysOffset
                + ", predicate=" + predicate + "}";
        assertEquals(expected, command.toString());
    }

}
