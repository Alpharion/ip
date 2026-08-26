import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Saves the task list to a fixed location on disk ({@code ./data/chud.txt}) so that
 * data survives between runs of the chatbot. Loading is not yet implemented.
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
}
