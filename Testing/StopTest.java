import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StopTest {
    Stop stops;

    @BeforeEach
    void startUp() {
        stops = new Stop(12, 11, "Stop", "stop_1", "Stop");
    }

    /**
     * Checks if adding stop times works
     * :author: hoffmanjc
     */
    @Test
    void addStopTimes() {
        //Test if normal stoptimes can be added
        for(int i = 1; i <= 10; i++) {
            StopTime stopTime = new StopTime("trip_" + 1, "10:10", "10:10", "", "", "", "", "");
            assertTrue(stops.addStopTimes(stopTime));
        }

        //test if null stoptimes can't be added
        assertFalse(stops.addStopTimes(null));
    }

}