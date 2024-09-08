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

    ArrayList<Task> getTasks();

    ArrayList<Epic> getEpics();

    ArrayList<SubTask> getSubTasks();

    ArrayList<SubTask> getSubTasksByEpicID(long epicID);

    void deleteAllTask();

    void deleteAllEpic();

    void deleteAllSubTask();

    void deleteTask(long id);

    void deleteEpic(long id);

    void deleteSubTask(long id);

    void updateStatusEpic(Epic epic);
}
