import org.junit.jupiter.api.Test;
import status.Status;
import task.Task;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {
    @Test
    public void RaznieZadachiSOdinmIDDolzhniBitRavni() {
        Task first = new Task("Купить муку", "В магазине ВкусВилл", Status.NEW);
        Task second = new Task("Купить машину", "В салоне, новую!", Status.DONE);
        assertEquals(first, second, "Задачи должны быть равны, так как у них один ID");
    }
}