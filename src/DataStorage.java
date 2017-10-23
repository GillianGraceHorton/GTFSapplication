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



	public DataStorage(){
		stops = new TreeMap<>();
		routes = new TreeMap<>();
		trips = new TreeMap<>();
		stopTimes = new ArrayList<>();
		tripsWithTimes = new ArrayList<>();
		observers = new ArrayList<>();
	}

	public Collection<Observer> getObservers() {
		return observers;
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
		if(stops != null) {
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
	 *
	 * @param observer
	 */
	public boolean detach(Observer observer){
		return observers.remove(observer);
	}

	/**
	 * searches for a route by routeID
	 * @author Joey Hoffman
	 * @param routeID
	 */
	public Route searchRoutes(String routeID) {
		if(routes != null) {
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
	 * sends updates to all observers after adding each object to its own dataStructures
	 * @param itemsToSend
	 */
	public void notifyObservers(ArrayList<Object> itemsToSend){
		for (Object item: itemsToSend) {
			if(item instanceof Stop){
				stops.put(((Stop)item).getStopID(), (Stop)item);
			}else if(item instanceof Route){
				routes.put(((Route)item).getRouteID(), (Route)item);
			}else if(item instanceof Trip){
				trips.put(((Trip)item).getTripID(), (Trip)item);
			}else if(item instanceof StopTime){
				stopTimes.add((StopTime)item);
			}
		}
		if (trips.size()!=0 && routes.size()!=0){
			addRoutesToTrips();
		}
		if(trips.size()!=0 && stopTimes.size()!=0){
			updateTripsWithStopTimes();
		}

		for (Observer observer: observers) {
			observer.update(itemsToSend);
		}
	}

	/**
	 * @author hortong
	 */
	private void addRoutesToTrips() {
		if(routes != null && trips != null) {
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
		if(trips != null) {
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
	 *
	 * @param stopID
	 */
	public Collection<Trip> searchTripsForStop(String stopID)
	{
		ArrayList<Trip> tripsToReturn = new ArrayList<>();
		if(trips != null) {
			NavigableSet<String> nav = trips.navigableKeySet();
			for (String id : nav) {
				if (trips.get(id).getStop(stopID) != null) {
					tripsToReturn.add(trips.get(id));
				}
			}
		}
		if(tripsToReturn.size() == 0){
			return null;
		}
		return tripsToReturn;
	}

	public ArrayList<Route> searchRoutesForStop(String stopID){
		ArrayList<Route> routesToReturn = new ArrayList<>();
		if(routes != null) {
			NavigableSet<String> nav = routes.navigableKeySet();
			for (String id : nav) {
				if (routes.get(id).searchRoute(stopID) != null) {
					routesToReturn.add(routes.get(id));
				}
			}
		}
		if(routesToReturn.size() == 0){
			return null;
		}
		return routesToReturn;
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