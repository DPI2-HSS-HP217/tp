---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

_{ list here sources of all reused/adapted ideas, code, documentation, and third-party libraries -- include links to the original source as well }_

--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.)

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ApplicationListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Application` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("add n/Nus r/Dev")` API call as an example.

<puml src="diagrams/AddSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `add n/Nus r/Dev` Command" />

<box type="info" seamless>

**Note:** The lifeline for `AddCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `AddCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add an application).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="760" />


The `Model` component,

* stores the address book data i.e., all `Application` objects (which are contained in a `UniqueApplicationList` object).
* stores the currently 'selected' `Appliction` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Application>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in `OfferFlow`, which `Application` references. This allows `OfferFlow` to only require one `Tag` object per unique tag, instead of each `Application` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="760" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both OfferFlow data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefsStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`).
* supports folder management operations: creating a new data folder (`createFolder`), switching between existing folders (`toggleFolder`), and listing all available folders (`getAvailableFolders`), allowing multiple separate address book files to be managed under the `data/` directory.

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Filter feature

The `filter` command is responsible for narrowing the currently displayed application list without modifying the
underlying stored applications.

The sequence diagram below shows how a filter command is parsed and executed:

<puml src="diagrams/FilterSequenceDiagram.puml" alt="Sequence diagram for filter command" width="760" />

`AddressBookParser` routes `filter ...` input to `FilterCommandParser`. The parser then:

* tokenizes the input using the supported filter prefixes:
  `n/` (company name), `d/` (application date), `r/` (role), `s/` (status), and `t/` (tag)
* rejects malformed commands such as unsupported prefixes, repeated single-value prefixes, empty values, or invalid
  dates
* constructs a list of predicates, one for each supplied criterion
* wraps those predicates in `ApplicationMatchesAllPredicate`, so an application must satisfy every supplied filter
  condition to remain visible

When the resulting `FilterCommand` executes, it calls `Model#updateFilteredApplicationList(predicate)`. In
`ModelManager`, this updates the `FilteredList<Application>` that backs the UI. As a result:

* the address book data itself is unchanged
* only the visible subset of applications changes
* the UI refreshes automatically because it observes the filtered application list

An additional implementation detail is that `AddressBookParser` also accepts compact commands such as
`filter/n Google` by normalizing command words that start with `filter/` before forwarding them to
`FilterCommandParser`.

### Update status feature

The `status` command updates the status field of exactly one existing application, identified by company name and job
role.

The sequence diagram below shows how a status update command is parsed and executed:

<puml src="diagrams/StatusSequenceDiagram.puml" alt="Sequence diagram for status command" width="760" />

`AddressBookParser` routes `status ...` input to `StatusCommandParser`. The parser:

* requires the prefixes `n/` (company name), `r/` (role), and `s/` (new status)
* explicitly rejects prefixes unrelated to status updates such as phone, email, address, tag, and date
* prevents duplicate occurrences of the required prefixes
* parses the target name, role, and new status into domain objects before constructing `StatusCommand`

When `StatusCommand` executes, it:

1. reads the current filtered application list from the model
1. scans that list to find the application whose name and role match the user input, ignoring case
1. creates a replacement `Application` with the new `Status`
1. preserves the rest of the application data, including any existing reminder
1. calls `Model#setApplication(target, updatedApplication)` to replace the old application in the address book
1. calls `Model#updateFilteredApplicationList(PREDICATE_SHOW_ALL_APPLICATIONS)` so the UI returns to the full list

This design keeps `Application` immutable from the perspective of command logic. Instead of mutating the existing
object in place, the command creates a new `Application` instance and swaps it into the model.

### Dynamic UI

Dynamic UI allows different visual styles to be applied based on the states of an application.

The sequence diagram below shows how dynamic UI is generated for an application's status:

<puml src="diagrams/DynamicStyleSequence.puml" alt="Sequence diagram for dynamic" width="760" />

Additional changes to UI styling may be performed in `ApplicationCard` without modifying other components:

