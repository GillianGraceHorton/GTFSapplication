import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * @author hortong, hoffmanjc
 * @version 1.0
 * @created 03-Oct-2017 4:57:29 PM
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

	public void setSubject(Subject dataStorage) {
		this.dataStorage = dataStorage;
	}

	public ArrayList<Stop> getStops() {
		return stops;
	}

	public ArrayList<Route> getRoutes() {
		return routes;
	}

	public ArrayList<Trip> getTrips() {
		return trips;
	}

	@Override
	public void update(ArrayList<Object> updates) {
		for (Object item: updates) {
			if(item instanceof Stop){
				stops.add((Stop)item);
			}else if(item instanceof Route){
				routes.add((Route)item);
			}else if(item instanceof Trip){
				trips.add((Trip)item);
			}
		}
	}
}