import javafx.scene.layout.Pane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

		webEngine = webView.getEngine();
		webEngine.load(getClass().getResource("loadGoogleMap.html").toString());

		addStopMarker(new Stop(44.810060,-89.497640, "", "", ""));
	}

	public void addStopMarker(Stop stop){
		try {
			webEngine.executeScript("document.addStopMarker(" + stop.getLocation().getLat() + ", " +
					stop.getLocation().getLon() + ")");
		}catch(Exception e){
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
			if(item instanceof Stop){ stops.add((Stop)item); }
			else if(item instanceof Route){ routes.add((Route)item); }
			else if(item instanceof Trip){ trips.add((Trip)item); }
		}
	}
}