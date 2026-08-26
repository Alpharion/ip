import java.time.LocalDate;

public class Deadline extends Task {
    protected TaskDateTime by;

    public Deadline(String description, TaskDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public TaskType getTaskType() {
        return TaskType.DEADLINE;
    }

    @Override
    public boolean occursOn(LocalDate date) {
        return by.getDate().equals(date);
    }

    @Override
    public String toString() {
        return super.toString() + " (by: " + by + ")";
    }

    @Override
    public String toFileString() {
        return super.toFileString() + " | " + by.toStorageString();
    }
}
