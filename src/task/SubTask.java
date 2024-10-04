package task;

import status.Status;

public class SubTask extends Task {
    private final long epicID;


    public SubTask(String title, String description, Status status, long epicID) {
        super(title, description, status);
        this.epicID = epicID;
    }

    public SubTask(Long id, String title, String description, Status status, long epicID) {
        super(id, title, description, status);
        this.epicID = epicID;
    }

    public long getEpicID() {
        return epicID;
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", taskId=" + getTaskId() +
                ", status=" + getStatus() +
                ", epicID=" + epicID +
                '}';
    }
}

