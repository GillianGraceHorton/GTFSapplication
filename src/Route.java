import com.sun.jdi.request.DuplicateRequestException;
import javafx.scene.paint.Color;
import java.util.Collection;
import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;

/**
 * @author Gracie Horton
 * @version 1.0
 * @created 03-Oct-2017 4:57:32 PM
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

		stops = new TreeMap<>();
	}

	public Route(String routeId) {
		this.routeID = routeId;
	}

	/**
	 * 
	 * @param stops
	 */
	public void Route(Collection<Stop> stops){

	}

	public void setColor(Color color) {
		this.color = color;
	}

	public void setRouteShortName(String routeShortName) {
		this.routeShortName = routeShortName;
	}

	public void setRouteLongName(String routeLongName) {
		this.routeLongName = routeLongName;
	}

	public void setRouteDescription(String routeDescription) {
		this.routeDescription = routeDescription;
	}

	public void setRouteType(String routeType) {
		this.routeType = routeType;
	}

	public void setRouteUrl(String routeUrl) {
		this.routeUrl = routeUrl;
	}

	public boolean addStop(Stop stop, int stopNum) throws DuplicateRequestException {
		boolean result = false;
		if(stop != null) {
			if(!stops.containsKey(stopNum)) {
				stops.put(stopNum, stop);
				result = true;
			}else{
				throw new DuplicateRequestException("Attempted To Add Duplicate Stop");
			}
		}
		return result;
	}

	public Color getColor() {
		return color;
	}

	public String getRouteID() {
		return routeID;
	}

	public String getAgencyID() {
		return agencyID;
	}

	public String getRouteShortName() {
		return routeShortName;
	}

	public String getRouteLongName() {
		return routeLongName;
	}

	public String getRouteDescription() {
		return routeDescription;
	}

	public String getRouteType() {
		return routeType;
	}

	public String getRouteUrl() {
		return routeUrl;
	}

	public String getRouteColor(){return routeColor;}

	public String getRouteTextColor (){return routeTextColor;}

	/**
	 * searches route for a stop with the specified stopID
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
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * @return returns string of data stored in route class
	 */
	public String toString(){
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
	 * compares two route objects based on their routeIDs
	 * @param route to compare to
     * @return true if their routeIDs are the same and false if they are not
	 */
	public boolean equals(Route route){
		return this.getRouteID().equalsIgnoreCase(route.getRouteID());
	}

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
}