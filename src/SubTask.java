public class SubTask extends Task {
    private final int epicID;


    public SubTask(String title, String description, int taskId, Status status, int epicID) {
        super(title, description, taskId, status);
        this.epicID = epicID;
    }

    public int getEpicID() {
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

