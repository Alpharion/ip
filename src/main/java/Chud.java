import java.util.Scanner;

public class Chud {
    private static final int MAX_TASKS = 100;

    public static void main(String[] args) {
        String banner = "  ____ _               _ \n"
                + " / ___| |__  _   _  __| |\n"
                + "| |   | '_ \\| | | |/ _` |\n"
                + "| |___| | | | |_| | (_| |\n"
                + " \\____|_| |_|\\__,_|\\__,_|\n";
        String horizontalLine = "    ____________________________________________________________";

        System.out.println(horizontalLine);
        System.out.print(banner);
        System.out.println("     Hello! I'm Chud.");
        System.out.println("     What can I do for you?");
        System.out.println(horizontalLine);

        Task[] tasks = new Task[MAX_TASKS];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(horizontalLine);

            int spaceIndex = input.indexOf(' ');
            String commandWord = spaceIndex == -1 ? input : input.substring(0, spaceIndex);
            String arguments = spaceIndex == -1 ? "" : input.substring(spaceIndex + 1).trim();

            try {
                switch (commandWord) {
                case "list":
                    System.out.println("     Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println("     " + (i + 1) + "." + tasks[i]);
                    }
                    break;
                case "mark": {
                    int taskIndex = parseTaskIndex(arguments, taskCount);
                    tasks[taskIndex].markAsDone();
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + tasks[taskIndex]);
                    break;
                }
                case "unmark": {
                    int taskIndex = parseTaskIndex(arguments, taskCount);
                    tasks[taskIndex].markAsNotDone();
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + tasks[taskIndex]);
                    break;
                }
                case "todo": {
                    if (arguments.isEmpty()) {
                        throw new ChudException("The description of a todo cannot be empty. Try: todo borrow book");
                    }
                    checkListNotFull(taskCount);
                    tasks[taskCount] = new Todo(arguments);
                    taskCount++;
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                    break;
                }
                case "deadline": {
                    int byIndex = arguments.indexOf("/by ");
                    if (byIndex == -1) {
                        throw new ChudException("A deadline needs a '/by' date/time. Try: deadline return book /by Sunday");
                    }
                    String description = arguments.substring(0, byIndex).trim();
                    String by = arguments.substring(byIndex + 4).trim();
                    if (description.isEmpty()) {
                        throw new ChudException("The description of a deadline cannot be empty. Try: deadline return book /by Sunday");
                    }
                    if (by.isEmpty()) {
                        throw new ChudException("The '/by' date/time of a deadline cannot be empty. Try: deadline return book /by Sunday");
                    }
                    checkListNotFull(taskCount);
                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                    break;
                }
                case "event": {
                    int fromIndex = arguments.indexOf("/from ");
                    int toIndex = arguments.indexOf("/to ");
                    if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                        throw new ChudException(
                                "An event needs both '/from' and '/to' date/times, in that order. "
                                        + "Try: event project meeting /from Mon 2pm /to 4pm");
                    }
                    String description = arguments.substring(0, fromIndex).trim();
                    String from = arguments.substring(fromIndex + 6, toIndex).trim();
                    String to = arguments.substring(toIndex + 4).trim();
                    if (description.isEmpty()) {
                        throw new ChudException("The description of an event cannot be empty. "
                                + "Try: event project meeting /from Mon 2pm /to 4pm");
                    }
                    if (from.isEmpty() || to.isEmpty()) {
                        throw new ChudException("The '/from' and '/to' date/times of an event cannot be empty. "
                                + "Try: event project meeting /from Mon 2pm /to 4pm");
                    }
                    checkListNotFull(taskCount);
                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                    break;
                }
                default:
                    throw new ChudException("I don't know what '" + commandWord + "' means. "
                            + "Try list, todo, deadline, event, mark, unmark, or bye.");
                }
            } catch (ChudException e) {
                System.out.println("     OOPS!!! " + e.getMessage());
            }

            System.out.println(horizontalLine);
            input = scanner.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }

    private static void printTaskAdded(Task task, int taskCount) {
        System.out.println("     Got it. I've added this task:");
        System.out.println("       " + task);
        System.out.println("     Now you have " + taskCount + " tasks in the list.");
    }

    private static void checkListNotFull(int taskCount) throws ChudException {
        if (taskCount >= MAX_TASKS) {
            throw new ChudException("Your task list is full (" + MAX_TASKS + " tasks max). Please clear some tasks first.");
        }
    }

    /**
     * Parses a 1-based task number typed by the user and returns the matching 0-based array index,
     * throwing a ChudException with a specific explanation for every way the input can be invalid.
     */
    private static int parseTaskIndex(String arguments, int taskCount) throws ChudException {
        if (arguments.isEmpty()) {
            throw new ChudException("Tell me which task number, e.g. mark 2");
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
