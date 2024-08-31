
public class Main {

    public static void main(String[] args) {
        TaskManager taskManager = new TaskManager();
        Task task = taskManager.create(new Task("Новая задача", "описание задачи", Status.NEW));
        System.out.println("Greate task: " + task);

        Task taskFromManager = taskManager.getTaskFromId(task.getTaskId());
        System.out.println("Get task: " + taskFromManager);

        taskFromManager.setTitle("New name");
        taskManager.updateTask(taskFromManager);
        System.out.println("Update task: " + taskFromManager);

        taskManager.deleteTask(taskFromManager.getTaskId());
        System.out.println("Delete: " + task);


    }
}
