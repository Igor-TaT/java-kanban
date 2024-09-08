import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EpicTest {
    @Test
    public void RaznieEpicZadachiSOdinmIDDolzhniBitRavni(){
        Epic first = new Epic("Купить муку","В магазине ВкусВилл");
        Epic second = new Epic("Купить машину","В салоне, новую!");
        assertEquals(first,second,"Задачи должны быть равны, так как у них один ID");
    }
}