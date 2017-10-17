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
        assertFalse(trip.addStop(new Stop(12, 11, "Stop", "stop_0", "Stop"), 0));

        //Test if all stops can be found with a valid route assigned in the trip
        trip.setRoute(new Route("route_1", " ", " ", " ", " ",
                " ", " ", " ", " "));
        for(int i = 1; i <= 10; i++) {
            Stop stop = new Stop(12, 11, "Stop", "stop_" + i, "Stop");
            assertTrue(trip.addStop(stop, i));
        }

        //add a null stop
        assertFalse(trip.addStop(null, 3));
    }

    /**
     * Tests if get stop can handle all possible errors
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
            Stop stop = new Stop(12, 11, "Stop", "stop_" + i, "Stop");
            trip.addStop(stop, i);
        }

        //Test if all stops can be found
        for(int i = 10; i >= 1; i--) {
            assertEquals(trip.getStop("stop_" + i).getStopID(), ("stop_" + i));
        }

        //search for a stop id not in trips
        assertEquals(trip.getStop("stop_15"), null);

        //search with a null stop id
        trip.addStop(null, 11);
        assertEquals(trip.getStop(null), null);
    }

}