package amos.tasks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import amos.exceptions.AmosException;


/**
 * Represents a task with a start and end time.
 *
 * <p>An Event task has a description, a start time, and an end time.</p>
 */
public class Event extends Task {
    protected static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    protected final LocalDateTime from;
    protected final LocalDateTime to;

    /**
     * Creates an Event task with a description, start time, and end time.
     *
     * @param des  the task description
     * @param from the start date/time
     * @param to   the end date/time
     * @throws AmosException if task creation fails
     */
    public Event(String des, LocalDateTime from, LocalDateTime to) throws AmosException {
        super(des);
        this.from = from;
        this.to = to;
    }

    @Override
    public String writeTxt() {
        return "E |" + super.writeTxt() + " |From:" + from.format(FORMATTER) + " |To: " + to.format(FORMATTER);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(From: " + from.format(FORMATTER) + " |To: " + to.format(FORMATTER) + ")";
    }

}
