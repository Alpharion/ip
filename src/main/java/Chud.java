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

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println(horizontalLine);
            System.out.println("     " + input);
            System.out.println(horizontalLine);
            input = scanner.nextLine();
        }

        System.out.println(horizontalLine);
        System.out.println("     Bye. Hope to see you again soon!");
        System.out.println(horizontalLine);
    }
}
