package amos.ui;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import amos.exceptions.AmosException;
import amos.exceptions.AmosTaskException;
import amos.exceptions.AmosTimeException;
import amos.exceptions.AmosUnfoundTaskException;
import amos.exceptions.AmosUnknownCommandException;
import amos.tasks.Deadline;
import amos.tasks.Event;
import amos.tasks.Task;
import amos.tasks.Todo;

public class Parser {
    public static CommandType getCommand(String cmd) throws AmosUnknownCommandException {
        try {
            return CommandType.valueOf(cmd.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AmosUnknownCommandException(cmd);
        }
    }

    private static LocalDateTime parseDateTime(String input) throws DateTimeParseException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return LocalDateTime.parse(input.trim(), formatter);
    }

    public static Task parseTodo(String des) throws AmosTaskException {
        if (des == null || des.trim().isEmpty()) {
            throw new AmosTaskException("todo");
        }
        return new Todo(des.trim());
    }

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

    public static int parseIndex(String str) throws AmosUnfoundTaskException {

        try {
            return Integer.parseInt(str.trim()) - 1;
        } catch (NumberFormatException e) {
            throw new AmosUnfoundTaskException();
        }
    }
}
