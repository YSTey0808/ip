package amos.tasks;

import java.util.List;

public class TaskList {
    private final List<Task> tasks;

    public TaskList(List<Task> loadedTasks) {
        tasks = loadedTasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public void delete(int index) {
        tasks.remove(index);
    }

    public Task get(int index) {
        return tasks.get(index);
    }

    public int size() {
        return tasks.size();
    }

}
