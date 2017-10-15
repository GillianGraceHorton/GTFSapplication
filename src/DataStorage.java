import java.util.ArrayList;
import java.util.Collection;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:21 PM
 */
public class DataStorage implements Subject {

	private Collection<Stop> stops;
	private Collection<Route> routes;
	private Collection<Trip> trips;
	private Collection<Trip> tripsWithTimes;
	private Collection<StopTime> stopTimes;
	private Collection<Observer> observers;

	public DataStorage(){
		stops = new ArrayList<>();
		routes = new ArrayList<>();
		trips = new ArrayList<>();
		stopTimes = new ArrayList<>();
		tripsWithTimes = new ArrayList<>();
	}

	public void setObservers(Collection<Observer> observers) {
		this.observers = observers;
	}

	/**
	 *
	 * @param observer
	 */
	public void attach(Observer observer){
		observers.add(observer);
	}

	/**
	 *
	 * @param stopID
	 */
	public Stop searchStops(String stopID){
		for (Stop stop: stops) {
			if (stop.getStopID().equals(stopID)){
				return stop;
			}
		}
		return null;
	}

	/**
	 *
	 * @param observer
	 */
	public void detach(Observer observer){
		observers.remove(observer);
	}

	/**
	 * searches for a route by routeID
	 * @author Joey Hoffman
	 * @param routeID
	 */
	public Route searchRoutes(String routeID) {
		Route result = null;
		for (Route route : routes) {
			if (route.getRouteID().equalsIgnoreCase(routeID)) {
				result = route;
			}
		}
		return result;
	}

	/**
	 * sends updates to all observers after adding each object to its own dataStructures
	 * @param itemsToSend
	 */
	public void notifyObservers(ArrayList<Object> itemsToSend){
		for (Object item: itemsToSend) {
			if(item instanceof Stop){
				stops.add((Stop)item);
			}else if(item instanceof Route){
				routes.add((Route)item);
			}else if(item instanceof Trip){
				trips.add((Trip)item);
			}else if(item instanceof StopTime){
				stopTimes.add((StopTime)item);
			}
		}
		if(trips.size()!=0 && stopTimes.size()!=0){
			updateTripsWithStopTimes();
		}
		if (trips.size()!=0 && routes.size()!=0){
			addRoutesToTrips();
		}
		for (Observer observer: observers) {
			observer.update(itemsToSend);
		}
	}

	/**
	 * @author hortong
	 */
	private void addRoutesToTrips() {
		for (Trip trip:trips) {
			if(trip.getRoute() == null) {
				trip.setRoute(searchRoutes(trip.getRouteID()));
			}
		}
	}

	/**
	 * @author hortong
	 * updates the trip with information loaded from the stopTimes file and adds the stopTime
	 * object to is corresponding stop object where its put into an arrayList in the stop object.
	 */
	private void updateTripsWithStopTimes() {
		for (StopTime stopTime: stopTimes) {
			Trip tripToUpdate = searchTrips(stopTime.getTripID());
			tripsWithTimes.add(tripToUpdate);
			tripToUpdate.addStop(searchStops(stopTime.getStopID()), Integer.parseInt(stopTime
					.getStopSequence()));
			tripToUpdate.getStop(stopTime.getStopID()).setArrivalTime(stopTime.getArrivalTime());
			tripToUpdate.getStop(stopTime.getStopID()).setDepartureTime(stopTime.getDepartureTime());
			searchStops(stopTime.getStopID()).addStopTimes(stopTime);
		}
	}

	/**
	 *
	 * @param tripID
	 */
	public Trip searchTrips(String tripID){
		for (Trip trip: trips) {
			if (trip.getTripID().equals(tripID)){
				return trip;
			}
		}
		return null;
	}

	/**
	 *
	 * @param stopID
	 */
	public Collection<Trip> searchTripsForStop(String stopID)
	{
		ArrayList<Trip> tripsToReturn = new ArrayList<>();
		for (Trip trip: trips) {
			if(trip.getStop(stopID) != null){
				tripsToReturn.add(trip);
			}
		}
		if(tripsToReturn.size() >= 0){
			return tripsToReturn;
		}
		return null;
	}

	public Collection<Route> searchRoutesForStop(String stopID){
		ArrayList<Route> routesToReturn = new ArrayList<>();
		for (Route route: routes) {
			if(route.searchRoute(stopID) != null){
				routesToReturn.add(route);
			}
		}
		if(routesToReturn.size() >= 0){
			return routesToReturn;
		}
		return null;
	}

	public Collection<Stop> getStops() {
		return stops;
	}

	public void setStops(Collection<Stop> stops) {
		this.stops = stops;
	}

	public Collection<Route> getRoutes() {
		return routes;
	}

	public void setRoutes(Collection<Route> routes) {
		this.routes = routes;
	}

	public Collection<Trip> getTrips() {
		return trips;
	}

	public void setTrips(Collection<Trip> trips) {
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