1. When updating a JavaFX component's style, get the `StyleClass` of the relevant JavaFX component
2. Check and remove any unnecessary style classes
3. Add the new style class
4. Add the relevant styles in the corresponding CSS stylesheet


### Folder feature

The `folder` command creates a new empty address book saved as a JSON file under the `data/` directory and immediately switches to it.

The sequence diagram below shows how a folder command is parsed and executed:

<puml src="diagrams/FolderSequenceDiagram.puml" alt="Sequence diagram for folder command" width="760" />

<box type="info" seamless>

**Note:** The lifeline for `FolderCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

`AddressBookParser` routes `folder ...` input to `FolderCommandParser`. The parser:

* rejects an empty folder name
* only accepts names containing letters, numbers, underscores, and hyphens

Unlike most commands, `FolderCommand#execute(model)` does not perform the actual work. Because `Command` only has access to `Model` and not `Storage`, the command instead signals `LogicManager` via `CommandResult` with `folderName` and `isCreateNew=true`. `LogicManager` then:

1. calls `Storage#createFolder(folderName)` to create the new JSON file
2. loads the newly created empty address book into `Model` via `Model#setAddressBook()`
3. updates the active file path via `Model#setAddressBookFilePath()`


### Toggle feature

The `toggle` command switches the active address book to an existing folder under the `data/` directory.

The sequence diagram below shows how a toggle command is parsed and executed:

<puml src="diagrams/ToggleSequenceDiagram.puml" alt="Sequence diagram for toggle command" width="760" />

<box type="info" seamless>

**Note:** The lifeline for `ToggleCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.

</box>

`AddressBookParser` routes `toggle ...` input to `ToggleCommandParser`. The parser applies the same validation as `FolderCommandParser`: rejecting empty names and names with invalid characters.

The execution follows the same delegation pattern as `folder`. `ToggleCommand#execute(model)` signals `LogicManager` via `CommandResult` with `folderName` and `isCreateNew=false`. `LogicManager` then:

1. calls `Storage#toggleFolder(folderName)` which loads the existing JSON file, throwing an error if it does not exist
2. loads the returned address book into `Model` via `Model#setAddressBook()`
3. updates the active file path via `Model#setAddressBookFilePath()`

After every command, `MainWindow` calls `Logic#getAddressBookFilePath()` and updates the status bar footer, so the currently active folder is always reflected in the GUI.


### Upcoming feature

The `upcoming` command is responsible for first narrowing the currently displayed application list similar to `filter`,
and secondly saving the parameter to disk so that the application may re-apply the same operation on start-up.

The sequence diagram below shows how an upcoming command is parsed and executed:

<puml src="diagrams/UpcomingSequenceDiagram.puml" alt="Sequence diagram for upcoming command" width="760" />

`AddressBookParser` routes `upcoming ...` input to `UpcomingCommandParser`. The parser then:

* rejects malformed commands such as empty values, non-integer values, or invalid integer values
* constructs a predicate `ReminderWithinOffsetPredicate`, which is used similarly to the predicate constructed
  by the `filter` command

When the resulting `UpcomingCommand` executes, it calls `Model#updateFilteredApplicationList(predicate)` to a similar
effect as the `filter` command.
In addition, the command will save the parameter given to disk. Due to similar restrictions in accessing storage as in `folder` command,
the command instead updates this value by calling `Model#setReminderOffset(offset)` which in turn calls `UserPrefs#setReminderOffset(reminderOffset)`. This will set
the value of a variable which will be saved to disk via `preferences.json` upon closure of the application.


### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th application in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/Google r/Software Engineer...` to add a new application. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the application was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/Google r/Software Engineer…​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the application being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* Has a need to manage a significant number internship applications
* Applies to many companies simultaneously and needs to track different application stages
* Prefers desktop apps over other types
* Can type fast and prefers typing over mouse interactions
* Is comfortable using CLI applications
* Wants to quickly update internship application statuses
* Needs to track deadlines, interviews, and follow-ups
* Values fast and efficient workflows with minimal steps

