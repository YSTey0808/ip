package amos.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import amos.exceptions.AmosTaskException;

/**
 * Represents a task with a deadline.
 *
 * <p>A Deadline task has a description and a due date/time.</p>
 */
public class Deadline extends Task {
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    protected final LocalDateTime by;

    /**
     * Creates a Deadline task with a description and a due date.
     *
     * @param des the task description
     * @param by  the due date/time
     * @throws AmosTaskException if task creation fails
     */
    public Deadline(String des, LocalDateTime by) throws AmosTaskException {
        super(des);
        this.by = by;
    }

    @Override
    public String writeTxt() {
        return "D |" + super.writeTxt() + " |By:" + by.format(FORMATTER);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (By: " + by.format(FORMATTER) + ")";
    }
}
