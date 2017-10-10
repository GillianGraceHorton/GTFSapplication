import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
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

            dataStorage.notifyObservers(fileManager.loadFromValidFiles());
		}catch (Exception e){
			System.out.println(e);
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

	public void importFilesHandler() {
		FileChooser fileChooser = new FileChooser();
		File fileToAdd = fileChooser.showOpenDialog(null);
		try {
			fileManager.addFile(fileToAdd);
			ArrayList<Object> stops = fileManager.parseFile(fileToAdd);
			dataStorage.notifyObservers(stops);
		}catch (Exception e){
			System.out.println("TEST: importFilesHandler -> " + e);
		}
	}

	public void exportFileHandler(){

	}

	public void exportStopFileHandler(){
	}

	public void exportStopTimesFileHandler(){

	}

	public void exportRouteFileHandler(){

	}

	public void exportTripFileHandler(){

	}

	public void searchRouteHandler(){

	}



	public void searchTripHandler(){

	}

    public void searchStopHandler(ActionEvent actionEvent) {
    }
}