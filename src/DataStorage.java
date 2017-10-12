import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

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
	private Collection<Observer> observers;

	public DataStorage(){
		stops = new ArrayList<Stop>();
		routes = new ArrayList<Route>();
		trips = new ArrayList<Trip>();
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
	 * sends updates to all observers
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
			}
		}
		for (Observer observer: observers) {
			observer.update(itemsToSend);
		}
	}

	/**
	 * 
	 * @param tripID
	 */
	public Trip searchTrips(String tripID){
		return null;
	}

	/**
	 * 
	 * @param stopID
	 */
	public Collection<Trip> searchTripsForStop(String stopID){
		return null;
	}

}