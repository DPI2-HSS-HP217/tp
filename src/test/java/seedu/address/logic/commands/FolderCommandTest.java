package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class FolderCommandTest {

    private Model model = new ModelManager();

    @Test
    public void equals() {
        FolderCommand folderA = new FolderCommand("Y1S2");
        FolderCommand folderB = new FolderCommand("Y2S1");
        FolderCommand folderC = new FolderCommand("Y1S2");

        assertTrue(folderA.equals(folderA));
        assertTrue(folderA.equals(folderC));
        assertFalse(folderA.equals(folderB));
        assertFalse(folderA.equals(null));
        assertFalse(folderA.equals(1));
    }

    @Test
    public void execute_folder_returnsCreatedMessage() {
        FolderCommand command = new FolderCommand("Y1S2");
        CommandResult result = command.execute(model);
        assertEquals("Created and switched to folder: Y1S2", result.getFeedbackToUser());
    }

    @Test
    public void execute_folder_signalsFolderNameAndCreateNew() {
        FolderCommand command = new FolderCommand("Y1S2");
        CommandResult result = command.execute(model);
        assertEquals("Y1S2", result.getFolderName());
        assertTrue(result.isCreateNew());
    }

}
