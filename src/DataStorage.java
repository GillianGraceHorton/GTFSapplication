

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

    /**
     * Author:
     * Description:
     * @param observer - the observer to attach
     */
    public void attach(Observer observer) {
        observers.add(observer);
    }

    /**
     * Author:
     * Description:
     * @param observer
     * @return boolean - result of removing an observer
     */
    public boolean detach(Observer observer) {
        return observers.remove(observer);
    }

    /**
     * Author: hortong
     * Description: Takes an arrayList of objects created by files loaded in the file manager and adds each object.
     * @param updates - ArrayList of objects created by the file loaded in the File manager class
     * @throws KeyAlreadyExistsException - if one of the items from updates has an ID that matches
     * one that is already in data structures and is not a placeholder.
     */
    public void updateFromFiles(LinkedList updates) throws KeyAlreadyExistsException{
        for (Object item : updates) {
            if (item instanceof Stop) {
                Stop thisStop = (Stop) item;
                //checks if the stops list contains a stop with the same stopID
                if (stops.containsKey(thisStop.getStopID())) {
                    Stop oldStop = stops.get(thisStop.getStopID());
                    if (oldStop.isEmpty()) {
                        //gives the empty stop all the variables of the newStop
                        oldStop.copyInstanceVariables(thisStop);
                    } else {
                        throw new KeyAlreadyExistsException("the stop: " + item.toString() +
                                "\ncannot bee added because it has the same ID as the stop: " +
                                stops.get(thisStop.getStopID()));
                    }
                } else {
                    stops.put(thisStop.getStopID(), thisStop);
                }
            } else if (item instanceof Route) {
                Route thisRoute = (Route) item;
                if (routes.containsKey(thisRoute.getRouteID())) {
                    Route oldRoute = routes.get(thisRoute.getRouteID());
                    if (routes.get(thisRoute.getRouteID()).isEmpty()) {
                        //gives the empty route all the variables of the newRoute
                        oldRoute.copyInstanceVariables(thisRoute);
                    } else {
                        throw new KeyAlreadyExistsException("the route: " + item.toString() +
                                "\ncannot bee added because it has the same ID as the route: " +
                                routes.get(thisRoute.getRouteID()));
                    }
                } else {
                    routes.put(thisRoute.getRouteID(), thisRoute);
                }
            } else if (item instanceof Trip) {
                Trip thisTrip = (Trip) item;
                //checks if the trips list contains a trip with the same tripID
                if (trips.containsKey(thisTrip.getTripID())) {
                    Trip oldTrip = trips.get(thisTrip.getTripID());
                    if (oldTrip.isEmpty()) {
                        //gives the empty route all the variables of the newRoute
                        oldTrip.copyInstanceVariables(thisTrip);
                        thisTrip = oldTrip;
                    } else {
                        //throws an exception because there already exists a trip object with the
                        // same ID
                        throw new KeyAlreadyExistsException("the trip: " + item.toString() +
                                "\ncannot bee added because it has the same ID as the trip: " +
                                trips.get(thisTrip.getTripID()));
                    }
                } else {
                    //adds the trip to the trips list
                    trips.put(thisTrip.getTripID(), thisTrip);
                }
                //creates Id references from this item
                createIDReferences(thisTrip);
            } else if (item instanceof StopTime) {
                try{
                stopTimes.add((StopTime) item);
                //creates empty object from ID references in the stopTimes Object if they don't
                // already exist
                createIDReferences(item);}
                catch(NullPointerException e){
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * sends the contents of this subject's dataStructures to each observer
     * @throws KeyAlreadyExistsException
     */
    public void notifyObservers(){
        ArrayList<Object> dataStructures = new ArrayList<>();
        dataStructures.addAll(stops.values());
        dataStructures.addAll(routes.values());
        dataStructures.addAll(trips.values());
        dataStructures.addAll(stopTimes);
        for (Observer observer : observers) {
            observer.update(dataStructures);
        }
    }

    /**
     * Author:
     * Description: Takes in an object that is either an instanceof Trip or StopTime and checks that each
     *              occurrence of an ID has an actual object belonging to it.
     * @param newItem -
     */
    private void createIDReferences(Object newItem){
        if(newItem instanceof Trip){
            Trip trip = (Trip)newItem;
            //checks if there is already a route object for the routeID in trip
            if(!routes.containsKey(trip.getRouteID())){
                //creates new route object from routeID
                Route newRoute = new Route(trip.getRouteID());
                //sets the route in the trip object to the new route
                trip.setRoute(newRoute);
                //puts the new route object in the routes set
                routes.put(trip.getRouteID(), newRoute);
            }else{
                //sets the route in the trip object to the new route
                trip.setRoute(routes.get(trip.getRouteID()));
            }
        }else if (newItem instanceof StopTime){
            StopTime stopTime = (StopTime)newItem;
            //checks if there is already a stop object for the stopID in stopTime
            if(!stops.containsKey(stopTime.getStopID())){
                //creates new stop object from stopID
                Stop newStop = new Stop(stopTime.getStopID());
                //sets the stop in the stopTime object to the new stop
                stopTime.setStop(newStop);
                //adds the stopTime to the new stop object
                newStop.addStopTimes(stopTime);
                //puts the new stop in the the stops set
                stops.put(stopTime.getStopID(), newStop);
            }else{
                //adds the stop to the StopTime object and adds the StopTime object to the stop
                Stop stop = stops.get(stopTime.getStopID());
                stopTime.setStop(stop);
                stop.addStopTimes(stopTime);

            }
            //checks if a trip with the tripID in stopTime exists
            if(!trips.containsKey(stopTime.getTripID())){
                //creates a new trip object from the tripID in stopTime, add the stopTime to
                // trip, and puts the new trip in the trips set
                Trip newTrip = new Trip(stopTime.getTripID());
                newTrip.addStopTime(stopTime);
                trips.put(stopTime.getTripID(), newTrip);
            }else{
                //adds the StopTime to the trip object
                trips.get(stopTime.getTripID()).addStopTime(stopTime);
            }
        }
    }

    /**
     * Author:
     * Description: Searches the stops for one containing the stop ID.
     * @param stopID
     * @return the stop with the specified stopID or null if no such stop is found
     */
    public Stop searchStops(String stopID) {
        if (stops != null) {
            NavigableSet<String> nav = stops.navigableKeySet();
            for (String id : nav) {
                if (id.equalsIgnoreCase(stopID)) {
                    return stops.get(id);
                }
            }
        }
        return null;
    }

    /**
     * Author: Joey Hoffman
     * Description: Searches for a route by routeID.
     * @param routeID
     * @return returns the route with the specified routeID or null if no such route is found
     */
    public Route searchRoutes(String routeID) {
        if (routes != null) {
            NavigableSet<String> nav = routes.navigableKeySet();
            for (String id : nav) {
                if (id.equalsIgnoreCase(routeID)) {
                    return routes.get(id);
                }
            }
        }
        return null;
    }

    /**
     * Author:
     * Description: Searches a trip object for the specified tripID
     * @param tripID - The tripID to search for
     * @return Trip object with the specified tripID or null if there is no such trip
     */
    public Trip searchTrips(String tripID) {
        if (trips != null) {
            NavigableSet<String> nav = trips.navigableKeySet();
            for (String id : nav) {
                if (id.equalsIgnoreCase(tripID)) {
                    return trips.get(id);
                }
            }
        }
        return null;
    }

    /**
     * Author:
     * Description: Searches all the trips for the specified stopID, calls the
     *              getStop method in each trip object in the trips map.
     * @param stopID to search for
     * @return an Collection made of an ArrayList containing all the trips that contains
     *          the Stop with the specified stopID or null if no such trip exists.
     */
    public Collection<Trip> searchTripsForStop(String stopID) throws NoSuchElementException {
        NavigableSet<String> navSet;
        ArrayList<Trip> tripsToReturn = null;
        if (trips != null) {
            navSet = trips.navigableKeySet();
            tripsToReturn = new ArrayList<>();
            for (String id : navSet) {
                final Trip tmp = trips.get(id);
                if (tmp.getStop(stopID) != null) { tripsToReturn.add(tmp); }
            }
            if (tripsToReturn.size() == 0) { throw new NoSuchElementException("Error: No Route Contains the StopID: " + stopID); }
        }
        return tripsToReturn;
    }

    /**
     * Author:
     * Description: Searches all the routes for those containing a stop with the specified stopID.
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
                if (tmp.searchRoute(stopID) != null) { routesToReturn.add(tmp); }
            }
            if (routesToReturn.size() == 0) { routesToReturn = null; }
        }
        return routesToReturn;
    }

    /**
     * Author:
     * Description:
     * @return
     */
    public Collection<Observer> getObservers() {
        return observers;
    }

    /**
     * Author:
     * Description:
     * @param observers
     */
    public void setObservers(Collection<Observer> observers) {
        this.observers = observers;
    }

    /**
     * Author:
     * Description:
     * @return
     */
    public NavigableMap<String, Stop> getStops() {
        return stops;
    }

    /**
     * Author:
     * Description:
     * @param stops
     */
    public void setStops(NavigableMap<String, Stop> stops) {
        this.stops = stops;
    }

    /**
     * Author:
     * Description:
     * @return
     */
    public NavigableMap<String, Route> getRoutes() {
        return routes;
    }

    /**
     * Author:
     * Description:
     * @param routes
     */
    public void setRoutes(NavigableMap<String, Route> routes) {
        this.routes = routes;
    }

    /**
     * Author:
     * Description:
     * @return
     */
    public NavigableMap<String, Trip> getTrips() {
        return trips;
    }

    public void setTrips(NavigableMap<String, Trip> trips) {
        this.trips = trips;
    }

    /**
     * Author:
     * Description:
     * @return
     */
    public Collection<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(Collection<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

}