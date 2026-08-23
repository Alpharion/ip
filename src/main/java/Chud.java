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

        String[] tasks = new String[MAX_TASKS];
        int taskCount = 0;

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(horizontalLine);
            if (input.equals("list")) {
                for (int i = 0; i < taskCount; i++) {
                    System.out.println("     " + (i + 1) + ". " + tasks[i]);
                }
            } else {
                tasks[taskCount] = input;
                taskCount++;
                System.out.println("     added: " + input);
            }
            System.out.println(horizontalLine);
            input = scanner.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
