import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:33 PM
 */
public class Stop {

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

	public Stop(String stopID){
		this.stopID = stopID;
	}
	public void setLocation(Location location) {
		this.location = location;
	}

	public void setStopDescription(String stopDescription) {
		this.stopDescription = stopDescription;
	}

	public Location getLocation() {
		return location;
	}

	public String getStopID() {
		return stopID;
	}
	public ArrayList<StopTime> getStopTimes() {
		return stopTimes;
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
	public String toStringData(){
		if(isEmpty()){
			return "StopID: " + getStopID() + "\nNo Data";
		}
		return  "StopID: " + getStopID() + "\n" +
				"Name: " + getName() + "\n" +
				"Description: " + getStopDescription() + "\n" +
				"Latitude: " + location.getLat() + "\n" +
				"Longitude: " + location.getLon() + "\n";
	}

	public String toString(){
		return "Stop Name: " + name + "\n  StopID: " + stopID;
	}

	public String toStringExport(){
		return String.format("%s,%s,%s,%f,%f,",getStopID(),getName(),getStopDescription(),getLocation().getLat(),getLocation().getLon());
	}

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: Checks if this Stop Object contains a location and name, if not, then the stop
	 * 				is a place holder for future stop information to added to.
	 * @return returns result of if this Stop Object is a placeholder (empty) or valid
	 */
	public boolean isEmpty(){
		boolean result = true;
		if(this.location != null && this.name != null){
			result = false;
		}
		return result;
	}


	public void copyInstanceVariables(Stop stop)throws IllegalArgumentException{
		if(!this.getStopID().equalsIgnoreCase(stop.getStopID())){
			throw new IllegalArgumentException("This trip's ID: " + this.getStopID() + ", does " +
					"not match the ID of the argument: " + stop.getStopID());
		}
		this.name = stop.getName();
		this.stopDescription = stop.getStopDescription();
		this.location = stop.getLocation();
		this.stopTimes = stop.getStopTimes();
	}

}