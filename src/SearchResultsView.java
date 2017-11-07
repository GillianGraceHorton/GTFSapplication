import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Accordion;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class SearchResultsView extends HBox {
    private Map<String, TitledPane> searches;
    private javafx.event.EventHandler<MouseEvent> itemClicked;
    private TextArea details;
    private Accordion accordion;

    /**
     * Author:
     * Description:
     */
    public SearchResultsView() {
        searches = new TreeMap<>();
        details = new TextArea();
        details.setEditable(false);
        accordion = new Accordion();
        this.getChildren().addAll(accordion, details);

        itemClicked = event -> {
            details.clear();
            Object item = ((ListView) event.getSource()).getSelectionModel().getSelectedItem();
            if (item instanceof Stop) {
                details.setText(((Stop) item).toStringData());
            } else if (item instanceof Route) {
                details.setText(((Route) item).toStringData());
            } else if (item instanceof Trip) {
                details.setText(((Trip) item).toStringData());
            }
        };
        adjustSizes();
    }

    /**
     * Author:
     * Description:
     */
    private void adjustSizes() {
        this.setWidth(900);
        accordion.setMinWidth(this.getWidth() * (2.0 / 3.0));
        accordion.setMinHeight(400);
        details.setMinWidth(this.getWidth() * (1.0 / 3.0));
        details.setMinHeight(400);
    }

    /**
     * Author:
     * Description:
     * @param searchedFor
     * @param results
     */
    public void addSearchResults(String searchedFor, ArrayList<Object> results) {
        TitledPane newPane = new TitledPane();
        if (results.get(0) instanceof Stop) {
            newPane.setText("results of the stopID: " + searchedFor);
        } else if (results.get(0) instanceof Route) {
            newPane.setText("results of the routeID: " + searchedFor);
        } else {
            newPane.setText("results of the tripID: " + searchedFor);
        }
        VBox lists = new VBox();
        newPane.setContent(lists);
        ObservableList<Stop> stops = FXCollections.observableArrayList();
        ObservableList<Route> routes = FXCollections.observableArrayList();
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        for (Object item : results) {
            if (item instanceof Stop) {
                stops.add((Stop) item);
            } else if (item instanceof Route) {
                routes.add((Route) item);
            } else if (item instanceof Trip) {
                trips.add((Trip) item);
            }
        }
        if (!stops.isEmpty()) {
            ListView stopsListView = new ListView(stops);
            stopsListView.setOnMouseClicked(itemClicked);
            lists.getChildren().add(stopsListView);
        }
        if (!routes.isEmpty()) {
            ListView routesListView = new ListView(routes);
            routesListView.setOnMouseClicked(itemClicked);
            lists.getChildren().add(routesListView);
        }
        if (!trips.isEmpty()) {
            ListView tripsListView = new ListView(trips);
            tripsListView.setOnMouseClicked(itemClicked);
            lists.getChildren().add(tripsListView);
        }
        accordion.getPanes().add(newPane);
    }
}
