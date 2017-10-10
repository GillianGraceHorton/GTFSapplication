import java.util.Collection;

/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:35 PM
 */
public class Trip {

	private String shapeID;
	private String blockID;
	private String directionID;
	private String tripHeadsign;
	private String serviceID;
	private Collection<Stop> tripList;
	private Route route;
	private String routeID;
	private String tripID;

	public Trip(String tripID, String serviceID, String routeID, String tripHeadsign, String
			directionID, String blockID, String shapeID){
		this.tripID = tripID;
		this.serviceID = serviceID;
		this.routeID = routeID;
		this.tripHeadsign = tripHeadsign;
		this.directionID = directionID;
		this.blockID = blockID;
		this.shapeID = shapeID;
	}

	/**
	 * 
	 * @param route
	 */
	public Trip(Route route){

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

	public boolean setTimes(){
		return false;
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * @return returns string of data stored in trip class
	 */
	public String toString(){
		return  "RouteID: " + getRouteID() + "\n" +
				"ServiceID: " + getServiceID() + "\n" +
				"TripID: " + getTripID() + "\n" +
				"HeadSign: " + getTripHeadsign() + "\n" +
				"DirectionID: " + getDirectionID() + "\n" +
				"BlockID: " + getBlockID() + "\n" +
				"ShapeID: " + getShapeID() + "\n";
	}

	/**
	 * 
	 * @param trip
	 */
	public boolean equals(Trip trip){
		return false;
	}

}