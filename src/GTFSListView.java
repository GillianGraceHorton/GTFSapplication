import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Author: Gracie Horton
 * Description:
 * Date Created: 10/3/2017 - 4:57:27 PM
 */
public class GTFSListView extends HBox implements Observer {

    private Subject dataStorage;
    private DataView dataView;
    private Stage dataStage;
    private VBox dataBox;
    private VBox detailsBox;
    private TextArea details;
    private TabPane tabPane;
    private Tab stopsTab;
    private Tab routesTab;
    private Tab tripsTab;
    private Tab stopTimesTab;
    private Tab routesContainingStopTab;
    private Tab routeWithStopsTab;

    private ListView<Stop> stops;
    private ListView<Route> routes;
    private ListView<Trip> trips;
    private ListView<Trip> stopTimes;
    private ListView<Object> elementsView;
    private TextArea routesContainingStop;
    private TextArea routeWithStops;

    private EventHandler<MouseEvent> itemClicked;
    private EventHandler<MouseEvent> elementClicked;


    /**
     * Author:
     * Description: Initializes the GTFSListView class
     */
    public GTFSListView() {
        tabPane = new TabPane();
        stopsTab = new Tab("STOPS");
        routesTab = new Tab("ROUTES");
        tripsTab = new Tab("TRIPS");
        stopTimesTab = new Tab("STOP TIMES");

        dataView = new DataView();
        dataStage = new Stage();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("DataView.fxml"));
            Parent root;
            root = loader.load();
            dataView = loader.getController();
            dataStage.setScene(new Scene(root));
            dataBox = dataView.getDataBox();
            dataView.setVisibility(false, false, false, false, false, false, false, false);
        } catch(IOException e) {
            System.out.println("Could not load DataView.fxml");
        }

        tabPane.getTabs().addAll(stopsTab, routesTab, tripsTab, stopTimesTab);
        details = new TextArea();
        detailsBox = new VBox();
        elementsView = new ListView<>();
        detailsBox.getChildren().addAll();
        detailsBox.getChildren().addAll(details, elementsView, dataBox);
        details.setEditable(false);

        this.getChildren().addAll(tabPane, detailsBox);

        stops = new ListView<>();
        routes = new ListView<>();
        trips = new ListView<>();
        stopTimes = new ListView<>();

        stopsTab.setContent(stops);
        routesTab.setContent(routes);
        tripsTab.setContent(trips);
        stopTimesTab.setContent(stopTimes);

        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        itemClicked = event -> {
            dataView.setVisibility(false, false, false, false, false, false, false, false);
            details.clear();
            Object list = event.getSource();
            Object item = ((ListView) event.getSource()).getSelectionModel().getSelectedItem();
            if (list == stopTimes) {
                details.setText(((Trip) item).tripListToString());
            } else if (item instanceof Stop) {
                details.setText(((Stop) item).toStringData());
                elementsView.getItems().clear();
                ObservableList<Object> stopTimes = FXCollections.observableArrayList();
                ArrayList<StopTime> stopTimesList = ((Stop) item).getStopTimes();
                if(stopTimesList != null && stopTimesList.size() != 0) {
                    for (StopTime o : stopTimesList) {
                        stopTimes.add(o);
                    }
                    elementsView.setItems(stopTimes);
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Warning");
                    alert.setHeaderText("StopTimes");
                    alert.setContentText("No StopTimes Associated With Stop " + ((Stop) item).getStopID() + ".");
                    alert.showAndWait();
                }
            } else if (item instanceof Route) {
                details.setText(((Route) item).toStringData());
                elementsView.getItems().clear();
                //TODO: List Any Elements of Route.
            } else if (item instanceof Trip) {
                details.setText(((Trip) item).toStringData());
                elementsView.getItems().clear();
                //TODO: List Any Elements of Trip.
            } else if (item instanceof StopTime) {
                details.setText(((StopTime) item).toString());
                elementsView.getItems().clear();
                //TODO: List Any Elements of StopTime.
            }
        };

        elementClicked = event -> {
            Object item = ((ListView) event.getSource()).getSelectionModel().getSelectedItem();
            if (item instanceof Stop) {
                //TODO: Pop up for editing Routes. Can change ListView to GUI element with back button option later.
                dataView.setVisibility(false, false, false, false, false, false, false, false);
            } else if (item instanceof Route) {
                //TODO: Pop up for editing Routes. Can change ListView to GUI element with back button option later.
                dataView.setVisibility(false, false, false, false, false, false, false, false);
            } else if (item instanceof Trip) {
                //TODO: Pop up for editing trips. Can change ListView to GUI element with back button option later.
                dataView.setVisibility(false, false, false, false, false, false, false, false);
            } else if (item instanceof StopTime) {
                dataView.setVisibility(true, true, true, true, true, true, true, true)
                        .setData(item)
                        .fillData();
            }
        };

        stops.setOnMouseClicked(itemClicked);
        routes.setOnMouseClicked(itemClicked);
        trips.setOnMouseClicked(itemClicked);
        stopTimes.setOnMouseClicked(itemClicked);
        elementsView.setOnMouseClicked(elementClicked);
    }

    /**
     * Author:
     * Description: Adjusts the sizes of the javaFX objects
     *
     * @param height height of the object
     * @param width  width of the object
     */
    public void adjustSizes(double height, double width) {
        tabPane.setPrefWidth(width * (2.0 / 3.0));
        details.setPrefWidth(width / 3.0);
        details.setPrefHeight(height/4);
        elementsView.setPrefWidth(width / 3.0);
        elementsView.setPrefHeight(height/4);
        dataBox.setPrefWidth(width / 3.0);
        dataBox.setPrefHeight(height / 2);
    }

    /**
     * Author:
     * Description:
     *
     * @param dataStorage
     */
    public void setSubject(Subject dataStorage) {
        this.dataStorage = dataStorage;
    }

    /**
     * Author: Gracie Horton
     * Description: Receives update from the subject
     *
     * @param addedItems items that have been updated
     */
    public void update(ArrayList<Object> addedItems) {
        ObservableList<Stop> stops = FXCollections.observableArrayList();
        ObservableList<Route> routes = FXCollections.observableArrayList();
        ObservableList<Trip> trips = FXCollections.observableArrayList();
        ObservableList<Trip> stopTimes = FXCollections.observableArrayList();
        for (Object item : addedItems) {
            if (item instanceof Stop) {
                stops.add((Stop) item);
            } else if (item instanceof Route) {
                routes.add((Route) item);
            } else if (item instanceof Trip) {
                trips.add((Trip) item);
                if (((Trip) item).getTripList() != null) {
                    stopTimes.add((Trip) item);
                }

            }
            this.stops.setItems(stops);
            this.routes.setItems(routes);
            this.trips.setItems(trips);
            this.stopTimes.setItems(stopTimes);
        }
    }

    /**
     * Author:
     * Description:
     *
     * @param routes
     */
    public void displayRoutesContainingStop(ArrayList<Route> routes) {
        if (routesContainingStopTab == null) {
            routesContainingStopTab = new Tab("Routes containing Stop");
            routesContainingStop = new TextArea();
            routesContainingStopTab.setContent(routesContainingStop);
            tabPane.getTabs().add(routesContainingStopTab);
        }
        StringBuilder toAdd = new StringBuilder();
        for (Route route : routes) {
            toAdd.append("RouteID: ").append(route.getRouteID()).append(", Route Name: ").append(route
                    .getRouteLongName()).append("\n");
        }
        routesContainingStop.setText(toAdd.toString());
        routesContainingStop.setEditable(false);
    }

    /**
     * Author:
     * Description:
     *
     * @param route
     */
    public void displayRouteWithStops(Route route) {
        if (routeWithStopsTab == null) {
            routeWithStopsTab = new Tab("Route With its Stops");
            routeWithStops = new TextArea();
            routeWithStopsTab.setContent(routeWithStops);
            tabPane.getTabs().add(routeWithStopsTab);
        }
        routeWithStops.setText(route.stopsToString());
    }
}