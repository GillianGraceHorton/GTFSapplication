import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TripTest {
    Trip trip;

    @BeforeEach
    void startUp() {
        trip = new Trip("trip_1", " ", " ", " ", " ", "", " ");
    }

    /**
     * Tests if the addStop(0 method functions properly
     * :author: hoffmanjc
     */
    @Test
    void addStop() {
        //Test if a stop cannot be added without setting route first.
        Stop stop1 = new Stop(12, 11, "Stop", "stop_0", "Stop");
        StopTime stopTime1 = new StopTime("stopTime_0", "", "",
                "stop_0", "0", "", "", "");
        stopTime1.setStop(stop1);
        assertFalse(trip.addStopTime(stopTime1));

        //Test if all stops can be found with a valid route assigned in the trip
        trip.setRoute(new Route("route_1", " ", " ", " ", " ",
                " ", " ", " ", " "));
        for (int i = 1; i <= 10; i++) {
            StopTime stopTime = new StopTime("stopTime_" + i, "", "",
                    "stop_0", "0", "", "", "");
            Stop stop = new Stop(12, 11, "Stop", "stop_" + i, "Stop");
            stopTime.setStop(stop);
            assertTrue(trip.addStopTime(stopTime));
        }

        //add a null stop
        assertFalse(trip.addStopTime(null));
    }


    /**
     * Tests if get stop can handle all possible errors that may occur.
     * :author: hoffmanjc
     */
    @Test
    void getStop() {
        //set route for trip
        trip.setRoute(new Route("route_1", " ", " ", " ", " ",
                " ", " ", " ", " "));
        //Makes sure searching for stops before adding doesnt create error
        assertEquals(trip.getStop("stop_3"), null);

        //Add stops
        for(int i = 1; i <= 10; i++) {
            StopTime stopTime = new StopTime("stopTime", "", "",
                    "stop_0", "0", "", "", "");
            Stop stop = new Stop(12, 11, "Stop", "stop_" + i, "Stop");
            stopTime.setStop(stop);
            trip.addStopTime(stopTime);
        }

        //Test if all stops can be found
        for(int i = 10; i >= 1; i--) {
            assertEquals(trip.getStop("stop_" + i).getStopID(), ("stop_" + i));
        }

        //search for a stop id not in trips
        assertEquals(trip.getStop("stop_15"), null);

        //search with a null stop id
        trip.addStopTime(null);
        assertEquals(trip.getStop(null), null);
    }

}