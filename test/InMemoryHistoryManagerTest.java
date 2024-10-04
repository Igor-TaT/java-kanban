import manager.HistoryManager;
import manager.Managers;
import manager.TaskManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import status.Status;
import task.Task;
import task.SubTask;
import task.Epic;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    HistoryManager history = Managers.getDefaultHistory();
    Task task;
    Epic epic;
    Subtask subTask;
    Long id;

    @BeforeEach
    public void beforeEach() {
        task = new Task("Zadacha task ", "Kakoy-to tekst", Status.NEW);
        task.setTaskId(1);
        epic = new Epic("Zadacha epic", "Kakoy-to tekst epic", Status.NEW);
        epic.setTaskId(2);
        subTask = new Subtask("Zadacha sub", "Kakoy-to tekst sub", Status.NEW, 2);
        subTask.setTaskId(3);
    }

    @Test
    void dobavitzapisvistoriyu() {
        history.add(task);
        final List<Task> historyManager = history.getHistory();
        assertNotNull(historyManager, "Istorya pustaya.");
        assertEquals(1, historyManager.size(), "Istoriya pustaya");
    }

    @Test
    void dobavitdublicatzadachi() {
        history.add(task);
        history.add(task);
        final List<Task> historyManager = history.getHistory();
        assertNotNull(historyManager, "Istoriya Pustaya");
        assertEquals(1, historyManager.size(), "Dublicat");
    }

    @Test
    void udalitzadachuizhead() {
        history.add(task);
        history.add(epic);
        history.add(subTask);
        history.remove(task.getTaskId());
        final List<Task> historyManager = history.getHistory();
        assertNotNull(historyManager, "Istoriya Pustaya");
        assertEquals(2, historyManager.size(), "Zapis ne udalena");
    }

    @Test
    void udalitzadachuizseredini() {
        history.add(task);
        history.add(epic);
        history.add(subTask);
        history.remove(epic.getTaskId());
        final List<Task> historyManager = history.getHistory();
        assertNotNull(historyManager, "Istoriya Pustaya");
        assertEquals(2, historyManager.size(), "Zapis ne udalena");
    }

    @Test
    void udalitzapisizhvosta() {

        history.add(epic);
        history.add(subTask);
        history.add(task);
        history.remove(task.getTaskId());
        final List<Task> historyManager = history.getHistory();
        assertNotNull(historyManager, "Istoriya Pustaya");
        assertEquals(2, historyManager.size(), "Zapis ne udalena");
    }

}