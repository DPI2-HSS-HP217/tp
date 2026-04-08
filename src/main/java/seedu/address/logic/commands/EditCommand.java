package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMINDER_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_APPLICATIONS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.ParserMode;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.application.Address;
import seedu.address.model.application.Application;
import seedu.address.model.application.Date;
import seedu.address.model.application.Email;
import seedu.address.model.application.IsBeingEditedPredicate;
import seedu.address.model.application.Name;
import seedu.address.model.application.Phone;
import seedu.address.model.application.Reminder;
import seedu.address.model.application.Role;
import seedu.address.model.application.Status;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing application in the address book.
 */
public class EditCommand extends Command {

    public static final String MESSAGE_USAGE = "Edits the details of the application identified "
            + "by the index number used in the displayed application list / combination of "
            + "Company Name and Job Role. Existing values will be overwritten by the input values.\n"
            + "Parameters: [" + PREFIX_NAME + "NAME] [" + PREFIX_PHONE + "PHONE] ["
            + PREFIX_EMAIL + "EMAIL] [" + PREFIX_ADDRESS + "ADDRESS] [" + PREFIX_TAG + "TAG]...\n"
            + "Note that [" + PREFIX_REMINDER + " REMINDER] must be paired with ["
            + PREFIX_REMINDER_DATE + " REMINDER_DATE]. \n"
            + "Example: " + PREFIX_PHONE + "91234567 " + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_APPLICATION_SUCCESS = "Edited Application: %1$s. To exit editing mode, "
                                    + "enter \"editexit\".";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided. Note only edit "
                                    + "commands are allowed in editing mode. To exit editing mode, enter \"editexit\".";
    public static final String MESSAGE_DUPLICATE_APPLICATION = "This application already exists in the address book."
                                    + "To exit editing mode, enter \"editexit\".";
    public static final String MESSAGE_NO_APPLICATION_EDITED = "No application is marked for editing "
                                    + "- this is likely due to an internal error.";
    public static final String MESSAGE_DATE_NOT_ALLOWED = "Date cannot be a date later than today. To exit editing "
                                    + "mode, enter \"editexit\"";

    private final EditApplicationDescriptor editApplicationDescriptor;

    /**
     * @param editApplicationDescriptor details to edit the application with
     */
    public EditCommand(EditApplicationDescriptor editApplicationDescriptor) {
        requireNonNull(editApplicationDescriptor);

        this.editApplicationDescriptor = new EditApplicationDescriptor(editApplicationDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Application> lastShownList = model.getFilteredApplicationList();

        IsBeingEditedPredicate predicate = new IsBeingEditedPredicate();
        Application applicationToEdit = lastShownList.stream().filter(predicate).findFirst()
                                        .orElseThrow(() -> new CommandException(MESSAGE_NO_APPLICATION_EDITED));

        Application editedApplication = createEditedApplication(applicationToEdit, editApplicationDescriptor);
        editedApplication.setBeingEdited(true);

        if (!applicationToEdit.isSameApplication(editedApplication) && model.hasApplication(editedApplication)) {
            throw new CommandException(MESSAGE_DUPLICATE_APPLICATION);
        }

        model.setApplication(applicationToEdit, editedApplication);
        model.updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS);
        return new CommandResult(String.format(MESSAGE_EDIT_APPLICATION_SUCCESS, Messages.format(editedApplication)),
                                        false, ParserMode.EDITING, false);
    }

    /**
     * Creates and returns a {@code Application} with the details of {@code applicationToEdit}
     * edited with {@code editApplicationDescriptor}.
     */
    private static Application createEditedApplication(Application applicationToEdit,
                                    EditApplicationDescriptor editApplicationDescriptor) throws CommandException {
        assert applicationToEdit != null;
        boolean isFutureDate = editApplicationDescriptor.getDate().map(x -> !x.hasNoFutureDate()).orElse(false);
        if (isFutureDate) {
            throw new CommandException(MESSAGE_DATE_NOT_ALLOWED);
        }

        Date updatedDate = editApplicationDescriptor.getDate().orElse(applicationToEdit.getDate());
        Name updatedName = editApplicationDescriptor.getName().orElse(applicationToEdit.getName());
        Phone updatedPhone = editApplicationDescriptor.getPhone().orElse(applicationToEdit.getPhone());
        Email updatedEmail = editApplicationDescriptor.getEmail().orElse(applicationToEdit.getEmail());
        Address updatedAddress = editApplicationDescriptor.getAddress().orElse(applicationToEdit.getAddress());
        Set<Tag> updatedTags = editApplicationDescriptor.getTags().orElse(applicationToEdit.getTags());
        Role updatedRole = editApplicationDescriptor.getRole().orElse(applicationToEdit.getRole());
        Status updatedStatus = editApplicationDescriptor.getStatus().orElse(applicationToEdit.getStatus());
        Reminder updateReminder = editApplicationDescriptor.getReminder().orElse(applicationToEdit.getReminder());

        return new Application(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updatedDate,
                                        updatedRole, updatedStatus, updateReminder);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        EditCommand otherEditCommand = (EditCommand) other;
        return editApplicationDescriptor.equals(otherEditCommand.editApplicationDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("editApplicationDescriptor", editApplicationDescriptor).toString();
    }

    /**
     * Stores the details to edit the application with. Each non-empty field value will replace the
     * corresponding field value of the application.
     */
    public static class EditApplicationDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Role role;
        private Date date;
        private Status status;
        private Reminder reminder;

        public EditApplicationDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditApplicationDescriptor(EditApplicationDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setTags(toCopy.tags);
            setRole(toCopy.role);
            setStatus(toCopy.status);
            setDate(toCopy.date);
            setReminder(toCopy.reminder);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, tags, status, role, date, reminder);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public Optional<Role> getRole() {
            return Optional.ofNullable(role);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public Optional<Date> getDate() {
            return Optional.ofNullable(date);
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        public void setReminder(Reminder reminder) {
            this.reminder = reminder;
        }

        public Optional<Reminder> getReminder() {
            return Optional.ofNullable(reminder);
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditApplicationDescriptor)) {
                return false;
            }

            EditApplicationDescriptor otherEditApplicationDescriptor = (EditApplicationDescriptor) other;
            return Objects.equals(name, otherEditApplicationDescriptor.name)
                                            && Objects.equals(phone, otherEditApplicationDescriptor.phone)
                                            && Objects.equals(email, otherEditApplicationDescriptor.email)
                                            && Objects.equals(address, otherEditApplicationDescriptor.address)
                                            && Objects.equals(tags, otherEditApplicationDescriptor.tags)
                                            && Objects.equals(status, otherEditApplicationDescriptor.status)
                                            && Objects.equals(date, otherEditApplicationDescriptor.date)
                                            && Objects.equals(role, otherEditApplicationDescriptor.role);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this).add("name", name).add("phone", phone).add("email", email)
                                            .add("address", address).add("tags", tags).add("status", status)
                                            .add("date", date).add("role", role).toString();
        }
    }
}