**Value proposition**: help students manage and track all their internship/job applications all under one application


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​           | I want to …​                                              | So that I can…​                                                  |
|----------|-------------------|-----------------------------------------------------------|------------------------------------------------------------------|
| `* * *`  | student           | add application with company name, job position and other details                   | keep track of my applications                                    |
| `* * *`  | lazy student              | be able to edit my application                                  | update my application if there are any changes without having to delete and create a new one for scratch                                     |
| `* * *`  | student              | be able to delete any application quickly                                | remove any redundant application                                     |
| `* * *`  | careless student    | look for specific applications easily                                | avoid applying twice to the same job position and/or company                             |
| `* * *`  | impatient student    | be able to filter out my applications                          | view only the relevant information I want                        |
| `* * *`  | busy student applying for many internships    | be able to ensure the app can load all of my applications quickly                          | log all my applications without slowing down my workflow                        |
| `* * *`  | busy student    | be able to track the progress/stages of all my applications                          | remember/ be updated on what is my application status for each of my applications                         |
| `* *`    | forgetful student              | be able to set reminders for any deadlines                | be reminded of relevant tasks nearing their date                  |
| `* *`  | potential user    | see some sample application on the app so that I can                        | see how the app will look and explore how the app works before using it myself |
| `*`      | user              | ensure buttons, tabs, and statuses look consistent        | avoid getting confused while navigating                          |
| `*`      | user with different accessibility needs             | ensure the app can support accessibility standards (e.g., screen readers, adjustable text size, dark mode),         | use it more conveniently                         |
| `*`      | busy student             | see a visual diagram of all the statuses of my applications            | quickly get an overview of my progress                |


*{More to be added}*

### Use cases

(For all use cases below, the **System** is the `OfferFlow` and the **Actor** is the `user`, unless specified otherwise)

**Use case: UC1 - Add an application**

**MSS**

1. User enters the new job application that they want to add, minimally the company name and job position they applied
but can also choose to add other optional details (date of application, contact details like email, phone number, address, status of application, reminders.)

2. OfferFlow creates and adds the application with the given details

   Use case ends.

**Extensions**

   * 1a. The user enters an application which has the same company name and job position as a previously added application.

      * 1a1. OfferFlow shows an error message stating that another application with the same name and role is already present

      * 1a2. User <u>chooses to overwrite the previously added application with the new application. (UC5)</u>

         Use case ends.

   * 1b. The user enters an application with invalid company name format.

      * 1b1. OfferFlow shows an error message stating that the name format is invalid and shows the valid format for adding company name.

         Use case resumes from step 1 if user trys to add an application again.

   * 1c. If the user enters an application with invalid format for any detail field (job position, date, reminder, status, address, email, or phone number)

      * 1c1. the process follows step 1b extension. However, the error message will be specific to each invalid field,
      showing either the valid format or the specific error reason.

   * 1d. The user enters the wrong command format when trying to add an application

      * 1d1. OfferFlows shows an error message stating the valid command format together with an example

         Use case resumes from step 1 if user trys to add an application again.


**Use case: UC2 - Overwrite an application**

**MSS**

1. User enters the `overwrite` command
2. OfferFlow deletes the existing duplicate application and adds the new application

**Extensions**

* 1a. There is no duplicate application.

    * 1a1. OfferFlow shows an error message stating that there is no duplicate application to overwrite.

      Use case ends.

**Use case: Edit an application**

**MSS**

1. User requests to edit a particular contact
2. OfferFlow shows the company's current contact details
3. User edits contact details such as changing the email and/or phone number
4. User confirms all changes made to the company contact details
5. OfferFlow updates the company contact details to reflect the new changes if any

   Use case ends.

**Extensions**

* 1a. The company contact cannot be found.

   * 1a1. OfferFlow shows an error message.

      Use case ends.

* 3a. The given phone number is invalid.

   * 3a1. OfferFlow shows an error message.

      Use case resumes at step 3.

* 3b. The given email address is invalid.

   * 3b1. OfferFlow shows an error message.

      Use case resumes at step 3.

**Use case: Edit Reminder**

**MSS**

