package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Application {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;
    private final Role role;
    private final Date date;
    private final Address address;
    private final Status status;
    private final Reminder reminder;

    // Data fields
    private final Set<Tag> tags = new HashSet<>();

    //Internal field
    private boolean isBeingEdited = false;

    /**
     * Every field must be present and not null.
     */
    public Application(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                Date date, Role role, Status status) {
        requireAllNonNull(name, phone, email, role, tags, status, date, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.date = date;
        this.tags.addAll(tags);
        this.address = address;
        this.status = status;
        this.reminder = null;
    }

    /**
     * Every field must be present and not null.
     */
    public Application(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                       Date date, Role role, Status status, Reminder reminder) {
        requireAllNonNull(name, phone, email, role, tags, status, date, address);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.role = role;
        this.date = date;
        this.tags.addAll(tags);
        this.address = address;
        this.status = status;
        this.reminder = reminder;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public Role getRole() {
        return role;
    }

    public Date getDate() {
        return date;
    }

    public Status getStatus() {
        return status;
    }

    public Reminder getReminder() {
        return reminder;
    }

    public boolean hasReminder() {
        return !(reminder == null);
    }

    public boolean getEditingStatus() {
        return isBeingEdited;
    }

    /**
     * Returns an immutable tag set, which throws
     * {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSameApplication(Application otherApplication) {
        if (otherApplication == this) {
            return true;
        }

        if (otherApplication == null) {
            return false;
        }

        return this.getName().fullName.equalsIgnoreCase(otherApplication.getName().fullName)
            && this.getRole().value.equalsIgnoreCase(otherApplication.getRole().value);
    }

    public void setBeingEdited(boolean isEdit) {
        this.isBeingEdited = isEdit;
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Application)) {
            return false;
        }

        Application otherApplication = (Application) other;
        return name.fullName.equalsIgnoreCase(otherApplication.name.fullName)
                && phone.equals(otherApplication.phone)
                && email.equals(otherApplication.email)
                && address.equals(otherApplication.address)
                && date.equals(otherApplication.date)
                && role.equals(otherApplication.role)
                && status.equals(otherApplication.status)
                && tags.equals(otherApplication.tags);
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, date, role, address, status, tags);
    }

    @Override
    public String toString() {
        ToStringBuilder sb = new ToStringBuilder(this)
                .add("name", name)
                .add("phone", phone)
                .add("email", email)
                .add("role", role)
                .add("date", date)
                .add("tags", tags)
                .add("address", address)
                .add("status", status);

        if (reminder != null) {
            sb.add("event" , reminder);
        }
        return sb.toString();
    }

}
