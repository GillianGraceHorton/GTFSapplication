import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


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
		try {
			Parent mainPane = FXMLLoader.load(getClass().getResource("transitc.fxml"));
			primaryStage.setTitle("Transit");
			primaryStage.setScene(new Scene(mainPane));
			primaryStage.show();
		}catch (Exception e){
			System.out.println(e);
		}
	}

}