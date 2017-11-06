import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InvalidObjectException;
import java.net.URL;
import java.util.*;

/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:20 PM
 */
public class Controller implements Initializable {
    private FileChooser fileChooser;
    private BusMap map;
    private FileManager fileManager;
    private GTFSListView gtfsListView;
    private SearchResultsView searchResultsView;
    private DataStorage dataStorage;
    @FXML
    private VBox mainVBox;
    @FXML
    private Tab importedItemsTab;
    @FXML
    private TextField searchForStopTextField;
    @FXML
    private TextField searchForRouteTextField;
    @FXML
    private TextField searchForTripTextField;
    @FXML
    private VBox tabSearchVBox;

    /**
     * Author:
     * Description:
     *
     * @param location
     * @param resources
     */
    public void initialize(URL location, ResourceBundle resources) {
        try {
            ArrayList<Observer> observers = new ArrayList<>();
            fileChooser = new FileChooser();
            fileManager = new FileManager();
            gtfsListView = new GTFSListView();
            dataStorage = new DataStorage();
            map = new BusMap();

            observers.add(gtfsListView);
            observers.add(map);
            dataStorage.setObservers(observers);
            gtfsListView.setSubject(dataStorage);
            map.setSubject(dataStorage);

            importedItemsTab.setContent(gtfsListView);
            gtfsListView.setPrefWidth(mainVBox.getWidth());
            gtfsListView.adjustSizes(mainVBox.getPrefHeight(), mainVBox.getPrefWidth());

            searchResultsView = new SearchResultsView();
            tabSearchVBox.getChildren().add(searchResultsView);
        } catch (Exception e) {
            writeErrorMessage(e.getMessage());
        }
    }

    public void editFilesHandler() {

    }

