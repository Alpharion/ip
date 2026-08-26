package chud.task;

import java.time.LocalDate;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        isDone = true;
    }

    public void markAsNotDone() {
        isDone = false;
    }

    public abstract TaskType getTaskType();

    /**
     * Returns true if this task is scheduled to occur on the given date, for the {@code on}
     * command. A plain Todo has no date, so the default implementation returns false.
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }

    /**
     * Encodes this task as a single line for storage in the save file, e.g.
     * {@code "T | 1 | read book"}. Subclasses append their own extra fields.
     */
    public String toFileString() {
        return getTaskType().getTag() + " | " + (isDone ? "1" : "0") + " | " + description;
    }

    @Override
    public String toString() {
        return "[" + getTaskType().getTag() + "][" + getStatusIcon() + "] " + description;
    }
}
