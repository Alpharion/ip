# Chud User Guide

Chud is a console chatbot that helps you track todos, deadlines, and events. Type a command and press Enter; Chud replies between two horizontal lines.

```
    ____________________________________________________________
  ____ _               _ 
 / ___| |__  _   _  __| |
| |   | '_ \| | | |/ _` |
| |___| | | | |_| | (_| |
 \____|_| |_|\__,_|\__,_|
     Hello! I'm Chud.
     What can I do for you?
    ____________________________________________________________
```

Your tasks are saved automatically to `./data/chud.txt` after every change, and reloaded the next time Chud starts, so nothing is lost between sessions.

## Adding a todo: `todo`

Adds a task with just a description -- no date or time attached.

Example: `todo borrow book`

```
     Got it. I've added this task:
       [T][ ] borrow book
     Now you have 1 tasks in the list.
```

## Adding a deadline: `deadline`

Adds a task with a description and a `/by` date/time. Chud understands several date formats and always shows the date back in a friendly form, regardless of which format you typed it in.

Accepted `/by` formats:

- `yyyy-mm-dd`, e.g. `2019-10-15`
- `yyyy-mm-dd HHmm` (24-hour time), e.g. `2019-10-15 1800`
- `d/m/yyyy`, e.g. `2/12/2019`
- `d/m/yyyy HHmm`, e.g. `2/12/2019 1800`

Example: `deadline return book /by 2/12/2019 1800`

```
     Got it. I've added this task:
       [D][ ] return book (by: Dec 02 2019, 6:00PM)
     Now you have 2 tasks in the list.
```

## Adding an event: `event`

Adds a task with a description and both a `/from` and a `/to` date/time, in that order. Both accept the same formats as a deadline's `/by`.

Example: `event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600`

```
     Got it. I've added this task:
       [E][ ] project meeting (from: Dec 02 2019, 2:00PM to: Dec 02 2019, 4:00PM)
     Now you have 3 tasks in the list.
```

## Listing all tasks: `list`

Shows every task currently in your list, numbered in the order they were added.

Example: `list`

```
     Here are the tasks in your list:
     1.[T][ ] borrow book
     2.[D][ ] return book (by: Dec 02 2019, 6:00PM)
     3.[E][ ] project meeting (from: Dec 02 2019, 2:00PM to: Dec 02 2019, 4:00PM)
```

## Listing tasks on a date: `on`

Shows only the deadlines and events that occur on a given date -- a deadline matches if its `/by` date matches, and an event matches if the date falls within its `/from`-`/to` range. Todos never appear (they have no date). Accepts the same date formats as `/by`/`/from`/`/to`; if a time is included, only the date part is used.

Example: `on 2019-12-02`

```
     Here are the tasks occurring on Dec 02 2019:
     1.[E][ ] project meeting (from: Dec 02 2019, 2:00PM to: Dec 02 2019, 4:00PM)
```

## Finding tasks by keyword: `find`

Shows only the tasks whose description contains the given keyword. The match is
case-insensitive and looks only at the description, not dates/times or task type.

Example: `find book`

```
     Here are the matching tasks in your list:
     1.[T][X] read book
     2.[D][X] return book (by: Jun 06 2019)
```

## Marking a task as done: `mark`

Marks the task at the given list number (as shown by `list`) as done.

Example: `mark 1`

```
     Nice! I've marked this task as done:
       [T][X] borrow book
```

## Marking a task as not done: `unmark`

Reverses the done status of the task at the given list number.

Example: `unmark 1`

```
     OK, I've marked this task as not done yet:
       [T][ ] borrow book
```

## Deleting a task: `delete`

Removes the task at the given list number from your list.

Example: `delete 2`

```
     Noted. I've removed this task:
       [D][ ] return book (by: Dec 02 2019, 6:00PM)
     Now you have 2 tasks in the list.
```

## Exiting: `bye`

Ends the session.

Example: `bye`

```
     Bye. Hope to see you again soon!
```

## Error handling

Chud checks every command and responds with a specific, correctable `OOPS!!!` message instead of crashing, for cases such as:

- An unrecognized command word.
- A `todo`, `deadline`, or `event` with a missing or empty description.
- A `deadline` missing its `/by`, or an `event` missing its `/from`/`/to` (or given out of order).
- A `/by`, `/from`, `/to`, or `on` date/time that isn't in one of the accepted formats.
- A `mark`, `unmark`, or `delete` with a missing, non-numeric, or out-of-range task number.
- A `find` with an empty keyword.

## Command summary

| Action | Format | Example |
|---|---|---|
| Add a todo | `todo DESCRIPTION` | `todo borrow book` |
| Add a deadline | `deadline DESCRIPTION /by DATE[ TIME]` | `deadline return book /by 2/12/2019 1800` |
| Add an event | `event DESCRIPTION /from DATE[ TIME] /to DATE[ TIME]` | `event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600` |
| List all tasks | `list` | `list` |
| List tasks on a date | `on DATE` | `on 2019-12-02` |
| Find tasks by keyword | `find KEYWORD` | `find book` |
| Mark a task done | `mark INDEX` | `mark 1` |
| Mark a task not done | `unmark INDEX` | `unmark 1` |
| Delete a task | `delete INDEX` | `delete 2` |
| Exit | `bye` | `bye` |
