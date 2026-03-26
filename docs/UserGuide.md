---
layout: default.md
  title: "User Guide"
  pageNav: 3
---

# OfferFlow User Guide

## 🎯 For the Internship-Hunting CS Student

Let's be honest applying for internships can be a nightmare. Between grinding LeetCode questions, projects and actually attending classes, tracking internship applications can itself become a full-time job.

Hence, **OfferFlow** was built by CS students by CS students as we understand the struggle. OfferFlow is a desktop app that helps you:
- Track every internship application without the spreadsheet and calender reminder chaos
- Update and moniter application statuses in seconds unlike spreadsheet which can be very time-consuming
- Keep all your opportunities organised with minimal overhead!

If you can type fast, OfferFlow will manage your internship pipeline faster than any GUI-focused application!

<!-- * Table of Contents -->

<page-nav-print />

---

## Quick start

1. Ensure you have Java `17` or above installed in your Computer.`<br>`
   **Mac users:** Ensure you have the precise JDK version prescribed [here](https://se-education.org/guides/tutorials/javaInstallationMac.html).

1. Download the latest `.jar` file from [here](https://github.com/AY2526S2-CS2103T-F10-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your OfferFlow.

1. Open a command terminal, `cd` into the folder you put the jar file in, and use the `java -jar OfferFlow.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)
5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.`<br>`
   Some example commands you can try:

   * `list` : Lists all internship applications.
   * `add n/Google p/96789012 e/google@gmail.com a/70 Pasir Panjang Rd, #03-71 d/2024-06-18 r/Backend Developer s/interviewed t/java` : Adds your internship application to Offerflow.
   * `delete 3` : Deletes the 3rd contact shown in the current list.
   * `clear` : Deletes all applications.
   * `exit` : Exits the app.
6. Refer to the [Features](#features) below for details of each command.

---

## Features

<box type="info" seamless>

**Notes about the command format:**`<br>`

* Words in `UPPER_CASE` are the parameters to be supplied by the user.`<br>`
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/Google`.
* Items in square brackets are optional.`<br>`
  e.g `n/NAME [t/TAG]` can be used as `n/Google t/java` or as `n/Google`.
* Items with `…` after them can be used multiple times including zero times.`<br>`
  e.g. `[t/TAG]…` can be used as ` ` (i.e. 0 times), `t/java`, `t/java t/React` etc.
* Parameters can be in any order.`<br>`
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.
* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.`<br>`
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.
* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.
  `</box>`

### Viewing help : `help`

Shows you a message explaining how to access the help page.

  ![help message](images/helpMessage.png)

Format: `help`


### Adding an internship application: `add`

Adds the internship application you have applied for, to help you keep track of all your applications.

<box type="warning" seamless>

**Caution:**
OfferFlow by default does not allow duplicate application with same name and role. Hence, if you choose to add application with duplicate name and role, you can choose whether or not to [overwrite](#overwrite-duplicate-application--overwrite) it (ie: replace the pre-existing application with the new application)
</box>

Format: `add n/NAME p/PHONE e/EMAIL a/ADDRESS d/DATE r/ROLE s/STATUS [t/TAG]...`

<box type="tip" seamless>

**Tip:** An application can have any number of tags (including 0)
</box>

#### Parameters
- `n/NAME` → Name of the company
- `p/PHONE` → company telephone number
- `e/EMAIL` → company email
- `a/ADDRESS` → company location
- `d/DATE` → date when you applied
- `r/ROLE` → job position applied for
- `s/STATUS` → application progress
- `t/TAGS` → optional fields

Notes:
* name and role is case insensitive
* Email must use `@` symbol
* Applied dates must use `YYYY-MM-DD`

#### Examples:
* `add n/Google p/96789012 e/google@gmail.com a/70 Pasir Panjang Rd, #03-71 d/2024-06-18 r/Backend Developer s/interviewed t/java`
* `add n/Google p/12345678 e/careers@google.com a/123 qSilicon Valley d/2025-06-01 r/Software Engineer s/pending`

#### Expected Outcome:

  ![Output](images/AddCommand.png)


### Overwrite duplicate application : `overwrite`

<box type="warning" seamless>

**Caution:**
OfferFlow by default does not allow duplicate application with same name and role. Hence, if you choose to add application with duplicate name and role, you can choose whether or not to overwrite it (ie: replace the pre-existing application with the new application)
</box>

Overwrites pre-existing application in OfferFlow that has the same name and role, with the new application when you try to add an application with the same name and role as another already existing application

  ![DuplicateApplication](images/DuplicateApplication.png)

Format: `overwrite`

If you choose to overwrite, type `overwrite`. If not, continue using the app as per normal and the new duplication application you were trying to add will be automatically discarded

#### Expected Outcome (overwrite):

  ![Overwrite](images/Overwrite.png)


### Listing all internship applications : `list`

Shows a list of all the internship applications you have added on OfferFlow.

Format: `list`

#### Expected Outcome:

  ![List](images/List.png)

### Editing an application : `editmode`

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [t/TAG]…`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the person will be removed i.e adding of tags is not cumulative.
* You can remove all the person’s tags by typing `t/` without
  specifying any tags after it.

Examples:

* `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
* `edit 2 n/Betsy Crower t/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags.
Edits an existing application in OfferFlow by entering editing mode with `editmode`

Format:
1. `editmode INDEX` or `editmode n/NAME r/ROLE` to enter edit mode to edit that particular application
2. possible editing commands
    `n/NAME`
    `r/ROLE`
    `p/PHONE`
    `e/EMAIL`
    `a/ADDRESS`
    `s/STATUS`
    `d/DATE`
    `t/TAG`
3. `exitedit` to finish editing and exit the editing mode

* Edits the application at the specified `INDEX`. The index refers to the index number shown in the displayed application list. The index **must be a positive integer** 1, 2, 3, … or specify a particular application using `NAME` and `ROLE`​
* Existing values will be updated to the input values.
* When editing tags, the existing tags of the application will be removed i.e adding of tags is not cumulative.
* You can remove all the application’s tags by typing `t/` without
    specifying any tags after it.

### Examples:
* `editmode 1` or `editmode n/Google r/Software Engineer`
* `a/Mapletree Business City II, Pasir Panjang area`
* `exitedit`

### Expected outcome:

  ![Edit](images/Edit.png)

### Modifying Reminders: `Reminder`

Use `editmode` command to modify or create new Reminders.

Format: `u/DESCRIPTION ud/DATE`
Example: `u/upcoming interview ud/2026-12-12`

* Creates a new upcoming interview Reminder on 12 December 2026.

Use `rmr` command **outside** of `editmode` to remove Reminder of specified Applciation.

Format: `rmr <INDEX>` or `rmr n/<Company_Name> r/<Role>`
Example: `rmr 1` or `rmr n/Google r/software engineer`

* Removes reminder associated to the specified Application.
* `INDEX`: Application index reflected on list.
* `<Company_Name> <Role>`: Application that represents an Application to `<Company>` for `<Role>`.

### Updating application status: `status`

Updates the status of an **existing** application.

Format: `status n/COMPANY_NAME r/JOB_ROLE s/STATUS`

<box type="tip" seamless>

**Tip:** Status is case-insensitive (e.g. `applied`, `Applied`, `APPLIED` all work)

</box>

#### Parameters

- `n/COMPANY_NAME` → Name of the company
- `r/JOB_ROLE` → Role applied for (must match exactly)
- `s/STATUS` → New status

#### Valid Statuses
| Status | When to use |
|:-------|:------------|
| **Interested** | Found the role, planning to apply |
| **Applied** | Submitted your application |
| **Interviewing** | Interviews scheduled (congrats, halfway there!) |
| **Rejected** | Didn't get it (we've all been there) |
| **Offered** | 🎉 You got the offer! |

#### Examples

* `status n/Tiktok r/Data Analyst s/Applied`
* `status n/Google r/Software Engineer s/Interviewing`
* `status n/Meta r/ML Engineer s/Rejected`

#### Expected Outcome

  ![UpdateStatus](images/Update_Status.png)

<box type="warning" seamless>

**Caution:**
- Both **company name and role must match exactly**
- Command will fail if:
  - missing parameters
  - invalid format
  - application does not exist
</box>

### Locating applications by the company name: `find`

Helps you finds applications whose company names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

#### Parameters
- `KEYWORD` → Name of the company
- `[MORE_KEYWORD}` → Name of the company

* The search is case-insensitive. e.g `google` will match `Google`
* The order of the keywords does not matter. e.g. `Google Meta` will match `Meta Google`
* Only the company name is searched.
* Only full words will be matched e.g. `Goog` will not match `Google`
* Applications matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Google Meta` will return `Google`, `Meta`

#### Examples
* `find Meta` returns all applications applied to `Meta`
* `find Meta Google` returns all applications applied to `Meta` and `Google`<br>

#### Expected Outcome:

  ![result for 'find Google Meta'](images/Find.png)

#### Examples
* `find Meta` returns all applications applied to `Meta`
* `find Meta Google` returns all applications applied to `Meta` and `Google`<br>

#### Expected Outcome:

  ![result for 'find Google Meta'](images/Find.png)

### Filtering applications: `filter`

Filters applications by company, applied date, status, or tag.

Format:
* `filter /n /KEYWORD`
* `filter /d /YYYY-MM-DD`
* `filter /s /STATUS`
* `filter /t /TAG`

#### Parameters
- `KEYWORD` → Name of the company
- `YYYY-MM-DD` → date
- `STATUS` → application progress
- `TAG` → tags in the application

Notes:
* Filter matching is case-insensitive.
* Leading and trailing spaces are ignored.
* Internal spacing still matters.
* Applied dates must use `YYYY-MM-DD`.

Examples:
* `filter /n /Google`
* `filter /d /2025-11-11`
* `filter /s /Applied`
* `filter /t /java`

#### Expected Outcome

  ![Filter](images/Filter.png)


### Deleting an application : `delete`

You can delete a specified application from OfferFlow via index or reference via Company name and Role.

Format: `delete INDEX`

* Deletes the Application at the specified `INDEX`.

#### Parameters
- `INDEX` → the index number shown in the displayed application list
<box type="warning" seamless>

**Caution:**
* The index **must be a positive integer** 1, 2, 3, …​
</box>

#### Examples
* `delete 2` deletes the application displayed at index 2

#### Expected Outcome:

  ![Delete_index](images/Index.png)

Format: `delete n/NAME r/ROLE`

* Deletes the specific Name and Role Application

#### Parameters
- `NAME` → company name
- `ROLE` → job position

#### Examples
* `delete n/google r/Backend Developer` deletes the application for Google as Backend Developer.

#### Expected Outcome:

  ![Delete_ref](images/Delete_ref.png)

### Creating a new address book : `folder`

Creates a new empty address book saved under `data/FOLDER_NAME.json` and switches to it.

Format: `folder FOLDER_NAME`

#### Parameters
- `FOLDER_NAME` → Name for the new address book (letters, numbers, underscores, and hyphens only)

<box type="warning" seamless>

**Caution:**
- Folder name cannot be empty
- Folder name cannot contain spaces or special characters (e.g. `@`, `.`)
</box>

#### Examples
* `folder Y1S2` creates and switches to a new address book at `data/Y1S2.json`
* `folder internships-2025` creates and switches to `data/internships-2025.json`

#### Expected Outcome:
- A new empty address book is created and you are switched to it immediately.

---

### Switching to an existing address book : `toggle`

Switches to an existing address book saved under `data/FOLDER_NAME.json`.

Format: `toggle FOLDER_NAME`

#### Parameters
- `FOLDER_NAME` → Name of the existing address book to switch to

<box type="warning" seamless>

**Caution:**
- Folder name cannot be empty
- Folder name cannot contain spaces or special characters
- The address book file must already exist at `data/FOLDER_NAME.json`
</box>

#### Examples
* `toggle Y1S2` switches to the address book at `data/Y1S2.json`
* `toggle internships-2025` switches to `data/internships-2025.json`

#### Expected Outcome:
- You are switched to the specified address book and its applications are loaded.

---

### Clearing all entries : `clear`

⚠️ Removes **ALL** applications. Use with caution.

You can delete all applications on OfferFlow with just 1 command hassle-free!

Format: `clear`

### Exiting the program : `exit`

Closes OfferFlow.

Format: `exit`

### Saving the data

OfferFlow data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

OfferFlow data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, OfferFlow will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause OfferFlow to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box>

### Archiving data files `[coming in v2.0]`

Export past applications into different folder to declutter your active list.

---

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous OfferFlow home folder.

---

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

---

## Glossary

1. **GUI:** Graphical User Interface (GUI) is a visual way to interact with a computer, using icons, menus, and pointers rather than typed commands.

--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action | Format | Example |
|:-------|:-------|:--------|
| **Add** | `add n/NAME p/PHONE e/EMAIL a/ADDRESS d/DATE r/ROLE s/STATUS [t/TAG]...` | `add n/Google p/96789012 e/google@gmail.com a/70 Pasir Panjang Rd, #03-71 d/2024-06-18 r/Backend Developer s/interviewed t/java` |
| **Overwrite** | `overwrite` | `overwrite` |
| **Clear** | `clear` | `clear` |
| **Delete** | `delete INDEX` or `delete n/NAME r/ROLE` | `delete 3` or `delete n/Google r/Backend Developer` |
| **Edit** | `editmode INDEX` or `editmode n/NAME r/ROLE` | `editmode 1` then `a/Mapletree Business City II, Pasir Panjang area` then `exitedit`  |
| **Status** | `status n/COMPANY r/ROLE s/STATUS` | `status n/Tiktok r/Data Analyst s/Rejected` |
| **Find** | `find KEYWORD [MORE_KEYWORDS]` | `find James Jake` |
| **Filter** | `filter /n /KEYWORD` or `filter /d /YYYY-MM-DD` or `filter /s /STATUS` or `filter /t /TAG` | `filter /n /Tiktok` or `filter /d /2024-06-18` or `filter /s /Rejected` or `filter /t /java` |
| **Folder** | `folder FOLDER_NAME` | `folder Y1S2` |
| **Toggle** | `toggle FOLDER_NAME` | `toggle Y1S2` |
| **List** | `list` | `list` |
| **Help** | `help` | `help` |
