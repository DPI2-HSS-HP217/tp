package seedu.address.logic;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Path;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookEditingParser;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.UpcomingCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Application;
import seedu.address.storage.Storage;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_FORMAT = "Could not save data due to the following error: %s";

    public static final String FILE_OPS_PERMISSION_ERROR_FORMAT =
            "Could not save data to file %s due to insufficient permissions to write to the file or the folder.";

    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser normalParser;
    private final AddressBookEditingParser editingParser;
    private AddressBookParser currentParser;
    private ParserMode currentMode;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        normalParser = new AddressBookParser();
        editingParser = new AddressBookEditingParser();
        currentParser = normalParser;

        /* This is for the purposes of forcing application to send a reminder on start-up.
          Not *perfect*, but I'm not sure how to reduce coupling. */
        try {
            new UpcomingCommandParser().parse(Integer.toString(model.getReminderOffset())).execute(model);
        } catch (ParseException e) {
            logger.info("Internal Error: Unable to send reminder notification on bootup.");
        }
    }

    @Override
    public CommandResult execute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");
        CommandResult commandResult;

        Command command = currentParser.parseCommand(commandText);
        commandResult = command.execute(model);

        // check if editing mode or normal mode
        switch (commandResult.getParserMode()) {
        case EDITING:
            currentParser = editingParser;
            currentMode = ParserMode.EDITING;
            break;
        case NORMAL:
            currentParser = normalParser;
            currentMode = ParserMode.NORMAL;
            break;
        case NO_CHANGE:
            break;
        default:
            break;
        }

        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (AccessDeniedException e) {
            throw new CommandException(String.format(FILE_OPS_PERMISSION_ERROR_FORMAT, e.getMessage()), e);
        } catch (IOException ioe) {
            throw new CommandException(String.format(FILE_OPS_ERROR_FORMAT, ioe.getMessage()), ioe);
        }

        String folderName = commandResult.getFolderName();
        if (folderName != null) {
            handleFolderSwitch(folderName, commandResult.isCreateNew());
        }

        return commandResult;
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Application> getFilteredPersonList() {
        return model.getFilteredPersonList();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    /**
     * Handles switching the active address book to a different folder.
     * Storage is responsible for the file-level operations;
     * this method updates the model to reflect the new data.
     */
    private void handleFolderSwitch(String folderName, boolean createNew) throws CommandException {
        try {
            if (createNew) {
                storage.createFolder(folderName);
                model.setAddressBook(storage.readAddressBook().orElseThrow());
            } else {
                ReadOnlyAddressBook loaded = storage.toggleFolder(folderName);
                model.setAddressBook(loaded);
            }
            model.setAddressBookFilePath(storage.getAddressBookFilePath());
            storage.saveUserPrefs(model.getUserPrefs());
        } catch (IOException | DataLoadingException e) {
            throw new CommandException(e.getMessage(), e);
        }
    }
}
