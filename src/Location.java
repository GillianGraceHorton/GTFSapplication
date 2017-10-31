/**
 * Author: hortong & joseph heinz - heinzja@msoe.edu
 * Description: Creates new Location class which holds a latitude and longitude
 */
public class Location {
	private double latitude;
	private double longitude;

	/**
	 * Author: hortong
	 * Description: Initializes the Location class
	 * @param lon - longitude value to store
	 * @param lat - latitude value to store
	 * @throws IllegalArgumentException - lon or lat not in valid range.
	 */
	public Location(double lon, double lat) throws IllegalArgumentException {
		setLat(lat);
		setLon(lon);
	}

	/**
	 * Author: hortong
	 * Description: Returns latitude
	 * @return double - value stored in latitude.
	 */
	public double getLat() {
		return latitude;
	}

	/**
	 * Author: hortong
	 * Description: Returns longitude
	 * @return double - value stored in longitude.
	 */
	public double getLon() {
		return longitude;
	}

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: Sets the longitude to the input value if it exists within valid range.
	 * @param lon - longitude to set for location object
	 * @throws IllegalArgumentException - longitude not in valid range.
	 */
	public void setLon(double lon) throws IllegalArgumentException {
		if(validLongitude(lon)){ this.longitude = lon; }
		else {
			throw new IllegalArgumentException("The longitude must fall within the range: -180 <= x <= 180\n" +
				"Invalid: " + lon);
		}
	}

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: Sets the latitude to the input value if it exists within valid range.
	 * @param lat - latitude to set for location object
	 * @throws IllegalArgumentException - latitude not in valid range.
	 */
	public void setLat(double lat) throws IllegalArgumentException {
		if(validLatitude(lat)){ this.latitude = lat; }
		else{
			throw new IllegalArgumentException("the latitude must fall withing the range: -90 <= y <= 90\n" +
				"Invalid: " + lat);
		}
	}

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: Checks if longitude is within valid range.
	 * @param lon - longitude to check validity of
	 * @return result - true if valid, false if not valid
	 */
	private boolean validLongitude(double lon){
		boolean result = false;
		if(lon <= 180 && lon >= -180){ result = true; }
		return result;
	}

	/**
	 * Author: Joseph Heinz - heinzja@msoe.edu
	 * Description: checks if latitude is within valid range.
	 * @param lat - latitude to check validity of
	 * @return result - true if valid, false if not valid
	 */
	private boolean validLatitude(double lat){
		boolean result = false;
		if(lat <= 90 && lat >= -90){ result = true; }
		return result;
	}

}