package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.person.Application;
import seedu.address.model.person.Reminder;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Application person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label role;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label status;
    @FXML
    private Label date;
    @FXML
    private FlowPane tags;
    @FXML
    private Label reminder;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Application person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        role.setText(" - " + person.getRole().value + "  ");
        date.setText(person.getDate().toString());
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        status.setText(person.getStatus().value);
        status.getStyleClass().removeIf(s -> s.startsWith("status-"));
        status.getStyleClass().add("status-" + person.getStatus().getStyleClass());

        if (person.hasReminder()) {
            Reminder u = person.getReminder();
            this.reminder.setVisible(true);
            String text = u.getReminderName();
            text += " - " + u.getReminderDate().value;

            this.reminder.setText(text);
        } else {
            this.reminder.setVisible(false);
        }

        person.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));
    }
}
