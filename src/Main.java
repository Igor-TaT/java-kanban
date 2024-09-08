
public class Main {

    public static void main(String[] args) {
        InMemoryTaskManager taskManager = new InMemoryTaskManager();
        Task tasker = taskManager.create(new Task("Новая задача", "описание задачи", Status.NEW));
        tasker = taskManager.create(new Task("Новая задача2", "описание задачи2", Status.NEW));
        tasker = taskManager.create(new Task("Новая задача3", "описание задачи3", Status.NEW));
        tasker = taskManager.create(new Epic("Новая Эпик задача3", "описание эпик задачи3"));
        tasker = taskManager.create(new SubTask("Новая подзадача3", "описание подзадачи3",Status.NEW,4));

        taskManager.getTaskFromId(1);

        System.out.println("Задачи:");
        for (Task task : taskManager.getTasks()) {
            System.out.println(task);
        }
        System.out.println("Эпики:");
        for (Task epic : taskManager.getEpics()) {
            System.out.println(epic);

            for (Task task : taskManager.getSubTasksByEpicID(epic.getTaskId())) {
                System.out.println("--> " + task);
            }
        }
        System.out.println("Подзадачи:");
        for (Task subtask : taskManager.getSubTasks()) {
            System.out.println(subtask);
        }

        System.out.println("История:");
        for (Task task : taskManager.getHistory()) {
            System.out.println(task);
        }



    }
}
