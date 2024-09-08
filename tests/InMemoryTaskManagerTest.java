import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InMemoryTaskManagerTest {

    protected static TaskManager taskManager;
    protected static HistoryManager history;
    protected static Task firstTask;
    protected static Task secondTask;
    protected static Epic epicTask;
    protected static SubTask epicSubTask;

    @BeforeEach
    public void vvodniye(){
        taskManager = Managers.getDefault();
        taskManager.create(new Task("Простая первая задача", "описание простой первой задачи", Status.NEW));
        taskManager.create(new Task("Простая вторая задача", "описание простой второй задачи", Status.NEW));
        taskManager.create(new Epic("Новая эпик задача", "описание новой эпик задачи"));
        taskManager.create(new SubTask("Новая подзадача для эпик", "описание новой подзадачи для эпик",Status.NEW,3));
        history = new InMemoryHistoryManager();
        firstTask = taskManager.getTasks().get(0);
        secondTask = taskManager.getTasks().get(1);
        epicTask = taskManager.getEpics().get(0);
        epicSubTask = taskManager.getSubTasks().get(0);
    }

    @Test
    public void MojetNaitiZadachuPoId(){
        assertNotNull(taskManager.getTaskFromId(firstTask.getTaskId()),"Не получилось найти простую задачу");
        assertNotNull(taskManager.getEpicFromId(epicTask.getTaskId()), "Не получилось найти Epic задачу");
        assertNotNull(taskManager.getSubTaskFromId(epicSubTask.getTaskId()),"Не получилось найти подзадачу");
    }

    @Test
    public void sozdalasLiPervayaProstayaZadachaVsePolyaNeIzmenni(){
        assertEquals("Простая первая задача - описание простой первой задачи - NEW",firstTask.getTitle() + " - " + firstTask.getDescription() + " - " + firstTask.getStatus());
    }

    @Test
    public void sozdalasLiEpicZadachaPolyaNeIzmenni(){
        assertEquals("Новая эпик задача - описание новой эпик задачи",epicTask.getTitle() + " - " + epicTask.getDescription());
    }

    @Test
    public void sozdalasLiEpicSubTaskZadachaPolyaNeIzmenni(){
        assertEquals("Новая подзадача для эпик - описание новой подзадачи для эпик",epicTask.getSubTasks().get(0).getTitle() + " - " + epicTask.getSubTasks().get(0).getDescription());
        assertEquals("Новая подзадача для эпик - описание новой подзадачи для эпик",epicSubTask.getTitle() + " - " + epicSubTask.getDescription());
    }

    @Test
    public void ObnovitProstuyuZadachu(){
        //Проверка до обновления задачи
        Long id = firstTask.getTaskId();
        String strokaDlyaProverki = id + " - Простая первая задача - описание простой первой задачи - NEW";
        assertEquals(strokaDlyaProverki,id + " - " + firstTask.getTitle() + " - " + firstTask.getDescription() + " - " + firstTask.getStatus());

        //Проверка после обновления задачи
        taskManager.updateTask(new Task(id,"Обновленная простая задача","Описание обновленной простой задачи",Status.DONE));
        Task updated = taskManager.getTaskFromId(id);
        assertNotEquals(strokaDlyaProverki,id + " - " + updated.getTitle() + " - " + updated.getDescription() + " - " + updated.getStatus());
    }

    @Test
    public void ProverkaNaUdaleniyeVsehZapisey() {
        assertFalse(taskManager.getTasks().isEmpty());
        taskManager.deleteAllTask();
        assertTrue(taskManager.getTasks().isEmpty());
    }

    @Test
    public void ProverkaNaUdaleniyeVsehPodZapisey() {
        assertFalse(taskManager.getSubTasks().isEmpty());
        taskManager.deleteAllSubTask();
        assertTrue(taskManager.getSubTasks().isEmpty());
    }

    @Test
    public void UpdateZadachuEpic(){
        //Проверка до обновления эпик задачи
        Long id = epicTask.getTaskId();
        String strokaDlyaProverki = id + " - Новая эпик задача - описание новой эпик задачи";
        assertEquals(strokaDlyaProverki,id + " - " + epicTask.getTitle() + " - " + epicTask.getDescription());

        //Проверка после обновления эпик задачи
        taskManager.updateEpic(new Epic(id,"Обновленная эпик задача","Описание обновленной эпик задачи"));
        Epic updated = taskManager.getEpicFromId(id);
        assertNotEquals(strokaDlyaProverki,id + " - " + updated.getTitle() + " - " + updated.getDescription());
    }

    @Test
    public void UpdatePodZadachiIPorverkaStatusaEpikaEsliPodzadachiVipolneni(){
        //Проверка до обновления эпик подзадачи
        Long id = epicSubTask.getTaskId();
        String strokaDlyaProverki = id + " - Новая подзадача для эпик - описание новой подзадачи для эпик";
        assertEquals(strokaDlyaProverki,id + " - " + epicSubTask.getTitle() + " - " + epicSubTask.getDescription());

        //Проверка после обновления эпик подзадачи
        taskManager.updateSubTask(new SubTask(id,"Обновленная эпик задача","Описание обновленной эпик задачи",Status.DONE,epicSubTask.getEpicID()));
        SubTask updated = taskManager.getSubTaskFromId(id);
        assertNotEquals(strokaDlyaProverki,id + " - " + updated.getTitle() + " - " + updated.getDescription());
        String status = taskManager.getEpicFromId(epicSubTask.getEpicID()).getStatus() + "";
        assertEquals(status,"DONE");
    }

}