1. User requests to edit a particular application
2. OfferFlow shows the company's current application details
3. User creates or edits both reminder description and reminder date
4. OfferFlow updates the application details to reflect the new changes if any

   Use case ends.

**Extensions**

* 1a. The company application cannot be found.

   * 1a1. OfferFlow shows an error message.

      Use case ends.

* 3a. The given reminder description or reminder date is invalid.

   * 3a1. OfferFlow shows an error message.

      Use case resumes at step 3.

* 3b. The reminder description or reminder date is not inputted.

   * 3b1. OfferFlow shows an error message.

      Use case resumes at step 3.

**Use case: Update status**

**MSS**

1. User requests to update the status of a specific application.
2. OfferFlow identifies the application to be updated.
3. User provides the new status.
4. OfferFlow updates the application's status and shows the updated application details.

   Use case ends.

**Extensions**

* 1a. The user enters the command in an invalid format.

   * 1a1. OfferFlow shows an error message.

      Use case ends.

* 2a. OfferFlow cannot find an application matching the given company name and role.

   * 2a1. OfferFlow shows an error message.

      Use case ends.

* 2b. The status provided by the user is invalid.

   * 2b1. OfferFlow shows an error message.

      Use case ends.

**Use case: Delete an application**

**MSS**

1. User request to delete a specific job application.
2. OfferFlow deletes the job application.
3. OfferFlow updates the application list.

   Use case ends.

**Extensions**

* 1a. The user enters details in an invalid format.

    * 1a1. OfferFlow shows an error message.

    * 1a2. User re-enters the details.

      Use case resumes at step 2.

* 2a. The user specified a non-existent application.

    * 2a1. OfferFlow shows an error message.

      Use case resumes at step 1.

**Use case: Filter applications**

**MSS**

1. User requests to filter the application list and specifies one or more filter criteria.
2. OfferFlow applies the given filter criteria and displays the matching applications.

   Use case ends.

**Extensions**

* 1a. The user enters the command in an invalid format.

    * 1a1. OfferFlow shows an error message.

    * 1a2. User re-enters the details.

      Use case resumes at step 2.


* 1b. The user provides an invalid filter value.

    * 1b1. OfferFlow shows an error message.

    * 1b2. User re-enters the details.

      Use case resumes at step 2.

* 1c. No applications match the specified criteria.

    * 1c1. OfferFlow informs the user that no matching applications were found.

      Use case ends.

**Use case: UC3 - Create a new folder**

**System:** OfferFlow
**Actor:** User

**MSS**

1. User requests to create a new folder with a given name.
2. OfferFlow creates a new empty address book under the given folder name and switches to it.
3. OfferFlow confirms the creation and displays the active folder.

   Use case ends.

**Extensions**

* 1a. User enters an empty folder name.

   * 1a1. OfferFlow shows an error message stating that the folder name cannot be empty.

      Use case ends.

* 1b. User enters a folder name with invalid characters (e.g. spaces, `@`, `.`).

   * 1b1. OfferFlow shows an error message stating the valid folder name format.

      Use case ends.

* 1c. A folder with the same name already exists.

   * 1c1. OfferFlow shows an error message stating the folder already exists.
   * 1c2. OfferFlow suggests using `toggle` to switch to the existing folder.

      Use case ends.

**Use case: UC4 - Switch to an existing folder**

**System:** OfferFlow
**Actor:** User

**MSS**

1. User requests to switch to an existing folder with a given name.
2. OfferFlow loads the address book from the folder and switches to it.
3. OfferFlow displays the loaded applications.

   Use case ends.

**Extensions**

* 1a. User enters an empty folder name.

   * 1a1. OfferFlow shows an error message stating that the folder name cannot be empty.

      Use case ends.

* 1b. User enters a folder name with invalid characters.

   * 1b1. OfferFlow shows an error message stating the valid folder name format.

      Use case ends.

* 2a. OfferFlow cannot find a folder with the given name.

   * 2a1. OfferFlow shows an error message stating the folder does not exist.
   * 2a2. OfferFlow suggests using `folder` to create a new one.

      Use case ends.

