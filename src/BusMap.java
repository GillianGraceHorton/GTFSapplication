import javafx.scene.layout.Pane;

import java.util.ArrayList;

/**
 * @author hortong, hoffmanjc
 * @version 1.0
 * @created 03-Oct-2017 4:57:29 PM
 */
public class BusMap extends Pane implements Observer {

	private Subject dataStorage;

	public BusMap(){

	}

	public void setSubject(Subject dataStorage) {
		this.dataStorage = dataStorage;
	}

	@Override
	public void update(ArrayList<Object> updatedItems) {

	}
}