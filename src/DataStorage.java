import java.util.Collection;

/**
 * @author hortong
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
	 * 
	 * @param routeID
	 */
	public Route searchRoutes(String routeID){
		return null;
	}

	public void notifyObservers(){

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