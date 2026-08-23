# UI Test Plan

This file drives the `test-ui` skill. Each test case lists the commands to
type at the program's prompt, in order, and the exact console session
transcript expected to result. The last input line of every test case must
be the command that makes the program exit (`bye`), so a full session can be
captured in one run.

## Setup

- **Source directory:** `src/main/java`
- **Main class:** `Chud`

## Test Case: Greet and exit

**Aim:** The program greets the user with its banner and name, then exits cleanly when told to.

**Input:**
```
bye
```

**Expected Output:**
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

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Add a todo

**Aim:** A `todo` command adds a task with no date/time and is acknowledged and counted correctly.

**Input:**
```
todo borrow book
bye
```

**Expected Output:**
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

todo borrow book
    ____________________________________________________________
     Got it. I've added this task:
       [T][ ] borrow book
     Now you have 1 tasks in the list.
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Add a deadline

**Aim:** A `deadline` command adds a task with a `/by` date/time, stored and displayed as free text.

**Input:**
```
deadline return book /by Sunday
bye
```

**Expected Output:**
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

deadline return book /by Sunday
    ____________________________________________________________
     Got it. I've added this task:
       [D][ ] return book (by: Sunday)
     Now you have 1 tasks in the list.
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Add an event

**Aim:** An `event` command adds a task with `/from` and `/to` date/times, stored and displayed as free text.

**Input:**
```
event project meeting /from Mon 2pm /to 4pm
bye
```

**Expected Output:**
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

event project meeting /from Mon 2pm /to 4pm
    ____________________________________________________________
     Got it. I've added this task:
       [E][ ] project meeting (from: Mon 2pm to: 4pm)
     Now you have 1 tasks in the list.
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Mark and unmark tasks

**Aim:** `mark` and `unmark` flip a task's done status, and `list` reflects the change immediately.

**Input:**
```
todo read book
todo return book
mark 1
list
unmark 1
list
bye
```

**Expected Output:**
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

todo read book
    ____________________________________________________________
     Got it. I've added this task:
       [T][ ] read book
     Now you have 1 tasks in the list.
    ____________________________________________________________

todo return book
    ____________________________________________________________
     Got it. I've added this task:
       [T][ ] return book
     Now you have 2 tasks in the list.
    ____________________________________________________________

mark 1
    ____________________________________________________________
     Nice! I've marked this task as done:
       [T][X] read book
    ____________________________________________________________

list
    ____________________________________________________________
     Here are the tasks in your list:
     1.[T][X] read book
     2.[T][ ] return book
    ____________________________________________________________

unmark 1
    ____________________________________________________________
     OK, I've marked this task as not done yet:
       [T][ ] read book
    ____________________________________________________________

list
    ____________________________________________________________
     Here are the tasks in your list:
     1.[T][ ] read book
     2.[T][ ] return book
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Unrecognized command

**Aim:** A command that isn't `list`, `mark`, `unmark`, `todo`, `deadline`, `event`, or `bye` gets a graceful, specific error instead of crashing or being silently added.

**Input:**
```
frobnicate
bye
```

**Expected Output:**
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

frobnicate
    ____________________________________________________________
     OOPS!!! I don't know what 'frobnicate' means. Try list, todo, deadline, event, mark, unmark, or bye.
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Empty todo description

**Aim:** A `todo` with no description is rejected with a specific error instead of adding a blank task.

**Input:**
```
todo
bye
```

**Expected Output:**
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

todo
    ____________________________________________________________
     OOPS!!! The description of a todo cannot be empty. Try: todo borrow book
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Deadline validation errors

**Aim:** A `deadline` missing its `/by` marker, or with an empty description or empty `/by` value, is rejected with a specific, correctable error instead of crashing.

**Input:**
```
deadline
deadline submit report
deadline /by Sunday
deadline submit report /by
bye
```

**Expected Output:**
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

deadline
    ____________________________________________________________
     OOPS!!! A deadline needs a '/by' date/time. Try: deadline return book /by Sunday
    ____________________________________________________________

deadline submit report
    ____________________________________________________________
     OOPS!!! A deadline needs a '/by' date/time. Try: deadline return book /by Sunday
    ____________________________________________________________

deadline /by Sunday
    ____________________________________________________________
     OOPS!!! The description of a deadline cannot be empty. Try: deadline return book /by Sunday
    ____________________________________________________________

deadline submit report /by
    ____________________________________________________________
     OOPS!!! A deadline needs a '/by' date/time. Try: deadline return book /by Sunday
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Event validation errors

**Aim:** An `event` missing its `/from`/`/to` markers, or with an empty description or empty `/from`/`/to` value, is rejected with a specific, correctable error instead of crashing.

**Input:**
```
event
event meeting /from Mon
event /from Mon /to 4pm
event meeting /from /to 4pm
bye
```

**Expected Output:**
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

event
    ____________________________________________________________
     OOPS!!! An event needs both '/from' and '/to' date/times, in that order. Try: event project meeting /from Mon 2pm /to 4pm
    ____________________________________________________________

event meeting /from Mon
    ____________________________________________________________
     OOPS!!! An event needs both '/from' and '/to' date/times, in that order. Try: event project meeting /from Mon 2pm /to 4pm
    ____________________________________________________________

event /from Mon /to 4pm
    ____________________________________________________________
     OOPS!!! The description of an event cannot be empty. Try: event project meeting /from Mon 2pm /to 4pm
    ____________________________________________________________

event meeting /from /to 4pm
    ____________________________________________________________
     OOPS!!! The '/from' and '/to' date/times of an event cannot be empty. Try: event project meeting /from Mon 2pm /to 4pm
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```

## Test Case: Mark and unmark validation errors

**Aim:** `mark`/`unmark` with a missing, non-numeric, or out-of-range task number is rejected with a specific error instead of crashing (this previously threw an uncaught exception for out-of-range and non-numeric input).

**Input:**
```
mark
mark abc
mark 5
todo do laundry
mark 0
unmark 99
bye
```

**Expected Output:**
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

mark
    ____________________________________________________________
     OOPS!!! Tell me which task number, e.g. mark 2
    ____________________________________________________________

mark abc
    ____________________________________________________________
     OOPS!!! 'abc' is not a valid task number.
    ____________________________________________________________

mark 5
    ____________________________________________________________
     OOPS!!! There is no task number 5. Your task list is empty.
    ____________________________________________________________

todo do laundry
    ____________________________________________________________
     Got it. I've added this task:
       [T][ ] do laundry
     Now you have 1 tasks in the list.
    ____________________________________________________________

mark 0
    ____________________________________________________________
     OOPS!!! There is no task number 0. You have 1 task(s).
    ____________________________________________________________

unmark 99
    ____________________________________________________________
     OOPS!!! There is no task number 99. You have 1 task(s).
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```
