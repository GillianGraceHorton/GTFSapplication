

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.*;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:21 PM
 */
public class DataStorage implements Subject {

    private NavigableMap<String, Stop> stops;
    private NavigableMap<String, Route> routes;
    private NavigableMap<String, Trip> trips;
    private Collection<StopTime> stopTimes;
    private Collection<Observer> observers;


    public DataStorage() {
        stops = new TreeMap<>();
        routes = new TreeMap<>();
        trips = new TreeMap<>();
        stopTimes = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public boolean detach(Observer observer) {
        return observers.remove(observer);
    }

    /**
     * takes an arrayList of objects created by files loaded in the file manager and adds each
     * object.
     * @param updates arrayList of objects created by the file loaded in the File manager class
     * @throws KeyAlreadyExistsException if one of the items from updates has an ID that matches
     * one that is already in data structures and is not a placeholder.
     * @author hortong
     */
    public void updateFromFiles(ArrayList<Object> updates) throws KeyAlreadyExistsException{
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
     *
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

    private void createIDReferences(Object newItem){
        if(newItem instanceof Trip){
            Trip trip = (Trip)newItem;
                if(!routes.containsKey(trip.getRouteID())){
                    Route newRoute = new Route(trip.getRouteID());
                    trip.setRoute(newRoute);
                    routes.put(trip.getRouteID(), newRoute);
                }else{
                    trip.setRoute(routes.get(trip.getRouteID()));
                }
        }else if (newItem instanceof StopTime){
            StopTime stopTime = (StopTime)newItem;
            if(!stops.containsKey(stopTime.getStopID())){
                Stop newStop = new Stop(stopTime.getStopID());
                stopTime.setStop(newStop);
                newStop.addStopTimes(stopTime);
                stops.put(stopTime.getStopID(), newStop);
            }else{
                Stop stop = stops.get(stopTime.getStopID());
                stopTime.setStop(stop);
                stop.addStopTimes(stopTime);

            }
            if(!trips.containsKey(stopTime.getTripID())){
                Trip newTrip = new Trip(stopTime.getTripID());
                newTrip.addStopTime(stopTime);
                trips.put(stopTime.getTripID(), newTrip);
            }else{
                trips.get(stopTime.getTripID()).addStopTime(stopTime);
            }
        }
    }

    /**
     * searches the stops for one containing the stop ID.
     *
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
     * searches for a route by routeID
     *
     * @param routeID
     * @return returns the route with the specified routeID or null if no such route is found
     * @author Joey Hoffman
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
     * searches a trip object for the specified tripID
     *
     * @param tripID to search for
     * @return the trip with the specified tripID or null if there is no such trip
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
     * searches all the trips for the specified stopID, calls the getStop method in each trip object
     * in the trips map.
     *
     * @param stopID to search for
     * @return an ArrayList containing all the trips that contain the stop with the specified
     * stopID or null if no such trip exists.
     */
    public Collection<Trip> searchTripsForStop(String stopID) {
        ArrayList<Trip> tripsToReturn = new ArrayList<>();
        if (trips != null) {
            NavigableSet<String> nav = trips.navigableKeySet();
            for (String id : nav) {
                if (trips.get(id).getStop(stopID) != null) {
                    tripsToReturn.add(trips.get(id));
                }
            }
        }
        if (tripsToReturn.size() == 0) {
            return null;
        }
        return tripsToReturn;
    }

    /**
     * searches all the routes for those containing a stop with the specified stopID.
     *
     * @param stopID of the stop to search for
     * @return an ArrayList containing all the routes that contain the stop with the specified
     * stopID or null if no such route exists.
     */
    public ArrayList<Route> searchRoutesForStop(String stopID) {
        ArrayList<Route> routesToReturn = new ArrayList<>();
        if (routes != null) {
            NavigableSet<String> nav = routes.navigableKeySet();
            for (String id : nav) {
                if (routes.get(id).searchRoute(stopID) != null) {
                    routesToReturn.add(routes.get(id));
                }
            }
        }
        if (routesToReturn.size() == 0) {
            return null;
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

}