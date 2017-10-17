import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:35 PM
 */
public class Trip {

    private String shapeID;
    private String blockID;
    private String directionID;
    private String tripHeadsign;
    private String serviceID;
    private NavigableMap<Integer, Stop> tripList;
    private Route route;
    private String routeID;
    private String tripID;



    public Trip(String tripID, String serviceID, String routeID, String tripHeadsign, String
            directionID, String blockID, String shapeID) {
        this.tripID = tripID;
        this.serviceID = serviceID;
        this.routeID = routeID;
        this.tripHeadsign = tripHeadsign;
        this.directionID = directionID;
        this.blockID = blockID;
        this.shapeID = shapeID;

    }

    /**
     * @param route
     */
    public Trip(Route route) {

    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public void setShapeID(String shapeID) {
        this.shapeID = shapeID;
    }

    public void setBlockID(String blockID) {
        this.blockID = blockID;
    }

    public void setDirectionID(String directionID) {
        this.directionID = directionID;
    }

    public void setTripHeadsign(String tripHeadsign) {
        this.tripHeadsign = tripHeadsign;
    }

    public void setServiceID(String serviceID) {
        this.serviceID = serviceID;
    }

    public String getShapeID() {
        return shapeID;
    }

    public String getBlockID() {
        return blockID;
    }

    public String getDirectionID() {
        return directionID;
    }

    public String getTripHeadsign() {
        return tripHeadsign;
    }

    public String getServiceID() {
        return serviceID;
    }

    public Route getRoute() {
        return route;
    }

    public String getRouteID() {
        return routeID;
    }

    public String getTripID() {
        return tripID;
    }

    public boolean setTimes() {
        return false;
    }

    /**
     * added the specified stop at the specified index in the tripList.
     * @param stop stop to add to the tripList
     * @param stopNum number indicating when the stop will be reached on the trip
     * @return true after the stop is added
     */
    public boolean addStop(Stop stop, int stopNum) {
        if(tripList == null){
            tripList = new TreeMap<>();
        }
        tripList.put(stopNum, stop);
        if(route != null && route.addStop(stop, stopNum)) {
            return true;
        }
        return false;
    }

    public Stop getStop(String stopId){
        if(tripList != null && stopId != null) {
            NavigableSet<Integer> nav = tripList.navigableKeySet();
            for (Integer num : nav) {
                if (tripList.get(num).getStopID().equals(stopId)) {
                    return tripList.get(num);
                }
            }
        }
        return null;
    }

    public NavigableMap<Integer, Stop> getTripList() {
        return tripList;
    }

    /**
     * @return returns string of data stored in trip class
     * @author Joseph Heinz - heinzja@msoe.edu
     */
    public String toString() {
        String toReturn = "RouteID: " + getRouteID() + "\n" +
                "ServiceID: " + getServiceID() + "\n" +
                "TripID: " + getTripID() + "\n" +
                "HeadSign: " + getTripHeadsign() + "\n" +
                "DirectionID: " + getDirectionID() + "\n" +
                "BlockID: " + getBlockID() + "\n" +
                "ShapeID: " + getShapeID() + "\n";
        return toReturn;
    }

    public String tripListToString(){
        String toReturn = "";
        toReturn += "TripID: " + this.getTripID() + "\n" + "Stops: " + "\n";
        NavigableSet<Integer> nav = tripList.navigableKeySet();
        for (Integer num: nav) {
            toReturn += "  " + num + " ID : " + tripList.get(num).getStopID() + ", " +
                    "Arrival: " + tripList.get(num).getArrivalTime() +
                    ", Departure: " + tripList.get(num).getDepartureTime() + "\n";
        }
        return toReturn;
    }

    /**
     * @param trip
     */
    public boolean equals(Trip trip) {
        return (this.getTripID().equals(trip.getTripID()));
    }
}