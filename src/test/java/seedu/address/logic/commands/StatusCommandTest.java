package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Status;

public class StatusCommandTest {

    @Test
    public void constructor_validInput_success() {
        Status status = new Status("Applied");
        StatusCommand command = new StatusCommand("Alex Yeoh", status);

        assertEquals("Alex Yeoh", command.getName());
        assertEquals(status, command.getStatus());
    }
}
