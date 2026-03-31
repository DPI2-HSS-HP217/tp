package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NameTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Name(null));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        String invalidName = "";
        assertThrows(IllegalArgumentException.class, () -> new Name(invalidName));
    }

    @Test
    public void isValidName() {
        // null name (equivalence partitioning)
        assertThrows(NullPointerException.class, () -> Name.isValidName(null));

        // invalid name
        // empty string (equivalence partitioning)
        assertFalse(Name.isValidName(""));

        // spaces only (equivalence partitioning)
        assertFalse(Name.isValidName(" "));

        // alphanumeric characters missing (equivalence partitioning)
        assertFalse(Name.isValidName("^"));

        // cannot end with * (equivalence partitioning)
        assertFalse(Name.isValidName("peter*"));

        // cannot end with - (equivalence partitioning)
        assertFalse(Name.isValidName("peter-"));

        // must start with alphanumeric character (equivalence partitioning)
        assertFalse(Name.isValidName("*peter"));
        assertFalse(Name.isValidName("$tartup"));
        assertFalse(Name.isValidName("-Google"));

        // cannot have consecutive spaces (equivalence partitioning)
        assertFalse(Name.isValidName("Google  Inc"));
        assertFalse(Name.isValidName("A  B"));

        // valid name
        assertTrue(Name.isValidName("peter jack")); // alphabets only
        assertTrue(Name.isValidName("12345")); // numbers only
        assertTrue(Name.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(Name.isValidName("Capital Tan")); // with capital letters
        assertTrue(Name.isValidName("David Roger Jackson Ray Jr 2nd")); // long names

        //valid name with symbol at the end (equivalence partitioning)
        assertTrue(Name.isValidName("Google Inc.")); // with dot
        assertTrue(Name.isValidName("Google Inc!")); // with exclamation mark

        //invalid name with symbol at the end (equivalence partitioning)
        assertFalse(Name.isValidName("Google Inc*")); // with asterisk
        assertFalse(Name.isValidName("Google Inc-")); // with hyphen
        assertFalse(Name.isValidName("Google Inc$")); // with dollar sign

        //single character name (boundary value analysis)
        assertFalse(Name.isValidName("a"));
        assertFalse(Name.isValidName("1"));

        //name with symbol in the middle (equivalence partitioning)
        assertTrue(Name.isValidName("Google_Inc")); // with underscore
        assertTrue(Name.isValidName("Google&Inc")); // with ampersand
    }

    @Test
    public void equals() {
        Name name = new Name("Valid Name");

        // same values -> returns true
        assertTrue(name.equals(new Name("Valid Name")));

        // same object -> returns true
        assertTrue(name.equals(name));

        // name differs in case, same values -> returns true
        assertTrue(name.equals(new Name("valid name")));
        assertTrue(name.equals(new Name("VALID NAME")));

        // name differs in case, same values -> returns true
        Name symbolName = new Name("AT&T");
        assertTrue(symbolName.equals(new Name("at&t")));

        // null -> returns false
        assertFalse(name.equals(null));

        // different types -> returns false
        assertFalse(name.equals(5.0f));

        // different values -> returns false
        assertFalse(name.equals(new Name("Other Valid Name")));

        // Different symbols -> returns false
        Name yahooWithSymbol= new Name("Yahoo!");
        Name yahooWithoutSymbol = new Name("Yahoo");
        assertFalse(yahooWithSymbol.equals(yahooWithoutSymbol));
    }
}
