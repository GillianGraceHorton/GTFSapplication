import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Author: hoffmanjc, Joseph Heinz - heinzja@msoe.edu
 * Description: Main method, and starting point of the application initialization and execution.
 * Date Created: 10/3/2017 - 4:57:23 PM
 */
@SuppressWarnings("ALL")
public class Driver extends Application {

	/**
	 * Author:
	 * Description: Begins application
	 * @param args - beginning arguments being passed into the application
	 */
	public static void main(String[] args){
		launch(args);
	}

	/**
	 * Author:
	 * Description:
	 * @param primaryStage
	 */
	public void start(Stage primaryStage) throws IOException {
		try {
			Parent mainPane = FXMLLoader.load(getClass().getResource("transitc.fxml"));
			primaryStage.setTitle("Transit");
			primaryStage.setScene(new Scene(mainPane));
			primaryStage.show();
		}catch (IOException e){
			throw new IOException("Error: transitc.fxml not found.\n" + e);
		}
	}

}