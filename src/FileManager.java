

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author heinzja
 * @version 1.0
 * Created: 03-Oct-2017 4:57:25 PM
 */
public class FileManager {

	private Collection<File> validFileList;
	private NavigableMap<String, String> validFileTypes;

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: initializes validFileList and validFileTypes
	 */
	public FileManager(){
		validFileList = new ArrayList<File>();
		validFileTypes = new TreeMap<>();
		addValidType();
	}

	/**
	 * @author hortong
	 * @return
	 */
	public ArrayList<Object> loadFromValidFiles(){
		ArrayList<Object> itemsToReturn = new ArrayList<Object>();
		File validFiles = new File("validfiles");
		//TODO: validFiles.list() may cause a NullPointerException
		try {
			if (validFiles.exists()) {
				for (String fileName : validFiles.list()) {
					itemsToReturn.addAll(parseFile(new File(validFiles, fileName)));
				}
			}
		}catch (Exception e){
			System.out.println("TEST: loadFromValidFiles -> " + e);
		}
		return itemsToReturn;
	}

	/**
	 * @author
	 *	Description: removes file from 'validfiles' directory
	 * @param filename the name of the file to remove
	 */
	public boolean rmFile(String filename){
		boolean result = false;
		//TODO: Complete rmFile
		return result;
	}

	/**
	 *	Description: returns specified file stored in Collection validFileList
	 * @param filename - name of the file to return
	 * @return File - file found in list, returns NULL if file does not exist
	 */
	private File getFile(String filename){
		File result = null;
		//TODO: Complete getFile
		return result;
	}

