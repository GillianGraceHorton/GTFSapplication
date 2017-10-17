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

        //add a null stop
        assertFalse(route.addStop(null, 3));
    }

    /**
     * Checks if a stop can be successfully searched in a route
     *
     * :author: hoffmanjc
     */
    @Test
    void searchRoute() {
        //Makes sure searching for stops before adding doesnt create error
        assertEquals(route.searchRoute("stop_3"), null);

        //Add stops
        for(int i = 1; i <= 10; i++) {
            Stop stop = new Stop(12, 11, "Stop", "stop_" + i, "Stop");
            route.addStop(stop, i);
        }

        //Test if all stops can be found
        for(int i = 10; i >= 1; i--) {
            assertEquals(route.searchRoute("stop_" + i).getStopID(), ("stop_" + i));
        }

        //search for a stop id not in routes
        assertEquals(route.searchRoute("stop_15"), null);

        //search with a null stop id
        route.addStop(null, 11);
        assertEquals(route.searchRoute(null), null);
    }

}