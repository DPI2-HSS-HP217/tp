package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class RoleTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Role(null)); // (equivalence partitioning)
    }

    @Test
    public void constructor_invalidRole_throwsIllegalArgumentException() {
        String invalidRole = " "; // (equivalence partitioning)
        assertThrows(IllegalArgumentException.class, () -> new Role(invalidRole));
    }

    @Test
    public void isValidRole() {
        // null input
        assertThrows(NullPointerException.class, () -> Role.isValidJobRole(null));

        // blank role
        assertFalse(Role.isValidJobRole("")); // empty string (equivalence partitioning)
        assertFalse(Role.isValidJobRole(" ")); // spaces only (equivalence partitioning)

        // invalid characters
        assertFalse(Role.isValidJobRole("@backend")); // symbols not allowed (equivalence partitioning)
        assertFalse(Role.isValidJobRole("software.engineer")); // symbols not allowed (equivalence partitioning)
        assertFalse(Role.isValidJobRole("backend-developer")); // symbols not allowed (equivalence partitioning)
        assertFalse(Role.isValidJobRole("job1")); // numeric not allowed (equivalence partitioning)
        assertFalse(Role.isValidJobRole("1job")); // numeric not allowed (equivalence partitioning)
        assertTrue(Role.isValidJobRole("software engineering")); // spaces allowed (equivalence partitioning)
    }

    @Test
    public void equals() {
        Role role = new Role("validRole");

        // same values -> returns true
        assertTrue(role.equals(new Role("validRole")));

        // same object -> returns true
        assertTrue(role.equals(role));

        // null -> returns false
        assertFalse(role.equals(null));

        assertFalse(role.equals(5.0f));
    }

}
