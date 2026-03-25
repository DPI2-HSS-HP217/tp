package seedu.address.model.person;

import seedu.address.logic.commands.AddCommand;

/**
 * Stores the last duplicate application
 */
public class DuplicateApplicationStore {
    private static AddCommand lastDuplicateApplication = null;

    /**
     * Stores the duplicate application.
     *
     * @param command the duplicate application
     */
    public static void setLastDuplicateApplication(AddCommand command) {
        lastDuplicateApplication = command;
    }

    /**
     * Retrieves the duplicate application.
     */
    public static AddCommand getLastDuplicateApplication() {
        return lastDuplicateApplication;
    }

    /**
     * Clears the stored duplicate application.
     */
    public static void clear() {
        lastDuplicateApplication = null;
    }

    /**
     * Checks if there is a stored duplicate application.
     */
    public static boolean hasLastDuplicateApplication() {
        return lastDuplicateApplication != null;
    }
}
