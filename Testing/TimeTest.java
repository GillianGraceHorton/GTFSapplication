import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TimeTest {
    @Test
    void constructors() {
        Time time1 = new Time("08:16:00");
        Time time2 = new Time("00:00:00");
        Time time3 = new Time("00:30:00");
        assertThrows(IllegalArgumentException.class, () -> {new Time("25:00:00");});
        assertThrows(IllegalArgumentException.class, () -> {new Time("30:40");});
        assertThrows(IllegalArgumentException.class, () -> {new Time("45");});

        assertEquals("08:16:00", time1.toString());
        assertEquals("00:00:00", time2.toString());
        assertEquals("00:30:00", time3.toString());
        assertNotEquals("00:30:00", time2.toString());
    }

}