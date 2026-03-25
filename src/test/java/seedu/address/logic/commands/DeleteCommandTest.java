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
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Application;
import seedu.address.model.person.Name;
import seedu.address.model.person.Role;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Application personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Application personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(personToDelete));

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        showNoPerson(expectedModel);
        System.out.println(model);
        System.out.println(expectedModel);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_DISPLAYED_INDEX);
    }

    // Non indexed Deletion Testcases
    @Test
    public void execute_validNormalDeleteCommand_success() throws CommandException {
        Application deleteTarget = BOB;
        model.addPerson(BOB);
        DeleteCommand deleteCommand = new DeleteCommand(deleteTarget.getName(), deleteTarget.getRole());

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_APPLICATION_SUCCESS,
                Messages.format(deleteTarget));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(deleteTarget);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_normalDeleteNoTarget_throwsCommandException() {
        DeleteCommand deleteCommand = new DeleteCommand(new Name("NonExistentName"), new Role("Engineer"));
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_APPLICATION_IDENTIFIER);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommandIndex = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommandIndex = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand deleteFirstCommandApp = new DeleteCommand(BOB.getName(), BOB.getRole());
        DeleteCommand deleteSecondCommandApp = new DeleteCommand(AMY.getName(), AMY.getRole());

        // same object -> returns true
        assertTrue(deleteFirstCommandIndex.equals(deleteFirstCommandIndex));

        // same values -> returns true
        DeleteCommand deleteFirstCommandIndexCopy = new DeleteCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstCommandIndex.equals(deleteFirstCommandIndexCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommandIndex.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommandIndex.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommandIndex.equals(deleteSecondCommandIndex));

        DeleteCommand deleteFirstCommandAppCopy = new DeleteCommand(BOB.getName(), BOB.getRole());
        assertTrue(deleteFirstCommandApp.equals(deleteFirstCommandApp));
        assertTrue(deleteFirstCommandApp.equals(deleteFirstCommandAppCopy));
        assertFalse(deleteFirstCommandApp.equals(1));
        assertFalse(deleteFirstCommandApp.equals(null));
        assertFalse(deleteFirstCommandApp.equals(deleteSecondCommandApp));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName() + "{targetIndex=" + targetIndex + "}";
        assertEquals(expected, deleteCommand.toString());
    }

    /**
     * Updates {@code model}'s filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assertTrue(model.getFilteredPersonList().isEmpty());
    }
}
