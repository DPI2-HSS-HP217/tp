package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void isValidStatus() {

        // invalid
        //        assertFalse(Status.isValidStatus(""));
        //        assertFalse(Status.isValidStatus(" "));

        // valid
        assertTrue(Status.isValidStatus("Applied"));
        assertTrue(Status.isValidStatus("Interview"));
        assertTrue(Status.isValidStatus("pending"));
        assertTrue(Status.isValidStatus("Rejected"));
        assertTrue(Status.isValidStatus("Offered"));
        assertFalse(Status.isValidStatus("Custom Status"));
    }
}
