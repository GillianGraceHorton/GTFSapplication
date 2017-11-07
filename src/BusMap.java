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

    public BusMap() {
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

    public ArrayList<Trip> getTrips() { return trips; }

    /**
     * Author:
     * Description: updates observers with the Stops, Routes, or Trips stored
     *
     * @param updates - Collection of objects to send to observers
     */
    @Override
    public void update(ArrayList<Object> updates) {
        Object firstItem = updates.get(0);
        if(firstItem instanceof  Stop){
            for (Object item : updates) {
                stops.add((Stop) item);
            }
        }
        else if (firstItem instanceof  Route) {
            for (Object item : updates) {
                routes.add((Route) item);
            }
        }
        else if (firstItem instanceof  Trip) {
            for (Object item : updates) {
                trips.add((Trip) item);
            }
        }
//        for (Object item : updates) {
//            if (item instanceof Stop) { stops.add((Stop) item); }
//            else if (item instanceof Route) { routes.add((Route) item); }
//            else if (item instanceof Trip) { trips.add((Trip) item); }
//        }
    }
}