import javafx.scene.paint.Color;

import java.util.Collection;

/**
 * @author hortong
 * @version 1.0
 * @created 03-Oct-2017 4:57:32 PM
 */
public class Route {

	private String routeTextColorCode;
	private String routeColorCode;
	private Color color;
	private String routeID;
	private Collection<Stop> stops;
	private String agencyID;
	private String routeShortName;
	private String routeLongName;
	private String routeDescription;
	private String routeType;
	private String routeUrl;

	public Route(String route_id, String agency_id, String route_short_name, String route_long_name,
				 String route_desc, String route_type, String route_url, String route_color, String route_text_color){
		this.routeID = route_id;
		this.agencyID = agency_id;
		this.routeShortName = route_short_name;
		this.routeLongName = route_long_name;
		this.routeDescription = route_desc;
		this.routeType = route_type;
		this.routeUrl = route_url;
		this.routeTextColorCode = route_text_color;
		this.routeColorCode = route_color;
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

	public void setStops(Collection<Stop> stops) {
		this.stops = stops;
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

	/**
	 * searches route for a stop with the specified stopID
	 * @param stopID to search for
	 * @return the stop with the specified stopID or null if no such stop is found.
	 */
	public Stop searchRoute(String stopID){
		for (Stop stop: stops) {
			if(stop.getStopID().equalsIgnoreCase(stopID)){
				return stop;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param route
	 */
	public boolean equals(Route route){
		return this.getRouteID().equalsIgnoreCase(route.getRouteID());
	}

	public String toString(){
		return "Route: " + routeID +
				"\n    AgencyID: " + agencyID +
				"\n    Name: " + routeShortName +
				"\n    Description: " + routeDescription +
				"\n    Type: " + routeType +
				"\n    URL: " + routeUrl;
	}

}