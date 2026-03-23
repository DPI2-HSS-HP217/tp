package seedu.address.model.person;

import seedu.address.commons.util.ToStringBuilder;

import java.util.Objects;

public class Event {
    private final String eventName;
    private final Date eventDate;

    public Event(String eventName) {
        this.eventName = eventName;
        this.eventDate = null;
    }

    public Event(String eventName, String eventDate) {
        this.eventName = eventName;
        this.eventDate = new Date(eventDate);
    }


    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        return false;
    }


    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, eventDate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Event", eventName)
                .add("Date", eventDate).toString();
    }

}
