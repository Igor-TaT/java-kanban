import java.util.ArrayList;

public class Epic extends Task {
    private ArrayList<SubTask> subTasks;

    public Epic(String title, String description, long taskId, Status status) {
        super(title, description, taskId, status);
        this.subTasks = new ArrayList<>();
    }

    public void addSubTask(SubTask subTask) {
        subTasks.add(subTask);
    }

    public void cleanAllSubTasks() {
        subTasks.clear();
    }

    public ArrayList<SubTask> getSubTasks() {
        return subTasks;
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
