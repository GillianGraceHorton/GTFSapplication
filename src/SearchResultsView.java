import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.*;

public class SearchResultsView extends HBox {
    private NavigableMap<String, TitledPane> searches;
    private EventHandler<MouseEvent> itemClicked;
    private EventHandler<MouseEvent> tabRightClicked;
    private TextArea details;
    private Accordion accordion;

    /**
     * @author: hortong
     * creates a new SearchResultsView object
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

        tabRightClicked = event -> {
            System.out.println("got here 5");
            if (event.getButton() == MouseButton.SECONDARY) {
                TitledPane selected = (TitledPane)event.getSource();
                ContextMenu menu = new ContextMenu();
                MenuItem delete = new MenuItem("Delete");
                menu.getItems().add(delete);
                menu.show(selected, event.getScreenX(), event.getScreenY());
                delete.setOnAction(event1 -> accordion.getPanes().remove(selected));
            }
        };

        adjustSizes();
    }

    /**
     * @author: hortong
     * adjusts the sizes of the gui elements
     */
    private void adjustSizes() {
        this.setWidth(900);
        accordion.setMinWidth(this.getWidth() * (2.0 / 3.0));
        accordion.setMinHeight(400);
        details.setMinWidth(this.getWidth() * (1.0 / 3.0));
        details.setMinHeight(400);
    }

    /**
     * @param searchedFor the id of the object searched for
     * @param results     the results of the search
     * @author hortong
     * adds a new titled pane with the search results of a new search
     */
    public void addSearchResults(String searchedFor, ArrayList<Object> results) {
        TitledPane newPane = new TitledPane();
        newPane.setOnMouseClicked(tabRightClicked);
        String searchType;
        if (results.get(0) instanceof Stop) {
            searchType = "stop";
            newPane.setText("results of the stopID: " + searchedFor);
        } else if (results.get(0) instanceof Route) {
            searchType = "route";
            newPane.setText("results of the routeID: " + searchedFor);
        } else {
            searchType = "trip";
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
        if(searches.containsKey(searchType + " " + searchedFor)){
            accordion.getPanes().remove(searches.get(searchType + " " + searchedFor));
            searches.remove(searchType + " " + searchedFor);
        }
        accordion.getPanes().add(newPane);
        searches.put(searchType + " " + searchedFor, newPane);
    }

    public NavigableSet<String> getSearches(){
        return searches.navigableKeySet();
    }
}
