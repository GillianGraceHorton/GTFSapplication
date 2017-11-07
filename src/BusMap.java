import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
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
	WebView webView;
	WebEngine webEngine;

	public BusMap() throws FileNotFoundException, ScriptException {
		stops = new ArrayList<>();
		routes = new ArrayList<>();
		trips = new ArrayList<>();
		webView = new WebView();
		this.getChildren().add(webView);

		try {
			webView.setVisible(true);
			webEngine = webView.getEngine();
			webEngine.setJavaScriptEnabled(true);
			File file = new File("src\\loadGoogleMap.html");
			System.out.println(file.exists() + " file exitence");
			webEngine.load(file.toURI().toURL().toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	public void addStopMarker(Stop stop){
		try {
			if(!stop.isEmpty()) {
				webView.getEngine().executeScript("addStopMarker(" + stop.getLocation().getLat() + "," +
						" " + stop.getLocation().getLon() + ")");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
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
			if(item instanceof Route){ drawRoute((Route)item); }
		}
	}

	private void drawRoute(Route route){
		if(!route.getStops().isEmpty() && !route.isEmpty()) {
			for (Stop stop : route.getStops().values()) {
				addStopMarker(stop);
			}
			System.out.println(route.getRouteColor());
			webView.getEngine().executeScript("drawRoute(\"" + route.getRouteColor() + "\")");
			System.out.println("drew route");
		}
	}
}