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
	private Collection<Observer> observers;

	public DataStorage(){

	}

	public void setObservers(Collection<Observer> observers) {
		this.observers = observers;
	}

	/**
	 * 
	 * @param observer
	 */
	public void attach(Observer observer){

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

	}

	/**
	 * searches for a route by routeID
	 * @param routeID
	 */
	public Route searchRoutes(String routeID) {
		Iterator<Route> iterator = routes.iterator();
		for (Route route : routes) {
				if (route.getRouteID().equalsIgnoreCase(routeID)) {
					break;
				} else {
					iterator.next();
				}
		}
		return iterator.next();
	}

	/**
	 * sends updates to all observers
	 * @param itemsToSend
	 */
	public void notifyObservers(ArrayList<Object> itemsToSend){
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