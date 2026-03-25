package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.ParserMode;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    /** Help information should be shown to the user. */
    private final boolean showHelp;

    /** The application should exit. */
    private final boolean exit;

    /** The editing mode status */
    private final ParserMode parserMode;

    /** The folder name to switch to, or null if no folder switch is requested. */
    private final String folderName;

    /** True = start with empty address book (folder command), false = load existing data (toggle command). */
    private final boolean createNew;

    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, boolean showHelp, ParserMode parserMode, boolean exit) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = showHelp;
        this.parserMode = parserMode;
        this.exit = exit;
        this.folderName = null;
        this.createNew = false;
    }

    /**
     * Constructs a {@code CommandResult} that signals a folder switch to {@code LogicManager}.
     *
     * @param folderName the name of the folder to switch to
     * @param createNew  true to start with an empty address book (folder), false to load existing data (toggle)
     */
    public CommandResult(String feedbackToUser, String folderName, boolean createNew) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.showHelp = false;
        this.parserMode = ParserMode.NO_CHANGE;
        this.exit = false;
        this.folderName = requireNonNull(folderName);
        this.createNew = createNew;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, false, ParserMode.NORMAL, false);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return showHelp;
    }

    public boolean isExit() {
        return exit;
    }

    public ParserMode getParserMode() {
        return parserMode;
    }

    public String getFolderName() {
        return folderName;
    }

    public boolean isCreateNew() {
        return createNew;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && showHelp == otherCommandResult.showHelp
                && exit == otherCommandResult.exit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, showHelp, exit);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("feedbackToUser", feedbackToUser)
                .add("showHelp", showHelp)
                .add("exit", exit)
                .toString();
    }

}
