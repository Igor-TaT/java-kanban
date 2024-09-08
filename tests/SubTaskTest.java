import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SubTaskTest {
    @Test
    public void RaznieSubTaskZadachiSOdinmIDDolzhniBitRavni(){
        Epic epic = new Epic("Главная задача","Описание главной задачи");
        SubTask first = new SubTask("Купить муку","В магазине ВкусВилл",Status.NEW,epic.getTaskId());
        SubTask second = new SubTask("Купить машину","В салоне, новую!",Status.NEW,epic.getTaskId());
        assertEquals(first,second,"Задачи должны быть равны, так как у них один ID");
    }
}