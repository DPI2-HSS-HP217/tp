package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BENSON_WITH_REMINDER_INTERVIEW;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Application;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code RemoveReminderCommand}.
 */
public class RemoveReminderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application personToRemoveReminder = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        RemoveReminderCommand removeReminderCommand = new RemoveReminderCommand(INDEX_SECOND_PERSON);

        Application bensonWithoutReminder = new PersonBuilder(BENSON).build();

        String expectedMessage = String.format(RemoveReminderCommand.MESSAGE_REMOVE_REMINDER_SUCCESS,
                Messages.format(bensonWithoutReminder)); // <-- use bensonWithoutReminder, not personToRemoveReminder

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(personToRemoveReminder, bensonWithoutReminder);

        assertCommandSuccess(removeReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validNormalRemoveReminderCommand_success() throws CommandException {
        Application removeReminderTarget = BENSON_WITH_REMINDER_INTERVIEW;
        RemoveReminderCommand removeReminderCommand
                = new RemoveReminderCommand(removeReminderTarget.getName(), removeReminderTarget.getRole());

        String expectedMessage = String.format(RemoveReminderCommand.MESSAGE_REMOVE_REMINDER_SUCCESS,
                Messages.format(BENSON)); // <-- use BENSON, not removeReminderTarget

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(removeReminderTarget, BENSON);

        assertCommandSuccess(removeReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemoveReminderCommand removeReminderCommand = new RemoveReminderCommand(outOfBoundIndex);

        assertCommandFailure(removeReminderCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemoveReminderCommand removeReminderCommand = new RemoveReminderCommand(outOfBoundIndex);

        assertCommandFailure(removeReminderCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_normalremoveReminderNoTarget_throwsCommandException() {
        RemoveReminderCommand removeReminderCommand = new RemoveReminderCommand(new Name("NonExistentName"), new Role("Engineer"));
        assertCommandFailure(removeReminderCommand, model, Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER);
    }

    @Test
    public void equals() {
        RemoveReminderCommand removeReminderFirstCommandIndex = new RemoveReminderCommand(INDEX_FIRST_PERSON);
        RemoveReminderCommand removeReminderSecondCommandIndex = new RemoveReminderCommand(INDEX_SECOND_PERSON);
        RemoveReminderCommand removeReminderFirstCommandApp = new RemoveReminderCommand(BOB.getName(), BOB.getRole());
        RemoveReminderCommand removeReminderSecondCommandApp = new RemoveReminderCommand(AMY.getName(), AMY.getRole());

        // same object -> returns true
        assertTrue(removeReminderFirstCommandIndex.equals(removeReminderFirstCommandIndex));

        // same values -> returns true
        RemoveReminderCommand removeReminderFirstCommandIndexCopy = new RemoveReminderCommand(INDEX_FIRST_PERSON);
        assertTrue(removeReminderFirstCommandIndex.equals(removeReminderFirstCommandIndexCopy));

        // different types -> returns false
        assertFalse(removeReminderFirstCommandIndex.equals(1));

        // null -> returns false
        assertFalse(removeReminderFirstCommandIndex.equals(null));

        // different person -> returns false
        assertFalse(removeReminderFirstCommandIndex.equals(removeReminderSecondCommandIndex));

        RemoveReminderCommand removeReminderFirstCommandAppCopy = new RemoveReminderCommand(BOB.getName(), BOB.getRole());
        assertTrue(removeReminderFirstCommandApp.equals(removeReminderFirstCommandApp));
        assertTrue(removeReminderFirstCommandApp.equals(removeReminderFirstCommandAppCopy));
        assertFalse(removeReminderFirstCommandApp.equals(1));
        assertFalse(removeReminderFirstCommandApp.equals(null));
        assertFalse(removeReminderFirstCommandApp.equals(removeReminderSecondCommandApp));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        RemoveReminderCommand removeReminderCommand = new RemoveReminderCommand(targetIndex);
        String expected = RemoveReminderCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, removeReminderCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
