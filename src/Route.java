import com.sun.jdi.request.DuplicateRequestException;
import javafx.scene.paint.Color;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * Author: Gracie Horton
 * Description:
 * Date Created: 10/3/2017 - 4:57:32 PM
 */
public class Route {

	private NavigableMap<Integer, Stop> stops;
	private Color color;
	private String routeID;
	private String agencyID;
	private String routeShortName;
	private String routeLongName;
	private String routeDescription;
	private String routeType;
	private String routeUrl;
	private String routeColor;
	private String routeTextColor;

	/**
	 * Author:
	 * Description:
	 * @param route_id
	 * @param agency_id
	 * @param route_short_name
	 * @param route_long_name
	 * @param route_desc
	 * @param route_type
	 * @param route_url
	 * @param route_color
	 * @param route_text_color
	 */
	public Route(String route_id, String agency_id,String route_short_name, String route_long_name,
				 String route_desc,String route_type, String route_url,String route_color, String route_text_color){
		this.routeID = route_id;
		this.agencyID = agency_id;
		this.routeShortName = route_short_name;
		this.routeLongName = route_long_name;
		this.routeDescription = route_desc;
		this.routeType = route_type;
		this.routeUrl = route_url;
		this.routeColor = route_color;
		this.routeTextColor = route_text_color;
		this.stops = new TreeMap<>();
	}

	/**
	 * Author:
	 * Description:
	 * @param routeId
	 */
	public Route(String routeId) {
		this.routeID = routeId;
	}

	/**
	 * Author:
	 * Description:
	 * @param stops
	 */
	public Route(Collection<Stop> stops){

	}

	/**
	 * Author:
	 * Description:
	 * @param color
	 */
	public void setColor(Color color) {
		this.color = color;
	}

	/**
	 * Author:
	 * Description:
	 * @param routeShortName
	 */
	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	/**
	 * Author:
	 * Description:
	 * @param routeLongName
	 */
	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	/**
	 * Author:
	 * Description:
	 * @param routeDescription
	 */
	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}

	/**
	 * Author:
	 * Description:
	 * @param routeType
	 */
	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	/**
	 * Author:
	 * Description:
	 * @param routeUrl
	 */
	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}

	/**
	 * Author:
	 * Description:
	 * @param stop
	 * @param stopNum
	 * @return
	 * @throws DuplicateRequestException
	 */
	public boolean addStop(Stop stop, int stopNum) throws DuplicateRequestException {
		boolean result = false;
		if(stop != null) {
			if(!stops.containsKey(stopNum)) {
				stops.put(stopNum, stop);
				result = true;
			}else if(stops.get(stopNum).isEmpty()){
				stops.replace(stopNum, stop);
			}else if(!stops.get(stopNum).equals(stop)){
				throw new DuplicateRequestException("Attempted To Add Duplicate Stop to Route: "
						+ routeID);
			}
		}
		return result;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public NavigableMap<Integer, Stop> getStops() {
		return stops;
	}

	/**
	 * Author:
	 * Description:
	 * @param stops
	 */
	public void setStops(NavigableMap<Integer, Stop> stops) {
		this.stops = stops;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteID() {
		return routeID;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getAgencyID() {
		return agencyID;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteShortName() {
		return routeShortName;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteLongName() {
		return routeLongName;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteDescription() {
		return routeDescription;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteType() {
		return routeType;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteUrl() {
		return routeUrl;
	}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteColor(){return routeColor;}

	/**
	 * Author:
	 * Description:
	 * @return
	 */
	public String getRouteTextColor (){return routeTextColor;}

	/**
	 * Author:
	 * Description: Searches route for a stop with the specified stopID
	 * @param stopID to search for
	 * @return the stop with the specified stopID or null if no such stop is found.
	 */
	public Stop searchRoute(String stopID){
		if(stops != null) {
			NavigableSet<Integer> nav = stops.navigableKeySet();
			for (Integer num : nav) {
				if (stops.get(num).getStopID().equalsIgnoreCase(stopID)) {
					return stops.get(num);
				}
			}
		}
		return null;
	}
	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: Returns string of data stored in route class
	 * @return String - formatted string of Route data for GUI
	 */
	public String toStringData(){
		if(isEmpty()){
			return  "RouteID: " + getRouteID() +"\nNo data";
		}
		return  "RouteID: " + getRouteID() +"\n" +
				"AgencyID: " + getAgencyID() +"\n" +
				"ShortName: " + getRouteShortName() +"\n" +
				"LongName: " + getRouteLongName() +"\n" +
				"Description: " + getRouteDescription() +"\n" +
				"Type: " + getRouteType() +"\n" +
				"URL: " + getRouteUrl() +"\n" +
				"Color: " + getRouteColor() +"\n" +
				"TextColor: " + getRouteTextColor() +"\n";
	}

	/**
	 * Author:
	 * Description:
	 * @return String - formatted string
	 */
	public String toString(){
		return "RouteID: " + routeID + "\n  Route Name: " + routeLongName;
	}

	/**
	 * Author:
	 * Description: Compares two route objects based on their routeIDs
	 * @param route - the route to compare to
	 * @return boolean - true if their routeIDs are the same, false if they are not the same
	 */
	public boolean equals(Route route){
		return this.getRouteID().equalsIgnoreCase(route.getRouteID());
	}

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: Returns a formatted string for a a line in an Route export file.
	 * @return String - formatted string used for file exporting
	 */
	public String toStringExport(){
		//returns Route format: route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color"
		return String.format("%s,%s,%s,%s,%s,%s,%s,%s,%s",getRouteID(),getAgencyID(),getRouteShortName(),
				getRouteLongName(),getRouteDescription(),getRouteType(),getRouteUrl(),getRouteColor(),getRouteTextColor());
	}

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: checks to see if Route Object is empty and a placeholder, or a valid Route Object
	 * @return result of if the Route Object is used as a place holder (empty) or is a valid Route Object (not empty)
	 */
	public boolean isEmpty(){
		boolean result = true;
		if(getRouteDescription() != null && getRouteShortName() != null && getRouteLongName() != null && getRouteType() != null){
			result = false;
		}
		return result;
	}

	/**
	 * Author:
	 * Description:
	 * @return String -
	 */
	public String stopsToString() {
		StringBuilder toReturn = new StringBuilder();
		toReturn.append(toString()).append("Stops: \n");
		if(stops.size() != 0){
			toReturn.append("    No stops loaded yet");
		}else {
			for (int num : stops.keySet()) {
				toReturn.append("    ").append(num).append(".) StopID: ").append(stops.get(num).getStopID()).append(" ").append("StopName: ").append(stops.get(num).getName()).append("\n");
			}
		}
		return toReturn.toString();
	}

	/**
	 * Author:
	 * Description:
	 * @param route
	 * @throws IllegalArgumentException
	 */
	public void copyInstanceVariables(Route route) throws IllegalArgumentException{
		if(!this.getRouteID().equalsIgnoreCase(route.getRouteID())){
			throw new IllegalArgumentException("This trip's ID: " + this.getRouteID() + ", does " +
					"not match the ID of the argument: " + route.getRouteID());
		}
		this.color = route.getColor();
		this.agencyID = route.getAgencyID();
		this.routeShortName = route.getRouteShortName();
		this.routeLongName = route.getRouteLongName();
		this.routeDescription = route.getRouteDescription();
		this.routeType = route.getRouteType();
		this.routeUrl = route.getRouteUrl();
		this.routeColor = route.getRouteColor();
		this.routeTextColor = route.getRouteTextColor();
	}

}