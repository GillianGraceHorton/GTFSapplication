import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:27 PM
 */
public class ListView extends Pane implements Observer {

	private Subject dataStorage;
	private HBox hbox;
	private TextArea stops;
	private TextArea routes;
	private TextArea trips;

	public ListView(){
		hbox = new HBox();
		stops = new TextArea("*STOPS*");
		stops.setEditable(false);
		routes = new TextArea("*ROUTES*");
		routes.setEditable(false);
		trips = new TextArea("*TRIPS*");
		trips.setEditable(false);
		hbox.getChildren().addAll(stops, routes, trips);
		this.getChildren().add(hbox);
	}

	public void adjustSizes(double height, double width){
		hbox.setPrefWidth(width);
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

	public void update(ArrayList<Object> addedItems){
		for (Object item: addedItems) {
			if(item instanceof Stop){
				stops.setText(stops.getText() + "\n" + item.toString());
			}else if(item instanceof Route){
				routes.setText(routes.getText() + "\n" + item.toString());
			}else if(item instanceof Trip){
				trips.setText(trips.getText() + "\n" + item.toString());			}
		}
	}

}