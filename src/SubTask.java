public class SubTask extends Task {
    private final long epicID;


    public SubTask(String title, String description, long taskId, Status status, long epicID) {
        super(title, description, taskId, status);
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

