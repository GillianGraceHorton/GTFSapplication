import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
	 * Author:
	 * Description:
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
		}catch (Exception e){
			writeErrorMessage(e.getMessage());
		}
	}

	public void editFilesHandler(){

	}

	public void exportFileHandler(){
		//TODO: remove exportFileHandler if not needed in future implementation
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
	public void exportStopTimesFileHandler(){
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
	public void exportRouteFileHandler(){
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
	public void exportTripFileHandler(){
		fileChooser.setTitle("Export Trip File");
		File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
		try {
			fileManager.exportTripFile(exportDir, dataStorage);
			writeInformationMessage("Export Successful", "Successfully exported TripsFile.");
		}
		catch (Exception e) {
			writeErrorMessage(e.getMessage());
		}
	}

	/**
	 * @author: hortong
	 * searches for a stop object
	 */
	public void searchForStopHandler() {
		String stopID = searchForStopTextField.getText();
		if(dataStorage.searchStops(stopID) != null){
			dataStorage.searchForStop(stopID);
		}else{
			JOptionPane.showMessageDialog(null, "No such stop exists for the the stop ID: " +
					stopID);
		}

	}

	/**
	 * @author: hortong
	 * searches for a trip object
	 */
	public void searchForTripHandler() {
		String tripID = searchForTripTextField.getText();
		if(dataStorage.searchTrips(tripID) != null){
			dataStorage.searchForTrip(tripID);
		}else{
			JOptionPane.showMessageDialog(null, "No such trip exists for the the trip ID: " +
					tripID);
		}

	}

    /**
     * Author: hortong
     * Description: searches for a route object
     */
	public void searchForRouteHandler() {
		String routeID = searchForRouteTextField.getText();
		if(dataStorage.searchRoutes(routeID) != null){
			dataStorage.searchForRoute(routeID);
		}else{
			JOptionPane.showMessageDialog(null, "No such trip exists for the the trip ID: " +
					routeID);
		}
	}

    /**
     * Author: hortong, heinzja
     * Description: handles the importing of stop objects from a file
     */
	public void importStopFileHandler() {
		fileChooser.setTitle("Import Stops");
		File fileToAdd = fileChooser.showOpenDialog(null);
        System.out.println();
        try {
			LinkedList<Stop> stops = fileManager.parseStopFile(fileToAdd);
			if(stops != null) {
				dataStorage.updateFromFiles(stops);
				dataStorage.notifyObservers();
				writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
			}
			else { throw new NullPointerException(); }
		}catch (Exception e){
			System.out.println("TEST: importStopFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
		}
	}

	/**
	 * Author: hortong, heinzja
	 * Description: handles the importing of stopTimes objects from a file
	 */
	public void importStopTimesFileHandler() {
        fileChooser.setTitle("Import Stop Times");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
			LinkedList<StopTime> stopTimes = fileManager.parseStopTimesFile(fileToAdd);
        	if(stopTimes != null) {
				dataStorage.updateFromFiles(stopTimes);
				dataStorage.notifyObservers();
			}
			else { throw new NullPointerException(); }
        }catch (Exception e){
            System.out.println("Error: importStopTimesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

	public void importMultipleFilesHandler(){
		List<File> files = fileChooser.showOpenMultipleDialog(null);
		for(File file : files){
			try{
				Scanner scanner = new Scanner(file);
				String firstLine = scanner.nextLine();
				if(firstLine.equals(fileManager.validFileTypes.get("stops"))){
					LinkedList<Stop> stops = fileManager.parseStopFile(file);
					System.out.println(file.getName());
					if(!stops.isEmpty()) {
						dataStorage.updateFromFiles(stops);
						dataStorage.notifyObservers();
					}
					else { throw new NullPointerException("Error: LinkedList<Stop> is empty"); }
				}
				else if(firstLine.equals(fileManager.validFileTypes.get("routes"))) {
					LinkedList<Route> routes = fileManager.parseRouteFile(file);
					System.out.println(file.getName());
					if (!routes.isEmpty()) {
						dataStorage.updateFromFiles(routes);
						dataStorage.notifyObservers();
					}
					else { throw new NullPointerException("Error: LinkedList<Route> is empty"); }
				}
				else if(firstLine.equals(fileManager.validFileTypes.get("trips"))){
					LinkedList<Trip> trips = fileManager.parseTripFile(file);
					System.out.println(file.getName());
					if(!trips.isEmpty()) {
						dataStorage.updateFromFiles(trips);
						dataStorage.notifyObservers();
					}
					else { throw new NullPointerException("Error: LinkedList<Trip> is empty"); }
				}
				else if(firstLine.equals(fileManager.validFileTypes.get("stop_times"))) {
					LinkedList<StopTime> stopTimes = fileManager.parseStopTimesFile(file);
					System.out.println(file.getName());
					if (!stopTimes.isEmpty()) {
						dataStorage.updateFromFiles(stopTimes);
						dataStorage.notifyObservers();
					}
					else { throw new NullPointerException("Error: LinkedList<StopTime> is empty"); }
				}
				else { throw new InvalidObjectException("Error: Invalid File Format"); }
			}
			catch (InvalidObjectException | InputMismatchException | FileNotFoundException | NullPointerException e){
				writeErrorMessage("Error: " + e.toString() +"\nMessage: "+ e.getMessage());
				e.printStackTrace();
			}
		}
	}

	/**
	 * Author: hortong, heinzja
	 * Description: handles the importing of route objects from a file
	 */
	public void importRouteFileHandler() {
        fileChooser.setTitle("Import Routes");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
			LinkedList<Route> routes = fileManager.parseRouteFile(fileToAdd);
            if(routes != null) {
				dataStorage.updateFromFiles(routes);
				dataStorage.notifyObservers();
				writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
			}
			else {
            	throw new NullPointerException();
			}
        }catch (Exception e){
            System.out.println("TEST: importRouteFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

	/**
	 * Author: hortong, heinzja
	 * Description: handles the importing of trip objects from a file
	 */
	public void importTripFileHandler() {
        fileChooser.setTitle("Import Trips");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
			LinkedList<Trip> stops = fileManager.parseTripFile(fileToAdd);
            if(stops != null) {
				dataStorage.updateFromFiles(stops);
				dataStorage.notifyObservers();
				writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
			}
			else {
            	throw new NullPointerException();
			}
        }catch (Exception e){
            System.out.println("TEST: importTripFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

    /**
     * Author: hoffmanj
     * Description: writes an error message to the user user alerts
     * @param message to give to the user
     */
	private void writeErrorMessage(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.showAndWait();
	}

    /**
     * Author: hoffmanj
     * Description: writes an informative message to the user
     * @param header for the message
     * @param context message to write to the user
     */
	private void writeInformationMessage(String header, String context) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
}