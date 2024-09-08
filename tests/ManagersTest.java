import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ManagersTest {
    @Test
    public void proverkaNaSovpadeniyeClassovManagersGetDefault(){
        assertEquals(InMemoryTaskManager.class,Managers.getDefault().getClass(),"Классы должны быть одинаковы");
    }

    @Test
    public void proverkaNaSovpadeniyeClassovManagersGetHistoryDefault(){
        assertEquals(InMemoryHistoryManager.class,Managers.getDefaultHistory().getClass(),"Классы должны быть одинаковы");
    }
}