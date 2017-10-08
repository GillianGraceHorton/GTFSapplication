import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import java.io.File;


/**
 * author hoffmanjc
 * 			joseph heinz - heinzja@msoe.edu
 * version 1.0
 * created 03-Oct-2017 4:57:23 PM
 */
public class Driver extends Application {

	/**
	 *
	 * @param args
	 */
	public static void main(String[] args){
		launch(args);
	}

	/**
	 * 
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("transitc.fxml"));
		primaryStage.setTitle("Hello World");
		primaryStage.show();
	}

}