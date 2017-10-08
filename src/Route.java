import javafx.scene.paint.Color;

import java.util.Collection;

/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:32 PM
 */
public class Route {

	private Color color;
	private String routeID;
	public DataStorage m_DataStorage;
	public Stop m_Stop;

	public Route(){

	}

	/**
	 * 
	 * @param stops
	 */
	public void Route(Collection<Stop> stops){

	}

	/**
	 * 
	 * @param stopID
	 */
	public Stop searchRoute(String stopID){
		return null;
	}

	/**
	 * 
	 * @param route
	 */
	public boolean equals(Route route){
		return false;
	}

}