package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.application.Address;
import seedu.address.model.application.Application;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.Name;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Reminder;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Application[] getSampleApplications() {
        return new Application[] {
            new Application(new Name("Google"), new Phone("6502530000"), new Email("careers@google.com"),
                    new Address("1600 Amphitheatre Parkway, Mountain View, CA 94043"),
                    getTagSet("tech", "remote"), new Date("2026-03-15"),
                    new Role("Software Engineer"), new Status("Interested"),
                    new Reminder("Technical Interview", "2026-04-17")),
            new Application(new Name("Microsoft"), new Phone("4258828080"), new Email("jobs@microsoft.com"),
                    new Address("One Microsoft Way, Redmond, WA 98052"),
                    getTagSet("tech", "hybrid"), new Date("2026-03-18"),
                    new Role("Product Manager"), new Status("Interview"),
                    new Reminder("Final Interview", "2026-04-18")),
            new Application(new Name("Amazon"), new Phone("2062661000"), new Email("jobs@amazon.com"),
                    new Address("410 Terry Ave N, Seattle, WA 98109"),
                    getTagSet("tech", "cloud"), new Date("2026-03-22"),
                    new Role("Cloud Architect"), new Status("Pending"),
                    new Reminder("Follow up Email", "2026-04-19")),
            new Application(new Name("Stripe"), new Phone("6282187000"), new Email("jobs@stripe.com"),
                    new Address("510 Townsend Street, San Francisco, CA 94103"),
                    getTagSet("startup", "fintech"), new Date("2026-04-08"),
                    new Role("Backend Engineer"), new Status("Applied")),
            new Application(new Name("Meta"), new Phone("6505434800"), new Email("careers@meta.com"),
                    new Address("1 Hacker Way, Menlo Park, CA 94025"),
                    getTagSet("tech", "social"), new Date("2026-03-25"),
                    new Role("Frontend Engineer"), new Status("Offered"),
                    new Reminder("Final Interview", "2026-04-17")),
            new Application(new Name("JPMorgan Chase"), new Phone("2122706000"),
                    new Email("recruiting@jpmchase.com"),
                    new Address("383 Madison Ave, New York, NY 10179"),
                    getTagSet("finance", "analytics"), new Date("2026-03-30"),
                    new Role("Quantitative Analyst"), new Status("Rejected")),
            new Application(new Name("JPMorgan Chase"), new Phone("2122706000"),
                    new Email("recruiting@jpmchase.com"),
                    new Address("383 Madison Ave, New York, NY 10179"),
                    getTagSet("data", "analytics"), new Date("2026-03-30"),
                    new Role("Data Analyst"), new Status("Accepted")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Application sampleApplication : getSampleApplications()) {
            sampleAb.addApplication(sampleApplication);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings).map(Tag::new).collect(Collectors.toSet());
    }

}