    public void exportFileHandler() {
        //TODO: remove exportFileHandler if not needed in future implementation
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: creates an exports directory in the user chosen directory, with the user chosen file name
     */
    public void exportStopFileHandler() {
        fileChooser.setTitle("Export Stop File");
        File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
        try {
            fileManager.exportStopFile(exportDir, dataStorage);
            writeInformationMessage("Export Successful", "Successfully exported StopsFile.");
        } catch (Exception e) {
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: creates an exports directory in the user chosen directory, with the user chosen file name
     */
    public void exportStopTimesFileHandler() {
        fileChooser.setTitle("Export StopTimes File");
        File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
        try {
            fileManager.exportStopTimesFile(exportDir, dataStorage);
            writeInformationMessage("Export Successful", "Successfully exported StopTimesFile.");
        } catch (Exception e) {
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: creates an exports directory in the user chosen directory, with the user chosen file name
     */
    public void exportRouteFileHandler() {
        File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
        try {
            fileManager.exportRouteFile(exportDir, dataStorage);
            writeInformationMessage("Export Successful", "Successfully exported RoutesFile.");
        } catch (Exception e) {
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: creates an exports directory in the user chosen directory, with the user chosen file name
     */
    public void exportTripFileHandler() {
        fileChooser.setTitle("Export Trip File");
        File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
        try {
            fileManager.exportTripFile(exportDir, dataStorage);
            writeInformationMessage("Export Successful", "Successfully exported TripsFile.");
        } catch (Exception e) {
            writeErrorMessage(e.getMessage());
        }
    }

    public void searchForStopHandler() {
        ArrayList<Object> results = new ArrayList<>();
        String stopID = searchForStopTextField.getText();
        if (dataStorage.searchStops(stopID) != null) {
            results.add(dataStorage.searchStops(stopID));
            results.addAll(dataStorage.searchRoutesForStop(stopID));
            results.addAll(dataStorage.searchTripsForStop(stopID));
            searchResultsView.addSearchResults(stopID, results);
        } else {
            JOptionPane.showMessageDialog(null, "No such stop exists for the the stop ID: " +
                    stopID);
        }

    }

    public void searchForTripHandler() {
        ArrayList<Object> results = new ArrayList<>();
        String tripID = searchForRouteTextField.getText();
        if (dataStorage.searchTrips(tripID) != null) {
            results.add(dataStorage.searchTrips(tripID));
            searchResultsView.addSearchResults(tripID, results);
        } else {
            JOptionPane.showMessageDialog(null, "No such trip exists for the the trip ID: " +
                    tripID);
        }

    }

    /**
     * Author:
     * Description:
     */
    public void searchForRouteHandler() {
        ArrayList<Object> results = new ArrayList<>();
        String routeID = searchForTripTextField.getText();
        if (dataStorage.searchRoutes(routeID) != null) {
            results.add(dataStorage.searchRoutes(routeID));
            searchResultsView.addSearchResults(routeID, results);
        } else {
            JOptionPane.showMessageDialog(null, "No such trip exists for the the trip ID: " +
                    routeID);
        }
    }

    /**
     * Author:
     * Description:
     */
    public void importStopFileHandler() {
        fileChooser.setTitle("Import Stops");
        File fileToAdd = fileChooser.showOpenDialog(null);
        System.out.println();
        try {
            LinkedList<Stop> stops = fileManager.parseStopFile(fileToAdd);
            if (stops != null) {
                dataStorage.updateFromFiles(stops);
                dataStorage.notifyObservers();
                writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            System.out.println("TEST: importStopFilesHandler -> " + e);
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author:
     * Description:
     */
    public void importStopTimesFileHandler() {
        fileChooser.setTitle("Import Stop Times");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            LinkedList<StopTime> stopTimes = fileManager.parseStopTimesFile(fileToAdd);
            if (stopTimes != null) {
                dataStorage.updateFromFiles(stopTimes);
                dataStorage.notifyObservers();
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            System.out.println("Error: importStopTimesHandler -> " + e);
            writeErrorMessage(e.getMessage());
        }
    }

    public void importMultipleFilesHandler() {
        SimpleTimer st = new SimpleTimer();
        List<File> files = fileChooser.showOpenMultipleDialog(null);
        writeInformationMessage("Importing...", "Warning: Loading Large/Multiple Files May Cause the Program to Appear Unresponsive.\n" +
                "Click Ok to Continue.");
        for (File file : files) {

            try {
                Scanner scanner = new Scanner(file);
                String firstLine = scanner.nextLine();
                if (firstLine.equals(fileManager.validFileTypes.get("stops"))) {
                    System.out.println("stops:");
                    st.start();
                    LinkedList<Stop> stops = fileManager.parseStopFile(file);
                    st.end();
                    if (!stops.isEmpty()) {
                        st.start();
                        dataStorage.updateFromFiles(stops);
                        st.end();
                    } else {
                        throw new NullPointerException("Error: LinkedList<Stop> is empty");
                    }
                } else if (firstLine.equals(fileManager.validFileTypes.get("routes"))) {
                    System.out.println("routes:");
                    st.start();
                    LinkedList<Route> routes = fileManager.parseRouteFile(file);
                    st.end();
                    if (!routes.isEmpty()) {
                        st.start();
                        dataStorage.updateFromFiles(routes);
                        st.end();
                    } else {
                        throw new NullPointerException("Error: LinkedList<Route> is empty");
                    }
                } else if (firstLine.equals(fileManager.validFileTypes.get("trips"))) {
                    System.out.println("trips:");
                    st.start();
                    LinkedList<Trip> trips = fileManager.parseTripFile(file);
                    st.end();
                    if (!trips.isEmpty()) {
                        st.start();
                        dataStorage.updateFromFiles(trips);
                        st.end();
                    } else {
                        throw new NullPointerException("Error: LinkedList<Trip> is empty");
                    }
                } else if (firstLine.equals(fileManager.validFileTypes.get("stop_times"))) {
                    System.out.println("stop_times:");
                    st.start();
                    LinkedList<StopTime> stopTimes = fileManager.parseStopTimesFile(file);
                    st.end();
                    if (!stopTimes.isEmpty()) {
                        st.start();
                        dataStorage.updateFromFiles(stopTimes);
                        st.end();
                    } else {
                        throw new NullPointerException("Error: LinkedList<StopTime> is empty");
                    }
                } else {
                    throw new InvalidObjectException("Error: Invalid File Format");
                }
            } catch (InvalidObjectException | InputMismatchException | FileNotFoundException | NullPointerException e) {
                writeErrorMessage("Error: " + e.toString() + "\nMessage: " + e.getMessage());
                e.printStackTrace();
            }

        }
        dataStorage.notifyObservers();
        writeInformationMessage("Import Successful", "All Files Were Imported Successfully");
    }

    /**
     * Author:
     * Description:
     */
    public void importRouteFileHandler() {
        fileChooser.setTitle("Import Routes");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            LinkedList<Route> routes = fileManager.parseRouteFile(fileToAdd);
            if (routes != null) {
                dataStorage.updateFromFiles(routes);
                dataStorage.notifyObservers();
                writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            System.out.println("TEST: importRouteFilesHandler -> " + e);
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author:
     * Description:
     */
    public void importTripFileHandler() {
        fileChooser.setTitle("Import Trips");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            LinkedList<Trip> stops = fileManager.parseTripFile(fileToAdd);
            if (stops != null) {
                dataStorage.updateFromFiles(stops);
                dataStorage.notifyObservers();
                writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
            } else {
                throw new NullPointerException();
            }
        } catch (Exception e) {
            System.out.println("TEST: importTripFilesHandler -> " + e);
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author:
     * Description:
     *
     * @param message
     */
    private void writeErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Author:
     * Description:
     *
     * @param header
     * @param context
     */
    private void writeInformationMessage(String header, String context) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}