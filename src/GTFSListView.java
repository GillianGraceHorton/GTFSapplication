import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import java.util.ArrayList;

/**
 * @author Gracie Horton
 * @version 1.0
 * Created: 03-Oct-2017 4:57:27 PM
 */
public class GTFSListView extends HBox implements Observer {

    private Subject dataStorage;
    private TextArea details;
    private TabPane tabPane;
    private Tab stopsTab;
    private Tab routesTab;
    private Tab tripsTab;
    private Tab stopTimesTab;
    private Tab routesContainingStopTab;
    private Tab routeWithStopsTab;

    private ListView stops;
    private ListView routes;
    private ListView trips;
    private ListView stopTimes;
    private TextArea routesContainingStop;
    private TextArea routeWithStops;

    private EventHandler<MouseEvent> itemClicked;


    public GTFSListView() {
        tabPane = new TabPane();
        stopsTab = new Tab("STOPS");
        routesTab = new Tab("ROUTES");
        tripsTab = new Tab("TRIPS");
        stopTimesTab = new Tab("STOP TIMES");
        tabPane.getTabs().addAll(stopsTab, routesTab, tripsTab, stopTimesTab);
        details = new TextArea();
        details.setEditable(false);
        this.getChildren().addAll(tabPane, details);


        stops = new ListView();
        routes = new ListView();
        trips = new ListView();
        stopTimes = new ListView();

        stopsTab.setContent(stops);
        routesTab.setContent(routes);
        tripsTab.setContent(trips);
        stopTimesTab.setContent(stopTimes);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        itemClicked = event -> {
            details.clear();
            Object list = event.getSource();
            Object item = ((ListView)event.getSource()).getSelectionModel().getSelectedItem();
            if(list == stopTimes){
                details.setText(((Trip)item).tripListToString());
            }else if(item instanceof Stop){
                details.setText(((Stop)item).toStringData());
            }else if(item instanceof Route){
                details.setText(((Route)item).toStringData());
            }else if(item instanceof Trip){
                details.setText(((Trip)item).toStringData());
            }
        };
        stops.setOnMouseClicked(itemClicked);
        routes.setOnMouseClicked(itemClicked);
        trips.setOnMouseClicked(itemClicked);
        stopTimes.setOnMouseClicked(itemClicked);
    }

    /**
     * adjusts the sizes of the javaFX objects
     *
     * @param height height of the object
     * @param width  width of the object
     */
    public void adjustSizes(double height, double width) {
        tabPane.setPrefWidth(width*(2.0/3.0));
        details.setPrefWidth(width/3.0);
        details.setPrefHeight(height);
    }

    public void setSubject(Subject dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * receives update from the subject
     *
     * @param addedItems items that have been updated
     */
    public void update(ArrayList<Object> addedItems) {
        ObservableList<Stop> stops =  FXCollections.observableArrayList();
        ObservableList<Route> routes =  FXCollections.observableArrayList();
        ObservableList<Trip> trips =  FXCollections.observableArrayList();
        ObservableList<Trip> stopTimes = FXCollections.observableArrayList();
        for (Object item : addedItems) {
            if (item instanceof Stop) {
                stops.add((Stop)item);
            } else if (item instanceof Route) {
                routes.add((Route)item);
            } else if (item instanceof Trip) {
                trips.add((Trip)item);
                if (((Trip)item).getTripList() != null) {
                    stopTimes.add((Trip)item);
                }

            }
            this.stops.setItems(stops);
            this.routes.setItems(routes);
            this.trips.setItems(trips);
            this.stopTimes.setItems(stopTimes);
        }
    }

    public void displayRoutesContainingStop(ArrayList<Route> routes) {
        if (routesContainingStopTab == null) {
            routesContainingStopTab = new Tab("Routes containing Stop");
            routesContainingStop = new TextArea();
            routesContainingStopTab.setContent(routesContainingStop);
            tabPane.getTabs().add(routesContainingStopTab);
        }
        String toAdd = "";
        for (Route route : routes) {
            toAdd += "RouteID: " + route.getRouteID() + ", Route Name: " + route
                    .getRouteLongName() + "\n";
        }
        routesContainingStop.setText(toAdd);
        routesContainingStop.setEditable(false);
    }

    public void displayRouteWithStops(Route route) {
        if(routeWithStopsTab == null){
            routeWithStopsTab = new Tab("Route With its Stops");
            routeWithStops = new TextArea();
            routeWithStopsTab.setContent(routeWithStops);
            tabPane.getTabs().add(routeWithStopsTab);
        }
        routeWithStops.setText(route.stopsToString());
    }
}