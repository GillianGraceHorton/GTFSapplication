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
	@FXML
	private Tab mapTab;

	/**
	 * @author: hortong
	 * Description: initializes the entire GUI and passes references of object to the necessary
	 * places
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

            mapTab.setContent(map);

			searchResultsView = new SearchResultsView();
			dataStorage.setSearchResultsView(searchResultsView);
            tabSearchVBox.getChildren().add(searchResultsView);
        } catch (Exception e) {
            writeErrorMessage(e.getMessage());
        }
    }

    public void editFilesHandler() {

    }

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: creates an exports directory in the user chosen directory, with the user chosen file name
	 */
	public void exportStopFileHandler(){
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
        String tripID = searchForTripTextField.getText();
        if (dataStorage.searchTrips(tripID) != null) {
            results.add(dataStorage.searchTrips(tripID));
            searchResultsView.addSearchResults(tripID, results);
        } else {
            JOptionPane.showMessageDialog(null, "No such trip exists for the the trip ID: " +
                    tripID);
        }
    }

    /**
     * Author: hortog
     * Description:
     */
    public void searchForRouteHandler() {
        ArrayList<Object> results = new ArrayList<>();
        String routeID = searchForRouteTextField.getText();
        if (dataStorage.searchRoutes(routeID) != null) {
            results.add(dataStorage.searchRoutes(routeID));
            results.addAll(dataStorage.searchTripsForRoute(routeID));
            searchResultsView.addSearchResults(routeID, results);
        } else {
            JOptionPane.showMessageDialog(null, "No such route exists for the the route ID: " +
                    routeID);
        }
    }

    /**
     * Author: hortog
     * Description:
     */
    public void importStopFileHandler() {
        fileChooser.setTitle("Import Stop File");
        File fileToAdd = fileChooser.showOpenDialog(null);
        System.out.println();
        try {

            LinkedList<Stop> stops = fileManager.parseStopFile(fileToAdd);
            if (stops != null) { dataStorage.updateFromFiles(stops); }
            else { throw new NullPointerException(); }
            dataStorage.notifyObservers();
            writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());

        } catch (Exception e) {
            System.out.println("TEST: importStopFilesHandler -> " + e);
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author: hortog
     * Description:
     */
    public void importStopTimesFileHandler() {
        fileChooser.setTitle("Import Stop_Times File");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            LinkedList<StopTime> stopTimes = fileManager.parseStopTimesFile(fileToAdd);
            if (stopTimes != null) {
                dataStorage.updateFromFiles(stopTimes);
            }
            else { throw new NullPointerException(); }
            dataStorage.notifyObservers();
            writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
        } catch (Exception e) {
            System.out.println("Error: importStopTimesHandler -> " + e);
            writeErrorMessage(e.getMessage());
        }
    }

    /**
     * Author: hortog, Joseph Heinz - heinzja@msoe.edu
     * Description: Allows the user to import several stop,route,trip, and stop_times files at once.
     */
    public void importMultipleFilesHandler() {
        List<File> files = fileChooser.showOpenMultipleDialog(null);
        writeInformationMessage("User Message:", "Warning:\n" +
                "When Importing Large and/or Multiple Files the Program May Appear Unresponsive.\n" +
                "\nPlease Click 'Ok' to Continue Importing...");
        for (File file : files) {
            try {
                Scanner scanner = new Scanner(file);
                String firstLine = scanner.nextLine();
                if (firstLine.equals(fileManager.validFileTypes.get("stops"))) {

                    LinkedList<Stop> stops = fileManager.parseStopFile(file);
                    if (!stops.isEmpty()) { dataStorage.updateFromFiles(stops); }
                    else { throw new NullPointerException("Error: LinkedList<Stop> is empty"); }

                }
                else if (firstLine.equals(fileManager.validFileTypes.get("routes"))) {

                    LinkedList<Route> routes = fileManager.parseRouteFile(file);
                    if (!routes.isEmpty()) { dataStorage.updateFromFiles(routes); }
                    else { throw new NullPointerException("Error: LinkedList<Route> is empty"); }

                } else if (firstLine.equals(fileManager.validFileTypes.get("trips"))) {

                    LinkedList<Trip> trips = fileManager.parseTripFile(file);
                    if (!trips.isEmpty()) { dataStorage.updateFromFiles(trips); }
                    else { throw new NullPointerException("Error: LinkedList<Trip> is empty"); }

                } else if (firstLine.equals(fileManager.validFileTypes.get("stop_times"))) {

                    LinkedList<StopTime> stopTimes = fileManager.parseStopTimesFile(file);
                    if (!stopTimes.isEmpty()) { dataStorage.updateFromFiles(stopTimes); }
                    else { throw new NullPointerException("Error: LinkedList<StopTime> is empty"); }

                }
                else { throw new InvalidObjectException("Error: Invalid File Format"); }

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
            if (routes != null) { dataStorage.updateFromFiles(routes); }
            else { throw new NullPointerException(); }
            dataStorage.notifyObservers();
            writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());

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
            if (stops != null) { dataStorage.updateFromFiles(stops); }
            else { throw new NullPointerException(); }
            dataStorage.notifyObservers();
            writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());

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