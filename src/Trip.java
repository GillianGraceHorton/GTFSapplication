import com.sun.jdi.request.DuplicateRequestException;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:35 PM
 */
@SuppressWarnings("ALL")
public class Trip {

    private String shapeID;
    private String blockID;
    private String directionID;
    private String tripHeadsign;
    private String serviceID;
    private NavigableMap<Integer, StopTime> tripList;
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
        this.tripList = new TreeMap<>();
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

    public void setTripList(NavigableMap<Integer, StopTime> tripList) {
        this.tripList = tripList;
    }

    public NavigableMap<Integer, StopTime> getTripList() {
        return tripList;
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

    public Boolean hasTripList(){
        return tripList.size()>0;
    }

    public boolean setTimes() {
        return false;
    }

    /**
     * added the specified stop at the specified index in the tripList.
     *
     * @param stopTime to be added to the tripList
     * @return true after the stop is added
     */
    public boolean addStopTime(StopTime stopTime) throws DuplicateRequestException {
        boolean result = false;
        if (stopTime != null) {
            if (!tripList.containsKey(stopTime.getStopSequence())) {
                tripList.put(stopTime.getStopSequence(), stopTime);
                result = true;
            }
            else {
                throw new DuplicateRequestException("Attempted To Add Duplicate Stop to Trip: " + tripID);
            }
        }
        if (route != null) {
            route.addStop(stopTime.getStop(), stopTime.getStopSequence());
        }
        return result;
    }

    /**
     * Gets the stop associated to the trip from the specified trip id.
     *
     * @param stopId
     * @return the stop object connected to the given stopID
     */
    public Stop getStop(String stopId) {
        Stop result = null;
        if (tripList != null && stopId != null) {
            for (StopTime stopTime:tripList.values()) {
                if(stopTime.getStopID().equals(stopId)){
                    return stopTime.getStop();
                }
            }
        }
        return result;
    }



    /**
     * @return returns string of data stored in trip class
     * @author Joseph Heinz - heinzja@msoe.edu
     */
    public String toStringData() {
        if (isEmpty()) {
            return "TripID: " + getTripID() + "\nNo data";
        }
        String toReturn = "TripID: " + getTripID() + "\n" +
                "ServiceID: " + getServiceID() + "\n" +
                "RouteID: " + getRouteID() + "\n" +
                "HeadSign: " + getTripHeadsign() + "\n" +
                "DirectionID: " + getDirectionID() + "\n" +
                "BlockID: " + getBlockID() + "\n" +
                "ShapeID: " + getShapeID() + "\n";
        return toReturn;
    }

    public String toString(){
        return "TripID: " + tripID + "\nRouteID: " + routeID;
    }

    public String tripListToString() {
        StringBuilder toReturn = new StringBuilder();
        toReturn.append("TripID: ").append(this.getTripID()).append("\n").append("Stops: ").append("\n");
        NavigableSet<Integer> nav = tripList.navigableKeySet();
        for (Integer num : nav) {
            toReturn.append("  ").append(num).append(".) StopID: ").append(tripList.get(num).getStopID()).append(", Name: ").append(tripList.get(num).getStop().getName()).append(", Arrival: ").append(tripList.get(num).getArrivalTime()).append(", Departure: ").append(tripList.get(num).getDepartureTime()).append("\n");
        }
        return toReturn.toString();
    }

    /**
     * @param trip
     */
    public boolean equals(Trip trip) {
        return (this.getTripID().equalsIgnoreCase(trip.getTripID()));
    }

    public String toStringExport() {
        //returns Trip format: route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id
        return String.format("%s,%s,%s,%s,%s,%s,%s", getRouteID(), getServiceID(), getTripID(), getTripHeadsign(),
                getDirectionID(), getBlockID(), getShapeID());
    }

    /**
     * Author: Joseph Heinz - heinzja@msoe.edu
     * Description: checks to see if the Trip Object is a placeholder (empty) or is a valid Trip Object
     *
     * @return result of if Trip Object is empty or not
     */
    public boolean isEmpty() {
        boolean result = true;
        if (getRouteID() != null && getServiceID() != null) {
            result = false;
        }
        return result;
    }

    /**
     * copies every instance variable within the trip parameter over this instance except for
     * the tripID, and the tripList
     * @param trip to copy from
     * @throws IllegalArgumentException if the IDs don't match
     */
    public void copyInstanceVariables(Trip trip)throws IllegalArgumentException {
        if(!this.getTripID().equalsIgnoreCase(trip.getTripID())){
            throw new IllegalArgumentException("This trip's ID: " + this.getTripID() + ", does " +
                    "not match the ID of the argument: " + trip.getTripID());
        }
        this.shapeID = trip.getShapeID();
        this.blockID = trip.getBlockID();
        this.directionID = trip.getDirectionID();
        this.tripHeadsign = trip.getTripHeadsign();
        this.serviceID = trip.getServiceID();
        this.route = trip.getRoute();
        this.routeID = trip.getRouteID();
    }
}