package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Status;

public class StatusCommandTest {

    @Test
    public void constructor_validInput_success() {
        Status status = new Status("Applied");
        StatusCommand command = new StatusCommand("Alex Yeoh", "Software Engineer", status);

        assertEquals("Alex Yeoh", command.getName());
        assertEquals("Software Engineer", command.getRole());
        assertEquals(status, command.getStatus());
    }
}
