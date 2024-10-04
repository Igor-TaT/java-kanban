package manager;

import status.Status;
import task.Epic;
import task.SubTask;
import task.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager {
    private final Map<Long, Task> tasks = new HashMap<>();
    private final Map<Long, Epic> epics = new HashMap<>();
    private final Map<Long, SubTask> subtasks = new HashMap<>();
    private long takeId = 0;
    protected static HistoryManager history = Managers.getDefaultHistory();

    private long generateId() {
        return ++takeId;
    }

    @Override
    public List<Task> getHistory(){
        return history.getHistory();
    }

    @Override
    public void remove(int id) {
        history.remove(id);
    }

    @Override
    public Task create(Task task) {
        task.setTaskId(generateId());
        tasks.put(task.getTaskId(), task);
        return task;
    }

    @Override
    public Epic create(Epic epic) {
        epic.setTaskId(generateId());
        epics.put(epic.getTaskId(), epic);
        return epic;
    }

    @Override
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

    @Override
    public Task updateTask(Task task) {
        Long taskId = task.getTaskId();
        if (!tasks.containsKey(taskId)) {
            return null;
        }
        tasks.replace(taskId, task);
        return task;
    }

    @Override
    public Epic updateEpic(Epic epic) {
        Long epicId = epic.getTaskId();
        Epic zadacha = epics.get(epicId);
        if (zadacha != null) {
            zadacha.setTitle(epic.getTitle());
            zadacha.setDescription(epic.getDescription());
        }
        return epic;
    }

    @Override
    public SubTask updateSubTask(SubTask subTask) {
        Long subTaskId = subTask.getTaskId();
        Long subTaskEpicId = subTask.getEpicID();
        SubTask podzadacha = subtasks.get(subTaskId);

        if (podzadacha != null && podzadacha.getEpicID() == subTaskEpicId) {
            podzadacha.setTitle(subTask.getTitle());
            podzadacha.setDescription(subTask.getDescription());
            podzadacha.setStatus(subTask.getStatus());
            if (epics.get(subTaskEpicId) != null) {
                epics.get(subTaskEpicId).updateSubTask(subTask);
                updateStatusEpic(epics.get(subTaskEpicId));
            }
        }

        return subTask;
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



    @Override
    public Task getTaskFromId(long id) {
        history.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicFromId(long id) {
        history.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskFromId(long id) {
        history.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subtasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasksByEpicID(long epicID) {
        ArrayList<SubTask> sb = new ArrayList<>();
        if (epics.get(epicID) != null) {
            sb = epics.get(epicID).getSubTasks();
        }
        return sb;
    }

    @Override
    public void deleteAllTask() {
        tasks.clear();
    }

    @Override
    public void deleteAllEpic() {
        epics.clear();
        subtasks.clear();
    }

    @Override
    public void deleteAllSubTask() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            epic.cleanAllSubTasks();
            updateStatusEpic(epic);
        }
    }

    @Override
    public void deleteTask(long id) {
        tasks.remove(id);
    }

    @Override
    public void deleteEpic(long id) {
        if (epics.containsKey(id)) {
            ArrayList<SubTask> epicSub = epics.get(id).getSubTasks();
            for (SubTask s : epicSub) {
                subtasks.remove(s.getTaskId());
            }
            epics.remove(id);
        }
    }

    @Override
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


}


