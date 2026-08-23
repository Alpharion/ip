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
            if (input.equals("list")) {
                System.out.println("     Here are the tasks in your list:");
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + "." + tasks[i]);
                }
            } else if (input.startsWith("mark ")) {
                int taskNumber = Integer.parseInt(input.substring(5).trim());
                tasks[taskNumber - 1].markAsDone();
                System.out.println("     Nice! I've marked this task as done:");
                System.out.println("       " + tasks[taskNumber - 1]);
            } else if (input.startsWith("unmark ")) {
                int taskNumber = Integer.parseInt(input.substring(7).trim());
                tasks[taskNumber - 1].markAsNotDone();
                System.out.println("     OK, I've marked this task as not done yet:");
                System.out.println("       " + tasks[taskNumber - 1]);
            } else if (input.startsWith("todo ")) {
                String description = input.substring(5).trim();
                tasks[taskCount] = Task.createTodo(description);
                taskCount++;
                printTaskAdded(tasks[taskCount - 1], taskCount);
            } else if (input.startsWith("deadline ")) {
                String remainder = input.substring(9);
                int byIndex = remainder.indexOf("/by ");
                if (byIndex == -1) {
                    System.out.println("     A deadline needs a '/by' date/time, e.g. deadline return book /by Sunday");
                } else {
                    String description = remainder.substring(0, byIndex).trim();
                    String by = remainder.substring(byIndex + 4).trim();
                    tasks[taskCount] = Task.createDeadline(description, by);
                    taskCount++;
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                }
            } else if (input.startsWith("event ")) {
                String remainder = input.substring(6);
                int fromIndex = remainder.indexOf("/from ");
                int toIndex = remainder.indexOf("/to ");
                if (fromIndex == -1 || toIndex == -1 || toIndex < fromIndex) {
                    System.out.println("     An event needs '/from' and '/to' date/times, e.g. event project meeting /from Mon 2pm /to 4pm");
                } else {
                    String description = remainder.substring(0, fromIndex).trim();
                    String from = remainder.substring(fromIndex + 6, toIndex).trim();
                    String to = remainder.substring(toIndex + 4).trim();
                    tasks[taskCount] = Task.createEvent(description, from, to);
                    taskCount++;
                    printTaskAdded(tasks[taskCount - 1], taskCount);
                }
            } else {
                System.out.println("     I'm sorry, I don't know what that means :-(");
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
}
