package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.RemoveReminderCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemoveReminder code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveReminderCommandParserTest {

    private RemoveReminderCommandParser parser = new RemoveReminderCommandParser();

    @Test
    public void parse_validIndexArgs_returnsRemoveReminderCommand() {
        assertParseSuccess(parser, "1", new RemoveReminderCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidIndexArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveReminderCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNormalArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, NAME_DESC_AMY + ROLE_DESC_AMY,
                new RemoveReminderCommand(AMY.getName(), AMY.getRole()));
    }

    @Test
    public void parse_invalidNormalArgs_throwsParseException() {
        assertParseFailure(parser, NAME_DESC_AMY + INVALID_ROLE_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveReminderCommand.MESSAGE_USAGE));
    }
}
