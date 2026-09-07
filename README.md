# Chud project template

This is a project template for a greenfield Java project. Given below are instructions on how to use it.

## Setting up in Intellij

Prerequisites: JDK 25, update Intellij to the most recent version.

1. Open Intellij (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project first)
1. Open the project into Intellij as follows:
   1. Click `Open`.
   1. Select the project directory, and click `OK`.
   1. If there are any further prompts, accept the defaults.
1. Configure the project to use **JDK 25** (not other versions) as explained in [here](https://www.jetbrains.com/help/idea/sdk.html#set-up-jdk).<br>
   In the same dialog, set the **Project language level** field to the `SDK default` option.
1. After that, locate the `src/main/java/chud/Chud.java` file, right-click it, and choose `Run Chud.main()` (if the code editor is showing compile errors, try restarting the IDE). If the setup is correct, you should see something like the below as the output:
   ```
     ____ _               _ 
    / ___| |__  _   _  __| |
   | |   | '_ \| | | |/ _` |
   | |___| | | | |_| | (_| |
    \____|_| |_|\__,_|\__,_|
   ```

**Warning:** Keep the `src\main\java` folder as the root folder for Java files (i.e., don't rename those folders or move Java files to another folder outside of this folder path), as this is the default location some tools (e.g., Gradle) expect to find Java files.

## Running the app

Chud has both a JavaFX GUI (the normal way to use it) and a plain console mode (used for
automated testing). Any of the following starts the GUI:

- **From IntelliJ:** locate `src/main/java/chud/gui/Launcher.java`, right-click it, and choose
  `Run 'Launcher.main()'`. (Run `Launcher`, not `Main` -- JavaFX refuses to launch an
  `Application` subclass directly when it's the entry point on the classpath.)
- **From the command line, via Gradle:**
  ```
  ./gradlew run
  ```
- **From a standalone JAR** (doesn't need Gradle or a separate JavaFX install -- everything is
  bundled in):
  ```
  ./gradlew shadowJar
  java -jar build/libs/chud.jar
  ```

To run the console version instead (e.g. for scripted testing), run
`src/main/java/chud/Chud.java`'s `main()` directly, either from IntelliJ or with
`java -cp build/classes/java/main chud.Chud` after `./gradlew compileJava`.

## Using Chud

Chud tracks three kinds of tasks -- todos, deadlines, and events -- typed as one-line commands
into the chat box (or the console). Task numbers referenced by `mark`/`unmark`/`delete` are the
1-based position shown by `list`.

| Command | Example | What it does |
|---|---|---|
| `todo DESCRIPTION` | `todo borrow book` | Adds a todo. |
| `deadline DESCRIPTION /by WHEN` | `deadline return book /by 2019-12-02` | Adds a deadline. `WHEN` accepts `yyyy-MM-dd`, `d/M/yyyy`, or either with a trailing 24-hour time, e.g. `2019-12-02 1800`. |
| `event DESCRIPTION /from WHEN /to WHEN` | `event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600` | Adds an event spanning a start and end date/time. |
| `list` | `list` | Shows every task, numbered, with its done status. |
| `mark INDEX` | `mark 2` | Marks task 2 as done. |
| `unmark INDEX` | `unmark 2` | Marks task 2 as not done. |
| `delete INDEX` | `delete 2` | Removes task 2 from the list. |
| `find KEYWORD` | `find book` | Lists tasks whose description contains `KEYWORD` (case-insensitive). |
| `on WHEN` | `on 2019-12-02` | Lists deadlines due, and events occurring, on that date. |
| `bye` | `bye` | Exits Chud. |

Tasks are saved automatically to `data/chud.txt` after every change and reloaded the next time
Chud starts, so nothing is lost between sessions.
