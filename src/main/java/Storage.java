import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Saves and loads the task list from a fixed location on disk ({@code ./data/chud.txt}) so
 * that data survives between runs of the chatbot.
 */
public class Storage {
    private static final String FILE_PATH = "./data/chud.txt";

    /**
     * Writes the given tasks to the save file, one per line, overwriting any previous contents.
     * Creates the parent {@code data} directory if it doesn't already exist.
     */
    public void save(ArrayList<Task> tasks) {
        try {
            Path filePath = Path.of(FILE_PATH);
            Files.createDirectories(filePath.getParent());
            try (FileWriter writer = new FileWriter(filePath.toFile())) {
                for (Task task : tasks) {
                    writer.write(task.toFileString() + System.lineSeparator());
                }
            }
        } catch (IOException e) {
            System.out.println("     Warning: could not save tasks to disk (" + e.getMessage() + ")");
        }
    }

    /**
     * Reads the save file and reconstructs the task list from it. Returns an empty list if the
     * save file doesn't exist yet (e.g. on first run).
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        Path filePath = Path.of(FILE_PATH);
        if (!Files.exists(filePath)) {
            return tasks;
        }
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (String line : lines) {
                Task task = parseTask(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("     Warning: could not load tasks from disk (" + e.getMessage() + ")");
        }
        return tasks;
    }

    /**
     * Parses one line of the save file (e.g. {@code "D | 0 | return book | Sunday"}) back
     * into the matching Task.
     */
    private Task parseTask(String line) {
        String[] fields = line.split(" \\| ");
        String tag = fields[0];
        boolean isDone = fields[1].equals("1");
        String description = fields[2];

        Task task;
        switch (tag) {
        case "D":
            task = new Deadline(description, fields[3]);
            break;
        case "E":
            task = new Event(description, fields[3], fields[4]);
            break;
        case "T":
        default:
            task = new Todo(description);
            break;
        }
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }
}
