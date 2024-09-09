package task;

import java.util.Objects;
import status.Status;

public class Task {
    private String title;
    private String description;
    private long taskId;
    private Status status;

    public Task(String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
    }

    public Task(Long taskId, String title, String description, Status status) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.taskId = taskId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getTaskId() {
        return taskId;
    }

    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        //return taskId == task.taskId && Objects.equals(title, task.title) && Objects.equals(description, task.description) && status == task.status;
        return taskId == task.taskId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, taskId, status);
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", taskId=" + taskId +
                ", status=" + status +
                '}';
    }

}
