import java.util.ArrayList;
import java.util.HashMap;


public class TaskManager {
    private final HashMap<Long, Task> tasks = new HashMap<>();
    private final HashMap<Long, Epic> epics = new HashMap<>();
    private final HashMap<Long, SubTask> subtasks = new HashMap<>();
    private long takeId = 0;

    private long generateId() {
        return ++takeId;
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
        Epic epic = epics.get(subTask.getEpicID());
        epic.addSubTask(subTask);
        subtasks.put(subTask.getTaskId(), subTask);
        updateStatusEpic(epic);
        return subTask;
    }

    public Task updateTask(Task task) {
        Long taskId = task.getTaskId();
        if (taskId == null || !tasks.containsKey(taskId)) {
            return null;
        }
        tasks.replace(taskId, task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        Long epicId = epic.getTaskId();
        if (epicId == null || !epics.containsKey(epicId)) {
            return null;
        }
        epics.replace(epicId, epic);
        return epic;
    }

    public SubTask updateSubTask(SubTask subTask) {
        Long subTaskId = subTask.getTaskId();
        if (subTaskId == null || !subtasks.containsKey(subTaskId)) {
            return null;
        }
        subtasks.replace(subTaskId, subTask);
        return subTask;
    }

    public Task getTaskFromId(long id) {
        return tasks.get(id);
    }

    public Epic getEpicFromId(long id) {
        return epics.get(id);
    }

    public SubTask getSubTaskFromId(long id) {
        return subtasks.get(id);
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    public ArrayList<SubTask> getSubTasks(Epic epic) {
        return epic.getSubTasks();
    }

    public void deleteAllTask() {
        tasks.clear();
    }

    public void deleteAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    public void deleteAllSubTask() {
        subtasks.clear();
    }

    public void deleteTask(long id) {
        tasks.remove(id);
    }

    public void deleteEpic(long id) {
        ArrayList<SubTask> epicSub = epics.get(id).getSubTasks();
        for (SubTask s : epicSub) {
            subtasks.remove(s.getTaskId());
        }
        epics.remove(id);
    }

    public void deleteSubTask(long id) {
        SubTask subTask = subtasks.get(id);
        long epicId = subTask.getEpicID();
        subtasks.remove(id);
        Epic epic = epics.get(epicId);
        ArrayList<SubTask> sList = epic.getSubTasks();
        sList.remove(subTask);
        epic.set(sList);
        updateStatusEpic(epic);
    }

    private void updateStatusEpic(Epic epic) {
        int colvoDone = 0;
        int colvoNew = 0;
        ArrayList<SubTask> sList = epic.getSubTasks();
        for (SubTask s : sList) {
            if (s.getStatus() == Status.DONE) {
                colvoDone++;
            }
            if (s.getStatus() == Status.NEW) {
                colvoNew++;
            }
        }
        if (colvoNew == sList.size()) {
            epic.setStatus(Status.NEW);
        } else if (colvoDone == sList.size()) {
            epic.setStatus(Status.DONE);
        } else {
            epic.setStatus(Status.IN_PROGRESS);
        }
    }


}


