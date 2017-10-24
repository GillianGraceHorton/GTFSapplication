/**
 * @author hortong & joseph heinz - heinzja@msoe.edu
 * Description: creates new Location class which holds a latitude and longitude
 */
public class Location {
	private double latitude;
	private double longitude;

	public Location(double lon, double lat) throws IllegalArgumentException {
		setLat(lat);
		setLon(lon);
	}

	public double getLat() {
		return latitude;
	}

	public double getLon() {
		return longitude;
	}

	public void setLon(double lon) throws IllegalArgumentException {
		if(validLongitude(lon)){ this.longitude = lon; }
		else { throw new IllegalArgumentException("The longitude must fall within the range: " +
				"-180 <= x <= 180\n    Invalid: " + lon); }
	}

	public void setLat(double lat) throws IllegalArgumentException {
		if(validLatitude(lat)){ this.latitude = lat; }
		else{ throw new IllegalArgumentException("the latitude must fall withing the range: -90 " +
				"<= y <= 90\n    Invalid: " + lat); }
	}

	private boolean validLongitude(double lon){
		boolean result = false;
		if(lon <= 180 && lon >= -180){ result = true; }
		return result;
	}

	private boolean validLatitude(double lat){
		boolean result = false;
		if(lat <= 90 && lat >= -90){ result = true; }
		return result;
	}

}