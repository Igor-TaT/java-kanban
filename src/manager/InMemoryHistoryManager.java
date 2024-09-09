package manager;

import task.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{
    private static final int LIMIT_OF_HISTORY_VIEWS = 10;
    private static final List<Task> taskHistoryList = new ArrayList<>();
    @Override
    public void add(Task task) {
        if(task != null) {
            if (taskHistoryList.size() >= LIMIT_OF_HISTORY_VIEWS) {
                taskHistoryList.removeFirst();
                taskHistoryList.add(task);
            } else {
                taskHistoryList.add(task);
            }
        }
    }

    @Override
    public List<Task> getHistory() {
        return new ArrayList<>(taskHistoryList);
    }
}
