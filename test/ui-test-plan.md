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

**Aim:** A command that isn't `list`, `mark`, `unmark`, `todo`, `deadline`, `event`, or `bye` gets a graceful error instead of crashing or being silently added.

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
     I'm sorry, I don't know what that means :-(
    ____________________________________________________________

bye
    ____________________________________________________________
     Bye. Hope to see you again soon!
    ____________________________________________________________
```
