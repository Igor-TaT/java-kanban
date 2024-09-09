package manager;

import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {
    List<Task> getHistory();

    Task create(Task task);

    Epic create(Epic epic);

    SubTask create(SubTask subTask);

    Task updateTask(Task task);

    Epic updateEpic(Epic epic);

    SubTask updateSubTask(SubTask subTask);

    Task getTaskFromId(long id);

    Epic getEpicFromId(long id);

    SubTask getSubTaskFromId(long id);

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    List<SubTask> getSubTasksByEpicID(long epicID);

    void deleteAllTask();

    void deleteAllEpic();

    void deleteAllSubTask();

    void deleteTask(long id);

    void deleteEpic(long id);

    void deleteSubTask(long id);
}
