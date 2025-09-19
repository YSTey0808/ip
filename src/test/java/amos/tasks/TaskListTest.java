package amos.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import amos.exceptions.AmosDuplicateException;
import amos.exceptions.AmosException;
import amos.exceptions.AmosTaskException;

public class TaskListTest {

    @Test
    public void testAddAndGetTask() throws AmosDuplicateException {
        TaskList list = new TaskList();
        Task t1 = new Todo("Read book");
        Task t2 = new Todo("CS2103T");
        list.add(t1);
        list.add(t2);
        assertEquals(t1, list.get(0));
        assertEquals(2, list.size());
    }

    @Test
    public void testDeleteTask() throws AmosDuplicateException {
        TaskList list = new TaskList();
        Task t1 = new Todo("Read book");
        list.add(t1);
        list.delete(0);
        assertEquals(0, list.size());
    }

    @Test
    public void testAddDuplicateTodo() throws AmosException {
        TaskList list = new TaskList();
        Task t1 = new Todo("Read book");
        Task t2 = new Todo("Read book");
        list.add(t1);
        assertThrows(AmosDuplicateException.class, () -> list.add(t2));
    }

    @Test
    public void testAddDeadline() throws AmosException {
        TaskList list = new TaskList();
        LocalDateTime by = LocalDateTime.of(2025, 9, 19, 23, 59);
        Task d1 = new Deadline("Submit assignment", by);
        list.add(d1);
        assertEquals(d1, list.get(0));
    }

    @Test
    public void testAddDuplicateDeadline() throws AmosDuplicateException, AmosTaskException {
        TaskList list = new TaskList();
        LocalDateTime by = LocalDateTime.of(2025, 9, 19, 23, 59);
        Task d1 = new Deadline("Submit assignment", by);
        Task d2 = new Deadline("Submit assignment", by);
        list.add(d1);
        assertThrows(AmosDuplicateException.class, () -> list.add(d2));
    }

    @Test
    public void testAddEvent() throws AmosException {
        TaskList list = new TaskList();
        LocalDateTime from = LocalDateTime.of(2025, 9, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 20, 12, 0);
        Task e1 = new Event("Team meeting", from, to);
        list.add(e1);
        assertEquals(e1, list.get(0));
    }

    @Test
    public void testAddDuplicateEvent() throws AmosException {
        TaskList list = new TaskList();
        LocalDateTime from = LocalDateTime.of(2025, 9, 20, 10, 0);
        LocalDateTime to = LocalDateTime.of(2025, 9, 20, 12, 0);
        Task e1 = new Event("Team meeting", from, to);
        Task e2 = new Event("Team meeting", from, to);
        list.add(e1);
        assertThrows(AmosDuplicateException.class, () -> list.add(e2));
    }
}
