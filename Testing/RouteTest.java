import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class RouteTest {
    Route route;

    @BeforeEach
    void startUp() {
        route = new Route("route_1","null","null","null",
                "null","null","null","null","null");
    }

    /**
     * Checks if a stop can be ucessfully added
     *
     * :author: hoffmanjc
     */
    @Test
    void addStop() {
        //Test if all stops can be found
        for(int i = 1; i <= 10; i++) {
            Stop stop = new Stop(12, 11, "Stop", "stop_" + i, "Stop");
            assertTrue(route.addStop(stop, i));
        }
    }

    /**
     * Checks if a stop can be successfully searched in a route
     *
     * :author: hoffmanjc
     */
    @Test
    void searchRoute() {
        //Add stops
        for(int i = 1; i <= 10; i++) {
            Stop stop = new Stop(12, 11, "Stop", "stop_" + i, "Stop");
            route.addStop(stop, i);
        }

        //Test if all stops can be found
        for(int i = 10; i >= 1; i--) {
            assertEquals(route.searchRoute("stop_" + i).getStopID(), ("stop_" + i));
        }
    }

}