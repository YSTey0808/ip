package amos.ui;

import static org.junit.jupiter.api.Assertions.*;

import amos.exceptions.*;
import amos.tasks.*;

import org.junit.jupiter.api.Test;

public class ParserTest {

    // 1. Test parseTodo
    @Test
    void testParseTodo_ValidAndEmpty() throws AmosTaskException {
        // Valid Todo
        Task todo = Parser.parseTodo("Read book");
        assertEquals("Read book", todo.getDescription());

        // Empty Todo should throw exception
        assertThrows(AmosTaskException.class, () -> Parser.parseTodo("   "));
    }

    // 2. Test parseDeadline
    @Test
    void testParseDeadline_ValidAndMissingBy() throws AmosException {
        // Valid Deadline
        Deadline deadline = Parser.parseDeadline("Submit report|By:05/09/2025 23:59");
        assertEquals("Submit report", deadline.getDescription());


        // Missing |By: should throw AmosTaskException
        assertThrows(AmosTaskException.class, () -> Parser.parseDeadline("Submit report"));
    }

    // 3. Test parseEvent
    @Test
    void testParseEvent_ValidAndInvalidTime() throws AmosException {
        // Valid Event
        Event event = Parser.parseEvent("Meeting|From:05/09/2025 10:00|To:05/09/2025 12:00");
        assertEquals("Meeting", event.getDescription());

        // Event with from after to should throw AmosTimeException
        assertThrows(AmosTimeException.class, () ->
                Parser.parseEvent("Meeting|From:05/09/2025 14:00|To:05/09/2025 12:00"));
    }


}
