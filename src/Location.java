/**
 * @author hortong & joseph heinz - heinzja@msoe.edu
 * Description: creates new Location class which holds a latitude and longitude
 */
public class Location {
	private double latitude;
	private double longitude;

	public Location(double lon, double lat) throws IllegalArgumentException {
		if(validLongitude(lon) && validLatitude(lat)){

			this.longitude = lon;
			this.latitude = lat;
		}
		else{ throw new IllegalArgumentException(); }
	}

	public double getLat() {
		return latitude;
	}

	public double getLon() {
		return longitude;
	}

	public void setLon(double lon) throws IllegalArgumentException {
		if(validLongitude(lon)){ this.longitude = lon; }
		else { throw new IllegalArgumentException(); }
	}

	public void setLat(double lat) throws IllegalArgumentException {
		if(validLatitude(lat)){ this.latitude = lat; }
		else{ throw new IllegalArgumentException(); }
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