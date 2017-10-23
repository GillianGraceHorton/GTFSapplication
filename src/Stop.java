import java.util.ArrayList;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:33 PM
 */
public class Stop {

	/**
	 * arrival time for the bus at given stop. to be set when the stop is part of a trip.
	 */
	private Time arrivalTime;

	/**
	 * departure time for the bus at given stop. to be set when the stop is part of a trip.
	 */
	private Time departureTime;

	/**
	 * gps location of the stop
	 */
	private Location location;

	/**
	 * stop identifier
	 */
	private String stopID;

	/**
	 * stop name
	 */
	private String name;

	/**
	 * short description of stop and/or where it is located
	 */
	private String stopDescription;

	private ArrayList<StopTime> stopTimes;

	/**
	 * creates a stop object with information loaded from a stop file
	 * @param lon longitude for the gps location of the stop
	 * @param lat latitude for the gps location of the stop
	 * @param name of the stop
	 * @param stopID identifier for the stop
	 * @param stopDesc description of the stop
	 */
	public Stop(double lon, double lat, String name, String stopID, String stopDesc){
		this.location = new Location(lon, lat);
		this.name = name;
		this.stopID = stopID;
		this.stopDescription = stopDesc;
	}

	public void setArrivalTime(String arrivalTime) {
		this.arrivalTime = new Time(arrivalTime);
	}

	public void setDepartureTime(String departureTime) {
		this.departureTime =  new Time(departureTime);
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public void setStopDescription(String stopDescription) {
		this.stopDescription = stopDescription;
	}

	public Time getArrivalTime() {
		return arrivalTime;
	}

	public Time getDepartureTime() {
		return departureTime;
	}

	public Location getLocation() {
		return location;
	}

	public String getStopID() {
		return stopID;
	}

	public String getName() {
		return name;
	}

	public String getStopDescription() {
		return stopDescription;
	}

    /**
     * Adds stop times to the stops object
     * @param stopTime - The stoptime to add
     * @return boolean - if it was successfully added or not
     */
	public boolean addStopTimes(StopTime stopTime){
		if(stopTimes == null){
			stopTimes = new ArrayList<StopTime>();
		}
		if(stopTime != null) {
			return stopTimes.add(stopTime);
		}
		return false;
	}

	/**
	 * @author Gracie Horton
	 * compares two stops based on their stopIDS
	 * @param stop to compare to
	 * @return true if the stopIDs are equal and false if they are not
	 */
	public boolean equals(Stop stop){
		return (stopID.equals(stop.getStopID()));
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * @return returns formatted string of data stored in stop class.
	 */
	public String toString(){
		return  "StopID: " + getStopID() + "\n" +
				"Name: " + getName() + "\n" +
				"Description: " + getStopDescription() + "\n" +
				"Latitude: " + location.getLat() + "\n" +
				"Longitude: " + location.getLon() + "\n" +
				"Arrival Time: " + getArrivalTime() + "\n" +
				"Departure Time: " + getDepartureTime() + "\n";
	}

	public String toStringExport(){
		return String.format("%s,%s,%s,%f,%f,%s,%s,",getStopID(),getName(),getStopDescription(),getLocation().getLat(),getLocation().getLon(),getArrivalTime(),getDepartureTime());
	}
}