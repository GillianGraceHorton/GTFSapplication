

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
    private Collection<Trip> tripsWithTimes;
    private Collection<StopTime> stopTimes;
    private Collection<Observer> observers;


    public DataStorage() {
        stops = new TreeMap<>();
        routes = new TreeMap<>();
        trips = new TreeMap<>();
        stopTimes = new ArrayList<>();
        tripsWithTimes = new ArrayList<>();
        observers = new ArrayList<>();
    }

    public void attach(Observer observer) {
        observers.add(observer);
    }

    public boolean detach(Observer observer) {
        return observers.remove(observer);
    }

    /**
     * sends updates to all observers after adding each object to its own dataStructures
     *
     * @param updates an arrayList of the objects that were added.
     */
    public void notifyObservers(ArrayList<Object> updates) throws KeyAlreadyExistsException {
        for (Object item : updates) {
            if (item instanceof Stop) {
                if(stops.containsKey(((Stop) item).getStopID())){
                    if(stops.get(((Stop) item).getStopID()).isEmpty()){
                        stops.replace(((Stop) item).getStopID(),(Stop)item);
                    }else{
                        throw new KeyAlreadyExistsException("the stop: " + item.toString() +
                                "\ncannot bee added because it has the same ID as the stop: " +
                                stops.get(((Stop)item).getStopID()));
                    }
                }else {
                    stops.put(((Stop) item).getStopID(), (Stop) item);
                }

            } else if (item instanceof Route) {
                if(routes.containsKey(((Route) item).getRouteID())){
                    if(routes.get(((Route) item).getRouteID()).isEmpty()){
                        routes.replace(((Route) item).getRouteID(),(Route)item);
                    }else{
                        throw new KeyAlreadyExistsException();
                    }
                }else {
                    routes.put(((Route) item).getRouteID(), (Route) item);
                }

            } else if (item instanceof Trip) {
                if(trips.containsKey(((Trip) item).getTripID())){
                    if(trips.get(((Trip) item).getTripID()).isEmpty()){
                        trips.replace(((Trip) item).getTripID(),(Trip)item);
                    }else{
                        throw new KeyAlreadyExistsException();
                    }
                }else {
                    trips.put(((Trip) item).getTripID(), (Trip) item);
                }

            } else if (item instanceof StopTime) {
                stopTimes.add((StopTime) item);
            }
        }
        ArrayList<Object> dataStructures = new ArrayList<>();
        dataStructures.addAll(stops.values());
        dataStructures.addAll(routes.values());
        dataStructures.addAll(trips.values());
        dataStructures.addAll(stopTimes);
        if(stopTimes.size()!=0){
            dataStructures.addAll(createIDReferencesFromStopTimes());
        }
        if (trips.size() != 0){
            dataStructures.addAll(createIDReferencesFromTrips());
            if(routes.size() != 0) {
                addRoutesToTrips();
            }
            if(stopTimes.size()!=0){
                updateTripsWithStopTimes();
            }
        }
        for (Observer observer : observers) {
            observer.update(dataStructures);
        }
    }

    /**
     * updates the trip with information loaded from the stopTimes file and adds the stopTime
     * object to is corresponding stop object where its put into an arrayList in the stop object.
     *
     * @author hortong
     */
    private void updateTripsWithStopTimes() {
        for (StopTime stopTime : stopTimes) {
            Trip tripToUpdate = searchTrips(stopTime.getTripID());
            tripsWithTimes.add(tripToUpdate);
            tripToUpdate.addStop(searchStops(stopTime.getStopID()), Integer.parseInt(stopTime
                    .getStopSequence()));
            tripToUpdate.getStop(stopTime.getStopID()).setArrivalTime(stopTime.getArrivalTime());
            tripToUpdate.getStop(stopTime.getStopID()).setDepartureTime(stopTime.getDepartureTime());
            searchStops(stopTime.getStopID()).addStopTimes(stopTime);
        }
    }

    private ArrayList<Object> createIDReferencesFromTrips(){
        ArrayList<Object> created = new ArrayList<>();
        NavigableSet<String> nav = trips.navigableKeySet();
        for (String id : nav) {
            if(!trips.get(id).isEmpty() && !routes.containsKey(trips.get(id).getRouteID())){
                Route newRoute = new Route(trips.get(id).getRouteID());
                routes.put(trips.get(id).getRouteID(), newRoute);
                created.add(newRoute);
            }
        }
        return created;
    }

    private ArrayList<Object> createIDReferencesFromStopTimes(){
        ArrayList<Object> created = new ArrayList<>();
        for (StopTime stopTime: stopTimes) {
            if(!stops.containsKey(stopTime.getStopID())){
                Stop newStop = new Stop(stopTime.getStopID());
                stops.put(stopTime.getStopID(), newStop);
                created.add(newStop);
            }
            if(!trips.containsKey(stopTime.getTripID())){
                Trip newTrip = new Trip(stopTime.getTripID());
                trips.put(stopTime.getTripID(), newTrip);
                created.add(newTrip);
            }
        }
        return created;
    }

    /**
     * updates the trip object with the route objects that they have ID's for but only if the
     * trips and routes maps are not empty
     *
     * @author hortong
     */
    private void addRoutesToTrips() {
        if (routes != null && trips != null) {
            NavigableSet<String> nav = trips.navigableKeySet();
            Trip thisTrip;
            for (String id : nav) {
                thisTrip = trips.get(id);
                if (thisTrip == null) {
                    thisTrip.setRoute(searchRoutes(thisTrip.getRouteID()));
                }
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

    public Collection<Trip> getTripsWithTimes() {
        return tripsWithTimes;
    }

    public void setTripsWithTimes(Collection<Trip> tripsWithTimes) {
        this.tripsWithTimes = tripsWithTimes;
    }

    public Collection<StopTime> getStopTimes() {
        return stopTimes;
    }

    public void setStopTimes(Collection<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }

}