**Use case: UC5 - List all folders**

**System:** OfferFlow
**Actor:** User

**MSS**

1. User requests to list all existing folders.
2. OfferFlow displays the folder names sorted alphabetically.

   Use case ends.

**Extensions**

* 2a. No folders exist in the data directory.

   * 2a1. OfferFlow informs the user that no folders were found.

      Use case ends.

### Non-Functional Requirements

1.  Should work on any _mainstream OS_ as long as it has Java `17` or above installed.
2.  Should be able to hold up to 1000 applications without a noticeable sluggishness in performance for typical usage.
3.  A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The user interface should be simple and intuitive so that first-time users can learn to use the application without a tutorial.
5. Components of the program interface should be of consistent sizes, shapes and colors to ensure a coherent user experience.
6. The system should respond to and complete execution of any user command within 5 seconds.
7. The system should maintain smooth performance when managing at least 200 application entries.
8. Loading and updating of programmes files should be efficient and quick to ensure a streamline experience.
9. The program should execute commands efficiently as the user submits an increasing amount of applications.
10. The program should finish executing a user command before executing the next.
11. The program should log past executions of user commands, so that users can undo their actions.
12. The program should save its current state after every execution of a user command.
13. The program should be able to revert to a limited number of previous states when a user undo.
14. The system should handle invalid commands gracefully and inform the user of the correct command format
15. When an error occurs, the system should remain in or return to the last valid state without crashing
16. The program should handle errors gracefully without crashing
17. The program should explicitly inform the user of the reasons for errors.
18. The program should remain or return to its latest correct state when an error occurs
19. The program code should follow the coding standards of cs2103.
20. The application should run on both Windows and macOS environments.

*{More to be added}*

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others
* **Application**: A job or internship application submitted by the user to a company.
* **Duplicate**:  A repeated Application with the same <Company_Name> and <Job_Role>.
* **Contact**: A record containing information about a company or recruiter, including name, role, and company.
* **Application Status**:  The current stage of a job application (e.g., Plan to Apply, Applied, Interview, Pending, Rejected, Offered).
* **Reminder**: An Event created by user, containing the Event description and date of event.
* **Notification**:  A reminder sent by OfferFlow to alert the user about upcoming deadlines or interview dates.
* **Folder**:  A storage group that allows users to archive past job search sessions.
* **Interaction**:   Any recorded communication or follow-up with a recruiter or company (e.g., email reply or interview invitation).
* **Sample Data**:   Preloaded example entries that allow new users to explore the app before adding their own information.
* **Cache / Archive**:  A way to store old job search records so that users can start a new job search while keeping historical data.
* **Filtering**:  A feature that allows users to view applications based on certain attributes such as company name or deadline.

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   2. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

2. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   2. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

### [Adding an application](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#adding-an-internship-application-add)

