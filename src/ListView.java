import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;

import java.util.ArrayList;

/**
 * @author Gracie Horton
 * @version 1.0
 * Created: 03-Oct-2017 4:57:27 PM
 */
public class ListView extends TabPane implements Observer {

	private Subject dataStorage;
	private Tab stopsTab;
	private Tab routesTab;
	private Tab tripsTab;
	private Tab stopTimesTab;
	private Tab routesContainingStopTab;

	private TextArea stops;
	private TextArea routes;
	private TextArea trips;
	private TextArea stopTimes;
	private TextArea routesContainingStop;


	public ListView(){
		stopsTab = new Tab("STOPS");
		routesTab = new Tab("ROUTES");
		tripsTab = new Tab("TRIPS");
		stopTimesTab = new Tab("STOP TIMES");
		this.getTabs().addAll(stopsTab, routesTab, tripsTab, stopTimesTab);

		stops = new TextArea();
		stops.setEditable(false);
		routes = new TextArea();
		routes.setEditable(false);
		trips = new TextArea();
		trips.setEditable(false);
		stopTimes = new TextArea();
		stopTimes.setEditable(false);

		stopsTab.setContent(stops);
		routesTab.setContent(routes);
		tripsTab.setContent(trips);
		stopTimesTab.setContent(stopTimes);

		this.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
	}

	/**
	 * adjusts the sizes of the javaFX objects
	 * @param height height of the object
	 * @param width width of the object
	 */
	public void adjustSizes(double height, double width){
		stops.setPrefWidth(width/3);
		stops.setPrefHeight(height);
		routes.setPrefWidth(width/3);
		routes.setPrefHeight(height);
		trips.setPrefWidth(width/3);
		trips.setPrefHeight(height);
	}

	public void setSubject(Subject dataStorage){
		this.dataStorage = dataStorage;
	}

	/**
	 * receives update from the subject
	 * @param addedItems items that have been updated
	 */
	public void update(ArrayList<Object> addedItems){
		for (Object item: addedItems) {
			if(item instanceof Stop){
				stops.setText(stops.getText() + "\n" + item.toString());
			}else if(item instanceof Route){
				routes.setText(routes.getText() + "\n" + item.toString());
			}else if(item instanceof Trip){
				trips.setText(trips.getText() + "\n" + item.toString());
				if (((Trip)item).getTripList() != null){
					stopTimes.setText(stopTimes.getText() + "\n" + ((Trip)item).tripListToString());
				}
			}
		}
	}

	public void displayRoutesContainingStop(ArrayList<Route> routes){
		if(routesContainingStopTab == null){
			routesContainingStopTab = new Tab("Routes containing Stop");
			routesContainingStop = new TextArea();
			routesContainingStopTab.setContent(routesContainingStop);
			this.getTabs().add(routesContainingStopTab);
		}
		String toAdd = "";
		for (Route route: routes) {
			toAdd += "RouteID: " + route.getRouteID() + ", Route Name: " + route
					.getRouteLongName() + "\n";
		}
		routesContainingStop.setText(toAdd);
		routesContainingStop.setEditable(false);
	}
}