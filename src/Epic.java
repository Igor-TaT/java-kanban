import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks;

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        this.subTasks = new ArrayList<>();
    }

    public Epic(Long id, String title, String description) {
        super(id, title, description, Status.NEW);
        this.subTasks = new ArrayList<>();
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public void cleanAllSubTasks() {
        subTasks.clear();
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks);
    }

    public void deleteById(SubTask subTask) {
        if(subTasks.contains(subTask)) subTasks.remove(subTask);
}

    public void updateSubTask(SubTask subTask) {
        if (subTasks.contains(subTask)) {
            long id = subTask.getTaskId();
            long epicID = subTask.getEpicID();
            for (SubTask s : subTasks) {
                if (s.getTaskId() == id && s.getEpicID() == epicID) {
                    s.setTitle(subTask.getTitle());
                    s.setDescription(subTask.getDescription());
                    s.setStatus(subTask.getStatus());
                }
            }
        }
    }


    @Override
    public String toString() {
        return "Epic{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", taskId=" + getTaskId() +
                ", status=" + getStatus() +
                ", subTasks=" + subTasks +
                '}';
    }
}
