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
     * save file doesn't exist yet (e.g. on first run). Blank lines are skipped silently; a line
     * that can't be parsed (e.g. the file was hand-edited into a corrupted state) is skipped
     * with a warning instead of crashing the whole program, and loading continues with the rest.
     */
    public ArrayList<Task> load() {
        ArrayList<Task> tasks = new ArrayList<>();
        Path filePath = Path.of(FILE_PATH);
        if (!Files.exists(filePath)) {
            return tasks;
        }
        try {
            List<String> lines = Files.readAllLines(filePath);
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                if (line.isBlank()) {
                    continue;
                }
                try {
                    tasks.add(parseTask(line));
                } catch (IllegalArgumentException e) {
                    System.out.println("     Warning: skipping corrupted line " + (i + 1)
                            + " in save file (" + e.getMessage() + ")");
                }
            }
        } catch (IOException e) {
            System.out.println("     Warning: could not load tasks from disk (" + e.getMessage() + ")");
        }
        return tasks;
    }

    /**
     * Parses one line of the save file (e.g. {@code "D | 0 | return book | Sunday"}) back
     * into the matching Task, throwing IllegalArgumentException with a specific reason if the
     * line is missing fields, has an invalid done flag, or has an unrecognized task type tag.
     */
    private Task parseTask(String line) {
        String[] fields = line.split(" \\| ");
        if (fields.length < 3) {
            throw new IllegalArgumentException("expected at least 3 fields, found " + fields.length);
        }
        String tag = fields[0];
        String doneFlag = fields[1];
        if (!doneFlag.equals("0") && !doneFlag.equals("1")) {
            throw new IllegalArgumentException("done flag must be '0' or '1', found '" + doneFlag + "'");
        }
        String description = fields[2];

        Task task;
        switch (tag) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            if (fields.length < 4) {
                throw new IllegalArgumentException("deadline is missing its '/by' field");
            }
            task = new Deadline(description, fields[3]);
            break;
        case "E":
            if (fields.length < 5) {
                throw new IllegalArgumentException("event is missing its '/from' or '/to' field");
            }
            task = new Event(description, fields[3], fields[4]);
            break;
        default:
            throw new IllegalArgumentException("unrecognized task type tag '" + tag + "'");
        }
        if (doneFlag.equals("1")) {
            task.markAsDone();
        }
        return task;
    }
}
