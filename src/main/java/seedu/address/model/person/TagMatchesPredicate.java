package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.Locale;
import java.util.function.Predicate;

/**
 * Tests that an {@code Application}'s tags contain the target tag.
 */
public class TagMatchesPredicate implements Predicate<Application> {
    private final String targetTag;

    /**
     * Creates a predicate that checks whether an application's tags contain {@code targetTag}.
     */
    public TagMatchesPredicate(String targetTag) {
        requireNonNull(targetTag);
        this.targetTag = targetTag;
    }

    @Override
    public boolean test(Application application) {
        return application.getTags().stream()
                .map(tag -> tag.tagName)
                .anyMatch(tagName -> tagName.equalsIgnoreCase(targetTag));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TagMatchesPredicate)) {
            return false;
        }

        TagMatchesPredicate otherPredicate = (TagMatchesPredicate) other;
        return targetTag.equalsIgnoreCase(otherPredicate.targetTag);
    }

    @Override
    public int hashCode() {
        return targetTag.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return TagMatchesPredicate.class.getCanonicalName() + "{targetTag=" + targetTag + "}";
    }
}
