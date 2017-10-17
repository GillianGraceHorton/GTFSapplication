import com.sun.media.sound.InvalidFormatException;

/**
 * @author hortong & joseph heinz - heinzja@msoe.edu
 * Description: creates new Location class which holds a latitude and longitude
 */
public class Location {
	private double latitude;
	private double longitude;

	public Location(double lon, double lat) throws InvalidFormatException {
		if(validLongitude(lon) && validLatitude(lat)){
			this.longitude = lon;
			this.latitude = lat;
		}
		else{ throw new InvalidFormatException(); }
	}

	public double getLat() {
		return latitude;
	}

	public double getLon() {
		return longitude;
	}

	public void setLon(double lon) throws InvalidFormatException {
		if(validLongitude(lon)){ this.longitude = lon; }
		else { throw new InvalidFormatException(); }
	}

	public void setLat(double lat) throws InvalidFormatException {
		if(validLatitude(lat)){ this.latitude = lat; }
		else{ throw new InvalidFormatException(); }
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