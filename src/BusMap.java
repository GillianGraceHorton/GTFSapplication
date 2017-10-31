import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * Author: hortong, hoffmanjc
 * Description:
 * Date: 10/3/2017 4:57:29 PM
 */
public class BusMap extends Pane implements Observer {

	private Subject dataStorage;
	private ArrayList<Stop> stops;
	private ArrayList<Route> routes;
	private ArrayList<Trip> trips;

	public BusMap(){
		stops = new ArrayList<>();
		routes = new ArrayList<>();
		trips = new ArrayList<>();
	}

	/**
	 * Author:
	 * Description:
	 * @param dataStorage
	 */
	public void setSubject(Subject dataStorage) {
		this.dataStorage = dataStorage;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public ArrayList<Stop> getStops() {
		return stops;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public ArrayList<Route> getRoutes() {
		return routes;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public ArrayList<Trip> getTrips() {
		return trips;
	}

	/**
	 * Author:
	 * Description:
	 * @param updates - Collection of objects to send to observers
	 */
	@Override
	public void update(ArrayList<Object> updates) {
		for (Object item: updates) {
			if(item instanceof Stop){ stops.add((Stop)item); }
			else if(item instanceof Route){ routes.add((Route)item); }
			else if(item instanceof Trip){ trips.add((Trip)item); }
		}
	}
}