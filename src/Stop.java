


/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:33 PM
 */
public class Stop {

	private int arrivalTime;
	private int departureTime;
	private Location location;
	private String stopID;
	private String name;
	public DataStorage m_DataStorage;
	public Location m_Location;

	public Stop(){

	}



	/**
	 * 
	 * @param lon
	 * @param lat
	 * @param name
	 */
	public void Stop(float lon, float lat, String name){

	}

	/**
	 * 
	 * @param arrivalTime
	 */
	public void setArrivalTime(int arrivalTime){

	}

	public int getArrivalTime(){
		return 0;
	}

	/**
	 * 
	 * @param departureTime
	 */
	public void setDepartureTime(int departureTime){

	}

	public int getDepartureTime(){
		return 0;
	}

	/**
	 * 
	 * @param lat
	 */
	public void setLatitude(float lat){

	}

	/**
	 * 
	 * @param lon
	 */
	public void setLongitude(float lon){

	}

	public float getLatitude(){
		float num = 0;
		return num;
	}

	public float getLongitude(){
		float num = 0;
		return num;
	}

	public int getID(){
		return 0;
	}

	/**
	 * 
	 * @param stop
	 */
	public boolean equals(Stop stop){
		return false;
	}

}