1. Add different applications with [different parameters](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#application)

    1. Test case: `add n/NUS r/tester`<br>
       Expected: Application to NUS for tester role added into list. Status should show `Interested`.

    2. Test case: `add n/NUS r/QA s/Interview u/Interview ud/2026-12-12`<br>
       Expected: Application to NUS for QA role added into list. Status shows `Interview`. Yellow Reminder Box appears.

    3. Test case: `add n/NUS s/Applied`<br>
       Expected: Application not added. Error details appear.

### [Overwriting an application](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#overwrite-duplicate-application-overwrite)

1. Add different applications with [different parameters](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#application)

   1. Prerequisites: Performed the testcases under "Adding an Application".

   2. Test case: `add n/NUS r/tester s/Applied`<br>
       Expected: `Overwrite` warning appears, replace command with `overwrite`. Status would now show `Applied`.

### [Deleting an application](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#deleting-an-application-delete)

1. Deleting an Application via Name and Role

    1. Test case: `delete n/NUS r/tester`<br>
       Expected: Specified application is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    2. Testcase: `delete n/NUS r/tester`<br>
       Expected: Delete failed. Application not found.

2. Deleting an application via index

    1. Prerequisites: List all application using the `list` command. Multiple application in the list.

    2. Test case: `delete 1`<br>
       Expected: First application is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    3. Test case: `delete 0`<br>
       Expected: No application is deleted. Error details shown in the status message. Status bar remains the same.

    4. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

### [Editing an application](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#editing-an-application-editmode)

1. Edit different applications with [different parameters](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#application)

    1. Prerequisites: Add at least one application.

    2. Test case: `editmode 1`<br>
       Expected: Application enters `editing mode`.

    3. Test case: `s/Applied`<br>
       Expected: Application status now changed to `Applied`.


### [Creating a new folder](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#creating-a-new-offerflow-folder--folder)

1. Creating a folder with a valid name

   1. Test case: `folder Y1S2`<br>
      Expected: New folder created. Status bar updates to `./data/y1s2.json`. Result message shows `Created and switched to folder: y1s2`.

   2. Test case: `folder internships-2025`<br>
      Expected: New folder created at `./data/internships-2025.json`.

2. Creating a folder that already exists

   1. Prerequisites: Run `folder Y1S2` first so `data/y1s2.json` exists.

   2. Test case: `folder Y1S2`<br>
      Expected: No folder created. Error message shown stating folder already exists and to use `toggle` instead.

   3. Test case: `folder y1s2`<br>
      Expected: Same error as above — folder names are case-insensitive.

3. Creating a folder with invalid names

   1. Test case: `folder`<br>
      Expected: No folder created. Error message stating folder name cannot be empty.

   2. Test case: `folder Y1 S2`<br>
      Expected: No folder created. Error message stating folder name can only contain letters, numbers, underscores, and hyphens.

   3. Test case: `folder name@folder`<br>
      Expected: No folder created. Same error as above.

### [Switching to an existing folder](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#switching-to-an-existing-offerflow-folder--toggle)

1. Switching to an existing folder

   1. Prerequisites: Run `folder Y1S2` to ensure `data/y1s2.json` exists. Then run `folder Y2S1` to create and switch to a second folder.

   2. Test case: `toggle Y1S2`<br>
      Expected: Switched to `data/y1s2.json`. Status bar updates accordingly. Result message shows `Switched to folder: y1s2`.

   3. Test case: `toggle y1s2`<br>
      Expected: Same result as above — folder names are case-insensitive.

2. Switching to a non-existent folder

   1. Test case: `toggle nonexistent`<br>
      Expected: No switch occurs. Error message shown stating folder not found and to use `folder` to create a new one.

3. Switching with invalid names

   1. Test case: `toggle`<br>
      Expected: No switch occurs. Error message stating folder name cannot be empty.

   2. Test case: `toggle Y1 S2`<br>
      Expected: No switch occurs. Error message stating folder name can only contain letters, numbers, underscores, and hyphens.

### [Listing all folders](https://ay2526s2-cs2103t-f10-4.github.io/tp/UserGuide.html#listing-all-address-books--folders)

1. Listing folders when folders exist

   1. Prerequisites: Run `folder Y1S2` and `folder Y2S1` to create at least two folders.

   2. Test case: `folders`<br>
      Expected: All folder names in `data/` are listed alphabetically, including `addressbook`, `y1s2`, `y2s1`.

2. Listing folders when only the default exists

   1. Prerequisites: Fresh install with no additional folders created.

   2. Test case: `folders`<br>
      Expected: Only `addressbook` is listed.

### Saving data

1. Dealing with missing data files

   1. Manually change a status of an application in `[JAR file location]/data/addressbook.json` to `""`
      Expected: When user reopens the app, the status gets automatically updated to `"Interested"`

   2. Manually change other fields in `[JAR file location]/data/addressbook.json` to `""`
      Testcase: change name field to `""`
      Expected: OfferFlow would clear all the applications and start with an empty file

2. Dealing with corrupt data files

   1. Manually change fields in `[JAR file location]/data/addressbook.json` to invalid values
      Testcase: change phone number field to `12` (phone number must be atleast 3 digits)
      Expected: OfferFlow would clear all the applications and start with an empty file

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Effort**
