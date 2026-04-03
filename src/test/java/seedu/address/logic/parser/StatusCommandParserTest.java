package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STATUS_DESC_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.StatusCommand;
import seedu.address.model.person.Status;

public class StatusCommandParserTest {

    private StatusCommandParser parser = new StatusCommandParser();

    @Test
    public void parse_validArgs_returnsStatusCommand() throws Exception {
        StatusCommand command = parser.parse(" n/Alex Yeoh r/Data Analyst s/Interview");

        assertEquals("Alex Yeoh", command.getName());
        assertEquals("Data Analyst", command.getRole());
        assertEquals(new Status("Interview"), command.getStatus());
    }

    @Test
    public void parse_randomPrefixOrder_success() {
        StatusCommand expectedCommand = new StatusCommand("Amy Bee", "Software Engineer", new Status("pending"));

        assertParseSuccess(parser, STATUS_DESC_AMY + NAME_DESC_AMY + ROLE_DESC_AMY, expectedCommand);
        assertParseSuccess(parser, ROLE_DESC_AMY + STATUS_DESC_AMY + NAME_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_repeatedPrefix_failure() {
        String validArguments = NAME_DESC_AMY + ROLE_DESC_AMY + STATUS_DESC_AMY;

        assertParseFailure(parser, validArguments + " s/tptptp",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));
        assertParseFailure(parser, validArguments + " r/Another Role",
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));
        assertParseFailure(parser, validArguments + INVALID_STATUS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_STATUS));
    }

    @Test
    public void parse_missingPrefix_failure() {
        assertParseFailure(parser, NAME_DESC_AMY + ROLE_DESC_AMY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusCommand.MESSAGE_USAGE));
    }
}
