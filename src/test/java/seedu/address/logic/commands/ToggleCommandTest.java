package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class ToggleCommandTest {

    private Model model = new ModelManager();

    @Test
    public void equals() {
        ToggleCommand toggleA = new ToggleCommand("Y1S2");
        ToggleCommand toggleB = new ToggleCommand("Y2S1");
        ToggleCommand toggleC = new ToggleCommand("Y1S2");

        assertTrue(toggleA.equals(toggleA));
        assertTrue(toggleA.equals(toggleC));
        assertFalse(toggleA.equals(toggleB));
        assertFalse(toggleA.equals(null));
        assertFalse(toggleA.equals(1));
    }

    @Test
    public void execute_toggle_returnsSwitchedMessage() {
        ToggleCommand command = new ToggleCommand("Y1S2");
        CommandResult result = command.execute(model);
        assertEquals("Switched to folder: Y1S2", result.getFeedbackToUser());
    }

    @Test
    public void execute_toggle_signalsFolderNameAndNotCreateNew() {
        ToggleCommand command = new ToggleCommand("Y1S2");
        CommandResult result = command.execute(model);
        assertEquals("Y1S2", result.getFolderName());
        assertFalse(result.isCreateNew());
    }

}
