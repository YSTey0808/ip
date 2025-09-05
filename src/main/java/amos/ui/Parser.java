package amos.ui;

import amos.exceptions.*;
import amos.tasks.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * A class for parsing user input into commands and tasks.
 *
 * <p>Methods in this class can convert user input into:
 * <ul>
 *   <li>{@link CommandType}</li>
 *   <li>{@link Todo}</li>
 *   <li>{@link Event}</li>
 *   <li>{@link Deadline}</li>
 * </ul>
 * It also validates input and throws the appropriate exceptions.
 * </p>
 */
public class Parser {

    /**
     * Converts a string command into a {@link CommandType}.
     *
     * @param cmd the command string
     * @return the corresponding {@link CommandType}
     * @throws AmosUnknownCommandException if the command is not recognized
     */
    public static CommandType getCommand(String cmd) throws AmosUnknownCommandException {
        try {
            return  CommandType.valueOf(cmd.toUpperCase());
        } catch (IllegalArgumentException  e){
            throw new AmosUnknownCommandException(cmd);
        }
    }

    private static LocalDateTime parseDateTime(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(input.trim(), formatter);
    }

    /**
     * Creates a {@link Todo} task from a description.
     *
     * @param des the task description
     * @return a {@link Todo} task
     * @throws AmosTaskException if the description is empty
     */
    public static Task parseTodo(String des) throws AmosTaskException {
        if (des == null || des.trim().isEmpty()) {
            throw new AmosTaskException("todo");
        }
        return new Todo(des.trim());
    }

    /**
     * Creates an {@link Event} task from a description with start and end times.
     *
     * @param des the event string in "description|From:dd/MM/yyyy HH:mm|To:dd/MM/yyyy HH:mm" format
     * @return an {@link Event} task
     * @throws AmosException if the description is empty, required fields are missing, or start time is after end time
     */
    public static Event parseEvent(String des) throws AmosException {
        if (des == null || des.trim().isEmpty()) {
            throw new AmosTaskException("event");
        }
        int fromIndex = des.indexOf("|From:");
        int toIndex = des.indexOf("|To:");
        if (fromIndex == -1 || toIndex == -1) {
            throw new AmosTaskException("event");
        }

        String description = des.substring(0, fromIndex).trim();
        LocalDateTime from = parseDateTime(des.substring(fromIndex + 6, toIndex));
        LocalDateTime to = parseDateTime(des.substring(toIndex + 4));

        if (from.isAfter(to)) {
            throw new AmosTimeException();
        }

        return new Event(description, from, to);
    }

    /**
     * Creates a {@link Deadline} task from a description with a due date.
     *
     * @param des the deadline string in "description|By:dd/MM/yyyy HH:mm" format
     * @return a {@link Deadline} task
     * @throws AmosException if the description is empty or the "|By:" field is missing
     */
    public static Deadline parseDeadline(String des) throws AmosException {
        if (des == null || des.trim().isEmpty()) {
            throw new AmosTaskException("deadline");
        }

        int byIndex = des.indexOf("|By:");
        if (byIndex == -1) {
            throw new AmosTaskException("deadline");
        }

        String description = des.substring(0, byIndex).trim();
        LocalDateTime by = parseDateTime(des.substring(byIndex + 4));

        return new Deadline(description, by);
    }

    /**
     * Converts a string to a 0-based task index.
     *
     * @param str the string representing a 1-based index
     * @return the 0-based index
     * @throws AmosUnfoundTaskException if the string is not a valid number
     */
    public static int parseIndex(String str) throws AmosUnfoundTaskException {

        try {
            return Integer.parseInt(str.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new AmosUnfoundTaskException();
        }
    }
}
