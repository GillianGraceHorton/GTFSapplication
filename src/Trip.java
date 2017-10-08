import java.util.Collection;

/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:35 PM
 */
public class Trip {

	private Collection<Stop> tripList;
	private Route route;
	private String tripID;
	public DataStorage m_DataStorage;
	public Route m_Route;

	public Trip(){

	}

	/**
	 * 
	 * @param route
	 */
	public void Trip(Route route){

	}

	public boolean setTimes(){
		return false;
	}

	/**
	 * 
	 * @param trip
	 */
	public boolean equals(Trip trip){
		return false;
	}

}