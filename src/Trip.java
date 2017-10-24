import com.sun.jdi.request.DuplicateRequestException;
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

        tripList = new TreeMap<>();
    }

    /**
     * @param route
     */
    public Trip(Route route) {
        //TODO: Implement if needed, remove if not
    }

    public Trip(String tripID) {
        this.tripID = tripID;
        tripList = new TreeMap<>();
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
    public boolean addStop(Stop stop, int stopNum) throws DuplicateRequestException {
        boolean result = false;
        if(stop != null) {
            if (!tripList.containsKey(stopNum)) {
                tripList.put(stopNum, stop);
                result = true;
            }else{
                throw new DuplicateRequestException("Attempted To Add Duplicate Stop");
            }
        }
        if(route != null){
            route.addStop(stop, stopNum);
        }

        return result;
    }

    /**
     * Gets the stop associated to the trip from the specified trip id.
     * @param stopId
     * @return the stop object connected to the given stopID
     */
    public Stop getStop(String stopId){
        Stop result = null;
        if(tripList != null && stopId != null) {
            NavigableSet<Integer> nav = tripList.navigableKeySet();
            for (Integer num : nav) {
                if (tripList.get(num).getStopID().equalsIgnoreCase(stopId)) {
                    result = tripList.get(num);
                }
            }
        }
        return result;
    }

    public NavigableMap<Integer, Stop> getTripList() {
        return tripList;
    }

    /**
     * @return returns string of data stored in trip class
     * @author Joseph Heinz - heinzja@msoe.edu
     */
    public String toString() {
        if(isEmpty()){
            return "TripID: " + getTripID() + "\nNo data";
        }
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
        return (this.getTripID().equalsIgnoreCase(trip.getTripID()));
    }

    public String toStringExport(){
        //returns Trip format: route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id
        return String.format("%s,%s,%s,%s,%s,%s,%s",getRouteID(),getServiceID(),getTripID(),getTripHeadsign(),
                getDirectionID(),getBlockID(),getShapeID());
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: checks to see if the Trip Object is a placeholder (empty) or is a valid Trip Object
     * @return result of if Trip Object is empty or not
     */
    public boolean isEmpty(){
        boolean result = true;
        if(getRouteID() != null && getServiceID() != null){
            result = false;
        }
        return result;
    }

}