import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:20 PM
 */
public class Controller implements Initializable {
	private BusMap map;
	private FileManager fileManager;
	private GTFSListView gtfsListView;
	private DataStorage dataStorage;
	@FXML
    private VBox mainVBox;

	public void initialize(URL location, ResourceBundle resources){
		try {
			fileManager = new FileManager();
			gtfsListView = new GTFSListView();
			map = new BusMap();
			dataStorage = new DataStorage();
			ArrayList<Observer> observers = new ArrayList<>();
			observers.add(gtfsListView);
			observers.add(map);
			dataStorage.setObservers(observers);
			gtfsListView.setSubject(dataStorage);
			map.setSubject(dataStorage);

			mainVBox.getChildren().add(gtfsListView);
            gtfsListView.setPrefWidth(mainVBox.getPrefWidth());
            gtfsListView.adjustSizes(mainVBox.getPrefHeight(), mainVBox.getPrefWidth());
		}catch (Exception e){
			System.out.println(e);
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
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: creates an exports directory in the user choosen directory, with the user chosen file name
	 */
	public void exportStopFileHandler(){
		FileChooser fileChooser = new FileChooser();
		File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
		try {
			fileManager.exportStopFile(exportDir, dataStorage);
			writeInformationMessage(Alert.AlertType.INFORMATION, "Export Successful",
					"Successfully exported StopsFile.");
		} catch (Exception e) {
			writeErrorMessage(e.getMessage());
		}
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: creates an exports directory in the user choosen directory, with the user chosen file name
	 */
	public void exportStopTimesFileHandler(){
		FileChooser fileChooser = new FileChooser();
		File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
		try {
			fileManager.exportStopTimesFile(exportDir, dataStorage);
			writeInformationMessage(Alert.AlertType.INFORMATION, "Export Successful",
					"Successfully exported StopTimesFile.");
		} catch (Exception e) {
			writeErrorMessage(e.getMessage());
		}
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: creates an exports directory in the user choosen directory, with the user chosen file name
	 */
	public void exportRouteFileHandler(){
		FileChooser fileChooser = new FileChooser();
		File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
		try {
			fileManager.exportRouteFile(exportDir, dataStorage);
			writeInformationMessage(Alert.AlertType.INFORMATION, "Export Successful",
					"Successfully exported RoutesFile.");
		} catch (Exception e) {
			writeErrorMessage(e.getMessage());
		}
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: creates an exports directory in the user choosen directory, with the user chosen file name
	 */
	public void exportTripFileHandler(){
		FileChooser fileChooser = new FileChooser();
		File exportDir = new File(fileChooser.showSaveDialog(null).getPath());
		try {
			fileManager.exportTripFile(exportDir, dataStorage);
			writeInformationMessage(Alert.AlertType.INFORMATION, "Export Successful",
					"Successfully exported TripsFile.");
		} catch (Exception e) {
			writeErrorMessage(e.getMessage());
		}
	}

	public void searchRouteForStopHandler(ActionEvent actionEvent) {
		TextInputDialog input = new TextInputDialog();
		input.setHeaderText("Search for a stop by stop_id and display all routes that contain the" +
						" stop");
		input.setContentText("please enter the StopID");
		Optional<String> result = input.showAndWait();
		String stopID = result.get();

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

	public void searchForRouteHandler() {
        TextInputDialog input = new TextInputDialog();
        input.setHeaderText("Search for a route by routeID and display all of the stops on the " +
                "route");
        input.setContentText("please enter the RouteID");
        Optional<String> result = input.showAndWait();
        String routeID = result.get();

        gtfsListView.displayRouteWithStops(dataStorage.searchRoutes(routeID));
	}

	public void importStopFileHandler() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Import Stops");
		File fileToAdd = fileChooser.showOpenDialog(null);
        System.out.println();
        try {
			LinkedList<Object> stops = fileManager.parseStopFile(fileToAdd);
            dataStorage.updateFromFiles(stops);
            dataStorage.notifyObservers();
            writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
		}catch (Exception e){
			System.out.println("TEST: importStopFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
		}
	}

	public void importStopTimesFileHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Stop Times");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            LinkedList<Object> stopTimes = fileManager.parseStopTimesFile(fileToAdd);
			System.out.println("read from stopTimes correctly");
			dataStorage.updateFromFiles(stopTimes);
			System.out.println("updated dataStorage with stopTimes correctly");
			dataStorage.notifyObservers();
			System.out.println("printed stopTimes correctly");
			writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
        }catch (Exception e){
            System.out.println("TEST: importStopTimesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

	public void importRouteFileHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Routes");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            LinkedList<Object> routes = fileManager.parseRouteFile(fileToAdd);
            dataStorage.updateFromFiles(routes);
            dataStorage.notifyObservers();
            writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
        }catch (Exception e){
            System.out.println("TEST: importRouteFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

	public void importTripFileHandler() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Import Trips");
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            LinkedList<Object> stops = fileManager.parseTripFile(fileToAdd);
            dataStorage.updateFromFiles(stops);
            dataStorage.notifyObservers();
            writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
        }catch (Exception e){
            System.out.println("TEST: importTripFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

	private void writeErrorMessage(String message){
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText("Error");
		alert.setContentText(message);
		alert.showAndWait();
	}

	private void writeInformationMessage(Alert.AlertType alertType, String header, String context) {
		Alert alert = new Alert(alertType);
		alert.setHeaderText(header);
		alert.setContentText(context);
		alert.showAndWait();
	}
}