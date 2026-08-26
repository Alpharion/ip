import java.time.LocalDate;

public class Chud {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();

        Storage storage = new Storage("data/chud.txt");
        TaskList tasks = new TaskList(storage.load());

        String input = ui.readCommand();
        while (!input.equals("bye")) {
            ui.showLine();

            String commandWord = Parser.parseCommandWord(input);
            String arguments = Parser.parseArguments(input);

            try {
                switch (CommandWord.fromCommandWord(commandWord)) {
                case LIST:
                    ui.showMessage("Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        ui.showMessage((i + 1) + "." + tasks.get(i));
                    }
                    break;
                case MARK: {
                    int taskIndex = Parser.parseTaskIndex(arguments, tasks.size(), "mark");
                    tasks.get(taskIndex).markAsDone();
                    storage.save(tasks);
                    ui.showMessage("Nice! I've marked this task as done:");
                    ui.showIndentedMessage(tasks.get(taskIndex));
                    break;
                }
                case UNMARK: {
                    int taskIndex = Parser.parseTaskIndex(arguments, tasks.size(), "unmark");
                    tasks.get(taskIndex).markAsNotDone();
                    storage.save(tasks);
                    ui.showMessage("OK, I've marked this task as not done yet:");
                    ui.showIndentedMessage(tasks.get(taskIndex));
                    break;
                }
                case DELETE: {
                    int taskIndex = Parser.parseTaskIndex(arguments, tasks.size(), "delete");
                    Task removedTask = tasks.remove(taskIndex);
                    storage.save(tasks);
                    ui.showMessage("Noted. I've removed this task:");
                    ui.showIndentedMessage(removedTask);
                    ui.showMessage("Now you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case TODO: {
                    String description = Parser.parseTodoDescription(arguments);
                    tasks.add(new Todo(description));
                    storage.save(tasks);
                    printTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case DEADLINE: {
                    Parser.DeadlineArgs deadlineArgs = Parser.parseDeadlineArgs(arguments);
                    tasks.add(new Deadline(deadlineArgs.description, deadlineArgs.by));
                    storage.save(tasks);
                    printTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case EVENT: {
                    Parser.EventArgs eventArgs = Parser.parseEventArgs(arguments);
                    tasks.add(new Event(eventArgs.description, eventArgs.from, eventArgs.to));
                    storage.save(tasks);
                    printTaskAdded(ui, tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case ON: {
                    TaskDateTime queryDateTime = Parser.parseOnDate(arguments);
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
}
