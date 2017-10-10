import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:27 PM
 */
public class ListView extends TabPane implements Observer {

	private Subject dataStorage;
	//private HBox hbox;
	private Tab stopsTab;
	private Tab routesTab;
	private Tab tripsTab;

	private TextArea stops;
	private TextArea routes;
	private TextArea trips;

	public ListView(){
		this.getTabs().addAll(stopsTab, routesTab, tripsTab);

		//hbox = new HBox();
		stops = new TextArea("*STOPS*");
		stops.setEditable(false);
		routes = new TextArea("*ROUTES*");
		routes.setEditable(false);
		trips = new TextArea("*TRIPS*");
		trips.setEditable(false);
	}

	/**
	 * adjusts the sizes of the javaFX objects
	 * @param height
	 * @param width
	 */
	public void adjustSizes(double height, double width){
		//hbox.setPrefWidth(width);
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
	 * recieves update from the subject
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
			}
		}
	}

}