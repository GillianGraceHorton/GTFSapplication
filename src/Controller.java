import javafx.fxml.Initializable;
import javafx.stage.FileChooser;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author heinzja
 * @version 1.0
 * @created 03-Oct-2017 4:57:20 PM
 */
public class Controller implements Initializable {

	private BusMap map;
	private FileManager fileManager;
	private ListView listView;
	private DataStorage dataStorage;

	public void initialize(URL location, ResourceBundle resources){
		fileManager = new FileManager();
		listView = new ListView();
		dataStorage = new DataStorage();
	}

	public void loadFilesHandler(){

	}

	public void addStopHandler(){

	}

	public void showOngoingTripsHandler(){

	}

	public void editFilesHandler(){

	}

	public void importFilesHandler(){
		FileChooser fileChooser = new FileChooser();
		File fileToAdd = fileChooser.showOpenDialog(null);
		if(fileToAdd == null){
			//TODO error message
		}else {
			fileManager.addFile(fileToAdd);
		}
	}

	public void exportFilesHandler(){

	}

	public void searchRouteHandler(){

	}



	public void searchTripHandler(){

	}

}