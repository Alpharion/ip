import java.util.ArrayList;
import java.util.Scanner;

public class Chud {
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

        ArrayList<Task> tasks = new ArrayList<>();

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(horizontalLine);

            int spaceIndex = input.indexOf(' ');
            String commandWord = spaceIndex == -1 ? input : input.substring(0, spaceIndex);
            String arguments = spaceIndex == -1 ? "" : input.substring(spaceIndex + 1).trim();

            try {
                switch (Command.fromCommandWord(commandWord)) {
                case LIST:
                    System.out.println("     Here are the tasks in your list:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println("     " + (i + 1) + "." + tasks.get(i));
                    }
                    break;
                case MARK: {
                    int taskIndex = parseTaskIndex(arguments, tasks.size(), "mark");
                    tasks.get(taskIndex).markAsDone();
                    System.out.println("     Nice! I've marked this task as done:");
                    System.out.println("       " + tasks.get(taskIndex));
                    break;
                }
                case UNMARK: {
                    int taskIndex = parseTaskIndex(arguments, tasks.size(), "unmark");
                    tasks.get(taskIndex).markAsNotDone();
                    System.out.println("     OK, I've marked this task as not done yet:");
                    System.out.println("       " + tasks.get(taskIndex));
                    break;
                }
                case DELETE: {
                    int taskIndex = parseTaskIndex(arguments, tasks.size(), "delete");
                    Task removedTask = tasks.remove(taskIndex);
                    System.out.println("     Noted. I've removed this task:");
                    System.out.println("       " + removedTask);
                    System.out.println("     Now you have " + tasks.size() + " tasks in the list.");
                    break;
                }
                case TODO: {
                    if (arguments.isEmpty()) {
                        throw new ChudException("The description of a todo cannot be empty. Try: todo borrow book");
                    }
                    tasks.add(new Todo(arguments));
                    printTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case DEADLINE: {
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
                    tasks.add(new Deadline(description, by));
                    printTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case EVENT: {
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
                    tasks.add(new Event(description, from, to));
                    printTaskAdded(tasks.get(tasks.size() - 1), tasks.size());
                    break;
                }
                case BYE:
                case UNKNOWN:
                default:
                    throw new ChudException("I don't know what '" + commandWord + "' means. "
                            + "Try list, todo, deadline, event, mark, unmark, delete, or bye.");
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
