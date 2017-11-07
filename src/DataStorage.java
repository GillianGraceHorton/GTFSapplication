

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

/**
 * Author: Gracie Horton
 * Description:
 * Date Created: 10/3/2017 - 4:57:21 PM
 */
public class DataStorage implements Subject {

    private NavigableMap<String, Stop> stops;
    private NavigableMap<String, Route> routes;
    private NavigableMap<String, Trip> trips;
    private Collection<StopTime> stopTimes;
    private Collection<Observer> observers;
    private SearchResultsView searchResultsView;

    /**
     * Author:
     * Description: Initializes the DataStorage class
     */
    public DataStorage() {
        stops = new TreeMap<>();
        routes = new TreeMap<>();
        trips = new TreeMap<>();
        stopTimes = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void setSearchResultsView(SearchResultsView view) {
        searchResultsView = view;
    }

    /**
     * @author: hortong
     * updates the search view object
     */
    public void updateSearchView(){
        NavigableSet<String> searches = searchResultsView.getSearches();
        if(searches.size() != 0) {
            String[] parts;
            for (String search : searches) {
                parts = search.split(" ");
                switch (parts[0]) {
                    case "stop":
                        searchForStop(parts[1]);
                        break;
                    case "route":
                        searchForRoute(parts[1]);
                        break;
                    case "trip":
                        searchForTrip(parts[1]);
                        break;
                }
            }
        }
    }

    /**
     * Author: hortong
     * Description: attaches observers
     * @param observer - the observer to attach
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Author: hortong
     * Description: removes observers
     * @param observer to remove
     * @return boolean - result of removing an observer
     */
    public boolean detach(Observer observer) {
        return observers.remove(observer);
    }

    /**
     * Author: hortong & Joseph Heinz - heinzja@msoe.edu
     * Description: Takes an arrayList of objects created by files loaded in the file manager and adds each object.
     *
     * @param updates - ArrayList of objects created by the file loaded in the File manager class
     * @throws KeyAlreadyExistsException - if one of the items from updates has an ID that matches
     *                                   one that is already in data structures and is not a placeholder.
     */
    public void updateFromFiles(LinkedList updates) throws KeyAlreadyExistsException {
        Object tmp = updates.get(0);
        if (tmp instanceof Stop) {
            for (Stop cStop : (LinkedList<Stop>) updates) {
                final String cStopID = cStop.getStopID();
                //checks if the stops list contains a stop with the same stopID
                if (stops.containsKey(cStopID)) {
                    Stop oldStop = stops.get(cStopID);
                    if (oldStop.isEmpty()) {
                        //gives the empty stop all the variables of the newStop
                        oldStop.copyInstanceVariables(cStop);
                    } else {
                        throw new KeyAlreadyExistsException("the stop: " + cStop.toString() +
                                "\ncannot bee added because it has the same ID as the stop: " +
                                stops.get(cStopID));
                    }
                } else {
                    stops.put(cStopID, cStop);
                }
            }
        } else if (tmp instanceof Route) {
            for (Route cRoute : (LinkedList<Route>) updates) {
                final String cRouteID = cRoute.getRouteID();
                if (routes.containsKey(cRouteID)) {
                    Route oldRoute = routes.get(cRouteID);
                    if (routes.get(cRouteID).isEmpty()) {
                        //gives the empty route all the variables of the newRoute
                        oldRoute.copyInstanceVariables(cRoute);
                    } else {
                        throw new KeyAlreadyExistsException("the route: " + updates.toString() +
                                "\ncannot bee added because it has the same ID as the route: " +
                                routes.get(cRouteID));
                    }
                } else {
                    routes.put(cRouteID, cRoute);
                }
            }
        } else if (tmp instanceof Trip) {
            for (Trip cTrip : (LinkedList<Trip>) updates) {
                String cTripID = cTrip.getTripID();
                //checks if the trips list contains a trip with the same tripID
                if (trips.containsKey(cTripID)) {
                    Trip oldTrip = trips.get(cTripID);
                    if (oldTrip.isEmpty()) {
                        //gives the empty route all the variables of the newRoute
                        oldTrip.copyInstanceVariables(cTrip);
                        cTrip = oldTrip;
                    } else {
                        //throws an exception because there already exists a trip object with the same ID
                        throw new KeyAlreadyExistsException("the trip: " + cTrip.toString() +
                                "\ncannot bee added because it has the same ID as the trip: " +
                                trips.get(cTrip.getTripID()));
                    }
                } else {
                    //adds the trip to the trips list
                    trips.put(cTripID, cTrip);
                }
                //creates Id references from this item
                createIDReferences(cTrip);
            }
        } else if (tmp instanceof StopTime) {
            for (StopTime cStopTime : (LinkedList<StopTime>) updates) {
                stopTimes.add(cStopTime);
                //creates empty object from ID references in the stopTimes Object if they don't already exist
                createIDReferences(cStopTime);
            }
        }
    }

    /**
     * @author: heinzja
     * sends the contents of this subject's dataStructures to each observer
     * @throws KeyAlreadyExistsException
     */
    public void notifyObservers() {
        ArrayList<Object> dataStructures = new ArrayList<>();
        dataStructures.addAll(stops.values());
        dataStructures.addAll(routes.values());
        dataStructures.addAll(trips.values());
        dataStructures.addAll(stopTimes);
        for (Observer observer : observers) {
            observer.update(dataStructures);
        }
        updateSearchView();
    }

    /**
     * Author:hortong
     * Description: Takes in an object that is either an instanceof Trip or StopTime and checks that each
     * occurrence of an ID has an actual object belonging to it.
     *
     * @param newItem -
     */
    private void createIDReferences(Object newItem) {
        if (newItem instanceof Trip) {
            Trip trip = (Trip) newItem;
            String tmpRouteID = trip.getRouteID();
            //checks if there is already a route object for the routeID in trip
            if (!routes.containsKey(tmpRouteID)) {
                //creates new route object from routeID
                Route newRoute = new Route(tmpRouteID);
                //sets the route in the trip object to the new route
                trip.setRoute(newRoute);
                //puts the new route object in the routes set
                routes.put(tmpRouteID, newRoute);
                if (trip.hasTripList()) {
                    newRoute.copyTripListToRoute(trip);
                }
            } else {
                //sets the route in the trip object to the new route
                trip.setRoute(routes.get(tmpRouteID));
            }
        } else if (newItem instanceof StopTime) {
            StopTime stopTime = (StopTime) newItem;
            String tmpStopID = stopTime.getStopID();
            String tmpTripID = stopTime.getTripID();
            //checks if there is already a stop object for the stopID in stopTime
            if (!stops.containsKey(tmpStopID)) {
                //creates new stop object from stopID
                Stop newStop = new Stop(tmpStopID);
                //sets the stop in the stopTime object to the new stop
                stopTime.setStop(newStop);
                //adds the stopTime to the new stop object
                newStop.addStopTimes(stopTime);
                //puts the new stop in the the stops set
                stops.put(tmpStopID, newStop);
            } else {
                //adds the stop to the StopTime object and adds the StopTime object to the stop
                Stop stop = stops.get(tmpStopID);
                stopTime.setStop(stop);
                stop.addStopTimes(stopTime);
            }
            //checks if a trip with the tripID in stopTime exists
            if (!trips.containsKey(tmpTripID)) {
                //creates a new trip object from the tripID in stopTime, add the stopTime to
                // trip, and puts the new trip in the trips set
                Trip newTrip = new Trip(tmpTripID);
                newTrip.addStopTime(stopTime);
                trips.put(tmpTripID, newTrip);
            } else {
                //adds the StopTime to the trip object
                trips.get(tmpTripID).addStopTime(stopTime);
            }
        }
    }

    /**
     * Author: hortong
     * Description: Searches the stops for one containing the stop ID.
     *
     * @param stopID
     * @return the stop with the specified stopID or null if no such stop is found
     */
    public Stop searchStops(String stopID) {
        Stop result = null;
        if (stops != null) {
            NavigableSet<String> nav = stops.navigableKeySet();
            for (String id : nav) {
                if (id.equalsIgnoreCase(stopID)) {
                    result = stops.get(id);
                }
            }
        }
        return result;
    }

    /**
     * Author: Joey Hoffman
     * Description: Searches for a route by routeID.
     *
     * @param routeID
     * @return returns the route with the specified routeID or null if no such route is found
     */
    public Route searchRoutes(String routeID) {
        Route result = null;
        if (routes != null) {
            NavigableSet<String> nav = routes.navigableKeySet();
            for (String id : nav) {
                if (id.equalsIgnoreCase(routeID)) {
                    result =  routes.get(id);
                }
            }
        }
        return result;
    }

    /**
     * Author: hortong
     * Description: Searches a trip object for the specified tripID
     *
     * @param tripID - The tripID to search for
     * @return Trip object with the specified tripID or null if there is no such trip
     */
    public Trip searchTrips(String tripID) {
        Trip result = null;
        if (trips != null) {
            NavigableSet<String> nav = trips.navigableKeySet();
            for (String id : nav) {
                if (id.equalsIgnoreCase(tripID)) {
                    result = trips.get(id);
                }
            }
        }
        return result;
    }

    /**
     * Author: hortong
     * Description: Searches all the trips for the specified stopID, calls the
     * getStop method in each trip object in the trips map.
     *
     * @param stopID to search for
     * @return an Collection made of an ArrayList containing all the trips that contains
     * the Stop with the specified stopID or null if no such trip exists.
     */
    public Collection<Trip> searchTripsForStop(String stopID) throws NoSuchElementException {
        NavigableSet<String> navSet;
        ArrayList<Trip> tripsToReturn = null;
        if (trips != null) {
            navSet = trips.navigableKeySet();
            tripsToReturn = new ArrayList<>();
            for (String id : navSet) {
                final Trip tmp = trips.get(id);
                if (tmp.getStop(stopID) != null) {
                    tripsToReturn.add(tmp);
                }
            }
        }
        return tripsToReturn;
    }

    /**
     * Author: hoffman
     * Description: Searches all the routes for those containing a stop with the specified stopID.
     *
     * @param stopID of the stop to search for
     * @return an ArrayList containing all the routes that contain the stop with the specified
     * stopID or null if no such route exists.
     */
    public ArrayList<Route> searchRoutesForStop(String stopID) {
        ArrayList<Route> routesToReturn = null;
        NavigableSet<String> navSet;
        if (routes != null) {
            navSet = routes.navigableKeySet();
            routesToReturn = new ArrayList<>();
            for (String id : navSet) {
                final Route tmp = routes.get(id);
                if (tmp.searchRoute(stopID) != null) {
                    routesToReturn.add(tmp);
                }
            }
        }
        return routesToReturn;
    }

    public Collection<Observer> getObservers() {
        return observers;
    }

    public void setObservers(Collection<Observer> observers) {
        this.observers = observers;
    }

    public NavigableMap<String, Stop> getStops() {
        return stops;
    }

    public void setStops(NavigableMap<String, Stop> stops) {
        this.stops = stops;
    }

    public NavigableMap<String, Route> getRoutes() {
        return routes;
    }

    public void setRoutes(NavigableMap<String, Route> routes) {
        this.routes = routes;
    }

    public NavigableMap<String, Trip> getTrips() {
        return trips;
    }

    public void setTrips(NavigableMap<String, Trip> trips) {
        this.trips = trips;
    }

    public Collection<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(Collection<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

    /**
     * @author: hortong
     * searchs for a stop in all the dataStructures
     * @param stopID to search for
     */
    public void searchForStop(String stopID){
        ArrayList<Object> results = new ArrayList<>();
        results.add(searchStops(stopID));
        results.addAll(searchRoutesForStop(stopID));
        results.addAll(searchTripsForStop(stopID));
        searchResultsView.addSearchResults(stopID, results);

    }

    /**
     * @author: hortong
     * searchs for a route in all the dataStructures
     * @param routeID to search for
     */
    public void searchForRoute(String routeID){
        ArrayList<Object> results = new ArrayList<>();
        results.add(searchRoutes(routeID));
        results.addAll(searchTripsForStop(routeID));
        searchResultsView.addSearchResults(routeID, results);
    }

    /**
     * @author: hortong
     * searchs for a trip in all the dataStructures
     * @param tripID to search for
     */
    public void searchForTrip(String tripID){
        ArrayList<Object> results = new ArrayList<>();
        results.add(searchTrips(tripID));
        searchResultsView.addSearchResults(tripID, results);
    }

}