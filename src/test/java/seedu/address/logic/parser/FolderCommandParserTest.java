package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FolderCommand;

public class FolderCommandParserTest {

    private final FolderCommandParser parser = new FolderCommandParser();

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, " ",
                "Folder name cannot be empty.\n" + FolderCommand.MESSAGE_USAGE);
    }

    @Test
    public void parse_nameWithSpaces_throwsParseException() {
        assertParseFailure(parser, "Y1 S2",
                "Folder name can only contain letters, numbers, underscores, and hyphens.");
    }

    @Test
    public void parse_nameWithSpecialChars_throwsParseException() {
        assertParseFailure(parser, "name@folder",
                "Folder name can only contain letters, numbers, underscores, and hyphens.");
    }

    @Test
    public void parse_validArgs_returnsFolderCommand() {
        assertParseSuccess(parser, "Y1S2", new FolderCommand("Y1S2"));
    }

    @Test
    public void parse_validArgsWithWhitespace_returnsFolderCommand() {
        assertParseSuccess(parser, "  Y1S2  ", new FolderCommand("Y1S2"));
    }

    @Test
    public void parse_validArgsWithHyphen_returnsFolderCommand() {
        assertParseSuccess(parser, "Y1-S2", new FolderCommand("Y1-S2"));
    }

    @Test
    public void parse_validArgsWithUnderscore_returnsFolderCommand() {
        assertParseSuccess(parser, "Y1_S2", new FolderCommand("Y1_S2"));
    }

    @Test
    public void parse_validArgsNumericOnly_returnsFolderCommand() {
        assertParseSuccess(parser, "123", new FolderCommand("123"));
    }

}
