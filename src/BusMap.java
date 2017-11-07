import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.script.ScriptException;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Author: hortong, hoffmanjc
 * Description:
 * Date: 10/3/2017 4:57:29 PM
 */
public class BusMap extends HBox implements Observer {

	private Subject dataStorage;
	WebView webView;
	WebEngine webEngine;
	ListView routes;
	List<Route> selected;
	private EventHandler<MouseEvent> itemClicked;

    /**
     * @author: hortong
     * creates the webView and webEngine objects and loads the HTML file.
     * creates the listView object and the routes list.
     * adds EventHandler to listView
     * @throws FileNotFoundException
     * @throws ScriptException
     */
	public BusMap() throws FileNotFoundException, ScriptException, MalformedURLException {
		routes = new ListView();
		routes.setMinWidth(150);
		routes.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		webView = new WebView();
		webView.setMaxWidth(650);
		selected = new LinkedList<>();
		this.getChildren().addAll(webView, routes);

		webView.setVisible(true);
		webEngine = webView.getEngine();
		webEngine.setJavaScriptEnabled(true);
		File file = new File("src\\loadGoogleMap.html");
		System.out.println(file.exists() + " file exitence");
		webEngine.load(file.toURI().toURL().toString());

		itemClicked = event -> {
			webView.getEngine().executeScript("removeRoutes()");
			Object item = ((ListView)event.getSource()).getSelectionModel().getSelectedItem();
			Route route = (Route)item;
			drawRoute(route);
		};
		routes.setOnMouseClicked(itemClicked);
	}

    /**
     * @author: hortong
     * adds a marker for the specified stop to the javaScript file markers array
     * @param stop to create a marker for
     */
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

	public void setSubject(Subject dataStorage) {
		this.dataStorage = dataStorage;
	}

	/**
	 * @author: hortong
	 * Description: adds each route in the update to the listView if it isn not empty
	 * @param updates - Collection of objects sent to each observer.
	 */
	@Override
	public void update(ArrayList<Object> updates) {
		ObservableList<Route> routes =  FXCollections.observableArrayList();
		for (Object item: updates) {
			if(item instanceof Route && !((Route)item).isEmpty()){ routes.add((Route)item); }
		}
		this.routes.setItems(routes);
	}

    /**
     * @author: hortong
     * add each stop in the route to the markers array in the javaScript file, then calls the
     * javaScript method to draw the route.
     * @param route to draw
     */
	private void drawRoute(Route route){
		if(!route.getStops().isEmpty() && !route.isEmpty()) {
			for (Stop stop : route.getStops().values()) {
				addStopMarker(stop);
			}
			webView.getEngine().executeScript("drawRoute(\"" + route.getRouteColor().trim() + "\")");
		}
	}
}