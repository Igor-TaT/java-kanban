import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryHistoryManagerTest {

    TaskManager taskManager;
    HistoryManager history;
    Task firstTask;
    Long id;

    @BeforeEach
    public void beforeEach(){
        taskManager = Managers.getDefault();
        history = Managers.getDefaultHistory();
        taskManager.create(new Task("Эта задача будет перезаписана","Описание простой задачи",Status.NEW));
        firstTask = taskManager.getTasks().get(0);
        id = firstTask.getTaskId();
    }

    @Test
    public void ProsmotriVIstroiiSohranyautsa(){
        assertTrue(history.getHistory().isEmpty());
        taskManager.getTaskFromId(id);
        assertFalse(history.getHistory().isEmpty());
    }

    @Test
    public void vIstroiiDolzhnoHranitsaNeBolee10Zapisey(){
        for(int i=0; i < 20; i++){
            taskManager.getTaskFromId(id);
        }
        assertTrue(history.getHistory().size() <= 10,"В истории должно храниться не более 10 записей");
    }

    @Test
    public void vIstroiiPriPerepolneniiUdalyaetsaSamayaStarayaZapis(){
        Long idZapisi = taskManager.getTasks().getLast().getTaskId();
        String titleZapisi = taskManager.getTasks().getLast().getTitle();
        taskManager.getTaskFromId(idZapisi);
        taskManager.create(new Task("Эта задача не будет перезаписана","Описание",Status.NEW));
        assertEquals(titleZapisi,history.getHistory().getFirst().getTitle(),"Заголовки должны совпасть");
        for (int i=0;i<10;i++){
            taskManager.getTaskFromId(taskManager.getTasks().getLast().getTaskId());
        }
        assertNotEquals(titleZapisi,history.getHistory().getFirst().getTitle(),"Заголовки не должны совпасть");
    }

    @Test
    public void HistoryHranitVersiuDoOblovleniyaIPosle(){
        taskManager.getTaskFromId(id);
        Task taskFromHistoryFirst = history.getHistory().getLast();
        assertEquals(id + " - " + firstTask.getTitle() + " - " + firstTask.getDescription() + " - " + firstTask.getStatus(),taskFromHistoryFirst.getTaskId() + " - " + taskFromHistoryFirst.getTitle() + " - " + taskFromHistoryFirst.getDescription() + " - " + taskFromHistoryFirst.getStatus());

        //Проверка после обновления задачи
        taskManager.updateTask(new Task(id,"Обновленная простая задача","Описание обновленной простой задачи",Status.DONE));
        taskManager.getTaskFromId(id);
        Task taskFromHistoryLast = history.getHistory().getLast();
        System.out.println(taskFromHistoryFirst.getTitle() + " - " + taskFromHistoryLast.getTitle());
        assertNotEquals(taskFromHistoryFirst.getTaskId() + " - " + taskFromHistoryFirst.getTitle() + " - " + taskFromHistoryFirst.getDescription() + " - " + taskFromHistoryFirst.getStatus(),taskFromHistoryLast.getTaskId() + " - " + taskFromHistoryLast.getTitle() + " - " + taskFromHistoryLast.getDescription() + " - " + taskFromHistoryLast.getStatus());

    }

}