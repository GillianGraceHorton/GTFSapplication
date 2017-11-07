import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.NavigableMap;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

class DataStorageTest {
    DataStorage dataStorage;

    @BeforeEach
    public void setUp(){
        dataStorage = new DataStorage();
    }

    /**
     * tests the attach method in the dataStorage class for two different test cases
     * @author: hortong
     */
    @Test
    void attach() throws FileNotFoundException, ScriptException {
        //creates observer object to test the attach method
        Observer observer = new BusMap();

        //checks that the observers collection in the dataStorage object is empty
        assertTrue(dataStorage.getObservers().size() == 0);

        //Test Case 1: tests that the attach method adds an observer to the observers collection
        dataStorage.attach(observer);
        assertTrue(dataStorage.getObservers().size() == 1);

        //Test Case 2: tests that the attach method added the correct observer to the observers
        // collection
        assertEquals(((ArrayList)dataStorage.getObservers()).get(0), observer);
    }

    /**
     * @author: hortong
     * tests the searchStops method in the dataStorage class for three different test cases
     */
    @Test
    void searchStops() {
        //creates the stops to add to the dataStorage object
        NavigableMap<String, Stop> stops = new TreeMap<>();
        Stop stop1 = new Stop(0,0,"", "1A", "");
        stops.put(stop1.getStopID(), stop1);
        Stop stop2 = new Stop(0,0,"", "2b", "");
        stops.put(stop2.getStopID(), stop2);
        Stop stop3 = new Stop(0,0,"", "3c", "");
        stops.put(stop3.getStopID(), stop3);

        //Test Case 1: tests that the method returns null when when the stops collection is empty
        assertNull(dataStorage.searchStops("1A"));

        //Test Case 2: tests that the method returns the correct stop when searching
        dataStorage.setStops(stops);
        assertEquals(dataStorage.searchStops("1A"), stop1);

        //Test Case 3: tests that the method returns null when the stop doesn't exist in the stop
        // collection
        assertNull(dataStorage.searchStops("4d"));
    }

    /**
     * tests the detach method of the DataStorage class for 3 test cases
     * @author: hortong
     */
    @Test
    void detach() throws FileNotFoundException, ScriptException {
        ArrayList<Observer> observers = new ArrayList<>();
        Observer observer1 = new BusMap();
        observers.add(observer1);
        Observer observer2 = new BusMap();
        observers.add(observer2);

        //Test Case 1: tests that the detach method doesn't say that it removed an observer when is
        // has an empty collection of observers
        assertFalse(dataStorage.detach(observer1));

        //Test Case 2: test that the detach method returns true when it successfully detaches an
        // observer
        dataStorage.setObservers(observers);
        assertTrue(dataStorage.detach(observer1));
        assertEquals(1, dataStorage.getObservers().size());

        //Test Case 3: tests that the detach method return false when it doesn't contain the
        // observer to detach.
        assertFalse(dataStorage.detach(observer1));
    }

    /**
     * tests the search routes method of the DataStorage Class for 3 test cases.
     * @author: hortong
     */
    @Test
    void searchRoutes() {
        //creates the routes to add to the dataStorage object
        NavigableMap<String, Route> routes = new TreeMap<>();
        Route route1 = new Route("1A", "", "", "", "", "", "", "", "");
        routes.put(route1.getRouteID(), route1);
        Route route2 = new Route("2B", "", "", "", "", "", "", "", "");
        routes.put(route2.getRouteID(), route2);
        Route route3 = new Route("3c", "", "", "", "", "", "", "", "");
        routes.put(route3.getRouteID(), route3);

        //Test Case 1: tests that the method returns null when when the routes collection is empty
        assertNull(dataStorage.searchRoutes("1A"));

        //Test Case 2: adds the routes to the dataStorage object and tests that the method returns
        // the correct route when searching
        dataStorage.setRoutes(routes);
        assertEquals(dataStorage.searchRoutes("3C"), route3);

        //Test Case 3: tests that the method returns null when the route doesn't exist in the route
        // collection
        assertNull(dataStorage.searchRoutes("4d"));
    }

    /**
     * test the notifyObservers method in the dataStorage class for 2 test cases
     * @author: hortong
     */
    @Test
    void notifyObservers() throws FileNotFoundException, ScriptException {
        ArrayList<Observer> observers = new ArrayList<>();
        LinkedList<Stop> updates = new LinkedList<>();
        Stop stop = new Stop(0, 0, "test",  "1A", "");
        Observer observer1 = new BusMap();
        Observer observer2 = new BusMap();

        observers.add(observer1);
        observers.add(observer2);
        updates.add(stop);

        //tests that the notifyObservers method does not throw an exception when there are no
        // observers loaded yet
        dataStorage.updateFromFiles(updates);
        dataStorage.notifyObservers();

        //tests that the correct update is passed to the observers
        dataStorage.setObservers(observers);
        dataStorage.notifyObservers();
        assertEquals(stop, ((BusMap)observer1).getStops().get(0));
        assertEquals(stop, ((BusMap)observer2).getStops().get(0));
    }

