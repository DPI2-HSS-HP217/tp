package seedu.address.model.person;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;


/**
 * Tests that an Application matches Both Name and Role provided.
 */
public class SameCompanySameRolePredicate implements Predicate<Application> {
    private final Name name;
    private final Role role;

    /**
     * Constructor a Predicate to check for same Applications.
     */
    public SameCompanySameRolePredicate(Name name, Role role) {
        this.name = name;
        this.role = role;
    }

    @Override
    public boolean test(Application application) {
        return application.getName().equals(name) && application.getRole().equals(role);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SameCompanySameRolePredicate)) {
            return false;
        }

        SameCompanySameRolePredicate otherSameCompanySameRolePredicate = (SameCompanySameRolePredicate) other;
        return name.equals(otherSameCompanySameRolePredicate.name)
                && role.equals(otherSameCompanySameRolePredicate.role);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("Company", name).add("Role", role).toString();
    }
}
