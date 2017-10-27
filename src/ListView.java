import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * @author Gracie Horton
 * @version 1.0
 * Created: 03-Oct-2017 4:57:27 PM
 */
public class ListView extends HBox implements Observer {

    private Subject dataStorage;
    private TextArea details;
    private TabPane tabPane;
    private Tab stopsTab;
    private Tab routesTab;
    private Tab tripsTab;
    private Tab stopTimesTab;
    private Tab routesContainingStopTab;
    private Tab routeWithStopsTab;

    private VBox stops;
    private VBox routes;
    private VBox trips;
    private VBox stopTimes;
    private TextArea routesContainingStop;
    private TextArea routeWithStops;

    private EventHandler<MouseEvent> labelClicked;


    public ListView() {
        tabPane = new TabPane();
        stopsTab = new Tab("STOPS");
        routesTab = new Tab("ROUTES");
        tripsTab = new Tab("TRIPS");
        stopTimesTab = new Tab("STOP TIMES");
        tabPane.getTabs().addAll(stopsTab, routesTab, tripsTab, stopTimesTab);
        details = new TextArea();
        details.setEditable(false);
        this.getChildren().addAll(tabPane, details);


        stops = new VBox();
        ScrollPane stopScroll = new ScrollPane(stops);
        routes = new VBox();
        ScrollPane routeScroll = new ScrollPane(routes);
        trips = new VBox();
        ScrollPane tripScroll = new ScrollPane(trips);
        stopTimes = new VBox();
        ScrollPane timeScroll = new ScrollPane(stopTimes);

        stopsTab.setContent(stopScroll);
        routesTab.setContent(routeScroll);
        tripsTab.setContent(tripScroll);
        stopTimesTab.setContent(timeScroll);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        labelClicked = event -> {
            details.clear();
            GTFSLabel label = (GTFSLabel)event.getSource();
            if(label.isStopTime()){
                details.setText(((Trip)label.getItem()).tripListToString());
            }else {
                details.setText(label.getItem().toString());
            }
        };
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
        clearDataTabs();
        for (Object item : addedItems) {
            if (item instanceof Stop) {
                Stop stop = (Stop)item;
                stop.updateLabelName();
                stop.addEventHandler(labelClicked);
                stops.getChildren().addAll(stop.getStopLabel(), new Separator());
            } else if (item instanceof Route) {
                Route route = (Route)item;
                route.updateLabelName();
                route.addEventHandler(labelClicked);
                routes.getChildren().addAll(route.getRouteLabel(), new Separator());
            } else if (item instanceof Trip) {
                Trip trip = (Trip)item;
                trip.updateLabelName();
                trip.addEventHandler(labelClicked);
                trips.getChildren().addAll(trip.getTripLabel(), new Separator());
                if (trip.getTripList() != null) {
                    stopTimes.getChildren().addAll(trip.getTripListLabel(), new Separator());
                }
            }
        }
    }

    private void clearDataTabs() {
        stops.getChildren().clear();
        routes.getChildren().clear();
        trips.getChildren().clear();
        stopTimes.getChildren().clear();
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