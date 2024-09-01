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
        for (int i = 0; i < epics.size(); i++) {
            if (epics.get(i) != null && epics.get(i).getTaskId() == epicId) {
                epics.get(i).setTitle(epic.getTitle());
                epics.get(i).setDescription(epic.getDescription());
                updateStatusEpic(epics.get(i));
            }
        }
        return epic;
    }

    public SubTask updateSubTask(SubTask subTask) {
        Long subTaskId = subTask.getTaskId();
        Long subTaskEpicId = subTask.getEpicID();
        for (int i = 0; i < subtasks.size(); i++) {
            if (subtasks.get(i) != null && subtasks.get(i).getTaskId() == subTaskId && subtasks.get(i).getEpicID() == subTaskEpicId) {
                subtasks.get(i).setTitle(subTask.getTitle());
                subtasks.get(i).setDescription(subTask.getDescription());
                subtasks.get(i).setStatus(subTask.getStatus());
                for (Epic ep : epics.values()) {
                    if (ep.getTaskId() == subTaskEpicId) {
                        ep.updateSubTask(subTask);
                        updateStatusEpic(ep);
                    }
                }
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
        for (SubTask st : subtasks.values()) {
            if (st.getEpicID() == epicID) {
                sb.add(st);
            }

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
            for (SubTask subTask : epic.getSubTasks()) {
                epic.deleteById(subTask.getTaskId());
                updateStatusEpic(epic);
            }
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
            System.out.println("Look : " + subTask);
            long epicId = subTask.getEpicID();
            subtasks.remove(id);
            Epic epic = epics.get(epicId);
            epic.deleteById(id);
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


