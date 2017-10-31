import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {
    Time time1;
    Time time2;
    Time time3;

    @BeforeEach
    void beforeEach(){
        time1 = new Time("08:16:00");
        time2 = new Time("00:00:00");
        time3 = new Time("00:30:00");
        assertThrows(IllegalArgumentException.class, () -> new Time("25:00:00"));
        assertThrows(IllegalArgumentException.class, () -> new Time("30:40"));
        assertThrows(IllegalArgumentException.class, () -> new Time("45"));

        assertThrows(IllegalArgumentException.class, () -> new Time(25, 26, 0));
        assertThrows(IllegalArgumentException.class, () -> new Time(125, 0, 0));
    }

    @Test
    void toStringTest() {
        assertEquals("08:16:00", time1.toString());
        assertEquals("00:00:00", time2.toString());
        assertEquals("00:30:00", time3.toString());
        assertNotEquals("00:30:00", time2.toString());
    }

    @Test
    void compareTo(){
        assertEquals(-1, time2.compareTo(time1));
        assertEquals(0, time1.compareTo(time1));
        assertEquals(1, time3.compareTo(time2));
        assertNotEquals(1, time2.compareTo(time1));
    }

}