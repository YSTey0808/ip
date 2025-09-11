package amos.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TaskListTest {
    @Test
    public void testAddAndGetTask() {
        TaskList list = new TaskList();
        Task t1 = new Todo("Read book");
        Task t2 = new Todo("CS2103T");
        list.add(t1);
        list.add(t2);
        assertEquals(t1, list.get(0));
        assertEquals(2, list.size());
    }

    @Test
    public void testDeleteTask() {
        TaskList list = new TaskList();
        Task t1 = new Todo("Read book");
        list.add(t1);
        list.delete(0);
        assertEquals(0, list.size());
    }

}
