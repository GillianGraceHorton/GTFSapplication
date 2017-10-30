import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileNotFoundException;
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
	private DataStorage dataStorage;
	@FXML
    private VBox mainVBox;

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

			mainVBox.getChildren().add(gtfsListView);
            gtfsListView.setPrefWidth(mainVBox.getPrefWidth());
            gtfsListView.adjustSizes(mainVBox.getPrefHeight(), mainVBox.getPrefWidth());
		}catch (Exception e){
			writeErrorMessage(e.getMessage());
		}
	}

	public void loadFilesHandler(){

	}

	public void addStopHandler(){

	}

	public void showOngoingTripsHandler(){

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
     * Author:
     * Description:
     * @param actionEvent
     */
	public void searchRouteForStopHandler(ActionEvent actionEvent) {
		TextInputDialog input = new TextInputDialog();
		String stopID = null;
		input.setHeaderText("Search for a stop by stop_id and display all routes that contain the stop");
		input.setContentText("Please enter the StopID");
		if(input.showAndWait().isPresent()){ stopID = input.getResult(); }
		ArrayList<Route> routes = dataStorage.searchRoutesForStop(stopID);
		gtfsListView.displayRoutesContainingStop(routes);
	}

	public void searchForStopHandler() {
	}

	public void searchTripsForRouteHandler() {
	}

	public void searchTripsForStopHandler() {
	}

	public void searchForTripHandler() {
	}

    /**
     * Author:
     * Description:
     */
	public void searchForRouteHandler() {
        TextInputDialog input = new TextInputDialog();
		String routeID = null;
        input.setHeaderText("Search for a route by routeID and display all of the stops on the route");
        input.setContentText("please enter the RouteID");
        if(input.showAndWait().isPresent()){ routeID = input.getResult(); }
        gtfsListView.displayRouteWithStops(dataStorage.searchRoutes(routeID));
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
			if(stops != null) {
				dataStorage.updateFromFiles(stops);
				dataStorage.notifyObservers();
				writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
			}
			else {
				throw new NullPointerException();
			}
		}catch (Exception e){
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
        	if(stopTimes != null) {
				dataStorage.updateFromFiles(stopTimes);
				dataStorage.notifyObservers();
				writeInformationMessage("Import Successful", "File Imported: " + fileToAdd.getName());
			}
			else {
        		throw new NullPointerException();
			}
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
					try {
						LinkedList<Stop> stops = fileManager.parseStopFile(file);
						if(stops != null) {
							dataStorage.updateFromFiles(stops);
							dataStorage.notifyObservers();
							writeInformationMessage("Import Successful", "File Imported: " + file.getName());
						}
						else {
							throw new NullPointerException();
						}
					}catch (Exception e){
						System.out.println("TEST: importStopFilesHandler -> " + e);
						writeErrorMessage(e.getMessage());
					}
				}
				else if(firstLine.equals(fileManager.validFileTypes.get("routes"))){
					try {
						LinkedList<Route> routes = fileManager.parseRouteFile(file);
						if(routes != null) {
							dataStorage.updateFromFiles(routes);
							dataStorage.notifyObservers();
							writeInformationMessage("Import Successful", "File Imported: " + file.getName());
						}
						else {
							throw new NullPointerException();
						}
					}catch (Exception e){
						System.out.println("TEST: importRouteFilesHandler -> " + e);
						writeErrorMessage(e.getMessage());
					}
				}
				else if(firstLine.equals(fileManager.validFileTypes.get("trips"))){
					try {
						LinkedList<Trip> stops = fileManager.parseTripFile(file);
						if(stops != null) {
							dataStorage.updateFromFiles(stops);
							dataStorage.notifyObservers();
							writeInformationMessage("Import Successful", "File Imported: " + file.getName());
						}
						else {
							throw new NullPointerException();
						}
					}catch (Exception e){
						System.out.println("TEST: importTripFilesHandler -> " + e);
						writeErrorMessage(e.getMessage());
					}
				}
				else if(firstLine.equals(fileManager.validFileTypes.get("stop_times"))){
					try {
						LinkedList<StopTime> stopTimes = fileManager.parseStopTimesFile(file);
						if(stopTimes != null) {
							dataStorage.updateFromFiles(stopTimes);
							dataStorage.notifyObservers();
							writeInformationMessage("Import Successful", "File Imported: " + file.getName());
						}
						else {
							throw new NullPointerException();
						}
					}catch (Exception e){
						System.out.println("Error: importStopTimesHandler -> " + e);
						writeErrorMessage(e.getMessage());
					}
				}
			}
			catch (FileNotFoundException e){

			}
			catch (NullPointerException e){

			}
		}
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
     * Author:
     * Description:
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
     * Author:
     * Description:
     * @param message
     */
	private void writeErrorMessage(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.showAndWait();
	}

    /**
     * Author:
     * Description:
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