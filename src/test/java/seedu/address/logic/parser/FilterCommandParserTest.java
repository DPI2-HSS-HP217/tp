package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.CompanyContainsKeywordPredicate;
import seedu.address.model.person.DateMatchesPredicate;
import seedu.address.model.person.StatusMatchesPredicate;
import seedu.address.model.person.TagMatchesPredicate;
import seedu.address.model.tag.Tag;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_companyFilter_success() {
        assertParseSuccess(parser, " /company /Google ",
                new FilterCommand(new CompanyContainsKeywordPredicate("Google")));
    }

    @Test
    public void parse_appliedFilter_success() {
        assertParseSuccess(parser, " /applied /2025-11-11 ",
                new FilterCommand(new DateMatchesPredicate("2025-11-11")));
    }

    @Test
    public void parse_statusFilter_success() {
        assertParseSuccess(parser, " /status /Applied ",
                new FilterCommand(new StatusMatchesPredicate("Applied")));
    }

    @Test
    public void parse_tagFilter_success() {
        assertParseSuccess(parser, " /tag /friends ",
                new FilterCommand(new TagMatchesPredicate("friends")));
    }

    @Test
    public void parse_emptyArguments_failure() {
        assertParseFailure(parser, " ", FilterCommandParser.MESSAGE_UNKNOWN_FILTER_COMMAND);
    }

    @Test
    public void parse_unknownType_failure() {
        assertParseFailure(parser, " /salary /5000 ", FilterCommandParser.MESSAGE_UNKNOWN_FILTER_TYPE);
    }

    @Test
    public void parse_missingCompanyValue_failure() {
        assertParseFailure(parser, " /company ", FilterCommandParser.MESSAGE_INVALID_COMPANY_FORMAT);
    }

    @Test
    public void parse_missingAppliedValue_failure() {
        assertParseFailure(parser, " /applied ", FilterCommandParser.MESSAGE_INVALID_APPLIED_FORMAT);
    }

    @Test
    public void parse_missingStatusValue_failure() {
        assertParseFailure(parser, " /status ", FilterCommandParser.MESSAGE_INVALID_STATUS_FORMAT);
    }

    @Test
    public void parse_missingTagValue_failure() {
        assertParseFailure(parser, " /tag ", FilterCommandParser.MESSAGE_INVALID_TAG_FORMAT);
    }

    @Test
    public void parse_invalidAppliedDate_failure() {
        assertParseFailure(parser, " /applied /11-11-2025 ", FilterCommandParser.MESSAGE_INVALID_DATE_FORMAT);
    }

    @Test
    public void parse_invalidTag_failure() {
        assertParseFailure(parser, " /tag /friends* ", Tag.MESSAGE_CONSTRAINTS);
    }
}
