package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StatusCommand;
import seedu.address.model.person.Status;

public class StatusCommandParserTest {

    private StatusCommandParser parser = new StatusCommandParser();

    @Test
    public void parse_validArgs_returnsStatusCommand() throws Exception {

        StatusCommand command = parser.parse(" n/Alex Yeoh s/Interviewing");

        assertEquals("Alex Yeoh", command.getName());
        assertEquals(new Status("Interviewing"), command.getStatus());
    }
}