	/**
     * parses the information from a file containing stop objects.
	 * @author hortong
	 * @param file containing stop objects
     * @return Array containing all the stop objects
	 */
	public ArrayList<Object> parseStopFile(File file) throws Exception {
		ArrayList<Object> toReturn = new ArrayList<>();
		try {
			String stop_id, stop_desc, stop_name;
			double stop_lat, stop_lon;
			Scanner scanner = new Scanner(file);
			String firstLine = scanner.nextLine();
			if(!firstLine.equals(validFileTypes.get("stops"))){
				throw new Exception("the file: " + file.getName() + ", was not " +
						"formatted correctly for a stop file");
			}
			String line;
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				stop_id = items[0];
				stop_name = items[1];
				stop_desc = items[2];
				stop_lat = Double.parseDouble(items[3]);
				stop_lon = Double.parseDouble(items[4]);
				toReturn.add(new Stop(stop_lon, stop_lat, stop_name, stop_id, stop_desc));
			}
		}catch(NullPointerException e){
			throw new Exception("the file: " + file.getName() + ", was formatted incorrectly for " +
					"a stop file", e);
		}catch(Exception e){
			throw new Exception("there was a problem reading from the file: " + file.getName(), e);
		}
		return toReturn;
	}

	/**
	 * parses the information from a file containing route objects.
     * @author hortong
     * @param file containing route objects
     * @return Array containing all the route objects
	 */
	public ArrayList<Object> parseRouteFile(File file) throws Exception {
		ArrayList<Object> toReturn = new ArrayList<>();
		try {
			String route_id, agency_id, route_short_name, route_long_name;
			String route_desc, route_type, route_url, route_color, route_text_color;
			Scanner scanner = new Scanner(file);
			String firstLine = scanner.nextLine();
			if(!firstLine.equals(validFileTypes.get("routes"))){
				throw new Exception("the file: " + file.getName() + ", was not " +
						"formatted correctly for a route file");
			}
			String line;
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				route_id = items[0];
				agency_id = items[1];
				route_short_name = items[2];
				route_long_name = items[3];
				route_desc = items[4];
				route_type = items[5];
				route_url = items[6];
				route_color = items[7];
				route_text_color = items[8];
				toReturn.add(new Route(route_id, agency_id, route_short_name, route_long_name,
						route_desc, route_type, route_url, route_color, route_text_color));
			}
		}catch(NullPointerException e){
			throw new Exception("the file: " + file.getName() + ", was formatted incorrectly for " +
					"a route file", e);
		}catch(Exception e){
			throw new Exception("there was a problem reading from the file: " + file.getName(), e);
		}
		return toReturn;
	}

	/**
	 * @author hortong
	 * @param file - file to parse for Trip data
	 * @return - returns ArrayList full of parsed data from trip file.
	 */
	public ArrayList<Object> parseTripFile(File file) throws Exception {
		ArrayList<Object> toReturn= new ArrayList<>();
		try {
			String route_id, service_id, trip_id, trip_head_sign, direction_id, block_id, shape_id;
			Scanner scanner = new Scanner(file);
			String firstLine = scanner.nextLine();
			if(!firstLine.equals(validFileTypes.get("trips"))){
				throw new Exception("the file: " + file.getName() + ", was not " +
						"formatted correctly for a trips file");
			}
			String line;
			while (scanner.hasNext()) {
				line = scanner.nextLine();
				String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				route_id = items[0];
				service_id = items[1];
				trip_id = items[2];
				trip_head_sign = items[3];
				direction_id = items[4];
				block_id = items[5];
				shape_id = items[6];
				toReturn.add(new Trip(trip_id, service_id, route_id, trip_head_sign, direction_id, block_id, shape_id));
			}
		}catch(NullPointerException e){
			throw new Exception("the file: " + file.getName() + ", was formatted incorrectly for " +
					"a trip file", e);
		}catch(Exception e){
			throw new Exception("there was a problem reading from the file: " + file.getName(), e);
		}
		return toReturn;
	}

	public ArrayList<Object> parseStopTimesFile(File file) throws Exception {
	    ArrayList<Object> toReturn = new ArrayList<>();
	    try {
			String trip_id, arrival_time, departure_time, stop_id, stop_sequence, stop_headsign,
					pickup_type, drop_off_type;
			Scanner scanner = new Scanner(file);
			String firstLine = scanner.nextLine();
			if(!firstLine.equals(validFileTypes.get("stop_times"))){
				throw new Exception("the file: " + file.getName() + ", was not " +
						"formatted correctly for a stopTimes file");
			}
			String line;

			while (scanner.hasNext()) {
				line = scanner.nextLine();
				String[] items = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
				trip_id = items[0];

				arrival_time = items[1];
				//if(!arrival_time.equals("[0-5][0-9]:[0-5][0-9]:[0-5][0-9]")) {
				//	throw new IllegalArgumentException("Arrival time for stop_time in trip " + trip_id + "not valid");
				//}

				departure_time = items[2];
				stop_id = items[3];
				stop_sequence = items[4];
				stop_headsign = items[5];
				pickup_type = items[6];
				drop_off_type = items[7];
				toReturn.add(new StopTime(trip_id, arrival_time, departure_time, stop_id,
						stop_sequence, stop_headsign, pickup_type, drop_off_type));
			}
		}catch(NullPointerException e){
			throw new Exception("the file: " + file.getName() + ", was formatted incorrectly for " +
					"a stopTimes file", e);
		}catch(Exception e){
			throw new Exception("there was a problem reading from the file: " + file.getName(), e);
		}
        return toReturn;
    }

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: Checks what type of file is to be parsed, and sends it to the correct parse method.
	 * @param  file - the file to parse for data
	 * @return returns an ArrayList of objects which contain the parsed data from the file.
	 */
	public ArrayList<Object> parseFile(File file){
		ArrayList<Object> result = null;
		try {
			//TODO: as we add more functionality, add specific parse for each valid file type.
			switch (getFirstLine(file)) {
				case "stop_id,stop_name,stop_desc,stop_lat,stop_lon":
					result = parseStopFile(file);
					break;
				case "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color":
					result = parseRouteFile(file);
					break;
				case "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id":
					result = parseTripFile(file);
					break;
				case "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type":
					result = parseStopTimesFile(file);
					break;
				default:
					System.out.println("TEST: parseFile -> parse format of valid file not supported yet");
			}
		}catch (Exception e){
			System.out.println("TEST: parseFile -> " + e);
		}
		return result; 													//returns result of if the file was parsed correctly.
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Temporary valid file type arraylist will be changed to be less static in the future.
	 * Description: adds valid file types to an arraylist of valid file types.
	 */
	private void addValidType(){
		validFileTypes.put("agency", "agency_id,agency_name,agency_url,agency_timezone," +
				"agency_phone");
		validFileTypes.put("calender", "service_id,monday,tuesday,wednesday,thursday,friday," +
				"saturday," +
				"sunday,start_date,end_date");
		validFileTypes.put("calender_dates", "service_id,date,exception_type");
		validFileTypes.put("fare_attributes", "fare_id,price,currency_type,payment_method," +
				"transfers,transfer_duration,agency_id");
		validFileTypes.put("feed_info", "feed_publisher_name,feed_publisher_url,feed_lang");
		validFileTypes.put("routes", "route_id,agency_id,route_short_name,route_long_name," +
				"route_desc," +
				"route_type,route_url,route_color,route_text_color");
		validFileTypes.put("shapes", "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence");
		validFileTypes.put("stop_times", "trip_id,arrival_time,departure_time,stop_id," +
				"stop_sequence," +
				"stop_headsign,pickup_type,drop_off_type");
		validFileTypes.put("stops", "stop_id,stop_name,stop_desc,stop_lat,stop_lon");
		validFileTypes.put("transfers", "from_stop_id,to_stop_id,transfer_type");
		validFileTypes.put("trips", "route_id,service_id,trip_id,trip_headsign,direction_id," +
				"block_id," +
				"shape_id");
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: Returns the first line of a given file, used for file validity check
	 * @param file - the file to get the first line of text from.
	 * @return returns string of first line of text from file.
	 */
	private String getFirstLine(File file) throws Exception {
		String firstLine = null;
		try{
			InputStream in = Files.newInputStream(file.toPath());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			firstLine = reader.readLine();
		}catch (Exception e){
			throw new Exception("there was a problem reading the first line of the file: " + file
					.getName(), e);
		}
		return firstLine;
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: exports stops to user choosen directory
	 * @param exportName the file containing the desired directory and name of the file to export
	 * @param  ds the DataStorage object which holds all the stops
	 */
	public void exportStopFile(File exportName, DataStorage ds) throws Exception {
		File exportDir = new File(exportName.getParent(),"exports");
		try {
			if(!exportDir.exists()){
				exportDir.mkdir();
			}
			File exportFile = new File(exportDir.getPath(),exportName.getName() + ".txt");
			if (!exportFile.exists()) {
				exportFile.createNewFile();
			}

			PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
			pw.println("stop_id,stop_name,stop_desc,stop_lat,stop_lon");
			Iterator<Stop> itr = ds.getStops().iterator();
			while(itr.hasNext()){
				Stop tmp = itr.next();
				String stopID,stopName,stopDescription;
				double stopLat,stopLon;
				stopID = tmp.getStopID();
				stopName = tmp.getName();
				stopDescription = tmp.getStopDescription();
				stopLat = tmp.getLocation().getLat();
				stopLon = tmp.getLocation().getLon();
				pw.format("%s,%s,%s,%f,%f\n",stopID,stopName,stopDescription,stopLat,stopLon);
			}
			pw.close();
		}catch (Exception e){
			throw new Exception("there was a problem writing the stop objects to the specified " +
					"file: " + exportName.getName(), e);
		}
		System.out.println("TEST: exportStopFile completed");
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: exports routes to user choosen directory
	 * @param exportName the file containing the desired directory and name of the file to export
	 * @param  ds the DataStorage object which holds all the routes
	 */
	public void exportRouteFile(File exportName, DataStorage ds) throws Exception {
		File exportDir = new File(exportName.getParent(),"exports");
		try {
			if(!exportDir.exists()){
				exportDir.mkdir();
			}
			File exportFile = new File(exportDir.getPath(),exportName.getName() + ".txt");
			if (!exportFile.exists()) {
				exportFile.createNewFile();
			}

			PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
			pw.println("route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color");
			Iterator<Route> itr = ds.getRoutes().iterator();
			while(itr.hasNext()){
				Route tmp = itr.next();
				String routeID,agencyID,routeShort,routeLong,routeDesc,routeType,routeURL,routeColor,routeTextColor;
				routeID = tmp.getRouteID();
				agencyID = tmp.getAgencyID();
				routeShort = tmp.getRouteShortName();
				routeLong = tmp.getRouteLongName();
				routeDesc = tmp.getRouteDescription();
				routeType = tmp.getRouteType();
				routeURL = tmp.getRouteUrl();
				routeColor = tmp.getRouteColor();
				routeTextColor = tmp.getRouteTextColor();
				pw.format("%s,%s,%s,%s,%s,%s,%s,%s,%s\n",routeID,agencyID,routeShort,routeLong,routeDesc,routeType,routeURL,routeColor,routeTextColor);
			}
			pw.close();
		}catch (Exception e){
			throw new Exception("there was a problem writing the route objects to the specified " +
					"file: " + exportName.getName(), e);
		}
		System.out.println("TEST: exportRouteFile completed");
	}

	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: exports trips to user choosen directory
	 * @param exportName the file containing the desired directory and name of the file to export
	 * @param  ds the DataStorage object which holds all the trips
	 */
	public void exportTripFile(File exportName, DataStorage ds) throws Exception {
		File exportDir = new File(exportName.getParent(),"exports");
		try {
			if(!exportDir.exists()){
				exportDir.mkdir();
			}
			File exportFile = new File(exportDir.getPath(),exportName.getName() + ".txt");
			if (!exportFile.exists()) {
				exportFile.createNewFile();
			}

			PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
			pw.println("route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id");
			Iterator<Trip> itr = ds.getTrips().iterator();
			while(itr.hasNext()){
				Trip tmp = itr.next();
				String routeID,serviceID,tripID,tripHeadsign,directionID,blockID,shapeID;
				routeID = tmp.getRouteID();
				serviceID = tmp.getServiceID();
				tripID = tmp.getTripID();
				tripHeadsign = tmp.getTripHeadsign();
				directionID = tmp.getDirectionID();
				blockID = tmp.getBlockID();
				shapeID = tmp.getShapeID();
				pw.format("%s,%s,%s,%s,%s,%s,%s\n",routeID,serviceID,tripID,tripHeadsign,directionID,blockID,shapeID);
			}
			pw.close();
		}catch (Exception e){
			throw new Exception("there was a problem writing the trip objects to the specified " +
					"file: " + exportName.getName(), e);
		}
		System.out.println("TEST: exportTripFile completed");
	}
	/**
	 * @author Joseph Heinz - heinzja@msoe.edu
	 * Description: exports all stored stop times to user choosen directory
	 * @param exportName the file containing the desired directory and name of the file to export
	 * @param  ds the DataStorage object which holds all the stop times
	 */
	public void exportStopTimesFile(File exportName, DataStorage ds) throws Exception {
		File exportDir = new File(exportName.getParent(),"exports");
		try {
			if(!exportDir.exists()){
				exportDir.mkdir();
			}
			File exportFile = new File(exportDir.getPath(),exportName.getName() + ".txt");
			if (!exportFile.exists()) {
				exportFile.createNewFile();
			}
			PrintWriter pw = new PrintWriter(exportFile, "UTF-8");
			pw.println("trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type");
			Iterator<StopTime> itr = ds.getStopTimes().iterator();
			while(itr.hasNext()){
				StopTime tmp = itr.next();
				String tripID,arrivalTime,departureTime,stopID,stopSeq,stopHeadsign,pickupType,dropoffType;
				tripID = tmp.getTripID();
				arrivalTime = tmp.getArrivalTime();
				departureTime = tmp.getDepartureTime();
				stopID = tmp.getStopID();
				stopSeq = tmp.getStopSequence();
				stopHeadsign = tmp.getStopHeadsign();
				pickupType = tmp.getPickupType();
				dropoffType = tmp.getDropoffType();
				pw.format("%s,%s,%s,%s,%s,%s,%s,%s\n",tripID,arrivalTime,departureTime,stopID,stopSeq,stopHeadsign,pickupType,dropoffType);
			}
			pw.close();
		}catch (Exception e){
			throw new Exception("there was a problem writing the stopTime objects to the " +
					"specified " +
					"file: " + exportName.getName(), e);
		}
		System.out.println("TEST: exportStopTimesFile completed");
	}
}