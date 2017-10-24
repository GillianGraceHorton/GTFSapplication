import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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
	private ListView listView;
	private DataStorage dataStorage;
	@FXML
    private VBox mainVBox;

	public void initialize(URL location, ResourceBundle resources){
		try {
			fileManager = new FileManager();
			listView = new ListView();
			map = new BusMap();
			dataStorage = new DataStorage();
			ArrayList<Observer> observers = new ArrayList<>();
			observers.add(listView);
			observers.add(map);
			dataStorage.setObservers(observers);
			listView.setSubject(dataStorage);
			map.setSubject(dataStorage);

			mainVBox.getChildren().add(listView);
            listView.setPrefWidth(mainVBox.getPrefWidth());
            listView.adjustSizes(mainVBox.getPrefHeight(), mainVBox.getPrefWidth());
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
		listView.displayRoutesContainingStop(routes);
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

	}

	public void importStopFileHandler() {
		FileChooser fileChooser = new FileChooser();
		File fileToAdd = fileChooser.showOpenDialog(null);
        System.out.println();
        try {
			//fileManager.addFile(fileToAdd);
			ArrayList<Object> stops = fileManager.parseStopFile(fileToAdd);
			dataStorage.notifyObservers(stops);
			writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
		}catch (Exception e){
			System.out.println("TEST: importStopFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
		}
	}

	public void importStopTimesFileHandler() {
        FileChooser fileChooser = new FileChooser();
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            //fileManager.addFile(fileToAdd);
            ArrayList<Object> stops = fileManager.parseStopTimesFile(fileToAdd);
            dataStorage.notifyObservers(stops);
            System.out.println("finished");
			writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
        }catch (Exception e){
            System.out.println("TEST: importFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

	public void importRouteFileHandler() {
        FileChooser fileChooser = new FileChooser();
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            //fileManager.addFile(fileToAdd);
            ArrayList<Object> stops = fileManager.parseRouteFile(fileToAdd);
            dataStorage.notifyObservers(stops);
			writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
        }catch (Exception e){
            System.out.println("TEST: importFilesHandler -> " + e);
			writeErrorMessage(e.getMessage());
        }
	}

	public void importTripFileHandler() {
        FileChooser fileChooser = new FileChooser();
        File fileToAdd = fileChooser.showOpenDialog(null);
        try {
            //fileManager.addFile(fileToAdd);
            ArrayList<Object> stops = fileManager.parseTripFile(fileToAdd);
            dataStorage.notifyObservers(stops);
            writeInformationMessage(Alert.AlertType.INFORMATION, "Import Successful",
					"Successfully imported " + fileToAdd.getName() + ".");
        }catch (Exception e){
            System.out.println("TEST: importFilesHandler -> " + e);
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