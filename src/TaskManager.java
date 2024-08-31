import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
        private final HashMap<Long, Task> tasks = new HashMap<>();
        private final HashMap<Long, Epic> epics = new HashMap<>();
        private final HashMap<Long, SubTask> subtasks = new HashMap<>();
        private long takeId = 0;

        private long generateId() {
            return takeId++;
        }

        public Task create(Task task) {
            task.setTaskId(generateId());
            tasks.put(task.getTaskId(), task);
            return task;
        }

        public Epic create(Epic epic) {
            epic.setTaskId(generateId());
            epics.put(epic.getTaskId(), epic);
            return epic;
        }

        public SubTask create(SubTask subTask) {
            subTask.setTaskId(generateId());
            subtasks.put(subTask.getTaskId(), subTask);
            return subTask;
        }

}


