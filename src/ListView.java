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
	private VBox stops;
	private VBox routes;
	private VBox trips;

	public ListView(){
		hbox = new HBox();
		stops = new VBox();
		routes = new VBox();
		trips = new VBox();
		hbox.getChildren().addAll(stops, routes, trips);
	}

	public void setSubject(Subject dataStorage){
		this.dataStorage = dataStorage;
	}

	public void update(ArrayList<Object> addedItems){
		for (Object item: addedItems) {
			if(item instanceof Stop){
				stops.getChildren().add(new TextArea(item.toString()));
			}else if(item instanceof Route){
				routes.getChildren().add(new TextArea(item.toString()));
			}else if(item instanceof Trip){
				trips.getChildren().add(new TextArea(item.toString()));
			}
		}
	}

}