    /**
     * tests the searchTrips method in the DataStorage class for 3 test cases
     * @author: hortong
     */
    @Test
    void searchTrips() {
        //creates the trips to add to the dataStorage object
        NavigableMap<String, Trip> trips = new TreeMap<>();
        Trip trip1 = new Trip("1A", "", "", "", "", "", "");
        trips.put(trip1.getTripID(), trip1);
        Trip trip2 = new Trip("2B", "", "", "", "", "", "");
        trips.put(trip2.getTripID(), trip2);
        Trip trip3 = new Trip("3C", "", "", "", "", "", "");
        trips.put(trip3.getTripID(), trip3);

        //Test Case 1: tests that the method returns null when when the trips collection is empty
        assertNull(dataStorage.searchTrips("1A"));

        //Test Case 2: adds the trips to the dataStorage object and tests that the method returns
        // the correct trip when searching
        dataStorage.setTrips(trips);
        assertEquals(dataStorage.searchTrips("3c"), trip3);

        //Test Case 3: tests that the method returns null when the trip doesn't exist in the trip
        // collection
        assertNull(dataStorage.searchTrips("4d"));
    }

    /**
     * tests the searchTripsForStop method in the DataStorage class for 4 test cases
     * @author: hortong
     */
    @Test
    void searchTripsForStop() {
        NavigableMap<String, Trip> trips = new TreeMap<>();
        Trip trip1 = new Trip("1A", "", "","","","","");
        trips.put(trip1.getTripID(), trip1);
        Trip trip2 = new Trip("2A", "", "","","","","");
        trips.put(trip2.getTripID(), trip2);
        StopTime stopTime1 = new StopTime("1C", "", "",
                "stop_0", "0", "", "", "");
        Stop stop1 = new Stop(0, 0, "", "1B", "");
        stopTime1.setStop(stop1);
        StopTime stopTime2 = new StopTime("2C", "", "",
                "stop_0", "0", "", "", "");
        Stop stop2 = new Stop(0, 0, "", "2B", "");
        stopTime2.setStop(stop2);
        StopTime stopTime3 = new StopTime("3C", "", "",
                "stop_0", "0", "", "", "");
        Stop stop3 = new Stop(0, 0, "", "3B", "");
        stopTime3.setStop(stop3);

        //Test Case 1: tests that the method returns null when the collection of trips in
        // dataStorage is empty
        assertNull(dataStorage.searchTripsForStop("2B"));

        //Test Case 2: tests that the method returns null when the list of stops in the trips is
        // empty
        dataStorage.setTrips(trips);
        assertNull(dataStorage.searchTripsForStop("1B"));

        //Test Case 3: tests that the method return the correct collection of trips
        trip1.addStopTime(stopTime1);
        trip1.addStopTime(stopTime2);
        trip1.addStopTime(stopTime3);
        trip2.addStopTime(stopTime1);
        ArrayList<Trip> check = new ArrayList<>();
        check.add(trip1);
        check.add(trip2);
        assertEquals(dataStorage.searchTripsForStop("1B"), check);

        //Test Case 4: test that the method returns null if the stop is not found in any of the
        // trips
        assertNull(dataStorage.searchTripsForStop("4B"));
    }

    /**
     * tests the searchRoutesForStop method in the dataStorage class for 4 test cases
     * @author: hortong
     */
    @Test
    void searchRoutesForStop() {
        NavigableMap<String, Route> routes = new TreeMap<>();
        Route route1 = new Route("1A", "", "", "", "", "", "", "", "");
        routes.put(route1.getRouteID(), route1);
        Route route2 = new Route("1B", "", "", "", "", "", "", "", "");
        routes.put(route2.getRouteID(), route2);
        Stop stop1 = new Stop(0, 0, "", "1B", "");
        Stop stop2 = new Stop(0, 0, "", "2B", "");
        Stop stop3 = new Stop(0, 0, "", "3B", "");

        //Test Case 1: tests that the method returns null when the collection of routes in
        // dataStorage is empty
        assertNull(dataStorage.searchRoutesForStop("2B"));

        //Test Case 2: tests that the method returns null when the list of stops in the routes is
        // empty
        dataStorage.setRoutes(routes);
        assertNull(dataStorage.searchRoutesForStop("1B"));

        //Test Case 3: tests that the method return the correct collection of routes
        route1.addStop(stop1, 1);
        route1.addStop(stop2, 2);
        route1.addStop(stop3, 3);
        route2.addStop(stop1, 1);
        ArrayList<Route> check = new ArrayList<>();
        check.add(route1);
        check.add(route2);
        assertEquals(dataStorage.searchRoutesForStop("1B"), check);

        //Test Case 4: test that the method returns null if the stop is not found in any of the
        // routes
        assertNull(dataStorage.searchRoutesForStop("4B"));
    }

}