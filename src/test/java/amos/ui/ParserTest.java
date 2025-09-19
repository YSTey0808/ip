package amos.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import amos.exceptions.AmosException;
import amos.exceptions.AmosTaskException;
import amos.exceptions.AmosTimeException;
import amos.exceptions.AmosUnfoundedTaskException;
import amos.tasks.Deadline;
import amos.tasks.Event;
import amos.tasks.Task;

import java.time.LocalDateTime;

public class ParserTest {

    // 1. Test parseTodo
    @Test
    void testParseTodoValidAndEmpty() throws AmosTaskException {
        // Valid Todo
        Task todo = Parser.parseTodo("Read book");
        assertEquals("Read book", todo.getDescription());

        // Empty Todo should throw exception
        assertThrows(AmosTaskException.class, () -> Parser.parseTodo("   "));
    }

    // 2. Test parseDeadline
    @Test
    void testParseDeadlineValidAndMissingBy() throws AmosException {
        // Valid Deadline
        Deadline deadline = Parser.parseDeadline("Submit report|By:05/09/2025 23:59");
        assertEquals("Submit report", deadline.getDescription());
        assertEquals(LocalDateTime.of(2025, 9, 5, 23, 59), deadline.getBy());

        // Missing |By: should throw AmosTaskException
        assertThrows(AmosTaskException.class, () -> Parser.parseDeadline("Submit report"));
    }

    // 3. Test parseEvent
    @Test
    void testParseEventValidAndInvalidTime() throws AmosException {
        // Valid Event
        Event event = Parser.parseEvent("Meeting|From:05/09/2025 10:00|To:05/09/2025 12:00");
        assertEquals("Meeting", event.getDescription());
        assertEquals(LocalDateTime.of(2025, 9, 5, 10, 0), event.getFrom());
        assertEquals(LocalDateTime.of(2025, 9, 5, 12, 0), event.getTo());

        // Event with from after to should throw AmosTimeException
        assertThrows(AmosTimeException.class, () ->
                Parser.parseEvent("Meeting|From:05/09/2025 14:00|To:05/09/2025 12:00"));

        // Missing |From: or |To: should throw AmosTaskException
        assertThrows(AmosTaskException.class, () -> Parser.parseEvent("Meeting|From:05/09/2025 10:00"));
        assertThrows(AmosTaskException.class, () -> Parser.parseEvent("Meeting|To:05/09/2025 12:00"));
    }


    // 4. Test parseTodo trims spaces
    @Test
    void testParseTodoTrims() throws AmosTaskException {
        Task todo = Parser.parseTodo("   Read book   ");
        assertEquals("Read book", todo.getDescription());
    }

    // 5. Test parseDeadline trims spaces
    @Test
    void testParseDeadlineTrims() throws AmosException {
        Deadline deadline = Parser.parseDeadline("  Submit report  |By:05/09/2025 23:59  ");
        assertEquals("Submit report", deadline.getDescription());
        assertEquals(LocalDateTime.of(2025, 9, 5, 23, 59), deadline.getBy());
    }

    // 6. Test parseEvent trims spaces
    @Test
    void testParseEventTrims() throws AmosException {
        Event event = Parser.parseEvent("  Meeting  |From:05/09/2025 10:00|To:05/09/2025 12:00 ");
        assertEquals("Meeting", event.getDescription());
        assertEquals(LocalDateTime.of(2025, 9, 5, 10, 0), event.getFrom());
        assertEquals(LocalDateTime.of(2025, 9, 5, 12, 0), event.getTo());
    }
}
