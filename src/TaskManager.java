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
        if (epics.get(subTask.getEpicID()) != null) {
            Epic epic = epics.get(subTask.getEpicID());
            subTask.setTaskId(generateId());
            epic.addSubTask(subTask);
            subtasks.put(subTask.getTaskId(), subTask);
            updateStatusEpic(epic);
        }
        return subTask;
    }

    public Task updateTask(Task task) {
        Long taskId = task.getTaskId();
        if (!tasks.containsKey(taskId)) {
            return null;
        }
        tasks.replace(taskId, task);
        return task;
    }

    public Epic updateEpic(Epic epic) {
        Long epicId = epic.getTaskId();
        if (epics.get(epicId) != null) {
            epics.get(epicId).setTitle(epic.getTitle());
            epics.get(epicId).setDescription(epic.getDescription());
        }
        return epic;
    }

    public SubTask updateSubTask(SubTask subTask) {
        Long subTaskId = subTask.getTaskId();
        Long subTaskEpicId = subTask.getEpicID();

        if (subtasks.get(subTaskId) != null && subtasks.get(subTaskId).getEpicID() == subTaskEpicId) {
            subtasks.get(subTaskId).setTitle(subTask.getTitle());
            subtasks.get(subTaskId).setDescription(subTask.getDescription());
            subtasks.get(subTaskId).setStatus(subTask.getStatus());
            if (epics.get(subTaskEpicId) != null) {
                epics.get(subTaskEpicId).updateSubTask(subTask);
                updateStatusEpic(epics.get(subTaskEpicId));
            }
        }

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

    public ArrayList<SubTask> getSubTasksByEpicID(long epicID) {
        ArrayList<SubTask> sb = new ArrayList<>();
        if (epics.get(epicID) != null) {
            sb = epics.get(epicID).getSubTasks();
        }
        return sb;
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
        for (Epic epic : epics.values()) {
            epic.cleanAllSubTasks();
            updateStatusEpic(epic);
        }
    }

    public void deleteTask(long id) {
        tasks.remove(id);
    }

    public void deleteEpic(long id) {
        if (epics.containsKey(id)) {
            ArrayList<SubTask> epicSub = epics.get(id).getSubTasks();
            for (SubTask s : epicSub) {
                subtasks.remove(s.getTaskId());
            }
            epics.remove(id);
        }
    }

    public void deleteSubTask(long id) {
        if (subtasks.get(id) != null) {
            SubTask subTask = subtasks.get(id);
            long epicId = subTask.getEpicID();
            subtasks.remove(id);
            Epic epic = epics.get(epicId);
            epic.deleteById(subTask);
            updateStatusEpic(epic);
        }
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


