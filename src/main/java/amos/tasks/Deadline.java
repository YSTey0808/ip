package amos.tasks;

import amos.exceptions.AmosTaskException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline.
 *
 * <p>A Deadline task has a description and a due date/time.</p>
 */
public class Deadline extends Task{
    protected final LocalDateTime by;
    protected final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    /**
     * Creates a Deadline task with a description and a due date.
     *
     * @param des the task description
     * @param by the due date/time
     * @throws AmosTaskException if task creation fails
     */
    public Deadline(String des,LocalDateTime by) throws AmosTaskException {
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
