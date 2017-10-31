import java.util.ArrayList;

/**
 * Author: Gracie Horton
 * Description:
 * Date Created: 10/3/2017 - 4:57:33 PM
 */
public class Stop {

    private Location location; //gps location of the stop
    private String stopID; //stop identifier
    private String name; //stop name
    private String stopDescription; //short description of stop and/or where it is located
    private ArrayList<StopTime> stopTimes;

    /**
     * Author:
     * Description: Creates a stop object with information loaded from a stop file
     *
     * @param lon      longitude for the gps location of the stop
     * @param lat      latitude for the gps location of the stop
     * @param name     of the stop
     * @param stopID   identifier for the stop
     * @param stopDesc description of the stop
     */
    public Stop(double lon, double lat, String name, String stopID, String stopDesc) {
        this.location = new Location(lon, lat);
        this.name = name;
        this.stopID = stopID;
        this.stopDescription = stopDesc;
    }

    /**
     * Author:
     * Description:
     *
     * @param stopID
     */
    public Stop(String stopID) {
        this.stopID = stopID;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public Location getLocation() {
        return location;
    }

    /**
     * Author:
     * Description:
     *
     * @param location
     */
    public void setLocation(Location location) {
        this.location = location;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getStopID() {
        return stopID;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public ArrayList<StopTime> getStopTimes() {
        return stopTimes;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String getStopDescription() {
        return stopDescription;
    }

    /**
     * Author:
     * Description:
     *
     * @param stopDescription
     */
    public void setStopDescription(String stopDescription) {
        this.stopDescription = stopDescription;
    }

    /**
     * Author:
     * Description: Adds stop times to the stops object
     *
     * @param stopTime - The stopTime to add
     * @return boolean - if it was successfully added or not
     */
    public boolean addStopTimes(StopTime stopTime) {
        boolean result = false;
        if (stopTimes == null) {
            stopTimes = new ArrayList<>();
        }
        if (stopTime != null) {
            result = stopTimes.add(stopTime);
        }
        return result;
    }

    /**
     * Author: Gracie Horton
     * Description: Compares two stops based on their stopIDS
     *
     * @param stop to compare to
     * @return true if the stopIDs are equal and false if they are not
     */
    public boolean equals(Stop stop) {
        return (stopID.equals(stop.getStopID()));
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: returns formatted string of data stored in stop class.
     *
     * @return String - formatted string of data used for GUI
     */
    public String toStringData() {
        String result;
        if (isEmpty()) {
            result = "StopID: " + getStopID() + "\nNo Data";
        } else {
            result = "StopID: " + getStopID() + "\n" +
                    "Name: " + getName() + "\n" +
                    "Description: " + getStopDescription() + "\n" +
                    "Latitude: " + location.getLat() + "\n" +
                    "Longitude: " + location.getLon() + "\n";
        }
        return result;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String toString() {
        return "Stop Name: " + name + "\n  StopID: " + stopID;
    }

    /**
     * Author:
     * Description:
     *
     * @return
     */
    public String toStringExport() {
        return String.format("%s,%s,%s,%f,%f,", getStopID(), getName(), getStopDescription(), getLocation().getLat(), getLocation().getLon());
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: Checks if this Stop Object contains a location and name, if not, then the stop
     * is a place holder for future stop information to added to.
     *
     * @return returns result of if this Stop Object is a placeholder (empty) or valid
     */
    public boolean isEmpty() {
        boolean result = true;
        if (this.location != null && this.name != null) {
            result = false;
        }
        return result;
    }

    /**
     * Author:
     * Description:
     *
     * @param stop
     * @throws IllegalArgumentException
     */
    public void copyInstanceVariables(Stop stop) throws IllegalArgumentException {
        if (!this.getStopID().equalsIgnoreCase(stop.getStopID())) {
            throw new IllegalArgumentException("This trip's ID: " + this.getStopID() + ", does " +
                    "not match the ID of the argument: " + stop.getStopID());
        }
        this.name = stop.getName();
        this.stopDescription = stop.getStopDescription();
        this.location = stop.getLocation();
        this.stopTimes = stop.getStopTimes();
    }

}