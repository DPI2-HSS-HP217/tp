package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.util.Set;

/**
 * Represents a Person's application status in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidStatus(String)}
 */
public class Status {

    public static final String MESSAGE_CONSTRAINTS = "Valid Statuses are Interested -> Applied ->"
            + " Interview -> Offered or Rejected -> Accepted";

    private static final Set<String> VALID_STATUSES = Set.of("interested", "applied",
            "pending", "interview", "offered", "rejected", "accepted", "");

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param status A valid status.
     */
    public Status(String status) {
        requireNonNull(status);
        checkArgument(isValidStatus(status), MESSAGE_CONSTRAINTS);
        value = getNormalisedStatus(status);
    }

    /**
     * Returns true if a given string is a valid status.
     */
    public static boolean isValidStatus(String test) {
        return test != null && (test.isEmpty() || VALID_STATUSES.contains(test.toLowerCase()));
    }

    /**
     * Returns standardised Statues String representation, where the first letter is capitalised.
     */
    private String getNormalisedStatus(String status) {
        assert status != null;

        return switch (status.toLowerCase()) {
        case "applied" -> "Applied";
        case "interview" -> "Interview";
        case "rejected" -> "Rejected";
        case "offered" -> "Offered";
        case "pending" -> "Pending";
        case "accepted" -> "Accepted";
        default -> "Interested";
        };
    }

    public String getStyleClass() {
        assert value != null;

        String statusVal = this.value.toLowerCase();
        return switch (statusVal) {
        case "applied" -> "Applied";
        case "interview" -> "Interview";
        case "rejected" -> "Rejected";
        case "offered" -> "Offered";
        case "pending" -> "Pending";
        case "accepted" -> "Accepted";
        default -> "Interested";
        };
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return value.equals(otherStatus.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
