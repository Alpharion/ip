import java.time.LocalDate;
import java.util.ArrayList;

public class Chud {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        Storage storage = new Storage();
        ArrayList<Task> tasks = storage.load();

        String input = ui.readCommand();
        while (!input.equals("bye")) {
            ui.showLine();

            int spaceIndex = input.indexOf(' ');
            String commandWord = spaceIndex == -1 ? input : input.substring(0, spaceIndex);
            String arguments = spaceIndex == -1 ? "" : input.substring(spaceIndex + 1).trim();

            try {
                switch (Command.fromCommandWord(commandWord)) {
                case LIST:
                    ui.showMessage("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showMessage((i + 1) + "." + tasks.get(i));
                    }
                    break;
                case MARK: {
                    int taskIndex = parseTaskIndex(arguments, tasks.size(), "mark");
                    tasks.get(taskIndex).markAsDone();
                    storage.save(tasks);
                    ui.showMessage("Nice! I've marked this task as done:");
                    ui.showIndentedMessage(tasks.get(taskIndex));
                    break;
                }
                case UNMARK: {
                    int taskIndex = parseTaskIndex(arguments, tasks.size(), "unmark");
                    tasks.get(taskIndex).markAsNotDone();
                    storage.save(tasks);
                    ui.showMessage("OK, I've marked this task as not done yet:");
                    ui.showIndentedMessage(tasks.get(taskIndex));
                    break;
                }
                case DELETE: {
                    int taskIndex = parseTaskIndex(arguments, tasks.size(), "delete");
                    Task removedTask = tasks.remove(taskIndex);
                    storage.save(tasks);
                    ui.showMessage("Noted. I've removed this task:");
                    ui.showIndentedMessage(removedTask);
                    ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case TODO: {
                    if (arguments.isEmpty()) {
                        throw new ChudException("The description of a todo cannot be empty. Try: todo borrow book");
                    }
                    tasks.add(new Todo(arguments));
                    storage.save(tasks);
                    printTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case DEADLINE: {
                    int byIndex = arguments.indexOf("/by ");
                    if (byIndex == -1) {
                        throw new ChudException("A deadline needs a '/by' date/time. "
                                + "Try: deadline return book /by 2019-10-15 1800");
                    }
                    String description = arguments.substring(0, byIndex).trim();
                    String byText = arguments.substring(byIndex + 4).trim();
                    if (description.isEmpty()) {
                        throw new ChudException("The description of a deadline cannot be empty. "
                                + "Try: deadline return book /by 2019-10-15 1800");
                    }
                    if (byText.isEmpty()) {
                        throw new ChudException("The '/by' date/time of a deadline cannot be empty. "
                                + "Try: deadline return book /by 2019-10-15 1800");
                    }
                    TaskDateTime by;
                    try {
                        by = TaskDateTime.parse(byText);
                    } catch (IllegalArgumentException e) {
                        throw new ChudException(e.getMessage());
                    }
                    tasks.add(new Deadline(description, by));
                    storage.save(tasks);
                    printTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case EVENT: {
                    int fromIndex = arguments.indexOf("/from ");
                    int toIndex = arguments.indexOf("/to ");
                    if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                        throw new ChudException(
                                "An event needs both '/from' and '/to' date/times, in that order. "
                                        + "Try: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
                    }
                    String description = arguments.substring(0, fromIndex).trim();
                    String fromText = arguments.substring(fromIndex + 6, toIndex).trim();
                    String toText = arguments.substring(toIndex + 4).trim();
                    if (description.isEmpty()) {
                        throw new ChudException("The description of an event cannot be empty. "
                                + "Try: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
                    }
                    if (fromText.isEmpty() || toText.isEmpty()) {
                        throw new ChudException("The '/from' and '/to' date/times of an event cannot be empty. "
                                + "Try: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
                    }
                    TaskDateTime from;
                    TaskDateTime to;
                    try {
                        from = TaskDateTime.parse(fromText);
                        to = TaskDateTime.parse(toText);
                    } catch (IllegalArgumentException e) {
                        throw new ChudException(e.getMessage());
                    }
                    tasks.add(new Event(description, from, to));
                    storage.save(tasks);
                    printTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case ON: {
                    if (arguments.isEmpty()) {
                        throw new ChudException("Tell me which date, e.g. on 2019-10-15");
                    }
                    TaskDateTime queryDateTime;
                    try {
                        queryDateTime = TaskDateTime.parse(arguments);
                    } catch (IllegalArgumentException e) {
                        throw new ChudException(e.getMessage());
                    }
                    LocalDate date = queryDateTime.getDate();
                    ui.showMessage("Here are the tasks occurring on " + queryDateTime + ":");
                    int count = 0;
                    for (Task task : tasks) {
                        if (task.occursOn(date)) {
                            count++;
                            ui.showMessage(count + "." + task);
                        }
                    }
                    break;
                }
                case BYE:
                case UNKNOWN:
                default:
                    throw new ChudException("I don't know what '" + commandWord + "' means. "
                            + "Try list, todo, deadline, event, on, mark, unmark, delete, or bye.");
                }
            } catch (ChudException e) {
                ui.showError(e.getMessage());
            }

            ui.showLine();
            input = ui.readCommand();
        }

        ui.showGoodbye();
    }

    private static void printTaskAdded(Ui ui, Task task, int taskCount) {
        ui.showMessage("Got it. I've added this task:");
        ui.showIndentedMessage(task);
        ui.showMessage("Now you have " + taskCount + " tasks in the list.");
    }

    /**
     * Parses a 1-based task number typed by the user and returns the matching 0-based list index,
     * throwing a ChudException with a specific explanation for every way the input can be invalid.
     */
    private static int parseTaskIndex(String arguments, int taskCount, String commandWord) throws ChudException {
        if (arguments.isEmpty()) {
            throw new ChudException("Tell me which task number, e.g. " + commandWord + " 2");
        }
        int taskNumber;
        try {
            taskNumber = Integer.parseInt(arguments);
        } catch (NumberFormatException e) {
            throw new ChudException("'" + arguments + "' is not a valid task number.");
        }
        if (taskNumber < 1 || taskNumber > taskCount) {
            throw new ChudException("There is no task number " + taskNumber + ". "
                    + (taskCount == 0 ? "Your task list is empty." : "You have " + taskCount + " task(s)."));
        }
        return taskNumber - 1;
    }
}
